package alex.alves.smartscanner;

import android.app.Activity;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class Ler {
    TextToSpeech ler;
    public TextToSpeech initLerTexto(Activity atividade)
    {
        // Preparando para receber os textos
        TextToSpeech.OnInitListener ouvir =
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(final int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            Log.d("OnInitListener", "Processo de leitura carregado corretamente.");
                            ler.setLanguage(Locale.getDefault());
                        } else {
                            Log.d("OnInitListener", "Erro ao carregar a voz");
                        }
                    }
                };
        ler = new TextToSpeech(atividade.getApplicationContext(), ouvir);
        return ler;
    }

    public void getLer(String texto){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ler.speak(texto,TextToSpeech.QUEUE_FLUSH,null,null);
        } else {
            ler.speak(texto, TextToSpeech.QUEUE_FLUSH, null);
        }
      //  ler.speak(texto, TextToSpeech.QUEUE_ADD, null, "DEFAULT");
    }

}
