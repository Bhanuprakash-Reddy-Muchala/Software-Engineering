package com.example.clinicalapplication.models;

import java.util.ArrayList;
import java.util.List;

public class Physiotherapist extends Person {
    private List<String> expertiseAreas;
    private List<Treatment> treatments;

    public Physiotherapist(String id, String name, String address, String phone) {
        super(id, name, address, phone);
        this.expertiseAreas = new ArrayList<>();
        this.treatments = new ArrayList<>();
    }

    public void addExpertise(String expertise) {
        expertiseAreas.add(expertise);
    }

    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
    }

    public List<String> getExpertiseAreas() {
        return expertiseAreas;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    @Override
    public String toString() {
        return "Physio: " + name;
    }
}
