package com.shyam.landoffreelancers;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Book extends AppCompatActivity {
private TextView name,cost,distance,phone,status;
private Button call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        name=findViewById(R.id.name);
        cost=findViewById(R.id.cost);
        distance=findViewById(R.id.distance);
        phone=findViewById(R.id.phone);
        call=findViewById(R.id.call);
        status=findViewById(R.id.pstatus);
        Intent intent=getIntent();
        String key=intent.getStringExtra("key");
        final double lat=intent.getDoubleExtra("lat",0.0000);
        final double lang=intent.getDoubleExtra("lang",0.000);
        final double dlat=intent.getDoubleExtra("dlat",0.0000);
        final double dlang=intent.getDoubleExtra("dlang",0.000);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("freeprof").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Lancer profile=dataSnapshot.getValue(Lancer.class);

                cost.setText(profile.getCost());
                name.setText(profile.getName());
                phone.setText(profile.getNumber());
                status.setText(profile.getStatus());
                float[] dist=new float[2];
                Location.distanceBetween(lat,lang,dlat,dlang,dist);
                distance.setText(String.valueOf(dist[0])+"m");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
