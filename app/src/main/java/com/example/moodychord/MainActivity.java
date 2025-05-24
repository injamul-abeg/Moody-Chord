package com.example.moodychord;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.moodychord.databinding.ActivityMainBinding;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_KEY_SELECTION = 1;
    private ActivityMainBinding binding;
    private ChordAdapter chordAdapter;
    private String selectedKey = "C";  // Default key
    private String selectedMood = "Happy";  // Default mood
    private PlayProgression playProgression; // Instance of PlayProgression

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Display default selected key
        binding.tvSelectedKey.setText("Key: " + selectedKey);

        // Open key selection page when button is clicked
        binding.btnKeySelection.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, KeySelectionActivity.class);
            startActivityForResult(intent, REQUEST_KEY_SELECTION);
        });

        // Initialize Mood Selection Dropdown (Spinner)
        Spinner spinnerMoodSelection = findViewById(R.id.spinnerMoodSelection);

        // Create adapter using mood_array
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.mood_array, R.layout.spinner_dropdown_item);

        // Set custom dropdown layout
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply adapter to spinner
        spinnerMoodSelection.setAdapter(adapter);

        // Initialize Mood Spinner
        binding.spinnerMoodSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMood = parent.getItemAtPosition(position).toString();
                updateChordSuggestions();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // RecyclerView for Chord Display
        binding.recyclerViewChords.setLayoutManager(new GridLayoutManager(this, 4));
        chordAdapter = new ChordAdapter();
        binding.recyclerViewChords.setAdapter(chordAdapter);

        // Initialize PlayProgression
        playProgression = new PlayProgression(this);

        // Play Progression Button
        binding.btnPlayProgression.setOnClickListener(v -> playChordProgression());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_KEY_SELECTION && resultCode == RESULT_OK && data != null) {
            selectedKey = data.getStringExtra("selected_key");
            binding.tvSelectedKey.setText("Key: " + selectedKey);
            updateChordSuggestions();
        }
    }

    private void updateChordSuggestions() {
        ChordGenerator chordGenerator = new ChordGenerator();
        ChordGenerator.Mood mood = ChordGenerator.Mood.valueOf(selectedMood.toUpperCase());
        List<Chord> suggestedChords = chordGenerator.getChordsForMoodAndKey(mood, selectedKey);
        chordAdapter.updateChords(suggestedChords);
    }

    /**
     * Plays the chord progression using the PlayProgression class.
     */
    private void playChordProgression() {
        // Get the current chord progression from the adapter
        List<Chord> chords = chordAdapter.getCurrentChords();

        // Play the progression
        playProgression.playProgression(chords, selectedKey);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playProgression != null) {
            playProgression.release(); // Release SoundPool resources
        }
    }
}