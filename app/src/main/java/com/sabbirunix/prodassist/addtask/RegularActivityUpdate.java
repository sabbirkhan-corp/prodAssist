package com.sabbirunix.prodassist.addtask;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sabbirunix.prodassist.R;

public class RegularActivityUpdate extends AppCompatActivity implements View.OnClickListener {
    private ImageButton cancelRegularUpdate, okayRegularUpdate;
    private EditText taskNameUpdate, taskCategoryUpdate;
    private TextView taskStartUpdate, taskEndUpdate;
    private DatePickerDialog datePickerDialogUpdate;
    private TimePickerDialog timePickerDialogUpdate;
    private RegularActivityDatabase regularActivityDatabase;
    String id, kTime, kName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_update);
        //finding the views from the `activity_regular.xml`
        cancelRegularUpdate = findViewById(R.id.cancel_regular_update);
        okayRegularUpdate = findViewById(R.id.okay_regular_update);
        taskNameUpdate = findViewById(R.id.task_name_regular_update);
        taskCategoryUpdate = findViewById(R.id.task_category_regular_update);
        taskStartUpdate = findViewById(R.id.start_time_edit_regular_update);
        taskEndUpdate = findViewById(R.id.end_time_edit_regular_update);

        getAndSetIntentData();

        taskStartUpdate.setOnClickListener(this);
        taskEndUpdate.setOnClickListener(this);
        okayRegularUpdate.setOnClickListener(this);
        cancelRegularUpdate.setOnClickListener(this);

    }


    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_time_edit_regular_update:
                selectStartTime();
                break;
            case R.id.end_time_edit_regular_update:
                selectEndTime();
                break;
            case R.id.okay_regular_update: {
                //for setting warning and handling the FormatError in DB
                if (taskNameUpdate.getText().toString().isEmpty()) {
                    taskNameUpdate.setError("Give a name");
                    taskNameUpdate.requestFocus();
                } else if (taskCategoryUpdate.getText().toString().matches("")) {
                    taskCategoryUpdate.setText("random");
                } else if (taskStartUpdate.getText().toString().isEmpty()) {
                    taskStartUpdate.setError("Choose a time");
                    taskStartUpdate.callOnClick(); //potential bug lurks here
                } else {
                    RegularActivityDatabase regDB = new RegularActivityDatabase(RegularActivityUpdate.this);
                    regDB.updateData(id, kTime, kName);
                    lastFragmentPop(); //getting back on lastFragment
                }
            }
            break;
            case R.id.cancel_regular_update: {
                Toast.makeText(getApplicationContext(), "Adding Canceled", Toast.LENGTH_SHORT).show();
                lastFragmentPop(); //getting back on lastFragment

            }
            break;

        }//end of switch

    }

    public void lastFragmentPop() {
        //For getting back on the lastFragment
        //works fine for now, but deprecated in api 28
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void selectStartTime() {
        Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        //time picker Dialog
        timePickerDialogUpdate = new TimePickerDialog(RegularActivityUpdate.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        taskStartUpdate.setText(sHour + ":" + sMinute);
                    }
                }, hour, minutes, false);
        timePickerDialogUpdate.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void selectEndTime() {
        Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        //time picker Dialog
        timePickerDialogUpdate = new TimePickerDialog(RegularActivityUpdate.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        taskEndUpdate.setText(sHour + ":" + sMinute);
                    }
                }, hour, minutes, false);
        timePickerDialogUpdate.show();
    }


    //Getting the intent data
    void getAndSetIntentData() {
        if (getIntent().hasExtra("kTime") && getIntent().hasExtra("kName")) {
            //getting data from intent
            kTime = getIntent().getStringExtra("Ktime");
            kName = getIntent().getStringExtra("kName");
            //setting intent data
            taskStartUpdate.setText(kTime);
            taskNameUpdate.setText(kName);
        } else {
            Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
        }
    }

}
