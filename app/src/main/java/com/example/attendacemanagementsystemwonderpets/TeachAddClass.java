package com.example.attendacemanagementsystemwonderpets;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Insets;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Random;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsCompat;

import com.example.attendacemanagementsystemwonderpets.DatabaseHelper;
import com.example.attendacemanagementsystemwonderpets.R;

public class TeachAddClass extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText inputCourseName;
    private Spinner inputCourseTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_add_class);
        EdgeToEdge.enable(this);

        dbHelper = new DatabaseHelper(this);
        inputCourseName = findViewById(R.id.inputCourseName);
        inputCourseTime = findViewById(R.id.inputCourseTime);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> saveClassDetails());
    }


    private void saveClassDetails() {
        String className = inputCourseName.getText().toString();
        String courseTime = inputCourseTime.getSelectedItem().toString();
        String classCode = generateClassCode(); // Assuming you have a method to generate the class code

        if (dbHelper.addClass(className, courseTime, classCode)) {
            Toast.makeText(this, "Class added successfully!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK); // Set result to indicate success
            finish(); // Finish the activity
        } else {
            Toast.makeText(this, "Failed to add class. Try again.", Toast.LENGTH_SHORT).show();
        }

    }
    private String generateClassCode() {
        // Define the characters that can be used in the code
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        // Create a random object
        Random random = new Random();

        // Initialize a StringBuilder to store the generated code
        StringBuilder code = new StringBuilder(6);
        String classCode;

        // Loop to generate a 6-character code
        do {
            // Clear the StringBuilder
            code.setLength(0);

            // Append random characters from the defined set to the StringBuilder
            for (int i = 0; i < 6; i++) {
                code.append(chars.charAt(random.nextInt(chars.length())));
            }

            // Convert the StringBuilder to a String
            classCode = code.toString();

            // Check if the generated code already exists
        } while (checkClassCodeExists(classCode));

        return classCode;
    }

    public boolean checkClassCodeExists(String classCode) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_CLASSES, null, DatabaseHelper.COLUMN_CLASS_CODE + "=?", new String[]{classCode}, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }


}
