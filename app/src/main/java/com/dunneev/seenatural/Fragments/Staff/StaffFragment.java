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
        viewModel.generatePracticableNoteArray();
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
                regenerateStaff();
                viewModel.generatePracticableNoteArray();
                sharedPreferencesEditor.putString(getResources().getString(R.string.staff_key_signature_key), keySignature.toString());
                sharedPreferencesEditor.apply();
            }
        };

        final Observer<Boolean> hideKeySignatureObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hideKeySignature) {
                regenerateStaff();
                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.staff_hide_key_signature_key), hideKeySignature);
                sharedPreferencesEditor.apply();
            }
        };

        final Observer<Boolean> generateAccidentalsObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean generateAccidentals) {
                regenerateStaff();
                viewModel.generatePracticableNoteArray();
                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.generate_accidentals_key), generateAccidentals);
                sharedPreferencesEditor.apply();
            }
        };

        final Observer<Boolean> generateFlatsObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean generateFlats) {
                regenerateStaff();
                viewModel.generatePracticableNoteArray();
                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.generate_flats_key), generateFlats);
                sharedPreferencesEditor.apply();
            }
        };

        final Observer<Boolean> generateNaturalsObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean generateNaturals) {
                regenerateStaff();
                viewModel.generatePracticableNoteArray();
                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.generate_naturals_key), generateNaturals);
                sharedPreferencesEditor.apply();
            }
        };

        final Observer<Boolean> generateSharpsObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean generateSharps) {
                regenerateStaff();
                viewModel.generatePracticableNoteArray();
                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.generate_sharps_key), generateSharps);
                sharedPreferencesEditor.apply();
            }
        };

        final Observer<Boolean> hideTrebleClefObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hideTrebleClef) {
                regenerateStaff();
                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.hide_treble_clef_key), hideTrebleClef);
                sharedPreferencesEditor.apply();
            }
        };

        final Observer<Boolean> hideTrebleClefLinesObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hideTrebleClefLines) {
                regenerateStaff();
                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.hide_treble_clef_lines_key), hideTrebleClefLines);
                sharedPreferencesEditor.apply();
            }
        };

        final Observer<Boolean> hideBassClefObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hideBassClef) {
                regenerateStaff();
                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.hide_bass_clef_lines_key), hideBassClef);
                sharedPreferencesEditor.apply();
            }
        };


        final Observer<Boolean> hideBassClefLinesObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hideBassClefLines) {
                regenerateStaff();
                sharedPreferencesEditor.putBoolean(getResources().getString(R.string.hide_bass_clef_lines_key), hideBassClefLines);
                sharedPreferencesEditor.apply();
            }
        };


        final Observer<PianoNote> lowNoteObserver = new Observer<PianoNote>() {
            @Override
            public void onChanged(PianoNote lowPianoNote) {
                regenerateStaff();
                viewModel.generatePracticableNoteArray();
                sharedPreferencesEditor.putString(getResources().getString(R.string.staff_low_practice_note_key), lowPianoNote.label);
                sharedPreferencesEditor.apply();
            }
        };

        final Observer<PianoNote> highNoteObserver = new Observer<PianoNote>() {
            @Override
            public void onChanged(PianoNote highPianoNote) {

                regenerateStaff();
                viewModel.generatePracticableNoteArray();

                sharedPreferencesEditor.putString(getResources().getString(R.string.staff_high_practice_note_key), highPianoNote.label);
                sharedPreferencesEditor.apply();
            }
        };


        viewModel.getMutableLiveDataKeySignature().observe(this, keySignatureObserver);
        viewModel.getMutableLiveDataHideKeySignature().observe(this, hideKeySignatureObserver);

        viewModel.getMutableLiveDataHideTrebleClef().observe(this, hideTrebleClefObserver);
        viewModel.getMutableLiveDataHideTrebleClefLines().observe(this, hideTrebleClefLinesObserver);
        viewModel.getMutableLiveDataHideBassClef().observe(this, hideBassClefObserver);
        viewModel.getMutableLiveDataHideBassClefLines().observe(this, hideBassClefLinesObserver);

        viewModel.getMutableLiveDataGenerateAccidentals().observe(this, generateAccidentalsObserver);
        viewModel.getMutableLiveDataGenerateFlats().observe(this, generateFlatsObserver);
        viewModel.getMutableLiveDataGenerateNaturals().observe(this, generateNaturalsObserver);
        viewModel.getMutableLiveDataGenerateSharps().observe(this, generateSharpsObserver);

        viewModel.getMutableLiveDataLowestStaffPracticeNote().observe(this, lowNoteObserver);
        viewModel.getMutableLiveDataHighestStaffPracticeNote().observe(this, highNoteObserver);

        // Functionality
        final Observer<ArrayList<PianoNote>> notesOnStaffObserver = new Observer<ArrayList<PianoNote>>() {
            @Override
            public void onChanged(ArrayList<PianoNote> notesOnStaff) {
                PianoNote latestNote = notesOnStaff.get(notesOnStaff.size()-1);
                    binding.staffView.addNote(latestNote);
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

        binding.addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "addNoteButton clicked");
                viewModel.addNoteToStaff(viewModel.generateRandomNoteFromPracticableNotes());
            }
        });
//
//        binding.toggleTrebleClefButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewModel.setHideTrebleClef(binding.toggleTrebleClefButton.isChecked());
//
//            }
//        });
        regenerateStaff();
    }



    private void setUpStaff() {
        Log.i(LOG_TAG, "setUpStaff()");

        binding.staffView.setKeySignature(viewModel.getSelectedKeySignature());
        binding.staffView.setHideKeySignature(viewModel.getHideKeySignature());

        binding.staffView.setHideTrebleClef(viewModel.getHideTrebleClef());
        binding.staffView.setHideTrebleClefLines(viewModel.getHideTrebleClefLines());
        binding.staffView.setHideBassClef(viewModel.getHideBassClef());
        binding.staffView.setHideBassClefLines(viewModel.getHideBassClefLines());

        binding.staffView.setLowestPracticeNote(viewModel.getLowestStaffPracticeNote());
        binding.staffView.setHighestPracticeNote(viewModel.getHighestStaffPracticeNote());

    }


    private void initializeStaff() {
        Log.i(LOG_TAG, "initializeStaff()");
        binding.staffView.init();
//        viewModel.addAllPracticableNotesToStaff();
//        binding.staffView.addNote(PianoNote.G4);
    }


    private void regenerateStaff(){
        Log.i(LOG_TAG, "regenerateStaff()");

        setUpStaff();
        initializeStaff();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}