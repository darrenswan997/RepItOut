package com.example.repitout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User_details extends AppCompatActivity {

    EditText age, height, weight;
    Button save;
    String age_s, height_s, weight_s;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Details");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        age = findViewById(R.id.age_et);
        height = findViewById(R.id.Height_et);
        weight = findViewById(R.id.Weight_et);
        save = findViewById(R.id.detailsBtn);



        save.setOnClickListener(v -> {
              age_s = age.getText().toString();
              height_s = height.getText().toString();
              weight_s = weight.getText().toString();

              if (age_s.isEmpty()){
                  age.setError("Enter your age");
                  age.requestFocus();
              }
              if (height_s.isEmpty()){
                  height.setError("Enter your height");
                  height.requestFocus();
              }
              if (weight_s.isEmpty()){
                  weight.setError("Enter your weight in kg");
              }
              else {
                  saveProfileData();
                  startActivity(new Intent(User_details.this, Profile.class));
                  finish();
              }
        });
    }

    private void saveProfileData() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userID = firebaseUser.getUid();
        User_Profile_Details profile_details = new User_Profile_Details(age_s,height_s,weight_s);
        databaseReference.child(userID).setValue(profile_details);
        Toast.makeText(this, "Your information has been updated", Toast.LENGTH_LONG).show();

    }
}
