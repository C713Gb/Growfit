package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.Adapters.TaskAdapter;
import com.example.myapplication.Models.Task;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Tasks extends AppCompatActivity {

    ImageButton back;
    Button add;
    EditText addTask;
    String s_task = "";
    private static int WELCOME_TIMEOUT = 10000;
    RecyclerView recyclerView;
    TaskAdapter taskAdapter;
    ArrayList<Task> taskArrayList;
    FirebaseAuth auth;
    DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        auth = FirebaseAuth.getInstance();

        init();

        taskArrayList = new ArrayList<>();
        taskArrayList.clear();
        
        updateTasks();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                s_task = addTask.getText().toString();
                if (s_task == "" || s_task.length() == 0) {
                    add.setVisibility(View.GONE);
                    addTask.clearFocus();
                } else {
                    add.setVisibility(View.VISIBLE);
                }
            }
        }, WELCOME_TIMEOUT);

        addTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s_task = addTask.getText().toString();
                if (s_task == "" || s_task.length() == 0) {
                    add.setVisibility(View.GONE);
                    addTask.clearFocus();
                } else {
                    add.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyTask()) {
                    addtask();
                    addTask.setText("");
                    addTask.clearFocus();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void updateTasks() {
    }

    private void addtask() {
        Toast.makeText(this, s_task, Toast.LENGTH_SHORT).show();

        
    }

    private boolean verifyTask() {
        if (addTask.getText().toString() == "" || addTask.getText().toString() == null || addTask.getText().toString().length() == 0) {
            Toast.makeText(this, "No task to add", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void init() {
        back = findViewById(R.id.back_1);
        add = findViewById(R.id.add_btn);
        addTask = findViewById(R.id.add_task);
        recyclerView = findViewById(R.id.task_recycler);
    }
}