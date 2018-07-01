package alex.alves.smartscanner;

import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class telaCapturar extends AppCompatActivity {

    TextToSpeech lerTexto;
    EditText campo;
    Intent tela;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_capturar);

        campo = (EditText)findViewById(R.id.editText2) ;

        tela = getIntent();
        campo.setText(tela.getStringExtra("telaCapturar"));

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        // Pega o inflater já existente
        MenuInflater criarMenu = getMenuInflater();
        // Cria menu a partir da leitura de um xml
        criarMenu.inflate(R.menu.meu_menu2,menu);

        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Implementar ação ao clicar no item do menu

        switch(item.getItemId()){
            case R.id.id_compartilhar:

                break;
            case R.id.id_ler:
                //lerTexto.speak(" VocÊ clicou no botão capturar ", TextToSpeech.QUEUE_ADD, null, "DEFAULT");
                String s =getTexto();
                // Toast.makeText(getBaseContext(),s,Toast.LENGTH_LONG).show();

                //lerTexto.speak(" VocÊ clicou no botão capturar ", TextToSpeech.QUEUE_ADD, null, "DEFAULT");
                lerTexto.speak(s, TextToSpeech.QUEUE_ADD, null, "DEFAULT");
                break;

        }


        return super.onOptionsItemSelected(item);
    }


    public String getTexto(){
        return String.valueOf(campo.getText());

    }
}
