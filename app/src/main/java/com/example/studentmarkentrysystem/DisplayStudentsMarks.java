package com.example.studentmarkentrysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class DisplayStudentsMarks extends AppCompatActivity {
    FirebaseUser firebaseUser;
    FirebaseFirestore firestore ;
    String temail,sems;
    FirebaseAuth firebaseAuth;

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnroll() {
        return enroll;
    }

    public void setEnroll(String enroll) {
        this.enroll = enroll;
    }

    public String getUnit1() {
        return unit1;
    }

    public void setUnit1(String unit1) {
        this.unit1 = unit1;
    }

    public String getUni2() {
        return uni2;
    }

    public void setUni2(String uni2) {
        this.uni2 = uni2;
    }

    String roll,name,enroll,unit1,uni2;
    String[] sem1 = {"English", "Basic Science", "Basic Mathematics", "Fundamentals of ICT", "Engineering Graphics", "Workshop Practice"};
    String[] sem2 = {"Elements of Electrical Engineering", "Applied Mathematics", "Basic Electronics", "Programming in C", "Bussiness Communication using Computers", "Computer Peripheral and Hardware maintenance", "Web Page Design with HTML"};
    String[] sem3 = {"Object Oriented Programming using C++", "Data Structure Using C", "GreenPeas", "Computer Graphics", "Database Management System", "Digital Techniques"};
    String[] sem4 = {"Java Programming", "Software Engineering", "Data Communication and Computer Network", "Microprocessors", "GUI Application Development using VB.Net"};
    String[] sem5 = {"Operating Systems", "Software Testing", "Client Side Scripting Language", "Enviromental Studies", "Advance Java Programming"};
    String[] sem6 = {"Management", "Programming with Python", "Mobile Application Development", "ETI", "WBP", "Entrepreneurship Development"};
    String[] prcs={"Fundamentals of ICT", "Engineering Graphics", "Workshop Practices","Business Communication", "Computer Peripheral and Hardware maintenance","Web Page Design with HTML","GUI pllication Development Using VB.Net"};
    ArrayList<String> prcsList = new ArrayList<>(Arrays.asList(prcs));
    ArrayList<String> semList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_students_marks);


        firestore= FirebaseFirestore.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        temail=firebaseUser.getEmail();

        final TableLayout tableLayout = findViewById(R.id.tableLayout);
        try {
            DocumentReference documentReference = firestore.collection("Teacher").document(temail);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {

                        sems = documentSnapshot.getString("Subject");
                        firestore.collection("Students")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {

                                                String seme=document.getString("Current Semester");
//
                                                if(seme.equals("1"))
                                                {
                                                    semList = new ArrayList<>(Arrays.asList(sem1));
                                                } else if (seme.equals("2")) {
                                                    semList = new ArrayList<>(Arrays.asList(sem2));
                                                }
                                                else if (seme.equals("3")) {
                                                    semList = new ArrayList<>(Arrays.asList(sem3));
                                                }
                                                else if (seme.equals("4")) {
                                                    semList = new ArrayList<>(Arrays.asList(sem4));
                                                }
                                                else if (seme.equals("5")) {
                                                    semList = new ArrayList<>(Arrays.asList(sem5));
                                                }
                                                else if (seme.equals("6")) {
                                                    semList = new ArrayList<>(Arrays.asList(sem6));
                                                }
                                                if(!semList.contains(sems))
                                                {
                                                    continue;
                                                }
                                                TableRow tableRow = new TableRow(DisplayStudentsMarks.this);

                                                String[] rowData = new String[5]; // Array to hold row data
                                                String email=document.getString("Email");
//                                 setRoll(document.getString("RollNo"));
                                                setName(document.getString("Name"));
//                                 setEnroll(document.getString("Enroll"));

                                                TextView textView = new TextView(DisplayStudentsMarks.this);
                                                textView.setText(document.getString("RollNo"));
                                                textView.setPadding(8, 8, 8, 8);
                                                textView.setBackground(getResources().getDrawable(R.drawable.cell_border));
                                                tableRow.addView(textView);
                                                TextView textView1 = new TextView(DisplayStudentsMarks.this);
                                                textView1.setText(document.getString("Name"));
                                                textView1.setPadding(8, 8, 8, 8);
                                                textView1.setBackground(getResources().getDrawable(R.drawable.cell_border));
                                                tableRow.addView(textView1);
                                                TextView textView2 = new TextView(DisplayStudentsMarks.this);
                                                textView2.setText(document.getString("Enroll"));
                                                textView2.setPadding(8, 8, 8, 8);
                                                textView2.setBackground(getResources().getDrawable(R.drawable.cell_border));
                                                tableRow.addView(textView2);
                                                try {
                                                    //get unit test marks
                                                    DocumentReference documentReference = firestore.collection("MarksUnit1").document(email);
                                                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            if (documentSnapshot.exists()) {
//                                            setUnit1(documentSnapshot.getString("English"));
//                                            Toast.makeText(DisplayStudentsMarks.this, "data"+getUnit1(), Toast.LENGTH_SHORT).show();
                                                                TextView textView3 = new TextView(DisplayStudentsMarks.this);
                                                                textView3.setText(documentSnapshot.getString(sems));
                                                                textView3.setPadding(8, 8, 8, 8);
                                                                textView3.setBackground(getResources().getDrawable(R.drawable.cell_border));
                                                                tableRow.addView(textView3);
                                                            }
                                                            else {

                                                            }
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });
                                                }
                                                catch (Exception e){
                                                    Toast.makeText(DisplayStudentsMarks.this, "Error occured", Toast.LENGTH_SHORT).show();
                                                }

                                                //unit 2 marks
                                                try {
                                                    //get unit test marks
                                                    DocumentReference documentReference = firestore.collection("MarksUnit2").document(email);
                                                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            if (documentSnapshot.exists()) {
//                                            setUnit1(documentSnapshot.getString("English"));
//                                                Toast.makeText(DisplayStudentsMarks.this, "data"+getUnit1(), Toast.LENGTH_SHORT).show();
                                                                TextView textView3 = new TextView(DisplayStudentsMarks.this);
                                                                textView3.setText(documentSnapshot.getString(sems));
                                                                textView3.setPadding(8, 8, 8, 8);
                                                                textView3.setBackground(getResources().getDrawable(R.drawable.cell_border));
                                                                tableRow.addView(textView3);
                                                            }
                                                            else {

                                                            }
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });
                                                }
                                                catch (Exception e){
                                                    Toast.makeText(DisplayStudentsMarks.this, "Error occured", Toast.LENGTH_SHORT).show();
                                                }

                                                //Practical Marks
                                                try {
                                                    //get unit test marks
                                                    DocumentReference documentReference = firestore.collection("Practicals").document(email);
                                                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            if (documentSnapshot.exists()) {
//                                            setUnit1(documentSnapshot.getString("English"));
//                                                Toast.makeText(DisplayStudentsMarks.this, "data"+getUnit1(), Toast.LENGTH_SHORT).show();
                                                                TextView textView3 = new TextView(DisplayStudentsMarks.this);
                                                                textView3.setText(documentSnapshot.getString(sems));
                                                                textView3.setPadding(8, 8, 8, 8);
                                                                textView3.setBackground(getResources().getDrawable(R.drawable.cell_border));
                                                                tableRow.addView(textView3);
                                                            }
                                                            else {

                                                            }
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });
                                                }
                                                catch (Exception e){
                                                    Toast.makeText(DisplayStudentsMarks.this, "Error occured", Toast.LENGTH_SHORT).show();
                                                }
//                                rowData[0] = document.getString("RollNo");
//                                rowData[1] = getName();
////                                rowData[2] = document.getString("Enroll");
//                                rowData[3] = getUnit1();
//
//                                // Add the row to the table
//                                addRowToTable(rowData, tableLayout);
                                                tableLayout.addView(tableRow);
                                            }
                                        } else {
                                            Log.w("Error get data", "Error getting documents.", task.getException());
                                        }
                                    }
                                });

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
        catch (Exception e){
            Toast.makeText(DisplayStudentsMarks.this, "Error occured", Toast.LENGTH_SHORT).show();
        }


//        TableLayout tableLayout = findViewById(R.id.tableLayout);

//        // Sample data
//        String[][] data = {
//                {"1", "Alice", "12345", "85", "90"},
//                {"2", "Bob", "54321", "75", "80"},
//                {"3", "Charlie", "67890", "80", "85"}
//                // Add more rows as needed
//        };

//        // Dynamically add rows to the table
//        for (String[] row : data) {
//            TableRow tableRow = new TableRow(this);
//
//            for (String cell : row) {
//                TextView textView = new TextView(this);
//                textView.setText(cell);
//                textView.setPadding(8, 8, 8, 8);
//                textView.setBackground(getResources().getDrawable(R.drawable.cell_border)); // Set custom border
//                tableRow.addView(textView);
//            }
//
//            tableLayout.addView(tableRow);
//        }
    }
    private void addRowToTable(String[] rowData, TableLayout tableLayout) {
        TableRow tableRow = new TableRow(this);

        for (String cell : rowData) {
            TextView textView = new TextView(this);
            textView.setText(cell);
            textView.setPadding(8, 8, 8, 8);
            textView.setBackground(getResources().getDrawable(R.drawable.cell_border));
            tableRow.addView(textView);
        }

        tableLayout.addView(tableRow);
    }

    public void back(View view) {
        finish();
    }
}