module com.checkers {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires junit;
    requires javafx.graphics;
    requires testfx.core;
    requires com.google.common;
    opens com.checkers.gui to javafx.graphics, javafx.fxml, junit, testfx.core;
    opens com.checkers.controller to javafx.fxml, junit, testfx.core;
}