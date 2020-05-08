package com.example.repitout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Progress extends nav_main_page {

    ListView lv;
    ArrayList<String> data = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> dataArrayAdapter;
    public String day, selDay, r;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Workout");
    DatabaseReference db = databaseReference.child("Routine_History");
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    String userID = firebaseUser.getUid();
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_progress, null, false);
        dl.addView(contentView, 0);


        lv = findViewById(R.id.showLV);
        lv.setAdapter(dataArrayAdapter);
        dataArrayAdapter = new ArrayAdapter<>(Progress.this, android.R.layout.simple_list_item_1, data);


            query = FirebaseDatabase.getInstance().getReference("Workout")
                    .child(userID)
                    .child("Routine_History")
                    .child("Exercises")
                    .orderByKey()
            ;


            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    data.clear();
                    for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Exercises_helper helper = snapshot.getValue(Exercises_helper.class);
                        Long time = (Long)snapshot.child("time").getValue();
                        DateFormat dateFormat = DateFormat.getDateTimeInstance();
                        Date netDate = (new Date(time));
                        String date = dateFormat.format(netDate);
                        String name = helper.getName();
                        String reps = helper.getReps();
                        data.add("Exercise : " + name + "\n" + "Reps : " + reps + "\n" + "Completed on : " + date);
                        dataArrayAdapter.notifyDataSetChanged();
                        lv.setAdapter(dataArrayAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }



