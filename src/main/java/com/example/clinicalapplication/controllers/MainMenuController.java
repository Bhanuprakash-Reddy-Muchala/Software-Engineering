package com.example.clinicalapplication.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private Button bookingButton;

    @FXML
    private Button patientsButton;

    @FXML
    private Button reportButton;

    @FXML private Button bookingsButton;

    @FXML
    public void initialize() {
        bookingButton.setOnAction(e -> loadScene("booking-view.fxml"));
        patientsButton.setOnAction(e -> loadScene("patient-list.fxml"));
        reportButton.setOnAction(e -> loadScene("report-view.fxml"));
        bookingsButton.setOnAction(e -> loadScene("view-bookings.fxml"));
    }

    private void loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clinicalapplication/" + fxmlFile));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) bookingButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
