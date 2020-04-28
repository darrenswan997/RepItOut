package com.example.repitout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateRoutines extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText newRoutineName;

    int startYear, startMonth,startDay;
    TextView selectedDate, exercizes_TV;
    public String routine, eName, dayofWeek;
    Spinner spinner;
    ArrayList<String> days = new ArrayList<>();
    Button saveDetails, addExercises;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Workout");
    ArrayList<String> exercises ;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("routines");
    Map<String,String> exerciseMap;
    ArrayList<String> exc ;
    ArrayAdapter dowApadter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routines);

        saveDetails = findViewById(R.id.saveRoutine);
        spinner = findViewById(R.id.Spinner);
        exercizes_TV = findViewById(R.id.excersizesTV);
        addExercises = findViewById(R.id.addExercisesBtn);
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        days.add("Sunday");

        dowApadter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,days);
        spinner.setAdapter(dowApadter);
        spinner.setOnItemSelectedListener(this);

        exerciseMap = new HashMap<>();

        addExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateRoutines.this,ListForCreateRoutines.class);
                intent.putExtra("day",routine);
                startActivity(intent);
            }
        });

        //intent from listforcreateroutines
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




        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRoutine();
                startActivity(new Intent(CreateRoutines.this, routines.class));
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        dayofWeek  = parent.getSelectedItem().toString();
        routine = dayofWeek;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void saveRoutine() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userID = firebaseUser.getUid();
        routines_helper routines = new routines_helper(routine);
        routine = dayofWeek;
        db = databaseReference.child(userID).child("Routines").child(routine);
        DatabaseReference dbr = db.child("Exercises");

        db.setValue(routines);
        for (String s : exercises){
            eName = s;
             exc = new ArrayList<String>();
             exc.add(eName);
             String reps = "0";
             Exercises_helper exercises_helper = new Exercises_helper(eName, reps);
            exerciseMap.put("Exercises",eName);
            dbr.child(eName).setValue(exercises_helper);
        }


        Toast.makeText(this, "Your routine has been recorded", Toast.LENGTH_LONG).show();

    }



}
