package com.sabbirunix.prodassist.ui.wallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class WalletDBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Wallet.db";
    private static final String TABLE_NAME = "wallet_statement";
    private static final String KEY_ID = "_id";
    private static final String KEY_DATE = "cash_flow_date";
    private static final String KEY_DETAILS = "cash_flow_details";
    private static final String KEY_CATEGORY = "cash_flow_category";
    private static final String KEY_AMOUNT = "cash_flow_amount";

    public WalletDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WALLET_TABLE =
                "CREATE TABLE " + TABLE_NAME
                        + "("
                        + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + KEY_DATE + " TEXT NOT NULL, "
                        + KEY_DETAILS + " TEXT NOT NULL, "
                        + KEY_CATEGORY + " TEXT  DEFAULT 'random' , "
                        + KEY_AMOUNT + " INTEGER NOT NULL"
                        + ")";
        db.execSQL(CREATE_WALLET_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    void addToWallet(String date, String details, String category, int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_DATE, date);
        cv.put(KEY_DETAILS, details);
        cv.put(KEY_CATEGORY, category);
        cv.put(KEY_AMOUNT, amount);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to add to wallet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Added to wallet", Toast.LENGTH_SHORT).show();
        }
    }


    //for displaying wallet status by reading them
    Cursor displayWalletItem() {
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_DATE + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    //for updating the data
    void updateWalletItem(String row_id, String date, String details, String category, Integer amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_DATE, date);
        cv.put(KEY_DETAILS, details);
        cv.put(KEY_CATEGORY, category);
        cv.put(KEY_AMOUNT, amount);
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Failed to update wallet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully updated wallet", Toast.LENGTH_SHORT).show();
        }
    }


    //for deleting wallet item
    void deleteWalletItem(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Error deleting wallet item", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted wallet item", Toast.LENGTH_SHORT).show();
        }
    }


    //returning total balance to top cardView
    public int getSumBalance() {
        int sum = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sumQuery = String.format("SELECT SUM(%s) AS sumBalance FROM %s", this.KEY_AMOUNT, this.TABLE_NAME);
        Cursor cursor = db.rawQuery(sumQuery, null);
        if (cursor.moveToFirst())
            sum = cursor.getInt(cursor.getColumnIndex("sumBalance"));
        cursor.close(); // i added it to remove warning
        return sum;
    }

    //returning total income to top cardView
    public int getSumIncome() {
        int sum = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sumQuery = String.format("SELECT SUM(%s) AS sumIncome FROM %s WHERE %s > 0", this.KEY_AMOUNT, this.TABLE_NAME, this.KEY_AMOUNT);
        Cursor cursor = db.rawQuery(sumQuery, null);
        if (cursor.moveToFirst())
            sum = cursor.getInt(cursor.getColumnIndex("sumIncome"));
        cursor.close(); // added to remove warning // works fine without it
        return sum;
    }

    //returning total expense to top cardView
    public int getSumExpense() {
        int sum = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sumQuery = String.format("SELECT SUM(%s) AS sumExpense FROM %s WHERE %s < 0", this.KEY_AMOUNT, this.TABLE_NAME, this.KEY_AMOUNT);
        Cursor cursor = db.rawQuery(sumQuery, null);
        if (cursor.moveToFirst())
            sum = cursor.getInt(cursor.getColumnIndex("sumExpense"));
        cursor.close(); //works fine without it
        return sum;
    }


}
