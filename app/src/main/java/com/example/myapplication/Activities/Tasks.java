package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.Adapters.TaskAdapter;
import com.example.myapplication.Models.Task;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Tasks extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ImageButton back;
    Button add;
    public EditText addTask;
    String s_task = "";
    private static int WELCOME_TIMEOUT = 10000;
    RecyclerView recyclerView;
    TaskAdapter taskAdapter;
    ArrayList<Task> taskArrayList;
    FirebaseAuth auth;
    DatabaseReference dref;
    RelativeLayout relativeLayout;
    SwipeRefreshLayout refresh;
    public int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        auth = FirebaseAuth.getInstance();

        init();

        refresh.setOnRefreshListener(this);
        onRefresh();

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
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyTask()) {
                    addtask();
                    addTask.setText("");
                    addTask.clearFocus();
                    onRefresh();
                }
            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask.clearFocus();
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

        try {

            String userId = auth.getCurrentUser().getUid();
            dref = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Tasks");

            taskArrayList = new ArrayList<>();
            taskArrayList.clear();

            dref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Task task = snapshot.getValue(Task.class);
                        String name = task.getName();
                        String done = task.getDone();
                        if (done.equals("yes")){
                            status++;
                        }
                        taskArrayList.add(task);
                    }

                    taskAdapter = new TaskAdapter(taskArrayList, Tasks.this);
                    recyclerView.setAdapter(taskAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "No tasks", Toast.LENGTH_SHORT).show();
            Log.d("TASK", e.getMessage());
        }
    }

    private void addtask() {
        try {

            String userId = auth.getCurrentUser().getUid();

            dref = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Tasks");
            String key = dref.push().getKey();
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            dref.child(key).child("id").setValue(key);
            dref.child(key).child("name").setValue(s_task);
            dref.child(key).child("done").setValue("no");
            dref.child(key).child("date").setValue(currentDate);
            dref.child(key).child("dueDate").setValue(currentDate);

            Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        relativeLayout = findViewById(R.id.recycler_layout);
        refresh = findViewById(R.id.refresh_tasks);
    }

    @Override
    public void onRefresh() {
        updateTasks();
        refresh.setRefreshing(false);
    }

    public void updateLayout(final String statCheck) {
        if (statCheck != "" && statCheck.length() > 0) {
            dref = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getUid()).child("Tasks");
            dref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int k = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Task task = snapshot.getValue(Task.class);
                        if (statCheck.equals(task.getName())) {
                            String id = task.getId();
                            DatabaseReference d2ref = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid()).child("Tasks").child(id);
                            d2ref.child("done").setValue("yes");
                            k = 1;
                        } else if (statCheck.equals(task.getName() + " remove_update")) {
                            String id = task.getId();
                            DatabaseReference d2ref = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid()).child("Tasks").child(id);
                            d2ref.child("done").setValue("no");
                            k = 1;
                        }
                        if (k==1) break;

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}