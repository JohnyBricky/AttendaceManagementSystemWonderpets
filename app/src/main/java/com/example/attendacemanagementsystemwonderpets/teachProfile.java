package com.example.attendacemanagementsystemwonderpets;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class teachProfile extends AppCompatActivity {

    private static final int REQUEST_GALLERY = 1;
    private static final String TAG = "teachProfile";

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
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imageProfileView.setImageURI(imageUri);
        }
    }

    private void submitProfile() {
        String teacherID = txtWriteTeachNum.getText().toString();
        String name = txtWriteTeachName.getText().toString();

        Log.d(TAG, "Submitting Profile: TeacherID = " + teacherID + ", Name = " + name);

        if (teacherID.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        boolean isInserted = dbHelper.addTeacherProfile(teacherID, name);
        if (isInserted) {
            Toast.makeText(this, "Profile Submitted Successfully", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Profile Submitted Successfully");
        } else {
            Toast.makeText(this, "Failed to Submit Profile", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Failed to Submit Profile");
        }
    }
}
