package com.shyam.landoffreelancers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class UserSign extends AppCompatActivity {
    private EditText name,phone,password,cpass,email,otp;
    private Button usercont,usersubmit;
    private FirebaseAuth mAuth;
    private LinearLayout first,second;
    private String userotp,verification,pass,conpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign);
        name=findViewById(R.id.username);
        phone=findViewById(R.id.userphone);
        password=findViewById(R.id.userpass);
        cpass=findViewById(R.id.usercpass);
        usersubmit=findViewById(R.id.usersubmit);
        otp=findViewById(R.id.userotp);
        usercont=findViewById(R.id.usercont);
        email=findViewById(R.id.useremail);
        first=findViewById(R.id.userfirst);
        second=findViewById(R.id.usersecond);
        second.setVisibility(View.GONE);
        FirebaseApp.initializeApp(this);
        mAuth=FirebaseAuth.getInstance();
        usercont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass=password.getText().toString();
                conpass=cpass.getText().toString();
                if (pass.contentEquals(conpass)) {
                    verify();
                } else {
                Toast.makeText(UserSign.this, "password not matching", Toast.LENGTH_SHORT).show();
            }
            }
        });
        usersubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userotp=otp.getText().toString();
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verification,userotp);
                signin(credential);
            }
        });

    }
    private void signin(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                    int len = pass.length();
                    if (len >= 6) {
                        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                Toast.makeText(UserSign.this, "completed", Toast.LENGTH_SHORT).show();
                                FirebaseApp.initializeApp(getApplicationContext());
                                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                                String[] m=email.getText().toString().split("@");
                                databaseReference.child("users").child(m[0]).setValue("user");
                                Intent i = new Intent(UserSign.this, UserMaps.class);
                                startActivity(i);
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UserSign.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else {
                        Toast.makeText(UserSign.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }
    private void verify(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+phone.getText().toString(),60, TimeUnit.SECONDS,
                this,mcallbacks
        );
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override

        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Toast.makeText(getApplicationContext(),"verified",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            second.setVisibility(View.VISIBLE);
            first.setVisibility(View.GONE);
            verification=s;
            Toast.makeText(getApplicationContext(),"sent",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(UserSign.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
