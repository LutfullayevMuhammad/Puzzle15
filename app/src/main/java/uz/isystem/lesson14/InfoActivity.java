package uz.isystem.lesson14;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView numberBtn, instaBtn, tgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        numberBtn = findViewById(R.id.number_button);
        instaBtn = findViewById(R.id.insta_button);
        tgBtn = findViewById(R.id.tg_button);

        numberBtn.setOnClickListener(this);
        instaBtn.setOnClickListener(this);
        tgBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.number_button: {

                Toast.makeText(this, "Contact tanlandi", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_DIAL);

                Uri uri = Uri.parse("tel:+998994885481");

                intent.setData(uri);

                startActivity(intent);

                break;

            }
            case R.id.insta_button: {
                Intent instagram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/l_muhammad_14/"));

                startActivity(instagram);

                break;

            }
            case R.id.tg_button:{
                Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse("https://t.me/m_lutfullayev"));
                startActivity(telegram);

                break;
            }
        }
    }
}