package com.sabbirunix.prodassist.ui.today;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sabbirunix.prodassist.R;
import com.sabbirunix.prodassist.addtask.RepeatingActivity;
import com.sabbirunix.prodassist.addtask.RegularActivity;
import com.sabbirunix.prodassist.addtask.RegularActivityDatabase;
import com.sabbirunix.prodassist.addtask.TodoActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

//public class TodayFragment extends Fragment implements View.OnClickListener {
public class TodayFragment extends Fragment {

    public RegularActivityDatabase regularActDB;
    public ArrayList<String> idTxt, nameTxt, categoryTxt, dateTxt, startTxt, endTxt;
    public TodayAdapter todayAdapter;
    private TodayViewModel todayViewModel;
    TextView textTodo, textProject, textRegular;
    FloatingActionButton fabMain, fabRegular, fabProject, fabTodo;
    ImageView emptyTodayImg;
    TextView emptyTodayTxt;
    /*
        setting boolean for setting the subFabs invisible at first
        and only making it visible after checking the onClick state of the boolean
    */
    boolean isAllFabsVisible = false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        todayViewModel =
                new ViewModelProvider(this).get(TodayViewModel.class);
        View root = inflater.inflate(R.layout.fragment_today, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);


        //Sync with NotesFragment
        //finding the fabButtons in the fragment
        fabMain = root.findViewById(R.id.fab_main);
        fabRegular = root.findViewById(R.id.fab_regular);
        fabProject = root.findViewById(R.id.fab_project);
        fabTodo = root.findViewById(R.id.fab_todo);
        textTodo = root.findViewById(R.id.text_todo);
        textProject = root.findViewById(R.id.text_project);
        textRegular = root.findViewById(R.id.text_regular);
        emptyTodayImg = root.findViewById(R.id.empty_today_img);
        emptyTodayTxt = root.findViewById(R.id.empty_today_txt);

        //setting the views visibility to gone
        fabTodo.setVisibility(View.GONE);  //using setVisibility requires restricted api
        fabProject.setVisibility(View.GONE);
        fabRegular.setVisibility(View.GONE);
        textTodo.setVisibility(View.GONE);
        textProject.setVisibility(View.GONE);
        textRegular.setVisibility(View.GONE);

        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isAllFabsVisible) {
//                    fabRegular.show();
                    fabProject.show();
                    fabTodo.show();
                    textTodo.setVisibility(View.VISIBLE);
                    textProject.setVisibility(View.VISIBLE);
//                    textRegular.setVisibility(View.VISIBLE);
                    isAllFabsVisible = true;
                } else {
                    fabRegular.hide();
                    fabProject.hide();
                    fabTodo.hide();
                    textTodo.setVisibility(View.GONE);
                    textProject.setVisibility(View.GONE);
                    textRegular.setVisibility(View.GONE);
                    isAllFabsVisible = false;
                }
            }
        });


        //Handling the userClick on fabRegular
        fabRegular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RegularActivity.class);
                startActivity(intent);
            }
        });

        //Handling the userClick on fabProject
        fabProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                startActivity(intent);
            }
        });

        //Handling the userClick on fabTodo
        fabTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TodoActivity.class);
                startActivity(intent);
            }
        });


        regularActDB = new RegularActivityDatabase(getContext());
        idTxt = new ArrayList<>();
        nameTxt = new ArrayList<>();
        categoryTxt = new ArrayList<>();
        dateTxt = new ArrayList<>();
        startTxt = new ArrayList<>();
        endTxt = new ArrayList<>();


        //finding the recycler view
        RecyclerView recyclerView = root.findViewById(R.id.today_recyclerview);

        displayRegularTask();
        todayAdapter = new TodayAdapter(getContext(), idTxt, nameTxt, categoryTxt, dateTxt, startTxt, endTxt);
        recyclerView.setAdapter(todayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //To hide the fabButtons when scrolled down
        //answer link: Do a more through review and find a better code
//https://stackoverflow.com/questions/32038332/using-google-design-library-how-to-hide-fab-button-on-scroll-down
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fabMain.hide();
                    fabRegular.hide();
                    fabProject.hide();
                    fabTodo.hide();
                } else if (dy < 0)
                    fabMain.show();
            }
        });
        return root;
    }

    void displayRegularTask() {
        Cursor cursor = regularActDB.readRegularTask();
        if (cursor.getCount() == 0) {
            emptyTodayImg.setVisibility(View.VISIBLE);
            emptyTodayTxt.setVisibility(View.VISIBLE);
//            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                idTxt.add(cursor.getString(0));
                nameTxt.add(cursor.getString(1));
                categoryTxt.add(cursor.getString(2));
                dateTxt.add(cursor.getString(3));
                startTxt.add(cursor.getString(4));
                endTxt.add(cursor.getString(5));
            }
            emptyTodayImg.setVisibility(View.GONE);
            emptyTodayTxt.setVisibility(View.GONE);
        }
    }
}