package com.shyam.landoffreelancers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update extends AppCompatActivity {
    private Button update,status;
    DatabaseReference reference;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        update=findViewById(R.id.updateprofile);
        status=findViewById(R.id.change_status);
        Intent intent=getIntent();
        uid=intent.getStringExtra("uid");

        FirebaseApp.initializeApp(this);
        reference = FirebaseDatabase.getInstance().getReference();
      /* try {
           reference.child("freeprof").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   Lancer lancer = dataSnapshot.getValue(Lancer.class);
                   if (lancer.getStatus().contentEquals("free")) {
                       status.setText("Free");
                   } else if (lancer.getStatus().contentEquals("busy")) {
                       status.setText("Booked");
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
       }catch (Exception e){

       }*/



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Update.this,Profile.class);
                startActivity(intent);
            }
        });
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.getText().toString().toLowerCase().contentEquals("free")){

                    reference.child("freeprof").child(uid).child("status").setValue("booked");
                }else if (status.getText().toString().toLowerCase().contentEquals("free")){

                        reference.child("freeprof").child(uid).child("status").setValue("free");
                    }

            }
        });
    }
}
