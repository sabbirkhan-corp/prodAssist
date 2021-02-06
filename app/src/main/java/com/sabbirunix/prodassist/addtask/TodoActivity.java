package com.sabbirunix.prodassist.addtask;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sabbirunix.prodassist.MainActivity;
import com.sabbirunix.prodassist.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TodoActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton cancelTodo, okayTodo;
    private Button todoSave, todoCancel;
    private EditText taskName, taskCategory;
    private TextView taskDate, taskStart, taskEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        //finding the views from the `activity_regular.xml`
        cancelTodo = findViewById(R.id.cancel_todo_add);
        okayTodo = findViewById(R.id.okay_todo_add);
        todoSave = findViewById(R.id.todo_save_btn);
        todoCancel = findViewById(R.id.todo_cancel_btn);

        taskName = findViewById(R.id.task_name_todo);
        taskCategory = findViewById(R.id.task_category_todo);
        taskDate = findViewById(R.id.date_edit_todo);
        taskStart = findViewById(R.id.start_time_edit_todo);
        taskEnd = findViewById(R.id.end_time_edit_todo);


        taskDate.setOnClickListener(this);
        taskStart.setOnClickListener(this);
        taskEnd.setOnClickListener(this);
        okayTodo.setOnClickListener(this);
        cancelTodo.setOnClickListener(this);
        todoSave.setOnClickListener(this);
        todoCancel.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_edit_todo:
                selectDate();
                break;
            case R.id.start_time_edit_todo:
                selectStartTime();
                break;
            case R.id.end_time_edit_todo:
                selectEndTime();
                break;
            case R.id.todo_cancel_btn:
            case R.id.cancel_todo_add:
                lastFragmentPop(); //getting back on lastFragment
                break;
            case R.id.todo_save_btn:
            case R.id.okay_todo_add: {
                if (!isTextEmpty()) {
                    RegularActivityDatabase regDB = new RegularActivityDatabase(TodoActivity.this);
                    regDB.insertRegularTask(
                            taskName.getText().toString().trim(),
                            taskCategory.getText().toString().trim(),
                            taskDate.getText().toString().trim(),
                            taskStart.getText().toString().trim(),
                            taskEnd.getText().toString().trim()
                    );
//                    lastFragmentPop(); //getting back on lastFragment
                    Intent intent = new Intent(TodoActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
            break;
        }//end of switch
    }

    //for checking if the EditTexts are empty and taking appropriate actions
    boolean isTextEmpty() {
        if (taskDate.getText().toString().trim().isEmpty()) {
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            taskDate.setText(currentDate);  //setting today's date
            if (taskCategory.getText().toString().trim().isEmpty()) {//setting the category here also
                taskCategory.setText("random");
            }
            return true;
        } else if (taskCategory.getText().toString().trim().isEmpty()) {
            taskCategory.setText("random");
            return true;
        } else if (taskName.getText().toString().trim().isEmpty()) {
            taskName.setError("Give a name");
            taskName.requestFocus();
            return true;
        } else if (taskStart.getText().toString().isEmpty()) {
            taskStart.setError("Choose a time");
            taskStart.callOnClick(); //potential bug lurks here
            return true;
        } else {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void selectDate() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialogW = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int monthX = month + 1;
                        String MM = "" + month;
                        String dd = "" + dayOfMonth;
                        if (monthX < 10) {
                            MM = "0" + monthX;
                        }
                        if (dayOfMonth < 10) {
                            dd = "0" + dayOfMonth;
                        }
                        //processing above for showing in `dd-MM-yyyy` format
                        taskDate.setText(dd + "-" + MM + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialogW.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void selectStartTime() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR);
        int minutes = cal.get(Calendar.MINUTE);
        int amPm = cal.get(Calendar.AM_PM);
        //time picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(TodoActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        String state = "AM";
                        if (sHour > 12) {
                            sHour -= 12;
                            state = "PM";
                        }
                        String time = sHour + ":" + sMinute + " " + state;
                        taskStart.setText(time);
                    }
                }, hour, minutes, false);
        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void selectEndTime() {
        Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        //time picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(TodoActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        String state = "AM";
                        if (sHour > 12) {
                            sHour -= 12;
                            state = "PM";
                        }
                        String time = sHour + ":" + sMinute + " " + state;
                        taskEnd.setText(time);
                    }
                }, hour, minutes, false);
        timePickerDialog.show();
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

}