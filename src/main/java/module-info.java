module com.checkers {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires junit;
    requires javafx.graphics;

    opens com.checkers.gui to javafx.graphics, javafx.fxml;
    opens com.checkers.controller to javafx.fxml;

}