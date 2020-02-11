package com.example.repitout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.repitout.ui.progress.ProgressFragment;

public class Progress extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ProgressFragment.newInstance())
                    .commitNow();
        }
    }
}
