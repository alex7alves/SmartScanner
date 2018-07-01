package alex.alves.smartscanner;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class TelaImagem extends AppCompatActivity {


    ImageView mostrarFotos;
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
}
