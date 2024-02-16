package com.example.studentmarkentrysystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MarksTable extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore database;
    MyAdapter myAdapter;
    ArrayList<Student> list;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_table);

        recyclerView=findViewById(R.id.datatable);
//        progressBar=findViewById(R.id.showprogress);

        database=FirebaseFirestore.getInstance();
        list=new ArrayList<Student>();
        myAdapter=new MyAdapter(MarksTable.this,list);
        
        EventChangeListner();


    }

    private void EventChangeListner() {
        database.collection("Students").orderBy("Name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("Firestore Error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc: value.getDocumentChanges()){
                            if (dc.getType()==DocumentChange.Type.ADDED){
                                list.add(dc.getDocument().toObject(Student.class));
                            }
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}