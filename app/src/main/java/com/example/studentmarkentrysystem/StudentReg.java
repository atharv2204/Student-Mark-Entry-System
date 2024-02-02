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
    TextInputEditText name,email,mobile_no,pass,sem;
    String NAME,EMAIL,MOBILE_NO,Password,Semester;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_reg);

        name=(TextInputEditText) findViewById(R.id.name);
        email=(TextInputEditText) findViewById(R.id.mail);
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
        MOBILE_NO=mobile_no.getText().toString();
        Password=pass.getText().toString();
        Semester=sem.getText().toString();

        Map<String, Object> user = new HashMap<>();
        user.put("Name", NAME);
        user.put("Email", EMAIL);
        user.put("MobileNo", MOBILE_NO);
        user.put("Password", Password);
        user.put("Current Semester", Semester);

        if (!EMAIL.matches(email_regex)) {
            email.setError("Enter correct Email");
        }
        else if(!(MOBILE_NO.length()==10)){
            mobile_no.setError("Enter Correct Mobile Number");
        }
        else if (Password.length() < 6) {
            pass.setError("Password Length must be greater than 5");
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

    public void loginPage(View view) {
        Intent intent=new Intent(StudentReg.this, Login.class);
        startActivity(intent);
    }
}