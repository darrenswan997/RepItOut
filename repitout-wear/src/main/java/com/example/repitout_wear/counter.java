package com.example.repitout_wear;

import android.content.Context;
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

import java.util.concurrent.TimeUnit;

public class counter extends WearableActivity implements SensorEventListener {

    private static final int DEFAULT_VIBRATION_DURATION_MS = 500;
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
    Button reset_btn;


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
        mSensorManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this,accel,SensorManager.SENSOR_DELAY_NORMAL);

        reset_btn = findViewById(R.id.reset_btn);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCounter();
            }
        });

        // Enables Always-on
        setAmbientEnabled();
    }
    /**
     * Causes device to vibrate for the given duration (in millis). If duration is set to 0, then it
     * will use the <code>DEFAULT_VIBRATION_DURATION_MS</code>.
     */
    public static void vibrate(Context context, int duration) {
        if (duration == 0) {
            duration = DEFAULT_VIBRATION_DURATION_MS;
        }
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(duration);
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
    private void onRepDetected(boolean handDown) {
        if (mHandDown != handDown) {
            mHandDown = handDown;

            // Only count when the hand is down (means the hand has gone up, then down).
            if (mHandDown) {
                mCounter++;
                setCounter(mCounter);
            }
            if (mCounter > 20){
                resetCounter();

            }
        }
    }

    /**
     * Updates the counter on UI, saves it to preferences and vibrates the watch when counter
     * reaches a multiple of 10.
     */
    private void setCounter(int i) {
        counter_tv.setText(String.valueOf(i));
        if (i > 0 && i % 10 == 0) {
            vibrate(this, 0);
        }
    }

    public void resetCounter(){
        setCounter(0);

    }


}
