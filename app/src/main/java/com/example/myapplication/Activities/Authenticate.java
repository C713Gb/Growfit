package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Authenticate extends AppCompatActivity {

    Button gbtn, ebtn, sbtn;
    Intent intent;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null){
            startActivity(new Intent(Authenticate.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        init();

        ebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Authenticate.this, RegisterEmail.class);
                startActivity(intent);
            }
        });

        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Authenticate.this, Authenticate2.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        gbtn = findViewById(R.id.signup_google);
        ebtn = findViewById(R.id.signup_email);
        sbtn = findViewById(R.id.signin_btn);
    }
}