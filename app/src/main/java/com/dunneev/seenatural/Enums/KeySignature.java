package com.dunneev.seenatural.Enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum KeySignature {

    // Majors
    C_FLAT_MAJOR("C♭", true,false, new String[]{"C♭","D♭","E♭","F♭","G♭","A♭","B♭"}, 7, 0, 0),
    G_FLAT_MAJOR("G♭", true,false, new String[]{"G♭","A♭","B♭","C♭","D♭","E♭","F"},6, 0, 1),
    D_FLAT_MAJOR("D♭", true,false, new String[]{"D♭","E♭","F","G♭","A♭","B♭","C"}, 5, 0, 2),
    A_FLAT_MAJOR("A♭", true,false, new String[]{"A♭","B♭","C","D♭","E♭","F","G"}, 4, 0, 3),
    E_FLAT_MAJOR("E♭", true,false, new String[]{"E♭","F","G","A♭","B♭","C","D"}, 3, 0, 4),
    B_FLAT_MAJOR("B♭", true,false, new String[]{"B♭","C","D","E♭","F","G","A"}, 2, 0, 5),
    F_MAJOR("F", true,false, new String[]{"F", "G", "A", "B♭", "C", "D", "E"}, 1, 0, 6),
    C_MAJOR("C", true, false, new String[]{"C","D","E","F","G","A","B"}, 0, 0, 7),
    G_MAJOR("G", true,false, new String[]{"G","A","B","C","D","E","F♯"}, 0, 1, 8),
    D_MAJOR("D", true,false, new String[]{"D","E","F♯","G","A","B","C♯"}, 0, 2, 9),
    A_MAJOR("A", true,false, new String[]{"A","B","C♯","D","E","F♯","G♯"}, 0, 3, 10),
    E_MAJOR("E", true,false, new String[]{"E","F♯","G♯","A","B","C♯","D♯"}, 0, 4, 11),
    B_MAJOR("B", true,false, new String[]{"B","C♯","D♯","E","F♯","G♯","A♯"}, 0, 5, 12),
    F_SHARP_MAJOR("F♯", true,false, new String[]{"F♯","G♯","A♯","B","C♯","D♯","E♯"}, 0, 6, 13),
    C_SHARP_MAJOR("C♯", true,false, new String[]{"C♯","D♯","E♯","F♯","G♯","A♯","B♯"}, 0, 7, 14),


    // Minors
    A_FLAT_MINOR("A♭", false,true, new String[]{"A♭","B♭","C♭","D♭","E♭","F♭","G♭"}, 7, 0, 15),
    E_FLAT_MINOR("E♭", false,true, new String[]{"E♭","F","G♭","A♭","B♭","C♭","D♭"}, 6, 0, 16),
    B_FLAT_MINOR("B♭", false,true, new String[]{"B♭","C","D♭","E♭","F","G♭","A♭"}, 5, 0, 17),
    F_MINOR("F", false,true, new String[]{"F","G","A♭","B♭","C","D♭","E♭"}, 4, 0, 18),
    C_MINOR("C", false,true, new String[]{"C","D","E♭","F","G","A♭","B♭"}, 3, 0, 19),
    G_MINOR("G", false,true, new String[]{"G","A","B♭","C","D","E♭","F"}, 2, 0, 20),
    D_MINOR("D", false,true, new String[]{"D","E","F","G","A","B♭","C"}, 1, 0, 21),
    A_MINOR("A", false,true, new String[]{"A","B","C","D","E","F","G"}, 0, 0, 22),
    E_MINOR("E", false,true, new String[]{"E","F♯","G","A","B","C","D"}, 0, 1, 23),
    B_MINOR("B", false,true, new String[]{"B","C♯","D","E","F♯","G","A"}, 0, 2, 24),
    F_SHARP_MINOR("F♯", false,true, new String[]{"F♯","G♯","A","B","C♯","D","E"}, 0, 3, 25),
    C_SHARP_MINOR("C♯", false,true, new String[]{"C♯","D♯","E","F♯","G♯","A","B"}, 0, 4, 26),
    G_SHARP_MINOR("G♯", false,true, new String[]{"G♯","A♯","B","C♯","D♯","E","F♯"}, 0, 5, 27),
    D_SHARP_MINOR("D♯", false,true, new String[]{"D♯","E♯","F♯","G♯","A♯","B","C♯"}, 0, 6, 28),
    A_SHARP_MINOR("A♯", false,true, new String[]{"A♯","B♯","C♯","D♯","E♯","F♯","G♯"}, 0, 7, 29);

    private KeySignature relativeKey;
    public final String label;
    public final Boolean isMajor;
    public final Boolean isMinor;
    public final String[] scaleNotes;
    public final Boolean hasSharps;
    public final int sharpCount;
    public final Boolean hasFlats;
    public final int flatCount;
    public final int storedOrdinal;

    // Cache lookup values using Map that's populated when the class loads
    private static final Map<Integer, KeySignature> BY_STORED_ORDINAL = new HashMap<Integer, KeySignature>();
    private static final Map<String, KeySignature> BY_STRING = new HashMap<String, KeySignature>();


    public static final String[] sharpsArray = {"F♯", "C♯", "G♯", "D♯", "A♯", "E♯", "B♯"};
    public static final String[] flatsArray = {"B♭", "E♭", "A♭", "D♭", "G♭", "C♭", "F♭"};

    // Number of possible values, excluding relative key signatures
    public static final int UNIQUE_KEY_SIGNATURE_COUNT = 15;

    KeySignature(String label,
                 Boolean isMajor,
                 Boolean isMinor,
                 String[] scaleNotes,
                 int flatCount,
                 int sharpCount,
                 int storedOrdinal) {

        this.label = label;
        this.isMajor = isMajor;
        this.isMinor = isMinor;
        this.scaleNotes = scaleNotes;
        this.flatCount = flatCount;
        this.sharpCount = sharpCount;
        this.hasFlats = this.flatCount > 0;
        this.hasSharps = this.sharpCount > 0;


        this.storedOrdinal = storedOrdinal;

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

        for (KeySignature key: values()) {
            BY_STORED_ORDINAL.put(key.storedOrdinal, key);
            BY_STRING.put(key.toString(), key);
        }
    }

    public KeySignature getRelativeKey() {
        return relativeKey;
    }


    // Is this stupid?
    public static KeySignature valueOfStoredOrdinal(int storedOrdinal) {
        return BY_STORED_ORDINAL.get(storedOrdinal);
    }

    public static KeySignature valueOfString(CharSequence keySignatureString) {
        return BY_STRING.get(keySignatureString);
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

