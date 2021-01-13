package com.sabbirunix.prodassist.ui.wallet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sabbirunix.prodassist.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WalletUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    TextView exDateU; //ex --> exchange
    EditText exDetailU, exCategoryU, exAmountU;
    Button walletUpdate, walletCancelU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_update);

        exDateU = findViewById(R.id.txt_date_up);
        exDetailU = findViewById(R.id.et_particulars_up);
        exCategoryU = findViewById(R.id.et_category_up);
        exAmountU = findViewById(R.id.et_cash_flow_up);
        walletUpdate = findViewById(R.id.wallet_update_btn);
        walletCancelU = findViewById(R.id.wallet_cancel_btn_up);

        exDateU.setOnClickListener(this);
        walletUpdate.setOnClickListener(this);
        walletCancelU.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_date_up: {
                selectDate();
            }
            break;
            case R.id.wallet_update_btn: {
                if (!isTextEmpty()) {
                    Toast.makeText(getApplicationContext(), "Updated Wallet", Toast.LENGTH_SHORT).show();
                    lastFragmentPop(); //getting back on lastFragment
                }
            }
            break;
            case R.id.wallet_cancel_btn_up:
                Toast.makeText(getApplicationContext(), "Wallet not updated", Toast.LENGTH_SHORT).show();
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
        DatePickerDialog datePickerDialogWU = new DatePickerDialog(this,
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
                        exDateU.setText(dd + "-" + MM + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialogWU.show();
    }


    //for checking if the EditTexts are empty and taking appropriate actions
    boolean isTextEmpty() {
        if (exDateU.getText().toString().trim().isEmpty()) {
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            exDateU.setText(currentDate);  //setting today's date
            if (exCategoryU.getText().toString().trim().isEmpty()) {//setting the category here also
                exCategoryU.setText("random");
            }
            return true;
        } else if (exCategoryU.getText().toString().trim().isEmpty()) {
            exCategoryU.setText("random");
            return true;
        } else if (exDetailU.getText().toString().trim().isEmpty()) {
            exDetailU.setError("Add transaction details");
            exDetailU.requestFocus();
            return true;
        } else if (exAmountU.getText().toString().trim().isEmpty()) {
            exAmountU.setError("Amount can't be empty");
            exAmountU.requestFocus();
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