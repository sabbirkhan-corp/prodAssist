package com.sabbirunix.prodassist.ui.notes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sabbirunix.prodassist.R;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {


    private Context context;
    private ArrayList noteID, noteTitle, noteCategory, noteDetails;

    public NotesAdapter(Context context, ArrayList noteID, ArrayList noteTitle, ArrayList noteCategory, ArrayList noteDetails) {
        this.context = context;
        this.noteID = noteID;
        this.noteTitle = noteTitle;
        this.noteCategory = noteCategory;
        this.noteDetails = noteDetails;
    }


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_notes_single_row_recycler, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.noteDisplayID.setText(String.valueOf(noteID.get(position)));
        holder.noteDisplayTitle.setText(String.valueOf(noteTitle.get(position)));
        holder.noteDisplayCategory.setText(String.valueOf(noteCategory.get(position)));
        holder.noteDisplayDetails.setText(String.valueOf(noteDetails.get(position)));
        holder.noteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NotesUpdateActivity.class);
                intent.putExtra("id", String.valueOf(noteID.get(position)));
                intent.putExtra("title", String.valueOf(noteTitle.get(position)));
                intent.putExtra("category", String.valueOf(noteCategory.get(position)));
                intent.putExtra("details", String.valueOf(noteDetails.get(position)));
                context.startActivity(intent);
            }
        });

//        if (position % 2 == 1) {
//            holder.itemView.setBackgroundColor(Color.parseColor("#efefef"));
//        } else {
//            holder.itemView.setBackgroundColor(Color.parseColor("#fefefe"));
//        }
    }

    @Override
    public int getItemCount() {
        return noteID.size();
    }


    public class NoteHolder extends RecyclerView.ViewHolder {
        TextView noteDisplayID, noteDisplayTitle, noteDisplayCategory, noteDisplayDetails;
        CardView noteCard;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            noteDisplayID = itemView.findViewById(R.id.note_display_id);
            noteDisplayTitle = itemView.findViewById(R.id.note_display_title);
            noteDisplayCategory = itemView.findViewById(R.id.note_display_category);
            noteDisplayDetails = itemView.findViewById(R.id.note_display_details);
            noteCard = itemView.findViewById(R.id.note_card);
//            //Animating recyclerView
//            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
//            later_single.setAnimation(translate_anim);
        }
    }
}
