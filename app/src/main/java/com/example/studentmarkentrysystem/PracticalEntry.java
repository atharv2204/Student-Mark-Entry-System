package com.example.studentmarkentrysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PracticalEntry extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    TextInputEditText uname, email,marks1,marks2;
    FirebaseFirestore db ;
    String name, semail,temail,smarks1,smarks2,subject;
    Context context;
    FirebaseUser firebaseUser;
    String[] prcs={"Fundamentals of ICT", "Engineering Graphics", "Workshop Practices","Business Communication", "Computer Peripheral and Hardware maintenance","Web Page Design with HTML","GUI pllication Development Using VB.Net"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_entry);

        uname = (TextInputEditText) findViewById(R.id.name);
        email = (TextInputEditText) findViewById(R.id.mail);
        marks1 = (TextInputEditText) findViewById(R.id.marks1);


        db= FirebaseFirestore.getInstance();
        context = getApplicationContext();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        temail=firebaseUser.getEmail();
    }

    public void addMarks(View view) {
        try {
            DocumentReference documentReference = db.collection("Teacher").document(temail);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        subject = documentSnapshot.getString("Subject");

                        String studentemail=email.getText().toString();
                        String marks1Text = marks1.getText().toString();


                        Map<String, Object> user = new HashMap<>();
                        user.put(subject, marks1Text);
                        try{
                            db.collection("Practicals")
                                    .document(studentemail)
                                    .update(user).
                                    addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(PracticalEntry.this, "Marks Filled Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(PracticalEntry.this, "" + e, Toast.LENGTH_SHORT).show();
                                        }
                                    });}
                        catch (Exception e){
                            Toast.makeText(PracticalEntry.this, "Error"+e , Toast.LENGTH_SHORT).show();
                            Log.d("err",e.toString());

                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
        catch (Exception e){
            Toast.makeText(context, "Error occured", Toast.LENGTH_SHORT).show();
        }
//        name=uname.getText().toString();

        finish();
    }
    public void back(View view) {
        finish();
    }
}