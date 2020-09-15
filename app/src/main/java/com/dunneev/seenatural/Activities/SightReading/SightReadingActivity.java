package com.dunneev.seenatural.Activities.SightReading;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dunneev.seenatural.Activities.Clef.ClefActivity;
import com.dunneev.seenatural.Activities.Difficulty.DifficultyActivity;
import com.dunneev.seenatural.R;

public class SightReadingActivity extends AppCompatActivity implements PianoKey.PianoKeyListener {

    private String selectedClef;
    private String selectedDifficulty;
    private int startingPianoKeyIndex = 39;
    private int numberOfKeys = 1;

    private static final String LOG_TAG = SightReadingActivity.class.getSimpleName();

    private SoundPlayer soundPlayer = new SoundPlayer(startingPianoKeyIndex, numberOfKeys);

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        Intent intent = getIntent();
        selectedClef = intent.getExtras().getString(ClefActivity.EXTRA_SELECTED_CLEF);
        selectedDifficulty = intent.getExtras().getString(DifficultyActivity.EXTRA_SELECTED_DIFFICULTY);

        Log.d(LOG_TAG, "Extras:");
        Log.d(LOG_TAG, intent.getExtras().getString(ClefActivity.EXTRA_SELECTED_CLEF));
        Log.d(LOG_TAG, intent.getExtras().getString(DifficultyActivity.EXTRA_SELECTED_DIFFICULTY));

        soundPlayer.loadWavAssets(this.getAssets());

    }

    @Override
    protected void onStart() {
        super.onStart();

        soundPlayer.setUpAudioStream();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Test key "c4"
        PianoView pianoView = this.findViewById(R.id.pianoView);
        pianoView.getPianoKeys().get(0).setPianoKeyListener(this);
    }

    @Override
    protected void onStop() {
        soundPlayer.teardownAudioStream();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        soundPlayer.unloadWavAssets();
        super.onDestroy();
    }



    @Override
    public void keyDown(PianoKey key) {
        Log.i(LOG_TAG, "keyDown(" + key.toString() + ")");
        int relativePianoKeyIndex = key.getNote().absoluteNotePositionIndex - startingPianoKeyIndex;

        soundPlayer.triggerDown(relativePianoKeyIndex);
    }

    @Override
    public void keyUp(PianoKey key) {
        Log.i(LOG_TAG, "keyUp(" + key.toString() + ")");
        int relativePianoKeyIndex = key.getNote().absoluteNotePositionIndex - startingPianoKeyIndex;

        soundPlayer.triggerUp(relativePianoKeyIndex);
    }
}
