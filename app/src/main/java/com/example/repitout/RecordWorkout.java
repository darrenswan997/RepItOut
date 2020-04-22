package com.example.repitout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RecordWorkout extends AppCompatActivity {

    TextView routineName;
    ListView listv;
    Map<String,Object> map;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    String userID = firebaseUser.getUid();
    ArrayList<String> arrayList;
    //ArrayAdapter<String> adapter;
    String routName;
    RecyclerView rV;
    ExcercisesAdapter adapter;
    List<Exercises_helper> exerciseList;
    CreateRoutines createRoutines = new CreateRoutines();
    String nameE = createRoutines.eName;



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

        Intent intent = getIntent();
        routName = intent.getStringExtra("Routine Name");
        String eDate = intent.getStringExtra("date");
        routineName.setText(routName + "\n" + "\n" + eDate);


        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("Workout").child(userID).child("Routines").child(routName).child("exercises");
        Query query = FirebaseDatabase.getInstance().getReference("Workout")
                .child(userID)
                .child("Routines")
                .child(routName)
                .child("Exercises")
                .orderByValue()
                ;

        query.addValueEventListener(valueEventListener);
        /*exerciseList.clear();
        if (dataSnapshot.exists()){
            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                //String s = snapshot.getValue(String.class);

                Exercises_helper exercises_helper = snapshot.getValue(Exercises_helper.class);
                exerciseList.add(exercises_helper);
            }

            adapter.notifyDataSetChanged();*/

    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            exerciseList.clear();
            if (dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String name = snapshot.getKey();

                    Exercises_helper helper = new Exercises_helper(name);
                    exerciseList.add(helper);

                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };



}
