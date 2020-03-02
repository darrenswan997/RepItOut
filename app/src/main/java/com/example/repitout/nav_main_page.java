package com.example.repitout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class nav_main_page extends AppCompatActivity {

    protected DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_main_page);

        dl = findViewById(R.id.nav_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.open, R.string.close);

        dl.addDrawerListener(t);
        t.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = findViewById(R.id.nv);

        // adding click event for nav drawer
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch (id){
                    case R.id.nav_routines:
                        startActivity(new Intent(nav_main_page.this, routines.class));
                        break;
                    case R.id.nav_exercise:
                        startActivity(new Intent(nav_main_page.this,Exercises.class));
                        break;
                    case R.id.nav_my_profile:
                        startActivity(new Intent(nav_main_page.this,Profile.class));
                        break;
                    case R.id.nav_my_progress:
                        startActivity(new Intent(nav_main_page.this,Progress.class));
                        break;
                        default:
                            return true;
                }
                return true;
            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (t.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
}
