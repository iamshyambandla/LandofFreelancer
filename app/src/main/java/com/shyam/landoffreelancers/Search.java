package com.shyam.landoffreelancers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Search extends AppCompatActivity {
private ListView listView;
private ArrayAdapter<String> adapter;
private String[] profs={"Photographer","Editor","Designer"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView=findViewById(R.id.list);
        adapter=new ArrayAdapter<String>(this,R.layout.item,R.id.list_item,profs);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
