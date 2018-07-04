package alex.alves.smartscanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.Locale;


public class TelaCamera extends AppCompatActivity {


    String texto=null;
    SurfaceView camera;
    CameraSource capturar;
    final int cameraPermission = 1001;

    Ler lerTexto;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case cameraPermission:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        capturar.start(camera.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_camera);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        camera = (SurfaceView) findViewById(R.id.surfaceView);
        getTexto();

        lerTexto = new Ler();
        lerTexto.initLerTexto(this);
    }


    public void getTexto(){
        // Da API vision do google
        final TextRecognizer pegarTexto = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!pegarTexto.isOperational()) {
            Toast.makeText(getApplicationContext(), " Texto não abriu", Toast.LENGTH_SHORT).show();
        } else {

            GerarCameraSoruce(pegarTexto,1024,768,1.0f,true);

            camera.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(TelaCamera.this, new String[]{Manifest.permission.CAMERA},
                                    cameraPermission);
                            return;
                        }
                        capturar.start(camera.getHolder());
                        pegarTexto.setProcessor(new Detector.Processor<TextBlock>() {

                            @Override
                            public void release() {

                            }

                            @Override
                            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                                final SparseArray<TextBlock> palavras = detections.getDetectedItems();
                                if(palavras.size() !=0){

                                    StringBuilder s = new StringBuilder();

                                    for(int i=0;i<palavras.size();i++){

                                        TextBlock p = palavras.valueAt(i);
                                        s.append(p.getValue());
                                        s.append("\n");
                                    }
                                    texto=s.toString();
                                    Toast.makeText(getBaseContext(),texto,Toast.LENGTH_LONG).show();

                                }
                            }
                        });

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    capturar.stop();
                }
            });

        }
    }

    protected void GerarCameraSoruce(TextRecognizer pegarTexto, int altura,int largura,float frame,boolean foco)
    {
        capturar = new CameraSource.Builder(getApplicationContext(), pegarTexto)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(altura,largura)
                .setRequestedFps(frame)
                .setAutoFocusEnabled(foco)
                .build();
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

                if( texto==null){
                    texto= "Texto não capturado";
                }else {
                    Intent telaCam = new Intent(TelaCamera.this, telaCapturar.class);
                    telaCam.putExtra("telaCapturar", texto);
                    startActivity(telaCam);
                    break;
                }
            case R.id.id_ler:


                Toast.makeText(getBaseContext(),texto,Toast.LENGTH_LONG).show();

                lerTexto.getLer(texto);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

}
