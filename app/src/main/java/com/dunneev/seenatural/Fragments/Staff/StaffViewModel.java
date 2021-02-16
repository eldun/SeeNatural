package com.dunneev.seenatural.Fragments.Staff;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.CustomException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class StaffViewModel extends ViewModel {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private int currentPracticeItemIndex = 0;

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
    public MutableLiveData<Boolean> isComplete = new MutableLiveData<Boolean>();

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
    public void setLowStaffNote(PianoNote lowStaffNote) throws CustomException.InvalidNoteRangeException {
        if (highStaffNote == null) {
            this.lowStaffNote = lowStaffNote;
            return;
        }

        else if (lowStaffNote.isEquivalentTo(highStaffNote, false) ||
                lowStaffNote.compareTo(highStaffNote) > 0) {
            throw new CustomException.InvalidNoteRangeException("Staff low note " + lowStaffNote + " is higher than " + highStaffNote);
        }


        this.lowStaffNote = lowStaffNote;
    }


    public PianoNote getHighStaffNote() {
        return highStaffNote;
    }
    public void setHighStaffNote(PianoNote highStaffNote) throws CustomException.InvalidNoteRangeException {
        if (lowStaffNote == null) {
            this.highStaffNote = highStaffNote;
            return;
        }

        if (highStaffNote.compareTo(lowStaffNote) < 0) {
            throw new CustomException.InvalidNoteRangeException("Staff high note " + highStaffNote + " is lower than " + lowStaffNote);
        }


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

    public MutableLiveData<Boolean> getMutableLiveDataIsComplete() {
        return isComplete;
    }
    public Boolean getIsComplete() {
            return this.isComplete.getValue();
    }
    public void setIsComplete(Boolean isComplete) {
        this.isComplete.setValue(isComplete);
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
        try {
            return getPracticeItemsOnStaff().get(index);
        }
        catch (IndexOutOfBoundsException e) {
            Log.i(LOG_TAG, String.format("StaffPracticeItem at [%d] does not exist", index));
            return null;
        }

    }


    /**
     * Create a StaffPracticeItem with the supplied notes. This method will remove any notes that lie
     * beyond the staff note range (as they wouldn't be visible to the user). If there are no notes
     * remaining to be added to the StaffPracticeItem, null is returned.
     *
     * The StaffPracticeItem deals with duplicate/equivalent notes.
     * @param itemNotes Notes to be contained in the new practice item
     * @return StaffPracticeItem created from itemNotes argument(s)
     */
    public StaffPracticeItem addItemToStaff(PianoNote... itemNotes) {

        List<PianoNote> noteList = new LinkedList<>(Arrays.asList(itemNotes));



        for (PianoNote note:noteList) {
            if (note.compareTo(lowStaffNote) < 0 || note.compareTo(highStaffNote) > 0)
                noteList.remove(note);
        }

        if (noteList.isEmpty()) {
            return null;
        }

        List<StaffPracticeItem> tempPracticeItemsOnStaff = getPracticeItemsOnStaff();
        StaffPracticeItem item = new StaffPracticeItem(keySignature, noteList, tempPracticeItemsOnStaff.size());
        tempPracticeItemsOnStaff.add(item);
        practiceItemsOnStaff.setValue(tempPracticeItemsOnStaff);
        return item;

    }


    /**
     * Increments the current practice item index
     * (if item is complete AND is not the last practice item),
     * and returns the original StaffPracticeItem & index on which the correct note was pressed.
     * @param note Correct note pressed
     * @return Practice item
     */
    public StaffPracticeItem onCorrectNote(PianoNote note){
        //todo: find an equivalent note to mark correct instead of the note pressed
        StaffPracticeItem currentItem = getCurrentPracticeItem();
        currentItem.markNoteCorrect(currentItem.getExactNoteOrEnharmonicEquivalent(note));


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


        if (currentItem == null) {
            return null;
        }

        StaffPracticeItem.StaffNote staffNote = currentItem.getExactNoteOrEnharmonicEquivalent(note);

        if (staffNote == null)
            return null;

        if (staffNote.state == StaffPracticeItem.NoteState.NEUTRAL)
            return currentItem;

        else if (staffNote.state == StaffPracticeItem.NoteState.INCORRECT)
            currentItem.removeIncorrectNote(staffNote);

        // todo: finish exercise when last note completed
        else if (staffNote.state == StaffPracticeItem.NoteState.CORRECT)
            setIsComplete(true);

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

