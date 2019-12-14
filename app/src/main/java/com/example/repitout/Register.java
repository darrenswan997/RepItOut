package com.example.repitout;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private EditText nameUserReg, phoneUserReg, emailUserReg, passUserReg, confPassReg;
    private Button bttnUserReg;
    private String name_UserReg, phone_UserReg, email_UserReg, pass_UserReg, confPass_Reg;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        nameUserReg = (EditText)findViewById(R.id.etNameUserReg);
        phoneUserReg = (EditText)findViewById(R.id.etPhoneUserReg);
        emailUserReg = (EditText)findViewById(R.id.etEmailUserReg);
        passUserReg = (EditText)findViewById(R.id.etPassUserReg);
        confPassReg = (EditText)findViewById(R.id.etConfPassUserReg);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        bttnUserReg = (Button)findViewById(R.id.btnUserReg);
        bttnUserReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_UserReg = nameUserReg.getText().toString();
                phone_UserReg = phoneUserReg.getText().toString();
                email_UserReg = emailUserReg.getText().toString();
                pass_UserReg = passUserReg.getText().toString();
                confPass_Reg = confPassReg.getText().toString();

                if (name_UserReg.isEmpty()){
                    nameUserReg.setError("Enter Name");
                    nameUserReg.requestFocus();
                }

                else if(phone_UserReg.isEmpty()){
                    phoneUserReg.setError("Enter Phone");
                    passUserReg.requestFocus();
                }

                else if (email_UserReg.isEmpty()){
                    emailUserReg.setError("Enter Email Address");
                    emailUserReg.requestFocus();
                }

                else if(!Patterns.EMAIL_ADDRESS.matcher(email_UserReg).matches()){
                    Toast.makeText(Register.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    emailUserReg.setError("Enter a valid Email Address");
                    emailUserReg.requestFocus();
                }

                else if(pass_UserReg.isEmpty()){
                    passUserReg.setError("Enter password");
                    passUserReg.requestFocus();
                }

                else if(confPass_Reg.isEmpty()){
                    confPassReg.setError("Enter your address");
                    confPassReg.requestFocus();
                }

                else if (!pass_UserReg.equals(confPass_Reg)) {
                    Toast.makeText(Register.this, "Confirm Password does not match Password", Toast.LENGTH_SHORT).show();
                    confPassReg.setError("Enter same Password");
                    confPassReg.requestFocus();
                }

                else{
                    String new_emailReg = emailUserReg.getText().toString().trim();
                    String new_passReg = passUserReg.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(new_emailReg,new_passReg).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                saveUserData();
                                finish();
                            }
                            startActivity(new Intent( Register.this,Nav.class));
                        }
                    });
                }

            }
        });



    }

    public void saveUserData(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userID = firebaseUser.getUid();
        com.example.repitout.User userReg = new com.example.repitout.User(name_UserReg, phone_UserReg, email_UserReg);
        databaseReference.child(userID).setValue(userReg);
        Toast.makeText(this, "User registered successfully", Toast.LENGTH_LONG).show();
    }
}


