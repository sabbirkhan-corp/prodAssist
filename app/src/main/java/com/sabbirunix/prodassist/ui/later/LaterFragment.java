package com.sabbirunix.prodassist.ui.later;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sabbirunix.prodassist.R;
import com.sabbirunix.prodassist.addtask.ProjectActivity;
import com.sabbirunix.prodassist.addtask.RegularActivity;
import com.sabbirunix.prodassist.addtask.TodoActivity;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class LaterFragment extends Fragment {

    private LaterViewModel laterViewModel;

    FloatingActionButton fabMain, fabRegular, fabProject, fabTodo;
    /*
        setting boolean for setting the subFabs invisible at first
        and only making it visible after checking the onClick state of the boolean
    */
    boolean isAllFabsVisible = false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        laterViewModel =
                new ViewModelProvider(this).get(LaterViewModel.class);
        View root = inflater.inflate(R.layout.fragment_later, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);


        //Sync with TodayFragment
        //finding the fabButtons in the fragment
        fabMain = root.findViewById(R.id.fab_main);
        fabRegular = root.findViewById(R.id.fab_regular);
        fabProject = root.findViewById(R.id.fab_project);
        fabTodo = root.findViewById(R.id.fab_todo);

        //setting the views visibility to gone
        fabTodo.setVisibility(View.GONE);  //using setVisibility requires restricted api
        fabProject.setVisibility(View.GONE);
        fabRegular.setVisibility(View.GONE);


        //declaring and finding the reyclerView from the later_frags_xml
        RecyclerView recyclerView = root.findViewById(R.id.later_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //getting the time, name, date from the users and adding it to the users
//        StringBuilder[] dateLater = new StringBuilder;
//        StringBuilder[] timeLater = new StringBuilder;
//        StringBuilder[] nameLater = new StringBuilder;
        String[] timeLater = new String[30];
        String[] nameLater = new String[30];
        String[] dateLater = new String[30];
        Arrays.fill(timeLater, "9:30");
        Arrays.fill(nameLater, "Go to work");
        Arrays.fill(dateLater, "03, Feb");


        recyclerView.setAdapter(new LaterAdapter(timeLater, nameLater, dateLater));


        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isAllFabsVisible) {
                    // when isAllFabsVisible becomes
                    // true make all the action name
                    // texts and FABs VISIBLE.
                    fabRegular.show();
                    fabProject.show();
                    fabTodo.show();
//                    addAlarmActionText.setVisibility(View.VISIBLE);
//                    addPersonActionText.setVisibility(View.VISIBLE);

                    // make the boolean variable true as
                    // we have set the sub FABs
                    // visibility to GONE
                    isAllFabsVisible = true;
                } else {
                    // when isAllFabsVisible becomes
                    // true make all the action name
                    // texts and FABs GONE.
                    fabRegular.hide();
                    fabProject.hide();
                    fabTodo.hide();
//                    addAlarmActionText.setVisibility(View.GONE);
//                    addPersonActionText.setVisibility(View.GONE);

                    // make the boolean variable false
                    // as we have set the sub FABs
                    // visibility to GONE
                    isAllFabsVisible = false;
                }
            }
        });


        //Handling the userClick on fabRegular
        fabRegular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Tapped on fabRegular", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), RegularActivity.class);
                startActivity(intent);
            }
        });

        //Handling the userClick on fabProject
        fabProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Tapped on fabProject", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), ProjectActivity.class);
                startActivity(intent);
            }
        });

        //Handling the userClick on fabTodo
        fabTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Tapped on fabTodo", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), TodoActivity.class);
                startActivity(intent);
            }
        });



/*
        laterViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
*/
        return root;
    }
}