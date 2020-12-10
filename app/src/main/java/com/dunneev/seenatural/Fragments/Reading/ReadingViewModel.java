package com.dunneev.seenatural.Fragments.Reading;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.Fragments.Piano.PianoKey;

import java.util.ArrayList;
import java.util.Random;

public class ReadingViewModel extends ViewModel {

    public PianoNote currentNote;
    public boolean isSingleOctaveMode;
    public MutableLiveData<Boolean> correctKeyPressed = new MutableLiveData<>();

    public void incorrectKeyPressed() {
        correctKeyPressed.setValue(false);
    }

    public void correctKeyPressed() {
        correctKeyPressed.setValue(true);
    }

    public void keyDown(PianoNote note) {

        if (note.equals(currentNote, isSingleOctaveMode)){
            correctKeyPressed();
        }
        else {
            incorrectKeyPressed();
        }
    }

    public void keyUp(PianoNote note) {
        correctKeyPressed.setValue(null);
    }
}
