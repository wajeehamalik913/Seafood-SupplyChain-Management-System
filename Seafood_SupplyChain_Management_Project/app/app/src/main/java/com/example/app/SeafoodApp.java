package com.example.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class SeafoodApp extends AppCompatActivity {

    TextView mName,mType,mDate,mQuality,mCost;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button mOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seafood_app);
        Intent i = getIntent();
        String id = i.getStringExtra("id");
        mName=findViewById(R.id.name);
        mType=findViewById(R.id.type);
        mDate=findViewById(R.id.date);
        mOrder=findViewById(R.id.order);
        mQuality=findViewById(R.id.quality);
        mCost=findViewById(R.id.cost);
        fAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        userId=fAuth.getCurrentUser().getUid();
        DocumentReference documentReference=fStore.collection("Seafood").document(id);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                mName.setText(documentSnapshot.getString("name"));
                mType.setText(documentSnapshot.getString("type"));
                mDate.setText(documentSnapshot.get("date").toString());
                mQuality.setText(documentSnapshot.getString("quality"));
                mCost.setText(documentSnapshot.getString("cost"));

                mOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        documentReference.update("status","dis");
                        documentReference.update("user_Id",userId);
                        startActivity(new Intent(getApplicationContext(),SeafoodApp.class));
                    }
                });
            }
        });



    }
}