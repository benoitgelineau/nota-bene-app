package com.noalino.notabene;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DisplayNoteActivity extends AppCompatActivity {
    boolean isNewNote;
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

        isNewNote = noteId == null;

        FloatingActionButton saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> save(noteId));

        noteContent = findViewById(R.id.noteContent);
        // Capture the layout's TextView and set the note content as its text
        if (!isNewNote) {
            noteContent.setText(getNoteContent(noteId));
        }
    }

    private String getUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private String readNotesFile() {
        String result = "";
        try (InputStream in = openFileInput(MainActivity.NOTES_FILENAME);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            StringBuilder buffer = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            result = buffer.toString();
        } catch (IOException e) {
            Toast.makeText(this, "Exception: " + e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return result;
    }

    private List<NoteBuilder> getNotesList() {
        List<NoteBuilder> result = new ArrayList<>();
        try {
            JSONArray notesListJson = new JSONArray(readNotesFile());
            ObjectMapper objectMapper = new ObjectMapper();
            for (int i = 0; i < notesListJson.length(); i++) {
                JSONObject noteJson = notesListJson.getJSONObject(i);
                NoteBuilder note = objectMapper.readValue(noteJson.toString(), NoteBuilder.class);
                result.add(note);
            }
        } catch (JSONException | JsonProcessingException e) {
            Toast.makeText(this, "Exception: " + e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return result;
    }

    private String getNoteContent(String noteId) {
        for (NoteBuilder note : getNotesList()) {
            if (note.getId().equals(noteId)) {
                return note.getContent();
            }
        }
        return null;
    }

    private void save(String noteId) {
        try {
            JSONArray newNotes = new JSONArray(readNotesFile());
            if (noteId == null) {
                // add new note
                JSONObject newNote = new JSONObject();
                newNote.put("id", getUuid());
                newNote.put("content", noteContent.getText().toString());
                newNotes.put(newNote);
            } else {
                // retrieve & update existing note
                for (int i = 0; i < newNotes.length(); i++) {
                    JSONObject newNote = newNotes.getJSONObject(i);
                    if (newNote.get("id") == noteId) {
                        newNote.put("content", noteContent.getText().toString());
                    }
                }
            }
            writeToFile(newNotes.toString());
            Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (JSONException e) {
            Toast.makeText(this, "Exception: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void writeToFile(String newNotes) {
        try (BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(openFileOutput(MainActivity.NOTES_FILENAME, 0)))) {
            out.write(newNotes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}