package com.example.clinicalapplication.controllers;

import com.example.clinicalapplication.models.Appointment;
import com.example.clinicalapplication.services.BookingSystem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportViewController {

    private final BookingSystem bookingSystem = BookingSystem.getInstance();

    @FXML private TextArea reportArea;
    @FXML private Button refreshButton;
    @FXML private Button backButton;

    @FXML
    public void initialize() {
        refreshButton.setOnAction(event -> displayReport());
        backButton.setOnAction(event -> goBackToMenu());
    }

    private void displayReport() {
        StringBuilder report = new StringBuilder("=== Clinic Appointments ===\n\n");

        for (Appointment a : bookingSystem.getAllAppointments()) {
            report.append(a.toString()).append("\n");
        }

        reportArea.setText(report.toString());
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
