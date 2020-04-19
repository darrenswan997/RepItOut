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
    Button createRoutine;
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


        createRoutine = findViewById(R.id.createRoutine);

        createRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(routines.this, CreateRoutines.class));
            }
        });


    }

}
