package com.dunneev.seenatural.Fragments.Piano;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.dunneev.seenatural.R;

public class PianoSettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.piano_preferences, rootKey);
    }


}