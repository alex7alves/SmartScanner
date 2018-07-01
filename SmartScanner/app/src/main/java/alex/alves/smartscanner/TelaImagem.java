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

    TextToSpeech lerTexto;

    int codigoFoto=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_imagem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    /*    fab.setOnClickListener(new View.OnClickListener() {
         //   @Override
           /* public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        mostrarFotos = (ImageView) findViewById(R.id.imageView2);


        // Preparando para receber os textos
        TextToSpeech.OnInitListener ouvir =
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(final int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            Log.d("OnInitListener", "Processo de leitura carregado corretamente.");
                            lerTexto.setLanguage(Locale.getDefault());
                        } else {
                            Log.d("OnInitListener", "Erro ao carregar a voz");
                        }
                    }
                };
        lerTexto = new TextToSpeech(this.getApplicationContext(), ouvir);


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

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == TelaImagem.RESULT_OK) {

              if (requestCode == codigoFoto) {

                  Uri imagemCaptada = data.getData();
                  String[] filePathColumn = { MediaStore.Images.Media.DATA };

                  Cursor cursor = getContentResolver().query(imagemCaptada,
                          filePathColumn, null, null, null);
                  cursor.moveToFirst();

                  int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                  String picturePath = cursor.getString(columnIndex);
                  cursor.close();

                 // mostrarFotos.setImageBitmap(getBitmap(imagemCaptada));
               //  Bitmap bm = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.teste);
                 // mostrarFotos.setImageBitmap(bm);
                  mostrarFotos.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                  //
                  //Log.d("OnInitListener",String.valueOf(imagemCaptada));
                  mostrarFotos.setScaleType(ImageView.ScaleType.FIT_XY);
                //  Toast.makeText(getBaseContext(),String.valueOf(picturePath),Toast.LENGTH_LONG).show();
            }
        }
    }
    */
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == codigoFoto && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
           // ImageView imageView = (ImageView) findViewById(R.id.iv);
            mostrarFotos.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

*/
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
               //  mostrarFotos.setImageBitmap(BitmapFactory.decodeFile(picturePath));
               //
               //Log.d("OnInitListener",String.valueOf(imagemCaptada));
              // mostrarFotos.setScaleType(ImageView.ScaleType.FIT_XY);
               //  Toast.makeText(getBaseContext(),String.valueOf(picturePath),Toast.LENGTH_LONG).show();
           }
       }
   }



    public Bitmap getBitmap(Uri imagem)
    {
      // Bitmap bm = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.olhos);

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


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Implementar ação ao clicar no item do menu

        switch(item.getItemId()){
            case R.id.id_capturar:
                //lerTexto.speak(" VocÊ clicou no botão capturar ", TextToSpeech.QUEUE_ADD, null, "DEFAULT");
                String s =getTextoImagem();
                Toast.makeText(getBaseContext(),s,Toast.LENGTH_LONG).show();

                //lerTexto.speak(" VocÊ clicou no botão capturar ", TextToSpeech.QUEUE_ADD, null, "DEFAULT");
                lerTexto.speak(s, TextToSpeech.QUEUE_ADD, null, "DEFAULT");
                break;
            case R.id.id_ler:
               /* Intent telaCam = new Intent(TelaCamera.this,TelaImagem.class);
                // telaCam.putExtra("tela","Bom dia");
                startActivity(telaCam );
                break;*/
        }


        return super.onOptionsItemSelected(item);
    }


   /* public String getTextoImagem(){
         final String[] texto = new String[1];
         TextRecognizer pegarTexto = new TextRecognizer.Builder(getApplicationContext()).build();
         pegarTexto.setProcessor(new Detector.Processor<TextBlock>() {

             @Override
             public void release() {

             }

             @Override
             public void receiveDetections(Detector.Detections<TextBlock> detections) {
                 final SparseArray<TextBlock> palavras = detections.getDetectedItems();

                 if(palavras.size() !=0){
                     mostrarFotos.post(new Runnable() {
                         @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                         @Override
                         public void run() {

                             StringBuilder s = new StringBuilder();
                             for(int i=0;i<palavras.size();i++){

                                 TextBlock p = palavras.valueAt(i);
                                 s.append(p.getValue());
                                 s.append("\n");
                             }

                             texto[0] =s.toString();
                             lerTexto.speak(s.toString(), TextToSpeech.QUEUE_ADD, null, "DEFAULT");
                        }
                    });
                 }
             }
         });
            return texto[0];
    }*/

    public String getTextoImagem() {
        StringBuilder s = new StringBuilder();
        BitmapDrawable drawable = (BitmapDrawable)mostrarFotos.getDrawable();
        Bitmap bm = drawable.getBitmap();
       // Bitmap bm= mostrarFotos.getDrawingCache();
       // Bitmap bm = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.olhos);

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
          //  campo.setText(s.toString());
        }
        return s.toString();
    }


}
