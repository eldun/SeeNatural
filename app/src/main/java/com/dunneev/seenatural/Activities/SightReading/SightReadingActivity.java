package com.dunneev.seenatural.Activities.SightReading;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dunneev.seenatural.Activities.Clef.ClefActivity;
import com.dunneev.seenatural.Activities.Difficulty.DifficultyActivity;
import com.dunneev.seenatural.R;

import java.util.ArrayList;

public class SightReadingActivity extends AppCompatActivity implements PianoKey.PianoKeyListener {

    private static final String LOG_TAG = SightReadingActivity.class.getSimpleName();

    static {
        System.loadLibrary("native-lib");
    }

    private ConstraintLayout constraintLayout;

    private String selectedClef;
    private String selectedDifficulty;

    private int absoluteStartingPianoKeyIndex = 39;
    private int numberOfKeys = 12;
    private ArrayList<PianoKey> pianoKeys = null;

    private SoundPlayer soundPlayer = new SoundPlayer(absoluteStartingPianoKeyIndex, numberOfKeys);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate()");
        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        setUpPianoView();

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
        Log.i(LOG_TAG, "onStart()");

        super.onStart();

        soundPlayer.setUpAudioStream();
    }

    @Override
    protected void onResume() {
        Log.i(LOG_TAG, "onResume()");

        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.i(LOG_TAG, "onStop()");

        soundPlayer.teardownAudioStream();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(LOG_TAG, "onDestroy()");

        soundPlayer.unloadWavAssets();
        super.onDestroy();
    }

    private void setUpPianoView() {
        constraintLayout = findViewById(R.id.pianoViewConstraintLayout);

        PianoView pianoView = new PianoView(this);
        pianoView.setAbsoluteStartingPianoKeyIndex(absoluteStartingPianoKeyIndex);
        pianoView.setNumberOfKeys(numberOfKeys);
        pianoView.populatePianoKeyArrays();
        pianoKeys = pianoView.getPianoKeys();
        setPianoKeyListeners();

        pianoView.addKeysToView();
        constraintLayout.addView(pianoView);
    }

    private void setPianoKeyListeners() {
        for (PianoKey key : pianoKeys) {
            key.setPianoKeyListener(this);
        }
    }



    @Override
    public void keyDown(PianoKey key) {
        Log.i(LOG_TAG, "keyDown(" + key.toString() + ")");
        int relativePianoKeyIndex = key.getNote().absoluteNotePositionIndex - absoluteStartingPianoKeyIndex;

        soundPlayer.triggerDown(relativePianoKeyIndex);
    }

    @Override
    public void keyUp(PianoKey key) {
        Log.i(LOG_TAG, "keyUp(" + key.toString() + ")");
        int relativePianoKeyIndex = key.getNote().absoluteNotePositionIndex - absoluteStartingPianoKeyIndex;

        soundPlayer.triggerUp(relativePianoKeyIndex);
    }
}
