package com.dunneev.seenatural.Fragments.Piano;

import android.app.usage.UsageEvents;
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
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;

import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.Fragments.Reading.ReadingFragment;
import com.dunneev.seenatural.Fragments.Reading.ReadingViewModel;
import com.dunneev.seenatural.Fragments.Staff.StaffViewModel;
import com.dunneev.seenatural.R;
import com.dunneev.seenatural.Utilities.Event;
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
        Log.i(LOG_TAG, "create");
        super.onCreate(savedInstanceState);



        readingViewModel = new ViewModelProvider(requireParentFragment()).get(ReadingViewModel.class);
        staffViewModel = new ViewModelProvider(requireParentFragment()).get(StaffViewModel.class);
        viewModel = new ViewModelProvider(requireParentFragment()).get(PianoViewModel.class);

        setViewModelFieldsFromPreferences();

        soundPlayer = new SoundPlayer();
        assetManager = getResources().getAssets();

        setUpObservables();

    }

    private void setViewModelFieldsFromPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        // This feels hacky, but I want to only use single octave mode if PianoFragment is contained
        // in ReadingFragment because of how I've organized the settings menu.
        boolean hostedByReadingFragment = getParentFragment().getClass() == ReadingFragment.class;

        boolean singleOctaveMode = sharedPreferences.getBoolean(getResources().getString(R.string.reading_single_octave_mode_key), true);

        String lowNoteLabel = sharedPreferences.getString(getResources().getString(R.string.piano_low_note_key), getResources().getString(R.string.PianoLowNoteDefault));
        String highNoteLabel = sharedPreferences.getString(getResources().getString(R.string.piano_high_note_key), getResources().getString(R.string.PianoHighNoteDefault));


        if (singleOctaveMode && hostedByReadingFragment) {
            viewModel.setIsSingleOctaveMode(singleOctaveMode);
        }

        else
        {
            viewModel.setLowNote(PianoNote.valueOfLabel(lowNoteLabel));
            viewModel.setHighNote(PianoNote.valueOfLabel(highNoteLabel));
        }


    }

    private void setUpObservables() {


        final Observer<Event<PianoNote>> correctKeyPressedObserver = pianoNoteEvent -> {
            Log.i(LOG_TAG, "correct key pressed");
            if(pianoNoteEvent.peekContent() != null) {

                binding.pianoView.setBlackKeyDownColor(viewModel.blackKeyDownCorrectColor);
                binding.pianoView.setWhiteKeyDownColor(viewModel.whiteKeyDownCorrectColor);
            }
        };

        final Observer<Event<PianoNote>> incorrectKeyPressedObserver = pianoNoteEvent -> {
            Log.i(LOG_TAG, "incorrect key pressed");
            if(pianoNoteEvent.peekContent() != null) {

                binding.pianoView.setBlackKeyDownColor(viewModel.blackKeyDownIncorrectColor);
                binding.pianoView.setWhiteKeyDownColor(viewModel.whiteKeyDownIncorrectColor);
            }
        };

        readingViewModel.getMutableLiveDataCorrectKeyPressed().observe(this, correctKeyPressedObserver);
        readingViewModel.getMutableLiveDataIncorrectKeyPressed().observe(this, incorrectKeyPressedObserver);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPianoBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "viewCreated");

        super.onViewCreated(view, savedInstanceState);

        setUpPiano();
    }

    private void setUpPiano() {
        PianoView.setWhiteKeyUpColor(viewModel.whiteKeyUpColor);
        PianoView.setWhiteKeyDownColor(viewModel.whiteKeyDownColor);
        PianoView.setBlackKeyUpColor(viewModel.blackKeyUpColor);
        PianoView.setBlackKeyDownColor(viewModel.blackKeyDownColor);

        binding.pianoView.setLowestPracticeNote(viewModel.getLowNote());
        binding.pianoView.setHighestPracticeNote(viewModel.getHighNote());



        // Not sure if this is the best way to do it. I call init from the PianoView xml constructor
        // so I have to "refresh" it here after updating the notes based on sharedPrefs
        binding.pianoView.init();

        setUpPianoKeyListeners();
        setUpSoundPlayer();

    }

    private void setUpPianoKeyListeners() {

        for (PianoKey key:binding.pianoView.getPianoKeys()) {
            key.setPianoKeyListener(this);
        }
    }


    private void setUpSoundPlayer() {


        /* todo: load notes based on staff range to allow the correct
            notes to be played even when in single octave mode.
            e.g. hitting a C4 for a staff's C6 in single octave mode will play C6
         */
        soundPlayer.loadPianoNoteWavAssets(assetManager, viewModel.getLowNote(), viewModel.getHighNote());

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

        int relativePianoKeyIndex = note.absoluteKeyIndex - viewModel.getLowNote().absoluteKeyIndex;


        // TODO: 11/23/2020 Play note depending on StaffNote, not PianoKey (especially if in single octave mode)
        soundPlayer.triggerDown(relativePianoKeyIndex);
    }

    @Override
    public void keyUp(PianoKey key) {
        viewModel.keyUp(key.getNote());
        PianoView.setBlackKeyDownColor(viewModel.blackKeyDownColor);
        PianoView.setWhiteKeyDownColor(viewModel.whiteKeyDownColor);
    }
}