package com.example.attendacemanagementsystemwonderpets;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TeacherProfileActivity extends AppCompatActivity {

    private EditText txtWriteTeachName, txtWriteTeachNum;
    private Button btnSubmitProfile, btnSelectImage;
    private ImageView imageProfileView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_profile);

        dbHelper = new DatabaseHelper(this);
        txtWriteTeachName = findViewById(R.id.txtWriteTeachName);
        txtWriteTeachNum = findViewById(R.id.txtWriteTeachNum);
        btnSubmitProfile = findViewById(R.id.btnSubmitProfile);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        imageProfileView = findViewById(R.id.imageProfileView);

        btnSubmitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitProfile();
            }
        });

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to select image from gallery
            }
        });
    }

    private void submitProfile() {
        String teacherID = txtWriteTeachNum.getText().toString();
        String name = txtWriteTeachName.getText().toString();

        if (teacherID.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        boolean isInserted = dbHelper.addTeacherProfile(teacherID, name);
        if (isInserted) {
            Toast.makeText(this, "Profile Submitted Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to Submit Profile", Toast.LENGTH_SHORT).show();
        }
    }
}
