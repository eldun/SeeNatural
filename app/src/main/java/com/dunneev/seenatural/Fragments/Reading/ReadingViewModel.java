package com.dunneev.seenatural.Fragments.Reading;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.Fragments.Staff.StaffPracticeItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReadingViewModel extends ViewModel {

    Random random = new Random();

    private boolean isSingleOctaveMode;

    private MutableLiveData<PianoNote> correctKeyPressed = new MutableLiveData<>();
    private MutableLiveData<PianoNote> incorrectKeyPressed = new MutableLiveData<>();

    private KeySignature keySignature;

    private PianoNote lowPracticeNote;
    private PianoNote highPracticeNote;

    // Allow users the ability to limit which notes/accidentals are to be practiced
    private final List<PianoNote> practicableNotes = new ArrayList<>();

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


    public List<PianoNote> getPracticableNotes() {
        return practicableNotes;
    }

    public List<PianoNote> getAllNotesInStaffPracticeRangeInclusive() {
        return PianoNote.notesInRangeInclusive(lowPracticeNote, highPracticeNote);
    }

    public boolean isCorrectPress(PianoNote notePressed, StaffPracticeItem currentPracticeItem) {

//        if (itemsOnStaff.size() == 0) {
//            return false;
//        }
//
//        if (currentPracticeItemIndex >= itemsOnStaff.size()) {
//            return false;
//        }
//
//        StaffPracticeItem currentPracticeItem = itemsOnStaff.get(currentPracticeItemIndex);


        if (currentPracticeItem.containsEquivalentPianoNote(notePressed, isSingleOctaveMode)) {
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


    // todo: Ensure that every note can be completed by the piano fragment (maybe add a field - List
    // of PianoNote? What does this mean for midi?)
    public void generatePracticableNoteList() {
        practicableNotes.clear();

        for (PianoNote note:getAllNotesInStaffPracticeRangeInclusive()) {
            boolean isAccidentalNote = PianoNote.isAccidental(note, keySignature);

            if (!generateAccidentals && isAccidentalNote) {
                continue;
            }
            if (!generateFlats && isAccidentalNote && note.isFlat){
                continue;
            }
            if (!generateNaturals && isAccidentalNote && note.isNatural){
                continue;
            }
            if (!generateSharps && isAccidentalNote && note.isSharp){
                continue;
            }

            practicableNotes.add(note);
        }
    }


    public PianoNote generateRandomNoteFromPracticableNotes(){
        int randomInt = random.nextInt(practicableNotes.size()-1);
        return practicableNotes.get(randomInt);
    }

    public KeySignature getKeySignature() {
        return keySignature;
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

    public void setIsSingleOctaveMode(boolean isSingleOctaveMode) {
        this.isSingleOctaveMode = isSingleOctaveMode;
    }
}
