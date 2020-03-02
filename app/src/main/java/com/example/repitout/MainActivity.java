package com.example.repitout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button loginBtn, Btnregister;
    private String et_email, et_password;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        etEmail = (EditText) findViewById(R.id.logEmail);
        etPassword = (EditText)findViewById(R.id.logPassword);

        loginBtn = (Button)findViewById(R.id.logBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_email=etEmail.getText().toString();
                et_password=etPassword.getText().toString();

                if (et_email.isEmpty()){ //if email et is blank
                    etEmail.setError("Enter your Login Email"); //sends error message
                    etEmail.requestFocus(); //keypad starts on this line
                }

                else if (!Patterns.EMAIL_ADDRESS.matcher(et_email).matches()){
                    Toast.makeText(MainActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    etEmail.setError("Enter your Login Email"); //sends error message
                    etEmail.requestFocus(); //keypad starts on this line
                }
                else if (et_password.isEmpty()){ //if email et is blank
                    etPassword.setError("Enter your Password"); //sends error message
                    etPassword.requestFocus(); //keypad starts on this line
                }
                else{
                    firebaseAuth.signInWithEmailAndPassword(et_email,et_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                etEmail.setText("");
                                etPassword.setText("");

                                Toast.makeText(MainActivity.this, "Log in Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this,Progress.class));
                                finish();
                            }

                            else{
                                Toast.makeText(MainActivity.this, "Log in failed, you entered a wrong Email or Password", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

        Btnregister = (Button)findViewById(R.id.registerBtn);
        Btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));

            }
        });
    }
}
