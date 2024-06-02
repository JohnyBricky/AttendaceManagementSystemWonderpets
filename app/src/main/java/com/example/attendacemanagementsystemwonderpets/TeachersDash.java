package com.example.attendacemanagementsystemwonderpets;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TeachersDash extends AppCompatActivity {
    TextView textView8;
    TextClock textClock2;
    Button btnAddClass;

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

        btnAddClass = findViewById(R.id.btnAddClass);
        textView8 = findViewById(R.id.textView8);
        textClock2 = findViewById(R.id.textClock2);

        // Set up Add Class button
        btnAddClass.setOnClickListener(v -> showAddClassDialog());
    }

    private void showAddClassDialog() {
        // Implementation for showing the dialog to add a class
    }
}
