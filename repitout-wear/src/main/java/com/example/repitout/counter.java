package com.example.repitout;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class counter extends WearableActivity implements SensorEventListener {

    private static final float DOWN_X_THRESHOLD = -4.82f;
    private static final float UP_X_THRESHOLD = 4.82f;
    private float xPrevious, yPrevious, zPrevious;
    private TextView mTextView;
    private SensorManager mSensorManager;
    private Sensor accel;
    private long mLastTime = 0;
    private boolean mHandDown = true;
    private int mCounter = 0;
    TextView reps_tv, counter_tv;
    Button reset_btn, startBtn, sets;
    ToggleButton pause;
    private boolean recording ;
    public String savedReps;


    // An up-down movement that takes more than 2 seconds will not be registered (in nanoseconds).
    private static final long TIME_THRESHOLD_NS = TimeUnit.SECONDS.toNanos(5);

    private boolean firstUpdate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        reps_tv = findViewById(R.id.repTv);
        counter_tv = findViewById(R.id.counterTv);
        mTextView =  findViewById(R.id.text);

        pause = findViewById(R.id.pause);
        sets = findViewById(R.id.view_sets_btn);
        sets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(counter.this, RecordedReps.class));
                saveReps(mCounter);
            }
        });
        /*startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               recording = true;
                Toast.makeText(getBaseContext(),"Started Recording Reps", Toast.LENGTH_SHORT).show();
            }
        });*/


        mSensorManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //mSensorManager.registerListener(this,accel,SensorManager.SENSOR_DELAY_NORMAL);

        reset_btn = findViewById(R.id.reset_btn);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCounter();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pause.isChecked()){
                    //register the sensor
                    mSensorManager.registerListener(counter.this,accel,SensorManager.SENSOR_DELAY_NORMAL);
                }else {
                    //unregster the listener
                    mSensorManager.unregisterListener(counter.this);
                    recording = true;
                }
            }
        });

        // Enables Always-on
        setAmbientEnabled();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        detectcurlMovement(event.values[0],event.values[1],event.values[2],event.timestamp);
        detectpushMovement(event.values[0],event.values[1],event.values[2],event.timestamp);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void detectcurlMovement(float xNewAccel, float yNewAccel, float zNewAccel, long timestamp) {
        //y will be for pressing/pulling movement
        if ((yNewAccel <= DOWN_X_THRESHOLD)
                || (yNewAccel >= UP_X_THRESHOLD)) {

            if (timestamp - mLastTime < TIME_THRESHOLD_NS) {
                // Hand is down when yValue is negative.
                onRepDetected(yNewAccel <= DOWN_X_THRESHOLD);
            }

            mLastTime = timestamp;
        }
    }

    private void detectpushMovement(float xNewAccel, float yNewAccel, float zNewAccel, long timestamp) {
        //y will be for pressing/pulling movement
        if ((xNewAccel <= DOWN_X_THRESHOLD)
                || (xNewAccel >= UP_X_THRESHOLD)) {

            if (timestamp - mLastTime < TIME_THRESHOLD_NS) {
                // Hand is down when yValue is negative.
                onRepDetected(xNewAccel <= DOWN_X_THRESHOLD);
            }

            mLastTime = timestamp;
        }
    }

    /**
     * Called on detection of a successful down -> up or up -> down movement of hand.
     */
    //
    private void onRepDetected(boolean handDown) {
        if (mHandDown != handDown) {
            mHandDown = handDown;

            //need this if statement or else counter increments by 2 instead of 1
            if (mHandDown && mCounter >= 0)
                mCounter++;
                setCounter(mCounter);
            }

        }



    private void setCounter(int i) {
        counter_tv.setText(String.valueOf(i));

    }

    public void resetCounter(){
        mCounter = 0;
        counter_tv.setText(String.valueOf(mCounter));
    }

    public void saveReps(int i){
        savedReps= (String.valueOf(i));
        Intent intent = new Intent(counter.this, RecordedReps.class);
        intent.putExtra("reps", savedReps);
        startActivity(intent);

    }

}
