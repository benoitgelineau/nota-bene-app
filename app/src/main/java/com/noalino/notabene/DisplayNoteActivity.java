package com.noalino.notabene;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.OutputStreamWriter;

public class DisplayNoteActivity extends AppCompatActivity {

    EditText noteContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Lifecycle", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);// Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        } else {
//          TODO Better handle this
            throw new NullPointerException("Something went wrong");
        }

        // Capture the layout's TextView and set the string as its text
        noteContent = findViewById(R.id.noteContent);
        noteContent.setText("TODO: Edit note");

        findViewById(R.id.saveButton).setOnClickListener(v -> Save("note.txt"));
    }

    public void Save(String fileName) {
//      TODO Use Buffer
        try (OutputStreamWriter out =
                     new OutputStreamWriter(openFileOutput(fileName, 0))) {
            out.write(noteContent.getText().toString());
            Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
//      TODO Use more specific error
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }
}