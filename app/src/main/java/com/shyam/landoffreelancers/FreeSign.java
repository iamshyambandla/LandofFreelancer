package com.shyam.landoffreelancers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class FreeSign extends AppCompatActivity {
    private EditText name,phone,password,cpass,email;
    private Button freecont;
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

    }
}
