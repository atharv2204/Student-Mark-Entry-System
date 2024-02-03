package com.example.studentmarkentrysystem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewMarks extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    LinearLayout linearLayout;
    FirebaseFirestore firestore ;
    Context context;
    TextView textview;

    CardView cardview;
    String[] sem1 = {"English", "Basic Science", "Basic Mathematics", "Fundamentals of ICT", "Engineering Graphics", "Workshop Practice"};
    String[] sem2 = {"Elements of Electrical Engineering", "Applied Mathematics", "Basic Electronics", "Programming in C", "Bussiness Communication using Computers", "Computer Peripheral and Hardware maintenance", "Web Page Design with HTML"};
    String[] sem3 = {"Object Oriented Programming using C++", "Data Structure Using C", "GreenPeas", "Computer Graphics", "Database Management System", "Digital Techniques"};
    String[] sem4 = {"Java Programming", "Software Engineering", "Data Communication and Computer Network", "Microprocessors", "GUI Application Development using VB.Net"};
    String[] sem5 = {"Operating Systems", "Software Testing", "Client Side Scripting Language", "Enviromental Studies", "Advance Java Programming"};
    String[] sem6 = {"Management", "Programming with Python", "Mobile Application Development", "ETI", "WBP", "Entrepreneurship Development"};

    String email,sems;
    String[] prcs={"Fundamentals of ICT", "Engineering Graphics", "Workshop Practices","Business Communication", "Computer Peripheral and Hardware maintenance","Web Page Design with HTML","GUI pllication Development Using VB.Net"};
    ArrayList<String> prcsList = new ArrayList<>(Arrays.asList(prcs));
    ArrayList<String> semList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_marks);


        firestore= FirebaseFirestore.getInstance();
        context = getApplicationContext();
        linearLayout = findViewById(R.id.linearLayout);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        email=firebaseUser.getEmail();
        sems= getIntent().getStringExtra("semester");
        if(sems.equals("1"))
        {
            semList = new ArrayList<>(Arrays.asList(sem1));
        } else if (sems.equals("2")) {
            semList = new ArrayList<>(Arrays.asList(sem2));
        }
        else if (sems.equals("3")) {
            semList = new ArrayList<>(Arrays.asList(sem3));
        }
        else if (sems.equals("4")) {
            semList = new ArrayList<>(Arrays.asList(sem4));
        }
        else if (sems.equals("5")) {
            semList = new ArrayList<>(Arrays.asList(sem5));
        }
        else if (sems.equals("6")) {
            semList = new ArrayList<>(Arrays.asList(sem6));
        }
        CollectionReference collectionRef = firestore.collection("MarksUnit1");

        // Use get() to retrieve the documents in the collection
        collectionRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Get the result (QuerySnapshot) from the task
                QuerySnapshot querySnapshot = task.getResult();

                // Iterate through each document in the QuerySnapshot
                for (QueryDocumentSnapshot document : querySnapshot) {
                    // Get the document ID
                    String documentId = document.getId();
                    System.out.println("Document ID: " + documentId);

                    // Get all fields in the document
                    for (String fieldName : document.getData().keySet()) {
                        // Access each field and its value
                        Object value = document.get(fieldName);
                        if(!prcsList.contains(fieldName) && semList.contains(fieldName)){
                            addDataToView(fieldName, value.toString());
                            System.out.println("Field: " + fieldName + ", Value: " + value);
                        }
                        else {
                            continue;
                        }
                    }

                    System.out.println("------------"); // Separating documents
                }
            } else {
                // Handle errors
                Exception e = task.getException();
                System.out.println("Error getting documents: " + e);
            }
        });
    }
    private void addDataToView(String subject, String marks) {
        cardview = new CardView(getApplicationContext());
        LinearLayout linearLayoutInner = new LinearLayout(getApplicationContext());

        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutparams.setMargins(20, 15, 20, 15);
        //linearLayoutInner.setBackground(getDrawable(R.drawable.cardview_bg));
        cardview.setCardBackgroundColor(Color.parseColor("#66CDAA"));

        LinearLayout.LayoutParams layoutparamscardview = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutparamscardview.setMargins(45, 15, 45, 15);


        cardview.setLayoutParams(layoutparamscardview);
        cardview.setRadius(15);
        cardview.setPadding(25, 25, 25, 25);

        cardview.setMaxCardElevation(30);
        cardview.setMaxCardElevation(6);
        textview = new TextView(getApplicationContext());
        textview.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        textview.setLayoutParams(layoutparams);

        String text = "Subject : "+subject+" \nMarks : "+marks;
        textview.setText(text);
        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        textview.setTextColor(Color.WHITE);
        textview.setPadding(25, 25, 25, 25);
        textview.setGravity(Gravity.CENTER);
        linearLayoutInner.addView(textview);


        linearLayoutInner.setOrientation(LinearLayout.VERTICAL);
        cardview.addView(linearLayoutInner);

        linearLayout.addView(cardview);

    }

    public void back(View view) {
        finish();
    }
}