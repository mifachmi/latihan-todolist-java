package com.example.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    Context context;
    private static final String DATABASE_NAME = "my_todolist_db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "my_tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TASK_NAME = "task_name";
    private static final String COLUMN_DESCRIPTION = "task_desc";
    private static final String COLUMN_DATE = "task_date";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASK_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addTask(String name_task, String desc_task, String date_task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TASK_NAME, name_task);
        cv.put(COLUMN_DESCRIPTION, desc_task);
        cv.put(COLUMN_DATE, date_task);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed Insert Data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Succeed Insert Data", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getAllTask() {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_DATE + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(selectQuery, null);
        }
        return cursor;
    }

    public void updateData(String task_id, String new_task_name, String new_task_desc, String new_task_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASK_NAME, new_task_name);
        cv.put(COLUMN_DESCRIPTION, new_task_desc);
        cv.put(COLUMN_DATE, new_task_date);

        long result = db.update(TABLE_NAME, cv, "id=?", new String[]{task_id});
        if (result == -1) {
            Toast.makeText(context, "Failed Update Data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Succeed Update Data", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteData(String task_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id=?", new String[]{task_id});
        if (result == -1) {
            Toast.makeText(context, "Failed Delete Data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Succeed Delete Data", Toast.LENGTH_SHORT).show();
        }
    }

}
