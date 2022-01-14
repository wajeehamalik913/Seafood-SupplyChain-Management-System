package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Restaurant extends AppCompatActivity {

    Button mLogOutBtn,mProfile,mAddDis,mSeafood,mOrder,mInventory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        mLogOutBtn=findViewById(R.id.logout);
        mProfile=findViewById(R.id.viewProfile);
        mAddDis=findViewById(R.id.AddDistributor);
        mOrder=findViewById(R.id.OrderSeafood);
        mInventory=findViewById(R.id.viewInventory);
        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ResProfile.class));
            }
        });
        mAddDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddDistributor.class));
            }
        });
        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ResOrder.class));
            }
        });
        mInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Inventory.class));
            }
        });
    }
}