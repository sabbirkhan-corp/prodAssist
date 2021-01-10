package com.sabbirunix.prodassist.ui.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sabbirunix.prodassist.MainActivity;
import com.sabbirunix.prodassist.R;
import com.sabbirunix.prodassist.ui.today.TodayFragment;

import java.net.Inet4Address;

public class NotesAddActivity extends AppCompatActivity implements View.OnClickListener {

    EditText noteTitle, noteCategory, noteDetails;
    Button noteSave, noteCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_add);

        noteTitle = findViewById(R.id.note_title);
        noteCategory = findViewById(R.id.note_category);
        noteDetails = findViewById(R.id.note_details);
        noteSave = findViewById(R.id.note_save_btn);
        noteCancel = findViewById(R.id.note_cancel_btn);

        noteSave.setOnClickListener(this);
        noteCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.note_save_btn: {
                if (!isTextEmpty()) {
                    NotesDBHelper notesDBHelper = new NotesDBHelper(NotesAddActivity.this);
                    notesDBHelper.addNote(
                            noteTitle.getText().toString(),
                            noteCategory.getText().toString(),
                            noteDetails.getText().toString()
                    );
//                    Intent intent = new Intent(getApplicationContext(), NotesFragment.class);
//                    startActivity(intent);
                    lastFragmentPop(); //getting back on lastFragment
                }
            }
            break;
            case R.id.note_cancel_btn:
                Toast.makeText(getApplicationContext(), "Note cancelled", Toast.LENGTH_SHORT).show();
                lastFragmentPop(); //getting back on lastFragment
                break;
        }

    }


    //for checking if the EditTexts are empty and taking appropriate actions
    boolean isTextEmpty() {
        if (noteTitle.getText().toString().trim().isEmpty()) {
            noteTitle.setError("Title can't be empty");
            noteTitle.requestFocus();
            return true;
        } else if (noteCategory.getText().toString().trim().isEmpty()) {
            noteCategory.setError("Category can't be empty");
            noteCategory.requestFocus();
            return true;
        } else if (noteDetails.getText().toString().trim().isEmpty()) {
            noteDetails.setError("Please, add note details");
            noteDetails.requestFocus();
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