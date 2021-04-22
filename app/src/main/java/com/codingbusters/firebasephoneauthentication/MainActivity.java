package com.codingbusters.firebasephoneauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    CountryCodePicker ccp;
    EditText t1;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = findViewById(R.id.t1);
        b1 = findViewById(R.id.b1);
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(t1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,manageotp.class);
                intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
                startActivity(intent);
                finish();
            }
        });

    }
}