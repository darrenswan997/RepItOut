package com.example.repitout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RecordWorkout extends AppCompatActivity {

    TextView routineName;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    DatabaseReference db;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Workout");
    String userID = firebaseUser.getUid();
    String routName, dayOfWeek ,days,finalday, daysCR ,exerciseReps, dayfromrepHandler;
    Button addExercises, saveRoutine;
    RecyclerView rV;
    ExcercisesAdapter adapter;
    List<Exercises_helper> exerciseList;
    ArrayList<String> exercises ;
    Map<String,String> exerciseMap;
    ArrayList<String> exc ;
    public Query query;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_workout);

        routineName = findViewById(R.id.tVRoutineName);
        exerciseList = new ArrayList<>();
        rV = findViewById(R.id.exercise_RV);
        rV.setHasFixedSize(true);
        rV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExcercisesAdapter(this, exerciseList);
        rV.setAdapter(adapter);
        addExercises = findViewById(R.id.add_moreExercises);

        addExercises.setOnClickListener(v -> {
            Intent intent2 = new Intent(RecordWorkout.this,Exercises_for_routines.class);
            intent2.putExtra("Day", days);
            startActivity(intent2);
        });

        //data from RepHandler
        Intent intent2 = getIntent();
        dayfromrepHandler = intent2.getStringExtra("DayName");

        //data from create routines
        Intent intent = getIntent();
        routName = intent.getStringExtra("Routine Name ");
        days =  routName;

        //get data

        daysCR = intent.getStringExtra("daysNameCR");

        //from listforcreateRoutines
        dayOfWeek = intent.getStringExtra("dayName");
        exercises= intent.getStringArrayListExtra("exercises");


        //checks which activity it is getting the day name from and sets the textview accordingly
        //also uses the vairable to query the firebase in order to get the correct exercises for the day
        if (days != null ){
            finalday = days;
        }else if (dayOfWeek != null){
            finalday = dayOfWeek;
        }else if (daysCR !=null){
            finalday = daysCR;
        }else if (dayfromrepHandler != null){
            finalday = dayfromrepHandler;
        }

        routineName.setText(finalday);

        //sharedPreference to send day to rephandler class
        SharedPreferences sharedPreferences = this.getSharedPreferences("DayForDB", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("DayW",finalday);

        editor.apply();

//send current day of the week to rephandler class to save data to correct db node
        Intent intent1 = new Intent(this, RepHandler.class);
        intent1.putExtra("Day",finalday);
        LocalBroadcastManager.getInstance(RecordWorkout.this).sendBroadcast(intent1);



        //query the database to get the list of exercises for each day
        //days variable need to be passed from different activities.this is
        //to know which day it is querying
         query = FirebaseDatabase.getInstance().getReference("Workout")
                .child(userID)
                .child("Routines")
                .child(finalday) //as a result of if/else statement above
                .child("Exercises")
                .orderByValue()
                ;

        query.addValueEventListener(valueEventListener);

    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            exerciseList.clear();
            if (dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Exercises_helper eHelper = snapshot.getValue(Exercises_helper.class);
                    exerciseList.add(eHelper);

                    String name = snapshot.getKey();
                    Exercises_helper helper = new Exercises_helper(name);//gets name of exercises
                    //exerciseList.add(helper);


                }

                adapter.notifyDataSetChanged(); //updates recycler view with latest entries to db
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };



    }




