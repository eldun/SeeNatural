package com.dunneev.seenatural.Enums;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PianoNoteTest {

    @Test
    public void notesInRangeInclusive_EntirePiano_SizeIs124() {
        int notesInRangeInclusive = PianoNote.notesInRangeInclusive(PianoNote.LOWEST_NOTE, PianoNote.HIGHEST_NOTE).size();
        assertThat(notesInRangeInclusive, is(124));
    }

    @Test
    public void notesInRangeInclusive_C4ToC4_SizeIs1() {
        int notesInRangeInclusive = PianoNote.notesInRangeInclusive(PianoNote.C4, PianoNote.C4).size();
        assertThat(notesInRangeInclusive, is(1));
    }

    @Test
    public void notesInRangeInclusive_SingleOctave_SizeIs18() {
        int notesInRangeInclusive = PianoNote.notesInRangeInclusive(PianoNote.C4, PianoNote.C5).size();
        assertThat(notesInRangeInclusive, is(18));
    }

    @Test
    public void notesInRangeInclusive_WrongParameterOrder_SizeIs0() {
        int notesInRangeInclusive = PianoNote.notesInRangeInclusive(PianoNote.HIGHEST_NOTE, PianoNote.LOWEST_NOTE).size();
        assertThat(notesInRangeInclusive, is(0));
    }

    @Test
    public void numberOfKeysInRangeInclusive_EntirePiano_Returns88() {
        int keysInRangeInclusive = PianoNote.numberOfKeysInRangeInclusive(PianoNote.LOWEST_NOTE, PianoNote.HIGHEST_NOTE);
        assertThat(keysInRangeInclusive, is(88));
    }

    @Test
    public void numberOfKeysInRangeInclusive_C4ToC4_Returns1() {
        int keysInRangeInclusive = PianoNote.numberOfKeysInRangeInclusive(PianoNote.C4, PianoNote.C4);
        assertThat(keysInRangeInclusive, is(1));
    }

    @Test
    public void numberOfKeysInRangeInclusive_SingleOctave_Returns13() {
        int keysInRangeInclusive = PianoNote.numberOfKeysInRangeInclusive(PianoNote.C4, PianoNote.C5);
        assertThat(keysInRangeInclusive, is(13));
    }

    @Test
    public void numberOfKeysInRangeInclusive_WrongParameterOrder_Returns0() {
        int keysInRangeInclusive = PianoNote.numberOfKeysInRangeInclusive(PianoNote.HIGHEST_NOTE, PianoNote.LOWEST_NOTE);
        assertThat(keysInRangeInclusive, is(0));
    }

    @Test
    public void numberOfWhiteKeysInRangeInclusive_EntirePiano_Returns52() {
        int keysInRangeInclusive = PianoNote.numberOfWhiteKeysInRangeInclusive(PianoNote.LOWEST_NOTE, PianoNote.HIGHEST_NOTE);
        assertThat(keysInRangeInclusive, is(52));
    }

    @Test
    public void numberOfWhiteKeysInRangeInclusive_C4ToC4_Returns1() {
        int keysInRangeInclusive = PianoNote.numberOfWhiteKeysInRangeInclusive(PianoNote.C4, PianoNote.C4);
        assertThat(keysInRangeInclusive, is(1));
    }

    @Test
    public void numberOfWhiteKeysInRangeInclusive_CSharp4ToCSharp4_Returns0() {
        int keysInRangeInclusive = PianoNote.numberOfWhiteKeysInRangeInclusive(PianoNote.C_SHARP_4, PianoNote.C_SHARP_4);
        assertThat(keysInRangeInclusive, is(0));
    }

    @Test
    public void numberOfWhiteKeysInRangeInclusive_SingleOctave_Returns8() {
        int keysInRangeInclusive = PianoNote.numberOfWhiteKeysInRangeInclusive(PianoNote.C4, PianoNote.C5);
        assertThat(keysInRangeInclusive, is(8));
    }

    @Test
    public void numberOfWhiteKeysInRangeInclusive_WrongParameterOrder_Returns0() {
        int keysInRangeInclusive = PianoNote.numberOfWhiteKeysInRangeInclusive(PianoNote.HIGHEST_NOTE, PianoNote.LOWEST_NOTE);
        assertThat(keysInRangeInclusive, is(0));
    }

    @Test
    public void isAccidental_CMajor_AllPitchesReturnCorrectly() {
        KeySignature key = KeySignature.C_MAJOR;

        assertThat(PianoNote.isAccidental(PianoNote.C4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.D4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.E4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.F4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.G4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.A4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.B4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.C_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.D_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.D_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.E_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.F_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.G_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.G_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.A_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.A_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.B_FLAT_4, key), is(true));

    }

    @Test
    public void isAccidental_AMinor_AllPitchesReturnCorrectly() {
        KeySignature key = KeySignature.A_MINOR;

        assertThat(PianoNote.isAccidental(PianoNote.C4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.D4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.E4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.F4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.G4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.A4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.B4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.C_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.D_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.D_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.E_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.F_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.G_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.G_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.A_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.A_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.B_FLAT_4, key), is(true));

    }

    @Test
    public void isAccidental_GMajor_AllPitchesReturnCorrectly() {
        KeySignature key = KeySignature.G_MAJOR;

        assertThat(PianoNote.isAccidental(PianoNote.C4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.D4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.E4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.F4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.G4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.A4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.B4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.C_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.D_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.D_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.E_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.F_SHARP_4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.G_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.G_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.A_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.A_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.B_FLAT_4, key), is(true));

    }

    @Test
    public void isAccidental_EMinor_AllPitchesReturnCorrectly() {
        KeySignature key = KeySignature.E_MINOR;

        assertThat(PianoNote.isAccidental(PianoNote.C4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.D4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.E4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.F4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.G4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.A4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.B4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.C_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.D_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.D_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.E_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.F_SHARP_4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.G_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.G_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.A_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.A_SHARP_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.B_FLAT_4, key), is(true));

    }

    @Test
    public void isAccidental_CSharpMajor_AllPitchesReturnCorrectly() {
        KeySignature key = KeySignature.C_SHARP_MAJOR;

        assertThat(PianoNote.isAccidental(PianoNote.C4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.D4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.E4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.F4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.G4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.A4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.B4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.C_SHARP_4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.D_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.D_SHARP_4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.E_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.F_SHARP_4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.G_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.G_SHARP_4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.A_FLAT_4, key), is(true));
        assertThat(PianoNote.isAccidental(PianoNote.A_SHARP_4, key), is(false));
        assertThat(PianoNote.isAccidental(PianoNote.B_FLAT_4, key), is(true));

    }

    @Test
    public void testIsEquivalentTo() {
        boolean notesAreEquivalent;

        notesAreEquivalent = PianoNote.C4.isEquivalentTo(PianoNote.C4, false);
        assertThat(notesAreEquivalent, is(true));

        notesAreEquivalent = PianoNote.C4.isEquivalentTo(PianoNote.C4, true);
        assertThat(notesAreEquivalent, is(true));

        notesAreEquivalent = PianoNote.C4.isEquivalentTo(PianoNote.C5, false);
        assertThat(notesAreEquivalent, is(false));

        notesAreEquivalent = PianoNote.C4.isEquivalentTo(PianoNote.C5, true);
        assertThat(notesAreEquivalent, is(true));


        notesAreEquivalent = PianoNote.C_SHARP_4.isEquivalentTo(PianoNote.D_FLAT_4, false);
        assertThat(notesAreEquivalent, is(true));

        notesAreEquivalent = PianoNote.C_SHARP_4.isEquivalentTo(PianoNote.D_FLAT_4, true);
        assertThat(notesAreEquivalent, is(true));

        notesAreEquivalent = PianoNote.C_SHARP_4.isEquivalentTo(PianoNote.D_FLAT_5, false);
        assertThat(notesAreEquivalent, is(false));

        notesAreEquivalent = PianoNote.C_SHARP_4.isEquivalentTo(PianoNote.D_FLAT_5, true);
        assertThat(notesAreEquivalent, is(true));
    }
}