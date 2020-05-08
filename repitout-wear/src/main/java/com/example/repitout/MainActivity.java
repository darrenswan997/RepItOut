package com.example.repitout;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.localbroadcastmanager.content.LocalBroadcastManager;



public class MainActivity extends WearableActivity {

   Button counter_Btn;
   TextView repNum, exerciseName;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counter_Btn = findViewById(R.id.rep_counter);
        repNum = findViewById(R.id.repsFromPhone);
        counter_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,counter.class));
            }
        });

        IntentFilter newFilter = new IntentFilter(Intent.ACTION_SEND);
        Receiver messageReceiver = new Receiver();

        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, newFilter);

    }

    public class Receiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //get intent data from bundle
            Bundle bundle = intent.getExtras();
            String data = bundle.getString("reps");
            repNum.setText(data);

        }
    }




}

