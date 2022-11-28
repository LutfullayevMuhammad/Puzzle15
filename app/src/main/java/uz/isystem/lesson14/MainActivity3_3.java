package uz.isystem.lesson14;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity3_3 extends AppCompatActivity implements View.OnClickListener {

    private final Button[][] buttons = new Button[3][3];
    private final ArrayList<Integer> numbers = new ArrayList<>();
    MediaPlayer mediaPlayer = null;
    private int emptyX = 2;
    private int emptyY = 2;
    private RelativeLayout buttonGroup;
    private TextView stepView;
    private TextView timeView;
    private ImageButton soundButton;
    private ImageButton gameStateButton;
    private ImageButton backBtn;

    private FrameLayout pauseView;

    private ConstraintLayout continueBtn,gameGroup;

    private int step = 0;
    private int time = 0;
    private Timer timer;
    private boolean isSoundOn = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_3_3);

        loadViews();

        loadNumbers();

        loadDataToView();

        loadAction();

        startTimer();

    }

    private void startTimer() {

        timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        setCountText();
                    }
                });
            }
        };

        timer.schedule(timerTask, 1000, 1000);

    }

    private void loadAction() {

        gameStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gameGroup.setVisibility(View.GONE);

                pauseView.setVisibility(View.VISIBLE);

                timer.cancel();

            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gameGroup.setVisibility(View.VISIBLE);

                pauseView.setVisibility(View.GONE);

                startTimer();

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity3_3.this, StartActivity.class);

                startActivity(intent);

                finish();

            }
        });

        for (int i = 0; i < buttonGroup.getChildCount(); i++) {

            int x = i / 3;
            int y = i % 3;

            buttons[x][y].setOnClickListener(this::onClick);

        }


        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                isSoundOn = !isSoundOn;

                if (isSoundOn) {
                    soundButton.setImageResource(R.drawable.volume_on);
                } else {
                    soundButton.setImageResource(R.drawable.volume_off);
                }

            }
        });

    }

    private void loadDataToView() {
        for (int i = 0; i < buttonGroup.getChildCount(); i++) {

            int x = i / 3;
            int y = i % 3;

            if (x == emptyX && y == emptyY) {
                buttons[x][y].setText("");
                buttons[x][y].setBackgroundResource(R.drawable.empty_button);
            } else {
                buttons[x][y].setText(String.valueOf(numbers.get(i)));
                buttons[x][y].setBackgroundResource(R.drawable.oyin_doskasi);

            }

        }
    }

    private void loadNumbers() {

        for (int i = 1; i < 9; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        while (!isResolvable(numbers)) {
            Collections.shuffle(numbers);
        }

    }

    private void loadViews() {

        gameGroup = findViewById(R.id.game_group);

        continueBtn = findViewById(R.id.continueBtn);

        soundButton = findViewById(R.id.sound_btn);
        gameStateButton = findViewById(R.id.game_state_btn);

        pauseView = findViewById(R.id.pause_view);
        backBtn = findViewById(R.id.back_button);


        stepView = findViewById(R.id.step_count);
        timeView = findViewById(R.id.time_count);

        buttonGroup = findViewById(R.id.button_group);


        for (int i = 0; i < buttonGroup.getChildCount(); i++) {
            int x = i / 3;
            int y = i % 3;
            buttons[x][y] = (Button) buttonGroup.getChildAt(i);
        }


    }

    @Override
    public void onClick(View view) {

        Button button = (Button) view;

        String tag = button.getTag().toString();// 0:1 //48 49

        int x = tag.charAt(0) - 48; // '0' > 48
        int y = tag.charAt(2) - 48;

        int result = Math.abs(x - emptyX) + Math.abs(y - emptyY);

        if (result == 1) {

            if (isSoundOn) {
                makeSound();
            }
            step++;

            setCountText();

//            Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
//            buttons[emptyX][emptyY].startAnimation(animation);

            String pressedText = button.getText().toString();

            buttons[emptyX][emptyY].setText(pressedText);
            buttons[emptyX][emptyY].setBackgroundResource(R.drawable.oyin_doskasi);

            button.setText("");
            button.setBackgroundResource(R.drawable.empty_button);

            emptyX = x;
            emptyY = y;

            if (emptyX == 2 && emptyY == 2) {
                checkWin();
            }

        }


    }

    private void setCountText() {

        stepView.setText(String.valueOf(step));

        int second = time % 60;
        int minute = time / 60 % 60;

        String timeFormat = String.format("%02d:%02d", minute, second);

        timeView.setText(timeFormat);

    }

    private void checkWin() {

        boolean isWin = false;

        for (int i = 1; i < 8; i++) {
            int orderOld = Integer.parseInt(buttons[(i - 1) / 3][(i - 1) % 3].getText().toString());
            int order = Integer.parseInt(buttons[i / 3][i % 3].getText().toString());
            if (orderOld < order) {
                isWin = true;
            } else {
                isWin = false;
                break;
            }
        }

        if (isWin) {

            timer.cancel();

            Toast.makeText(this, "Win", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity3_3.this);

            builder.setTitle("Finish!");
            builder.setMessage("You won\uD83E\uDD73");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent intent = new Intent(MainActivity3_3.this, StartActivity.class);

                    startActivity(intent);

                    finish();

                }
            });
            builder.setNegativeButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent intent = new Intent(MainActivity3_3.this, MainActivity3_3.class);

                    startActivity(intent);

                    finish();

                }
            });

            builder.show();

        } else {

//            Toast.makeText(this, "Lose", Toast.LENGTH_SHORT).show();
        }

    }

    private void makeSound() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.click_sound3);
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.click_sound3);
        }
        mediaPlayer.start();
    }

    private boolean isResolvable(ArrayList<Integer> list) {
        int count = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) > list.get(j)) {
                    count++;
                }
            }
        }
        if (count % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(MainActivity3_3.this,StartActivity.class);

        startActivity(intent);

        finish();

    }
}
