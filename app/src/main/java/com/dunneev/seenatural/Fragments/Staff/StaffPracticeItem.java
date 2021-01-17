package com.dunneev.seenatural.Fragments.Staff;

import androidx.annotation.NonNull;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class StaffPracticeItem extends AbstractCollection<PianoNote> {

    private static final String LOG_TAG = StaffPracticeItem.class.getSimpleName();

    public enum Type {
        NOTE,
        CHORD
    }

    public Type type;
    public KeySignature keySignature;
    public List<PianoNote> notes = new ArrayList<>();



    // Standard suggested Collection constructor
    StaffPracticeItem() {
        this.notes = new ArrayList<>();
    }

    // Standard suggested Collection constructor
    StaffPracticeItem(Collection<PianoNote> collection) {
        this.notes = new ArrayList<PianoNote>(notes);
    }

    public StaffPracticeItem(KeySignature keySignature, PianoNote note) {
        this.type = Type.NOTE;
        this.keySignature = keySignature;
        this.notes.add(note);
    }

    public StaffPracticeItem(KeySignature keySignature, Collection<PianoNote> notes) {
        this.type = Type.CHORD;
        this.keySignature = keySignature;
        this.notes = new ArrayList<PianoNote>(notes);
    }



    @Override
    public int size() {
        return this.notes.size();
    }

    @NonNull
    @NotNull
    @Override
    public Iterator iterator() {
        return new Iterator() {

            private int current = 0;
            private List<PianoNote> notes = StaffPracticeItem.this.notes;

            @Override
            public boolean hasNext() {
                return current < notes.size();
            }

            @Override
            public Object next() {
                return  notes.get(current++);
            }
        };
    }



}