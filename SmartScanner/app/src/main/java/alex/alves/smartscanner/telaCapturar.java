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

    Ler lerTexto;
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
        lerTexto = new Ler();
        lerTexto.initLerTexto(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        // Pega o inflater já existente
        MenuInflater criarMenu = getMenuInflater();
        // Cria menu a partir da leitura de um xml
        criarMenu.inflate(R.menu.meu_menu2,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Implementar ação ao clicar no item do menu

        switch(item.getItemId()){
            case R.id.id_compartilhar:

                break;
            case R.id.id_ler:
                String s =getTexto();
                lerTexto.getLer(s);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public String getTexto(){
        return String.valueOf(campo.getText());

    }
}
