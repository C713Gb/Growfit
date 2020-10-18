package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Models.User;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditProfile extends AppCompatActivity {

    EditText name, feet, inches, weight;
    TextView dob;
    Button save;
    Spinner gender;
    FirebaseAuth auth;
    DatabaseReference dref;
    DatePickerDialog datePicker;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    ImageButton back;
    List<String> listspin;
    ArrayAdapter<String> spinneradapter;
    String genders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name = findViewById(R.id.username_edit);
        feet = findViewById(R.id.feet_edit);
        inches = findViewById(R.id.inch_edit);
        dob = findViewById(R.id.dob_edit);
        save = findViewById(R.id.save_btn);
        weight = findViewById(R.id.weight_edit);
        gender = findViewById(R.id.gender_edit);
        back = findViewById(R.id.back_3);

        auth = FirebaseAuth.getInstance();

        dref = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getUid());
        listspin=new ArrayList<String>();
        listspin.add("Male");
        listspin.add("Female");
        listspin.add("Others");
        spinneradapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,listspin);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(spinneradapter);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genders=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);
                String s_name = user.getUsername();
                name.setText(s_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePicker = new DatePickerDialog(EditProfile.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
                        String date = day + "-" + (month + 1) + "-" + year;
                        dob.setText(date);
                    }
                }, year, month, dayOfMonth);
                datePicker.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (name.getText().toString() != null && name.getText().toString().length() > 0 && name.getText().toString() != "") {
                        dref.child("name").setValue(name.getText().toString().trim());
                    }

                    if (dob.getText().toString() != null && dob.getText().toString().length() > 0 && dob.getText().toString() != "") {
                        dref.child("dob").setValue(name.getText().toString().trim());
                    }

                    if (feet.getText().toString() != null && feet.getText().toString().length() > 0 && feet.getText().toString() != "") {
                        dref.child("feet").setValue(feet.getText().toString().trim());
                    }

                    if (inches.getText().toString() != null && inches.getText().toString().length() > 0 && inches.getText().toString() != "") {
                        dref.child("inches").setValue(inches.getText().toString().trim());
                    }

                    if (weight.getText().toString() != null && weight.getText().toString().length() > 0 && weight.getText().toString() != "") {
                        dref.child("weight").setValue(weight.getText().toString().trim());
                    }
                    if (genders != null && genders.length() > 0 && genders != "") {
                        dref.child("gender").setValue(genders);
                    }
                } catch (Exception e) {
                    Log.d("PROFILE", e.getMessage());
                }
            }
        });

    }
}