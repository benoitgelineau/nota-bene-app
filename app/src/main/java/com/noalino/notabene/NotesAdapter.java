package com.noalino.notabene;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<NoteBuilder> localNotesList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.content);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public NotesAdapter(List<NoteBuilder> notesList) {
        localNotesList = notesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        NoteBuilder note = localNotesList.get(position);
        viewHolder.getTextView().setText(note.getContent());
    }

    @Override
    public int getItemCount() {
        return localNotesList.size();
    }
}
