package WingYan.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static int QuestionNo =  1 ;
    private static int correct = 0 ;
    private static int skip = 0 ;
    private static int wrong = 0 ;

    private static final long START_TIME_IN_MILLIS = 300000 ;
    private CountDownTimer countDownTimer ;
    private long timeLeftInMilliseconds = START_TIME_IN_MILLIS ;
    private long timeForUsed ;
    private String timeLeftFormatted ;

    private TextView QuestionNumber ;
    private TextView Question ;
    private TextView Score ;
    private TextView Timer ;
    private EditText answerByInput ;

    private int AnswerByInput ;
    private int CorrectAnswer ;
    private float pitchValue = 1.0f ;
    private float speedValue = 1.0f ;

    private Button Submit ;
    private Button Skip ;

    private TextView Result ;
    private Button OK ;
    private ImageView yes ;
    private ImageView no ;
    private ImageView empty ;

    private Button settingMain ;

    private TextToSpeech TTS ;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_main) ;

        QuestionNo =  1 ;
        correct = 0 ;
        skip = 0 ;
        wrong = 0 ;

        startTimer() ;

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

                        Submit.setEnabled(true) ;

                    }
                }

                else {

                    Log.e("TTS", "Initialization failed") ;

                }

            }

        });

        QuestionNumber = (TextView)findViewById(R.id.QuestionNumber) ;
        Question = (TextView)findViewById(R.id.Question) ;
        answerByInput = (EditText)findViewById(R.id.anwserByInput) ;
        Score = (TextView)findViewById(R.id.Score) ;
        Timer = (TextView)findViewById(R.id.Timer) ;

        QuestionNumber.setText( "Question " + QuestionNo++ ) ;

        Submit = (Button)findViewById(R.id.Submit) ;
        Skip = (Button)findViewById(R.id.Skip) ;

        Result = (TextView)findViewById(R.id.Result) ;
        OK = (Button)findViewById(R.id.OK) ;
        yes = (ImageView)findViewById(R.id.yes) ;
        no = (ImageView)findViewById(R.id.no) ;
        empty = (ImageView)findViewById(R.id.empty) ;


        Result.setVisibility(View.INVISIBLE) ;
        OK.setVisibility(View.INVISIBLE) ;
        yes.setVisibility(View.INVISIBLE) ;
        no.setVisibility(View.INVISIBLE) ;
        empty.setVisibility(View.INVISIBLE) ;

        settingMain =(Button)findViewById(R.id.settingMain) ;

        Random ran = new Random() ;
        int M = ran.nextInt(4) + 1 ;

        switch (M) {

            case 1:

                int x1 = (ran.nextInt(98) + 1) ;
                int y1 = (ran.nextInt(98) + 1) ;

                int sum = x1 + y1 ;
                CorrectAnswer = sum ;

                Question.setText(x1 + " + " + y1) ;

                break ;

            case 2:

                int x2 ;
                int y2 ;

                do {

                    x2 = (ran.nextInt(98) + 1) ;
                    y2 = (ran.nextInt(98) + 1) ;

                } while (y2 < x2) ;

                int sub = y2 - x2 ;
                CorrectAnswer = sub ;

                Question.setText(y2 + " - " + x2) ;

                break ;

            case 3:

                int x3 = (ran.nextInt(19) + 1) ;
                int y3 = (ran.nextInt(19) + 1) ;

                int mul = x3 * y3 ;
                CorrectAnswer = mul ;

                Question.setText(x3 + " × " + y3) ;

                break ;

            case 4:

                int x4 ;
                int y4 ;

                do{

                    x4 = (ran.nextInt(98) + 1) ;
                    y4 = (ran.nextInt(98 - x4 + 1) + 1) ;

                } while (x4 % y4 != 0) ;

                int div = x4 / y4 ;
                CorrectAnswer = div ;

                Question.setText(x4 + " ÷ " + y4) ;

                break ;

        }

        settingMain.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                speakEmpty() ;
                openSetting() ;

            }

        });


        Submit.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                String checkIfempty = answerByInput.getText().toString() ;

                if (checkIfempty.isEmpty() || checkIfempty.length() == 0 ||
                        checkIfempty.equals("") || checkIfempty == null) {

                    Result.setVisibility(View.VISIBLE) ;
                    empty.setVisibility(View.VISIBLE) ;
                    OK.setVisibility(View.VISIBLE) ;
                    answerByInput.setVisibility(View.INVISIBLE) ;
                    Submit.setVisibility(View.INVISIBLE) ;
                    Skip.setVisibility(View.INVISIBLE) ;
                    Result.setText("You skipped this question.\nThe correct answer is " + CorrectAnswer + ".") ;
                    skip = ++skip ;
                    Score.setText("Score : " + correct) ;
                    speakSubmit() ; ;

                }

                else {

                    AnswerByInput = Integer.parseInt(checkIfempty) ;

                    if (AnswerByInput != CorrectAnswer) {

                        Result.setVisibility(View.VISIBLE) ;
                        no.setVisibility(View.VISIBLE) ;
                        OK.setVisibility(View.VISIBLE) ;
                        answerByInput.setVisibility(View.INVISIBLE) ;
                        Submit.setVisibility(View.INVISIBLE) ;
                        Skip.setVisibility(View.INVISIBLE) ;
                        Result.setText("You are wrong.\nThe correct answer is " + CorrectAnswer + ".") ;
                        wrong = ++wrong ;
                        Score.setText("Score : " + correct) ;
                        speakSubmit() ;

                    }

                    else {

                        Result.setVisibility(View.VISIBLE) ;
                        yes.setVisibility(View.VISIBLE) ;
                        OK.setVisibility(View.VISIBLE) ;
                        answerByInput.setVisibility(View.INVISIBLE) ;
                        Submit.setVisibility(View.INVISIBLE) ;
                        Skip.setVisibility(View.INVISIBLE) ;
                        Result.setText("You are correct.\n" + CorrectAnswer) ;
                        correct = ++correct ;
                        Score.setText("Score : " + correct) ;
                        speakSubmit(); ;

                    }

                }

            }

        });

        Skip.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Result.setVisibility(View.VISIBLE) ;
                empty.setVisibility(View.VISIBLE) ;
                OK.setVisibility(View.VISIBLE) ;
                answerByInput.setVisibility(View.INVISIBLE) ;
                Submit.setVisibility(View.INVISIBLE) ;
                Skip.setVisibility(View.INVISIBLE) ;
                Result.setText("You skipped this question.\nThe correct answer is " + CorrectAnswer + ".") ;
                skip = ++skip ;
                Score.setText("Score : " + correct) ;
                speakSubmit() ;

            }

        });

        OK.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                QuestionNumber.setText("Question " + QuestionNo++);

                answerByInput.setVisibility(View.VISIBLE) ;
                Submit.setVisibility(View.VISIBLE) ;
                Skip.setVisibility(View.VISIBLE) ;
                Result.setVisibility(View.INVISIBLE) ;
                OK.setVisibility(View.INVISIBLE) ;
                yes.setVisibility(View.INVISIBLE) ;
                no.setVisibility(View.INVISIBLE) ;
                empty.setVisibility(View.INVISIBLE) ;

                answerByInput.setText("") ;
                speakEmpty() ;

                if (QuestionNo > 11) {

                    Score.setVisibility(View.INVISIBLE) ;
                    QuestionNumber.setVisibility(View.INVISIBLE) ;
                    Question.setVisibility(View.INVISIBLE) ;
                    answerByInput.setVisibility(View.INVISIBLE) ;
                    Submit.setVisibility(View.INVISIBLE) ;
                    Skip.setVisibility(View.INVISIBLE) ;
                    timeForUsed = START_TIME_IN_MILLIS - timeLeftInMilliseconds ;
                    openFinal() ;

                }

                else {

                    if (QuestionNo == 11) {

                        Result.setVisibility(View.VISIBLE) ;
                        Result.setText("This is the last question.") ;
                        speakSubmit();

                    }

                    Random ran = new Random() ;
                    int M = ran.nextInt(4) + 1 ;

                    switch (M) {

                        case 1 :

                            int x1 = (ran.nextInt(98) + 1) ;
                            int y1 = (ran.nextInt(98) + 1) ;

                            int sum = x1 + y1 ;
                            CorrectAnswer = sum ;

                            Question.setText(x1 + " + " + y1) ;

                            break ;

                        case 2 :

                            int x2 ;
                            int y2 ;

                            do {

                                x2 = (ran.nextInt(98) + 1) ;
                                y2 = (ran.nextInt(98) + 1) ;

                            } while (y2 < x2) ;

                            int sub = y2 - x2 ;
                            CorrectAnswer = sub ;

                            Question.setText(y2 + " - " + x2) ;

                            break ;

                        case 3 :

                            int x3 = (ran.nextInt(19) + 1) ;
                            int y3 = (ran.nextInt(19) + 1) ;

                            int mul = x3 * y3 ;
                            CorrectAnswer = mul ;

                            Question.setText(x3 + " × " + y3) ;

                            break ;

                        case 4:

                            int x4 ;
                            int y4 ;

                            do {

                                x4 = (ran.nextInt(98) + 1) ;
                                y4 = (ran.nextInt(98 - x4 + 1) + 1) ;

                            } while (x4 % y4 != 0) ;

                            int div = x4 / y4 ;
                            CorrectAnswer = div ;

                            Question.setText(x4 + " ÷ " + y4) ;

                            break ;

                    }


                }

            }

        });





    }

    public void openFinal() {

        pauseTimer() ;
        Intent intent = new Intent(this , Final.class) ;
        intent.putExtra("Score" , correct) ;
        intent.putExtra("Skip" , skip) ;
        intent.putExtra("Wrong" , wrong) ;
        intent.putExtra("timeUsed", timeForUsed) ;
        intent.putExtra("QuestionNO" , QuestionNo) ;
        intent.putExtra("pitchValue", pitchValue) ;
        intent.putExtra("speedValue", speedValue) ;
        startActivity(intent) ;
        QuestionNo = 1 ;
        correct = 0 ;
        wrong = 0 ;
        skip = 0 ;
        timeLeftInMilliseconds = START_TIME_IN_MILLIS ;
        finish() ;


    }

    public void openEndOfTime() {

        Intent intent = new Intent(this , EndOfTime.class) ;
        intent.putExtra("Score" , correct) ;
        intent.putExtra("Skip" , skip) ;
        intent.putExtra("Wrong" , wrong) ;
        intent.putExtra("timeUsed" , START_TIME_IN_MILLIS) ;
        intent.putExtra("QuestionNO" , QuestionNo) ;
        intent.putExtra("pitchValue", pitchValue) ;
        intent.putExtra("speedValue", speedValue) ;
        startActivity(intent) ;
        QuestionNo = 1 ;
        correct = 0 ;
        wrong = 0 ;
        skip = 0 ;
        timeLeftInMilliseconds = START_TIME_IN_MILLIS ;
        finish() ;

    }
    public void openSetting() {

        Intent intent = new Intent(this , Setting.class) ;
        intent.putExtra("timeLeft", timeLeftInMilliseconds) ;
        pauseTimer();
        startActivity(intent) ;

    }




    private void startTimer() {

        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {

            @Override

            public void onTick(long millisUntilFinished) {

                timeLeftInMilliseconds = millisUntilFinished ;
                updateCountDownText() ;

            }

            @Override

            public void onFinish() {

                String checkIfempty = answerByInput.getText().toString() ;

                if (checkIfempty.isEmpty() || checkIfempty.length() == 0 ||
                        checkIfempty.equals("") || checkIfempty == null) {

                    skip = ++skip ;

                }

                openEndOfTime() ;

            }

        }.start() ;

    }

    private void updateCountDownText() {

        int minutes = (int) (timeLeftInMilliseconds / 1000) / 60 ;
        int seconds = (int) (timeLeftInMilliseconds / 1000) % 60 ;

        timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds) ;

        Timer.setText(timeLeftFormatted) ;

    }

    private void speakSubmit() {

        String text = Result.getText().toString() ;

        if (pitchValue < 0.1)
            pitchValue = 0.1f ;
        if (speedValue < 0.1)
            speedValue = 0.1f ;

        TTS.setPitch(Setting.pitchValue) ;
        TTS.setSpeechRate(Setting.speedValue) ;

        TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null) ;

    }

    private void speakEmpty() {
        String text="";
        TTS.setPitch(Setting.pitchValue) ;
        TTS.setSpeechRate(Setting.speedValue) ;

        TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null) ;

    }

    private void pauseTimer() {

        countDownTimer.cancel() ;

    }

    @Override
    protected void onRestart(){

        super.onRestart() ;

        startTimer() ;
    }

    @Override
    protected void onPause(){

        super.onPause(); ;

        finish();
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
