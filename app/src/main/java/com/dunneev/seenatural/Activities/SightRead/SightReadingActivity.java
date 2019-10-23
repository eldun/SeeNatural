package com.dunneev.seenatural.Activities.SightRead;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dunneev.seenatural.Activities.Clef.ClefActivity;
import com.dunneev.seenatural.Activities.Difficulty.DifficultyActivity;
import com.dunneev.seenatural.R;

public class SightReadingActivity extends AppCompatActivity {

    private static final String LOG_TAG = SightReadingActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        Intent intent = getIntent();
        Log.d(LOG_TAG, "Extras:");
        Log.d(LOG_TAG, intent.getExtras().getString(ClefActivity.EXTRA_SELECTED_CLEF));
        Log.d(LOG_TAG, intent.getExtras().getString(DifficultyActivity.EXTRA_SELECTED_DIFFICULTY));
    }
}
