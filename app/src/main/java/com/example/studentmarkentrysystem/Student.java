package com.example.studentmarkentrysystem;

public class Student {

    static String name;
    public Student() {
        // Required empty public constructor for Firestore serialization
    }

    public static void setName(String name) {
        Student.name = name;
    }

    public static String getName() {
        return name;
    }
}
