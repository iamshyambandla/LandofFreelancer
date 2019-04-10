package com.shyam.landoffreelancers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private EditText email,password;
    private Button signin;
    private TextView signup,forgot;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            FirebaseApp.initializeApp(getApplicationContext());
            final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
            String s=FirebaseAuth.getInstance().getCurrentUser().getEmail();
            String[] m=s.split("@");
            databaseReference.child("users").child(m[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {


                    if (dataSnapshot.getValue().toString().contentEquals("free")){
                        Intent intent=new Intent(MainActivity.this,Maps.class);
                        startActivity(intent);
                    }else if (dataSnapshot.getValue().toString().contentEquals("user")){
                        Intent intent=new Intent(MainActivity.this,UserMaps.class);
                        startActivity(intent);
                    }
                }catch (Exception e){

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.username);
        password=findViewById(R.id.password);
        signin=findViewById(R.id.signin);
        signup=findViewById(R.id.registertext);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Select.class);
                startActivity(intent);
            }
        });
        forgot=findViewById(R.id.forgot);

        FirebaseApp.initializeApp(this);
        mAuth=FirebaseAuth.getInstance();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent=new Intent(MainActivity.this,Maps.class);
                        startActivity(intent);
                    }
                }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Toast.makeText(MainActivity.this, "successfully signedin", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().length()>0){
                    mAuth.sendPasswordResetEmail(email.getText().toString());
                }
            }
        });
    }
}
