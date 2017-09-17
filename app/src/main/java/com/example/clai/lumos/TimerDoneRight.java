package com.example.clai.lumos;

import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

import static com.example.clai.lumos.R.id.textClock;

public class TimerDoneRight extends AppCompatActivity {

    Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    Ringtone ringtone;

    long time;
    boolean rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_done_right);

        Button stopButton = (Button) findViewById(R.id.button);
        ringtone = RingtoneManager.getRingtone(this, alarmUri);


        stopButton.setOnClickListener(new View.OnClickListener() {//button to stop
            public void onClick(View v) {
                stop();
                if (rest) {
                    double time = System.currentTimeMillis();
                    time = (time / 3600000) + 0.25;
                    time *= 3600000;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            ringtone.play();
                        }
                    }, (long) time - System.currentTimeMillis());
                }
            }
        });

    }
    public void OnToggleClicked(View view) {

        if (((ToggleButton) view).isChecked()) {
            Toast.makeText(TimerDoneRight.this, "ALARM ON", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();
            int currentHour = getAlarm();
            if (ringtone != null) {
                long time = System.currentTimeMillis();
                time = (time / 3600000) + 1;
                time *= 3600000;
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        ringtone.play();
                    }
                }, time - System.currentTimeMillis());
                rest = true;
//                Toast.makeText(TimerDoneRight.this, "ALARM ON", Toast.LENGTH_SHORT).show();
//                ringtone.play();
//                while (true) {
//                    if (System.currentTimeMillis() > time) {
//                        ringtone.play();
//                        return;
//                    }
//                }
            } else {
                Toast.makeText(TimerDoneRight.this, "NULL", Toast.LENGTH_SHORT).show();

            }
        }
    }


    public int getAlarm() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MINUTE);
    }

    public void play() {
        ringtone.play();
    }

    public void stop() {
        ringtone.stop();
    }
}