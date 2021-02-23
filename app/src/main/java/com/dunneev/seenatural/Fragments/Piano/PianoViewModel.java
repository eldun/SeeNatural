package com.dunneev.seenatural.Fragments.Piano;

import android.graphics.Color;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.Utilities.Event;

public class PianoViewModel extends ViewModel {

    private final String LOG_TAG = this.getClass().getSimpleName();

    public static final int TOTAL_KEYS = 88;

    private MutableLiveData<Boolean> correctKeyPressed = new MutableLiveData<>();


    // todo: set piano note range based on options selected
    private PianoNote lowNote;
    private PianoNote highNote;

    private boolean isSingleOctaveMode;

    private MutableLiveData<Event<PianoNote>> keyPressed = new MutableLiveData<>();
    private MutableLiveData<Event<PianoNote>> keyReleased = new MutableLiveData<>();

    // TODO: Change colors to facilitate correct/incorrect when sight-reading
    public int whiteKeyUpColor = Color.WHITE;
    public int whiteKeyDownColor = Color.GRAY;
    public int whiteKeyDownCorrectColor = Color.GREEN;
    public int whiteKeyDownIncorrectColor = Color.RED;
    public int blackKeyUpColor = Color.BLACK;
    public int blackKeyDownColor = Color.LTGRAY;
    public int blackKeyDownCorrectColor = Color.GREEN;
    public int blackKeyDownIncorrectColor = Color.RED;

    public boolean getIsSingleOctaveMode() {
        return isSingleOctaveMode;
    }
    public void setIsSingleOctaveMode(boolean isSingleOctaveMode) {
        this.isSingleOctaveMode = isSingleOctaveMode;
        if (isSingleOctaveMode) {
            this.lowNote = PianoNote.C4;
            this.highNote = PianoNote.B4;
        }
    }

    public MutableLiveData<Event<PianoNote>> getMutableLiveDataKeyPressed() {
        return keyPressed;
    }
    public PianoNote getKeyPressed() {
        return keyPressed.getValue().peekContent();
    }
    public void setKeyPressed(PianoNote note) {
        this.keyPressed.setValue(new Event<>(note));
    }

    public MutableLiveData<Event<PianoNote>> getMutableLiveDataKeyReleased() {
        return keyReleased;
    }
    public PianoNote getKeyReleased() {
        return keyReleased.getValue().peekContent();
    }
    public void setKeyReleased(PianoNote note) {
        this.keyReleased.setValue(new Event<>(note));
    }


    // Do processing of data here in the ViewModel, UI management in the fragment.
    public void keyDown(PianoNote note) {
        setKeyPressed(note);
    }

    public void keyUp(PianoNote note) {
        setKeyReleased(note);
    }

    public void setLowNote(PianoNote lowNote) {
        this.lowNote = lowNote;
    }

    public void setHighNote(PianoNote highNote) {
        this.highNote = highNote;
    }

    public PianoNote getLowNote() {
        return this.lowNote;
    }

    public PianoNote getHighNote() {
        return this.highNote;
    }
}
