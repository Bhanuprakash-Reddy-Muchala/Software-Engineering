package com.example.clinicalapplication.models;

public class Appointment {
    public enum Status {
        BOOKED, CANCELLED, ATTENDED
    }

    private Physiotherapist physio;
    private Patient patient;
    private Treatment treatment;
    private Status status;

    public Appointment(Physiotherapist physio, Patient patient, Treatment treatment) {
        this.physio = physio;
        this.patient = patient;
        this.treatment = treatment;
        this.status = Status.BOOKED;
    }

    public Physiotherapist getPhysio() {
        return physio;
    }

    public Patient getPatient() {
        return patient;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return treatment + " with " + physio.getName() + " for " + patient.getName() + " [" + status + "]";
    }
}
