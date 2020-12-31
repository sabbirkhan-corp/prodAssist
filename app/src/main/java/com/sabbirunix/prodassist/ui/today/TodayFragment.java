package com.sabbirunix.prodassist.ui.today;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sabbirunix.prodassist.R;
import com.sabbirunix.prodassist.addtask.FabHandler.FabHanlder;

public class TodayFragment extends Fragment implements View.OnClickListener {

    private TodayViewModel todayViewModel;
    FloatingActionButton fabMain, fabRegular, fabProject, fabTodo;
    /*
        setting boolean for setting the subFabs invisible at first
        and only making it visible after checking the onClick state of the boolean
    */
    boolean isFabMainOpen = false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        todayViewModel =
                new ViewModelProvider(this).get(TodayViewModel.class);
        View root = inflater.inflate(R.layout.fragment_today, container, false);
        final TextView textView = root.findViewById(R.id.text_home);


        //finding the fabButtons in the fragment
        fabMain = root.findViewById(R.id.fab_main);
        fabRegular = root.findViewById(R.id.fab_regular);
        fabProject = root.findViewById(R.id.fab_project);
        fabTodo = root.findViewById(R.id.fab_todo);

        //setting the views visibility to gone
        fabTodo.setVisibility(View.GONE);  //using setVisibility requires restricted api
        fabProject.setVisibility(View.GONE);
        fabRegular.setVisibility(View.GONE);

//        FabHanlder fabHanlder = new FabHanlder();
//        fabHanlder.onClick(fabMain);


        todayViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_main:
                if (isFabMainOpen) {
                    fabTodo.setVisibility(View.GONE);  //using setVisibility requires restricted api
                    fabProject.setVisibility(View.GONE);
                    fabRegular.setVisibility(View.GONE);

                    isFabMainOpen = false;
                } else {
                    fabTodo.setVisibility(View.VISIBLE);  //using setVisibility requires restricted api
                    fabProject.setVisibility(View.VISIBLE);
                    fabRegular.setVisibility(View.VISIBLE);

                    isFabMainOpen = true;
                }
                break;
            default:
//                Toast.makeText(getApplicationContext(), "There are some bugs", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}