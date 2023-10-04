package com.riyad.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int Edit_NOTE_REQUEST = 2;

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton = findViewById(R.id.AddFloatingActionButton);
        recyclerView = findViewById(R.id.NotesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteRecyclerAdapter adapter = new NoteRecyclerAdapter();

        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditNotesActivity.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
            }
        });
        
        noteViewModel = ViewModelProviders.of(MainActivity.this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(MainActivity.this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);
                Toast.makeText(MainActivity.this, "onChanged.", Toast.LENGTH_SHORT).show();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                noteViewModel.delete(adapter.getNotesAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NoteRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra("Note_id", note.getId());
                intent.putExtra("Note_title", note.getTitle());
                intent.putExtra("Note_description", note.getDescription());
                intent.putExtra("Note_priority", note.getPriority());
                startActivityForResult(intent, Edit_NOTE_REQUEST);
                Toast.makeText(MainActivity.this, "Clicked!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra("TitleAdd");
            String description = data.getStringExtra("DescriptionAdd");
            int priority = data.getIntExtra("PriorityAdd", 1);

            Note note = new Note(title, description, priority);
            noteViewModel.insert(note);

            Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
        } else if (requestCode == Edit_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra("TitleUpdate");
            String description = data.getStringExtra("DescriptionUpdate");
            int priority = data.getIntExtra("PriorityUpdate", 1);

            Note note = new Note(title, description, priority);
            note.setId(data.getIntExtra("NoteId", 1));
            noteViewModel.update(note);

            Toast.makeText(this, "Note Updated!", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Note Updating failed!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.DeleteNotes:
                noteViewModel.deleteAllNote();
                Toast.makeText(this, "All Note Deleted.", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}