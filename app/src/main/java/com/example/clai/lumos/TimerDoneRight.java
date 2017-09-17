package com.example.clai.lumos;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import java.util.ArrayList;
import java.util.Arrays;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import java.util.Random;

import static com.example.clai.lumos.R.id.textClock;

import java.util.Iterator;

import static com.example.clai.lumos.R.id.textClock;
import static com.example.clai.lumos.R.id.textView;


public class TimerDoneRight extends AppCompatActivity {
    ArrayList<String> quotes = new ArrayList<String>(Arrays.asList("Focus on the Good","Grow through what you go through","Believe"));
    Random r = new Random();

    Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    TextView mTextView;
    Ringtone ringtone;

    private FirebaseUser user; //= FirebaseAuth.getInstance().getCurrentUser();
    String email;// = user.getEmail();
    String uid;// = user.getUid();
    JSONObject userdata;// = new JSONObject();
    JSONArray array;// = new JSONArray();
    JSONObject item;// = new JSONObject();
    DatabaseReference myData = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mChildRef = myData.child("users");

    long time;
    boolean rest;
    int num_depressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_done_right);

        user = FirebaseAuth.getInstance().getCurrentUser();

        myData.child("users").child(user.getUid()).child("user_email").setValue(user.getEmail());
        myData.child("users").child(user.getUid()).child("status").setValue("true");

        Button stopButton = (Button) findViewById(R.id.button);
        mTextView = (TextView)findViewById(R.id.textView);
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
            Toast.makeText(TimerDoneRight.this,"ALARM ON", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onStart() {
        super.onStart();

        mChildRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //User user = snapshot.getValue(User.class);
                    //System.out.println(user.mEmail);

                    String status = (String) snapshot.child("status").getValue();

                    if (status == "true") {
                        num_depressed++;
                    }
                }
                mTextView.setText("Number of Depressed People: " + num_depressed + " :)");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void onStop() {
        super.onStop();

        myData.child("users").child(user.getUid()).child("status").setValue("false");
    }

    public void play() {
        ringtone.play();
    }

    public void stop() {
        if (ringtone.isPlaying()){
            Toast.makeText(TimerDoneRight.this,quotes.get(r.nextInt(quotes.size())), Toast.LENGTH_LONG).show();
        }
        ringtone.stop();
    }

    public static class User {

        public String mEmail;
        public String mStatus;

        public User(){
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String email, String status) {
            this.mEmail = email;
            this.mStatus = status;
        }
    }
}
