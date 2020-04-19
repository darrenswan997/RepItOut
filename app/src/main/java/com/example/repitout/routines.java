package com.example.repitout;


import android.app.DatePickerDialog;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import javax.security.auth.login.LoginException;


public class routines extends nav_main_page {

    EditText newRoutineName, date;

    int startYear, startMonth,startDay;
    TextView selectedDate, exercizes_TV;
    String routine, year, month, day, exercise_List;
    Button saveDetails, addExercises;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Workout");
    ArrayList<String> exercises ;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("routines");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_routines, null, false);
        dl.addView(contentView, 0);

        selectedDate = findViewById(R.id.dateTV2);
        saveDetails = findViewById(R.id.saveRoutine);
        newRoutineName = findViewById(R.id.routineNameET);
        exercizes_TV = findViewById(R.id.excersizesTV);
        addExercises = findViewById(R.id.addExercisesBtn);

        addExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(routines.this, Exercises_for_routines.class));
            }
        });

        Intent intent = getIntent();

               exercises= intent.getStringArrayListExtra("exercises");

        StringBuilder builder = new StringBuilder();
        if (exercises == null) {
            System.out.println("empty");
        }else if(exercises.size() >=1){
            for (String s : exercises) {
                builder.append(s).append("\n");
                exercizes_TV.setText(builder.toString());
            }
        }




        Calendar MyCalendar1 = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener MyOnDateSetListener1 = new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker view, int year, int month, int day) {
                startYear = year;
                startMonth = month;
                startDay = day;

                selectedDate.setText("" + day  + "/" + (month + 1) + "/" + year);
            }
        };

        selectedDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(routines.this, MyOnDateSetListener1,
                                MyCalendar1.get(Calendar.YEAR),
                                MyCalendar1.get(Calendar.MONTH),
                                MyCalendar1.get(Calendar.DAY_OF_MONTH)
                        ).show();

                    }
                }

        );


        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routine = newRoutineName.getText().toString();
                day = String.valueOf(startDay);
                month = String.valueOf(startMonth+1);
                year = String.valueOf(startYear);
                //exercise_List = exercises.toString();

                if (routine.isEmpty()){
                    newRoutineName.setError("Enter routine name");
                    newRoutineName.requestFocus();
                }
                saveExercises();
                saveRoutine();
                startActivity(new Intent(routines.this, Profile.class));
            }
        });

    }

    private void saveRoutine() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userID = firebaseUser.getUid();
        routines_helper routines = new routines_helper(routine, day, month, year);
        db = databaseReference.child(userID).child("routines");
        db.setValue(routines);
        Toast.makeText(this, "Your routine has been recorded", Toast.LENGTH_LONG).show();

    }

    private void saveExercises(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userID = firebaseUser.getUid();
        //DatabaseReference ref = databaseReference.child(userID).child("routines");
        db = databaseReference.child(userID).child("exercises");
        for (String exercise : exercises){
            db.push().setValue(exercise);
        }
    }



}
