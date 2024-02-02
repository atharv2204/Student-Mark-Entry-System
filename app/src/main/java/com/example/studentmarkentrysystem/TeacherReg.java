package com.example.studentmarkentrysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TeacherReg extends AppCompatActivity {
    String[] sem1 = { "Tomato", "Brinjal", "Cabbage", "Cucumber", "Ladyfinger","Fenugreek","Lemon","Onion","Potato","Raddish"};
    String[] sem2 = { "Wheat", "Rice", "Maize", "Sorghum","Barley", "Oat","Rye","Teff"};
    String[] sem3 = { "Soyabeans", "Masoordal", "GreenPeas", "Mungdaal", "Turdal","ChanaDal","BlackedEyedPeas","Chickpeas"};
    String[] sem4 = { "Mango", "Apple", "Banana", "Orange", "Kiwi","Mulberry","Papaya","Peer","Pineapple","Watermelon"};
    String[] semesters = { "1", "2", "3", "4","5","6"};
    private Spinner subjects,semspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_reg);

        semspinner=findViewById(R.id.spinnerSem);
        subjects = (Spinner) findViewById(R.id.spinnerSubjects);

        ArrayAdapter<String> Sem = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, semesters);
        Sem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        semspinner.setAdapter(Sem);
        semspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ArrayAdapter<String> veg = new ArrayAdapter<String>(TeacherReg.this, android.R.layout.simple_spinner_item, sem1);
                    veg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    subjects.setAdapter(veg);
                }
                if (position == 1) {
                    ArrayAdapter<String> grain = new ArrayAdapter<String>(TeacherReg.this, android.R.layout.simple_spinner_item, sem2);
                    grain.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    subjects.setAdapter(grain);
                }
                if (position == 2) {
                    ArrayAdapter<String> pluses = new ArrayAdapter<String>(TeacherReg.this, android.R.layout.simple_spinner_item, sem3);
                    pluses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    subjects.setAdapter(pluses);
                }
                if (position == 3) {
                    ArrayAdapter<String> fruits = new ArrayAdapter<String>(TeacherReg.this, android.R.layout.simple_spinner_item, sem4);
                    fruits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    subjects.setAdapter(fruits);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }
}