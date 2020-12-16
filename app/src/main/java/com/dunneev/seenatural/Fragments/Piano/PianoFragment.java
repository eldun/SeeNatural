package com.dunneev.seenatural.Fragments.Piano;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
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

import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.Fragments.Reading.ReadingViewModel;
import com.dunneev.seenatural.Fragments.Staff.StaffViewModel;
import com.dunneev.seenatural.R;
import com.dunneev.seenatural.Utilities.SoundPlayer;
import com.dunneev.seenatural.databinding.FragmentPianoBinding;

public class PianoFragment extends Fragment implements PianoKey.PianoKeyListener {

    private final String LOG_TAG = this.getClass().getSimpleName();

    SharedPreferences sharedPreferences;

    private FragmentPianoBinding binding;
    ReadingViewModel readingViewModel;
    StaffViewModel staffViewModel;
    PianoViewModel viewModel;


    private SoundPlayer soundPlayer;
    AssetManager assetManager;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.


        readingViewModel = new ViewModelProvider(requireParentFragment()).get(ReadingViewModel.class);
        staffViewModel = new ViewModelProvider(requireParentFragment()).get(StaffViewModel.class);
        viewModel = new ViewModelProvider(requireParentFragment()).get(PianoViewModel.class);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        String lowNoteLabel = sharedPreferences.getString(getResources().getString(R.string.piano_low_practice_note_key), "");
        String highNoteLabel = sharedPreferences.getString(getResources().getString(R.string.piano_high_practice_note_key), "");

        viewModel.setLowestPracticeNote(PianoNote.valueOfLabel(lowNoteLabel));
        viewModel.setHighestPracticeNote(PianoNote.valueOfLabel(highNoteLabel));

        // todo: use shared prefs
        viewModel.setIsSingleOctaveMode(false);
        viewModel.populatePianoNoteArrays();

        soundPlayer = new SoundPlayer();
        assetManager = getResources().getAssets();

        setUpObservables();


    }

    private void setUpObservables() {



        final Observer<PianoNote> correctKeyPressedObserver = new Observer<PianoNote>() {
            @Override
            public void onChanged(PianoNote note) {
                Log.i(LOG_TAG, "correct key pressed");

                PianoView.setBlackKeyDownColor(viewModel.blackKeyDownCorrectColor);
                PianoView.setWhiteKeyDownColor(viewModel.whiteKeyDownCorrectColor);
            }
        };

        final Observer<PianoNote> incorrectKeyPressedObserver = new Observer<PianoNote>() {
            @Override
            public void onChanged(PianoNote note) {
                Log.i(LOG_TAG, "incorrect key pressed");

                PianoView.setBlackKeyDownColor(viewModel.blackKeyDownIncorrectColor);
                PianoView.setWhiteKeyDownColor(viewModel.whiteKeyDownIncorrectColor);
            }
        };
//
//        // Create the observer which updates the UI.
//        final Observer<PianoNote> lowNoteObserver = new Observer<PianoNote>() {
//            @Override
//            public void onChanged(PianoNote lowPianoNote) {
//                // Update the UI
//            }
//        };
//
//        // Create the observer which updates the UI.
//        final Observer<PianoNote> highNoteObserver = new Observer<PianoNote>() {
//            @Override
//            public void onChanged(PianoNote highPianoNote) {
//                setUpPiano();
//            }
//        };
//
//        // Create the observer which updates the UI.
//        final Observer<Boolean> singleOctaveObserver = new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean isSingleOctave) {
//
//            }
//        };
//
        readingViewModel.getMutableLiveDataCorrectKeyPressed().observe(requireParentFragment(), correctKeyPressedObserver);
        readingViewModel.getMutableLiveDataIncorrectKeyPressed().observe(requireParentFragment(), incorrectKeyPressedObserver);
//        viewModel.getMutableLiveDataLowestPracticeNote().observe(this, lowNoteObserver);
//        viewModel.getMutableLiveDataHighestPracticeNote().observe(this, highNoteObserver);
//        viewModel.getMutableLiveDataIsSingleOctaveMode().observe(this, singleOctaveObserver);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentPianoBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.toggleHighNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.toggleHighNoteButton.isChecked())
                    viewModel.setHighestPracticeNote(PianoNote.C6);
                else
                    viewModel.setHighestPracticeNote(PianoNote.C8);
            }
        });

        setUpPiano();
    }

    private void setUpPiano() {
        PianoView.setWhiteKeyUpColor(viewModel.whiteKeyUpColor);
        PianoView.setWhiteKeyDownColor(viewModel.whiteKeyDownColor);
        PianoView.setBlackKeyUpColor(viewModel.blackKeyUpColor);
        PianoView.setBlackKeyDownColor(viewModel.blackKeyDownColor);

        binding.pianoview.setLowestPracticeNote(viewModel.getLowestPracticeNote());
        binding.pianoview.setHighestPracticeNote(viewModel.getHighestPracticeNote());
        viewModel.populatePianoNoteArrays();



        // Not sure if this is the best way to do it. I call init from the PianoView xml constructor
        // so I have to "refresh" it here after updating the notes based on sharedPrefs
        binding.pianoview.init();

        setUpPianoKeyListeners();
        setUpSoundPlayer();

    }

    private void setUpPianoKeyListeners() {

        // TODO: 12/7/2020 Better way to set listeners? (In terms of loose coupling)
        for (PianoKey key:binding.pianoview.getPianoKeys()) {
            key.setPianoKeyListener(this);
        }
    }


    private void setUpSoundPlayer() {


        soundPlayer.loadPianoNoteWavAssets(assetManager, viewModel.getLowestPracticeNote(), viewModel.numberOfKeys);
        soundPlayer.setUpAudioStream();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        soundPlayer.teardownAudioStream();
        soundPlayer.unloadWavAssets();
    }


    // Update UI in fragment, process data in the ViewModel
    @Override
    public void keyDown(PianoKey key) {
        Log.i(LOG_TAG, "keyDown(" + key.toString() + ")");
        PianoNote note = key.getNote();

        viewModel.keyDown(note);

        int relativePianoKeyIndex = note.absoluteKeyIndex - viewModel.getLowestPracticeNote().absoluteKeyIndex;


        // TODO: 11/23/2020 Play note depending on StaffNote, not PianoKey (especially if in single octave mode)
        soundPlayer.triggerDown(relativePianoKeyIndex);
    }

    @Override
    public void keyUp(PianoKey key) {
        viewModel.keyUp(key.getNote());
        PianoView.setBlackKeyDownColor(viewModel.blackKeyDownColor);
        PianoView.setWhiteKeyDownColor(viewModel.whiteKeyDownColor);
    }


//    // TODO: 11/18/2020 Consider displaying a translucent wrong note for a short time on incorrect key
//    private void incorrectKeyPressed(PianoKey key) {
//        viewModel.incorrectKeyPressed();
//    }
//
//    private void correctKeyPressed() {
//        viewModel.correctKeyPressed();
//    }




}