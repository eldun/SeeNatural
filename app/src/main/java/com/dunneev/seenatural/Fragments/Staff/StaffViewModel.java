package com.dunneev.seenatural.Fragments.Staff;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StaffViewModel extends ViewModel {


    private int currentNoteIndex = 0;
    public boolean incorrectKeyDown;
    public boolean correctKeyDown;

    private KeySignature keySignature;
    private boolean  hideKeySignature;


    private PianoNote lowStaffNote;
    private PianoNote highStaffNote;
    private List<PianoNote> allNotesInStaffRangeDescending = new ArrayList<>();



    private boolean hideTrebleClef;
    private boolean hideTrebleClefLines;
    private boolean hideBassClef;
    private boolean hideBassClefLines;

    public MutableLiveData<List<StaffPracticeItem>> practiceItemsOnStaff = new MutableLiveData<>();

    public int getCurrentNoteIndex() {
        return currentNoteIndex;
    }
    public void setCurrentNoteIndex(int currentNoteIndex) {
        this.currentNoteIndex = currentNoteIndex;
    }


    public boolean getHideKeySignature() {
        return hideKeySignature;
    }
    public void setHideKeySignature(boolean hideKeySignature) {
        this.hideKeySignature = hideKeySignature;
    }

    public boolean getHideTrebleClef() {
        return hideTrebleClef;
    }
    public void setHideTrebleClef(boolean hideTrebleClef) {
        this.hideTrebleClef = hideTrebleClef;
    }

    public boolean getHideTrebleClefLines() {
        return hideTrebleClefLines;
    }
    public void setHideTrebleClefLines(boolean hideTrebleClefLines) {
        this.hideTrebleClefLines = hideTrebleClefLines;
    }

    public boolean getHideBassClef() {
        return hideBassClef;
    }
    public void setHideBassClef(boolean hideBassClef) {
        this.hideBassClef = hideBassClef;
    }

    public boolean getHideBassClefLines() {
        return hideBassClefLines;
    }
    public void setHideBassClefLines(boolean hideBassClefLines) {
        this.hideBassClefLines = hideBassClefLines;
    }


    public PianoNote getLowStaffNote() {
        return lowStaffNote;
    }
    public void setLowStaffNote(PianoNote lowestPracticeNote) {
        this.lowStaffNote = lowestPracticeNote;
    }


    public PianoNote getHighStaffNote() {
        return highStaffNote;
    }
    public void setHighStaffNote(PianoNote highStaffNote) {
        this.highStaffNote = highStaffNote;
    }

    public MutableLiveData<List<StaffPracticeItem>> getMutableLiveDataPracticeItemsOnStaff() {
        return practiceItemsOnStaff;
    }
    public List<StaffPracticeItem> getPracticeItemsOnStaff() {
        if (this.practiceItemsOnStaff.getValue() == null) {
            return new ArrayList<>();
        }
        else {
            return this.practiceItemsOnStaff.getValue();
        }
    }
    public void setPracticeItemsOnStaff(List<StaffPracticeItem> practiceItemsOnStaff) {
        this.practiceItemsOnStaff.setValue(practiceItemsOnStaff);
    }

    public List<PianoNote> getAllNotesInStaffRangeDescending() {

            allNotesInStaffRangeDescending = PianoNote.notesInRangeInclusive(getLowStaffNote(), getHighStaffNote());
            Collections.reverse(allNotesInStaffRangeDescending);
            return allNotesInStaffRangeDescending;
    }


//    public void addRandomPracticableNoteToStaff() {
//        addNoteToStaff(generateRandomNoteFromPracticableNotes());
//    }


    public void addChordToStaff(Collection<PianoNote> chordNotes) {
        StaffPracticeItem chordItem = new StaffPracticeItem(keySignature, chordNotes);
        List<StaffPracticeItem> tempPracticeItemsOnStaff = getPracticeItemsOnStaff();
        tempPracticeItemsOnStaff.add(chordItem);
        practiceItemsOnStaff.setValue(tempPracticeItemsOnStaff);
    }

    // Notes are treated as one-note chords
    public void addNoteToStaff(PianoNote note) {
        StaffPracticeItem noteItem = new StaffPracticeItem(keySignature, note);

        List<StaffPracticeItem> tempPracticeItemsOnStaff = getPracticeItemsOnStaff();
        tempPracticeItemsOnStaff.add(noteItem);
        practiceItemsOnStaff.setValue(tempPracticeItemsOnStaff);
    }

//    public void addAllPracticableNotesToStaff() {
//        setPracticeItemsOnStaff(practicableNotes);
//    }

    public void onCorrectNote(){
        currentNoteIndex ++;
    }

    public void onIncorrectNote(PianoNote note) {

    }

    public KeySignature getKeySignature() {
        return this.keySignature;
    }

    public void setKeySignature(KeySignature keySignature) {
        this.keySignature = keySignature;
    }
}

