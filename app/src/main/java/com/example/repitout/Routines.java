package com.example.repitout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.repitout.ui.routines.RoutinesFragment;

public class Routines extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routines_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RoutinesFragment.newInstance())
                    .commitNow();
        }
    }
}
