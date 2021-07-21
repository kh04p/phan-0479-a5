package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class exportController {

    @FXML
    private TextField filePathField;

    @FXML
    void exportFile(ActionEvent event) {
        inventory inventory = new inventory();
        String filePath = filePathField.getText().trim();
        String output = inventory.exportHTML(filePath);
        filePathField.setText(output);
    }

    @FXML
    void goBack(ActionEvent event) {
        App App = new App();
        App.changeScene("/mainPage.fxml");
    }

}
