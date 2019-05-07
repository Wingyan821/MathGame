package WingYan.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class Setting extends AppCompatActivity {

    private TextView pitch;
    private SeekBar seek_bar_pitch;
    private TextView speed;
    private SeekBar seek_bar_speed;
    private Button Finish;
    private TextView testingText;
    private Button testing;

    private TextView settingText;

    private long timeLeftInMilliseconds ;
    public static float pitchValue = 1.0f;
    public static float speedValue = 1.0f;

    private TextToSpeech TTS;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        TTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            @Override

            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {

                    int result = TTS.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                        Log.e("TTS", "Language not supported");

                    } else {

                        testing.setEnabled(true);

                    }

                } else {

                    Log.e("TTS", "Initialization failed");

                }

            }

        });

        Intent intent = this.getIntent() ;
        timeLeftInMilliseconds = intent.getLongExtra("timeLeft", 0);

        pitch = (TextView) findViewById(R.id.pitch);
        seek_bar_pitch = (SeekBar) findViewById(R.id.seek_bar_pitch);
        speed = (TextView) findViewById(R.id.speed);
        seek_bar_speed = (SeekBar) findViewById(R.id.seek_bar_speed);
        Finish = (Button) findViewById(R.id.Finish);
        testingText = (TextView) findViewById(R.id.testingText);
        testing = (Button) findViewById(R.id.testing);
        settingText = (TextView) findViewById(R.id.settingText);

        testingText.setVisibility(View.INVISIBLE);

        testing.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                speakTesting();

            }

        });

        Finish.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                pitchValue = (float) seek_bar_pitch.getProgress() / 50;
                if (pitchValue < 0.1) pitchValue = 0.1f;
                speedValue = (float) seek_bar_speed.getProgress() / 50;
                if (speedValue < 0.1) speedValue = 0.1f;

                speakEmpty();

                openMainActivity();

                finish();

            }

        });
    }

    @Override

    protected void onStart() {

        super.onStart();

        seek_bar_pitch.setProgress((int)(pitchValue*50));
        seek_bar_speed.setProgress((int)(speedValue*50));

    }


    private void speakTesting() {

        String text = testingText.getText().toString();

        pitchValue = (float) seek_bar_pitch.getProgress() / 50;
        if (pitchValue < 0.1) pitchValue = 0.1f;
        speedValue = (float) seek_bar_speed.getProgress() / 50;
        if (speedValue < 0.1) speedValue = 0.1f;

        TTS.setPitch(pitchValue);
        TTS.setSpeechRate(speedValue);

        TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);

    }

    @Override

    protected void onDestroy () {

        if (TTS != null) {

            TTS.stop();
            TTS.shutdown();

        }

        super.onDestroy();

    }

    public void openMainActivity() {

        Intent intent = new Intent(this , MainActivity.class) ;
        intent.putExtra("timeLeft", timeLeftInMilliseconds) ;
        intent.putExtra("pitchValue", pitchValue);
        intent.putExtra("speedValue", speedValue);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent) ;

    }

    private void speakEmpty() {

        String text="";
        TTS.setPitch(Setting.pitchValue) ;
        TTS.setSpeechRate(Setting.speedValue) ;

        TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null) ;

    }

    }



