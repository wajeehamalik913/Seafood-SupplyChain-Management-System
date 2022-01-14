package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ResOrder extends AppCompatActivity {

    Spinner spinner;
    String[] dummy=new String[50];
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> Id;
    Button mGoBtn;
    String d;
    Bundle bundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_seafood);
        fAuth = FirebaseAuth.getInstance();
        mGoBtn=findViewById(R.id.go);
        fStore = FirebaseFirestore.getInstance();
        dummy[0] = fAuth.getCurrentUser().getUid();
        spinner = (Spinner) findViewById(R.id.spinner);
        Intent i = new Intent(this, ExtInventory.class);
        // AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        Id = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(ResOrder.this, android.R.layout.simple_spinner_dropdown_item, Id);
        spinner.setAdapter(arrayAdapter);
        // Id.add("Choose Extractor");
        CollectionReference cf = fStore.collection("User");
        cf.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    String k=documentSnapshot.get("access").toString();
                    if (k.equals("Distributor")) {
                        String h =documentSnapshot.get("User_Id").toString();
                        Id.add(h);


                    }


                }
                arrayAdapter.notifyDataSetChanged();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                d = parent.getItemAtPosition(position).toString().trim();
                Log.d("myTag", d);

            }
            public void onNothingSelected(AdapterView<?> parent) {
                // Toast.makeText(OrderSeafood.this,"Error! Select extractor" ,Toast.LENGTH_SHORT).show();
            }
        });
        mGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String id= dummy;
                i.putExtra("store",d);
                startActivity(i);
            }

        });

    }
}