package com.example.todolist.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class UsersDatabaseHelper extends SQLiteOpenHelper {

    Context context;
    private static final String DATABASE_NAME = "my_todolist_db";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_NAME = "my_users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_EMAIL = "user_email";
    private static final String COLUMN_PASSWORD = "user_password";

    public UsersDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FULL_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(String _fullName, String _email, String _password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FULL_NAME, _fullName);
        cv.put(COLUMN_EMAIL, _email);
        cv.put(COLUMN_PASSWORD, _password);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed Insert Data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Succeed Insert Data", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Recycle")
    public boolean checkLogin(String _email, String _password) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT *  FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor;

        if (db != null) {
            cursor = db.rawQuery(query, new String[] {_email, _password});

            if (cursor.getCount() > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @SuppressLint("Recycle")
    public int getIdUser(String _email, String _password) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor;

        if (db != null) {
            cursor = db.rawQuery(query, new String[] {_email, _password});

            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                return cursor.getInt(0);
            } else {
                return 0;
            }
        }
        return 0;
    }

    @SuppressLint("Recycle")
    public String getEmailUser(String _email) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_EMAIL + " FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ?";
        Cursor cursor;

        if (db != null) {
            cursor = db.rawQuery(query, new String[] {_email});

            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                return cursor.getString(0);
            } else {
                return null;
            }
        }
        return null;
    }

}
