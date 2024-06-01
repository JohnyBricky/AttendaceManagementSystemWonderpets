package com.example.attendacemanagementsystemwonderpets;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendacemanagementsystemwonderpets.DatabaseHelper;
import com.example.attendacemanagementsystemwonderpets.R;

public class TeacherLogin extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText txtLogTeachUser, txtLogTeachPass;
    private Button btnTeachLog, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        dbHelper = new DatabaseHelper(this);
        txtLogTeachUser = findViewById(R.id.txtLogTeachUser);
        txtLogTeachPass = findViewById(R.id.txtLogTeachPass);
        btnTeachLog = findViewById(R.id.btnTeachLog);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnTeachLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginTeacher();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpTeacher();
            }
        });
    }

    private void loginTeacher() {
        String email = txtLogTeachUser.getText().toString();
        String password = txtLogTeachPass.getText().toString();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_TEACHERS,
                new String[]{DatabaseHelper.COLUMN_ID},
                DatabaseHelper.COLUMN_EMAIL + "=? AND " + DatabaseHelper.COLUMN_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            cursor.close();
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void signUpTeacher() {
        String email = txtLogTeachUser.getText().toString();
        String password = txtLogTeachPass.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);

        long newRowId = db.insert(DatabaseHelper.TABLE_TEACHERS, null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show();
        }
    }
}