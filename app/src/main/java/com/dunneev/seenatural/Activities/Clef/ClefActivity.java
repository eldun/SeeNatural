package com.dunneev.seenatural.Activities.Clef;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

import com.dunneev.seenatural.R;

public class ClefActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // TODO: Add a shared pref to select light or dark mode for all screens

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
