package com.example.moodychord;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Chord extends BaseObservable {
    private String displayName; // Chord name for display (e.g., E Major, G# Minor)
    private String playbackName; // Chord name for playback (e.g., C Major, D Minor)

    public Chord(String displayName, String playbackName) {
        this.displayName = displayName;
        this.playbackName = playbackName;
    }

    @Bindable
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        notifyPropertyChanged(BR.displayName);
    }

    public String getPlaybackName() {
        return playbackName;
    }

    public void setPlaybackName(String playbackName) {
        this.playbackName = playbackName;
    }
}