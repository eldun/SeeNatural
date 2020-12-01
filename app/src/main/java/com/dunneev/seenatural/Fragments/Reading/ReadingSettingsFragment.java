package com.dunneev.seenatural.Fragments.Reading;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.dunneev.seenatural.R;

public class ReadingSettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.reading_preferences, rootKey);
    }
}