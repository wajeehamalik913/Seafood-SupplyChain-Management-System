package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TypeInventory extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseFirestore fStore;
    ArrayList<String> Id;
    TypeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ext_inventory);
        mRecyclerView=findViewById(R.id.recyclerview);
        fStore = FirebaseFirestore.getInstance();
        Intent ii = new Intent(this, SeafoodApp.class);



        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Id = new ArrayList<>();

        CollectionReference cf = fStore.collection("Seafood");
        cf.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    String h =documentSnapshot.get("User_Id").toString();
                    String k=documentSnapshot.get("access").toString();
                    if (k.equals("Extractor")) {

                        Id.add(h);


                    }


                }
                adapter=new TypeAdapter(Id);
                mRecyclerView.setAdapter(adapter);
            }
        });

    }

}