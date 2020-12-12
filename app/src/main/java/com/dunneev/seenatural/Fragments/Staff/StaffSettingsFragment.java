package com.dunneev.seenatural.Fragments.Staff;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.dunneev.seenatural.R;

public class StaffSettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.staff_preferences, rootKey);
    }

    // TODO: 12/10/2020 move all the sharedPref editor operations in StaffFragment OnChanged observer operations to here
}