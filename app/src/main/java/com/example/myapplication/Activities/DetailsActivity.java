package com.example.myapplication.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {
String videodesc,videoname;
TextView vidn,viddes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(DetailsActivity.this);
        videodesc=sharedPreferences.getString("videodesc","unknown");
        videoname=sharedPreferences.getString("videoname","unknown");
        vidn=findViewById(R.id.videonm);
        viddes=findViewById(R.id.viddescr);
        vidn.setText(videoname);
        viddes.setText(videodesc);
    }
}