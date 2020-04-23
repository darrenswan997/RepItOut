package com.example.repitout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import static android.util.Config.LOGD;

public class RepHandler extends AppCompatActivity {
    public TextView tvM;
    public EditText setsET, repsET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_handler);

        tvM = findViewById(R.id.messageTV);
        setsET = findViewById(R.id.ETSets);
        repsET = findViewById(R.id.ETReps);



    }
}
