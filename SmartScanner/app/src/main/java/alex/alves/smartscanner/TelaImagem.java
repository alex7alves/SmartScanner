package alex.alves.smartscanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class TelaImagem extends AppCompatActivity {


    ImageView mostrarFotos;
    int codigoFoto=0;
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

    }
    public void fabAction( View v)
    {
        Toast.makeText(getBaseContext()," VocÊ clicou no botão flutuante ",Toast.LENGTH_LONG).show();

        Intent captarFoto =  new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        codigoFoto=123;
        startActivityForResult(Intent.createChooser(captarFoto, "Selecione uma imagem"), codigoFoto);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == TelaImagem.RESULT_OK) {

              if (requestCode == codigoFoto) {

                  Uri imagemCaptada = data.getData();
                  mostrarFotos.setImageBitmap(getBitmap(imagemCaptada));
                  mostrarFotos.setScaleType(ImageView.ScaleType.FIT_XY);

            }
        }
    }
    public Bitmap getBitmap(Uri imagem)
    {
        //Bitmap bm = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.olhos);
        return BitmapFactory.decodeFile(String.valueOf(imagem));
    }
}
