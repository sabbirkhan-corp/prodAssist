package com.sabbirunix.prodassist.ui.today;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabbirunix.prodassist.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.TodoHolder> {

    Context context;
    ArrayList<String> textTimeToday, textNameToday;

    public TodayAdapter(Context context, ArrayList<String> textTimeToday, ArrayList<String> textNameToday) {
        this.context = context;
        this.textTimeToday = textTimeToday;
        this.textNameToday = textNameToday;
    }

    String[] timeToday;
    String[] nameToday;

    public TodayAdapter(String[] timeToday, String[] nameToday) {
        Log.d(TAG, "TodayAdapter: inside AdapterConstructor");
        this.timeToday = timeToday;
        this.nameToday = nameToday;
    }


    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: inside OnCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_today_single_row_recycler, parent, false);
        return new TodoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: inside onBindViewHolder");
        holder.timeTodayText.setText(String.valueOf(textTimeToday.get(position)));
        holder.nameTodayText.setText(String.valueOf(textNameToday.get(position)));

        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#efefef"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#fefefe"));
        }
    }

/*
    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: inside onBindViewHolder");
        holder.timeTodayText.setText(timeToday[position]);
        holder.nameTodayText.setText(nameToday[position]);

        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#efefef"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#fefefe"));
        }
    }
*/

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: inside getItemCount");
//        return timeToday.length;
        return textTimeToday.size();
    }

    public class TodoHolder extends RecyclerView.ViewHolder {
        TextView timeTodayText, nameTodayText;

        public TodoHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "TodoHolder: inside ViewHolder extender");

            timeTodayText = itemView.findViewById(R.id.time_today_text);
            nameTodayText = itemView.findViewById(R.id.name_today_text);
        }
    }
}
/*
package com.sabbirunix.prodassist.ui.today;

public class TodayAdapter {
}
*/
