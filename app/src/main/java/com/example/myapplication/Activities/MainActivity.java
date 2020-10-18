package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
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
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView bottomNav;
    Toolbar toolbar;
    TextView title;
    public String currentFrame = "";
    ImageButton profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        title.setText("Today");
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Profile.class);
                startActivity(i);
            }
        });

        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Today()).commit();

        currentFrame = "Today";
    }

    private void init() {
        bottomNav = findViewById(R.id.bottom_nav);
        toolbar = findViewById(R.id.main_toolbar);
        title = toolbar.findViewById(R.id.toolbar_text);
        profile = toolbar.findViewById(R.id.profile_btn);
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