package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.Adapters.CustomSwipeAdapter;
import com.example.myapplication.Fragments.Diet;
import com.example.myapplication.Fragments.Exercises;
import com.example.myapplication.Fragments.Today;
import com.example.myapplication.Fragments.Walk;
import com.example.myapplication.Models.Task;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView bottomNav;
    Toolbar toolbar;
    TextView title, points;
    public String currentFrame = "", str_points = "";
    ImageButton profile;
    Dialog dialog;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    public int pointss = 0;
    TextView ok, msg;


    @Override
    public void onBackPressed() {
        if (currentFrame == "Today") {
            super.onBackPressed();
        } else {
            bottomNav.setSelectedItemId(R.id.item1nav);
            currentFrame = "Today";
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        title.setText("Today");
        auth = FirebaseAuth.getInstance();

        dialog = new Dialog(this);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Profile.class);
                startActivity(i);
            }
        });
        points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Fitpoints.class);
                startActivity(i);
            }
        });

        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Today()).commit();

        currentFrame = "Today";


        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getUid()).child("Tasks");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Task task = snapshot1.getValue(Task.class);
                    if (task.getDone().equals("yes")){
                        pointss++;
                    } else {
                        pointss--;
                    }
                }

                pointss*= 2;

                str_points = Integer.toString(pointss);
                if (pointss>0) {
                    String statement = "You received " + str_points + " Fit points for completing your tasks";
                    showDialog(statement);
                }

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getUid());
                ref.child("fitpoints").setValue(str_points);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void init() {
        bottomNav = findViewById(R.id.bottom_nav);
        toolbar = findViewById(R.id.main_toolbar);
        title = toolbar.findViewById(R.id.toolbar_text);
        profile = toolbar.findViewById(R.id.profile_btn);
        points = toolbar.findViewById(R.id.pts);
    }

    public void showDialog(String  statement) {

        dialog.setContentView(R.layout.dialog_layout);
        ok = dialog.findViewById(R.id.ok_txt);
        msg = dialog.findViewById(R.id.msg_txt);
        msg.setText(statement);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selected = null;
                    switch (menuItem.getItemId()){
                        case R.id.item1nav:
                            selected = new Today();
                            currentFrame = "Today";
                            break;
                        case R.id.item2nav:
                            selected = new Walk();
                            currentFrame = "Walk";
                            break;
                        case R.id.item3nav:
                            selected = new Exercises();
                            currentFrame = "Exercises";
                            break;
                        case R.id.item4nav:
                            selected = new Diet();
                            currentFrame = "Diet";
                            break;
                    }
                    title.setText(currentFrame);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selected).commit();
                    return true;
                }
            };
}