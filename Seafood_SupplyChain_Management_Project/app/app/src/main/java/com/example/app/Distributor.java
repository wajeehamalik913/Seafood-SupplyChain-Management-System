package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Distributor extends AppCompatActivity {

    Button mLogOutBtn,mProfile,mAddExt,mSeafood,mOrder,mTrack,mGetOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor);
        mLogOutBtn=findViewById(R.id.logout);
        mProfile=findViewById(R.id.DisProfile);
        mAddExt=findViewById(R.id.AddExtractor);
        mSeafood=findViewById(R.id.viewSeafood);
        mOrder=findViewById(R.id.OrderSeafood);
        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DisProfile.class));
            }
        });
        mAddExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddExtractor.class));
            }
        });
        mSeafood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewDisSeafood.class));
            }
        });
        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),OrderSeafood.class));
            }
        });

    }
}