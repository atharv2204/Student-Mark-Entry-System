package com.example.studentmarkentrysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class StudentReg extends AppCompatActivity {
    String email_regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextInputEditText name,email,mobile_no,pass,sem,rollno,enroll;
    String NAME,EMAIL,ROLL_NO,ENROLL,MOBILE_NO,Password,Semester;
    String[] prcs={"Fundamentals of ICT", "Engineering Graphics", "Workshop Practices","Business Communication", "Computer Peripheral and Hardware maintenance","Web Page Design with HTML","GUI pllication Development Using VB.Net"};
    String[] sem1 = {"English", "Basic Science", "Basic Mathematics", "Fundamentals of ICT", "Engineering Graphics", "Workshop Practice"};
    String[] sem2 = {"Elements of Electrical Engineering", "Applied Mathematics", "Basic Electronics", "Programming in C", "Bussiness Communication using Computers", "Computer Peripheral and Hardware maintenance", "Web Page Design with HTML"};
    String[] sem3 = {"Object Oriented Programming using C++", "Data Structure Using C", "GreenPeas", "Computer Graphics", "Database Management System", "Digital Techniques"};
    String[] sem4 = {"Java Programming", "Software Engineering", "Data Communication and Computer Network", "Microprocessors", "GUI Application Development using VB.Net"};
    String[] sem5 = {"Operating Systems", "Software Testing", "Client Side Scripting Language", "Enviromental Studies", "Advance Java Programming"};
    String[] sem6 = {"Management", "Programming with Python", "Mobile Application Development", "ETI", "WBP", "Entrepreneurship Development"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_reg);

        name=(TextInputEditText) findViewById(R.id.name);
        email=(TextInputEditText) findViewById(R.id.mail);
        rollno=(TextInputEditText) findViewById(R.id.rno);
        enroll=(TextInputEditText) findViewById(R.id.enrol);
        mobile_no=(TextInputEditText) findViewById(R.id.mob);
        pass=(TextInputEditText) findViewById(R.id.pass);
        sem=(TextInputEditText) findViewById(R.id.sem);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    public void std_Reg(View view){
        NAME=name.getText().toString();
        EMAIL=email.getText().toString();
        ROLL_NO=rollno.getText().toString();
        ENROLL=enroll.getText().toString();
        MOBILE_NO=mobile_no.getText().toString();
        Password=pass.getText().toString();
        Semester=sem.getText().toString();

        Map<String, Object> user = new HashMap<>();
        user.put("Name", NAME);
        user.put("Email", EMAIL);
        user.put("RollNo", ROLL_NO);
        user.put("Enroll", ENROLL);
        user.put("MobileNo", MOBILE_NO);
        user.put("Password", Password);
        user.put("Current Semester", Semester);

        Map<String, Object> practicals = new HashMap<>();
        Map<String, String> subjectsMap = new HashMap<>();

        // Populate the map with subjects from each semester
        int seme=Integer.parseInt(Semester);
        if(seme==1)
        {
            addSubjectsToMap(subjectsMap, sem1);
        }
        else if(seme==2)
        {
            addSubjectsToMap(subjectsMap, sem1);
            addSubjectsToMap(subjectsMap, sem2);

        }
        else if (seme==3) {
            addSubjectsToMap(subjectsMap, sem1);
            addSubjectsToMap(subjectsMap, sem2);
            addSubjectsToMap(subjectsMap, sem3);
        }
        else if (seme==4) {
            addSubjectsToMap(subjectsMap, sem1);
            addSubjectsToMap(subjectsMap, sem2);
            addSubjectsToMap(subjectsMap, sem3);
            addSubjectsToMap(subjectsMap, sem4);
        }
        else if (seme==5) {
            addSubjectsToMap(subjectsMap, sem1);
            addSubjectsToMap(subjectsMap, sem2);
            addSubjectsToMap(subjectsMap, sem3);
            addSubjectsToMap(subjectsMap, sem4);
            addSubjectsToMap(subjectsMap, sem5);
        }
        else if (seme==6) {
            addSubjectsToMap(subjectsMap, sem1);
            addSubjectsToMap(subjectsMap, sem2);
            addSubjectsToMap(subjectsMap, sem3);
            addSubjectsToMap(subjectsMap, sem4);
            addSubjectsToMap(subjectsMap, sem5);
            addSubjectsToMap(subjectsMap, sem6);
        }


        // Populate the map with initial values of 0
        for (String subject : prcs) {
            practicals.put(subject, "0");
        }

        if (!EMAIL.matches(email_regex)) {
            email.setError("Enter correct Email");
        }
        else if(!(MOBILE_NO.length()==10)){
            mobile_no.setError("Enter Correct Mobile Number");
        }
        else if (Password.length() < 6) {
            pass.setError("Password Length must be greater than 5");
        }
        else if (Integer.parseInt(Semester) > 6 || Integer.parseInt(Semester) <0) {
            sem.setError("Enter Valid Semester");
        }
        else{
            db.collection("Students")
                    .document(EMAIL)
                    .set(user).
                    addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(StudentReg.this, "Created Account Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(StudentReg.this, ""+e, Toast.LENGTH_SHORT).show();
                        }
                    });
            db.collection("MarksUnit1")
                    .document(EMAIL)
                    .set(subjectsMap).
                    addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(StudentReg.this, "Created Account Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(StudentReg.this, ""+e, Toast.LENGTH_SHORT).show();
                        }
                    });
            db.collection("MarksUnit2")
                    .document(EMAIL)
                    .set(subjectsMap).
                    addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(StudentReg.this, "Created Account Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(StudentReg.this, ""+e, Toast.LENGTH_SHORT).show();
                        }
                    });
            db.collection("Practicals")
                    .document(EMAIL)
                    .set(subjectsMap).
                    addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(StudentReg.this, "Created Account Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(StudentReg.this, ""+e, Toast.LENGTH_SHORT).show();
                        }
                    });
            firebaseAuth.createUserWithEmailAndPassword(EMAIL, Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(StudentReg.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(StudentReg.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(StudentReg.this, "Can't Register!An Error Occurred", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private static void addSubjectsToMap(Map<String, String> subjectsMap, String[] subjects) {
        for (String subject : subjects) {
            subjectsMap.put(subject, "0");
        }
    }
    public void loginPage(View view) {
        Intent intent=new Intent(StudentReg.this, Login.class);
        startActivity(intent);
    }
}