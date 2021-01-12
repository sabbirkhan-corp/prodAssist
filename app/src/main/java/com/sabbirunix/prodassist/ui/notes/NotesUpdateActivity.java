package com.sabbirunix.prodassist.ui.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sabbirunix.prodassist.R;

public class NotesUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    EditText noteTitleU, noteCategoryU, noteDetailsU;
    Button noteUpdate, noteCancelU;
    String id, title, category, details;
    NotesDBHelper notesDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_update);


        noteTitleU = findViewById(R.id.note_title_up);
        noteCategoryU = findViewById(R.id.note_category_up);
        noteDetailsU = findViewById(R.id.note_details_up);
        noteUpdate = findViewById(R.id.note_save_btn_up);
        noteCancelU = findViewById(R.id.note_cancel_btn_up);

        //first we call this then anclick update
        getSetIntentData();

        noteUpdate.setOnClickListener(this);
        noteCancelU.setOnClickListener(this);

    }

    //showing the delete icon in updateNote
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                confirmDialog();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                notesDBHelper = new NotesDBHelper(NotesUpdateActivity.this);
                notesDBHelper.deleteNote(id);
                finish();//to finish activity and get back
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //this doesn't do anything //just returns
            }
        });
        builder.create().show(); //to actually show the dialog //pst
    }

    void getSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title")
                && getIntent().hasExtra("category") && getIntent().hasExtra("details")) {

            //Getting data from intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            category = getIntent().getStringExtra("category");
            details = getIntent().getStringExtra("details");

            //setting intent data
            noteTitleU.setText(title);
            noteCategoryU.setText(category);
            noteDetailsU.setText(details);

        } else {
            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.note_save_btn_up: {
                if (!isTextEmpty()) {
                    notesDBHelper = new NotesDBHelper(NotesUpdateActivity.this);
                    notesDBHelper.updateNote(
                            id,
                            noteTitleU.getText().toString(),
                            noteCategoryU.getText().toString(),
                            noteDetailsU.getText().toString()
                    );
                    lastFragmentPop(); //getting back on lastFragment
                }
            }
            break;
            case R.id.note_cancel_btn_up:
                Toast.makeText(getApplicationContext(), "Note not updated", Toast.LENGTH_SHORT).show();
                lastFragmentPop(); //getting back on lastFragment
                break;
        }

    }


    //for checking if the EditTexts are empty and taking appropriate actions
    boolean isTextEmpty() {
        if (noteTitleU.getText().toString().trim().isEmpty()) {
            noteTitleU.setError("Title can't be empty");
            noteTitleU.requestFocus();
            return true;
        } else if (noteCategoryU.getText().toString().trim().isEmpty()) {
            noteCategoryU.setError("Category can't be empty");
            noteCategoryU.requestFocus();
            return true;
        } else if (noteDetailsU.getText().toString().trim().isEmpty()) {
            noteDetailsU.setError("Please, add note details");
            noteDetailsU.requestFocus();
            return true;
        } else {
            return false;
        }
    }


    public void lastFragmentPop() {
        //For getting back on the lastFragment
        //works fine for now, but deprecated in api 28
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }

}