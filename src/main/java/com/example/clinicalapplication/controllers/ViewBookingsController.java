package com.example.clinicalapplication.controllers;

import com.example.clinicalapplication.models.Appointment;
import com.example.clinicalapplication.services.BookingSystem;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewBookingsController {

    private final BookingSystem bookingSystem = BookingSystem.getInstance();

    @FXML private TableView<Appointment> bookingTable;
    @FXML private TableColumn<Appointment, String> physioCol;
    @FXML private TableColumn<Appointment, String> patientCol;
    @FXML private TableColumn<Appointment, String> treatmentCol;
    @FXML private TableColumn<Appointment, String> timeCol;
    @FXML private TableColumn<Appointment, String> statusCol;
    @FXML private Button backButton;

    @FXML
    public void initialize() {
        physioCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() ->
                data.getValue().getPhysio().getName()));
        patientCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() ->
                data.getValue().getPatient().getName()));
        treatmentCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() ->
                data.getValue().getTreatment().getName()));
        timeCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() ->
                data.getValue().getTreatment().getTimeSlot()));
        statusCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() ->
                data.getValue().getStatus().toString()));

        bookingTable.setItems(FXCollections.observableArrayList(bookingSystem.getAllAppointments()));

        backButton.setOnAction(e -> goBackToMenu());
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
