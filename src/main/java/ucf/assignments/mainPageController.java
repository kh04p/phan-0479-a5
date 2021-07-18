package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class mainPageController {

    @FXML
    private ListView<String> itemList;

    @FXML
    private TextField searchField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField serialField;

    @FXML
    private TextField valueField;

    @FXML
    private ChoiceBox<?> sortChoiceBox;

    @FXML
    void importFile(ActionEvent event) {
        App App = new App();
        App.changeScene("/importPage.fxml");
    }

    @FXML
    void exportFile(ActionEvent event) {
        App App = new App();
        App.changeScene("/exportPage.fxml");
    }

    @FXML
    void getHelp(ActionEvent event) {

    }

    @FXML
    void saveItem(ActionEvent event) {

    }

    @FXML
    void searchItem(ActionEvent event) {

    }

    @FXML
    void selectItem(ActionEvent event) {

    }

}
