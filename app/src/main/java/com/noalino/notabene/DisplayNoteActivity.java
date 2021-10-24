package com.noalino.notabene;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class DisplayNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Lifecycle", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        // Capture the layout's TextView and set the string as its text
        EditText textView = findViewById(R.id.noteContent);
        textView.setText("TODO: Edit note");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("DisplayNoteActivity", "onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DisplayNoteActivity","onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("DisplayNoteActivity","onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        // TODO: Send note content to main activity with intent
        Log.d("DisplayNoteActivity","onStop");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("DisplayNoteActivity","onRestart");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DisplayNoteActivity","onDestroy");
    }
}