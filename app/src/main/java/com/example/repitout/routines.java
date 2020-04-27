package com.example.repitout;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class routines extends nav_main_page {


    Button createRoutine;
    RecyclerView rv;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    String userID = firebaseUser.getUid();
    DatabaseReference dbWorkout;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("workout");
    DatabaseReference dbr = db.child(userID);

    ProductAdapter adapter;
    List<routines_helper> routinesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_routines, null, false);
        dl.addView(contentView, 0);

        rv = findViewById(R.id.recycler);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        routinesList = new ArrayList<>();
        adapter=new ProductAdapter(this, routinesList);
        rv.setAdapter(adapter);

        createRoutine = findViewById(R.id.createRoutine);
        createRoutine.setOnClickListener(v -> startActivity(new Intent(routines.this, CreateRoutines.class)));

        dbWorkout = FirebaseDatabase.getInstance().getReference("Workout");

        //dbWorkout.addListenerForSingleValueEvent(valueEventListener);

        Query query = FirebaseDatabase.getInstance().getReference("Workout")
                .child(userID)
                .child("Routines")
                .orderByChild("name");

       query.addListenerForSingleValueEvent(valueEventListener);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            routinesList.clear();
            if (dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String name = snapshot.getKey();
                    routines_helper helper = new routines_helper(name);
                    routinesList.add(helper);

                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}
