package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Inventory extends AppCompatActivity {


    RecyclerView mRecyclerView;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userId;
    OrderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        mRecyclerView=findViewById(R.id.recyclerview);
        fStore = FirebaseFirestore.getInstance();
        fAuth= FirebaseAuth.getInstance();
        userId=fAuth.getCurrentUser().getUid();

//Extract the data…
        Query query =fStore.collection("Seafood").whereEqualTo("status","res").whereEqualTo("user_Id",userId);

        FirestoreRecyclerOptions<SeafoodData> options=new FirestoreRecyclerOptions.Builder<SeafoodData>().setQuery(query,SeafoodData.class).build();
        adapter=new OrderAdapter(options);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                SeafoodData seafood =documentSnapshot.toObject(SeafoodData.class);
                String id=documentSnapshot.getId();
                Toast.makeText(Inventory.this,"Position: "+position+"ID:"+id ,Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}