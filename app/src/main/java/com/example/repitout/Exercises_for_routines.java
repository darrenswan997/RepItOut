package com.example.repitout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Exercises_for_routines extends AppCompatActivity {

    public ListView execerise_lv;
    public ArrayList<Workout> wList;
    public ArrayList<String> titleList;
    public Adapter adapter;
    private FloatingActionButton fab;
    String exercise;
    ArrayList<String> exercises = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_for_routines);

        fab = findViewById(R.id.confirm);
        execerise_lv = findViewById(R.id.exercise_list);

        wList = DataHelper.loadWorkout(this);
        titleList = new ArrayList<>();
        for (int i = 0; i<wList.size(); i++){
            String str = wList.get(i).getTitle();
            titleList.add(str);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercises_for_routines.this,CreateRoutines.class);
                intent.putExtra("exercises",exercises);
                startActivity(intent);
            }
        });

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList);
        execerise_lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titleList));
        execerise_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String exercise = titleList.get(position);
                 exercise = (execerise_lv.getItemAtPosition(position)).toString();
                 exercises.add(exercise);
                 System.out.println(exercises);

            }
        });


    }



}

