package com.example.attendacemanagementsystemwonderpets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText txtEmail, txtPassword, txtConfirmPassword;
    private CheckBox chkStudent, chkTeacher;
    private Button btnSignUp;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        // Initialize views
        txtEmail = findViewById(R.id.txtSignUser);
        txtPassword = findViewById(R.id.txtSignPass);
        txtConfirmPassword = findViewById(R.id.txtSignConPass);
        chkStudent = findViewById(R.id.checkBox);
        chkTeacher = findViewById(R.id.chkUser);
        btnSignUp = findViewById(R.id.btnSignIn);

        dbHelper = new DatabaseHelper(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String confirmPassword = txtConfirmPassword.getText().toString().trim();
        boolean isStudent = chkStudent.isChecked();
        boolean isTeacher = chkTeacher.isChecked();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email or password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Determine the table based on user type
        String table = isStudent ? DatabaseHelper.TABLE_STUDENTS : DatabaseHelper.TABLE_TEACHERS;

        // Insert user in database
        boolean insertSuccess = dbHelper.addUser(email, password, table);
        if (insertSuccess) {
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();

            // Navigate to StudProfileActivity if the student checkbox is checked
            if (isStudent) {
                Intent intent = new Intent(SignUpActivity.this, StudProfileActivity.class);
                startActivity(intent);
                finish(); // Finish the current activity to remove it from the back stack
            }
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }
}