package com.example.attendacemanagementsystemwonderpets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TeachersDash extends AppCompatActivity {
    private DatabaseHelper dbHelper; // Assuming you have a DatabaseHelper class
    private TextView textView8;
    private TextClock textClock2;
    private Button btnAddClass;
    private ListView classList;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            updateClassList();
        }
    }

    private void updateClassList() {
        dbHelper = new DatabaseHelper(this); // Initialize dbHelper if not already done
        String className = ""; // Initialize className
        String courseTime = ""; // Initialize courseTime
        String classCode = ""; // Initialize classCode

        if (dbHelper.addClass(className, courseTime, classCode)) {
            Toast.makeText(this, "Class added successfully!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK); // Set result to indicate success
            finish(); // Finish the activity
        } else {
            Toast.makeText(this, "Failed to add class. Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teachers_dash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this); // Initialize dbHelper
        btnAddClass = findViewById(R.id.btnAddClass);
        textView8 = findViewById(R.id.textView8);
        textClock2 = findViewById(R.id.textClock2);

        btnAddClass.setOnClickListener(v -> {
            Intent intent = new Intent(TeachersDash.this, TeachAddClass.class);
            startActivityForResult(intent, 1); // Using request code 1
        });
    }
}