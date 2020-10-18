package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterEmail extends AppCompatActivity {
    EditText name, email, pwd;
    String s_name = "", s_email = "", s_pwd = "";
    Button done;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);

        init();

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(RegisterEmail.this);

        DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("TEST");
        dref.setValue("Hello, World");
        Log.d("DREF", dref.toString());

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_name = name.getText().toString();
                s_email = email.getText().toString();
                s_pwd = pwd.getText().toString();

                boolean verify = verifyDetails();
                if (verify) {
                    register(s_email, s_pwd);
                }
            }
        });
    }

    private void register(final String email, String pwd) {
        progressDialog.setMessage("Signing up...");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            try {

                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                String userid = firebaseUser.getUid();

                                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("id", userid);
                                hashMap.put("username", s_name.toLowerCase().trim());
                                hashMap.put("email", email.toLowerCase().trim());

                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.d("TEST", "5");

                                        if (task.isSuccessful()) {
                                            Log.d("TEST", "6");

                                            progressDialog.dismiss();
                                            Intent i = new Intent(RegisterEmail.this, MainActivity.class);
                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(i);
                                            finish();
                                        } else {
                                            Log.d("TEST", "7");

                                            //Failed
                                            progressDialog.dismiss();
                                            Toast.makeText(RegisterEmail.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                Log.d("TEST", "8");

                                Toast.makeText(RegisterEmail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.d("TEST", "9");

                            //Failed
                            progressDialog.dismiss();
                            Toast.makeText(RegisterEmail.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }

    private boolean verifyDetails() {
        if ((s_name.length()==0) || (s_name.equals(""))) {
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
            return false;
        }

        if ((s_email.length()==0) || (s_email.equals(""))) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if ((s_pwd.length()==0) || (s_pwd.equals(""))) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (s_pwd.length()<6) {
            Toast.makeText(this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void init() {
        name = findViewById(R.id.user_txt);
        email = findViewById(R.id.email_txt);
        pwd = findViewById(R.id.pwd_txt);
        done = findViewById(R.id.next_btn_1);
    }
}