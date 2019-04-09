package com.shyam.landoffreelancers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
private EditText cost,name,timings,phone;
private Button submit;
private Spinner spinner;
private String selected;
    ArrayAdapter<String> adapter;
private String[] profs={"nothing","Photographer","Editor","Designer"};
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
        selected=profs[0];
        adapter.notifyDataSetChanged();
        submit=findViewById(R.id.upprof);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                Lancer lancer=new Lancer(name.getText().toString(),user.getUid().toString(),cost.getText().toString(),phone.getText().toString(),user.getEmail(),"Free",timings.getText().toString(),selected);
                databaseReference.child("freeprof").child(lancer.getUid()).setValue(lancer);

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected=adapter.getItem(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
