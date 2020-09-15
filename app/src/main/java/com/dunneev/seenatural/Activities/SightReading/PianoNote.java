package com.dunneev.seenatural.Activities.SightReading;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public enum PianoNote {
    A0("A0", 21, "[A0]"),
    A_SHARP_0("A#0", 22, "[Bb0]"),
    B_FLAT_0("B♭0", 22, "[Bb0]"),
    B0("B0", 23, "[B0]"),

    C1("C1", 24, "[C1]"),
    C_SHARP_1("C#1", 25, "[Db1]"),
    D_FLAT_1("D♭1", 25, "[Db1]"),
    D1("D1", 26, "[D1]"),
    D_SHARP_1("D#1", 27, "[Eb1]"),
    E_FLAT_1("E♭1", 27, "[Eb1]"),
    E1("E1", 28, "[E1]"),
    F1("F1", 29, "[F1]"),
    F_SHARP_1("F#1", 30, "[F#1]"),
    G_FLAT_1("G♭1", 30, "[F#1]"),
    G1("G1", 31, "[G1]"),
    G_SHARP_1("G#1", 32, "[G#1]"),
    A_FLAT_1("A♭1", 32, "[G#1]"),
    A1("A1", 33, "[A1]"),
    A_SHARP_1("A#1", 34, "[Bb1]"),
    B_FLAT_1("B♭1", 34, "[Bb1]"),
    B1("B1", 35, "[B1]"),

    C2("C2", 36, "[C2]"),
    C_SHARP_2("C#2", 37, "[Db2]"),
    D_FLAT_2("D♭2", 37, "[Db2]"),
    D2("D2", 38, "[D2]"),
    D_SHARP_2("D#2", 39, "[Eb2]"),
    E_FLAT_2("E♭2", 39, "[Eb2]"),
    E2("E2", 40, "[E2]"),
    F2("F2", 41, "[F2]"),
    F_SHARP_2("F#2", 42, "[F#2]"),
    G_FLAT_2("G♭2", 42, "[F#2]"),
    G2("G2", 43, "[G2]"),
    G_SHARP_2("G#2", 44, "[G#2]"),
    A_FLAT_2("A♭2", 44, "[G#2]"),
    A2("A2", 45, "[A2]"),
    A_SHARP_2("A#2", 46, "[Bb2]"),
    B_FLAT_2("B♭2", 46, "[Bb2]"),
    B2("B2", 47, "[B2]"),

    C3("C3", 48, "[C3]"),
    C_SHARP_3("C#3", 49, "[Db3]"),
    D_FLAT_3("D♭3", 49, "[Db3]"),
    D3("D3", 50, "[D3]"),
    D_SHARP_3("D#3", 51, "[Eb3]"),
    E_FLAT_3("E♭3", 51, "[Eb3]"),
    E3("E3", 52, "[E3]"),
    F3("F3", 53, "[F3]"),
    F_SHARP_3("F#3", 54, "[F#3]"),
    G_FLAT_3("G♭3", 54, "[F#3]"),
    G3("G3", 55, "[G3]"),
    G_SHARP_3("G#3", 56, "[G#3]"),
    A_FLAT_3("A♭3", 56, "[G#3]"),
    A3("A3", 57, "[A3]"),
    A_SHARP_3("A#3", 58, "[Bb3]"),
    B_FLAT_3("B♭3", 58, "[Bb3]"),
    B3("B3", 59, "[B3]"),

    C4("C4", 60, "[C4]"),
    C_SHARP_4("C#4", 61, "[Db4]"),
    D_FLAT_4("D♭4", 61, "[Db4]"),
    D4("D4", 62, "[D4]"),
    D_SHARP_4("D#4", 63, "[Eb4]"),
    E_FLAT_4("E♭4", 65, "[Eb4]"),
    E4("E4", 64, "[E4]"),
    F4("F4", 65, "[F4]"),
    F_SHARP_4("F#4", 66, "[F#4]"),
    G_FLAT_4("G♭4", 66, "[F#4]"),
    G4("G4", 67, "[G4]"),
    G_SHARP_4("G#4", 68, "[G#4]"),
    A_FLAT_4("A♭4", 68, "[G#4]"),
    A4("A4", 69, "[A4]"),
    A_SHARP_4("A#4", 70, "[Bb4]"),
    B_FLAT_4("B♭4", 70, "[Bb4]"),
    B4("B4", 71, "[B4]"),

    C5("C5", 72, "[C5]"),
    C_SHARP_5("C#5", 73, "[Db5]"),
    D_FLAT_5("D♭5", 73, "[Db5]"),
    D5("D5", 74, "[D5]"),
    D_SHARP_5("D#5", 75, "[Eb5]"),
    E_FLAT_5("E♭5", 75, "[Eb5]"),
    E5("E5", 76, "[E5]"),
    F5("F5", 77, "[F5]"),
    F_SHARP_5("F#5", 78, "[F#5]"),
    G_FLAT_5("G♭5", 78, "[F#5]"),
    G5("G5", 79, "[G5]"),
    G_SHARP_5("G#5", 80, "[G#5]"),
    A_FLAT_5("A♭5", 80, "[G#5]"),
    A5("A5", 81, "[A5]"),
    A_SHARP_5("A#5", 82, "[Bb5]"),
    B_FLAT_5("B♭5", 82, "[Bb5]"),
    B5("B5", 83, "[B5]"),

    C6("C6", 84, "[C6]"),
    C_SHARP_6("C#6", 85, "[Db6]"),
    D_FLAT_6("D♭6", 85, "[Db6]"),
    D6("D6", 86, "[D6]"),
    D_SHARP_6("D#6", 87, "[Eb6]"),
    E_FLAT_6("E♭6", 87, "[Eb6]"),
    E6("E6", 88, "[E6]"),
    F6("F6", 89, "[F6]"),
    F_SHARP_6("F#6", 90, "[F#6]"),
    G_FLAT_6("G♭6", 90, "[F#6]"),
    G6("G6", 91, "[G6]"),
    G_SHARP_6("G#6", 92, "[G#6]"),
    A_FLAT_6("A♭6", 92, "[G#6]"),
    A6("A6", 93, "[A6]"),
    A_SHARP_6("A#6", 94, "[Bb6]"),
    B_FLAT_6("B♭6", 94, "[Bb6]"),
    B6("B6", 95, "[B6]"),

    C7("C7", 96, "[C7]"),
    C_SHARP_7("C#7", 97, "[Db7]"),
    D_FLAT_7("D♭7", 97, "[Db7]"),
    D7("D7", 98, "[D7]"),
    D_SHARP_7("D#7", 99, "[Eb7]"),
    E_FLAT_7("E♭7", 99, "[Eb7]"),
    E7("E7", 100, "[E7]"),
    F7("F7", 101, "[F7]"),
    F_SHARP_7("F#7", 102, "[F#7]"),
    G_FLAT_7("G♭7", 102, "[F#7]"),
    G7("G7", 103, "[G7]"),
    G_SHARP_7("G#7", 104, "[G#7]"),
    A_FLAT_7("A♭7", 104, "[G#7]"),
    A7("A7", 105, "[A7]"),
    A_SHARP_7("A#7", 106, "[Bb7]"),
    B_FLAT_7("B♭7", 106, "[Bb7]"),
    B7("B7", 107, "[B7]"),

    C8("C8", 108, "[C8]");

    public final String label;
    public final int midiValue;

    // A value from 0 to 87, spanning any piano note
    public final int absoluteNotePositionIndex;

    public final int keyColor;
    public final int keyDownColor;
    public final String filename;

    PianoNote(String label, int midi, String filename) {
        this.label = label;
        this.midiValue = midi;
        this.absoluteNotePositionIndex = midi - 21;
        this.keyColor = setKeyColor();
        this.keyDownColor = setKeyDownColor();
        this.filename = filename;
    }

    private int setKeyColor() {
        if (label.length() == 2)
            return Color.WHITE;
        return Color.BLACK;
    }

    private int setKeyDownColor() {
        if (this.keyColor == Color.WHITE)
            return Color.LTGRAY;
        return  Color.DKGRAY;
    }

    // Cache lookup values using Map that's populated when the class loads
    private static final Map<String, PianoNote> BY_LABEL = new HashMap<>();
    private static final Map<Integer, PianoNote> BY_MIDI_VALUE = new HashMap<>();
    private static final Map<Integer, PianoNote> BY_NOTE_POSITION_INDEX = new HashMap<>();
    private static final Map<String, PianoNote> BY_FILENAME = new HashMap<>();


    static {
        for (PianoNote note: values()) {
            BY_LABEL.put(note.label, note);
            BY_MIDI_VALUE.put(note.midiValue, note);
            BY_NOTE_POSITION_INDEX.put(note.absoluteNotePositionIndex, note);
            BY_FILENAME.put(note.filename, note);
        }
    }

    public static PianoNote valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    public static PianoNote valueOfMidi(int midiKey) {
        return BY_MIDI_VALUE.get(midiKey);
    }

    public static PianoNote valueOfNotePosition(int notePositionIndex) {
        return BY_NOTE_POSITION_INDEX.get(notePositionIndex);
    }

    public static PianoNote valueOfFilename(String filename) {
        return BY_FILENAME.get(filename);
    }

}
