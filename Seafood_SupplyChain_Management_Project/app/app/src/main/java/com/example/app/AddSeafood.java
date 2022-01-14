package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddSeafood extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mName,mType,mDate,mCost,mQuantity;
    Button mSaveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seafood);
        mName=findViewById(R.id.SeafoodName);
        mType=findViewById(R.id.SeafoodType);
        mCost=findViewById(R.id.SeafoodCost);
        mQuantity=findViewById(R.id.SeafoodQuantity);
        mSaveBtn=findViewById(R.id.Save);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date current=new Date();
              //  long diff=(current.getTime()-d.getTime())/86400000;
              //  diff=Math.abs(diff);
                String Name = mName.getText().toString().trim();
                String Type = mType.getText().toString().trim();
                String Cost = mCost.getText().toString().trim();
                String Quantity = mQuantity.getText().toString().trim();

                if (TextUtils.isEmpty(Name)) {
                    mName.setError("Name Required");
                    return;
                }
                if (TextUtils.isEmpty(Type)) {
                    mType.setError("Type Required");
                    return;
                }
                if (TextUtils.isEmpty(Cost)) {
                    mCost.setError("Cost Required");
                    return;
                }
                if (TextUtils.isEmpty(Quantity)) {
                    mQuantity.setError("Quantity Required");
                    return;
                }
                String S = "ext";
                userId=fAuth.getCurrentUser().getUid();
                DocumentReference documentReference=fStore.collection("Seafood").document();
                SeafoodData sf= new SeafoodData(Name,Type,Quantity,Cost, S,current,userId);
                documentReference.set(sf).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"onSuccess: Seafood added for"+userId);
                    }
                });
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}