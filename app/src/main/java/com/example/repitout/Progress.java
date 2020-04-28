package com.example.repitout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Progress extends nav_main_page {
   /* Spinner spinner;
    ArrayList<String> days = new ArrayList<>();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    String userID = firebaseUser.getUid();
    String day;
    TextView tv;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_progress, null, false);
        dl.addView(contentView, 0);

        /*tv = findViewById(R.id.viewRoutineTV);
        spinner = findViewById(R.id.Spin);

        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        days.add("Sunday");

        da;

        Query query = FirebaseDatabase.getInstance().getReference("Workout")
                .child(userID)
                .child("Routines")
                .child(day)
                .child("Exercises")
                .orderByValue();

        ArrayList<String> ar = new ArrayList<>();

        query.addValueEventListener(valueEventListener);




    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String name = snapshot.getKey();
                    tv.setText(name + "\n");
                }


            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }*/
    };
}
