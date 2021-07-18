package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class exportController {

    @FXML
    private AnchorPane filePathField;

    @FXML
    void exportFile(ActionEvent event) {

    }

    @FXML
    void goBack(ActionEvent event) {
        App App = new App();
        App.changeScene("/mainPage.fxml");
    }

}
