package com.example.moodychord;

import android.content.Context;
import android.media.SoundPool;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayProgression {

    private SoundPool soundPool;
    private Map<String, Integer> soundIds; // Map to store sound IDs for C Major scale chords
    private int currentChordIndex = 0; // Track the current chord in the progression
    private List<Chord> progressionChords; // Array to store the current progression chords
    private Context context;

    public PlayProgression(Context context) {
        this.context = context;
        initializeSoundPool();
        loadChordSounds();
    }

    /**
     * Initializes the SoundPool.
     */
    private void initializeSoundPool() {
        soundPool = new SoundPool.Builder()
                .setMaxStreams(7) // Maximum number of simultaneous streams
                .build();
        soundIds = new HashMap<>();
    }

    /**
     * Loads the C Major scale chord audio files into SoundPool.
     */
    private void loadChordSounds() {
        soundIds.put("C Major", soundPool.load(context, R.raw.c_major, 1));
        soundIds.put("D Minor", soundPool.load(context, R.raw.d_minor, 1));
        soundIds.put("E Minor", soundPool.load(context, R.raw.e_minor, 1));
        soundIds.put("F Major", soundPool.load(context, R.raw.f_major, 1));
        soundIds.put("G Major", soundPool.load(context, R.raw.g_major, 1));
        soundIds.put("A Minor", soundPool.load(context, R.raw.a_minor, 1));
        soundIds.put("B Diminished", soundPool.load(context, R.raw.b_diminished, 1));
    }

    /**
     * Starts playing the chord progression.
     *
     * @param progressionChords The list of chords in the progression (C Major scale chords).
     * @param key               The selected key (e.g., "C", "D", "F#").
     */
    public void playProgression(List<Chord> progressionChords, String key) {
        this.progressionChords = progressionChords;
        this.currentChordIndex = 0; // Reset to the first chord
        playNextChord(key);
    }

    /**
     * Plays the next chord in the progression.
     *
     * @param key The selected key (e.g., "C", "D", "F#").
     */
    private void playNextChord(String key) {
        if (currentChordIndex >= progressionChords.size()) {
            return; // Stop after playing all chords
        }

        // Calculate pitch shift based on the selected key
        int semitones = getSemitonesFromC(key);
        float pitch = (float) Math.pow(2, semitones / 12.0); // Pitch multiplier

        // Get the sound ID for the current chord
        String chordName = progressionChords.get(currentChordIndex).getPlaybackName();

        int soundId = soundIds.getOrDefault(chordName, -1);


        if (soundId != -1) {
            Log.d("PlayProgression", "Playing chord: " + chordName + " in key: " + key);
            soundPool.play(soundId, 1.0f, 1.0f, 1, 0, pitch);
        } else {
            Log.e("PlayProgression", "Chord not found: " + chordName);
        }

        currentChordIndex++; // Move to the next chord
        if (currentChordIndex < progressionChords.size()) {
            // Add a delay before playing the next chord (e.g., 1 second)
            new android.os.Handler().postDelayed(() -> playNextChord(key), 1000); // Delay in milliseconds
        }
    }

    /**
     * Calculates the number of semitones to shift from C to the selected key.
     *
     * @param key The selected key (e.g., "C", "D", "F#").
     * @return The number of semitones to shift.
     */
    private int getSemitonesFromC(String key) {
        String[] chromaticNotes = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        for (int i = 0; i < chromaticNotes.length; i++) {
            if (chromaticNotes[i].equalsIgnoreCase(key)) {
                return i;
            }
        }
        return 0; // Default to no shift
    }


    /**
     * Releases SoundPool resources.
     */
    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}