package com.sabbirunix.prodassist.ui.wallet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sabbirunix.prodassist.MainActivity;
import com.sabbirunix.prodassist.R;
import com.sabbirunix.prodassist.ui.notes.NotesAddActivity;
import com.sabbirunix.prodassist.ui.notes.NotesDBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WalletAddActivity extends AppCompatActivity implements View.OnClickListener {

    TextView exDate; //ex --> exchange
    EditText exDetail, exCategory, exAmount;
    Button walletSave, walletCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_add);

        exDate = findViewById(R.id.txt_date);
        exDetail = findViewById(R.id.et_particulars);
        exCategory = findViewById(R.id.et_category);
        exAmount = findViewById(R.id.et_cash_flow);
        walletSave = findViewById(R.id.wallet_save_btn);
        walletCancel = findViewById(R.id.wallet_cancel_btn);

        exDate.setOnClickListener(this);
        walletSave.setOnClickListener(this);
        walletCancel.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_date: {
                selectDate();
            }
            break;
            case R.id.wallet_save_btn: {
                if (!isTextEmpty()) {
                    WalletDBHelper walletDBHelper = new WalletDBHelper(WalletAddActivity.this);
                    walletDBHelper.addToWallet(
                            exDate.getText().toString(),
                            exDetail.getText().toString(),
                            exCategory.getText().toString(),
                            Integer.parseInt(exAmount.getText().toString())
                    );
                    //some random comment
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
//                    lastFragmentPop(); //getting back on lastFragment
                }
            }
            break;
            case R.id.wallet_cancel_btn:
                Toast.makeText(getApplicationContext(), "Adding Cancel", Toast.LENGTH_SHORT).show();
                lastFragmentPop(); //getting back on lastFragment
                break;
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
                        exDate.setText(dd + "-" + MM + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialogW.show();
    }


    //for checking if the EditTexts are empty and taking appropriate actions
    boolean isTextEmpty() {
        if (exDate.getText().toString().trim().isEmpty()) {
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            exDate.setText(currentDate);  //setting today's date
            if (exCategory.getText().toString().trim().isEmpty()) {//setting the category here also
                exCategory.setText("random");
            }
            return true;
        } else if (exCategory.getText().toString().trim().isEmpty()) {
            exCategory.setText("random");
            return true;
        } else if (exDetail.getText().toString().trim().isEmpty()) {
            exDetail.setError("Add transaction details");
            exDetail.requestFocus();
            return true;
        } else if (exAmount.getText().toString().trim().isEmpty()) {
            exAmount.setError("Amount can't be empty");
            exAmount.requestFocus();
            return true;
        } else {
            return false;
        }
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