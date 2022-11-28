package uz.isystem.lesson14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1 , button2 , button3 ,button4;
    private ImageButton infoBtn;

    private Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        loadView();

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        infoBtn.setOnClickListener(this);

    }

    private void loadView() {
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        infoBtn = findViewById(R.id.info_button);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.button1:{
                Toast.makeText(this, "3x3 tanlandi", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(StartActivity.this , MainActivity3_3.class);

                bundle.putInt("testType" , 1);

                intent.putExtras(bundle);


                startActivity(intent);
//                Animatoo.animateZoom(this);

                finish();

                break;
            }case R.id.button2:{
                Toast.makeText(this, "4x4 tanlandi", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(StartActivity.this , MainActivity4_4.class);

                bundle.putInt("testType" , 2);

                intent.putExtras(bundle);

                startActivity(intent);
//                Animatoo.animateZoom(this);

                finish();

                break;
            }case R.id.button3:{
                Toast.makeText(this, "5x5 tanlandi", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(StartActivity.this , MainActivity5_5.class);

                bundle.putInt("testType" , 3);

                intent.putExtras(bundle);


                startActivity(intent);
//                Animatoo.animateZoom(this);

                finish();

                break;
            }case R.id.button4:{
                Toast.makeText(this, "6x6 tanlandi", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(StartActivity.this , MainActivity6_6.class);

                bundle.putInt("testType" , 4);

                intent.putExtras(bundle);


                startActivity(intent);
//                Animatoo.animateZoom(this);

                finish();

                break;
            }case R.id.info_button:{
                Toast.makeText(this, "Info tanlandi", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(StartActivity.this , InfoActivity.class);

                bundle.putInt("testType" , 5);

                intent.putExtras(bundle);


                startActivity(intent);
//                Animatoo.animateZoom(this);

                break;
            }
        }
    }
}