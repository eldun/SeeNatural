package com.dunneev.seenatural.Utilities;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;

import com.dunneev.seenatural.Enums.PianoNote;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SoundPlayer {

    static {
        System.loadLibrary("native-lib");
    }

    private static final String LOG_TAG = SoundPlayer.class.getSimpleName();

    private final String notesFolder = "notes";

    private ArrayList loadedNotes = new ArrayList(PianoNote.NUMBER_OF_KEYS);



    // The number of channels in the player Stream.
    // Stereo Playback, set to 1 for Mono playback
    // This IS NOT the channel format of the source samples
    // (which must be mono).
    private final int NUM_CHANNELS = 1;

    // All the input samples are assumed to BE 44.1K
    // All the input samples are assumed to be mono.
    private final int SAMPLE_RATE = 44100;



    public SoundPlayer() {
    }


    public void setUpAudioStream() {
        setupAudioStreamNative(NUM_CHANNELS, SAMPLE_RATE);
    }

    public void teardownAudioStream() { teardownAudioStreamNative(); }


    public void loadPianoNoteWavAssets(AssetManager assetManager, PianoNote startingNote, PianoNote endingNote) {
        PianoNote note;
        for (int i=startingNote.absoluteKeyIndex; i<=endingNote.absoluteKeyIndex; i++) {
            note = PianoNote.valueOfAbsoluteKeyIndex(i);

            if (loadedNotes.contains(note))
                continue;
            else {
                loadWavAsset(assetManager, notesFolder + "/" + PianoNote.valueOfAbsoluteKeyIndex(i).filename + ".wav");
                loadedNotes.add(PianoNote.valueOfAbsoluteKeyIndex(i));
            }
        }
    }

    public void unloadWavAssets() {
        unloadWavAssetsNative();
        loadedNotes.clear();
    }

    private void loadWavAsset(AssetManager assetManager, String filename) {
        try {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(filename);
            FileInputStream fileInputStream = assetFileDescriptor.createInputStream();
            int dataLength = (int) assetFileDescriptor.getLength();
            byte[] dataBytes = new byte[dataLength];
            fileInputStream.read(dataBytes, 0, dataLength);
            loadWavAssetNative(dataBytes);
            assetFileDescriptor.close();
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "IOException: " + e.toString());
        }
    }


    private native void setupAudioStreamNative(int numChannels, int sampleRate);
    private native void teardownAudioStreamNative();
    private native void loadWavAssetNative(byte[] dataBytes);
    private native void unloadWavAssetsNative();
    public native void triggerDown(int pianoKey);
    public native void triggerUp(int pianoKey);
    private native boolean getOutputReset();
    private native void clearOutputReset();
    private native void restartStream();
}
