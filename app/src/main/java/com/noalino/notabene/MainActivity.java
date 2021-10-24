package com.noalino.notabene;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String NOTE_CONTENT = "com.noalino.mypersonalnotes.NOTE_CONTENT";
    private String tempNoteContent = "TODO: Edit note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity","onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity","onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity","onStop");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity","onRestart");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity","onDestroy");
    }

    /** Called when the user taps the Add button */
    public void openNote(View view) {
        Intent intent = new Intent(this, DisplayNoteActivity.class);
        intent.putExtra(NOTE_CONTENT, tempNoteContent);
        startActivity(intent);
    }
}