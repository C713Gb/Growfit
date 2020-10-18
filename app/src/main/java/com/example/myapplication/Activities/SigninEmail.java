package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninEmail extends AppCompatActivity {

    EditText email, pwd;
    String s_email = "", s_pwd = "";
    Button done;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_email);

        init();

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(SigninEmail.this);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_email = email.getText().toString();
                s_pwd = pwd.getText().toString();

                boolean verify = verifyDetails();
                if (verify) {
                    register(s_email, s_pwd);
                }
            }
        });
    }

    private void register(String s_email, String s_pwd) {
        progressDialog.setMessage("Signing in...");
        progressDialog.show();

        auth.signInWithEmailAndPassword(s_email, s_pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            try {

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                                        .child("Users").child(auth.getCurrentUser().getUid());

                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(SigninEmail.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        progressDialog.dismiss();
                                    }
                                });
                            } catch (Exception e) {
                                Toast.makeText(SigninEmail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            //Failed
                            progressDialog.dismiss();
                            Toast.makeText(SigninEmail.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean verifyDetails() {

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
        email = findViewById(R.id.email_txt_2);
        pwd = findViewById(R.id.pwd_txt_2);
        done = findViewById(R.id.next_btn_2);
    }
}