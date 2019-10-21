package com.dunneev.seenatural.Activities.SightRead;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.SparseArray;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayer {

        private SparseArray<PlayThread> threadMap = null;
        private Context context;
        final String notesFolder = "notes";
        private static final SparseArray<String> SOUND_MAP = new SparseArray<>();
        public static final int MAX_VOLUME = 100, CURRENT_VOLUME = 90;

        static {
            // white keys
            SOUND_MAP.put(1, "low_c");
            SOUND_MAP.put(2, "low_d");
            SOUND_MAP.put(3, "low_e");
            SOUND_MAP.put(4, "low_f");
            SOUND_MAP.put(5, "low_g");
            SOUND_MAP.put(6, "low_a");
            SOUND_MAP.put(7, "low_b");
            SOUND_MAP.put(8, "high_c");
            SOUND_MAP.put(9, "high_d");
            SOUND_MAP.put(10, "high_e");
            SOUND_MAP.put(11, "high_f");
            SOUND_MAP.put(12, "high_g");
            SOUND_MAP.put(13, "high_a");
            SOUND_MAP.put(14, "high_b");

            // black keys
            SOUND_MAP.put(15, "low_c_sharp");
            SOUND_MAP.put(16, "low_d_sharp");
            SOUND_MAP.put(17, "low_f_sharp");
            SOUND_MAP.put(18, "low_g_sharp");
            SOUND_MAP.put(19, "low_a_sharp");
            SOUND_MAP.put(20, "high_c_sharp");
            SOUND_MAP.put(21, "high_d_sharp");
            SOUND_MAP.put(22, "high_f_sharp");
            SOUND_MAP.put(23, "high_g_sharp");
            SOUND_MAP.put(24, "high_a_sharp");
        }

        public SoundPlayer(Context context) {
            this.context = context;
            threadMap = new SparseArray<>();
        }

        public void playNote(int note) {
            if (!isNotePlaying(note)) {
                PlayThread thread = new PlayThread(note);
                thread.start();
                threadMap.put(note, thread);
            }
        }

        public void stopNote(int note) {
            PlayThread thread = threadMap.get(note);

            if (thread != null) {
                threadMap.remove(note);
            }
        }

        public boolean isNotePlaying(int note) {
            return threadMap.get(note) != null;
        }

        private class PlayThread extends Thread {
            int note;
            AudioTrack audioTrack;

            public PlayThread(int note) {
                this.note = note;
            }

            @Override
            public void run() { // TODO: fix clicking at beginning of .wav file
                try{
                    String path = notesFolder + "/" + SOUND_MAP.get(note) + ".wav";
                    AssetManager assetManager = context.getAssets();
                    AssetFileDescriptor ad = assetManager.openFd(path);
                    long fileSize = ad.getLength();
                    int bufferSize = audioTrack.getMinBufferSize(44100,AudioFormat.CHANNEL_CONFIGURATION_STEREO, AudioFormat.ENCODING_PCM_16BIT);
                    byte[] buffer = new byte[bufferSize];

                    audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                            AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);

                    float logVolume = (float) (1 - (Math.log(MAX_VOLUME - CURRENT_VOLUME) / Math.log(MAX_VOLUME)));
                    audioTrack.setStereoVolume(logVolume, logVolume);

                    audioTrack.play();
                    InputStream audioStream = null;
                    int headerOffset = 0x2C; long bytesWritten = 0; int bytesRead = 0;

                    audioStream = assetManager.open(path);
                    audioStream.read(buffer, 0, headerOffset);

                    while (bytesWritten < fileSize - headerOffset) {
                        bytesRead = audioStream.read(buffer, 0, bufferSize);
                        bytesWritten += audioTrack.write(buffer, 0, bytesRead);
                    }

                    audioTrack.stop();
                    audioTrack.release();

                } catch (FileNotFoundException e) {
                    // TODO
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                }    finally {
                    if (audioTrack != null) {
                        audioTrack.release();
                    }
                }
            }


        }
}
