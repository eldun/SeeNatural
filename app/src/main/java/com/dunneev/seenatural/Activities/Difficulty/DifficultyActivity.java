package com.dunneev.seenatural.Activities.Difficulty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dunneev.seenatural.Activities.Clef.ClefActivity;
import com.dunneev.seenatural.Activities.SightRead.SightReadingActivity;
import com.dunneev.seenatural.R;

public class DifficultyActivity extends AppCompatActivity {

    public static final String EXTRA_SELECTED_DIFFICULTY =
            "com.dunneev.seenatural.ClefActivity.extra.SELECTED_DIFFICULTY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
    }

    public void launchSightReadingActivity(View view) {
        Intent intent = getIntent();
        String selectedClef = intent.getStringExtra(ClefActivity.EXTRA_SELECTED_CLEF);


        intent = new Intent(this, SightReadingActivity.class);
        String selectedDifficulty = ((Button)view).getText().toString();
        intent.putExtra(ClefActivity.EXTRA_SELECTED_CLEF, selectedClef);
        intent.putExtra(EXTRA_SELECTED_DIFFICULTY, selectedDifficulty);
        startActivity (intent);
    }
}
