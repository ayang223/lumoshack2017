package com.example.clai.lumos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Button;
import android.widget.Toast;
import android.view.*;

public class Timer extends BroadcastReceiver
{
    static boolean isRinging;
    static boolean running;
    Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    static Ringtone ringtone;


    @Override
    public void onReceive(Context context, Intent intent)
    {


        running = true;
        if (isRinging) {
            Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();
//        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
            ringtone = RingtoneManager.getRingtone(context, alarmUri);
            ringtone.play();
        }
    }

    public static void play() {
        ringtone.play();
    }

    public static void stop() {
        ringtone.stop();
        isRinging = false;
    }
}