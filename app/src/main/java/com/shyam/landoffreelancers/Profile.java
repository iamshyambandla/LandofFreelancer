package com.shyam.landoffreelancers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity  {
private EditText cost,name,timings,phone;
private Button submit;
private Spinner spinner;
private String selected;
    ArrayAdapter<String> adapter;
   private String[] profs={"nothing","Event Managing","Orchestra","Photography","Food catering","Fashion designing","Packers and movers"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        cost=findViewById(R.id.cost);
        name=findViewById(R.id.profname);
        timings=findViewById(R.id.timings);
        phone=findViewById(R.id.profphone);
        spinner=findViewById(R.id.profspinner);
         adapter=new ArrayAdapter<String>(this,R.layout.item,R.id.list_item,profs);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        submit=findViewById(R.id.upprof);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                Lancer lancer=new Lancer(name.getText().toString(),user.getUid().toString(),cost.getText().toString(),phone.getText().toString(),user.getEmail(),"Free",timings.getText().toString(),selected);
                databaseReference.child("freeprof").child(lancer.getUid()).setValue(lancer);
                databaseReference.child("profs").child(lancer.getUid()).setValue(selected);

            }
        });
       // get();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected=parent.getSelectedItem().toString();
                Toast.makeText(Profile.this, selected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

private void get(){
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
    databaseReference.child("freeprof").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Lancer lancer=dataSnapshot.getValue(Lancer.class);
            name.setText(lancer.getName());
            cost.setText(lancer.getCost());
            timings.setText(lancer.getTimings());
            phone.setText(lancer.getNumber());

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}
}
