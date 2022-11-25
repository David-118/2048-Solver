/**
 * This project is modular because of two helpful sources
 *  [2] describes an issue which prompted me to go for a modular approach for the project
 *  [3] I used the section Modular with Maven under the section JavaFX and IntelliJ to create this file
 */
module PROJECT {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.dataformat.csv;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;


    opens uk.ac.rhul.project to javafx.fxml;
    exports uk.ac.rhul.project;
    exports uk.ac.rhul.project.game;
    opens uk.ac.rhul.project.game to javafx.fxml;
    exports uk.ac.rhul.project.userInterface;
    opens uk.ac.rhul.project.userInterface to javafx.fxml;
    exports uk.ac.rhul.project.benchmark;
    opens uk.ac.rhul.project.benchmark to com.fasterxml.jackson.databind;
}