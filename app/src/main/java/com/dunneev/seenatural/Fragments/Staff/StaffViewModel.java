package com.dunneev.seenatural.Fragments.Staff;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StaffViewModel extends ViewModel {


    Random random = new Random();

    private int currentNoteIndex = 0;
    public boolean incorrectKeyDown;
    public boolean correctKeyDown;

    private ArrayList<PianoNote> incorrectNoteEntries = new ArrayList<>();


    private MutableLiveData<KeySignature> selectedKeySignature = new MutableLiveData<>();
    private MutableLiveData<Boolean>  hideKeySignature = new MutableLiveData<>();

    private MutableLiveData<Boolean> generateAccidentals = new MutableLiveData<>();
    private MutableLiveData<Boolean> generateFlats = new MutableLiveData<>();
    private MutableLiveData<Boolean> generateSharps = new MutableLiveData<>();
    private MutableLiveData<Boolean> generateNaturals = new MutableLiveData<>();

    private MutableLiveData<PianoNote> lowestStaffPracticeNote = new MutableLiveData<>();
    private MutableLiveData<PianoNote> highestStaffPracticeNote = new MutableLiveData<>();
    private ArrayList<PianoNote> allNotesInStaffPracticeRangeDescending = new ArrayList<>();

    // Allow users the ability to limit which notes/accidentals are to be practiced
    private ArrayList<PianoNote> practicableNotes = new ArrayList<>();

    private MutableLiveData<Boolean> hideTrebleClef = new MutableLiveData<>();
    private MutableLiveData<Boolean> hideTrebleClefLines = new MutableLiveData<>();
    private MutableLiveData<Boolean> hideBassClef = new MutableLiveData<>();
    private MutableLiveData<Boolean> hideBassClefLines = new MutableLiveData<>();

    public MutableLiveData<ArrayList<PianoNote>> notesOnStaff = new MutableLiveData<>();

    public int getCurrentNoteIndex() {
        return currentNoteIndex;
    }
    public void setCurrentNoteIndex(int currentNoteIndex) {
        this.currentNoteIndex = currentNoteIndex;
    }

    public ArrayList<PianoNote> getIncorrectNoteEntries() {
        return incorrectNoteEntries;
    }


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
    public List<PianoNote> getNotesOnStaff() {
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

//    public void populateStaffLines() {
//        staffLines.clear();
//
//        for (PianoNote note: getAllNotesInStaffPracticeRangeDescending()) {
//
//            // Staff lines are only ever "natural" (white).
//            // Whether they are sharp or flat is signified by
//            // either the key signature or a ♯/♮/♭ symbol if the note in question is an accidental.
//            if (note.isWhiteKey) {
//                staffLines.add(note);
//            }
//        }
//    }

    public ArrayList<PianoNote> getAllNotesInStaffPracticeRangeDescending() {

            allNotesInStaffPracticeRangeDescending = PianoNote.NotesInRangeInclusive(getLowestStaffPracticeNote(), getHighestStaffPracticeNote());
            Collections.reverse(allNotesInStaffPracticeRangeDescending);
            return allNotesInStaffPracticeRangeDescending;
    }

    public void generatePracticableNoteArray() {
        practicableNotes.clear();

        ArrayList<PianoNote> allNotes = getAllNotesInStaffPracticeRangeDescending();

        for (int i=0;i<allNotes.size();i++) {
            boolean isAccidentalNote = PianoNote.isAccidental(allNotes.get(i), getSelectedKeySignature());

            if (!getGenerateAccidentals() && isAccidentalNote) {
                continue;
            }
            if (!getGenerateFlats() && isAccidentalNote && allNotes.get(i).isFlat){
                continue;
            }
            if (!getGenerateNaturals() && isAccidentalNote && allNotes.get(i).isNatural){
                continue;
            }
            if (!getGenerateSharps() && isAccidentalNote && allNotes.get(i).isSharp){
                continue;
            }

            practicableNotes.add(allNotes.get(i));
        }

    }

    public void addRandomPracticableNoteToStaff() {
        addNoteToStaff(generateRandomNoteFromPracticableNotes());
    }

    public PianoNote generateRandomNoteFromPracticableNotes(){
        int randomInt = random.nextInt(practicableNotes.size()-1);
        return practicableNotes.get(randomInt);
    }

    public void addNoteToStaff(PianoNote note) {

        ArrayList tempNotesOnStaff;
        tempNotesOnStaff = (ArrayList) getNotesOnStaff();
        tempNotesOnStaff.add(note);
        notesOnStaff.setValue(tempNotesOnStaff);
    }

    public void addAllPracticableNotesToStaff() {
        setNotesOnStaff(practicableNotes);
    }

    public void onCorrectNote(){
        currentNoteIndex ++;
    }

    public void onIncorrectNote(PianoNote note) {
        incorrectNoteEntries.add(note);

    }

}

