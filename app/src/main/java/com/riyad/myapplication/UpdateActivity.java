package com.riyad.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class UpdateActivity extends AppCompatActivity {
    private static final String TAG="UpdateActivity";

    private NoteViewModel viewModel;
    private Note update_note;

    TextInputEditText u_title;
    TextInputEditText u_description;
    NumberPicker u_priorityPicker;

    String x_title;
    String x_description;
    int id;
    int x_priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_close_24);
        setTitle("Update Note");

        u_title = findViewById(R.id.UpdateTitleEdittext);
        u_description = findViewById(R.id.UpdateDescriptionEdittext);
        u_priorityPicker = findViewById(R.id.UpdatePriorityPicker);

        Intent intent = getIntent();
        id = intent.getIntExtra("Note_id", 0);
        x_priority = intent.getIntExtra("Note_priority", 0);
        x_title = intent.getStringExtra("Note_title");
        x_description = intent.getStringExtra("Note_description");


        u_title.setText(x_title);
        u_description.setText(x_description);
        u_priorityPicker.setMinValue(0);
        u_priorityPicker.setMaxValue(10);
        u_priorityPicker.setValue(x_priority);

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
                UpdateNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void UpdateNote() {
        String NoteTitle = u_title.getText().toString();
        String NoteDescription = u_description.getText().toString();
        int NotePriority = u_priorityPicker.getValue();

        Toast.makeText(this, NoteTitle+NoteDescription+NotePriority, Toast.LENGTH_SHORT).show();
        System.out.println(NoteTitle+NoteDescription+NotePriority);

        if(NoteTitle.trim().isEmpty() || NoteDescription.trim().isEmpty()){
            Toast.makeText(this, "Insert a Title and Description.", Toast.LENGTH_SHORT).show();
            return;
        }
        update_note = new Note(u_title.getText().toString(),u_description.getText().toString(),u_priorityPicker.getValue());
        update_note.setId(id);

        Intent data = new Intent();
        data.putExtra("TitleUpdate", NoteTitle);
        data.putExtra("DescriptionUpdate",NoteDescription);
        data.putExtra("PriorityUpdate", NotePriority);
        if(id != -1){
            data.putExtra("NoteId", id);
        }
        Log.d(TAG, "UpdateNote: ");
        setResult(RESULT_OK, data);

        finish();

    }
}