package com.example.repitout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;



import java.util.ArrayList;

public class Exercises extends nav_main_page {


    private ListView execerise_lv;
    private ArrayList<Workout> wList;
    private ArrayList<String> titleList;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.exercises_activity, null, false);
        dl.addView(contentView, 0);

        execerise_lv = findViewById(R.id.exercise_list);

        wList = DataHelper.loadWorkout(this);
        titleList = new ArrayList<>();
        for (int i = 0; i<wList.size(); i++){
            String str = wList.get(i).getTitle();
            titleList.add(str);
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList);
        execerise_lv.setAdapter((ListAdapter) adapter);
        execerise_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Exercises.this, DetailActivity.class);
                String title = wList.get(position).getTitle();
                String link = wList.get(position).getLink();

                intent.putExtra("EXTRA_TITLE", title);
                intent.putExtra("EXTRA_LINK", link);

                startActivity(intent);

            }
        });
    }
}
