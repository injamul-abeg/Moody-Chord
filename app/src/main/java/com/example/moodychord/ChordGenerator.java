package com.example.moodychord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.util.Log;

public class ChordGenerator {

    private static final int[] MAJOR_SCALE_PATTERN = {2, 2, 1, 2, 2, 2, 1};
    private static final String[] CHROMATIC_NOTES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    // Define diatonic chord positions for major keys
    private static final String[] CHORD_QUALITIES = {"Major", "Minor", "Minor", "Major", "Major", "Minor", "Diminished"};
    // Define diatonic chord positions for the C Major scale
    //private static final String[] C_MAJOR_CHORDS = {"C Major", "D Minor", "E Minor", "F Major", "G Major", "A Minor", "B Diminished"};

    // Define common progressions for different moods
    private static final Map<Mood, int[]> MOOD_PROGRESSIONS = new HashMap<>();

    static {
        MOOD_PROGRESSIONS.put(Mood.HAPPY, new int[]{1, 4, 5, 6}); // I-IV-V-vi
        MOOD_PROGRESSIONS.put(Mood.ROMANTIC, new int[]{6, 2, 5, 4}); // vi-ii-V-IV
        MOOD_PROGRESSIONS.put(Mood.MYSTERIOUS, new int[]{2, 4, 6, 7}); // ii-IV-vi-vii°
        MOOD_PROGRESSIONS.put(Mood.SAD, new int[]{6, 4, 1, 5}); // vi-IV-I-V
        MOOD_PROGRESSIONS.put(Mood.ENERGETIC, new int[]{1, 5, 6, 3}); // I-V-vi-iii
    }

    public enum Mood {
        HAPPY, ROMANTIC, MYSTERIOUS, SAD, ENERGETIC
    }

    /**
     * Generates chords based on mood and key.
     * Returns chord names that reflect the selected key while using C Major scale chord names.
     @return A list of Chord objects.
     */

    // Generate a major scale for the given root note
    private List<String> generateMajorScale(String rootNote) {
        List<String> scale = new ArrayList<>();
        int index = getIndex(rootNote);

        for (int step : MAJOR_SCALE_PATTERN) {
            scale.add(CHROMATIC_NOTES[index]);
            index = (index + step) % 12;
        }
        return scale;
    }

    // Get the index of the root note in the chromatic scale
    private int getIndex(String note) {
        for (int i = 0; i < CHROMATIC_NOTES.length; i++) {
            if (CHROMATIC_NOTES[i].equalsIgnoreCase(note)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Invalid key: " + note);
    }
    public List<Chord> getChordsForMoodAndKey(Mood mood, String key) {
        List<Chord> chords = new ArrayList<>();
        List<String> majorScale = generateMajorScale(key);

        // Get the progression for the given mood, or default if not found
        int[] progression = MOOD_PROGRESSIONS.getOrDefault(mood, new int[]{1, 4, 5, 6});

        // Log the selected progression (optional for debugging)
        Log.d("ChordGenerator", "Progression for mood " + mood + ": " + java.util.Arrays.toString(progression));

        // C Major scale chord names for playback
        String[] cMajorChords = {"C Major", "D Minor", "E Minor", "F Major", "G Major", "A Minor", "B Diminished"};

        assert progression != null;
        for (int degree : progression) {
            int index = degree - 1; // Degrees are 1-based, so adjust the index
            String displayName = majorScale.get(index) + " " + CHORD_QUALITIES[index]; // Chord name for display
            String playbackName = cMajorChords[index]; // Chord name for playback
            chords.add(new Chord(displayName, playbackName));
        }

        return chords;
    }
}