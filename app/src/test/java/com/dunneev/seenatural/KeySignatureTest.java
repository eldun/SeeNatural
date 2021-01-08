package com.dunneev.seenatural;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class KeySignatureTest {



    @Test
    public void ContainsNote_CMajorScale_ContainsCMajorScaleNotes() {
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