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













//    private SparseArray<PlayThread> threadMap = null;
//        private Context context;
//        final String notesFolder = "notes";
//        private static final SparseArray<String> SOUND_MAP = new SparseArray<>();
//        public static final int MAX_VOLUME = 100, CURRENT_VOLUME = 90;
//
//
//    static {
//            // white keys
//            SOUND_MAP.put(1, "low_c");
//            SOUND_MAP.put(2, "low_d");
//            SOUND_MAP.put(3, "low_e");
//            SOUND_MAP.put(4, "low_f");
//            SOUND_MAP.put(5, "low_g");
//            SOUND_MAP.put(6, "low_a");
//            SOUND_MAP.put(7, "low_b");
//            SOUND_MAP.put(8, "high_c");
//            SOUND_MAP.put(9, "high_d");
//            SOUND_MAP.put(10, "high_e");
//            SOUND_MAP.put(11, "high_f");
//            SOUND_MAP.put(12, "high_g");
//            SOUND_MAP.put(13, "high_a");
//            SOUND_MAP.put(14, "high_b");
//
//            // black keys
//            SOUND_MAP.put(15, "low_c_sharp");
//            SOUND_MAP.put(16, "low_d_sharp");
//            SOUND_MAP.put(17, "low_f_sharp");
//            SOUND_MAP.put(18, "low_g_sharp");
//            SOUND_MAP.put(19, "low_a_sharp");
//            SOUND_MAP.put(20, "high_c_sharp");
//            SOUND_MAP.put(21, "high_d_sharp");
//            SOUND_MAP.put(22, "high_f_sharp");
//            SOUND_MAP.put(23, "high_g_sharp");
//            SOUND_MAP.put(24, "high_a_sharp");
//        }
//
//        public SoundPlayer(Context context) {
//            this.context = context;
//            threadMap = new SparseArray<>();
//        }
//
//        public void playNote(int note) {
//            if (!isNotePlaying(note)) {
//                PlayThread thread = new PlayThread(note);
//                thread.start();
//                threadMap.put(note, thread);
//            }
//        }
//
//        public void stopNote(int note) {
//            PlayThread thread = threadMap.get(note);
//
//            if (thread != null) {
//                threadMap.remove(note);
//            }
//        }
//
//        public boolean isNotePlaying(int note) {
//            return threadMap.get(note) != null;
//        }
//
//        private class PlayThread extends Thread {
//            int note;
//            AudioTrack audioTrack;
//
//            public PlayThread(int note) {
//                this.note = note;
//            }
//
//            @Override
//            public void run() { // TODO: fix clicking at beginning of .wav file (maybe re-record piano/re-render at different bitrate)
//                try{
//                    String path = notesFolder + "/" + SOUND_MAP.get(note) + ".wav";
//                    AssetManager assetManager = context.getAssets();
//                    AssetFileDescriptor ad = assetManager.openFd(path);
//                    long fileSize = ad.getLength();
//                    int bufferSize = audioTrack.getMinBufferSize(44100,AudioFormat.CHANNEL_CONFIGURATION_STEREO, AudioFormat.ENCODING_PCM_16BIT);
//                    byte[] buffer = new byte[bufferSize];
//
//                    audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_CONFIGURATION_MONO,
//                            AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);
//
//                    float logVolume = (float) (1 - (Math.log(MAX_VOLUME - CURRENT_VOLUME) / Math.log(MAX_VOLUME)));
//                    audioTrack.setStereoVolume(logVolume, logVolume);
//
//                    audioTrack.play();
//                    InputStream audioStream = null;
//                    int headerOffset = 44; long bytesWritten = 0; int bytesRead = 0;
//
//                    audioStream = assetManager.open(path);
//                    audioStream.read(buffer, 0, headerOffset);
//
//                    while (bytesWritten < fileSize - headerOffset) {
//                        bytesRead = audioStream.read(buffer, 0, bufferSize);
//                        bytesWritten += audioTrack.write(buffer, 0, bytesRead);
//                    }
//
//                    audioTrack.stop();
//                    audioTrack.release();
//
//                } catch (FileNotFoundException e) {
//                    // TODO
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    // TODO
//                    e.printStackTrace();
//                }    finally {
//                    if (audioTrack != null) {
//                        audioTrack.release();
//                    }
//                }
//            }
//
//
//        }
}
