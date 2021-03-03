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
import java.util.Set;
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
    public Set<PianoNote> ledgerLineNotes = new TreeSet<>();

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
        return add(new StaffNote(note, keySignature));
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
            boolean result;

            // Add non-accidentals first
            if (keySignature.containsNote(staffNote.note))
                result = this.practiceNotes.add(staffNote);

                // Add enharmonic if it is in key
            else if (keySignature.containsNote(enharmonicEquivalent))
                result = this.practiceNotes.add(new StaffNote(enharmonicEquivalent, staffNote, keySignature));

                // Add note
            else {
                result = this.practiceNotes.add(staffNote);
            }

            updateLedgerLinesForPracticeItem();

            return result;
        }

        return false;
    }


    //todo: Add ledger lines if one of the staves is hidden e.g. B3 for treble staff when bass is hidden
    private void updateLedgerLinesForPracticeItem() {

        ledgerLineNotes.clear();
        PianoNote note;

        for (StaffNote staffNote:practiceNotes) {
            note = staffNote.getNote();


            // Note is higher than first ledger line above treble staff
            if (note.compareTo(PianoNote.B4) >= 0) {
                for (PianoNote noteInRange : PianoNote.notesInRangeInclusive(PianoNote.B4, note)) {
                    if (noteInRange.isWhiteKey &&
                            (noteInRange.equals(PianoNote.A5) ||
                                    noteInRange.equals(PianoNote.C6) ||
                                    noteInRange.equals(PianoNote.E6) ||
                                    noteInRange.equals(PianoNote.G6) ||
                                    noteInRange.equals(PianoNote.B6) ||
                                    noteInRange.equals(PianoNote.D7) ||
                                    noteInRange.equals(PianoNote.F7) ||
                                    noteInRange.equals(PianoNote.A7) ||
                                    noteInRange.equals(PianoNote.C8))) {
                        ledgerLineNotes.add(noteInRange);

                    }
                }
            }

            // Middle C
            if (note.equals(PianoNote.C4)) {
                ledgerLineNotes.add(PianoNote.C4);
            }

            // Note is lower than first ledger line below bass clef
            if (note.compareTo(PianoNote.E2) <= 0){
                for (PianoNote noteInRange : PianoNote.notesInRangeInclusive(note, PianoNote.E2)) {
                    if (noteInRange.isWhiteKey &&
                            (noteInRange.equals(PianoNote.E2) ||
                            noteInRange.equals(PianoNote.C2) ||
                            noteInRange.equals(PianoNote.A1) ||
                            noteInRange.equals(PianoNote.F1) ||
                            noteInRange.equals(PianoNote.D1) ||
                            noteInRange.equals(PianoNote.B0))){
                        ledgerLineNotes.add(noteInRange);
                    }
                }
            }


            // No additional space between clefs right now
            // todo: add space between clefs



        }

    }

    public boolean addIncorrectNote(PianoNote note) {
        StaffNote staffNote = new StaffNote(note, keySignature);
        staffNote.state = StaffNote.State.INCORRECT;
        staffNote.color = incorrectNoteColor;
        return add(staffNote);
    }

    public void markNoteCorrect(StaffNote note) {

        if (note != null) {

            note.state = StaffNote.State.CORRECT;
            note.color = correctNoteColor;

            if (areAllNotesCorrect())
                this.isComplete = true;
        }

    }

    public void markNoteDefault(StaffNote note) {

        if (note != null) {

            if (note.state == StaffNote.State.CORRECT) {
                note.state = StaffNote.State.NEUTRAL;
                this.isComplete = false;
                note.color = neutralNoteColor;
            }
        }
    }

    public void removeIncorrectNote(StaffNote note) {
        if (note != null) {

            if (note.state == StaffNote.State.INCORRECT) {
                this.remove(note);
                updateLedgerLinesForPracticeItem();
            }
        }
    }

    private boolean areAllNotesCorrect() {
        for (StaffNote staffNote : this) {
            if (staffNote.state != StaffNote.State.CORRECT)
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

    public StaffNote getExactNoteOrEnharmonicEquivalent(PianoNote note) {
        StaffNote result = getExactStaffNote(note);

        if(result == null) {
            result = getExactStaffNote(note.getEnharmonicEquivalent());
        }

        if (result == null) {
            return null;
        }

        return result;
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

    static class StaffNote implements Comparable<StaffNote> {

        public enum State {
            CORRECT,
            NEUTRAL,
            INCORRECT;
        }

        public enum GuidingLedgerLine {
            NONE,
            THROUGH,
            TANGENTIAL
        }

        private PianoNote note;

        // I was originally just using the PracticeItem keySignature, but I might want to create
        // one-off notes in tutorials or something.
        private KeySignature keySignature;
        public boolean isAccidental;
        public GuidingLedgerLine gudingLedgerLine;


        public State state = State.NEUTRAL;
        private int color;


        public PianoNote getNote() {
            return note;
        }

        public int getColor() {
            return color;
        }

        public StaffNote(PianoNote note, KeySignature keySignature) {
            this.note = note;
            this.isAccidental = PianoNote.isAccidental(note, keySignature);
            this.color = neutralNoteColor;
//            this.gudingLedgerLine = determineLedgerLinePosition(note);
            this.keySignature = keySignature;
        }


        /**
         * Construct a note with the properties of another. This is really only applicable to enharmonic notes
         * e.g. A note is added to an item that is not in the current key, but its enharmonic is.
         *
         * @param note
         * @param baseNote
         */
        public StaffNote(PianoNote note, StaffNote baseNote, KeySignature keySignature) {
            this.note = note;
            this.isAccidental = PianoNote.isAccidental(note, keySignature);
            this.color = baseNote.color;
            this.state = baseNote.state;
//            this.gudingLedgerLine = determineLedgerLinePosition(note);
            this.keySignature = keySignature;
        }

//        /**
//         * Determine the ledger line for the note. This will be used in StaffPracticeItemView.StaffNote,
//         * and will be highlighted along with the note (default, correct, incorrect),
//         * as opposed to the ledger lines in this outer class (StaffPracticeItem).
//         *
//         * @param note
//         * @return GuidingLedgerLine enum
//         */
//        public static GuidingLedgerLine determineLedgerLinePosition(PianoNote note){
//
//            if (// Above treble staff or below bass staff
//                    (note.compareTo(PianoNote.G_SHARP_5)>0 || note.compareTo(PianoNote.F2)<0) ||
//                            // in between bass and treble staff
//                            (note.compareTo(PianoNote.D_FLAT_4) < 0 && note.compareTo(PianoNote.B3) > 0)) {
//
//                if (note.pitch.equals(PianoNote.A4.pitch) || note.pitch.equals(PianoNote.A_FLAT_4.pitch) || note.pitch.equals(PianoNote.A_SHARP_4.pitch) ||
//                        note.pitch.equals(PianoNote.C4.pitch) || note.pitch.equals(PianoNote.C_SHARP_4.pitch) ||
//                        note.pitch.equals(PianoNote.E4.pitch) || note.pitch.equals(PianoNote.E_FLAT_4.pitch)){
//                    return GuidingLedgerLine.THROUGH;
//                }
//
//                if (note.pitch.equals(PianoNote.B4.pitch) || note.pitch.equals(PianoNote.B_FLAT_4.pitch) ||
//                        note.pitch.equals(PianoNote.D4.pitch) || note.pitch.equals(PianoNote.D_FLAT_4.pitch) || note.pitch.equals(PianoNote.D_SHARP_4.pitch) ||
//                        note.pitch.equals(PianoNote.F4.pitch) || note.pitch.equals(PianoNote.F_SHARP_4.pitch) ||
//                        note.pitch.equals(PianoNote.G4.pitch) || note.pitch.equals(PianoNote.G_FLAT_4.pitch) || note.pitch.equals(PianoNote.G_SHARP_4.pitch)){
//                    return GuidingLedgerLine.TANGENTIAL;
//                }
//
//            }
//
//            return GuidingLedgerLine.NONE;
//        }

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