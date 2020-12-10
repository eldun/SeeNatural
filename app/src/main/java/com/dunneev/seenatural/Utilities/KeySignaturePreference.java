package com.dunneev.seenatural.Utilities;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.ListPreference;

import com.dunneev.seenatural.Enums.KeySignature;

public class KeySignaturePreference extends ListPreference {
    public KeySignaturePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public KeySignaturePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public KeySignaturePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KeySignaturePreference(Context context) {
        super(context);
        init();
    }


    private void init() {

        CharSequence[] keySignatureEntries = new CharSequence[KeySignature.UNIQUE_KEY_SIGNATURE_COUNT];
        CharSequence[] keySignatureValues = new CharSequence[KeySignature.UNIQUE_KEY_SIGNATURE_COUNT];

        keySignatureEntries[0] = keySignatureValues[0] = KeySignature.C_MAJOR.toString();
        keySignatureEntries[1] = keySignatureValues[1] = KeySignature.G_MAJOR.toString();
        keySignatureEntries[2] = keySignatureValues[2] = KeySignature.D_MAJOR.toString();
        keySignatureEntries[3] = keySignatureValues[3] = KeySignature.A_MAJOR.toString();
        keySignatureEntries[4] = keySignatureValues[4] = KeySignature.E_MAJOR.toString();
        keySignatureEntries[5] = keySignatureValues[5] = KeySignature.B_MAJOR.toString();
        keySignatureEntries[6] = keySignatureValues[6] = KeySignature.F_SHARP_MAJOR.toString();
        keySignatureEntries[7] = keySignatureValues[7] = KeySignature.C_SHARP_MAJOR.toString();
        keySignatureEntries[8] = keySignatureValues[8] = KeySignature.F_MAJOR.toString();
        keySignatureEntries[9] = keySignatureValues[9] = KeySignature.B_FLAT_MAJOR.toString();
        keySignatureEntries[10] = keySignatureValues[10] = KeySignature.E_FLAT_MAJOR.toString();
        keySignatureEntries[11] = keySignatureValues[11] = KeySignature.A_FLAT_MAJOR.toString();
        keySignatureEntries[12] = keySignatureValues[12] = KeySignature.D_FLAT_MAJOR.toString();
        keySignatureEntries[13] = keySignatureValues[13] = KeySignature.G_FLAT_MAJOR.toString();
        keySignatureEntries[14] = keySignatureValues[14] = KeySignature.C_FLAT_MAJOR.toString();

        setEntries(keySignatureEntries);
        setEntryValues(keySignatureValues);
    }
}
