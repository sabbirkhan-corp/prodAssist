package com.sabbirunix.prodassist.ui.today;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.sabbirunix.prodassist.addtask.FabHandler.FabHanlder;
import com.sabbirunix.prodassist.addtask.ProjectActivity;
import com.sabbirunix.prodassist.addtask.RegularActivity;
import com.sabbirunix.prodassist.addtask.TodoActivity;

//public class TodayFragment extends Fragment implements View.OnClickListener {
public class TodayFragment extends Fragment {

    private TodayViewModel todayViewModel;
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


        //Sync with LaterFragment
        //finding the fabButtons in the fragment
        fabMain = root.findViewById(R.id.fab_main);
        fabRegular = root.findViewById(R.id.fab_regular);
        fabProject = root.findViewById(R.id.fab_project);
        fabTodo = root.findViewById(R.id.fab_todo);

        //setting the views visibility to gone
        fabTodo.setVisibility(View.GONE);  //using setVisibility requires restricted api
        fabProject.setVisibility(View.GONE);
        fabRegular.setVisibility(View.GONE);

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





        //finding the recycler view
        RecyclerView recyclerView = root.findViewById(R.id.today_recyclerview);
        //setting recylder views layoutmanager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String[] t1 = {"From Left 1",
                "From Left 2",
                "From Left 3",
                "From Left 4",
                "From Left 5",
                "From Left 6",
                "From Left 6",
                "From Left 6",
                "From Left 7",
                "From Left 8",
                "From Left 9",
                "From Left 10",
                "From Left 11",
                "From Left 12",
                "From Left 13",
                "From Left 14",
                "From Left 15",
                "From Left 16",
                "From Left 17",
                "From Left 18",
                "From Left 19",
        };
        String[] t2 = {"From Right 1",
                "From Right 20",
                "From Right 30",
                "From Right 40",
                "From Right 50",
                "From Right 60",
                "From Right 70",
                "From Right 70",
                "From Right 60",
                "From Right 50",
                "From Right 40",
                "From Right 30",
                "From Right 20",
                "From Right 10",
                "From Right 9",
                "From Right 8",
                "From Right 7",
                "From Right 7",
                "From Right 7",
                "From Right 7",
                "From Right 7"
        };
        //sending the data to the adapter through the constructor of adapter class
        recyclerView.setAdapter(new TodayAdapter(t1, t2));


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
}