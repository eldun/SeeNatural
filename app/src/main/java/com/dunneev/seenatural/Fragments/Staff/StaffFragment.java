package com.dunneev.seenatural.Fragments.Staff;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.R;
import com.dunneev.seenatural.databinding.FragmentStaffBinding;

import java.util.ArrayList;

public class StaffFragment extends Fragment /*implements StaffView.onStaffLaidOutListener*/{

    private final String LOG_TAG = this.getClass().getSimpleName();

    private FragmentStaffBinding binding;
    private StaffViewModel viewModel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG, "create");


        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(StaffViewModel.class);

        setViewModelFieldsFromPreferences();
        setUpObservables();

    }

    private void setViewModelFieldsFromPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        sharedPreferencesEditor = sharedPreferences.edit();

        KeySignature keySignature = KeySignature.valueOfString(sharedPreferences.getString(getResources().getString(R.string.staff_key_signature_key), KeySignature.C_MAJOR.toString()));
        boolean hideKeySignature = sharedPreferences.getBoolean(getResources().getString(R.string.staff_hide_key_signature_key), false);

        boolean hideTrebleClef = sharedPreferences.getBoolean(getResources().getString(R.string.hide_treble_clef_key), false);
        boolean hideTrebleClefLines = sharedPreferences.getBoolean(getResources().getString(R.string.hide_treble_clef_lines_key), false);
        boolean hideBassClef = sharedPreferences.getBoolean(getResources().getString(R.string.hide_bass_clef_key), false);
        boolean hideBassClefLines = sharedPreferences.getBoolean(getResources().getString(R.string.hide_bass_clef_lines_key), false);

        boolean generateAccidentals = sharedPreferences.getBoolean(getResources().getString(R.string.generate_accidentals_key), true);
        boolean generateFlats = sharedPreferences.getBoolean(getResources().getString(R.string.generate_flats_key), true);
        boolean generateNaturals = sharedPreferences.getBoolean(getResources().getString(R.string.generate_naturals_key), true);
        boolean generateSharps = sharedPreferences.getBoolean(getResources().getString(R.string.generate_sharps_key), true);

        PianoNote lowNote =  PianoNote.valueOfLabel(sharedPreferences.getString(getResources().getString(R.string.staff_low_practice_note_key), ""));
        PianoNote highNote = PianoNote.valueOfLabel(sharedPreferences.getString(getResources().getString(R.string.staff_high_practice_note_key), ""));
        
        viewModel.setSelectedKeySignature(keySignature);
        viewModel.setHideKeySignature(hideKeySignature);
        
        viewModel.setHideTrebleClef(hideTrebleClef);
        viewModel.setHideTrebleClefLines(hideTrebleClefLines);
        viewModel.setHideBassClef(hideBassClef);
        viewModel.setHideBassClefLines(hideBassClefLines);
        
        viewModel.setGenerateAccidentals(generateAccidentals);
        viewModel.setGenerateFlats(generateFlats);
        viewModel.setGenerateNaturals(generateNaturals);
        viewModel.setGenerateSharps(generateSharps);
        
        viewModel.setLowestStaffPracticeNote(lowNote);
        viewModel.setHighestStaffPracticeNote(highNote);
    }

    // TODO: 12/8/2020 Make StaffViewModel itself observable and update the binding with viewmodel properties
    private void setUpObservables() {
        final Observer<KeySignature> keySignatureObserver = new Observer<KeySignature>() {
            @Override
            public void onChanged(KeySignature keySignature) {
                setUpStaff();
            }
        };

        final Observer<Boolean> hideKeySignatureObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hideKeySignature) {
                setUpStaff();
            }
        };

        final Observer<Boolean> displayFlatsObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean displayFlats) {
                setUpStaff();
            }
        };

        final Observer<Boolean> displayNaturalsObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean displayNaturals) {
                setUpStaff();
            }
        };

        final Observer<Boolean> displaySharpsObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean displaySharps) {
                setUpStaff();
            }
        };

        final Observer<Boolean> hideTrebleClefObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hideTrebleClef) {
                setUpStaff();
            }
        };

        final Observer<Boolean> hideTrebleClefLinesObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hideTrebleClefLines) {
                setUpStaff();
            }
        };

        final Observer<Boolean> hideBassClefObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hideBassClef) {
                setUpStaff();
            }
        };


        final Observer<Boolean> hideBassClefLinesObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hideBassClefLines) {
                setUpStaff();
            }
        };


        final Observer<PianoNote> lowNoteObserver = new Observer<PianoNote>() {
            @Override
            public void onChanged(PianoNote lowPianoNote) {
                setUpStaff();
            }
        };

        final Observer<PianoNote> highNoteObserver = new Observer<PianoNote>() {
            @Override
            public void onChanged(PianoNote highPianoNote) {
                setUpStaff();
            }
        };


        viewModel.getMutableLiveDataKeySignature().observe(this, keySignatureObserver);
        viewModel.getMutableLiveDataHideKeySignature().observe(this, hideKeySignatureObserver);

        viewModel.getMutableLiveDataHideTrebleClef().observe(this, hideTrebleClefObserver);
        viewModel.getMutableLiveDataHideTrebleClefLines().observe(this, hideTrebleClefLinesObserver);
        viewModel.getMutableLiveDataHideBassClef().observe(this, hideBassClefObserver);
        viewModel.getMutableLiveDataHideBassClefLines().observe(this, hideBassClefLinesObserver);

        viewModel.getMutableLiveDataDisplayFlats().observe(this, displayFlatsObserver);
        viewModel.getMutableLiveDataDisplayNaturals().observe(this, displayNaturalsObserver);
        viewModel.getMutableLiveDataDisplaySharps().observe(this, displaySharpsObserver);
        viewModel.getMutableLiveDataLowestStaffPracticeNote().observe(this, lowNoteObserver);
        viewModel.getMutableLiveDataHighestStaffPracticeNote().observe(this, highNoteObserver);

        // Functionality
        final Observer<ArrayList<PianoNote>> notesOnStaffObserver = new Observer<ArrayList<PianoNote>>() {
            @Override
            public void onChanged(ArrayList<PianoNote> notesOnStaff) {
                Log.i(LOG_TAG, "notesOnStaff changed");

            }
        };

        viewModel.getMutableLiveDataNotesOnStaff().observe(requireParentFragment(), notesOnStaffObserver);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "createView");

        binding = FragmentStaffBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "viewCreated");

        super.onViewCreated(view, savedInstanceState);

        binding.toggleHighNoteButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.toggleHighNoteButton2.isChecked())
                    viewModel.setHighestStaffPracticeNote(PianoNote.C6);
                else
                    viewModel.setHighestStaffPracticeNote(PianoNote.C8);
            }
        });

        initializeStaff();
    }


    private void setUpStaff() {

        binding.staffView.setKeySignature(viewModel.getSelectedKeySignature());
//        binding.staffView.setHideKeySignature(viewModel.getHideKeySignature());
//
//        binding.staffView.setHideTrebleClef(viewModel.getHideTrebleClef());
//        binding.staffView.setHideTrebleClefLines(viewModel.getHideTrebleClefLines());
//        binding.staffView.setHideBassClef(viewModel.getHideBassClef());
//        binding.staffView.setHideBassClefLines(viewModel.getHideBassClefLines());
//
//        binding.staffView.setDisplayFlats(viewModel.getDisplayFlats());
//        binding.staffView.setDisplayNaturals(viewModel.getDisplayNaturals());
//        binding.staffView.setDisplaySharps(viewModel.getDisplaySharps());

        binding.staffView.setLowestPracticeNote(viewModel.getLowestStaffPracticeNote());
        binding.staffView.setHighestPracticeNote(viewModel.getHighestStaffPracticeNote());

    }


    private void initializeStaff() {
        binding.staffView.init();
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}