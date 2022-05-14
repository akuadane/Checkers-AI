module com.company {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires junit;
    requires mapdb;

    opens com.company to javafx.fxml;
    exports com.company.gui;
}