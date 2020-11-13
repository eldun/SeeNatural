package com.dunneev.seenatural.Activities.SightReading;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.dunneev.seenatural.Activities.Clef.ClefActivity;
import com.dunneev.seenatural.Activities.Difficulty.DifficultyActivity;
import com.dunneev.seenatural.R;

import java.util.ArrayList;
import java.util.Random;

public class SightReadingActivity extends AppCompatActivity implements PianoKey.PianoKeyListener {

    private static final String LOG_TAG = SightReadingActivity.class.getSimpleName();

    static {
        System.loadLibrary("native-lib");
    }

    public String getSelectedClef() {
        return selectedClef;
    }

    public String getSelectedDifficulty() {
        return selectedDifficulty;
    }

    private String selectedClef;
    private String selectedDifficulty;

    private int absoluteStartingPianoKeyIndex = 39;
    private int numberOfKeys = 12;
    private ArrayList<PianoKey> pianoKeys = null;

    private ArrayList sightReadingNotes = new ArrayList();

    private SoundPlayer soundPlayer = new SoundPlayer(absoluteStartingPianoKeyIndex, numberOfKeys);

    Random random = new Random();

    StaffView staffView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate()");

        Intent intent = getIntent();
        selectedClef = intent.getExtras().getString(ClefActivity.EXTRA_SELECTED_CLEF);
        selectedDifficulty = intent.getExtras().getString(DifficultyActivity.EXTRA_SELECTED_DIFFICULTY);

        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        setUpStaffView();
        setUpPianoView();

        soundPlayer.loadWavAssets(this.getAssets());

    }

    public void addTestButton(View view) {
        staffView.addTestButton(view);
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            // todo: randomize notes BASED ON SELECTED DIFFICULTY
//            PianoNote randomNote = generatePracticablePianoNote();
            addSightReadingNote(PianoNote.B4);
            addSightReadingNote(PianoNote.F4);
            addSightReadingNote(PianoNote.B_FLAT_4);
            addSightReadingNote(PianoNote.C_SHARP_5);
            addSightReadingNote(PianoNote.G5);


        }
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

    private void setUpStaffView() {
        staffView = findViewById(R.id.staffView);

    }


    private void setUpPianoView() {

        // Replace default XML-generated PianoView with custom PianoView
        PianoView pianoView = findViewById(R.id.pianoView);
        pianoView.setAbsoluteStartingPianoKeyIndex(absoluteStartingPianoKeyIndex);
        pianoView.setNumberOfKeys(numberOfKeys);
        pianoView.invalidate();


        pianoKeys = pianoView.getPianoKeys();
        setPianoKeyListeners();
    }

    private void setPianoKeyListeners() {
        for (PianoKey key : pianoKeys) {
            key.setPianoKeyListener(this);
        }
    }

    private PianoNote generatePracticablePianoNote() {
        int randomInt = random.nextInt(numberOfKeys);
        return pianoKeys.get(randomInt).getNote();
    }


    private void addSightReadingNote(PianoNote note) {
        Log.i(LOG_TAG, "Adding note: " + note.toString());
        sightReadingNotes.add(note);
        staffView.addNote(note);
    }

    private void removeSightReadingNote(PianoNote note) {
        sightReadingNotes.remove(0);

        staffView.removeNote(note);
    }

    private boolean isCorrectNote(PianoNote note) {
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER,0,0);

        if (note == sightReadingNotes.get(0)) {

            toast = Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }
        else {
            toast = Toast.makeText(this, "ain't it chief", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
    }

    @Override
    public void keyDown(PianoKey key) {
        Log.i(LOG_TAG, "keyDown(" + key.toString() + ")");
        PianoNote note = key.getNote();
        int relativePianoKeyIndex = note.absoluteKeyIndex - absoluteStartingPianoKeyIndex;

        soundPlayer.triggerDown(relativePianoKeyIndex);

        if(isCorrectNote(note)) {
            removeSightReadingNote(note);

            addSightReadingNote(generatePracticablePianoNote());
        }


    }



    @Override
    public void keyUp(PianoKey key) {
//        Log.i(LOG_TAG, "keyUp(" + key.toString() + ")");
//        int relativePianoKeyIndex = key.getNote().absoluteKeyIndex - absoluteStartingPianoKeyIndex;
//
//        soundPlayer.triggerUp(relativePianoKeyIndex);
    }
}
