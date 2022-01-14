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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.ObjectInputValidation;
import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText mName,mOld,mPassword;
    Button mUpBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser userId;
    String Id;
    String AccessLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mName=findViewById(R.id.user_name);
        mOld=findViewById(R.id.old);
        mPassword=findViewById(R.id.password);
        mUpBtn=findViewById(R.id.update);
        int id=0;
        fAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        final String[] email = new String[1];
        mUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String old=mOld.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String name=mName.getText().toString().trim();
                userId=FirebaseAuth.getInstance().getCurrentUser();
                Id=fAuth.getCurrentUser().getUid();
                AccessLevel="Extractor";
                DocumentReference documentReference=fStore.collection("User").document(Id);
                documentReference.addSnapshotListener(Update.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                        email[0]=documentSnapshot.getString("email").toString();
                        Log.d("myyy",email[0]);

                    }
                });
                if(!TextUtils.isEmpty(name)){

                    documentReference.update("name",name);
                    //Toast.makeText(Update.this,"Name Updated" ,Toast.LENGTH_SHORT).show();
                    UserProfileChangeRequest req= new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                    userId.updateProfile(req).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                    documentReference.update("name",name);
                    Toast.makeText(Update.this,"Name Updated" ,Toast.LENGTH_SHORT).show();
                }
                if(!TextUtils.isEmpty(old)&&!TextUtils.isEmpty(password)){
                    AuthCredential credential= EmailAuthProvider.getCredential(email[0],old);
                    userId.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            userId.updatePassword(password);
                            Toast.makeText(Update.this,"Password Updated" ,Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mOld.setError("Enter Correct Password");
                        }
                    });


                }
                if(!TextUtils.isEmpty(old)&&TextUtils.isEmpty(password)) {
                    mPassword.setError("Password Required");
                }

                if(TextUtils.isEmpty(old)&&!TextUtils.isEmpty(password)){
                    mOld.setError("Password Required");
                    return;
                }



            }
        });

    }
}