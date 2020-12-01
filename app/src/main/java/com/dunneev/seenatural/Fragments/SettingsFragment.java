package com.dunneev.seenatural.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.dunneev.seenatural.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }


    @Override
    public boolean onPreferenceTreeClick(Preference preference) {

        final String key = preference.getKey();
        if (key.equals(getString(R.string.piano_settings_key))) {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_settingsFragment_to_pianoSettingsFragment);
            return true;
        }
        else if (key.equals(getString(R.string.staff_settings_key))) {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_settingsFragment_to_staffSettingsFragment);
            return true;
        }
        else if (key.equals(getString(R.string.reading_settings_key))) {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_settingsFragment_to_readingSettingsFragment);
            return true;
        }
        return false;
    }
}