package com.dunneev.seenatural.Activities.SightReading;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class KeySignatureTest {

    @Test
    public void getRelativeKey() {
        for (KeySignature keySig : KeySignature.values()) {
            if (keySig.isMajor) {
                assertThat(keySig.scaleNotes[5], is(keySig.getRelativeKey().label));
            }

            else if (keySig.isMinor) {
                assertThat(keySig.scaleNotes[2], is(keySig.getRelativeKey().label));
            }
        }
    }

    @Test
    public void containsNote() {
        assertThat(KeySignature.C_MAJOR.containsNote(PianoNote.C4), is(true));
        assertThat(KeySignature.C_MAJOR.containsNote(PianoNote.C_SHARP_5), is(false));
        assertThat(KeySignature.C_MAJOR.containsNote(PianoNote.D5), is(true));
        assertThat(KeySignature.C_MAJOR.containsNote(PianoNote.D_SHARP_1), is(false));
        assertThat(KeySignature.C_MAJOR.containsNote(PianoNote.E_FLAT_4), is(false));
        assertThat(KeySignature.C_MAJOR.containsNote(PianoNote.F4), is(true));
        assertThat(KeySignature.C_MAJOR.containsNote(PianoNote.F_SHARP_1), is(false));
        assertThat(KeySignature.C_MAJOR.containsNote(PianoNote.G_FLAT_5), is(false));
        assertThat(KeySignature.C_MAJOR.containsNote(PianoNote.A5), is(true));
        assertThat(KeySignature.C_MAJOR.containsNote(PianoNote.A_SHARP_0), is(false));
        assertThat(KeySignature.C_MAJOR.containsNote(PianoNote.B_FLAT_0), is(false));


    }


}