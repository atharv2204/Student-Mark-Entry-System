package com.example.studentmarkentrysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentHome extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    TextView sname, sem;
    FirebaseFirestore db ;
    String name, uemail,sems;
    Context context;
    String[] years = { "1", "2", "3"};
    String[] s1={"1","2"};
    String[] s2={"3","4"};
    String[] s3={"5","6"};
    private Spinner yrspin,semspinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        semspinner=findViewById(R.id.spinnerSem);
        yrspin = (Spinner) findViewById(R.id.spinneryear);

        ArrayAdapter<String> yr = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        yr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        yrspin.setAdapter(yr);
        yrspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ArrayAdapter<String> veg = new ArrayAdapter<String>(StudentHome.this, android.R.layout.simple_spinner_item, s1);
                    veg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    semspinner.setAdapter(veg);
                }
                if (position == 1) {
                    ArrayAdapter<String> grain = new ArrayAdapter<String>(StudentHome.this, android.R.layout.simple_spinner_item, s2);
                    grain.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    semspinner.setAdapter(grain);
                }
                if (position == 2) {
                    ArrayAdapter<String> pluses = new ArrayAdapter<String>(StudentHome.this, android.R.layout.simple_spinner_item, s3);
                    pluses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    semspinner.setAdapter(pluses);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        sname=(TextView) findViewById(R.id.name);
        sem=(TextView) findViewById(R.id.sem);

        db= FirebaseFirestore.getInstance();
        context = getApplicationContext();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        uemail=firebaseUser.getEmail();
        try {
            DocumentReference documentReference = db.collection("Students").document(uemail);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        name = documentSnapshot.getString("Name");

                        sems = documentSnapshot.getString("Current Semester");
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
            Toast.makeText(context, "Error occured", Toast.LENGTH_SHORT).show();
        }
    }

    private void setTextViewValues(String name, String sems) {
        sname.setText(name);
        sem.setText("Semester:"+sems);
    }

    public void viewProfile(View view) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseAuth.signOut();

        Intent intent=new Intent(StudentHome.this, Login.class);
        startActivity(intent);
    }

    public void viewMarks(View view) {
        Intent intent=new Intent(StudentHome.this, ViewMarks.class);
        startActivity(intent);
    }
}