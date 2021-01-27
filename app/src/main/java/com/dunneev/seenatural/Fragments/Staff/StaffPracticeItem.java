package com.dunneev.seenatural.Fragments.Staff;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

public class StaffPracticeItem extends AbstractCollection<StaffPracticeItem.StaffNote> {

    private static final String LOG_TAG = StaffPracticeItem.class.getSimpleName();
    public static int correctNoteColor = Color.GREEN;
    public static int neutralNoteColor = Color.WHITE;
    public static int incorrectNoteColor = Color.RED;




    public enum Type {
        NOTE,
        CHORD
    }

    public enum NoteState {
        CORRECT,
        NEUTRAL,
        INCORRECT;
    }


    public Type type;
    public KeySignature keySignature;
    public TreeSet<StaffNote> practiceNotes = new TreeSet<>();
    public int index;
    public boolean isComplete = false;


    // Standard suggested Collection constructor
    StaffPracticeItem() {
        this.practiceNotes = new TreeSet<>();
    }



    // Standard suggested Collection constructor
    StaffPracticeItem(Collection<StaffNote> collection) {
        this.addAll(collection);
    }

    public StaffPracticeItem(KeySignature keySignature, PianoNote note, int index) {
        this.type = Type.NOTE;
        this.keySignature = keySignature;
        this.add(note);
        this.index = index;

    }

    public StaffPracticeItem(KeySignature keySignature, Collection<PianoNote> notes, int index) {

        if (notes.size()>1) {
            this.type = Type.CHORD;
        }
        else
            this.type = Type.NOTE;

        this.keySignature = keySignature;
        this.index = index;

        this.addPianoNoteCollection(notes);


    }


    @Override
    public int size() {
        return this.practiceNotes.size();
    }


    @NonNull
    @NotNull
    @Override
    public Iterator<StaffNote> iterator() {
        return this.practiceNotes.descendingIterator();
    }


    public boolean containsExactPianoNote(PianoNote note) {
        Iterator<StaffNote> noteIterator = this.iterator();
        while (noteIterator.hasNext()) {
            if (noteIterator.next().note.equals(note)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param note The note to check for - enharmonics included. (e.g. an item with C Sharp 4 would
     *             "contain" D Flat 4. Use containsExactNote for a more exclusive search.
     * @param isSingleOctaveMode Qualifier for note checking. If true, pitches are the only aspect
     *                           of a note compared. If false, the key index is what matters.
     * @return true if this contains the PianoNote specified.
     */
    public boolean containsEquivalentPianoNote(PianoNote note, boolean isSingleOctaveMode) {


        Iterator<StaffNote> noteIterator = this.iterator();
        while (noteIterator.hasNext()) {
            if (note.isEquivalentTo(noteIterator.next().note, isSingleOctaveMode)) {
                return true;
            }
        }
        return false;
    }

    private boolean add(PianoNote note) {
        return add(new StaffNote(note));
    }

    public boolean add(PianoNote... notes) {
        Collection<PianoNote> noteCollection = new ArrayList<>();
        for (PianoNote note : notes) {
            noteCollection.add(note);
        }

        return addPianoNoteCollection(noteCollection);
    }

    public boolean addPianoNoteCollection(Collection<PianoNote> pianoNotes) {

        boolean modified = false;
        for (PianoNote note : pianoNotes)
            if (add(note))
                modified = true;
        return modified;

    }

    // todo: allow equivalent notes in separate octaves depending on practice mode
    // todo: move this logic up??? Create helper class?
    @Override
    public boolean add(StaffNote staffNote) {

        PianoNote enharmonicEquivalent = staffNote.note.getEnharmonicEquivalent();


        if(!containsEquivalentPianoNote(staffNote.note, false)) {

            // Add non-accidentals first
            if (keySignature.containsNote(staffNote.note))
                return this.practiceNotes.add(staffNote);

            else if (keySignature.containsNote(enharmonicEquivalent))
                return this.practiceNotes.add(new StaffNote(enharmonicEquivalent));

            else return this.practiceNotes.add(staffNote);
        }

        return false;
    }

    public boolean addIncorrectNote(PianoNote note) {
        StaffNote staffNote = new StaffNote(note);
        staffNote.state = NoteState.INCORRECT;
        staffNote.color = incorrectNoteColor;
        return add(staffNote);
    }

    public void markNoteCorrect(StaffNote note) {

        if (note != null) {

            note.state = NoteState.CORRECT;
            note.color = correctNoteColor;

            if (areAllNotesCorrect())
                this.isComplete = true;
        }

    }

    public void markNoteDefault(StaffNote note) {

        if (note != null) {

            if (note.state == NoteState.CORRECT) {
                note.state = NoteState.NEUTRAL;
                this.isComplete = false;
                note.color = neutralNoteColor;
            }
        }
    }

    public void removeIncorrectNote(StaffNote note) {
        if (note != null) {

            if (note.state == NoteState.INCORRECT) {
                this.remove(note);
            }
        }
    }

    private boolean areAllNotesCorrect() {
        for (StaffNote staffNote : this) {
            if (staffNote.state != NoteState.CORRECT)
                return false;
        }
        return true;
    }

    /**
     * Return the StaffNote with the exact value of note. If the note doesn't exist, null is returned.
     * There are no duplicates, as the StaffNotes are stored in a Set.
     *
     * @param note
     */
    public StaffNote getExactStaffNote(PianoNote note) {
        Iterator<StaffNote> noteIterator = this.iterator();
        while (noteIterator.hasNext()) {
            StaffNote staffNote = noteIterator.next();

            if (staffNote.note.equals(note)){
                return staffNote;
            }

        }

        // Is returning null the best option????
        return null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(size());
        for (StaffNote staffNote:this) {
            stringBuilder.append(staffNote.note + " ,");
        };

        // remove comma from end of list
        stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length());
        return stringBuilder.toString();
    }

    class StaffNote implements Comparable<StaffNote> {

        private PianoNote note;
        public boolean isAccidental;

        public NoteState state = NoteState.NEUTRAL;
        private int color;


        public PianoNote getNote() {
            return note;
        }

        public int getColor() {
            return color;
        }

        public StaffNote(PianoNote note) {
            this.note = note;
            this.isAccidental = PianoNote.isAccidental(note, keySignature);
            this.color = neutralNoteColor;
        }


        @Override
        public int compareTo(StaffNote staffNote) {
            return note.compareTo(staffNote.note);
        }

        @Override
        public String toString() {
            return note + ", status = " + state.toString();
        }
    }
}