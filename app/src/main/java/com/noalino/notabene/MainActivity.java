package com.noalino.notabene;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public static final String NOTE_CONTENT = "com.noalino.notabene.NOTE_CONTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerButtons();
    }

    private void registerButtons() {
        registerButton(R.id.floatingActionButton, () -> {
            openNote();
        });
    }

    private void registerButton(int buttonResourceId, Runnable r) {
        findViewById(buttonResourceId).setOnClickListener(v -> r.run());
    }

    /** Called when the user taps the Add button */
    private void openNote() {
        Intent intent = new Intent(this, DisplayNoteActivity.class);
        intent.putExtra(NOTE_CONTENT, "");
        startActivity(intent);
    }
}