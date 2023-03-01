package com.example.mykerja.jobList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class JobListDatabase extends SQLiteOpenHelper {
    //this part is to make a database table
    //assign variables
    private final Context context;
    //the database name
    private static final String DATABASE_NAME = "JobList.db";
    //version of the database
    private static final int DATABASE_VERSION = 1;
    //table name of the database
    private static final String TABLE_NAME = "JobList";
    //make columns
    private static final String COLUMN_ID = "job_id";
    private static final String COLUMN_POSITION = "job_position";
    private static final String COLUMN_COMPANY_NAME = "company_name";
    private static final String COLUMN_SALARY = "job_salary";
    private static final String COLUMN_LOCATION = "job_location";
    private static final String COLUMN_DESC_LINE1 = "desc_line1";
    private static final String COLUMN_DESC_LINE2 = "desc_line2";
    private static final String COLUMN_DESC_LINE3 = "desc_line3";




    public JobListDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create Job Listing Table SQL
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_POSITION + " TEXT, " + COLUMN_COMPANY_NAME + " TEXT, " + COLUMN_SALARY + " INTEGER, " + COLUMN_LOCATION + " TEXT, " + COLUMN_DESC_LINE1 + " TEXT, " + COLUMN_DESC_LINE2 + " TEXT, " + COLUMN_DESC_LINE3 + " TEXT);");
        //Create Users Table SQL
        db.execSQL("CREATE TABLE users (userId INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS users ");
        onCreate(db);
    }

    public void addJob(String position, String companyName, int salary, String location, String line1, String line2, String line3){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_POSITION, position);
        cv.put(COLUMN_COMPANY_NAME, companyName);
        cv.put(COLUMN_SALARY, salary);
        cv.put(COLUMN_LOCATION, location);
        cv.put(COLUMN_DESC_LINE1, line1);
        cv.put(COLUMN_DESC_LINE2, line2);
        cv.put(COLUMN_DESC_LINE3, line3);

        long result = db.insert(TABLE_NAME, null, cv);

        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void addDescJob(String row_id, String line1, String line2, String line3){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DESC_LINE1, line1);
        cv.put(COLUMN_DESC_LINE2, line2);
        cv.put(COLUMN_DESC_LINE3, line3);

        long result = db.update(TABLE_NAME, cv, "job_id=?", new String[]{row_id});

        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        }
    }

     public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getJobList(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = job_id=?";

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();

        }
        return cursor;
    }

    public void updateData(String row_id, String position, String company, String salary, String location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_POSITION, position);
        cv.put(COLUMN_COMPANY_NAME, company);
        cv.put(COLUMN_SALARY, salary);
        cv.put(COLUMN_LOCATION, location);


        long result = db.update(TABLE_NAME, cv, "job_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Updated", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDescJob(String row_id, String line1, String line2, String line3){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DESC_LINE1, line1);
        cv.put(COLUMN_DESC_LINE2, line2);
        cv.put(COLUMN_DESC_LINE3, line3);


        long result = db.update(TABLE_NAME, cv, "job_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Updated", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRow(String row_id){
        SQLiteDatabase db = getWritableDatabase();
        long result = db.delete(TABLE_NAME, "job_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
        }
    }
/*
This part is for user sign up and login method
* */

    //Insert data into database
    public boolean insertData (String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();

        contentValues.put("email",email);
        contentValues.put("password",password);
        long result = db.insert("users",null,contentValues);

        return result != -1;
    }

    //check redundant email in  db  method
    public boolean checkEmail (String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?",new String[] {email});

        return cursor.getCount() > 0;
    }

    //Check email and password method
    public boolean checkEmailPassword (String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ? and password = ?",new String[] {email,password});

        return cursor.getCount() > 0;
    }
    //To get user id
    public int getUserID (String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT userId from users WHERE email = ?",new String[]{email});

        int id = -1;
        if(cursor.moveToFirst()){
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

}
