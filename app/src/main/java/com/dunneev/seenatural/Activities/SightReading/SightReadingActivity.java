package com.dunneev.seenatural.Activities.SightReading;

import android.content.Intent;
import android.graphics.Color;
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

    private String selectedClef;
    private boolean trebleClef;
    private boolean bassClef;

    private String selectedDifficulty;

    private int numberOfKeys = 12;
    private ArrayList<PianoKey> pianoKeys = null;

    private PianoNote lowPracticeNote;
    private PianoNote highPracticeNote;

    private ArrayList<PianoNote> practicableNotes = new ArrayList();

    private ArrayList notesOnStaff = new ArrayList();

    private SoundPlayer soundPlayer;

    Random random = new Random();

    StaffView staffView;

    public String getSelectedClef() {
        return this.selectedClef;
    }


    public void setSelectedClef(String selectedClef) {
        this.selectedClef = selectedClef;

        if (selectedClef.equals(R.string.trebleClef)) {
            trebleClef = true;
            bassClef = false;
        }
        else if (selectedClef.equals(R.string.bassClef)) {
            trebleClef = false;
            bassClef = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate()");

        Intent intent = getIntent();
        setSelectedClef(intent.getExtras().getString(ClefActivity.EXTRA_SELECTED_CLEF));
        selectedDifficulty = intent.getExtras().getString(DifficultyActivity.EXTRA_SELECTED_DIFFICULTY);

//        soundPlayer = new SoundPlayer(lowPracticeNote.absoluteKeyIndex, numberOfKeys);
        soundPlayer = new SoundPlayer(0, 12);
        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

//        setUpPianoView();

        soundPlayer.loadWavAssets(this.getAssets());

    }

    private void setPracticableNotes() {
//        if (trebleClef) {
//            lowPracticeNote = PianoNote.C4;
//        }
//        else if (bassClef) {
//            lowPracticeNote = PianoNote.G2;
//        }
//
//        highPracticeNote = PianoNote.valueOfAbsoluteKeyIndex(lowPracticeNote.absoluteKeyIndex + numberOfKeys);
//
//        for (int i=0;i<numberOfKeys;i++) {
//            practicableNotes.add(PianoNote.valueOfAbsoluteKeyIndex(lowPracticeNote.absoluteKeyIndex + i));
//        }
    }

    public void addTestButton(View view) {
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
            // todo: generate random notes based on piano keys as well staffview practice range
            // todo: randomize notes BASED ON SELECTED DIFFICULTY
            for (PianoNote note : practicableNotes) {
                addSightReadingNote(note);
            }
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
        pianoView.setStartingNote(lowPracticeNote);
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
        staffView = findViewById(R.id.staffView);
        notesOnStaff.add(note);
        staffView.addNote(note);
    }

    private void removeSightReadingNote(PianoNote note) {
        notesOnStaff.remove(0);

        staffView.removeNote(note);
    }

    private boolean isCorrectNote(PianoNote note) {
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER,0,0);

        if (note == notesOnStaff.get(0)) {

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
        int relativePianoKeyIndex = note.absoluteKeyIndex - lowPracticeNote.absoluteKeyIndex;

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
