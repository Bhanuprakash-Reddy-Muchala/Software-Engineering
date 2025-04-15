package com.example.clinicalapplication.controllers;

import com.example.clinicalapplication.models.Patient;
import com.example.clinicalapplication.services.BookingSystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientListController {

    private final BookingSystem bookingSystem = BookingSystem.getInstance();

    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, String> idColumn;
    @FXML private TableColumn<Patient, String> nameColumn;
    @FXML private TableColumn<Patient, String> addressColumn;
    @FXML private TableColumn<Patient, String> phoneColumn;

    @FXML private Button backButton;
    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;
    @FXML private Button refreshButton;

    private ObservableList<Patient> patientList;

    @FXML
    public void initialize() {
        // Setup column value factories
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // ✅ Apply black text style via cell factories
        setBlackTextCellFactory(idColumn);
        setBlackTextCellFactory(nameColumn);
        setBlackTextCellFactory(addressColumn);
        setBlackTextCellFactory(phoneColumn);

        // Load patients into table
        patientList = FXCollections.observableArrayList(bookingSystem.getPatients());
        patientTable.setItems(patientList);

        // Button handlers
        addButton.setOnAction(e -> openPatientForm(null));

        editButton.setOnAction(e -> {
            Patient selected = patientTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                openPatientForm(selected);
            } else {
                showAlert("Please select a patient to edit.");
            }
        });

        deleteButton.setOnAction(e -> {
            Patient selected = patientTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                bookingSystem.removePatient(selected.getId());
                patientList.remove(selected);
            } else {
                showAlert("Please select a patient to delete.");
            }
        });

        refreshButton.setOnAction(e -> {
            patientList.setAll(bookingSystem.getPatients());
        });

        backButton.setOnAction(event -> goBackToMenu());
    }

    private void openPatientForm(Patient patientToEdit) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clinicalapplication/patient-form.fxml"));
            Scene scene = new Scene(loader.load());

            PatientFormController controller = loader.getController();
            controller.setPatient(patientToEdit);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(patientToEdit == null ? "Add Patient" : "Edit Patient");
            stage.setScene(scene);
            stage.showAndWait();

            // Refresh list after modal closes
            patientList.setAll(bookingSystem.getPatients());

        } catch (IOException e) {
            e.printStackTrace();
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

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notice");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // ✅ Cell factory to ensure text color is black
    private void setBlackTextCellFactory(TableColumn<Patient, String> column) {
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setStyle("-fx-text-fill: black;");
            }
        });
    }
}
