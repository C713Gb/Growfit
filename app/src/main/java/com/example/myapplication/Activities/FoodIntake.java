package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;

public class FoodIntake extends AppCompatActivity {

    EditText search;
    TextView food, cal;
    Button search_btn;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_intake);

        search = findViewById(R.id.search_food);
        search_btn = findViewById(R.id.search_btn_2);
        food = findViewById(R.id.food_name);
        cal = findViewById(R.id.cal_name);
        back = findViewById(R.id.back_5);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}