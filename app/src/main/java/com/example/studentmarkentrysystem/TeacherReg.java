package com.example.studentmarkentrysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TeacherReg extends AppCompatActivity {
    String email_regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextInputEditText name, email, mobile_no, pass;
    String NAME, EMAIL, MOBILE_NO, Password, Semester, Subject;
    String[] sem1 = {"English", "Basic Science", "Basic Mathematics", "Fundamentals of ICT", "Engineering Graphics", "Workshop Practice"};
    String[] sem2 = {"Elements of Electrical Engineering", "Applied Mathematics", "Basic Electronics", "Programming in C", "Bussiness Communication using Computers", "Computer Peripheral and Hardware maintenance", "Web Page Design with HTML"};
    String[] sem3 = {"Object Oriented Programming using C++", "Data Structure Using C", "GreenPeas", "Computer Graphics", "Database Management System", "Digital Techniques"};
    String[] sem4 = {"Java Programming", "Software Engineering", "Data Communication and Computer Network", "Microprocessors", "GUI Application Development using VB.Net"};
    String[] sem5 = {"Operating Systems", "Software Testing", "Client Side Scripting Language", "Enviromental Studies", "Advance Java Programming"};
    String[] sem6 = {"Management", "Programming with Python", "Mobile Application Development", "ETI", "WBP", "Entrepreneurship Development"};
    String[] semesters = {"1", "2", "3", "4", "5", "6"};
    private Spinner subjects, semspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_reg);


        name = (TextInputEditText) findViewById(R.id.name);
        email = (TextInputEditText) findViewById(R.id.mail);
        mobile_no = (TextInputEditText) findViewById(R.id.mob);
        pass = (TextInputEditText) findViewById(R.id.pass);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        semspinner = (Spinner) findViewById(R.id.spinnerSem);
        subjects = (Spinner) findViewById(R.id.spinnerSubjects);

        ArrayAdapter<String> Sem = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, semesters);
        Sem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        semspinner.setAdapter(Sem);
        semspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ArrayAdapter<String> veg = new ArrayAdapter<String>(TeacherReg.this, android.R.layout.simple_spinner_item, sem1);
                    veg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    subjects.setAdapter(veg);
                }
                if (position == 1) {
                    ArrayAdapter<String> grain = new ArrayAdapter<String>(TeacherReg.this, android.R.layout.simple_spinner_item, sem2);
                    grain.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    subjects.setAdapter(grain);
                }
                if (position == 2) {
                    ArrayAdapter<String> pluses = new ArrayAdapter<String>(TeacherReg.this, android.R.layout.simple_spinner_item, sem3);
                    pluses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    subjects.setAdapter(pluses);
                }
                if (position == 3) {
                    ArrayAdapter<String> fruits = new ArrayAdapter<String>(TeacherReg.this, android.R.layout.simple_spinner_item, sem4);
                    fruits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    subjects.setAdapter(fruits);
                }
                if (position == 4) {
                    ArrayAdapter<String> fruits = new ArrayAdapter<String>(TeacherReg.this, android.R.layout.simple_spinner_item, sem5);
                    fruits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    subjects.setAdapter(fruits);
                }
                if (position == 5) {
                    ArrayAdapter<String> fruits = new ArrayAdapter<String>(TeacherReg.this, android.R.layout.simple_spinner_item, sem6);
                    fruits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    subjects.setAdapter(fruits);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    public void tr_reg(View view) {
        NAME = name.getText().toString();
        EMAIL = email.getText().toString();
        MOBILE_NO = mobile_no.getText().toString();
        Password = pass.getText().toString();
        Semester = semspinner.getSelectedItem().toString();
        Subject = subjects.getSelectedItem().toString();


        Map<String, Object> user = new HashMap<>();
        user.put("Name", NAME);
        user.put("Email", EMAIL);
        user.put("MobileNo", MOBILE_NO);
        user.put("Password", Password);
        user.put("Semester", Semester);
        user.put("Subject", Semester);

        if (!EMAIL.matches(email_regex)) {
            email.setError("Enter correct Email");
        } else if (!(MOBILE_NO.length() == 10)) {
            mobile_no.setError("Enter Correct Mobile Number");
        } else if (Password.length() < 6) {
            pass.setError("Password Length must be greater than 5");
        } else {
            db.collection("Teacher")
                    .document(EMAIL)
                    .set(user).
                    addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(TeacherReg.this, "Created Account Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TeacherReg.this, "" + e, Toast.LENGTH_SHORT).show();
                        }
                    });
            firebaseAuth.createUserWithEmailAndPassword(EMAIL, Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(TeacherReg.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TeacherReg.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(TeacherReg.this, "Can't Register!An Error Occurred", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}