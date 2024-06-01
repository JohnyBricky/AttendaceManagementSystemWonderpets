package com.example.attendacemanagementsystemwonderpets;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudProfileActivity extends AppCompatActivity {

    private EditText txtWriteStudNum, txtWriteSection, txtWriteStudName;
    private CheckBox chkMale, chkFemale;
    private Button btnSubmitProfile;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_profile);

        dbHelper = new DatabaseHelper(this);
        txtWriteStudNum = findViewById(R.id.txtWriteStudNum);
        txtWriteSection = findViewById(R.id.txtWriteSection);
        txtWriteStudName = findViewById(R.id.txtWriteStudName);
        chkMale = findViewById(R.id.chkMale);
        chkFemale = findViewById(R.id.chkFemale);
        btnSubmitProfile = findViewById(R.id.btnSubmitProfile);

        btnSubmitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitProfile();
            }
        });
    }

    private void submitProfile() {
        String studentNumber = txtWriteStudNum.getText().toString();
        String name = txtWriteStudName.getText().toString();
        String section = txtWriteSection.getText().toString();
        String gender = chkMale.isChecked() ? "Male" : chkFemale.isChecked() ? "Female" : "";

        if (studentNumber.isEmpty() || name.isEmpty() || section.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "Please fill all fields and select gender", Toast.LENGTH_LONG).show();
            return;
        }

        boolean isInserted = dbHelper.addStudentProfile(studentNumber, name, section, gender);
        if (isInserted) {
            Toast.makeText(this, "Profile Submitted Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to Submit Profile", Toast.LENGTH_SHORT).show();
        }
    }
}