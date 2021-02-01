package com.sabbirunix.prodassist.ui.today;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sabbirunix.prodassist.R;
import com.sabbirunix.prodassist.addtask.TodoUpdateActivity;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.TodoHolder> {

    Context context;
    ArrayList<String> taskID, taskName, taskCategory, taskDate, startTime, endTime;
    public Activity activity;
    public Animation translate_anim;

    public TodayAdapter(Context context, ArrayList taskID, ArrayList taskName,
                        ArrayList taskCategory, ArrayList taskDate, ArrayList startTime, ArrayList endTime) {
        this.context = context;
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskCategory = taskCategory;
        this.taskDate = taskDate;
        this.startTime = startTime;
        this.endTime = endTime;
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
        holder.idTxt.setText(String.valueOf(taskID.get(position)));
        holder.nameTxt.setText(String.valueOf(taskName.get(position)));
        holder.categoryTxt.setText(String.valueOf(taskCategory.get(position)));
        holder.dateTxt.setText(String.valueOf(taskDate.get(position)));
        holder.startTxt.setText(String.valueOf(startTime.get(position)));
        holder.endTxt.setText(String.valueOf(endTime.get(position)));
        holder.todaySingleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TodoUpdateActivity.class);
                intent.putExtra("tID", String.valueOf(taskID.get(position)));
                intent.putExtra("tName", String.valueOf(taskName.get(position)));
                intent.putExtra("tCategory", String.valueOf(taskCategory.get(position)));
                intent.putExtra("tDate", String.valueOf(taskDate.get(position)));
                intent.putExtra("tStart", String.valueOf(startTime.get(position)));
                intent.putExtra("tEnd", String.valueOf(endTime.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return startTime.size();
    }

    public class TodoHolder extends RecyclerView.ViewHolder {
        TextView idTxt, nameTxt, categoryTxt, dateTxt, startTxt, endTxt;
        ConstraintLayout today_single;
        CardView todaySingleCard;

        public TodoHolder(@NonNull View itemView) {
            super(itemView);
            idTxt = itemView.findViewById(R.id.task_id);
            nameTxt = itemView.findViewById(R.id.name_today_text);
            categoryTxt = itemView.findViewById(R.id.todo_category);
            dateTxt = itemView.findViewById(R.id.todo_date);
            startTxt = itemView.findViewById(R.id.time_today_text);
            endTxt = itemView.findViewById(R.id.time_end_today_text);
            today_single = itemView.findViewById(R.id.today_single);
            todaySingleCard = itemView.findViewById(R.id.today_single_card);
            //Animating recyclerView
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            today_single.setAnimation(translate_anim);
        }
    }
}
