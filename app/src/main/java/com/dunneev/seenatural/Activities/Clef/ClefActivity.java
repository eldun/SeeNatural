package com.dunneev.seenatural.Activities.Clef;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;

import com.dunneev.seenatural.Activities.Difficulty.DifficultyActivity;
import com.dunneev.seenatural.R;

public class ClefActivity extends AppCompatActivity {

    public static final String EXTRA_SELECTED_CLEF =
            "com.dunneev.seenatural.ClefActivity.extra.SELECTED_CLEF";

    private static final String LOG_TAG = ClefActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // TODO: Add a setting to select light or dark mode for all screens

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clef);
    }

    public void launchDifficultyActivity(View view) {
        Intent intent = new Intent(this, DifficultyActivity.class);
        String selectedClef = ((Button)view).getText().toString();
        intent.putExtra(EXTRA_SELECTED_CLEF, selectedClef);
        startActivity (intent);
    }
}
