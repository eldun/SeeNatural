package com.dunneev.seenatural.Fragments.Piano;

import android.graphics.Color;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dunneev.seenatural.Enums.PianoNote;

import java.net.HttpCookie;
import java.util.ArrayList;

public class PianoViewModel extends ViewModel {

    private final String LOG_TAG = this.getClass().getSimpleName();

    public static final int TOTAL_KEYS = 88;

    // todo: set piano note range based on options selected
    private MutableLiveData<PianoNote> lowestPracticeNote = new MutableLiveData<PianoNote>();
    private MutableLiveData<PianoNote> highestPracticeNote  = new MutableLiveData<PianoNote>();

    private MutableLiveData<Boolean> isSingleOctaveMode = new MutableLiveData<Boolean>();

    public int numberOfKeys;

    public ArrayList<PianoNote> pianoNotes = new ArrayList<>();
    public ArrayList<PianoNote> whitePianoNotes = new ArrayList<>();
    public ArrayList<PianoNote> blackPianoNotes = new ArrayList<>();

    // TODO: Change colors to facilitate correct/incorrect when sight-reading
    public int whiteKeyUpColor = Color.WHITE;
    public int whiteKeyDownColor = Color.GRAY;
    public int whiteKeyDownCorrectColor = Color.GREEN;
    public int whiteKeyDownIncorrectColor = Color.RED;
    public int blackKeyUpColor = Color.BLACK;
    public int blackKeyDownColor = Color.LTGRAY;
    public int blackKeyDownCorrectColor = Color.GREEN;
    public int blackKeyDownIncorrectColor = Color.RED;

    public MutableLiveData<PianoNote> getMutableLiveDataLowestPracticeNote() {
        return lowestPracticeNote;
    }
    public PianoNote getLowestPracticeNote() {
        return lowestPracticeNote.getValue();
    }
    public void setLowestPracticeNote(PianoNote lowestPracticeNote) {
        this.lowestPracticeNote.setValue(lowestPracticeNote);
    }

    public MutableLiveData<PianoNote> getMutableLiveDataHighestPracticeNote() {
        return highestPracticeNote;
    }
    public PianoNote getHighestPracticeNote() {
        return highestPracticeNote.getValue();
    }
    public void setHighestPracticeNote(PianoNote highestPracticeNote) {
        this.highestPracticeNote.setValue(highestPracticeNote);
    }

    public MutableLiveData<Boolean> getMutableLiveDataIsSingleOctaveMode() {
        return isSingleOctaveMode;
    }
    public boolean getIsSingleOctaveMode() {
        return isSingleOctaveMode.getValue();
    }
    public void setIsSingleOctaveMode(boolean isSingleOctaveMode) {
        this.isSingleOctaveMode.setValue(isSingleOctaveMode);
    }



    public void populatePianoNoteArrays() {
        numberOfKeys = PianoNote.numberOfKeysInRangeInclusive(getLowestPracticeNote(), getHighestPracticeNote());

        PianoNote note;
        for (int i = 0; i < numberOfKeys; i++) {

            note = PianoNote.valueOfAbsoluteKeyIndex(lowestPracticeNote.getValue().absoluteKeyIndex+i);

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
}
