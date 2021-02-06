package com.sabbirunix.prodassist.addtask;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.sabbirunix.prodassist.ui.wallet.WalletDBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TodoUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton cancelTodoU, okayTodoU;
    private Button todoSaveU, todoCancelU;
    private EditText taskNameU, taskCategoryU;
    private TextView taskDateU, taskStartU, taskEndU;
    String tID, tName, tCategory, tDate, tStart, tEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_update);


        //finding the views from the `activity_regular.xml`
        cancelTodoU = findViewById(R.id.cancel_todo_up);
        okayTodoU = findViewById(R.id.okay_todo_up);
        todoSaveU = findViewById(R.id.todo_save_btn_up);
        todoCancelU = findViewById(R.id.todo_cancel_btn_up);

        taskNameU = findViewById(R.id.task_name_todo_up);
        taskCategoryU = findViewById(R.id.task_category_todo_up);
        taskDateU = findViewById(R.id.date_edit_todo_up);
        taskStartU = findViewById(R.id.start_time_edit_todo_up);
        taskEndU = findViewById(R.id.end_time_edit_todo_up);

        //first we call this then anclick update
        getSetIntentData();

        taskDateU.setOnClickListener(this);
        taskStartU.setOnClickListener(this);
        taskEndU.setOnClickListener(this);
        okayTodoU.setOnClickListener(this);
        cancelTodoU.setOnClickListener(this);
        todoSaveU.setOnClickListener(this);
        todoCancelU.setOnClickListener(this);

    }


    //showing the delete icon in updateNote
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                confirmDialog();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + taskNameU.getText() + " ?");
        builder.setMessage("Are you sure you want to delete " + taskNameU.getText() + " ?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RegularActivityDatabase dbHelper = new RegularActivityDatabase(TodoUpdateActivity.this);
                dbHelper.deleteItem(tID);
                finish();//to finish activity and get back
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //this doesn't do anything //just returns
            }
        });
        builder.create().show(); //to actually show the dialog //pst
    }

    void getSetIntentData() {
        if (getIntent().hasExtra("tID") && getIntent().hasExtra("tName") && getIntent().hasExtra("tCategory")
                && getIntent().hasExtra("tDate") && getIntent().hasExtra("tStart") && getIntent().hasExtra("tEnd")) {

            //Getting data from intent
            tID = getIntent().getStringExtra("tID");
            tName = getIntent().getStringExtra("tName");
            tCategory = getIntent().getStringExtra("tCategory");
            tDate = getIntent().getStringExtra("tDate");
            tStart = getIntent().getStringExtra("tStart");
            tEnd = getIntent().getStringExtra("tEnd");

            //setting intent data
            taskNameU.setText(tName);
            taskCategoryU.setText(tCategory);
            taskDateU.setText(tDate);
            taskStartU.setText(tStart);
            taskEndU.setText(tEnd);

        } else {
            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_edit_todo_up:
                selectDate();
                break;
            case R.id.start_time_edit_todo_up:
                selectStartTime();
                break;
            case R.id.end_time_edit_todo_up:
                selectEndTime();
                break;
            case R.id.todo_cancel_btn_up:
            case R.id.cancel_todo_up:
                lastFragmentPop(); //getting back on lastFragment
                break;
            case R.id.todo_save_btn_up:
            case R.id.okay_todo_up: {
                if (!isTextEmpty()) {
                    RegularActivityDatabase regDB = new RegularActivityDatabase(TodoUpdateActivity.this);
                    regDB.updateItem(
                            tID,
                            taskNameU.getText().toString().trim(),
                            taskCategoryU.getText().toString().trim(),
                            taskDateU.getText().toString().trim(),
                            taskStartU.getText().toString().trim(),
                            taskEndU.getText().toString().trim()
                    );
                    lastFragmentPop(); //getting back on lastFragment
                    Intent intent = new Intent(TodoUpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
            break;
        }//end of switch
    }

    //for checking if the EditTexts are empty and taking appropriate actions
    boolean isTextEmpty() {
        if (taskDateU.getText().toString().trim().isEmpty()) {
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            taskDateU.setText(currentDate);  //setting today's date
            if (taskCategoryU.getText().toString().trim().isEmpty()) {//setting the category here also
                taskCategoryU.setText("random");
            }
            return true;
        } else if (taskCategoryU.getText().toString().trim().isEmpty()) {
            taskCategoryU.setText("random");
            return true;
        } else if (taskNameU.getText().toString().trim().isEmpty()) {
            taskNameU.setError("Give a name");
            taskNameU.requestFocus();
            return true;
        } else if (taskStartU.getText().toString().isEmpty()) {
            taskStartU.setError("Choose a time");
            taskStartU.callOnClick(); //potential bug lurks here
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
                        taskDateU.setText(dd + "-" + MM + "-" + year);
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(TodoUpdateActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        String state = "AM";
                        if (sHour > 12) {
                            sHour -= 12;
                            state = "PM";
                        }
                        String time = sHour + ":" + sMinute + " " + state;
                        taskStartU.setText(time);
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(TodoUpdateActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        taskEndU.setText(sHour + ":" + sMinute);
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