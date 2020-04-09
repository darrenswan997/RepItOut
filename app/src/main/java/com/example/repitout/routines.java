package com.example.repitout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataReadRequest;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class routines extends nav_main_page {

    private static final int MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION = 1;
    private static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1;
    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_routines, null, false);
        dl.addView(contentView, 0);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
        }

        ActivityCompat.requestPermissions(this,
                new String[]{(Manifest.permission.ACTIVITY_RECOGNITION)},
                MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION);


        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_WORKOUT_EXERCISE, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.TYPE_WORKOUT_EXERCISE, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_WRITE)
                .build();

        GoogleSignInAccount account = GoogleSignIn.getAccountForExtension(this, fitnessOptions);

        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this, // your activity
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE, // e.g. 1
                    account,
                    fitnessOptions);
        } else {
            accessGoogleFit();
        }




    }


    private void accessGoogleFit() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.YEAR, -1);
        long startTime = cal.getTimeInMillis();

        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .bucketByTime(1, TimeUnit.DAYS)
                .build();


        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_WORKOUT_EXERCISE, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.TYPE_WORKOUT_EXERCISE, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_WRITE)
                .build();

            GoogleSignInAccount account = GoogleSignIn
                    .getAccountForExtension(this, fitnessOptions);

        Fitness.getHistoryClient(this, account)
                .readData(readRequest)
                .addOnSuccessListener(response -> {
                    // Use response data here

                    Log.d(TAG, "OnSuccess()");
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "OnFailure()", e);
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
                accessGoogleFit();
            }
        }
    }



}
