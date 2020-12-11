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

    private MutableLiveData<Boolean> generateAccidentals = new MutableLiveData<>();
    private MutableLiveData<Boolean> generateFlats = new MutableLiveData<>();
    private MutableLiveData<Boolean> generateSharps = new MutableLiveData<>();
    private MutableLiveData<Boolean> generateNaturals = new MutableLiveData<>();

    private MutableLiveData<PianoNote> lowestStaffPracticeNote = new MutableLiveData<>();
    private MutableLiveData<PianoNote> highestStaffPracticeNote = new MutableLiveData<>();
    private ArrayList<PianoNote> allNotesInStaffPracticeRange = new ArrayList<>();

    // Allow users the ability to limit which notes/accidentals are to be practiced
    private ArrayList<PianoNote> practicableNotes = new ArrayList<>();

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

    public MutableLiveData<Boolean> getMutableLiveDataGenerateAccidentals() {
        return generateAccidentals;
    }
    public boolean getGenerateAccidentals() {
        return generateAccidentals.getValue();
    }
    public void setGenerateAccidentals(boolean generateAccidentals) {
        this.generateAccidentals.setValue(generateAccidentals);
    }
    
    public MutableLiveData<Boolean> getMutableLiveDataGenerateFlats() {
        return generateFlats;
    }
    public boolean getGenerateFlats() {
        return generateFlats.getValue();
    }
    public void setGenerateFlats(boolean generateFlats) {
        this.generateFlats.setValue(generateFlats);
    }

    public MutableLiveData<Boolean> getMutableLiveDataGenerateSharps() {
        return generateSharps;
    }
    public boolean getGenerateSharps() {
        return generateSharps.getValue();
    }
    public void setGenerateSharps(boolean generateSharps) {
        this.generateSharps.setValue(generateSharps);
    }


    public MutableLiveData<Boolean> getMutableLiveDataGenerateNaturals() {
        return generateNaturals;
    }
    public boolean getGenerateNaturals() {
        return generateNaturals.getValue();
    }
    public void setGenerateNaturals(boolean generateNaturals) {
        this.generateNaturals.setValue(generateNaturals);
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
        if (this.notesOnStaff.getValue() == null) {
            return new ArrayList<PianoNote>();
        }
        else {
            return this.notesOnStaff.getValue();
        }
    }
    public void setNotesOnStaff(ArrayList<PianoNote> notesOnStaff) {
        this.notesOnStaff.setValue(notesOnStaff);
    }


    public ArrayList<PianoNote> getAllNotesInStaffPracticeRange() {

            allNotesInStaffPracticeRange = PianoNote.NotesInRangeInclusive(getLowestStaffPracticeNote(), getHighestStaffPracticeNote());
            return allNotesInStaffPracticeRange;
    }

    public void generatePracticableNoteArray() {
        ArrayList<PianoNote> allNotes = getAllNotesInStaffPracticeRange();

        for (int i=0;i<allNotes.size();i++) {
            boolean isAccidental = PianoNote.isAccidental(allNotes.get(i), getSelectedKeySignature());

            if (!getGenerateAccidentals() && isAccidental) {
                continue;
            }
            if (!getGenerateFlats() && isAccidental && allNotes.get(i).isFlat){
                continue;
            }
            if (!getGenerateNaturals() && isAccidental && allNotes.get(i).isNatural){
                continue;
            }
            if (!getGenerateSharps() && isAccidental && allNotes.get(i).isSharp){
                continue;
            }

            practicableNotes.add(allNotes.get(i));
        }

    }

    public void addRandomNoteFromPracticableNotesToStaff() {

        ArrayList tempNotesOnStaff = new ArrayList();
        tempNotesOnStaff = getNotesOnStaff();
        tempNotesOnStaff.add(PianoNote.G4);
        setNotesOnStaff(tempNotesOnStaff);

    }

}

