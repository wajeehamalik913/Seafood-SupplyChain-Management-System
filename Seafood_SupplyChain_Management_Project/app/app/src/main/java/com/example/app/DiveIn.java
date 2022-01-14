package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class DiveIn extends AppCompatActivity {
    private static int SPLASH=3000;
    Animation top, right;
    ImageView seafood,slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dive_in);
        top= AnimationUtils.loadAnimation(this,R.anim.left_anim);
        right= AnimationUtils.loadAnimation(this,R.anim.right_anim);
        seafood=findViewById(R.id.imageView4);
        slogan=findViewById(R.id.imageView5);
        seafood.setAnimation(top);
        slogan.setAnimation(right);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(DiveIn.this,Register.class);
                startActivity(i);
                finish();
            }
        },SPLASH);
    }
}