package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class mainPageController implements Initializable {
    private final ObservableList<item> itemList = FXCollections.observableArrayList();

    @FXML private ChoiceBox<String> sortChoiceBox;
    @FXML private TableView<item> itemTable;
    @FXML private TextField searchField;
    @FXML private TableColumn<item, String> itemName;
    @FXML private TableColumn<item, String> itemSerialNum;
    @FXML private TableColumn<item, String> itemValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemSerialNum.setCellValueFactory(new PropertyValueFactory<>("itemSerialNum"));
        itemValue.setCellValueFactory(new PropertyValueFactory<>("itemValue"));

        item item1 = new item("egg", "egg0001", 2);
        item item2 = new item("car", "car0001", 3);
        item item3 = new item("meme", "420noscope", 10);
        item item4 = new item("milk", "milk50", 5);

        itemList.addAll(item1, item2, item3, item4);

        FilteredList<item> filteredItems = new FilteredList<>(itemList, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredItems.setPredicate(item -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String valueLowerCase = newValue.toLowerCase().trim();

                if (item.getItemName().toLowerCase().contains(valueLowerCase)) {
                    return true;
                } else if (item.getItemSerialNum().toLowerCase().contains(valueLowerCase)) {
                    return true;
                } else if (String.valueOf(item.getItemValue()).contains(valueLowerCase)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<item> sortedItems = new SortedList<>(filteredItems);
        itemTable.setItems(sortedItems);
    }

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
    void itemSearch(ActionEvent event) {

    }
}

