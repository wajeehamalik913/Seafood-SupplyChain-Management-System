package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ExtInventory extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseFirestore fStore;
    OrderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ext_inventory);
        mRecyclerView=findViewById(R.id.recyclerview);
        fStore = FirebaseFirestore.getInstance();
        Intent ii = new Intent(this, SeafoodApp.class);
        Intent i = getIntent();

//Extract the data…
        String stuff = i.getStringExtra("store");
        Log.d("myTag", stuff);
        Query query =fStore.collection("Seafood").whereEqualTo("user_Id",stuff);

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
                Toast.makeText(ExtInventory.this,"Position: "+position+"ID: "+id ,Toast.LENGTH_SHORT).show();
                ii.putExtra("id",id);
                startActivity(ii);

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