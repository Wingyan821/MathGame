package WingYan.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class Final extends AppCompatActivity {

    private TextView Final ;
    private TextView resultFinal ;
    private ImageView excellent ;
    private ImageView good ;
    private ImageView workHard ;
    private Button Restart ;

    private int questionNoFinal ;
    private int correctFinal ;
    private int wrongFinal ;
    private int skipFinal ;
    private long timerForUsedFinal ;
    private String textToSpeak;
    private float pitchValue;
    private float speedValue;

    private TextToSpeech TTS ;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        Final = (TextView)findViewById(R.id.Final) ;
        resultFinal = (TextView)findViewById(R.id.resultFinal) ;
        excellent = (ImageView)findViewById(R.id.excellent) ;
        good = (ImageView)findViewById(R.id.good) ;
        workHard = (ImageView)findViewById(R.id.workHard) ;
        Restart = (Button)findViewById(R.id.Restart) ;

        excellent.setVisibility(View.INVISIBLE) ;
        good.setVisibility(View.INVISIBLE) ;
        workHard.setVisibility(View.INVISIBLE) ;

        TTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            @Override

            public void onInit ( int status ) {

                if (status == TextToSpeech.SUCCESS) {

                    int result = TTS.setLanguage(Locale.ENGLISH) ;

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                        Log.e("TTS", "Language not supported") ;

                    }

                    else {

                        speak() ;
                    }
                }

                else {

                    Log.e("TTS", "Initialization failed") ;

                }

            }

        });

        Intent intent = this.getIntent() ;
        correctFinal = intent.getIntExtra("Score", 0) ;
        skipFinal = intent.getIntExtra("Skip", 0) ;
        wrongFinal = intent.getIntExtra("Wrong", 0) ;
        timerForUsedFinal = intent.getLongExtra("timeUsed",0) ;
        questionNoFinal = intent.getIntExtra("QuestionNO", 0) ;
        pitchValue = intent.getFloatExtra("pitchValue", 1.0f) ;
        speedValue = intent.getFloatExtra("speedValue", 1.0f) ;


        int minutes = (int) (timerForUsedFinal / 1000) / 60 ;
        int seconds = (int) (timerForUsedFinal / 1000) % 60 ;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds) ;

        questionNoFinal = questionNoFinal - 2 ;

        resultFinal.setText("Score : " + correctFinal + " / " + questionNoFinal +
                "\nSkip : " + skipFinal + " / " + questionNoFinal +
                "\nWrong : " + wrongFinal + " / " + questionNoFinal +
                "\nusing time : " + timeLeftFormatted) ;

        textToSpeak = "Score : " + correctFinal + " / " + questionNoFinal +
                "\nSkip : " + skipFinal + " / " + questionNoFinal +
                "\nWrong : " + wrongFinal + " / " + questionNoFinal +
                "\nusing time : " + minutes + " minutes " + seconds + " seconds." ;

        speak() ;

        if (correctFinal >= 9) {

            excellent.setVisibility(View.VISIBLE) ;

        }
        else if (correctFinal <= 8 && correctFinal >= 6) {

            good.setVisibility(View.VISIBLE) ;

        }
        else {

            workHard.setVisibility(View.VISIBLE) ;

        }

        Restart.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                speakEmpty() ;
                openMainActivity() ;

            }

        });

    }

    public void openMainActivity() {

        Intent intent = new Intent(this , MainActivity.class) ;
        startActivity(intent) ;

    }

    private void speak() {

        TTS.setPitch(Setting.pitchValue);
        TTS.setSpeechRate(Setting.speedValue);
        TTS.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null) ;

    }

    private void speakEmpty() {
        String text="";
        TTS.setPitch(Setting.pitchValue) ;
        TTS.setSpeechRate(Setting.speedValue) ;

        TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null) ;

    }

    @Override

    protected void onDestroy() {

        if (TTS != null) {

            TTS.stop() ;
            TTS.shutdown() ;

        }

        super.onDestroy() ;

    }






}
