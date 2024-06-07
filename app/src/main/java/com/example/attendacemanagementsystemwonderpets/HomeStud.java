package com.example.attendacemanagementsystemwonderpets;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.util.List;


public class HomeStud extends AppCompatActivity {

    private ListView listView;
    private DatabaseHelper dbHelper;
    private String studentName; // Assuming studentName needs to be set somewhere

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_stud);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.attendanceList);
        dbHelper = new DatabaseHelper(this);
        List<YourClassModel> classes = dbHelper.getClasses();
        ArrayAdapter<YourClassModel> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            YourClassModel selectedClass = (YourClassModel) parent.getItemAtPosition(position);
            Toast.makeText(HomeStud.this, "Attendance marked for " + selectedClass.getClassName(), Toast.LENGTH_SHORT).show();

            TextView textView4 = findViewById(R.id.textView4);
            textView4.setText("HELLO THERE " + studentName);
        });
    }
}
