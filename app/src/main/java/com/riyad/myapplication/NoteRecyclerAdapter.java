package com.riyad.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolderClass> {

    private List<Note> notes = new ArrayList<>();

    private OnItemClickListener listener;


    public NoteRecyclerAdapter() {
    }

    @NonNull
    @Override
    public NoteViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolderClass(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_design, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolderClass holder, int position) {
        Note note = notes.get(position);

        holder.titleTextView.setText(note.getTitle());
        holder.descTextView.setText(note.getDescription());
        holder.priorityTextView.setText(String.valueOf(note.getPriority()));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public Note getNotesAt(int position) {
        return notes.get(position);
    }


    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public class NoteViewHolderClass extends RecyclerView.ViewHolder {

        ConstraintLayout singleNoteItem;

        TextView priorityTextView, titleTextView, descTextView;

        public NoteViewHolderClass(@NonNull View itemView) {
            super(itemView);
            priorityTextView = itemView.findViewById(R.id.PriorityViewTextView);
            titleTextView = itemView.findViewById(R.id.TitleViewTextView);
            descTextView = itemView.findViewById(R.id.DescriptionViewTextView);

            singleNoteItem = itemView.findViewById(R.id.SingleNoteItem);

            singleNoteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(notes.get(position));
                    }
                }
            });


        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
