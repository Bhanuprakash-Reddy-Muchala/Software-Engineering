package com.example.clinicalapplication.controllers;

import com.example.clinicalapplication.models.Patient;
import com.example.clinicalapplication.services.BookingSystem;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PatientFormController {

    private final BookingSystem bookingSystem = BookingSystem.getInstance();

    @FXML private Label formTitle;
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField phoneField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private Patient existingPatient;

    @FXML
    public void initialize() {
        saveButton.setOnAction(e -> handleSave());
        cancelButton.setOnAction(e -> ((Stage) cancelButton.getScene().getWindow()).close());
    }

    public void setPatient(Patient patient) {
        if (patient != null) {
            existingPatient = patient;
            idField.setText(patient.getId());
            nameField.setText(patient.getName());
            addressField.setText(patient.getAddress());
            phoneField.setText(patient.getPhone());
            formTitle.setText("Edit Patient");
            idField.setDisable(true);
        } else {
            formTitle.setText("Add Patient");
        }
    }

    private void handleSave() {
        String id = idField.getText();
        String name = nameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();

        if (id.isEmpty() || name.isEmpty()) {
            showAlert("Validation Error", "ID and Name are required.");
            return;
        }

        Patient patient = new Patient(id, name, address, phone);

        if (existingPatient == null) {
            bookingSystem.addPatient(patient);
        } else {
            existingPatient.setName(name);
            existingPatient.setAddress(address);
            existingPatient.setPhone(phone);
        }

        ((Stage) saveButton.getScene().getWindow()).close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
