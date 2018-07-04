package alex.alves.smartscanner;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.Locale;

public class TelaImagem extends AppCompatActivity {


    ImageView mostrarFotos;
    String s=null;
    Ler lerTexto;

    int codigoFoto=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_imagem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        mostrarFotos = (ImageView) findViewById(R.id.imageView2);

        // Preparando para receber os textos
        lerTexto = new Ler();
        lerTexto.initLerTexto(this);

    }
    public void fabAction( View v)
    {
      //  Toast.makeText(getBaseContext()," VocÊ clicou no botão flutuante ",Toast.LENGTH_LONG).show();

        Intent captarFoto =  new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
       // Intent captarFoto =  new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        //  codigoFoto=123;
        startActivityForResult(Intent.createChooser(captarFoto, "Selecione uma imagem"), codigoFoto);
      //  startActivityForResult(captarFoto,codigoFoto);

    }


   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
       if (resultCode == TelaImagem.RESULT_OK) {

           if (requestCode == codigoFoto) {

               Uri imagemCaptada = data.getData();
               try {
                   Bitmap   bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagemCaptada);
                   mostrarFotos.setImageBitmap(bitmap);
                   // Redimensiona para o tamanho da ImageView
                   mostrarFotos.setScaleType(ImageView.ScaleType.CENTER);
               } catch (IOException e) {
                   e.printStackTrace();
               }

           }
       }
   }



    public Bitmap getBitmap(Uri imagem)
    {

        return BitmapFactory.decodeFile(String.valueOf(imagem));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        // Pega o inflater já existente
        MenuInflater criarMenu = getMenuInflater();
        // Cria menu a partir da leitura de um xml
        criarMenu.inflate(R.menu.meu_menu,menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Implementar ação ao clicar no item do menu

        switch(item.getItemId()){
            case R.id.id_capturar:
                if( s==null){
                    s= "Texto não capturado";
                }else {
                    Intent telaCam = new Intent(TelaImagem.this, telaCapturar.class);
                    telaCam.putExtra("telaCapturar", s);
                    startActivity(telaCam);
                    break;
                }
            case R.id.id_ler:

                s =getTextoImagem();
                Toast.makeText(getBaseContext(),s,Toast.LENGTH_LONG).show();

                lerTexto.getLer(s);
                break;
        }


        return super.onOptionsItemSelected(item);
    }



    public String getTextoImagem() {
        StringBuilder s = new StringBuilder();
        BitmapDrawable drawable = (BitmapDrawable)mostrarFotos.getDrawable();
        Bitmap bm = drawable.getBitmap();

        // Da API vision do google
        TextRecognizer pegarTexto = new TextRecognizer.Builder(getApplicationContext()).build();

        if(!pegarTexto.isOperational()){
            Toast.makeText(getApplicationContext(), " Texto não abriu",Toast.LENGTH_SHORT).show();
        }else{
            Frame frame = new Frame.Builder().setBitmap(bm).build();
            SparseArray<TextBlock> palavras = pegarTexto.detect(frame);

            for(int i=0;i<palavras.size();i++){

                TextBlock p = palavras.valueAt(i);
                s.append(p.getValue());
                s.append("\n");

            }
        }
        return s.toString();
    }

}
