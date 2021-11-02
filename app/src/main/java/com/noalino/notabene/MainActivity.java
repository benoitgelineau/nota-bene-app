package com.noalino.notabene;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public static final String NOTES_FILENAME = "notes.json";
    public static final String NOTE_ID = "com.noalino.notabene.NOTE_ID";
    private final List<NoteBuilder> notesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerButtons();

        RecyclerView notesRecycler = findViewById(R.id.notes);

        NotesAdapter nAdapter = new NotesAdapter(notesList);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        notesRecycler.setLayoutManager(mLayoutManager);
        notesRecycler.setItemAnimator(new DefaultItemAnimator());
        notesRecycler.setAdapter(nAdapter);

        if (notesFileExists()) {
            getNotesList();
        } else {
            createNotesFile();
        }
    }

    private void registerButtons() {
        registerButton(R.id.addNoteActionButton, () -> {
            openNote(getUuid());
        });
    }

    private void registerButton(int buttonResourceId, Runnable r) {
        findViewById(buttonResourceId).setOnClickListener(v -> r.run());
    }

    private String getUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public boolean notesFileExists(){
        File file = getBaseContext().getFileStreamPath(NOTES_FILENAME);
        return file.exists();
    }

    private void createNotesFile() {
        try (BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(openFileOutput(NOTES_FILENAME, 0)))) {
            out.write(new JSONArray().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readNotesFile() {
        String result = "";
        try (InputStream in = openFileInput(NOTES_FILENAME);
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

    private void getNotesList() {
        try {
            JSONArray notesListJson = new JSONArray(readNotesFile());
            ObjectMapper objectMapper = new ObjectMapper();
            for (int i = 0; i < notesListJson.length(); i++) {
                JSONObject noteJson = notesListJson.getJSONObject(i);
                NoteBuilder note = objectMapper.readValue(noteJson.toString(), NoteBuilder.class);
                notesList.add(note);
            }
        } catch (JSONException | JsonProcessingException e) {
            Toast.makeText(this, "Exception: " + e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void openNote(String noteId) {
        Intent intent = new Intent(this, DisplayNoteActivity.class);
//        intent.putExtra(NOTE_ID, noteId);
        startActivity(intent);
    }
}