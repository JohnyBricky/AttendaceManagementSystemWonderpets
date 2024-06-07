package com.example.attendacemanagementsystemwonderpets;

import android.content.ContentValues;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AttendanceManagement.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_STUDENTS = "students";
    public static final String TABLE_TEACHER_PROFILES = "teacher_profiles";
    public static final String TABLE_CLASSES = "classes";

    // Common column names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    // Teacher profile specific column names
    public static final String COLUMN_TEACHER_ID = "teacherID";
    public static final String COLUMN_TEACHER_NAME = "teacherName";

    // Student profile specific column names
    public static final String COLUMN_STUDENT_NUMBER = "studentNumber";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SECTION = "section";
    public static final String COLUMN_GENDER = "gender";

    // Class specific column names
    public static final String COLUMN_CLASS_CODE = "classCode";
    public static final String COLUMN_CLASS_NAME = "className";
    public static final String COLUMN_COURSE_TIME = "courseTime";

    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "
            + TABLE_STUDENTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT UNIQUE,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_STUDENT_NUMBER + " TEXT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_SECTION + " TEXT,"
            + COLUMN_GENDER + " TEXT" + ")";

    private static final String CREATE_TABLE_TEACHER_PROFILES = "CREATE TABLE "
            + TABLE_TEACHER_PROFILES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT UNIQUE,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_TEACHER_ID + " TEXT,"
            + COLUMN_TEACHER_NAME + " TEXT" + ")";

    private static final String CREATE_TABLE_CLASSES = "CREATE TABLE "
            + TABLE_CLASSES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CLASS_NAME + " TEXT,"
            + COLUMN_COURSE_TIME + " TEXT,"
            + COLUMN_CLASS_CODE + " TEXT UNIQUE" + ")";
    private static String columnId;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static String getColumnId() {
        return COLUMN_ID;
    }

    public static String getColumnEmail() {
        return COLUMN_EMAIL;
    }

    public static String getColumnPassword() {
        return COLUMN_PASSWORD;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_TEACHER_PROFILES);
        db.execSQL(CREATE_TABLE_CLASSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHER_PROFILES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES);
        onCreate(db);
    }

    public boolean addClass(String className, String courseTime, String classCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLASS_NAME, className);
        values.put(COLUMN_COURSE_TIME, courseTime);
        values.put(COLUMN_CLASS_CODE, classCode);

        long result = -1;
        try {
            result = db.insertOrThrow(TABLE_CLASSES, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return result != -1;
    }

    public boolean addUser(String email, String password, String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long result = -1;
        try {
            result = db.insertOrThrow(table, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return result != -1;
    }

    public boolean addTeacherProfile(String teacherID, String teacherName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEACHER_ID, teacherID);
        values.put(COLUMN_TEACHER_NAME, teacherName);

        long result = -1;
        try {
            result = db.insertOrThrow(TABLE_TEACHER_PROFILES, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return result != -1;
    }

    public List<YourClassModel> getClasses() {
        List<YourClassModel> classes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CLASSES, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String className = cursor.getString(cursor.getColumnIndex(COLUMN_CLASS_NAME));
                String courseTime = cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_TIME));
                String classCode = cursor.getString(cursor.getColumnIndex(COLUMN_CLASS_CODE));
                YourClassModel yourClassModel = new YourClassModel(className, courseTime, classCode);
                classes.add(yourClassModel);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return classes;
    }


    public boolean addStudentProfile(String studentNumber, String name, String section, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_NUMBER, studentNumber);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SECTION, section);
        values.put(COLUMN_GENDER, gender);

        long result = -1;
        try {
            result = db.insertOrThrow(TABLE_STUDENTS, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return result != -1;
    }

    public boolean checkUser(String email, String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table, null, COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

}
