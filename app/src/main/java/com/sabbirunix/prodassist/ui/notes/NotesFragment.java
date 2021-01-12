package com.sabbirunix.prodassist.ui.notes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sabbirunix.prodassist.R;

import java.util.ArrayList;
import java.util.Arrays;

public class NotesFragment extends Fragment {

    private NotesViewModel notesViewModel;
    FloatingActionButton fabAddNote;
    RecyclerView recyclerViewNote;
    NotesDBHelper notesDBHelper;
    ArrayList<String> noteID, noteTitle, noteCategory, noteDetails;
    NotesAdapter notesAdapter;
    ImageView emptyNotesImg;
    TextView emptyNotesTxt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notes, container, false);

        fabAddNote = root.findViewById(R.id.fab_add_notes);
        emptyNotesImg = root.findViewById(R.id.empty_note_img);
        emptyNotesTxt = root.findViewById(R.id.empty_note_txt);

        //declaring and finding the reyclerView from the later_frags_xml
        recyclerViewNote = root.findViewById(R.id.notes_recyclerview);

        notesDBHelper = new NotesDBHelper(getContext());
        noteID = new ArrayList<>();
        noteTitle = new ArrayList<>();
        noteCategory = new ArrayList<>();
        noteDetails = new ArrayList<>();
        displayNotes();

        notesAdapter = new NotesAdapter(getContext(), noteID, noteTitle, noteCategory, noteDetails);
        recyclerViewNote.setAdapter(notesAdapter);
//        recyclerViewNote.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewNote.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        recyclerViewNote.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.HORIZONTAL));


        //Handling the userClick on fabRegular
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NotesAddActivity.class);
                startActivity(intent);
            }
        });



/*
        notesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
*/
        return root;
    }

    void displayNotes() {
        Cursor cursor = notesDBHelper.displayNotes();
        if (cursor.getCount() == 0) {
            emptyNotesImg.setVisibility(View.VISIBLE);
            emptyNotesTxt.setVisibility(View.VISIBLE);
//            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                noteID.add(cursor.getString(0)); //getting noteID
                noteTitle.add(cursor.getString(1)); //getting noteTitle
                noteCategory.add(cursor.getString(2)); //getting noteCategory
                noteDetails.add(cursor.getString(3)); //getting noteDetails
            }
            emptyNotesImg.setVisibility(View.GONE);
            emptyNotesTxt.setVisibility(View.GONE);
        }
    }
}