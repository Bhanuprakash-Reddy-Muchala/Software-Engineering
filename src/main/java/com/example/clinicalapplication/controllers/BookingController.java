package com.example.clinicalapplication.controllers;

import com.example.clinicalapplication.models.*;
import com.example.clinicalapplication.services.BookingSystem;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BookingController {

    private final BookingSystem bookingSystem = BookingSystem.getInstance();

    @FXML private ComboBox<String> expertiseComboBox;
    @FXML private ComboBox<String> physioComboBox;
    @FXML private ComboBox<String> treatmentComboBox;
    @FXML private ComboBox<String> patientComboBox;
    @FXML private Button bookAppointmentButton;
    @FXML private TextArea outputArea;
    @FXML private Button backButton;

    // Maps to resolve name back to object ID
    private final Map<String, String> patientNameToId = new HashMap<>();
    private final Map<String, String> physioNameToId = new HashMap<>();

    @FXML
    public void initialize() {
        // Dummy data load if needed
        preloadPhysiosIfEmpty();

        // Expertise list
        expertiseComboBox.setItems(FXCollections.observableArrayList("Physiotherapy", "Osteopathy", "Rehabilitation"));

        // Load patient list dynamically
        refreshPatients();

        // On expertise change, show relevant physios
        expertiseComboBox.setOnAction(event -> {
            var physios = bookingSystem.findPhysiotherapistsByExpertise(expertiseComboBox.getValue());
            physioNameToId.clear(); // reset map
            for (Physiotherapist p : physios) {
                physioNameToId.put(p.getName(), p.getId());
            }
            physioComboBox.setItems(FXCollections.observableArrayList(physioNameToId.keySet()));
        });

        // On physio change, show treatments
        physioComboBox.setOnAction(event -> {
            String physioName = physioComboBox.getValue();
            String physioId = physioNameToId.get(physioName);

            Physiotherapist physio = bookingSystem.getPhysiotherapists().stream()
                    .filter(p -> p.getId().equalsIgnoreCase(physioId))
                    .findFirst()
                    .orElse(null);

            if (physio != null) {
                treatmentComboBox.setItems(FXCollections.observableArrayList(
                        physio.getTreatments().stream().map(Treatment::getName).toList()
                ));
            }
        });

        // Book appointment
        bookAppointmentButton.setOnAction(event -> handleBookAppointment());

        // Back to menu
        backButton.setOnAction(event -> goBackToMenu());
    }

    private void refreshPatients() {
        patientNameToId.clear();
        for (Patient p : bookingSystem.getPatients()) {
            patientNameToId.put(p.getName(), p.getId());
        }
        patientComboBox.setItems(FXCollections.observableArrayList(patientNameToId.keySet()));
    }

    private void handleBookAppointment() {
        String physioName = physioComboBox.getValue();
        String patientName = patientComboBox.getValue();
        String treatmentName = treatmentComboBox.getValue();

        if (physioName == null || patientName == null || treatmentName == null) {
            outputArea.setText("Please select all required fields.");
            return;
        }

        String physioId = physioNameToId.get(physioName);
        String patientId = patientNameToId.get(patientName);

        Appointment appointment = bookingSystem.bookAppointment(physioId, patientId, treatmentName);

        if (appointment != null) {
            outputArea.setText("Appointment booked:\n" + appointment);
        } else {
            outputArea.setText("Booking failed. Please check values.");
        }
    }

    private void preloadPhysiosIfEmpty() {
        if (bookingSystem.getPhysiotherapists().isEmpty()) {
            Physiotherapist physio1 = new Physiotherapist("PH1", "Dr. Smith", "Clinic Road", "998877");
            physio1.addExpertise("Physiotherapy");
            physio1.addTreatment(new Treatment("Massage", "Physiotherapy", "Mon 10-11"));
            physio1.addTreatment(new Treatment("Acupuncture", "Physiotherapy", "Tue 12-1"));

            Physiotherapist physio2 = new Physiotherapist("PH2", "Dr. John", "Wellness Ave", "223344");
            physio2.addExpertise("Osteopathy");
            physio2.addTreatment(new Treatment("Mobilisation", "Osteopathy", "Wed 3-4"));

            bookingSystem.addPhysiotherapist(physio1);
            bookingSystem.addPhysiotherapist(physio2);
        }
    }

    private void goBackToMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clinicalapplication/main-menu.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
