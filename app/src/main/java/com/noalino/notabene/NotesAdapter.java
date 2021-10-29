package com.noalino.notabene;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    private List<NoteBuilder> notesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView content;

        public MyViewHolder(View view) {
            super(view);
            content = (TextView) view.findViewById(R.id.content);

        }
    }

    public NotesAdapter(List<NoteBuilder> notesList) {
        this.notesList = notesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NoteBuilder note = notesList.get(position);
        holder.content.setText(note.getContent());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
