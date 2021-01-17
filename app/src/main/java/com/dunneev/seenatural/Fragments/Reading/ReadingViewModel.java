package com.dunneev.seenatural.Fragments.Reading;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.Fragments.Staff.StaffPracticeItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ReadingViewModel extends ViewModel {

    Random random = new Random();

    public boolean isSingleOctaveMode;

    private MutableLiveData<PianoNote> correctKeyPressed = new MutableLiveData<>();
    private MutableLiveData<PianoNote> incorrectKeyPressed = new MutableLiveData<>();

    private KeySignature keySignature;

    private PianoNote lowPracticeNote;
    private PianoNote highPracticeNote;

    // Allow users the ability to limit which notes/accidentals are to be practiced
    private List<PianoNote> practicableNotes = new ArrayList<>();

    private boolean generateAccidentals;
    private boolean generateFlats;
    private boolean generateSharps;
    private boolean generateNaturals;


    public MutableLiveData<PianoNote> getMutableLiveDataCorrectKeyPressed(){
        return correctKeyPressed;
    }

    public MutableLiveData<PianoNote> getMutableLiveDataIncorrectKeyPressed(){
        return incorrectKeyPressed;
    }

    public List<PianoNote> getAllNotesInStaffPracticeRangeDescending() {

        List<PianoNote> allNotesInStaffRangeDescending = PianoNote.notesInRangeInclusive(lowPracticeNote, highPracticeNote);
        Collections.reverse(allNotesInStaffRangeDescending);
        return allNotesInStaffRangeDescending;
    }

    public boolean isCorrectPress(PianoNote notePressed, List<StaffPracticeItem> itemsOnStaff, int currentItemIndex) {

        if (itemsOnStaff.size() == 0) {
            return false;
        }

        if (currentItemIndex >= itemsOnStaff.size()) {
            return false;
        }

        // todo: adapt to chords
        if (notePressed.isEquivalentTo(itemsOnStaff.get(currentItemIndex).notes.get(0), isSingleOctaveMode)) {
            return true;
        }

        return false;
//        if (itemsOnStaff.get(currentItemIndex).contains(notePressed))

    }

    public void onCorrectKeyPressed(PianoNote note) {
        correctKeyPressed.setValue(note);
    }

    public void onIncorrectKeyPressed(PianoNote note) {
        incorrectKeyPressed.setValue(note);
    }

    public void generatePracticableNoteList() {
        practicableNotes.clear();

        List<PianoNote> allNotes = getAllNotesInStaffPracticeRangeDescending();

        for (int i=0;i<allNotes.size();i++) {
            boolean isAccidentalNote = PianoNote.isAccidental(allNotes.get(i), keySignature);

            if (!generateAccidentals && isAccidentalNote) {
                continue;
            }
            if (!generateFlats && isAccidentalNote && allNotes.get(i).isFlat){
                continue;
            }
            if (!generateNaturals && isAccidentalNote && allNotes.get(i).isNatural){
                continue;
            }
            if (!generateSharps && isAccidentalNote && allNotes.get(i).isSharp){
                continue;
            }

            practicableNotes.add(allNotes.get(i));
        }

    }


    public PianoNote generateRandomNoteFromPracticableNotes(){
        int randomInt = random.nextInt(practicableNotes.size()-1);
        return practicableNotes.get(randomInt);
    }

    public void setKeySignature(KeySignature keySignature) {
        this.keySignature = keySignature;
    }

    public void setGenerateAccidentals(boolean generateAccidentals) {
        this.generateAccidentals = generateAccidentals;
    }

    public void setGenerateFlats(boolean generateFlats) {
        this.generateFlats = generateFlats;
    }

    public void setGenerateNaturals(boolean generateNaturals) {
        this.generateNaturals = generateNaturals;
    }

    public void setGenerateSharps(boolean generateSharps) {
        this.generateSharps = generateSharps;
    }

    public void setLowPracticeNote(PianoNote lowPracticeNote) {
        this.lowPracticeNote = lowPracticeNote;
    }

    public void setHighPracticeNote(PianoNote highPracticeNote) {
        this.highPracticeNote = highPracticeNote;
    }

    public void setIsSingleOctaveMode(boolean singleOctaveMode) {
        this.isSingleOctaveMode = isSingleOctaveMode;
    }
}
