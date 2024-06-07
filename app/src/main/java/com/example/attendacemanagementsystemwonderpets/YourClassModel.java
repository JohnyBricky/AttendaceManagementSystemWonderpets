package com.example.attendacemanagementsystemwonderpets;

import androidx.appcompat.app.AppCompatActivity;

public class YourClassModel extends AppCompatActivity {
    private String className;
    private String courseTime;
    private String classCode;

    public YourClassModel(String className, String courseTime, String classCode) {
        this.className = className;
        this.courseTime = courseTime;
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public String getClassCode() {
        return classCode;
    }
}
