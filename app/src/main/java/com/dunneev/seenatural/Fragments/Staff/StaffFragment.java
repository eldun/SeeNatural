package com.dunneev.seenatural.Fragments.Staff;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.Fragments.Piano.PianoKey;
import com.dunneev.seenatural.Fragments.Piano.PianoViewModel;
import com.dunneev.seenatural.Fragments.Reading.ReadingFragment;
import com.dunneev.seenatural.Fragments.Reading.ReadingViewModel;
import com.dunneev.seenatural.R;
import com.dunneev.seenatural.databinding.FragmentStaffBinding;

import java.util.ArrayList;

public class StaffFragment extends Fragment /*implements StaffView.onStaffLaidOutListener*/{

    private final String LOG_TAG = this.getClass().getSimpleName();

    private FragmentStaffBinding binding;
    private StaffViewModel viewModel;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG, "create");


        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(StaffViewModel.class);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        String lowNoteLabel = sharedPreferences.getString(getResources().getString(R.string.staff_low_practice_note_key), "");
        String highNoteLabel = sharedPreferences.getString(getResources().getString(R.string.staff_high_practice_note_key), "");

        setUpObservables();

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