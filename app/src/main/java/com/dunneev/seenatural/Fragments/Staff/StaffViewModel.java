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


    private int currentPracticeItemIndex = 0;
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

    public int getCurrentPracticeItemIndex() {
        if (getCurrentPracticeItem() == null){
            return 0;
        }

        else
            return getCurrentPracticeItem().index;
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

    public StaffPracticeItem getPreviousPracticeItem(){
        return getPracticeItemAt(currentPracticeItemIndex-1);
    }

    public StaffPracticeItem getNextPracticeItem() {
        return getPracticeItemAt(currentPracticeItemIndex+1);
    }

    public StaffPracticeItem getCurrentPracticeItem() {
        return getPracticeItemAt(currentPracticeItemIndex);
    }

    public StaffPracticeItem getPracticeItemAt(int index) {
        return getPracticeItemsOnStaff().get(index);
    }

    public void addChordToStaff(Collection<PianoNote> chordNotes) {
        List<StaffPracticeItem> tempPracticeItemsOnStaff = getPracticeItemsOnStaff();
        StaffPracticeItem chordItem = new StaffPracticeItem(keySignature, chordNotes, tempPracticeItemsOnStaff.size());
        tempPracticeItemsOnStaff.add(chordItem);
        practiceItemsOnStaff.setValue(tempPracticeItemsOnStaff);
    }

    // Notes are treated as one-note chords
    public void addNoteToStaff(PianoNote note) {
        List<StaffPracticeItem> tempPracticeItemsOnStaff = getPracticeItemsOnStaff();
        StaffPracticeItem noteItem = new StaffPracticeItem(keySignature, note, tempPracticeItemsOnStaff.size());

        tempPracticeItemsOnStaff.add(noteItem);
        practiceItemsOnStaff.setValue(tempPracticeItemsOnStaff);
    }

//    public void addAllPracticableNotesToStaff() {
//        setPracticeItemsOnStaff(practicableNotes);
//    }

    /**
     * Increments the current practice item index
     * (if item is complete AND is not the last practice item),
     * and returns the original StaffPracticeItem & index on which the correct note was pressed.
     * @param note Correct note pressed
     * @return Practice item
     */
    public StaffPracticeItem onCorrectNote(PianoNote note){
        StaffPracticeItem currentItem = getCurrentPracticeItem();
        currentItem.markNoteCorrect(currentItem.getExactStaffNote(note));


        if (currentItem.type == StaffPracticeItem.Type.NOTE && !isLastItem(currentItem)) {
            currentItem.isComplete = true;
            currentPracticeItemIndex++;
            return getPreviousPracticeItem();
        }

        else if (currentItem.type == StaffPracticeItem.Type.CHORD) {

            if (currentItem.isComplete &&
            !isLastItem(currentItem)) {
                currentItem.isComplete = true;
                currentPracticeItemIndex++;
                return getPreviousPracticeItem();
            }
        }

        return currentItem;

    }

    public StaffPracticeItem onIncorrectNote(PianoNote note) {
        StaffPracticeItem currentItem = getCurrentPracticeItem();

        currentItem.addIncorrectNote(note);

        return currentItem;


    }

    /**
     * Set staff note back to default colors and remove incorrect ghost notes.
     *
     * Note: the current item index is incremented when a correct key is *pressed* AND the item is *complete*.
     * This new current note will be "fresh", and marking it default will have no effect.
     * (The previous complete notes stay green)
     *
     * @param note Note Released
     * @return Current practice item
     */
    public StaffPracticeItem onKeyReleased(PianoNote note) {


        StaffPracticeItem currentItem = getCurrentPracticeItem();
        StaffPracticeItem.StaffNote staffNote = currentItem.getExactStaffNote(note);

        if (staffNote.state == StaffPracticeItem.NoteState.NEUTRAL)
            return currentItem;

        else if (staffNote.state == StaffPracticeItem.NoteState.INCORRECT)
            currentItem.removeIncorrectNote(staffNote);

        else if (staffNote.state == StaffPracticeItem.NoteState.CORRECT)
            currentItem.markNoteDefault(staffNote);

        return currentItem;
    }

    public KeySignature getKeySignature() {
        return this.keySignature;
    }

    public void setKeySignature(KeySignature keySignature) {
        this.keySignature = keySignature;
    }

    public boolean isFirstItem(StaffPracticeItem item) {
        return !getPracticeItemsOnStaff().listIterator(item.index).hasPrevious();
    }

    public boolean isLastItem(StaffPracticeItem item) {
        // Iterator starts before first element:
        /*

          b u n g i o r n o
         ^
      iterator

         */
        // So I use item index + 1
        return ! (getPracticeItemsOnStaff().listIterator(item.index + 1).hasNext());
    }


}

