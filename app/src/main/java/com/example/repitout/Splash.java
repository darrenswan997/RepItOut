package com.example.repitout;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    private Handler mHandler;
    private Runnable mRunnable;
    Animation rotateAnimation;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_splash);

        imageView=findViewById(R.id.anim);
        mRunnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                //finish();
            }
        };

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable,3000);
        rotateAnimation();
    }

    private void rotateAnimation() {
        rotateAnimation= AnimationUtils.loadAnimation(this,R.anim.rotate);
        imageView.startAnimation(rotateAnimation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler !=null && mRunnable!=null)
            mHandler.removeCallbacks(mRunnable);
    }


}

