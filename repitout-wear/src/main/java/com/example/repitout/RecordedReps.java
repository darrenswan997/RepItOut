package com.example.repitout;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecordedReps extends WearableActivity {

    private TextView mTextView, setRepsT;
    Button saveSRBtn;
    counter counterclass = new counter();
    Map<String,String> setsReps;
    ArrayList<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_reps);

        mTextView = (TextView) findViewById(R.id.text);
        setRepsT = findViewById(R.id.setandrepsTV);
        saveSRBtn = findViewById(R.id.saveSetsReps);
        saveSRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Intent i = getIntent();
        list = i.getStringArrayListExtra("sets");
        StringBuilder sb = new StringBuilder();
        for (String s : list){
            Integer setNum = 1;
            sb.append("set " + setNum++ + ":" + s + "\n");
            setRepsT.setText(sb);
            //*setsReps = new HashMap<>();
           // setsReps.put("Set",s);*//
        }

        // Enables Always-on
        setAmbientEnabled();
    }
}
