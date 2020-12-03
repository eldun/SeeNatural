package com.dunneev.seenatural.Fragments.Piano;

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

import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.R;
import com.dunneev.seenatural.Utilities.SoundPlayer;
import com.dunneev.seenatural.databinding.FragmentPianoBinding;

import java.util.ArrayList;

public class PianoFragment extends Fragment implements PianoKey.PianoKeyListener {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private FragmentPianoBinding binding;
    PianoViewModel pianoViewModel;
    private PianoView pianoView;

    private SoundPlayer soundPlayer;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.

        pianoViewModel = new ViewModelProvider(this).get(PianoViewModel.class);
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
        setUpSoundPlayer();
//        binding.pianoviewPiano.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(PianoFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }

//    @Override
//    public void onInflate(@NonNull @NotNull Context context, @NonNull @NotNull AttributeSet attrs, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
//        super.onInflate(context, attrs, savedInstanceState);
//        setUpPiano();
//        setUpSoundPlayer();
//    }

    private void setUpSoundPlayer() {
        soundPlayer = new SoundPlayer(pianoView.getLowestPracticeNote(), PianoKey.count);
        AssetManager assetManager = getResources().getAssets();
        soundPlayer.loadWavAssets(assetManager);
        soundPlayer.setUpAudioStream();

    }

    private void setUpPiano() {

        pianoView = getView().findViewById(R.id.pianoview);
        pianoView.setLowestPracticeNote(pianoViewModel.getLowPracticeNote());
        pianoView.setHighestPracticeNote(pianoViewModel.getHighPracticeNote());

//        if (singleOctavePracticeMode) {
//            pianoView.setLowestPracticeNote(PianoNote.C4);
//            pianoView.setHighestPracticeNote(PianoNote.B4);
//        }
//
//        else {
//            pianoView.setLowestPracticeNote(lowestPracticeNote);
//            pianoView.setHighestPracticeNote(highestPracticeNote);
//        }

        ArrayList<PianoKey> pianoKeys = pianoView.getPianoKeys();
        for (PianoKey key : pianoKeys) {
            key.setPianoKeyListener(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        soundPlayer.teardownAudioStream();
        soundPlayer.unloadWavAssets();
    }

    @Override
    public void keyDown(PianoKey key) {
        Log.i(LOG_TAG, "keyDown(" + key.toString() + ")");

        PianoNote note = key.getNote();

        int relativePianoKeyIndex = note.absoluteKeyIndex - pianoViewModel.getLowPracticeNote().absoluteKeyIndex;



        // TODO: 11/23/2020 Play note depending on StaffNote, not PianoKey (especially if in single octave mode)
        soundPlayer.triggerDown(relativePianoKeyIndex);
    }

    @Override
    public void keyUp(PianoKey key) {

    }


    // TODO: 11/18/2020 Consider displaying a translucent wrong note for a short time on incorrect key
    private void incorrectKeyPressed(PianoKey key) {

    }




}