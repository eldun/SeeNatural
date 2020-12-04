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
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.R;
import com.dunneev.seenatural.Utilities.SoundPlayer;
import com.dunneev.seenatural.databinding.FragmentPianoBinding;

public class PianoFragment extends Fragment implements PianoKey.PianoKeyListener {

    private final String LOG_TAG = this.getClass().getSimpleName();

    SharedPreferences sharedPreferences;

    private FragmentPianoBinding binding;
    PianoViewModel viewModel;

    private SoundPlayer soundPlayer;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.

        viewModel = new ViewModelProvider(this).get(PianoViewModel.class);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        String lowNoteLabel = sharedPreferences.getString(getResources().getString(R.string.piano_low_practice_note_key), "");
        String highNoteLabel = sharedPreferences.getString(getResources().getString(R.string.piano_high_practice_note_key), "");

        viewModel.lowestPracticeNote = PianoNote.valueOfLabel(lowNoteLabel);
        viewModel.highestPracticeNote = PianoNote.valueOfLabel(highNoteLabel);
        viewModel.populatePianoNoteArrays();

        // TODO: 12/3/2020 Use MutableLiveData in the ViewModel to dynamically update the UI when prefs are changed 
//        // Create the observer which updates the UI.
//        final Observer<PianoNote> lowNoteObserver = new Observer<PianoNote>() {
//            @Override
//            public void onChanged(PianoNote pianoNote) {
//                // Update the UI
//            }
//        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentPianoBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setUpPiano();
        setUpPianoKeyListeners();
        setUpSoundPlayer();
    }

    private void setUpPiano() {
        PianoView.setWhiteKeyUpColor(viewModel.whiteKeyUpColor);
        PianoView.setWhiteKeyDownColor(viewModel.whiteKeyDownColor);
        PianoView.setWhiteKeyDownCorrectColor(viewModel.whiteKeyDownCorrectColor);
        PianoView.setWhiteKeyDownIncorrectColor(viewModel.whiteKeyDownIncorrectColor);
        PianoView.setBlackKeyUpColor(viewModel.blackKeyUpColor);
        PianoView.setBlackKeyDownColor(viewModel.blackKeyDownColor);
        PianoView.setBlackKeyDownCorrectColor(viewModel.blackKeyDownCorrectColor);
        PianoView.setBlackKeyDownIncorrectColor(viewModel.blackKeyDownIncorrectColor);

        binding.pianoview.setLowestPracticeNote(viewModel.lowestPracticeNote);
        binding.pianoview.setHighestPracticeNote(viewModel.highestPracticeNote);

        // Not sure if this is the best way to do it. I call init from the PianoView xml constructor
        // so I have to "refresh" it here after updating the notes based on sharedPrefs
        binding.pianoview.init();
    }

    private void setUpPianoKeyListeners() {

        for (PianoKey key:binding.pianoview.getPianoKeys()) {
            key.setPianoKeyListener(this);
        }
    }


    private void setUpSoundPlayer() {
        soundPlayer = new SoundPlayer(viewModel.lowestPracticeNote, viewModel.getNumberOfKeys());
        AssetManager assetManager = getResources().getAssets();
        soundPlayer.loadWavAssets(assetManager);
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

        viewModel.keyDown();

        PianoNote note = key.getNote();
        int relativePianoKeyIndex = note.absoluteKeyIndex - viewModel.lowestPracticeNote.absoluteKeyIndex;


        // TODO: 11/23/2020 Play note depending on StaffNote, not PianoKey (especially if in single octave mode)
        soundPlayer.triggerDown(relativePianoKeyIndex);
    }

    @Override
    public void keyUp(PianoKey key) {
        viewModel.keyUp();
    }


    // TODO: 11/18/2020 Consider displaying a translucent wrong note for a short time on incorrect key
    private void incorrectKeyPressed(PianoKey key) {
        viewModel.incorrectKeyPressed();
    }

    private void correctKeyPressed() {
        viewModel.correctKeyPressed();
    }




}