package com.dunneev.seenatural.Fragments.Staff;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.Fragments.Staff.StaffPracticeItem.StaffNote;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.*;

public class StaffPracticeItemTest {

    StaffPracticeItem item;
    KeySignature keySignature = KeySignature.C_SHARP_MAJOR;
    Collection<PianoNote> notes = new ArrayList<>();


    @After
    public void tearDown() throws Exception {
        item = null;
        notes = null;
    }

    @Test
    public void StaffPracticeItem_NewNoteItemCreated_NoteAddedToItem() {
        item = new StaffPracticeItem(keySignature, PianoNote.C4, 0);

        assertThat(item.size(), equalTo(1));

    }

    @Test
    public void StaffPracticeItem_NewChordItemCreated_NoteAddedToItem() {
        item = new StaffPracticeItem(keySignature, PianoNote.C4, 0);

        assertThat(item.size(), equalTo(1));

    }

    @Test
    public void Iterator_ChordIteration_ReturnsStaffNotesDescending() {
        notes.add(PianoNote.C4);
        notes.add( PianoNote.E5);
        notes.add(PianoNote.G6);

        item = new StaffPracticeItem(keySignature, notes, 0);

        StaffNote prevNote = null;
        for (StaffNote note : item) {
            if (prevNote != null) {
                if (note.compareTo(prevNote) > 0)
                    fail(note + " was returned from the iterator before " + prevNote);
            }

            prevNote = note;
        }


    }

    @Test
    public void contains() {
    }

    @Test
    public void add() {
    }

    @Test
    public void addIncorrectNote() {
    }

    @Test
    public void markNoteCorrect() {
    }

    @Test
    public void markNoteDefault() {
    }

    @Test
    public void removeIncorrectNote() {
    }

    @Test
    public void getExactStaffNote() {
    }

    @Test
    public void testToString() {
    }
}