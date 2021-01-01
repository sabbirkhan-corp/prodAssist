package com.sabbirunix.prodassist.addtask;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegularActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton cancelRegular, okayRegular;
    private EditText taskName, taskCatagory;
    private TextView taskStart, taskEnd;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular);

        //finding the views from the `activity_regular.xml`
        cancelRegular = findViewById(R.id.cancel_regular_add);
        okayRegular = findViewById(R.id.okay_regular_add);

        taskName = findViewById(R.id.task_name_regular);
        taskCatagory = findViewById(R.id.task_catagory_regular);
        taskStart = findViewById(R.id.start_time_edit_regular);
        taskEnd = findViewById(R.id.end_time_edit_regular);


        taskStart.setOnClickListener(this);
        taskEnd.setOnClickListener(this);
        okayRegular.setOnClickListener(this);
        cancelRegular.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_time_edit_regular:
                selectTime();
                break;
            case R.id.end_time_edit_regular:
                selectTime();
                break;
            case R.id.okay_regular_add: {
                Toast.makeText(getApplicationContext(), "Items Added Successfully", Toast.LENGTH_SHORT).show();
                //clearing text after adding the task
                taskName.setText("");
                taskCatagory.setText("");
                taskStart.setText("");
                taskEnd.setText("");
            }
            break;
            case R.id.cancel_regular_add: {
                Toast.makeText(getApplicationContext(), "Adding Canceled", Toast.LENGTH_SHORT).show();

                //For getting back on the lastFragment
                //works fine for now, but deprecated in api 28
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStackImmediate();
                } else {
                    super.onBackPressed();
                }
            }
            break;

        }//end of switch

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void selectTime() {
//        add some code here to call it everytime onclick listener is called
        Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        //time picker Dialog
        timePickerDialog = new TimePickerDialog(RegularActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        taskStart.setText(sHour + ":" + sMinute);
                    }
                }, hour, minutes, true);
        timePickerDialog.show();
    }
}