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


public class EndOfTime extends AppCompatActivity {

    private TextView endOfTime ;
    private TextView resultET ;
    private ImageView excellent ;
    private ImageView good ;
    private ImageView workHard ;
    private Button Restart ;

    private int questionNoET ;
    private int correctET ;
    private int wrongET ;
    private int skipET ;
    private long timerForUsedET ;
    private String textToSpeak;
    private float pitchValue;
    private float speedValue;

    private TextToSpeech TTS ;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_time);

        endOfTime = (TextView)findViewById(R.id.endOfTime) ;
        resultET = (TextView)findViewById(R.id.resultET) ;
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
        correctET = intent.getIntExtra("Score" , 0 ) ;
        skipET = intent.getIntExtra("Skip" , 0 ) ;
        wrongET = intent.getIntExtra("Wrong" , 0 ) ;
        timerForUsedET = intent.getLongExtra("timeUsed",0);
        questionNoET = intent.getIntExtra("QuestionNO" , 0 );
        pitchValue = intent.getFloatExtra("pitchValue", 1.0f) ;
        speedValue = intent.getFloatExtra("speedValue", 1.0f);

        int minutes = (int) (timerForUsedET / 1000) / 60 ;
        int seconds = (int) (timerForUsedET / 1000) % 60 ;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds) ;

        --questionNoET ;

        resultET.setText("Score : " + correctET + " / " + questionNoET +
                "\nSkip : " + skipET + " / " + questionNoET +
                "\nWrong : " + wrongET + " / " + questionNoET +
                "\nusing time : " + timeLeftFormatted ) ;

        textToSpeak = "Score : " + correctET + " / " + questionNoET +
                "\nSkip : " + skipET + " / " + questionNoET +
                "\nWrong : " + wrongET + " / " + questionNoET +
                "\nusing time : " + minutes + " minutes " + seconds + " seconds." ;


        speak() ;

        if (correctET >= 9) {

            excellent.setVisibility(View.VISIBLE) ;

        }
        else if (correctET <= 8 && correctET >= 6) {

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

        String text="" ;

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



