package com.example.repitout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.repitout.ui.exercises.ExercisesFragment;
import com.example.repitout.ui.exercises.ExercisesViewModel;

import java.util.ArrayList;

public class Exercises extends AppCompatActivity {


    private ListView execerise_lv;
    private ArrayList<Workout> Wlist;
    private ArrayList<String> titleList;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ExercisesFragment.newInstance())
                    .commitNow();
        }
        execerise_lv = findViewById(R.id.exercise_list);

        Wlist = DataHelper.loadWorkout(this);
        titleList = new ArrayList<>();
        for (int i = 0; i<Wlist.size(); i++){
            String str = Wlist.get(i).getTitle();
            titleList.add(str);
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList);
        execerise_lv.setAdapter((ListAdapter) adapter);
        execerise_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Exercises.this, DetailActivity.class);
                String title = Wlist.get(position).getTitle();
                String link = Wlist.get(position).getLink();

                intent.putExtra("EXTRA_TITLE", title);
                intent.putExtra("EXTRA_LINK", link);

                startActivity(intent);

            }
        });
    }
}
