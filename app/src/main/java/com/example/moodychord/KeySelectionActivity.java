package com.example.moodychord;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class KeySelectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_selection);

        Button keyC = findViewById(R.id.keyC);
        Button keyCsharp = findViewById(R.id.keyCsharp);
        Button keyD = findViewById(R.id.keyD);
        Button keyDsharp = findViewById(R.id.keyDsharp);
        Button keyE = findViewById(R.id.keyE);
        Button keyF = findViewById(R.id.keyF);
        Button keyFsharp = findViewById(R.id.keyFsharp);
        Button keyG = findViewById(R.id.keyG);
        Button keyGsharp = findViewById(R.id.keyGsharp);
        Button keyA = findViewById(R.id.keyA);
        Button keyAsharp = findViewById(R.id.keyAsharp);
        Button keyB = findViewById(R.id.keyB);

        keyC.setOnClickListener(v -> returnSelectedKey("C"));
        keyCsharp.setOnClickListener(v -> returnSelectedKey("C#"));
        keyD.setOnClickListener(v -> returnSelectedKey("D"));
        keyDsharp.setOnClickListener(v -> returnSelectedKey("D#"));
        keyE.setOnClickListener(v -> returnSelectedKey("E"));
        keyF.setOnClickListener(v -> returnSelectedKey("F"));
        keyFsharp.setOnClickListener(v -> returnSelectedKey("F#"));
        keyG.setOnClickListener(v -> returnSelectedKey("G"));
        keyGsharp.setOnClickListener(v -> returnSelectedKey("G#"));
        keyA.setOnClickListener(v -> returnSelectedKey("A"));
        keyAsharp.setOnClickListener(v -> returnSelectedKey("A#"));
        keyB.setOnClickListener(v -> returnSelectedKey("B"));

    }

    private void returnSelectedKey(String key) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("selected_key", key);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
