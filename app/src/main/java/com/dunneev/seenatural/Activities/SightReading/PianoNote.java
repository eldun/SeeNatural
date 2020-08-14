package com.dunneev.seenatural.Activities.SightReading;

import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

public enum PianoNote {
    A0("A0", 21),
    A_SHARP_0("A#0", 22),
    B_FLAT_0("B♭0", 22),
    B0("B0", 23),

    C1("C1", 24),
    C_SHARP_1("C#1", 25),
    D_FLAT_1("D♭1", 25),
    D1("D1", 26),
    D_SHARP_1("D#1", 27),
    E_FLAT_1("E♭1", 27),
    E1("E1", 28),
    F1("F1", 29),
    F_SHARP_1("F#1", 30),
    G_FLAT_1("G♭1", 30),
    G1("G1", 31),
    G_SHARP_1("G#1", 32),
    A_FLAT_1("A♭1", 32),
    A1("A1", 33),
    A_SHARP_1("A#1", 34),
    B_FLAT_1("B♭1", 34),
    B1("B1", 35),

    C2("C2", 36),
    C_SHARP_2("C#2", 37),
    D_FLAT_2("D♭2", 37),
    D2("D2", 38),
    D_SHARP_2("D#2", 39),
    E_FLAT_2("E♭2", 39),
    E2("E2", 40),
    F2("F2", 41),
    F_SHARP_2("F#2", 42),
    G_FLAT_2("G♭2", 42),
    G2("G2", 43),
    G_SHARP_2("G#2", 44),
    A_FLAT_2("A♭2", 44),
    A2("A2", 45),
    A_SHARP_2("A#2", 46),
    B_FLAT_2("B♭2", 46),
    B2("B2", 47),

    C3("C3", 48),
    C_SHARP_3("C#3", 49),
    D_FLAT_3("D♭3", 49),
    D3("D3", 50),
    D_SHARP_3("D#3", 51),
    E_FLAT_3("E♭3", 51),
    E3("E3", 52),
    F3("F3", 53),
    F_SHARP_3("F#3", 54),
    G_FLAT_3("G♭3", 54),
    G3("G3", 55),
    G_SHARP_3("G#3", 56),
    A_FLAT_3("A♭3", 56),
    A3("A3", 57),
    A_SHARP_3("A#3", 58),
    B_FLAT_3("B♭3", 58),
    B3("B3", 59),

    C4("C4", 60),
    C_SHARP_4("C#4", 61),
    D_FLAT_4("D♭4", 61),
    D4("D4", 62),
    D_SHARP_4("D#4", 63),
    E_FLAT_4("E♭4", 65),
    E4("E4", 64),
    F4("F4", 65),
    F_SHARP_4("F#4", 66),
    G_FLAT_4("G♭4", 66),
    G4("G4", 67),
    G_SHARP_4("G#4", 68),
    A_FLAT_4("A♭4", 68),
    A4("A4", 69),
    A_SHARP_4("A#4", 70),
    B_FLAT_4("B♭4", 70),
    B4("B4", 71),

    C5("C5", 72),
    C_SHARP_5("C#5", 73),
    D_FLAT_5("D♭5", 73),
    D5("D5", 74),
    D_SHARP_5("D#5", 75),
    E_FLAT_5("E♭5", 75),
    E5("E5", 76),
    F5("F5", 77),
    F_SHARP_5("F#5", 78),
    G_FLAT_5("G♭5", 78),
    G5("G5", 79),
    G_SHARP_5("G#5", 80),
    A_FLAT_5("A♭5", 80),
    A5("A5", 81),
    A_SHARP_5("A#5", 82),
    B_FLAT_5("B♭5", 82),
    B5("B5", 83),

    C6("C6", 84),
    C_SHARP_6("C#6", 85),
    D_FLAT_6("D♭6", 85),
    D6("D6", 86),
    D_SHARP_6("D#6", 87),
    E_FLAT_6("E♭6", 87),
    E6("E6", 88),
    F6("F6", 89),
    F_SHARP_6("F#6", 90),
    G_FLAT_6("G♭6", 90),
    G6("G6", 91),
    G_SHARP_6("G#6", 92),
    A_FLAT_6("A♭6", 92),
    A6("A6", 93),
    A_SHARP_6("A#6", 94),
    B_FLAT_6("B♭6", 94),
    B6("B6", 95),

    C7("C7", 96),
    C_SHARP_7("C#7", 97),
    D_FLAT_7("D♭7", 97),
    D7("D7", 98),
    D_SHARP_7("D#7", 99),
    E_FLAT_7("E♭7", 99),
    E7("E7", 100),
    F7("F7", 101),
    F_SHARP_7("F#7", 102),
    G_FLAT_7("G♭7", 102),
    G7("G7", 103),
    G_SHARP_7("G#7", 104),
    A_FLAT_7("A♭7", 104),
    A7("A7", 105),
    A_SHARP_7("A#7", 106),
    B_FLAT_7("B♭7", 106),
    B7("B7", 107),

    C8("C8", 108);

    public final String label;
    public final int midiValue;

    PianoNote(String label, int midi) {
        this.label = label;
        this.midiValue = midi;
    }

    // Cache lookup values using Map that's populated when the class loads
    private static final Map<String, PianoNote> BY_LABEL = new HashMap<>();
    private static final Map<Integer, PianoNote> BY_MIDI_VALUE = new HashMap<>();


    static {
        for (PianoNote note: values()) {
            BY_LABEL.put(note.label, note);
            BY_MIDI_VALUE.put(note.midiValue, note);
        }
    }

    public static PianoNote valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    public static PianoNote valueOfMidi(int midiKey) {
        return BY_MIDI_VALUE.get(midiKey);
    }
}
