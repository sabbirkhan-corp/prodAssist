package com.sabbirunix.prodassist.ui.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.Nullable;

public class NotesDBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Notes.db";
    private static final String TABLE_NAME = "notes_library";
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "note_title";
    private static final String KEY_CATEGORY = "note_category";
    private static final String KEY_DETAILS = "note_details";
    private static final String KEY_PRIORITY = "note_priority";

    public NotesDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE =
                "CREATE TABLE " + TABLE_NAME
                        + "("
                        + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + KEY_TITLE + " TEXT NOT NULL, "
                        + KEY_CATEGORY + " TEXT  DEFAULT 'random' , "
                        + KEY_DETAILS + " TEXT NOT NULL, "
                        + KEY_PRIORITY + "INTEGER"
                        + ")";
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    //adding method for note insertion //right now avoiding using priority
    //we will use priority later
//    void addNote(String title, String category, String details, String priority) {
    void addNote(String title, String category, String details) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_TITLE, title);
        cv.put(KEY_CATEGORY, category);
        cv.put(KEY_DETAILS, details);
//        cv.put(KEY_PRIORITY, priority);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to add note", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Added Note", Toast.LENGTH_SHORT).show();
        }
    }


    //for displaying notes by reading them
    Cursor displayNotes() {
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    //for updating the data
    void updateNote(String row_id, String title, String category, String details) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, title);
        cv.put(KEY_CATEGORY, category);
        cv.put(KEY_DETAILS, details);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to add note", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Added Note", Toast.LENGTH_SHORT).show();
        }
    }


    //for deleting notes
    void deleteNote(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Error deleting note", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted note", Toast.LENGTH_SHORT).show();
        }
    }

}
