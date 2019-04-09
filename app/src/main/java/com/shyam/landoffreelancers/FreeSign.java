package com.shyam.landoffreelancers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class FreeSign extends AppCompatActivity {
    private EditText name,phone,password,cpass,email;
    private Button freecont;
    Spinner spinner;
    private FirebaseAuth mAuth;
    String selected;
    private String[] profs={"nothing","Photographer","Editor","Designer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_sign);
        name=findViewById(R.id.freename);
        phone=findViewById(R.id.freephone);
        password=findViewById(R.id.freepass);
        cpass=findViewById(R.id.freecpass);
        freecont=findViewById(R.id.freecont);
        email=findViewById(R.id.freemail);
        spinner=findViewById(R.id.freespin);
        FirebaseApp.initializeApp(this);
        mAuth=FirebaseAuth.getInstance();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.item,R.id.list_item,profs);
        spinner.setAdapter(adapter);
        freecont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });

    }
    private void verify(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+phone.getText().toString(),60, TimeUnit.SECONDS,
                this,mcallbacks
        );
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selected=parent.getSelectedItem().toString();
                Toast.makeText(FreeSign.this, selected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override

        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Toast.makeText(getApplicationContext(),"verified",Toast.LENGTH_SHORT).show();
            if (password.getText().toString().contentEquals(cpass.getText().toString())) {
                int len = password.getText().length();
                if (len >= 6) {
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FreeSign.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(FreeSign.this, "completed", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(FreeSign.this, MapActivity.class);
                                    startActivity(i);
                                }
                            });


                        }
                    });

                }else {
                    Toast.makeText(FreeSign.this, "Invalid password", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(FreeSign.this, "password not matching", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Toast.makeText(getApplicationContext(),"sent",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(FreeSign.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
