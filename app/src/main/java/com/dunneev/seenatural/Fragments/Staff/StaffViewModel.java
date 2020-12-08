package com.dunneev.seenatural.Fragments.Staff;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;

import java.util.ArrayList;
import java.util.Random;

public class StaffViewModel extends ViewModel {

    Random random = new Random();


    private MutableLiveData<KeySignature> selectedKeySignature = new MutableLiveData<>();
    private MutableLiveData<Boolean>  hideKeySignature = new MutableLiveData<>();

    private MutableLiveData<Boolean>  displayFlats = new MutableLiveData<>();
    private MutableLiveData<Boolean>  displaySharps = new MutableLiveData<>();
    private MutableLiveData<Boolean>  displayNaturals = new MutableLiveData<>();

    private MutableLiveData<PianoNote> lowestStaffPracticeNote = new MutableLiveData<>();
    private MutableLiveData<PianoNote> highestStaffPracticeNote = new MutableLiveData<>();
    private ArrayList<PianoNote> allNotesInStaffPracticeRange = new ArrayList<>();

    // Allow users the ability to limit which notes/accidentals are to be practiced
    private MutableLiveData<ArrayList<PianoNote>> practicableNotes = new MutableLiveData<>();

    private MutableLiveData<Boolean> hideTrebleClef = new MutableLiveData<>();
    private MutableLiveData<Boolean> hideTrebleClefLines = new MutableLiveData<>();
    private MutableLiveData<Boolean> hideBassClef = new MutableLiveData<>();
    private MutableLiveData<Boolean> hideBassClefLines = new MutableLiveData<>();

    public MutableLiveData<ArrayList<PianoNote>> notesOnStaff = new MutableLiveData<>();


    public MutableLiveData<Boolean> getMutableLiveDataHideKeySignature() {
        return hideKeySignature;
    }
    public boolean getHideKeySignature() {
        return hideKeySignature.getValue();
    }
    public void setHideKeySignature(boolean hideKeySignature) {
        this.hideKeySignature.setValue(hideKeySignature);
    }

    public MutableLiveData<Boolean> getMutableLiveDataDisplayFlats() {
        return displayFlats;
    }
    public boolean getDisplayFlats() {
        return displayFlats.getValue();
    }
    public void setDisplayFlats(boolean displayFlats) {
        this.displayFlats.setValue(displayFlats);
    }

    public MutableLiveData<Boolean> getMutableLiveDataDisplaySharps() {
        return displaySharps;
    }
    public boolean getDisplaySharps() {
        return displaySharps.getValue();
    }
    public void setDisplaySharps(boolean displaySharps) {
        this.displaySharps.setValue(displaySharps);
    }


    public MutableLiveData<Boolean> getMutableLiveDataDisplayNaturals() {
        return displayNaturals;
    }
    public boolean getDisplayNaturals() {
        return displayNaturals.getValue();
    }
    public void setDisplayNaturals(boolean displayNaturals) {
        this.displayNaturals.setValue(displayNaturals);
    }

    public MutableLiveData<Boolean> getMutableLiveDataHideTrebleClef() {
        return hideTrebleClef;
    }
    public boolean getHideTrebleClef() {
        return hideTrebleClef.getValue();
    }
    public void setHideTrebleClef(boolean hideTrebleClef) {
        this.hideTrebleClef.setValue(hideTrebleClef);
    }

    public MutableLiveData<Boolean> getMutableLiveDataHideTrebleClefLines() {
        return hideTrebleClefLines;
    }
    public boolean getHideTrebleClefLines() {
        return hideTrebleClefLines.getValue();
    }
    public void setHideTrebleClefLines(boolean hideTrebleClefLines) {
        this.hideTrebleClefLines.setValue(hideTrebleClefLines);
    }

    public MutableLiveData<Boolean> getMutableLiveDataHideBassClef() {
        return hideBassClef;
    }
    public boolean getHideBassClef() {
        return hideBassClef.getValue();
    }
    public void setHideBassClef(boolean hideBassClef) {
        this.hideBassClef.setValue(hideBassClef);
    }

    public MutableLiveData<Boolean> getMutableLiveDataHideBassClefLines() {
        return hideBassClefLines;
    }
    public boolean getHideBassClefLines() {
        return hideBassClefLines.getValue();
    }
    public void setHideBassClefLines(boolean hideBassClefLines) {
        this.hideBassClefLines.setValue(hideBassClefLines);
    }

    public MutableLiveData<KeySignature> getMutableLiveDataKeySignature() {
        return selectedKeySignature;
    }
    public KeySignature getSelectedKeySignature() {
        return selectedKeySignature.getValue();
    }
    public void setSelectedKeySignature(KeySignature selectedKeySignature) {
        this.selectedKeySignature.setValue(selectedKeySignature);
    }

    public MutableLiveData<PianoNote> getMutableLiveDataLowestStaffPracticeNote() {
        return lowestStaffPracticeNote;
    }
    public PianoNote getLowestStaffPracticeNote() {
        return lowestStaffPracticeNote.getValue();
    }
    public void setLowestStaffPracticeNote(PianoNote lowestPracticeNote) {
        this.lowestStaffPracticeNote.setValue(lowestPracticeNote);
    }

    public MutableLiveData<PianoNote> getMutableLiveDataHighestStaffPracticeNote() {
        return highestStaffPracticeNote;
    }
    public PianoNote getHighestStaffPracticeNote() {
        return highestStaffPracticeNote.getValue();
    }
    public void setHighestStaffPracticeNote(PianoNote highestPracticeNote) {
        this.highestStaffPracticeNote.setValue(highestPracticeNote);
    }

    public MutableLiveData<ArrayList<PianoNote>> getMutableLiveDataNotesOnStaff() {
        return notesOnStaff;
    }
    public ArrayList<PianoNote> getNotesOnStaff() {
        return this.notesOnStaff.getValue();
    }
    public void setNotesOnStaff(ArrayList<PianoNote> notesOnStaff) {
        this.notesOnStaff.setValue(notesOnStaff);
    }


    public ArrayList<PianoNote> getAllNotesInStaffPracticeRange() {

            allNotesInStaffPracticeRange = PianoNote.NotesInRangeInclusive(getLowestStaffPracticeNote(), getHighestStaffPracticeNote());
            return allNotesInStaffPracticeRange;
    }

    public void addRandomNoteFromPracticableNotes() {

    }
}

