package com.dunneev.seenatural.Enums;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public enum PianoNote {
    A0("A0", 21, 0, -1, "[A0]"),
    A_SHARP_0("A♯0", 22, 1, 2, "[A♯0]-[B♭0]"),
    B_FLAT_0("B♭0", 22, 2, 1, "[A♯0]-[B♭0]"),
    B0("B0", 23, 3, -1, "[B0]"),

    C1("C1", 24, 4, -1, "[C1]"),
    C_SHARP_1("C♯1", 25, 5, 6, "[C♯1]-[D♭1]"),
    D_FLAT_1("D♭1", 25, 6, 5, "[C♯1]-[D♭1]"),
    D1("D1", 26, 7, -1, "[D1]"),
    D_SHARP_1("D♯1", 27, 8, 9, "[D♯1]-[E♭1]"),
    E_FLAT_1("E♭1", 27, 9, 8, "[D♯1]-[E♭1]"),
    E1("E1", 28, 10, -1, "[E1]"),
    F1("F1", 29, 11, -1, "[F1]"),
    F_SHARP_1("F♯1", 30, 12, 13, "[F♯1]-[G♭1]"),
    G_FLAT_1("G♭1", 30, 13, 12, "[F♯1]-[G♭1]"),
    G1("G1", 31, 14, -1, "[G1]"),
    G_SHARP_1("G♯1", 32, 15, 16, "[G♯1]-[A♭1]"),
    A_FLAT_1("A♭1", 32, 16, 15, "[G♯1]-[A♭1]"),
    A1("A1", 33, 17, -1, "[A1]"),
    A_SHARP_1("A♯1", 34, 18, 19, "[A♯1]-[B♭1]"),
    B_FLAT_1("B♭1", 34, 19, 18, "[A♯1]-[B♭1]"),
    B1("B1", 35, 20, -1, "[B1]"),

    C2("C2", 36, 21, -1, "[C2]"),
    C_SHARP_2("C♯2", 37, 22, 23, "[C♯2]-[D♭2]"),
    D_FLAT_2("D♭2", 37, 23, 22, "[C♯2]-[D♭2]"),
    D2("D2", 38, 24, -1, "[D2]"),
    D_SHARP_2("D♯2", 39, 25, 26, "[D♯2]-[E♭2]"),
    E_FLAT_2("E♭2", 39, 26, 25, "[D♯2]-[E♭2]"),
    E2("E2", 40, 27, -1, "[E2]"),
    F2("F2", 41, 28, -1, "[F2]"),
    F_SHARP_2("F♯2", 42, 29, 30, "[F♯2]-[G♭2]"),
    G_FLAT_2("G♭2", 42, 30, 29, "[F♯2]-[G♭2]"),
    G2("G2", 43, 31, -1, "[G2]"),
    G_SHARP_2("G♯2", 44, 32, 33, "[G♯2]-[A♭2]"),
    A_FLAT_2("A♭2", 44, 33, 32, "[G♯2]-[A♭2]"),
    A2("A2", 45, 34, -1, "[A2]"),
    A_SHARP_2("A♯2", 46, 35, 36, "[A♯2]-[B♭2]"),
    B_FLAT_2("B♭2", 46, 36, 35, "[A♯2]-[B♭2]"),
    B2("B2", 47, 37, -1, "[B2]"),

    C3("C3", 48, 38, -1, "[C3]"),
    C_SHARP_3("C♯3", 49, 39, 40, "[C♯3]-[D♭3]"),
    D_FLAT_3("D♭3", 49, 40, 39, "[C♯3]-[D♭3]"),
    D3("D3", 50, 41, -1, "[D3]"),
    D_SHARP_3("D♯3", 51, 42, 43, "[D♯3]-[E♭3]"),
    E_FLAT_3("E♭3", 51, 43, 42, "[D♯3]-[E♭3]"),
    E3("E3", 52, 44, -1, "[E3]"),
    F3("F3", 53, 45, -1, "[F3]"),
    F_SHARP_3("F♯3", 54, 46, 47, "[F♯3]-[G♭3]"),
    G_FLAT_3("G♭3", 54, 47, 46, "[F♯3]-[G♭3]"),
    G3("G3", 55, 48, -1, "[G3]"),
    G_SHARP_3("G♯3", 56, 49, 50, "[G♯3]-[A♭3]"),
    A_FLAT_3("A♭3", 56, 50, 49, "[G♯3]-[A♭3]"),
    A3("A3", 57, 51, -1, "[A3]"),
    A_SHARP_3("A♯3", 58, 52, 53, "[A♯3]-[B♭3]"),
    B_FLAT_3("B♭3", 58, 53, 52, "[A♯3]-[B♭3]"),
    B3("B3", 59, 54, -1, "[B3]"),

    C4("C4", 60, 55, -1, "[C4]"),
    C_SHARP_4("C♯4", 61, 56, 57, "[C♯4]-[D♭4]"),
    D_FLAT_4("D♭4", 61, 57, 56, "[C♯4]-[D♭4]"),
    D4("D4", 62, 58, -1, "[D4]"),
    D_SHARP_4("D♯4", 63, 59, 60, "[D♯4]-[E♭4]"),
    E_FLAT_4("E♭4", 63, 60, 59, "[D♯4]-[E♭4]"),
    E4("E4", 64, 61, -1, "[E4]"),
    F4("F4", 65, 62, -1, "[F4]"),
    F_SHARP_4("F♯4", 66, 63, 64, "[F♯4]-[G♭4]"),
    G_FLAT_4("G♭4", 66, 64, 63, "[F♯4]-[G♭4]"),
    G4("G4", 67, 65, -1, "[G4]"),
    G_SHARP_4("G♯4", 68, 66, 67, "[G♯4]-[A♭4]"),
    A_FLAT_4("A♭4", 68, 67, 66, "[G♯4]-[A♭4]"),
    A4("A4", 69, 68, -1, "[A4]"),
    A_SHARP_4("A♯4", 70, 69, 70, "[A♯4]-[B♭4]"),
    B_FLAT_4("B♭4", 70, 70, 69, "[A♯4]-[B♭4]"),
    B4("B4", 71, 71, -1, "[B4]"),

    C5("C5", 72, 72, -1, "[C5]"),
    C_SHARP_5("C♯5", 73, 73, 74, "[C♯5]-[D♭5]"),
    D_FLAT_5("D♭5", 73, 74, 73, "[C♯5]-[D♭5]"),
    D5("D5", 74, 75, -1, "[D5]"),
    D_SHARP_5("D♯5", 75, 76, 77, "[D♯5]-[E♭5]"),
    E_FLAT_5("E♭5", 75, 77, 76, "[D♯5]-[E♭5]"),
    E5("E5", 76, 78, -1, "[E5]"),
    F5("F5", 77, 79, -1, "[F5]"),
    F_SHARP_5("F♯5", 78, 80, 81, "[F♯5]-[G♭5]"),
    G_FLAT_5("G♭5", 78, 81, 80, "[F♯5]-[G♭5]"),
    G5("G5", 79, 82, -1, "[G5]"),
    G_SHARP_5("G♯5", 80, 83, 84, "[G♯5]-[A♭5]"),
    A_FLAT_5("A♭5", 80, 84, 83, "[G♯5]-[A♭5]"),
    A5("A5", 81, 85, -1, "[A5]"),
    A_SHARP_5("A♯5", 82, 86, 87, "[A♯5]-[B♭5]"),
    B_FLAT_5("B♭5", 82, 87, 86, "[A♯5]-[B♭5]"),
    B5("B5", 83, 88, -1, "[B5]"),

    C6("C6", 84, 89, -1, "[C6]"),
    C_SHARP_6("C♯6", 85, 90, 91, "[C♯6]-[D♭6]"),
    D_FLAT_6("D♭6", 85, 91, 90, "[C♯6]-[D♭6]"),
    D6("D6", 86, 92, -1, "[D6]"),
    D_SHARP_6("D♯6", 87, 93, 94, "[D♯6]-[E♭6]"),
    E_FLAT_6("E♭6", 87, 94, 93, "[D♯6]-[E♭6]"),
    E6("E6", 88, 95, -1, "[E6]"),
    F6("F6", 89, 96, -1, "[F6]"),
    F_SHARP_6("F♯6", 90, 97, 98, "[F♯6]-[G♭6]"),
    G_FLAT_6("G♭6", 90, 98, 97, "[F♯6]-[G♭6]"),
    G6("G6", 91, 99, -1, "[G6]"),
    G_SHARP_6("G♯6", 92, 100, 101, "[G♯6]-[A♭6]"),
    A_FLAT_6("A♭6", 92, 101, 100, "[G♯6]-[A♭6]"),
    A6("A6", 93, 102, -1, "[A6]"),
    A_SHARP_6("A♯6", 94, 103, 104, "[A♯6]-[B♭6]"),
    B_FLAT_6("B♭6", 94, 104, 103, "[A♯6]-[B♭6]"),
    B6("B6", 95, 105, -1, "[B6]"),

    C7("C7", 96, 106, -1, "[C7]"),
    C_SHARP_7("C♯7", 97, 107, 108, "[C♯7]-[D♭7]"),
    D_FLAT_7("D♭7", 97, 108, 107, "[C♯7]-[D♭7]"),
    D7("D7", 98, 109, -1, "[D7]"),
    D_SHARP_7("D♯7", 99, 110, 111, "[D♯7]-[E♭7]"),
    E_FLAT_7("E♭7", 99, 111, 110, "[D♯7]-[E♭7]"),
    E7("E7", 100, 112, -1, "[E7]"),
    F7("F7", 101, 113, -1, "[F7]"),
    F_SHARP_7("F♯7", 102, 114, 115, "[F♯7]-[G♭7]"),
    G_FLAT_7("G♭7", 102, 115, 114, "[F♯7]-[G♭7]"),
    G7("G7", 103, 116, -1, "[G7]"),
    G_SHARP_7("G♯7", 104, 117, 118, "[G♯7]-[A♭7]"),
    A_FLAT_7("A♭7", 104, 118, 117, "[G♯7]-[A♭7]"),
    A7("A7", 105, 119, -1, "[A7]"),
    A_SHARP_7("A♯7", 106, 120, 121, "[A♯7]-[B♭7]"),
    B_FLAT_7("B♭7", 106, 121, 120, "[A♯7]-[B♭7]"),
    B7("B7", 107, 122, -1, "[B7]"),

    C8("C8", 108, 123, -1, "[C8]");

    public static final PianoNote LOWEST_NOTE = PianoNote.A0;
    public static final PianoNote HIGHEST_NOTE = PianoNote.C8;
    public static final int NUMBER_OF_KEYS = 88;

    public final String label;
    public final String pitch;
    public final int octave;
    public final String naturalNoteLabel;
    public final int midiValue;

    // A value from 0 to 87, spanning any piano note
    public final int absoluteKeyIndex;

    public final boolean isWhiteKey;
    public final boolean isBlackKey;
    public final String filename;

    public final int storedOrdinal;
    public final int enharmonicEquivalentOrdinal;

    PianoNote(String label, int midi, int storedOrdinal, Integer enharmonicEquivalentOrdinal, String filename) {
        this.label = label;
        this.pitch = setPitch();
        this.octave = Integer.parseInt(String.valueOf(this.label.charAt(this.label.length() - 1)));
        this.naturalNoteLabel = String.valueOf(this.label.charAt(0)) + this.octave;
        this.midiValue = midi;
        this.absoluteKeyIndex = midi - 21;
        this.storedOrdinal = storedOrdinal;
        this.enharmonicEquivalentOrdinal = enharmonicEquivalentOrdinal;
        this.filename = filename;
        this.isWhiteKey = checkIfWhiteKey();
        this.isBlackKey = !isWhiteKey;

    }

    private String setPitch() {
        if (label.length() == 2)
            return label.substring(0, 1);
        else
            return label.substring(0, 2);
    }

    private boolean checkIfWhiteKey() {
        if (label.length() == 2) {
            return true;
        }
        return false;
    }

    // Cache lookup values using Map that's populated when the class loads
    private static final Map<String, PianoNote> BY_LABEL = new HashMap<>();
    private static final Map<Integer, PianoNote> BY_MIDI_VALUE = new HashMap<>();
    private static final Map<Integer, PianoNote> BY_ABSOLUTE_KEY_INDEX = new HashMap<>();
    private static final Map<Integer, PianoNote> BY_STORED_ORDINAL = new HashMap<>();
    private static final Map<String, PianoNote> BY_FILENAME = new HashMap<>();


    static {
        for (PianoNote note : values()) {
            BY_LABEL.put(note.label, note);
            BY_MIDI_VALUE.put(note.midiValue, note);
            BY_ABSOLUTE_KEY_INDEX.put(note.absoluteKeyIndex, note);
            BY_STORED_ORDINAL.put(note.storedOrdinal, note);
            BY_FILENAME.put(note.filename, note);
        }
    }

    public static PianoNote valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    public static PianoNote valueOfMidi(int midiKey) {
        return BY_MIDI_VALUE.get(midiKey);
    }

    public static PianoNote valueOfAbsoluteKeyIndex(int absoluteKeyIndex) {
        return BY_ABSOLUTE_KEY_INDEX.get(absoluteKeyIndex);
    }

    public static PianoNote valueOfStoredOrdinal(int storedOrdinal) {
        return BY_STORED_ORDINAL.get(storedOrdinal);
    }

    public static PianoNote valueOfFilename(String filename) {
        return BY_FILENAME.get(filename);
    }

    public static ArrayList<PianoNote> NotesInRangeInclusive(PianoNote lowNote, PianoNote highNote) {
        ArrayList<PianoNote> notes = new ArrayList<>();
        for (int i = lowNote.storedOrdinal; i < highNote.storedOrdinal; i++) {
            notes.add(valueOfStoredOrdinal(i));
        }
        return notes;
    }

    public static int numberOfKeysInRangeInclusive(PianoNote lowNote, PianoNote highNote){
        int keyCount = 0;

        for (int i = lowNote.absoluteKeyIndex; i <= highNote.absoluteKeyIndex; i++) {
                keyCount++;
        }
        return keyCount;
    }

    public static int numberOfWhiteKeysInRangeInclusive(PianoNote lowNote, PianoNote highNote) {

        int whiteKeyCount = 0;

        for (int i = lowNote.absoluteKeyIndex; i < highNote.absoluteKeyIndex; i++) {
            if (PianoNote.valueOfAbsoluteKeyIndex(i).isWhiteKey) {
                whiteKeyCount++;
            }
        }
        return whiteKeyCount;
    }


    public boolean equals(PianoNote note, boolean singleOctavePractice) {
        if (!singleOctavePractice)
            if (this.storedOrdinal == note.enharmonicEquivalentOrdinal ||
                    this.absoluteKeyIndex == note.absoluteKeyIndex) {
                return true;
            }
            else {
                return false;
            }
        else {
            if (this.pitch == note.pitch) {
                return true;
            }
            if (this.enharmonicEquivalentOrdinal > 0)
            {
                return valueOfStoredOrdinal(this.enharmonicEquivalentOrdinal).pitch ==
                        valueOfStoredOrdinal(note.storedOrdinal).pitch;

            }
            else
                return false;
        }

    }

    @Override
    public String toString() {
        if (enharmonicEquivalentOrdinal>0)
            return label + " / " + valueOfStoredOrdinal(enharmonicEquivalentOrdinal).label;
        else
            return label;
    }
}
