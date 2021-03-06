package com.example.repitout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListForCreateRoutines extends AppCompatActivity {

    public ListView execerise_lv;
    public ArrayList<Workout> wList;
    public ArrayList<String> titleList;
    public Adapter adapter;
    private FloatingActionButton fab;
    String exercise;
    ArrayList<String> exercises = new ArrayList<>();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    String userID = firebaseUser.getUid();
    DatabaseReference db;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Workout").child(userID).child("Routines").child("name");
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Workout");
    String stri, name, eName;
    TextView day;
    ArrayList<String> exc ;
    Map<String, String> exerciseMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_for_create_routines);
        fab = findViewById(R.id.confirm);
        execerise_lv = findViewById(R.id.exercise_list);
        day = findViewById(R.id.WoD);

        //from record workout
        Intent intent2 = getIntent();
        stri = intent2.getStringExtra("day");
        name = stri;
        day.setText(name);
        exerciseMap = new HashMap<>();

        wList = DataHelper.loadWorkout(this);
        titleList = new ArrayList<>();
        for (int i = 0; i<wList.size(); i++){
            String str = wList.get(i).getTitle();
            titleList.add(str);
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListForCreateRoutines.this,CreateRoutines.class);
                //pass list of exercises back to record workout
                intent.putExtra("exercises",exercises);
                //pass the day of the week value back to record workout
                //intent.putExtra("dayNameCR", name);
                startActivity(intent);
            }
        });


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, titleList);
        execerise_lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,titleList));

        execerise_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String exercise = titleList.get(position);

                exercise = (execerise_lv.getItemAtPosition(position)).toString();
                if (exercises.contains(exercise)){
                    exercises.remove(exercise);
                    view.setBackgroundColor(Color.WHITE);

                }else{
                    exercises.add(exercise);
                    view.setBackgroundColor(Color.GREEN);
                }
            }
        });


    }
    /*private void saveRoutine() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userID = firebaseUser.getUid();
        routines_helper routines = new routines_helper(name);
        db = databaseReference.child(userID).child("Routines").child(name);
        DatabaseReference dbr = db.child("Exercises");


        db.setValue(routines);
        for (String s : exercises) {
            eName = s;
            exc = new ArrayList<String>();
            exc.add(eName);
            exerciseMap.put("Exercises", eName);
            Exercises_helper helper = new Exercises_helper(s, exerciseMap);

            dbr.child(eName).setValue(exerciseMap);
        }*/



}

