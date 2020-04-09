package com.example.repitout_wear;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.wear.ambient.AmbientModeSupport;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.RecordingClient;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.request.SessionInsertRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";
    // An up-down movement that takes more than 2 seconds will not be registered (in nanoseconds).
    private static final long TIME_THRESHOLD_NS = TimeUnit.SECONDS.toNanos(2);
    /**
     * Earth gravity is around 9.8 m/s^2 but user may not completely direct his/her hand vertical
     * during the exercise so we leave some room. Basically, if the x-component of gravity, as
     * measured by the Gravity sensor, changes with a variation delta > 0.03 from the hand down
     * and hand up threshold we define below, we consider that a successful count.
     *
     * This is a very rudimentary formula and is by no means production accurate. You will want to
     * take into account Y and Z gravity changes to get a truly accurate jumping jack.
     *
     * This sample is just meant to show how to easily get sensor values and use them.
     */
    private static final float HAND_DOWN_GRAVITY_X_THRESHOLD = -.040f;
    private static final float HAND_UP_GRAVITY_X_THRESHOLD = -.010f;

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private long mLastTime = 0;
    private int mJumpCounter = 0;
    private boolean mHandDown = true;

    Button counter_Btn;

    private ViewPager mPager;

    private ImageView mSecondIndicator;
    private ImageView mFirstIndicator;


    //private static final String TAG = "";
    private static final int REQUEST_OAUTH_REQUEST_CODE = 1;
    private OnDataPointListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counter_Btn = findViewById(R.id.rep_counter);

        counter_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,counter.class));
            }
        });




        //check if user has sign in permissions, other wise request
        /*if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), getFitnessSignInOptions())) {
            GoogleSignIn.requestPermissions(
                    this,
                    REQUEST_OAUTH_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(this),
                    getFitnessSignInOptions());
        } else {
            subscribe();
        }

       *//* subList();*//*

        }*/
    }






    /**
     * A wrapper for {@link #findFitnessDataSources}. If the user account has OAuth permission,
     * continue to {@link #findFitnessDataSources}, else request OAuth permission for the account.
     */
   /* private void findFitnessDataSourcesWrapper() {
        if (hasOAuthPermission()) {
            findFitnessDataSources();
        } else {
            requestOAuthPermission();
        }
    }


    *//** Gets the {@link FitnessOptions} in order to check or request OAuth permission for the user. *//*
    private FitnessOptions getFitnessSignInOptions() {
        return FitnessOptions.builder().addDataType(DataType.TYPE_STEP_COUNT_DELTA).build();
    }*/

  /*  *//** Checks if user's account has OAuth permission to Fitness API. *//*
    private boolean hasOAuthPermission() {
        FitnessOptions fitnessOptions = getFitnessSignInOptions();
        return GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions);
    }

    *//** Launches the Google SignIn activity to request OAuth permission for the user. *//*
    private void requestOAuthPermission() {
        FitnessOptions fitnessOptions = getFitnessSignInOptions();
        GoogleSignIn.requestPermissions(
                this,
                REQUEST_OAUTH_REQUEST_CODE,
                GoogleSignIn.getLastSignedInAccount(this),
                fitnessOptions);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // This ensures that if the user denies the permissions then uses Settings to re-enable
        // them, the app will start working.
        findFitnessDataSourcesWrapper();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
                findFitnessDataSources();
            }
        }*/
   /* }
     [END auth_oncreate_setup]



    private void findFitnessDataSources(){
        // [START find_data_sources]
        // Note: Fitness.SensorsApi.findDataSources() requires the ACCESS_FINE_LOCATION permission.
        // Note: Fitness.SensorsApi.findDataSources() requires the ACCESS_FINE_LOCATION permission.
        Fitness.getSensorsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .findDataSources(
                        new DataSourcesRequest.Builder()
                                .setDataTypes(DataType.TYPE_STEP_COUNT_DELTA)
                                .setDataSourceTypes(DataSource.TYPE_RAW)
                                .build())
                .addOnSuccessListener(
                        new OnSuccessListener<List<DataSource>>() {
                            @Override
                            public void onSuccess(List<DataSource> dataSources) {
                                for (DataSource dataSource : dataSources) {
                                    Log.i(TAG, "Data source found: " + dataSource.toString());
                                    Log.i(TAG, "Data Source type: " + dataSource.getDataType().getName());

                                    // Let's register a listener to receive Activity data!
                                    if (dataSource.getDataType().equals(DataType.TYPE_STEP_COUNT_DELTA)
                                            && mListener == null) {
                                        Log.i(TAG, "Data source for LOCATION_SAMPLE found!  Registering.");
                                        registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_DELTA);
                                    }
                                }
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "failed", e);
                            }
                        });
        // [END find_data_sources]
        
        

    }
*/
   /* private void registerFitnessDataListener(DataSource dataSource, DataType typeStepCountDelta) {
        mListener =
                new OnDataPointListener() {
                    @Override
                    public void onDataPoint(DataPoint dataPoint) {
                        for (Field field : dataPoint.getDataType().getFields()) {
                            Value val = dataPoint.getValue(field);
                            Log.i(TAG, "Detected DataPoint field: " + field.getName());
                            Log.i(TAG, "Detected DataPoint value: " + val);
                        }
                    }
                };

        Fitness.getSensorsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .add(
                        new SensorRequest.Builder()
                                .setDataSource(dataSource) // Optional but recommended for custom data sets.
                                .setDataType(DataType.TYPE_STEP_COUNT_DELTA) // Can't be omitted.
                                .setSamplingRate(10, TimeUnit.SECONDS)
                                .build(),
                        mListener)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, "Listener registered!");
                                } else {
                                    Log.e(TAG, "Listener not registered.", task.getException());
                                }
                            }
                        });
        // [END register_data_listener]
    }

    /** Unregisters the listener with the Sensors API. **/
    /*private void unregisterFitnessDataListener() {
        if (mListener == null) {
            // This code only activates one listener at a time.  If there's no listener, there's
            // nothing to unregister.
            return;
        }

        // [START unregister_data_listener]
        // Waiting isn't actually necessary as the unregister call will complete regardless,
        // even if called from within onStop, but a callback can still be added in order to
        // inspect the results.
        Fitness.getSensorsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .remove(mListener)
                .addOnCompleteListener(
                        new OnCompleteListener<Boolean>() {
                            @Override
                            public void onComplete(@NonNull Task<Boolean> task) {
                                if (task.isSuccessful() && task.getResult()) {
                                    Log.i(TAG, "Listener was removed!");
                                } else {
                                    Log.i(TAG, "Listener was not removed.");
                                }
                            }
                        });
        // [END unregister_data_listener]
    }
*/
    /*private SessionReadRequest readFitnessSession() {
        // Set a start and end time for the data, using a start time of 1 hour before this moment.
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        long startTime = cal.getTimeInMillis();

        // Create a data source
        DataSource dataSource =
                new DataSource.Builder()
                        .setAppPackageName(this)
                        .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
                        .setStreamName(TAG + " - step count")
                        .setType(DataSource.TYPE_RAW)
                        .build();


         final String sessionName = "Daily Steps";
         //create a session with metadata about activity
        Session session = new Session.Builder()
                .setName(sessionName)
                .setIdentifier("steps")
                .setActivity(FitnessActivities.WALKING)
                .setDescription("daily steps")
                .setStartTime(startTime, TimeUnit.MILLISECONDS)
                .build();

        //build a session insert request
        SessionInsertRequest insertRequest = new SessionInsertRequest.Builder()
                .setSession(session)
                .addDataSet(speedDataSet)
                .build();

        Task<Void> response = Fitness.getSessionsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .startSession(session);



        return readRequest;*/
        //end sessions
    //}

   /* private void subscribe()

    {
        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "Successfully subscribed!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "There was a problem subscribing.");
                    }
                });
        //end of subscription
    }

    private void subList(){
        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .listSubscriptions(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(new OnSuccessListener<List<Subscription>>() {
                    @Override
                    public void onSuccess(List<Subscription> subscriptions) {
                        for (Subscription sc : subscriptions) {
                            DataType dt = sc.getDataType();
                            Log.i(TAG, "Active subscription for data type: " + dt.getName());
                        }
                    }
                });
        //end subscription list
    }
*/




}

