package com.dunneev.seenatural.Fragments.Piano;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dunneev.seenatural.Enums.PianoNote;

public class PianoViewModel extends ViewModel {

    public static final int TOTAL_KEYS = 88;

    // todo: set piano note range based on options selected
    private PianoNote lowPracticeNote;
    private PianoNote highPracticeNote;
    private boolean singleOctaveMode;

    public PianoNote getLowPracticeNote() {
        return lowPracticeNote;
    }

    public void setLowPracticeNote(PianoNote lowPracticeNote) {
        this.lowPracticeNote = lowPracticeNote;
    }

    public PianoNote getHighPracticeNote() {
        return highPracticeNote;
    }

    public void setHighPracticeNote(PianoNote highPracticeNote) {
        this.highPracticeNote = highPracticeNote;
    }

    public boolean isSingleOctaveMode() {
        return singleOctaveMode;
    }

    public void setSingleOctaveMode(boolean singleOctaveMode) {
        this.singleOctaveMode = singleOctaveMode;
    }


    //    private final MutableLiveData<User> pianoLiveData = new MutableLiveData<>();
//
//    public LiveData<User> getUser() {
//        return userLiveData;
//    }

//    public UserModel() {
//        // trigger user load.
//    }
//
//    void doAction() {
//        // depending on the action, do necessary business logic calls and update the
//        // userLiveData.
//    }
}
