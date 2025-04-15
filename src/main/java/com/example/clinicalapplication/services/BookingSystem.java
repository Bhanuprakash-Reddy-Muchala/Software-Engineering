package com.example.clinicalapplication.services;

import com.example.clinicalapplication.models.*;

import java.util.*;
import java.util.stream.Collectors;

public class BookingSystem {
    private static BookingSystem instance;

    private final Map<String, Patient> patients = new HashMap<>();
    private final Map<String, Physiotherapist> physiotherapists = new HashMap<>();
    private final List<Appointment> appointments = new ArrayList<>();

    public BookingSystem() {
        // Private constructor to prevent external instantiation
    }

    public static BookingSystem getInstance() {
        if (instance == null) {
            instance = new BookingSystem();
        }
        return instance;
    }

    // Add a new patient
    public void addPatient(Patient patient) {
        patients.put(patient.getId(), patient);
    }

    // Remove an existing patient by ID
    public void removePatient(String patientId) {
        patients.remove(patientId);
    }

    // Add a new physiotherapist
    public void addPhysiotherapist(Physiotherapist physio) {
        physiotherapists.put(physio.getId(), physio);
    }

    // Lookup physiotherapists by area of expertise
    public List<Physiotherapist> findPhysiotherapistsByExpertise(String expertise) {
        List<Physiotherapist> results = new ArrayList<>();
        for (Physiotherapist p : physiotherapists.values()) {
            if (p.getExpertiseAreas().contains(expertise)) {
                results.add(p);
            }
        }
        return results;
    }

    // Find physiotherapist by name
    public Physiotherapist findPhysiotherapistByName(String name) {
        return physiotherapists.values().stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    // Book an appointment
    public Appointment bookAppointment(String physioId, String patientId, String treatmentName) {
        Physiotherapist physio = physiotherapists.get(physioId);
        Patient patient = patients.get(patientId);

        if (physio == null || patient == null) {
            System.out.println("Physio or patient not found!");
            return null;
        }

        for (Treatment t : physio.getTreatments()) {
            if (t.getName().equalsIgnoreCase(treatmentName)) {
                Appointment appointment = new Appointment(physio, patient, t);
                appointments.add(appointment);
                return appointment;
            }
        }

        System.out.println("Treatment not found!");
        return null;
    }

    // Cancel appointment
    public void cancelAppointment(Appointment appointment) {
        appointment.setStatus(Appointment.Status.CANCELLED);
    }

    // Mark appointment as attended
    public void markAsAttended(Appointment appointment) {
        appointment.setStatus(Appointment.Status.ATTENDED);
    }

    // List all appointments
    public List<Appointment> getAllAppointments() {
        return appointments;
    }

    // Generate report by physiotherapist
    public void generateReport() {
        System.out.println("==== Treatment Appointments ====");
        for (Appointment a : appointments) {
            System.out.println(a);
        }

        System.out.println("\n==== Physiotherapist by Attended Count ====");
        Map<String, Long> attendedCounts = appointments.stream()
                .filter(a -> a.getStatus() == Appointment.Status.ATTENDED)
                .collect(Collectors.groupingBy(
                        a -> a.getPhysio().getName(),
                        Collectors.counting()
                ));

        attendedCounts.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " attended"));
    }

    // Getters for UI access
    public Collection<Patient> getPatients() {
        return patients.values();
    }

    public Collection<Physiotherapist> getPhysiotherapists() {
        return physiotherapists.values();
    }
}
