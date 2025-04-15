module com.example.clinicalapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.clinicalapplication to javafx.fxml;
    opens com.example.clinicalapplication.controllers to javafx.fxml;
    opens com.example.clinicalapplication.models to javafx.base, javafx.fxml;

    exports com.example.clinicalapplication;
}