package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddExtractor extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText mName,mEmail,mPassword;
    Button mRegBtn;
    TextView mSignInBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String AccessLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_extractor);

        mName=findViewById(R.id.user_name);
        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mRegBtn=findViewById(R.id.register);
        int id=0;
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String name=mName.getText().toString().trim();
                AccessLevel="Extractor";
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password Required");
                    return;
                }
                if(password.length()<5){
                    mPassword.setError("Password must be greater than 5 characters");
                    return;
                }

                //registration in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            Toast.makeText(AddExtractor.this,"User Created",Toast.LENGTH_SHORT).show();
                            userId=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=fStore.collection("User").document(userId);
                            Map<String,Object> user= new HashMap<>();
                            user.put("User_Id",userId);
                            user.put("name",name);
                            user.put("email",email);
                            user.put("access",AccessLevel);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onSuccess: Extractor profile created for"+userId);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),Distributor.class));
                        }
                        else{
                            Toast.makeText(AddExtractor.this,"Error"+ task.getException().getMessage() ,Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        });
    }
}