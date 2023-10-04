package com.riyad.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AddEditNotesActivity extends AppCompatActivity {


    private TextInputEditText title;
    private TextInputEditText description;
    private NumberPicker priorityPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        title = findViewById(R.id.TitleEdittext);
        description = findViewById(R.id.DescriptionEdittext);

        priorityPicker = findViewById(R.id.PriorityPicker);

        priorityPicker.setMinValue(0);
        priorityPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_close_24);
        setTitle("Add Note");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.note_save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void saveNote() {
        String NoteTitle = title.getText().toString();
        String NoteDescription = description.getText().toString();
        int NotePriority = priorityPicker.getValue();

        if(NoteTitle.trim().isEmpty() || NoteDescription.trim().isEmpty()){
            Toast.makeText(this, "Insert a Title and Description.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra("TitleAdd", NoteTitle);
        data.putExtra("DescriptionAdd",NoteDescription);
        data.putExtra("PriorityAdd", NotePriority);

        setResult(RESULT_OK, data);

        finish();

    }
}