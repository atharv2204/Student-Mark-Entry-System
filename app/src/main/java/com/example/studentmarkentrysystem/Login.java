package com.example.studentmarkentrysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    RelativeLayout signup;
    TextInputEditText uname, pass;
    String email_regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseFirestore firestore;
    public static String get_DBName,get_DBEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uname = (TextInputEditText) findViewById(R.id.mail);
        pass = (TextInputEditText) findViewById(R.id.pass);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firestore= FirebaseFirestore.getInstance();

        signup=findViewById(R.id.gotosignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Registration.class));
            }
        });
        if (firebaseUser!=null)
        {
            try {
                DocumentReference documentReference = firestore.collection("Students").document(firebaseUser.getEmail());
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            startActivity(new Intent(Login.this, StudentHome.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        startActivity(new Intent(Login.this, TeacherReg.class));
                        finish();
                    }
                });
            }
            catch (Exception e){
                Toast.makeText(Login.this, "Error occured", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }


    public void login(View view) {
        String email = uname.getText().toString();
        String psw = pass.getText().toString();

        if (!email.matches(email_regex)) {
            uname.setError("Enter correct Email");
        } else if (psw.length() < 6) {
            pass.setError("Password Length must be greater than 5");
        } else {

            firebaseAuth.signInWithEmailAndPassword(email,psw).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    DocumentReference documentReference=firestore.collection("Students").document(email);
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){

                                startActivity(new Intent(Login.this, StudentHome.class));
                                finish();
                                Toast.makeText(getApplicationContext(), "Login in Successful", Toast.LENGTH_SHORT).show();
//                                 Intent intent = new Intent(Login.this, StudentHome.class);
////                                intent.putExtra("NAME",get_DBName);
////                                intent.putExtra("Email",get_DBEmail);
//                                startActivity(intent);
//                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            startActivity(new Intent(Login.this, TeacherReg.class));
                            finish();
                            Toast.makeText(getApplicationContext(), "Login in Failed\nPlease Recheck Username and Password", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Login in Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}