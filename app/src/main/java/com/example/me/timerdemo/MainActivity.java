package com.example.me.timerdemo;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import org.w3c.dom.Text;

/*
Seekbar will adjust currentTime Variable
Seekbar will also update timerView
goButton will start timer countdown and disable seekbar
when timer reaches 0 play audio file

 */

public class MainActivity extends AppCompatActivity {


    public int seconds = 0;
    public int minutes = 0;
    public long currentTime = 0;
    public TextView timerView;
    public SeekBar seekBar;
    boolean timerActive = false;
    boolean alarmActive = false;
    MediaPlayer mplayer;
    TextView textView;
    ImageView imageView;
    CountDownTimer countDownTimer;
    Animation shake;
    //View shakeView = findViewById(R.id.imageView);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerView = (TextView) findViewById(R.id.timerView);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(600);


        updateTimer(currentTime);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(seekBar.getProgress());//update timerView

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void startTimer(long curTime) {
        countDownTimer = new CountDownTimer((curTime * 1000), 1000) {


            @Override
            public void onTick(long l) {

                Log.i("Seconds Left", String.valueOf(l / 1000));
                updateTimer(l / 1000);

            }

            @Override
            public void onFinish() {
                alarmActive = true;
                timerUp();
                seekBar.setProgress(0);
            }
        }.start();

    }

    public void timerUp() {
        textView.setText("Tap to Stop Alarm!");
        mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
        mplayer.start();
        mplayer.setLooping(true);

        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        findViewById(R.id.imageView).startAnimation(shake);
    }


    public void updateTimer(long curTime) {

        int min = (int)(curTime/60);
        int seconds = (int)(curTime - min*60);
        String secondString = Integer.toString(seconds);

        if(seconds <= 9) {
            secondString = ("0" + seconds);
        }


        timerView.setText(Integer.toString(min) + ":" + secondString);
        currentTime = curTime;

    }

    public void startTime(View view) {
        textView = (TextView)findViewById(R.id.textView);

        if(alarmActive){
            findViewById(R.id.imageView).clearAnimation();
            mplayer.setLooping(false);
            timerActive = false;
            seekBar.setEnabled(true);
            textView.setText("Tap to Start!");
            seekBar.setProgress((int) currentTime);
            alarmActive = false;

        } else {

            if (timerActive == false) {
                timerActive = true;
                seekBar.setEnabled(false);
                textView.setText("Tap to Stop...");
                startTimer(currentTime);

            } else {
                timerActive = false;
                seekBar.setEnabled(true);
                textView.setText("Tap to Start!");
                countDownTimer.cancel();
                seekBar.setProgress((int) currentTime);
            }

        }
    }
}


