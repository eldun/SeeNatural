package com.dunneev.seenatural.Fragments.Staff;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.Fragments.Staff.StaffPracticeItem.StaffNote;
import com.dunneev.seenatural.Fragments.Staff.StaffPracticeItem.Type;

import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static com.dunneev.seenatural.Enums.KeySignature.*;
import static com.dunneev.seenatural.Enums.PianoNote.*;
import static com.dunneev.seenatural.Fragments.Staff.StaffPracticeItem.NoteState.*;
import static com.dunneev.seenatural.Fragments.Staff.StaffPracticeItem.Type.*;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.*;

public class StaffPracticeItemTest {

    StaffPracticeItem item;
    KeySignature keySignature = C_SHARP_MAJOR;
    Collection<PianoNote> noteCollection = new ArrayList<>();


    @After
    public void tearDown() throws Exception {
        item = null;
        keySignature = null;
        noteCollection = null;
    }

    @Test
    public void StaffPracticeItem_NewNoteItemCreated_NoteAddedToItem() {
        item = new StaffPracticeItem(keySignature, C4, 0);

        assertThat(item.type, is(equalTo(NOTE)));
        assertThat(item.index, is(equalTo(0)));
        assertThat(item.size(), equalTo(1));

    }

    @Test
    public void StaffPracticeItem_NewChordItemCreated_NotesAddedToItem() {
        populateNoteCollectionFromNotes(C3, C5, C6);
        item = new StaffPracticeItem(keySignature, noteCollection, 0);

        assertThat(item.type, is(equalTo(CHORD)));
        assertThat(item.index, is(equalTo(0)));
        assertThat(item.size(), equalTo(3));

    }

    @Test
    public void StaffPracticeItem_NewChordItemWithDuplicateNotes_IgnoresDuplicates() {
        populateNoteCollectionFromNotes(C3, C3, C6);
        item = new StaffPracticeItem(keySignature, noteCollection, 0);

        assertThat(item.size(), is(equalTo(2)));
    }

    @Test
    public void StaffPracticeItem_NewChordItemWithEnharmonicNotes_IgnoresEnharmonicNotes() {
        populateNoteCollectionFromNotes(D_FLAT_4, C_SHARP_4, C6);
        item = createChordItem(noteCollection);

        assertThat(item.size(), is(equalTo(2)));
    }

    @Test
    public void StaffPracticeItem_NewChordItemWithEnharmonicNotes_OnlyIncludesNoteMatchingKeySignature() {
        keySignature = C_SHARP_MAJOR;
        populateNoteCollectionFromNotes(D_FLAT_4, C_SHARP_4, C6);
        item = createChordItem(noteCollection);

        assertThat(item.size(), is(equalTo(2)));
        assertTrue(item.containsExactPianoNote(C_SHARP_4));
        assertFalse(item.containsExactPianoNote(D_FLAT_4));

    }

    @Test
    public void StaffPracticeItem_NewChordItemWithOneNote_CreatesNoteItem() {
        populateNoteCollectionFromNotes(D_FLAT_4);
        item = createChordItem(noteCollection);

        assertThat(item.size(), is(equalTo(1)));
        assertThat(item.type, is(equalTo(NOTE)));

    }

    @Test
    public void Iterator_ChordItemIteration_ReturnsStaffNotesDescending() {

        populateNoteCollectionFromNotes(C4, E5, G6);

        item = new StaffPracticeItem(keySignature, noteCollection, 0);

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
    public void Contains_NoteItemCreated_ReturnsCorrectly() {
        item = createNoteItem(C_SHARP_4);

        // Exact contains(o) assertions
        assertTrue(item.containsExactPianoNote(C_SHARP_4));
        assertFalse(item.containsExactPianoNote(D_FLAT_4));
        assertFalse(item.containsExactPianoNote(C_SHARP_1));
        assertFalse(item.containsExactPianoNote(D_FLAT_6));


        // Single Octave enabled assertions
        assertTrue(item.containsEquivalentPianoNote(C_SHARP_4, true));
        assertTrue(item.containsEquivalentPianoNote(D_FLAT_4, true));
        assertTrue(item.containsEquivalentPianoNote(C_SHARP_1, true));
        assertTrue(item.containsEquivalentPianoNote(C_SHARP_7, true));
        assertTrue(item.containsEquivalentPianoNote(D_FLAT_2, true));
        assertTrue(item.containsEquivalentPianoNote(D_FLAT_7, true));

        assertFalse(item.containsEquivalentPianoNote(D2, true));
        assertFalse(item.containsEquivalentPianoNote(F_SHARP_4, true));

        // Single Octave disabled assertions
        assertTrue(item.containsEquivalentPianoNote(C_SHARP_4, false));
        assertTrue(item.containsEquivalentPianoNote(C_SHARP_4, false));





        assertTrue(item.containsEquivalentPianoNote(C_SHARP_4, false));
        assertFalse(item.containsEquivalentPianoNote(C_SHARP_3, false));

    }

    @Test
    public void Contains_ChordItemCreated_ReturnsCorrectly() {
        populateNoteCollectionFromNotes(C_SHARP_4, E_FLAT_4, F5);
        item = createChordItem(noteCollection);

        assertThat(item.size(), is(equalTo(3)));

        // Exact contains(o) assertions
        assertTrue(item.containsExactPianoNote(C_SHARP_4));
        assertFalse(item.containsExactPianoNote(D_FLAT_4));
        assertFalse(item.containsExactPianoNote(C_SHARP_1));
        assertFalse(item.containsExactPianoNote(D_FLAT_6));


        // Single Octave enabled assertions
        assertTrue(item.containsEquivalentPianoNote(C_SHARP_4, true));
        assertTrue(item.containsEquivalentPianoNote(D_FLAT_4, true));
        assertTrue(item.containsEquivalentPianoNote(C_SHARP_1, true));
        assertTrue(item.containsEquivalentPianoNote(C_SHARP_7, true));
        assertTrue(item.containsEquivalentPianoNote(D_FLAT_2, true));
        assertTrue(item.containsEquivalentPianoNote(D_FLAT_7, true));

        assertFalse(item.containsEquivalentPianoNote(D2, true));
        assertFalse(item.containsEquivalentPianoNote(F_SHARP_4, true));

        // Single Octave disabled assertions
        assertTrue(item.containsEquivalentPianoNote(C_SHARP_4, false));
        assertTrue(item.containsEquivalentPianoNote(C_SHARP_4, false));





        assertTrue(item.containsEquivalentPianoNote(C_SHARP_4, false));
        assertFalse(item.containsEquivalentPianoNote(C_SHARP_3, false));

    }


    @Test
    public void AddIncorrectNote_AddNote_InitializedProperly() {
        keySignature = C_MAJOR;
        item = createNoteItem(G5);
        item.addIncorrectNote(C4);

        assertThat(item.getExactStaffNote(C4).getColor(), is(equalTo(StaffPracticeItem.incorrectNoteColor)));
        assertThat(item.getExactStaffNote(C4).isAccidental, is(false));
        assertThat(item.getExactStaffNote(C4).state, is(INCORRECT));
    }

    @Test
    public void AddIncorrectNote_AddDuplicateNote_NotAdded() {
        populateNoteCollectionFromNotes(C3,C4,C5);
        item = createChordItem(noteCollection);
        item.addIncorrectNote(C4);

        assertThat(item.size(),is(equalTo(3)));
        assertThat(item.getExactStaffNote(C4).getColor(), is(equalTo(StaffPracticeItem.neutralNoteColor)));
        assertThat(item.getExactStaffNote(C4).state, is(NEUTRAL));
    }

    @Test
    public void MarkNoteCorrect_CalledOnDefaultNote_SetsAllRelevantProperties() {
        item = createNoteItem(C4);

        item.markNoteCorrect(item.getExactStaffNote(C4));

        StaffNote note = item.getExactStaffNote(C4);
        assertThat(note.state, is(equalTo(CORRECT)));
        assertThat(note.getColor(), is(StaffPracticeItem.correctNoteColor));

        assertThat(item.isComplete, is(true));
    }

    @Test
    public void MarkNoteCorrect_CalledOnTotallyDefaultChord_SetsRelevantProperties() {
        populateNoteCollectionFromNotes(C4,E4,G5);
        item = createChordItem(noteCollection);

        item.markNoteCorrect(item.getExactStaffNote(C4));

        assertThat(item.isComplete, is(equalTo(false)));

        assertThat(item.getExactStaffNote(C4).state, is(CORRECT));
        assertThat(item.getExactStaffNote(C4).getColor(), is(StaffPracticeItem.correctNoteColor));

        assertThat(item.getExactStaffNote(E4).state, is(NEUTRAL));
        assertThat(item.getExactStaffNote(G5).state, is(NEUTRAL));
    }

    @Test
    public void MarkNoteCorrect_CalledOnChordWithOneNoteLeft_MarksItemComplete() {
        populateNoteCollectionFromNotes(C4,E4,G5);
        item = createChordItem(noteCollection);

        assertThat(item.isComplete, is(equalTo(false)));

        item.markNoteCorrect(item.getExactStaffNote(C4));
        item.markNoteCorrect(item.getExactStaffNote(G5));

        assertThat(item.getExactStaffNote(C4).state, is(CORRECT));
        assertThat(item.getExactStaffNote(E4).state, is(NEUTRAL));
        assertThat(item.getExactStaffNote(G5).state, is(CORRECT));


        assertThat(item.isComplete, is(equalTo(false)));


        item.markNoteCorrect(item.getExactStaffNote(E4));
        assertThat(item.isComplete, is(true));

    }


    @Test
    public void MarkNoteDefault_CorrectNoteOfIncompleteChord_SetsNoteToDefault() {
        populateNoteCollectionFromNotes(C4,E4,G5);
        item = createChordItem(noteCollection);

        assertThat(item.isComplete, is(equalTo(false)));

        item.markNoteCorrect(item.getExactStaffNote(C4));
        item.markNoteCorrect(item.getExactStaffNote(G5));

        assertThat(item.getExactStaffNote(C4).state, is(CORRECT));
        assertThat(item.getExactStaffNote(E4).state, is(NEUTRAL));
        assertThat(item.getExactStaffNote(G5).state, is(CORRECT));


        item.markNoteDefault(item.getExactStaffNote(G5));

        assertThat(item.getExactStaffNote(G5).state, is(equalTo(NEUTRAL)));
        assertThat(item.getExactStaffNote(G5).getColor(), is(equalTo(StaffPracticeItem.neutralNoteColor)));
    }

    @Test
    public void RemoveIncorrectNote_IncorrectNoteInStaffPracticeItem_RemovesIncorrectNote() {
        populateNoteCollectionFromNotes(C4,E4,G5);
        item = createChordItem(noteCollection);

        item.addIncorrectNote(C_SHARP_4);

        assertThat(item.getExactStaffNote(C_SHARP_4).state, is(INCORRECT));
        assertThat(item.getExactStaffNote(C_SHARP_4).getColor(), is(StaffPracticeItem.incorrectNoteColor));

        item.removeIncorrectNote(item.getExactStaffNote(C_SHARP_4));

        assertNull(item.getExactStaffNote(C_SHARP_4));
    }

    @Test
    public void RemoveIncorrectNote_NoIncorrectNoteExists_DoesNothing() {
        populateNoteCollectionFromNotes(C4,E4,G5);
        item = createChordItem(noteCollection);

        item.removeIncorrectNote(item.getExactStaffNote(C_SHARP_4));

        assertThat(item.size(),is(equalTo(3)));

        assertTrue(item.containsExactPianoNote(C4));
        assertTrue(item.containsExactPianoNote(E4));
        assertTrue(item.containsExactPianoNote(G5));

        assertNull(item.getExactStaffNote(C_SHARP_4));
    }

    @Test
    public void GetExactStaffNote() {
        populateNoteCollectionFromNotes(C1,D2,E3,F4,G5,A6,A_FLAT_1,B_FLAT_0,C_SHARP_4,D_FLAT_7,A0,C8);

        item = createChordItem(noteCollection);

        assertNull(item.getExactStaffNote(B0));
        assertNotNull(item.getExactStaffNote(C8));
    }

    @Test
    public void StaffPracticeItem_PlayChordWithCorrectAndIncorrectNotes_DoesNotMarkItemCorrect() {
        fail("not implemented");
    }

    private void populateNoteCollectionFromNotes(PianoNote... notes) {
        for (PianoNote note : notes) {
            noteCollection.add(note);
        }
    }

    private StaffPracticeItem createNoteItem(PianoNote note) {
        return new StaffPracticeItem(keySignature, note, 0);
    }

    private StaffPracticeItem createChordItem(Collection<PianoNote> pianoNoteCollection) {
        return new StaffPracticeItem(keySignature, pianoNoteCollection, 0);
    }
}