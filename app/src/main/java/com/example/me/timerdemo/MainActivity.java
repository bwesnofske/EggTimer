package com.example.me.timerdemo;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
Button goButton;
CountDownTimer countDownTimer;


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
        countDownTimer = new CountDownTimer((curTime*1000), 1000) {


            @Override
            public void onTick(long l) {

                Log.i("Seconds Left", String.valueOf(l/1000));
                updateTimer(l/1000);

            }

            @Override
            public void onFinish() {
                // add sound
                MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                mplayer.start();
                goButton.setText("Go!");
                seekBar.setEnabled(true);
                seekBar.setProgress(0);
                Log.i("Done", "Count Down Timer Done");
            }
        }.start();

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
        goButton = (Button)findViewById(R.id.goButton);
        //goButton.setText("TEST!");

        if(timerActive == false){
            timerActive = true;
            seekBar.setEnabled(false);
            goButton.setText("Stop!");
            startTimer(currentTime);

        } else {
            timerActive = false;
            seekBar.setEnabled(true);
            goButton.setText("Go!");
            countDownTimer.cancel();
            seekBar.setProgress((int)currentTime);
        }




    }
}




    /*
        new CountDownTimer(10000, 1000) {


            @Override
            public void onTick(long l) {
                Log.i("Seconds Left", String.valueOf(l/1000));
            }

            @Override
            public void onFinish() {
                Log.i("Done", "Count Down Timer Done");
            }
        }.start();

        */

