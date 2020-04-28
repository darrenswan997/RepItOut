package com.example.repitout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView title, link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.title_tv);
        link = findViewById(R.id.link_tv);

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            String t = extra.getString("EXTRA_TITLE");
            String l = extra.getString("EXTRA_LINK");

            title.setText(t);
            link.setText(l);
        }




    }
}
