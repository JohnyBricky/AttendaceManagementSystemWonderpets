package com.example.attendacemanagementsystemwonderpets;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SchoolDatabase";

    // Table Names
    public static final String TABLE_STUDENTS = "students";
    public static final String TABLE_TEACHERS = "teachers";
    public static final String TABLE_PROFILE = "student_profile"; // Added table name for student profile

    // Common column names
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    // Student Profile column names
    public static final String COLUMN_STUDENT_NUMBER = "student_number";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SECTION = "section";
    public static final String COLUMN_GENDER = "gender";

    // Table Create Statements
    // Student table create statement
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "
            + TABLE_STUDENTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASSWORD + " TEXT" + ")";

    // Teacher table create statement
    private static final String CREATE_TABLE_TEACHERS = "CREATE TABLE "
            + TABLE_TEACHERS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASSWORD + " TEXT" + ")";

    // Student Profile table create statement
    private static final String CREATE_TABLE_PROFILE = "CREATE TABLE "
            + TABLE_PROFILE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_STUDENT_NUMBER + " TEXT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_SECTION + " TEXT,"
            + COLUMN_GENDER + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_TEACHERS);
        db.execSQL(CREATE_TABLE_PROFILE); // Create the profile table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE); // Drop the profile table
        // create new tables
        onCreate(db);
    }

    public boolean addUser(String email, String password, String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(table, null, values);
        return result != -1; // return true if insert is successful
    }

    public boolean addStudentProfile(String studentNumber, String name, String section, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_NUMBER, studentNumber);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SECTION, section);
        values.put(COLUMN_GENDER, gender);

        long result = db.insert(TABLE_PROFILE, null, values);
        return result != -1; // return true if insert is successful
    }

    public boolean addTeacherProfile(String teacherID, String name) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("TEACHER_ID", teacherID);
            contentValues.put("NAME", name);

            long result = db.insert("teacher_table", null, contentValues);
            return result != -1;
        }

    // Method to check student login
    public boolean checkStudentLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENTS,
                new String[]{COLUMN_ID},
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null);

        boolean loginSuccess = cursor.moveToFirst();
        cursor.close();
        return loginSuccess;
    }

    // Method to check teacher login
    public boolean checkTeacherLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TEACHERS,
                new String[]{COLUMN_ID},
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null);

        boolean loginSuccess = cursor.moveToFirst();
        cursor.close();
        return loginSuccess;
    }
}
