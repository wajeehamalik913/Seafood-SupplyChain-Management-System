package com.example.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.internal.$Gson$Preconditions;

public class Login extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mEmail,mPassword;
    ProgressBar p;
    Button mLoginBtn;
    TextView mRegBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail=findViewById(R.id.login_email);
        mPassword=findViewById(R.id.login_password);
        mLoginBtn=findViewById(R.id.login);
        mRegBtn=findViewById(R.id.register);
        p=findViewById(R.id.progress);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
                finish();
            }

        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString();
                String password=mPassword.getText().toString();

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
                p.setVisibility(View.VISIBLE);
                //Authenticating User

                fAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {


                        userId=fAuth.getCurrentUser().getUid();
                        DocumentReference docF=fStore.collection("User").document(fAuth.getCurrentUser().getUid());
                         docF.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                documentSnapshot.getData();
                                if(documentSnapshot.getString("access").equals("Restaurant")){
                                    p.setVisibility(View.GONE);
                                    Toast.makeText(Login.this,"Logged In Successfully" ,Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),Restaurant.class));
                                    finish();
                                }
                                if(documentSnapshot.getString("access").equals("Extractor")){
                                    p.setVisibility(View.GONE);
                                    Toast.makeText(Login.this,"Logged In Successfully" ,Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    finish();
                                }
                                if(documentSnapshot.getString("access").equals("Distributor")){
                                    p.setVisibility(View.GONE);
                                    Toast.makeText(Login.this,"Logged In Successfully" ,Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),Distributor.class));
                                    finish();
                                }

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        p.setVisibility(View.GONE);
                        Toast.makeText(Login.this,"Error!" ,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
