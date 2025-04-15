package com.example.clinicalapplication.models;

public class Patient extends Person {
    public Patient(String id, String name, String address, String phone) {
        super(id, name, address, phone);
    }

    @Override
    public String toString() {
        return "Patient: " + name;
    }
}
