package WingYan.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    private TextView Welcome_t ;
    private TextView Welcome_m ;
    private TextView Welcome_l ;

    private Button Start ;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_welcome) ;

        Welcome_t = (TextView)findViewById(R.id.Welcome_t) ;
        Welcome_m = (TextView)findViewById(R.id.Welcome_m) ;
        Welcome_l = (TextView)findViewById(R.id.Welcome_l) ;

        Start = (Button)findViewById((R.id.Start)) ;


        Welcome_m.setText("10 questions in 5 minutes\nIncluding 4 mathematics operation") ;

        Welcome_l.setText("-Addition\n-Subtraction\n-Multiplication\n-Division") ;

        Start.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                openMainActivity() ;

            }

        });

    }

    public void openMainActivity() {

        Intent intent = new Intent(this , MainActivity.class) ;
        startActivity(intent) ;
        finish();

    }

}
