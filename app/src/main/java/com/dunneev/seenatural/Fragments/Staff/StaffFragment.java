package com.dunneev.seenatural.Fragments.Staff;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.Fragments.Piano.PianoKey;
import com.dunneev.seenatural.Fragments.Piano.PianoViewModel;
import com.dunneev.seenatural.R;
import com.dunneev.seenatural.databinding.FragmentStaffBinding;

public class StaffFragment extends Fragment {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private FragmentStaffBinding binding;
    private StaffViewModel viewModel;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(StaffViewModel.class);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        String lowNoteLabel = sharedPreferences.getString(getResources().getString(R.string.staff_low_practice_note_key), "");
        String highNoteLabel = sharedPreferences.getString(getResources().getString(R.string.staff_high_practice_note_key), "");

        viewModel.lowestPracticeNote = PianoNote.valueOfLabel(lowNoteLabel);
        viewModel.highestPracticeNote = PianoNote.valueOfLabel(highNoteLabel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentStaffBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpStaff();
    }

    private void setUpStaff() {
        binding.staffView.setLowestPracticeNote(viewModel.lowestPracticeNote);
        binding.staffView.setHighestPracticeNote(viewModel.highestPracticeNote);
        binding.staffView.init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}