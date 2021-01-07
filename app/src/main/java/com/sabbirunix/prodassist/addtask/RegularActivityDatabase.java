package com.sabbirunix.prodassist.addtask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class RegularActivityDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RegularTask.db";
    private static final String TABLE_NAME = "regular_task";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME = "end_time";
    private Context context;

    public RegularActivityDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context; //without that you will get a lot of fucking bugs
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REGULAR_TABLE =
                "CREATE TABLE " + TABLE_NAME
                        + "("
                        + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + KEY_NAME + " TEXT NOT NULL, "
                        + KEY_CATEGORY + " TEXT  DEFAULT 'random' , "
                        + KEY_START_TIME + " TEXT NOT NULL, "
                        + KEY_END_TIME + " TEXT"
                        + ")";
        db.execSQL(CREATE_REGULAR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }


    //adding methods for inserting the data from the regular_activity task
    void insertRegularTask(String name, String category, String startTime, String endTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAME, name);
        cv.put(KEY_CATEGORY, category);
        cv.put(KEY_START_TIME, startTime);
        cv.put(KEY_END_TIME, endTime);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to insert the data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully inserted", Toast.LENGTH_SHORT).show();
        }
/* answer link: https://stackoverflow.com/questions/754684/how-to-insert-a-sqlite-record-with-a-datetime-set-to-now-in-android-applicatio
        // set the format to sql date time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues initialValues = new ContentValues();
        initialValues.put("date_created", dateFormat.format(date));
        long rowId = mDb.insert(DATABASE_TABLE, null, initialValues);
*/
    }


    //methods to fetch all regular tasks from the database
    public Cursor readRegularTask() {
//        String query = "SELECT * FROM " + TABLE_NAME ;
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " +KEY_START_TIME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        //don't be stupid like me and check the `db !== null` ; `NOT cursor!=null`
        //spend 10+ hours for this stupidity
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    //methods for updating the data upon click on recyclerView
    void updateData(String row_id, String startTime, String taskName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_START_TIME, startTime);
        cv.put(KEY_NAME, taskName);

        long result = db.update(TABLE_NAME, cv, "id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

}
