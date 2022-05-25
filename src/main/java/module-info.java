module com.checkers {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires junit;
    requires javafx.graphics;

    opens com.checkers to javafx.fxml;
    exports com.checkers.gui;
}