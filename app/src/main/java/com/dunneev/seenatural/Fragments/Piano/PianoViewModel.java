package com.dunneev.seenatural.Fragments.Piano;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dunneev.seenatural.Enums.PianoNote;

import java.util.ArrayList;

public class PianoViewModel extends ViewModel {

    private final String LOG_TAG = this.getClass().getSimpleName();

    public static final int TOTAL_KEYS = 88;

    // todo: set piano note range based on options selected
    public PianoNote lowestPracticeNote;
    public PianoNote highestPracticeNote;

    private int numberOfKeys;
    public boolean singleOctaveMode;

    public ArrayList<PianoNote> pianoNotes = new ArrayList<>();
    public ArrayList<PianoNote> whitePianoNotes = new ArrayList<>();
    public ArrayList<PianoNote> blackPianoNotes = new ArrayList<>();

    // TODO: Change colors to facilitate correct/incorrect when sight-reading
    public int whiteKeyUpColor;
    public int whiteKeyDownColor;
    public int whiteKeyDownCorrectColor;
    public int whiteKeyDownIncorrectColor;
    public int blackKeyUpColor;
    public int blackKeyDownColor;
    public int blackKeyDownCorrectColor;
    public int blackKeyDownIncorrectColor;

    public int getNumberOfKeys() {
        // "null check"
        if (numberOfKeys == 0){
            numberOfKeys = PianoNote.numberOfKeysInRangeInclusive(lowestPracticeNote, highestPracticeNote);
        }
        return numberOfKeys;
    }


    public PianoViewModel(){
        Log.i(LOG_TAG, "PianoViewModel Created");

        whiteKeyUpColor = Color.WHITE;
        whiteKeyDownColor = Color.GRAY;
        whiteKeyDownCorrectColor = Color.GREEN;
        whiteKeyDownIncorrectColor = Color.RED;
        blackKeyUpColor = Color.BLACK;
        blackKeyDownColor = Color.LTGRAY;
        blackKeyDownCorrectColor = Color.GREEN;
        blackKeyDownIncorrectColor = Color.RED;

    }

    public void populatePianoNoteArrays() {
        numberOfKeys = PianoNote.numberOfKeysInRangeInclusive(lowestPracticeNote, highestPracticeNote);

        PianoNote note;
        for (int i = 0; i < numberOfKeys; i++) {

            note = PianoNote.valueOfAbsoluteKeyIndex(lowestPracticeNote.absoluteKeyIndex+i);

            pianoNotes.add(note);
            if (note.isWhiteKey) {
                whitePianoNotes.add(note);
            }
            else
                blackPianoNotes.add(note);
        }
        PianoKey.whiteCount = whitePianoNotes.size();
        PianoKey.blackCount = blackPianoNotes.size();
    }

    // Do processing of data here in the ViewModel, UI management in the fragment.
    public void keyDown() {
    }

    public void keyUp() {
    }

    public void incorrectKeyPressed() {

    }

    public void correctKeyPressed() {

    }

}
