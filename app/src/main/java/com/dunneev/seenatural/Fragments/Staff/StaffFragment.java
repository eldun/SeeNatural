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

        viewModel.lowestPracticeNote = PianoNote.valueOfLabel(lowNoteLabel);
        viewModel.highestPracticeNote = PianoNote.valueOfLabel(highNoteLabel);

        final Observer<ArrayList<PianoNote>> notesOnStaffObserver = new Observer<ArrayList<PianoNote>>() {
            @Override
            public void onChanged(ArrayList<PianoNote> notesOnStaff) {
                Log.i(LOG_TAG, "notesOnStaff now has " + notesOnStaff.size() + " elements");
                if (binding.staffView != null){
//                    updateNotesOnStaff();
                }

            }

        };

        // Observe the LiveData, passing in this fragment as the LifecycleOwner and the observer.
        viewModel.getLiveNotesOnStaff().observe(this, notesOnStaffObserver);

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

        setUpStaff();
//        for (PianoNote note:viewModel.practicableNotes) {
//            binding.staffView.addNote(note);
//        }

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

//    @Override
//    public void onStaffLaidOut() {
//        for (PianoNote note:viewModel.notesOnStaff) {
//            binding.staffView.addNote(note);
//        }
//    }
}