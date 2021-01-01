package com.sabbirunix.prodassist.ui.later;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabbirunix.prodassist.R;

public class LaterAdapter extends RecyclerView.Adapter<LaterAdapter.LaterHolder> {

//    StringBuilder[] timeLater;
//    StringBuilder[] nameLater;
//    StringBuilder[] dateLater;
    String[] timeLater;
    String[] nameLater;
    String[] dateLater;

    public LaterAdapter(String[] timeLater, String[] nameLater, String[] dateLater) {
        this.timeLater = timeLater;
        this.nameLater = nameLater;
        this.dateLater = dateLater;
    }


    @NonNull
    @Override
    public LaterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_later_single_row_recycler, parent, false);
        return new LaterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaterHolder holder, int position) {
        holder.timeLaterText.setText(timeLater[position]);
        holder.nameLaterText.setText(nameLater[position]);
        holder.dateLaterText.setText(dateLater[position]);

        if(position%2 ==1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#efefef"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#fefefe"));
        }
    }

    @Override
    public int getItemCount() {
        //returning namelength because start time and end time may be (can be?) empty sometimes
        return nameLater.length;
    }


    /**
    //ViewHolder Class
    //finding the view id's from the single row layout here
    //by passing reference from the singlerow xml
    **/
    public class LaterHolder extends RecyclerView.ViewHolder {
        TextView timeLaterText, nameLaterText, dateLaterText;
        public LaterHolder(@NonNull View itemView) {
            super(itemView);
            timeLaterText = itemView.findViewById(R.id.time_today_text);
            nameLaterText = itemView.findViewById(R.id.name_today_text);
            dateLaterText = itemView.findViewById(R.id.date_today_text);
        }
    }
}
