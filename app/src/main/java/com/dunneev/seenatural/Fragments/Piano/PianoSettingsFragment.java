package com.dunneev.seenatural.Fragments.Piano;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;

import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.R;

import java.util.ArrayList;

public class PianoSettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.piano_preferences, rootKey);

//        Context context = getPreferenceManager().getContext();
//        PreferenceScreen screen = getPreferenceManager().getPreferenceScreen();


//        ListPreference lowPracticeNotePreference = new ListPreference(context);
//        lowPracticeNotePreference.setKey(getString(R.string.low_practice_note_key));
//        lowPracticeNotePreference.setTitle("Low Practice Note");


//        lowPracticeNotePreference.setDefaultValue(PianoNote.C4.label);




//        screen.addPreference(lowPracticeNotePreference);
    }

}