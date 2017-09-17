package com.example.clai.lumos;

//import android.app.Activity;
//import android.os.Bundle;
//
//public class MainActivity extends Activity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//}

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    TimePicker alarmTimePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    long time;

    public int getAlarm(){
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MINUTE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


    }
    public void OnToggleClicked(final View view)
    {
        final Intent intent2 = new Intent(this, Timer.class);
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone = RingtoneManager.getRingtone(this, alarmUri);


        if (((ToggleButton) view).isChecked())
        {
            Timer.isRinging = true;
            Toast.makeText(MainActivity.this, "ALARM ON", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();
            Intent intent = new Intent(this, Timer.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            int currentHour = getAlarm();
            time = (currentHour + 1) * 60000;
            if(System.currentTimeMillis()>time)
            {
                if (calendar.AM_PM == 0)
                    time = time + (1000*60*60*12);
                else
                    time = time + (1000*60*60*24);
            }

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
            Button stopButton = (Button) findViewById(R.id.button);

            stopButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Timer.stop();
                    ((ToggleButton) view).setChecked(false);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1253, intent2, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                }
            });

        }
        else
        {
            Timer.stop();
            alarmManager.cancel(pendingIntent);
            Toast.makeText(MainActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
        }
    }
}