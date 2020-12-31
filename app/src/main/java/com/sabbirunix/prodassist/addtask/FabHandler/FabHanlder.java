/**
 * This class handles the fab Buttons that appear on the LaterFragments
 * and TodoFragments by managing the userClick events inside this class
 * class this class from the above 2 mentions class
 **/


package com.sabbirunix.prodassist.addtask.FabHandler;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sabbirunix.prodassist.R;

public class FabHanlder extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "FabHanlder class";
    //setting boolean for setting the subFabs invisible at first
    //and only making it visible after checking the onClick state of the boolean
    boolean isFabMainOpen = false;
    FloatingActionButton fabMain, fabRegular, fabProject, fabTodo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        fabMain = findViewById(R.id.fab_main);
        fabRegular = findViewById(R.id.fab_regular);
        fabProject = findViewById(R.id.fab_project);
        fabTodo = findViewById(R.id.fab_todo);


        //setting onClick listener to the fabMain
        //and to the other sub fabs
        fabMain.setOnClickListener(this);
        fabRegular.setOnClickListener(this);
        fabProject.setOnClickListener(this);
        fabTodo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: Inside FabHandler onClick");
        switch (v.getId()) {
            case R.id.fab_main:
                if (isFabMainOpen) {
                    fabTodo.setVisibility(View.INVISIBLE);  //using setVisibility requires restricted api
                    fabProject.setVisibility(View.INVISIBLE);
                    fabRegular.setVisibility(View.INVISIBLE);

                    isFabMainOpen = false;
                } else {
                    fabTodo.setVisibility(View.VISIBLE);  //using setVisibility requires restricted api
                    fabProject.setVisibility(View.VISIBLE);
                    fabRegular.setVisibility(View.VISIBLE);

                    isFabMainOpen = true;
                }
                break;
            default:
                Toast.makeText(getApplicationContext(), "There are some bugs", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

/*
    //boolean to check is fabMain is already open
    boolean isFabMainOpen;
    //using the floating action bar
//        FloatingActionButton fabMain = findViewById(R.id.fab_main);
    FloatingActionButton fabMain, fabRegular, fabProject, fabTodo;
    TextView textRegular, textProject, textTodo;
    Animation fabMainOpen, fabMainClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.home_page);
        setContentView(R.layout.activity_main);


        //finding fabs buttons
        fabMain = findViewById(R.id.fab_main);
        fabTodo = findViewById(R.id.fab_todo);
        fabProject = findViewById(R.id.fab_project);
        fabRegular = findViewById(R.id.fab_regular);

        //finding text buttons
        textTodo = findViewById(R.id.text_todo);
        textProject = findViewById(R.id.text_project);
        textRegular = findViewById(R.id.text_regular);

        //showing animation effect for fabMain
        fabMainOpen = AnimationUtils.loadAnimation(com.sabbirunix.productivityproj.MainActivity.this, R.anim.rotate_open_anim);
        fabMainClose = AnimationUtils.loadAnimation(com.sabbirunix.productivityproj.MainActivity.this, R.anim.rotate_close_anim);

//        fabMain.setOnClickListener(this);
        //adding listener to the fab for functionality
        isFabMainOpen = false;
        fabMain.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")  //setvisibility requires it
            @Override
            public void onClick(View v) {

                if (isFabMainOpen) {

                    fabTodo.setVisibility(View.INVISIBLE);  //using setVisibility requires restricted api
                    textTodo.setVisibility(View.INVISIBLE);
                    fabProject.setVisibility(View.INVISIBLE);
                    textProject.setVisibility(View.INVISIBLE);
                    fabRegular.setVisibility(View.INVISIBLE);
                    textRegular.setVisibility(View.INVISIBLE);

                    isFabMainOpen = false;
                } else {
                    fabTodo.setVisibility(View.VISIBLE);  //using setVisibility requires restricted api
                    textTodo.getText();
                    textTodo.setVisibility(View.VISIBLE);
                    fabProject.setVisibility(View.VISIBLE);
                    textProject.getText();
                    textProject.setVisibility(View.VISIBLE);
                    fabRegular.setVisibility(View.VISIBLE);
                    textRegular.getText();
                    textRegular.setVisibility(View.VISIBLE);

                    fabRegular.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getApplicationContext(),RegularTaskActivity.class);
                            startActivity(i);
                        }
                    });


                    isFabMainOpen = true;
                }

            }
        });

*/
