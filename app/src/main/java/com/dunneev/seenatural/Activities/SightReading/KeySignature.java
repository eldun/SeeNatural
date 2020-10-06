package com.dunneev.seenatural.Activities.SightReading;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum KeySignature {

    // Majors
    C_FLAT_MAJOR("C♭", true,false, new String[]{"C♭","D♭","E♭","F♭","G♭","A♭","B♭"}, false, true),
    G_FLAT_MAJOR("G♭", true,false, new String[]{"G♭","A♭","B♭","C♭","D♭","E♭","F"},false, true),
    D_FLAT_MAJOR("D♭", true,false, new String[]{"D♭","E♭","F","G♭","A♭","B♭","C"}, false, true),
    A_FLAT_MAJOR("A♭", true,false, new String[]{"A♭","B♭","C","D♭","E♭","F","G"}, false, true),
    E_FLAT_MAJOR("E♭", true,false, new String[]{"E♭","F","G","A♭","B♭","C","D"}, false, true),
    B_FLAT_MAJOR("B♭", true,false, new String[]{"B♭","C","D","E♭","F","G","A"}, false, true),
    F_MAJOR("F", true,false, new String[]{"F", "G", "A", "B♭", "C", "D", "E"}, false, true),
    C_MAJOR("C", true, false, new String[]{"C","D","E","F","G","A","B"}, false, false),
    G_MAJOR("G", true,false, new String[]{"G","A","B","C","D","E","F♯"}, true, false),
    D_MAJOR("D", true,false, new String[]{"D","E","F♯","G","A","B","C♯"}, true, false),
    A_MAJOR("A", true,false, new String[]{"A","B","C♯","D","E","F♯","G♯"}, true, false),
    E_MAJOR("E", true,false, new String[]{"E","F♯","G♯","A","B","C♯","D♯"}, true, false),
    B_MAJOR("B", true,false, new String[]{"B","C♯","D♯","E","F♯","G♯","A♯"}, true, false),
    F_SHARP_MAJOR("F♯", true,false, new String[]{"F♯","G♯","A♯","B","C♯","D♯","E♯"}, true, false),
    C_SHARP_MAJOR("C♯", true,false, new String[]{"C♯","D♯","E♯","F♯","G♯","A♯","B♯"}, true, false),


    // Minors
    A_FLAT_MINOR("A♭", false,true, new String[]{"A♭","B♭","C♭","D♭","E♭","F♭","G♭"}, false, true),
    E_FLAT_MINOR("E♭", false,true, new String[]{"E♭","F","G♭","A♭","B♭","C♭","D♭"}, false, true),
    B_FLAT_MINOR("B♭", false,true, new String[]{"B♭","C","D♭","E♭","F","G♭","A♭"}, false, true),
    F_MINOR("F", false,true, new String[]{"F","G","A♭","B♭","C","D♭","E♭"}, false, true),
    C_MINOR("C", false,true, new String[]{"C","D","E♭","F","G","A♭","B♭"}, false, true),
    G_MINOR("G", false,true, new String[]{"G","A","B♭","C","D","E♭","F"}, false, true),
    D_MINOR("D", false,true, new String[]{"D","E","F","G","A","B♭","C"}, false, true),
    A_MINOR("A", false,true, new String[]{"A","B","C","D","E","F","G"}, false, false),
    E_MINOR("E", false,true, new String[]{"E","F♯","G","A","B","C","D"}, true, false),
    B_MINOR("B", false,true, new String[]{"B","C♯","D","E","F♯","G","A"}, true, false),
    F_SHARP_MINOR("F♯", false,true, new String[]{"F♯","G♯","A","B","C♯","D","E"}, true, false),
    C_SHARP_MINOR("C♯", false,true, new String[]{"C♯","D♯","E","F♯","G♯","A","B"}, true, false),
    G_SHARP_MINOR("G♯", false,true, new String[]{"G♯","A♯","B","C♯","D♯","E","F♯"}, true, false),
    D_SHARP_MINOR("D♯", false,true, new String[]{"D♯","E♯","F♯","G♯","A♯","B","C♯"}, true, false),
    A_SHARP_MINOR("A♯", false,true, new String[]{"A♯","B♯","C♯","D♯","E♯","F♯","G♯"}, true, false);

    private KeySignature relativeKey;
    public final String label;
    public final Boolean isMajor;
    public final Boolean isMinor;
    public final String[] scaleNotes;
    public final Boolean hasSharps;
    public final Boolean hasFlats;

    public static final String[] sharpsArray = {"F♯", "C♯", "G♯", "D♯", "A♯", "E♯", "B♯"};
    public static final String[] flatsArray = {"B♭", "E♭", "A♭", "D♭", "G♭", "C♭", "F♭"};

    KeySignature(String label,
                 Boolean isMajor,
                 Boolean isMinor,
                 String[] scaleNotes,
                 Boolean hasSharps,
                 Boolean hasFlats) {

        this.label = label;
        this.isMajor = isMajor;
        this.isMinor = isMinor;
        this.scaleNotes = scaleNotes;
        this.hasSharps = hasSharps;
        this.hasFlats = hasFlats;

    }

    static {
        // Majors
        C_FLAT_MAJOR.relativeKey = A_FLAT_MINOR;
        G_FLAT_MAJOR.relativeKey = E_FLAT_MINOR;
        D_FLAT_MAJOR.relativeKey = B_FLAT_MINOR;
        A_FLAT_MAJOR.relativeKey = F_MINOR;
        E_FLAT_MAJOR.relativeKey = C_MINOR;
        B_FLAT_MAJOR.relativeKey = G_MINOR;
        F_MAJOR.relativeKey =  D_MINOR;
        C_MAJOR.relativeKey = A_MINOR;
        G_MAJOR.relativeKey = E_MINOR;
        D_MAJOR.relativeKey = B_MINOR;
        A_MAJOR.relativeKey = F_SHARP_MINOR;
        E_MAJOR.relativeKey = C_SHARP_MINOR;
        B_MAJOR.relativeKey = G_SHARP_MINOR;
        F_SHARP_MAJOR.relativeKey = D_SHARP_MINOR;
        C_SHARP_MAJOR.relativeKey = A_SHARP_MINOR;


        // Minors
        A_FLAT_MINOR.relativeKey = C_FLAT_MAJOR;
        E_FLAT_MINOR.relativeKey = G_FLAT_MAJOR;
        B_FLAT_MINOR.relativeKey = D_FLAT_MAJOR;
        F_MINOR.relativeKey = A_FLAT_MAJOR;
        C_MINOR.relativeKey = E_FLAT_MAJOR;
        G_MINOR.relativeKey = B_FLAT_MAJOR;
        D_MINOR.relativeKey =  F_MAJOR;
        A_MINOR.relativeKey = C_MAJOR;
        E_MINOR.relativeKey = G_MAJOR;
        B_MINOR.relativeKey = D_MAJOR;
        F_SHARP_MINOR.relativeKey = A_MAJOR;
        C_SHARP_MINOR.relativeKey = E_MAJOR;
        G_SHARP_MINOR.relativeKey = B_MAJOR;
        D_SHARP_MINOR.relativeKey = F_SHARP_MAJOR;
        A_SHARP_MINOR.relativeKey = C_SHARP_MAJOR;
    }

    public KeySignature getRelativeKey() {
        return relativeKey;
    }




    public boolean containsNote(PianoNote note) {
        return Arrays.asList(scaleNotes).contains(note.pitch);
    }

    @Override
    public String toString() {
        String keyMode, relativeKeyMode;
        if (isMajor) {
            keyMode = "maj";
            relativeKeyMode = "min";
        }
        else {
            keyMode = "min";
            relativeKeyMode = "maj";
        }

        return label + keyMode + " / " + relativeKey.label + relativeKeyMode;
    }
}

