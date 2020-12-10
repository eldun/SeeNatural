package com.dunneev.seenatural.Utilities;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.ListPreference;

import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.Fragments.Piano.PianoViewModel;

public class NotePreference extends ListPreference {

    public NotePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public NotePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public NotePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public NotePreference(Context context) {
        super(context);
        init();

    }

    private void init() {
        CharSequence[] noteEntries = new CharSequence[PianoViewModel.TOTAL_KEYS];
        CharSequence[] noteValues = new CharSequence[PianoViewModel.TOTAL_KEYS];
        for (int i=0;i<PianoViewModel.TOTAL_KEYS;i++) {
            noteEntries[i] = PianoNote.valueOfAbsoluteKeyIndex(i).toString();
            noteValues[i] = (PianoNote.valueOfAbsoluteKeyIndex(i).label);
        }
        setEntries(noteEntries);
        setEntryValues(noteValues);
    }
}
