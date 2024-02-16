package com.example.studentmarkentrysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TeacherHome extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    TextView sname, sem;
    FirebaseFirestore db ;
    String name, uemail,sems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        sname=(TextView) findViewById(R.id.name);
        sem=(TextView) findViewById(R.id.sem);

        db= FirebaseFirestore.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        uemail=firebaseUser.getEmail();
        try {
            DocumentReference documentReference = db.collection("Teacher").document(uemail);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        name = documentSnapshot.getString("Name");

                        sems = documentSnapshot.getString("Subject");
                        setTextViewValues(name,sems);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
        catch (Exception e){
            Toast.makeText(TeacherHome.this, "Error occured", Toast.LENGTH_SHORT).show();
        }
    }

    public void enterMarks(View view) {
        try{
            Intent intent=new Intent(TeacherHome.this, MarksEntry.class);
            startActivity(intent);
        }
        catch (Exception e)
        {
            Toast.makeText(TeacherHome.this, "Error occured"+e, Toast.LENGTH_LONG).show();

        }

    }

    public void practical(View view) {
        Intent intent=new Intent(TeacherHome.this, PracticalEntry.class);
        startActivity(intent);
    }
    private void setTextViewValues(String name, String sems) {
        sname.setText(name);
        sem.setText("Subject:"+sems);
    }

    public void lgt(View view) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseAuth.signOut();
        Intent intent=new Intent(TeacherHome.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void viewStudentMarks(View view) {

        startActivity(new Intent(TeacherHome.this, DisplayStudentsMarks.class));
    }
}