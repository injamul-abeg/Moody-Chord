package com.example.moodychord;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moodychord.databinding.ItemChordBinding;
import java.util.ArrayList;
import java.util.List;

public class ChordAdapter extends RecyclerView.Adapter<ChordAdapter.ChordViewHolder> {
    private List<Chord> chords;

    // Constructor initializes chords as an empty list
    public ChordAdapter() {
        this.chords = new ArrayList<>(); // Initialize it as an empty list
    }

    public static class ChordViewHolder extends RecyclerView.ViewHolder {
        private final ItemChordBinding binding;

        public ChordViewHolder(ItemChordBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Chord chord) {
            binding.setChord(chord); // Set the Chord object for DataBinding
            binding.executePendingBindings(); // Ensures immediate UI updates
        }
    }

    @NonNull
    @Override
    public ChordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemChordBinding binding = ItemChordBinding.inflate(inflater, parent, false);
        return new ChordViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChordViewHolder holder, int position) {
        Chord chord = chords.get(position);
        holder.binding.setChord(chord); // Bind the Chord object for DataBinding
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return chords.size();
    }


    // Update the chords list with new data
    public void updateChords(List<Chord> newChords) {
        if (newChords != null) { // Check for null to prevent crashes
            chords.clear();
            chords.addAll(newChords);
            notifyDataSetChanged();
        }
    }

    // Return chord progression
    public List<Chord> getCurrentChords() {
        return new ArrayList<>(chords);
    }
}
