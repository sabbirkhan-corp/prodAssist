package com.sabbirunix.prodassist.ui.today;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sabbirunix.prodassist.R;
import com.sabbirunix.prodassist.addtask.ProjectActivity;
import com.sabbirunix.prodassist.addtask.RegularActivity;
import com.sabbirunix.prodassist.addtask.RegularActivityDatabase;
import com.sabbirunix.prodassist.addtask.TodoActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

//public class TodayFragment extends Fragment implements View.OnClickListener {
public class TodayFragment extends Fragment {

    public RegularActivityDatabase regularActDB;
    public ArrayList<String> timeText, nameText;
    public TodayAdapter todayAdapter;
    private TodayViewModel todayViewModel;
    TextView textTodo, textProject, textRegular;
    FloatingActionButton fabMain, fabRegular, fabProject, fabTodo;
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
                    // when isAllFabsVisible becomes
                    // true make all the action name
                    // texts and FABs VISIBLE.
                    fabRegular.show();
                    fabProject.show();
                    fabTodo.show();
                    textTodo.setVisibility(View.VISIBLE);
                    textProject.setVisibility(View.VISIBLE);
                    textRegular.setVisibility(View.VISIBLE);

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
                    textTodo.setVisibility(View.GONE);
                    textProject.setVisibility(View.GONE);
                    textRegular.setVisibility(View.GONE);

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
                Intent intent = new Intent(getContext(), RegularActivity.class);
                startActivity(intent);
            }
        });

        //Handling the userClick on fabProject
        fabProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProjectActivity.class);
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
        timeText = new ArrayList<>();
        nameText = new ArrayList<>();


        //finding the recycler view
        RecyclerView recyclerView = root.findViewById(R.id.today_recyclerview);
        //setting recylder views layoutmanager
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        String[] t1 = new String[30];
//        String[] t2 = new String[30];
//        Arrays.fill(t1, "07:00");
//        Arrays.fill(t2, "wake up ");
        //sending the data to the adapter through the constructor of adapter class
//        recyclerView.setAdapter(new TodayAdapter(t1, t2));
        displayRegularTask();
        todayAdapter = new TodayAdapter(getContext(), timeText, nameText);
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

//        FabHanlder fabHanlder = new FabHanlder();
//        fabHanlder.onClick(fabMain);
/* //Generated Code
        todayViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
*/
        return root;
    }

    void displayRegularTask() {
        Cursor cursor = regularActDB.readRegularTask();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                nameText.add(cursor.getString(1)); //getting startTime
                timeText.add(cursor.getString(3)); //getting taskName
            }
        }
    }
}