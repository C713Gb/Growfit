package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.Adapters.CustomSwipeAdapter;
import com.example.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

public class Today extends Fragment {

    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    LinearLayout SliderDotsPanel;
    private int dotsCount;
    private ImageView[] dots;
    MainActivity ma;
    CardView task, walk, calculate;
    TextView see;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        ma = (MainActivity) getActivity();
        viewPager = view.findViewById(R.id.view_pager_1);
        adapter = new CustomSwipeAdapter(getContext());
        SliderDotsPanel = view.findViewById(R.id.Slider_Dots);
        dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];
        walk = view.findViewById(R.id.walk_card);
        calculate = view.findViewById(R.id.diet_card);
        see = view.findViewById(R.id.see_exercises);

        DisplayDots();

        viewPager.setAdapter(adapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (viewPager.getCurrentItem() == 0){
                    viewPager.setCurrentItem(1);
                }else if (viewPager.getCurrentItem() == 1){
                    viewPager.setCurrentItem(2);
                }else {
                    viewPager.setCurrentItem(0);
                }
            }
        }, 2000, 2000);

        return view;
    }

    private void DisplayDots() {


        for (int i=0; i< dotsCount; i++){
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8,0,8,0);

            SliderDotsPanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                try {

                    for (int i = 0; i < dotsCount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
                } catch (Exception e) {
                    //Exception
                    Log.d("TODAY", e.getMessage());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ma.bottomNav.setSelectedItemId(R.id.item2nav);
                ma.currentFrame = "Walk";
            }
        });

        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ma.bottomNav.setSelectedItemId(R.id.item3nav);
                ma.currentFrame = "Exercises";
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ma.bottomNav.setSelectedItemId(R.id.item4nav);
                ma.currentFrame = "Diet";
            }
        });

    }
}