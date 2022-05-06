module com.company {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires junit;

    opens com.company to javafx.fxml;
    exports com.company.gui;
}