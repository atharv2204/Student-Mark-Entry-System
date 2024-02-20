package com.example.studentmarkentrysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;

public class MarksEntry extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    TextInputEditText uname, email,marks1,marks2;
    FirebaseFirestore db ;
    String name, semail,temail,smarks1,smarks2;
    static String subject;
    Context context;
    FirebaseUser firebaseUser;
    String[] prcs={"Fundamentals of ICT", "Engineering Graphics", "Workshop Practices","Business Communication", "Computer Peripheral and Hardware maintenance","Web Page Design with HTML","GUI pllication Development Using VB.Net"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_entry);

        uname = (TextInputEditText) findViewById(R.id.name);
        email = (TextInputEditText) findViewById(R.id.mail);
        marks1 = (TextInputEditText) findViewById(R.id.marks1);
        marks2 = (TextInputEditText) findViewById(R.id.marks2);

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
                        String marks2Text = marks2.getText().toString();

//        Toast.makeText(context, "Error occured"+subject, Toast.LENGTH_SHORT).show();
                        Map<String, Object> user = new HashMap<>();
                        user.put(MarksEntry.subject, marks1Text);
                        try{

                            db.collection("Students")
                                    .whereEqualTo("RollNo", studentemail)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                                String documentID = documentSnapshot.getId();
                                                db.collection("MarksUnit1")
                                                        .document(documentID)
                                                        .update(user).
                                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                Toast.makeText(MarksEntry.this, "Marks Filled Successfully", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(MarksEntry.this, "Error"  , Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                            } else {
                                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

}
                        catch (Exception e){
                            Toast.makeText(MarksEntry.this, "" + e, Toast.LENGTH_SHORT).show();
                            Log.d("err",e.toString());
                        }
//        try {
//            DocumentReference documentReference = db.collection("MarksUnit1").document(studentemail);
//            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                @Override
//                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                    if (documentSnapshot.exists()) {
//                        if(documentSnapshot.getString(subject) != null) {
//                            try {
//                                db.collection("MarksUnit1")
//                                        .document(studentemail)
//                                        .update(user).
//                                        addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void unused) {
//                                                Toast.makeText(MarksEntry.this, "Marks Filled Successfully", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Toast.makeText(MarksEntry.this, "" + e, Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                            } catch (Exception e) {
//                                Toast.makeText(MarksEntry.this, "" + e, Toast.LENGTH_LONG).show();
//                                Log.d("err", e.toString());
//
//                            }
//                        }
//                        else {
//                            try {
//                                db.collection("MarksUnit1")
//                                        .document(studentemail)
//                                        .set(user).
//                                        addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void unused) {
//                                                Toast.makeText(MarksEntry.this, "Marks Filled Successfully", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Toast.makeText(MarksEntry.this, "" + e, Toast.LENGTH_LONG).show();
//                                            }
//                                        });
//                            } catch (Exception e) {
//                                Toast.makeText(MarksEntry.this, "" + e, Toast.LENGTH_LONG).show();
//                                Log.d("err", e.toString());
//
//                            }
//                        }
//                    }
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                }
//            });
//        }
//        catch (Exception e){
//            Toast.makeText(context, "Error occured", Toast.LENGTH_SHORT).show();
//        }
//unit test 2 marks
                        Map<String, Object> user2 = new HashMap<>();
                        user2.put(MarksEntry.subject, marks2Text);

//        try {
//            DocumentReference documentReference = db.collection("MarksUnit2").document(studentemail);
//            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                @Override
//                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                    if (documentSnapshot.exists()) {
//                        if(documentSnapshot.getString(subject) != null) {
//                            try {
//                                db.collection("MarksUnit2")
//                                        .document(studentemail)
//                                        .update(user2).
//                                        addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void unused) {
//                                                Toast.makeText(MarksEntry.this, "Marks Filled Successfully", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Toast.makeText(MarksEntry.this, "" + e, Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                            } catch (Exception e) {
//                                Toast.makeText(MarksEntry.this, "" + e, Toast.LENGTH_SHORT).show();
//                                Log.d("err", e.toString());
//
//                            }
//                        }
//                        else {
//                            try {
//                                db.collection("MarksUnit2")
//                                        .document(studentemail)
//                                        .set(user2).
//                                        addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void unused) {
//                                                Toast.makeText(MarksEntry.this, "Marks Filled Successfully", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Toast.makeText(MarksEntry.this, "" + e, Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                            } catch (Exception e) {
//                                Toast.makeText(MarksEntry.this, "" + e, Toast.LENGTH_SHORT).show();
//                                Log.d("err", e.toString());
//
//                            }
//                        }
//                    }
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                }
//            });
//        }
//        catch (Exception e){
//            Toast.makeText(context, "Error occured", Toast.LENGTH_SHORT).show();
//        }



                        try{

                            db.collection("Students")
                                    .whereEqualTo("RollNo", studentemail)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                                String documentID = documentSnapshot.getId();
                                                db.collection("MarksUnit2")
                                                        .document(documentID)
                                                        .update(user2).
                                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                Toast.makeText(MarksEntry.this, "Marks Filled Successfully", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(MarksEntry.this, "Error"  , Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                            } else {
                                                Toast.makeText(context, "Failed"+studentemail, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
//                            db.collection("MarksUnit2")
//                                    .document(studentemail)
//                                    .update(user2).
//                                    addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            Toast.makeText(MarksEntry.this, "Marks Filled Successfully", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Toast.makeText(MarksEntry.this, "Error"  , Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
                                    }
                        catch (Exception e){
                            Toast.makeText(MarksEntry.this, "" + e, Toast.LENGTH_SHORT).show();
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