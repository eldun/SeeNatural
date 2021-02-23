package com.dunneev.seenatural.Fragments.Reading;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;

import com.dunneev.seenatural.Activities.MainActivity;
import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.Fragments.Piano.PianoViewModel;
import com.dunneev.seenatural.Fragments.Staff.StaffPracticeItem;
import com.dunneev.seenatural.Fragments.Staff.StaffViewModel;
import com.dunneev.seenatural.R;
import com.dunneev.seenatural.Utilities.Event;
import com.dunneev.seenatural.databinding.FragmentReadingBinding;

import org.jetbrains.annotations.NotNull;

public class ReadingFragment extends Fragment {

    private final String LOG_TAG = this.getClass().getSimpleName();

    NavController navController;

    private FragmentReadingBinding binding;
    private ReadingViewModel viewModel;
    private StaffViewModel staffViewModel;
    private PianoViewModel pianoViewModel;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.i(LOG_TAG, "create");
        super.onCreate(savedInstanceState);


        // Although each view should supposedly have one viewmodel, I couldn't find
        // anything addressing best practices regarding nested fragments.
        // I'm adhering to DRY and referencing the child model views.
        viewModel = new ViewModelProvider(this).get(ReadingViewModel.class);
        staffViewModel = new ViewModelProvider(this).get(StaffViewModel.class);
        pianoViewModel = new ViewModelProvider(this).get(PianoViewModel.class);

        setViewModelFieldsFromPreferences();



        setUpObservers();

    }

    private void setViewModelFieldsFromPreferences() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        boolean singleOctaveMode = sharedPreferences.getBoolean(getResources().getString(R.string.reading_single_octave_mode_key), true);

        KeySignature keySignature = KeySignature.valueOfString(sharedPreferences.getString(getResources().getString(R.string.reading_key_signature_key), KeySignature.C_MAJOR.toString()));


        // Staff preferences are set up so that the flats, naturals, and sharps preferences are grayed out when generateAccidentals is false.
        // However, this does not change their value in sharedPrefs. That's the reason for the conditionals here. I could change them to false in
        // StaffSettingsFragment, but I think the persistence of what was selected is more user friendly.
        boolean generateAccidentals = sharedPreferences.getBoolean(getResources().getString(R.string.reading_generate_accidentals_key), true);
        boolean generateFlats;
        boolean generateNaturals;
        boolean generateSharps;
        if (generateAccidentals) {
            generateFlats = sharedPreferences.getBoolean(getResources().getString(R.string.reading_generate_flats_key), true);
            generateNaturals = sharedPreferences.getBoolean(getResources().getString(R.string.reading_generate_naturals_key), true);
            generateSharps = sharedPreferences.getBoolean(getResources().getString(R.string.reading_generate_sharps_key), true);
        }
        else {
            generateFlats = generateNaturals = generateSharps = false;
        }


        PianoNote lowNote =  PianoNote.valueOfLabel(sharedPreferences.getString(getResources().getString(R.string.piano_low_note_key), getResources().getString(R.string.PianoLowNoteDefault)));
        PianoNote highNote = PianoNote.valueOfLabel(sharedPreferences.getString(getResources().getString(R.string.piano_high_note_key), getResources().getString(R.string.PianoHighNoteDefault)));

        viewModel.setIsSingleOctaveMode(singleOctaveMode);
        viewModel.setKeySignature(keySignature);
        viewModel.setLowPracticeNote(lowNote);
        viewModel.setHighPracticeNote(highNote);
        viewModel.setGenerateAccidentals(generateAccidentals);
        viewModel.setGenerateFlats(generateFlats);
        viewModel.setGenerateNaturals(generateNaturals);
        viewModel.setGenerateSharps(generateSharps);
    }


    private void setUpObservers() {

//        final Observer<Boolean> singleOctaveObserver = new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean isSingleOctaveMode) {
//                viewModel.isSingleOctaveMode = isSingleOctaveMode;
//            }
//        };

        final Observer<Event<PianoNote>> keyPressedObserver = pianoNoteEvent -> {
            PianoNote note = pianoNoteEvent.getContentIfNotHandled();

            if (note != null) {
                Log.i(LOG_TAG, note.toString() + " pressed");
                StaffPracticeItem item = staffViewModel.getCurrentPracticeItem();

                if (item == null)
                    return;

                if (viewModel.isCorrectPress(note, staffViewModel.getCurrentPracticeItem())) {
                    viewModel.onCorrectKeyPressed(note);
                } else {
                    viewModel.onIncorrectKeyPressed(note);
                }
            }
        };

        final Observer<Event<PianoNote>> keyReleasedObserver = pianoNoteEvent -> {
            Log.i(LOG_TAG, pianoNoteEvent.peekContent().toString() + " released");

        };

        final Observer<Boolean> staffCompleteObserver = new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean staffComplete) {
                if (staffComplete){
                    navController.navigate(R.id.action_reading_fragment_to_readingCompleteFragment);
                }
            }
        };

//        pianoViewModel.getMutableLiveDataIsSingleOctaveMode().observe(this, singleOctaveObserver);
        pianoViewModel.getMutableLiveDataKeyPressed().observe(this, keyPressedObserver);
        pianoViewModel.getMutableLiveDataKeyReleased().observe(this, keyReleasedObserver);
        staffViewModel.getMutableLiveDataIsComplete().observe(this, staffCompleteObserver);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView");

        binding = FragmentReadingBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG, "viewCreated");

        navController = NavHostFragment.findNavController(this);


        // This conditional is to prevent the staff from repopulating on rotation
        // todo: handle endless mode
        if (staffViewModel.getPracticeItemsOnStaff().size()<1) {
            viewModel.generatePracticableNoteList();

            staffViewModel.addItemToStaff(PianoNote.G4);
            staffViewModel.addItemToStaff(PianoNote.A4);
        }


        super.onViewCreated(view, savedInstanceState);
//        staffViewModel.addRandomNoteFromPracticableNotes();
    }

}
