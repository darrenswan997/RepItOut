package com.example.repitout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.repitout.ui.feedback.FeedbackFragment;

public class feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_activity2);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, FeedbackFragment.newInstance())
                    .commitNow();
        }
    }
}
