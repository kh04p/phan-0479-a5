package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class importController {

    @FXML
    private AnchorPane filePathField;

    @FXML
    void importFile(ActionEvent event) {

    }

    @FXML
    void goBack(ActionEvent event) {
        App App = new App();
        App.changeScene("/mainPage.fxml");
    }
}
