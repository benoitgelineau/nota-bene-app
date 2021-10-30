package com.noalino.notabene;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
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

        Intent intent = getIntent();
        String noteId = intent.getStringExtra(MainActivity.NOTE_ID);

        FloatingActionButton saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> save(noteId + ".txt"));

        // Capture the layout's TextView and set the string as its text
        noteContent = findViewById(R.id.noteContent);
        noteContent.setText(getFileContent(noteId + ".txt"));
    }

    private void save(String fileName) {
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

    private boolean fileExists(String fileName){
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }

    private String getFileContent(String fileName) {
        String content = "";
        if (fileExists(fileName)) {
            content = openFile(fileName);
        }
        return content;
    }

    private String openFile(String fileName) {
        String fileContent = "";
        try {
            InputStream in = openFileInput(fileName);
            if ( in != null) {
                InputStreamReader tmp = new InputStreamReader( in );
                BufferedReader reader = new BufferedReader(tmp);
                String str;
                StringBuilder buf = new StringBuilder();
                while ((str = reader.readLine()) != null) {
                    buf.append(str).append("\n");
                } in .close();
                fileContent = buf.toString();
            }
        } catch (java.io.FileNotFoundException e) {} catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
        return fileContent;
    }
}