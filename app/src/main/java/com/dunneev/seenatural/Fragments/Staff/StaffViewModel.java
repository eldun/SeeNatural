package com.dunneev.seenatural.Fragments.Staff;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;

import java.util.ArrayList;
import java.util.Random;

public class StaffViewModel extends ViewModel {

    Random random = new Random();

    public KeySignature selectedKeySignature;
    public boolean hideTrebleClef;
    public boolean hidetrebleClefLines;
    public boolean hideBassClef;
    public boolean hideBassClefLines;

    public PianoNote lowestPracticeNote;
    public PianoNote highestPracticeNote;
    public ArrayList<PianoNote> allNotesInPracticeRange;

    // Allow users the ability to limit which notes/accidentals are to be practiced
    public ArrayList<PianoNote> practicableNotes;


    // Was having trouble adding to mutable live data array list
    public ArrayList<PianoNote> notesOnStaff = new ArrayList<>();
    public MutableLiveData<ArrayList<PianoNote>> liveNotesOnStaff = new MutableLiveData<>();

    public MutableLiveData<ArrayList<PianoNote>> getLiveNotesOnStaff() {
        if (liveNotesOnStaff == null) {
            liveNotesOnStaff = new MutableLiveData<ArrayList<PianoNote>>();
        }
        return liveNotesOnStaff;
    }


    public ArrayList<PianoNote> getAllNotesInPracticeRange() {
        // "null check"
        if (allNotesInPracticeRange == null){
            allNotesInPracticeRange = PianoNote.NotesInRangeInclusive(lowestPracticeNote, highestPracticeNote);
        }
        return allNotesInPracticeRange;
    }

    public void addRandomNoteFromPracticableNotes() {
//        // TODO: 12/4/2020 Allow user to customize notes within range
        practicableNotes = getAllNotesInPracticeRange();

        int randomInt = random.nextInt(practicableNotes.size());
        PianoNote randomNoteInRange = PianoNote.valueOfStoredOrdinal(lowestPracticeNote.storedOrdinal + randomInt);

        notesOnStaff.add(randomNoteInRange);
        getLiveNotesOnStaff().setValue(notesOnStaff);
    }
}

