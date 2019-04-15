package com.shyam.landoffreelancers;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplasScreen extends AppCompatActivity {
    @Override
    protected void onStart() {

        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splas_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
                    FirebaseApp.initializeApp(getApplicationContext());
                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    String s = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    String[] m = s.split("@");
                    databaseReference.child("users").child(m[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {


                                if (dataSnapshot.getValue().toString().contentEquals("free")) {
                                    Intent intent = new Intent(SplasScreen.this, Maps.class);
                                    startActivity(intent);
                                    finish();
                                } else if (dataSnapshot.getValue().toString().contentEquals("user")) {
                                    Intent intent = new Intent(SplasScreen.this, UserMaps.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } catch (Exception e) {
                                Toast.makeText(SplasScreen.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    Intent intent=new Intent(SplasScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 2000);
    }
}
