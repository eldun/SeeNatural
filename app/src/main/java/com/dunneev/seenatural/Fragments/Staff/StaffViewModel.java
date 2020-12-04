package com.dunneev.seenatural.Fragments.Staff;

import androidx.lifecycle.ViewModel;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;

import java.util.ArrayList;

public class StaffViewModel extends ViewModel {

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

    public ArrayList<PianoNote> getAllNotesInPracticeRange() {
            // "null check"
            if (allNotesInPracticeRange == null){
                allNotesInPracticeRange = PianoNote.NotesInRangeInclusive(lowestPracticeNote, highestPracticeNote);
            }
            return allNotesInPracticeRange;
    }
}
