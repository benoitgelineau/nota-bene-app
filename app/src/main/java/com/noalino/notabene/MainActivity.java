package com.noalino.notabene;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String NOTE_CONTENT = "com.noalino.notabene.NOTE_CONTENT";
    private List<NoteBuilder> notesList = new ArrayList<>();
    private NotesAdapter nAdapter;
    private RecyclerView notesRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerButtons();

        notesRecycler = (RecyclerView) findViewById(R.id.notes);

        nAdapter = new NotesAdapter(notesList);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        notesRecycler.setLayoutManager(mLayoutManager);
        notesRecycler.setItemAnimator(new DefaultItemAnimator());
        notesRecycler.setAdapter(nAdapter);

        prepareNotes();
    }

    private void registerButtons() {
        registerButton(R.id.addNoteActionButton, () -> {
            openNote();
        });
    }

    private void registerButton(int buttonResourceId, Runnable r) {
        findViewById(buttonResourceId).setOnClickListener(v -> r.run());
    }

    private void prepareNotes() {
        File directory;
        directory = getFilesDir();
        File[] files = directory.listFiles();
        String theFile;
        for (int i = 1; i <= files.length; i++) {
            theFile = "Note" + i + ".txt";
            NoteBuilder note = new NoteBuilder(Open(theFile));
            notesList.add(note);
        }

    }

    public String Open(String fileName) {
        String content = "";
        try {
            InputStream in = openFileInput(fileName);
            if ( in != null) {
                InputStreamReader tmp = new InputStreamReader( in );
                BufferedReader reader = new BufferedReader(tmp);
                String str;
                StringBuilder buf = new StringBuilder();
                while ((str = reader.readLine()) != null) {
                    buf.append(str + "\n");
                } in .close();

                content = buf.toString();
            }
        } catch (java.io.FileNotFoundException e) {} catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }

        return content;
    }

    /** Called when the user taps the Add button */
    private void openNote() {
        Intent intent = new Intent(this, DisplayNoteActivity.class);
        intent.putExtra(NOTE_CONTENT, "");
        startActivity(intent);
    }
}