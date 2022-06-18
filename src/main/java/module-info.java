module com.checkers {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires junit;
    requires javafx.graphics;
    requires mapdb;

    exports com.checkers.models to junit;
    opens com.checkers.gui to javafx.graphics, javafx.fxml;
    opens com.checkers.controller to javafx.fxml;

}