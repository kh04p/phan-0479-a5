/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Khoa Phan
 */
package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class exportController {

    @FXML
    private TextField filePathField;

    @FXML
    void exportFile(ActionEvent event) {
        //program will get file path from user before trying to export and display confirmation/error message
        inventory inventory = new inventory();
        String filePath = filePathField.getText().trim();
        String output = inventory.exportFile(filePath);
        filePathField.setText(output);
    }

    @FXML
    void goBack(ActionEvent event) {
        App App = new App();
        App.changeScene("/mainPage.fxml");
    }

}
