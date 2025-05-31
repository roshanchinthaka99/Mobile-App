package com.example.citycyclerentals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "UserDB.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE users (" +
                "email TEXT PRIMARY KEY, " +
                "nic TEXT, " +
                "name TEXT, " +
                "phone TEXT, " +
                "password TEXT)";
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    // Register user
    public boolean registerUser(String email, String nic, String name, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("nic", nic);
        values.put("name", name);
        values.put("phone", phone);
        values.put("password", password);
        long result = db.insert("users", null, values);
        return result != -1;
    }

    // Login user
    public boolean loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=? AND password=?", new String[]{email, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;


    }

    public boolean UpdateUser(String nic, String name, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nic", nic);
        values.put("name", name);
        values.put("phone", phone);
        values.put("password", password);
        int rows = db.update("users", values, "nic = ?", new String[]{nic});
        return rows > 0;
    }


    public Cursor getUserByNIC(String nic) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM users WHERE nic = ?", new String[]{nic});
    }

    public boolean updateUser(String nic, String name, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        values.put("password", password);

        int rows = db.update("users", values, "nic = ?", new String[]{nic});
        return rows > 0;
    }

}
