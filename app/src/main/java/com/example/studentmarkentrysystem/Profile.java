package com.example.studentmarkentrysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class Profile extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    TextInputEditText uname, email,mob;
    FirebaseFirestore db ;
    String name, uemail,mobile;
    Context context;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        uname = (TextInputEditText) findViewById(R.id.updateName);
        email = (TextInputEditText) findViewById(R.id.updateEmail);
        mob = (TextInputEditText) findViewById(R.id.updatePhoneNo);
        email.setEnabled(false);
//        reference = FirebaseDatabase.getInstance().getReference().child("Users");
//        storageReference = FirebaseStorage.getInstance().getReference();

        db= FirebaseFirestore.getInstance();
        context = getApplicationContext();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        uemail=firebaseUser.getEmail();
        try {
            DocumentReference documentReference = db.collection("Student").document(uemail);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        name = documentSnapshot.getString("Name");

                        mobile = documentSnapshot.getString("MobileNo");
                        setEditTextValues(name,uemail,mobile);
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

    private void setEditTextValues(String name, String uemail, String mobile) {
        uname.setText(name);
        email.setText(uemail);
        mob.setText(mobile);

    }

    public void logout(View view) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseAuth.signOut();

        Intent intent=new Intent(Profile.this, Login.class);
        startActivity(intent);
        Toast.makeText(context,"Thanks for using!!",Toast.LENGTH_SHORT).show();
    }
}
