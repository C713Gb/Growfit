package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class Authenticate2 extends AppCompatActivity {
    Button gbtn, ebtn, sbtn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate2);

        init();

        ebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Authenticate2.this, SigninEmail.class);
                startActivity(intent);
            }
        });

        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Authenticate2.this, RegisterEmail.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        gbtn = findViewById(R.id.signin_google);
        ebtn = findViewById(R.id.signin_email);
        sbtn = findViewById(R.id.signin_btn_2);
    }
}