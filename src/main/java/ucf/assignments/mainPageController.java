package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class mainPageController implements Initializable {
    private ObservableList<item> itemList = FXCollections.observableArrayList();

    @FXML private ChoiceBox<String> sortChoiceBox;
    @FXML private TableView<item> itemTable;
    @FXML private TextField searchField;
    @FXML private TableColumn<item, String> itemName;
    @FXML private TableColumn<item, String> itemSerialNum;
    @FXML private TableColumn<item, Double> itemValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inventory inventory = new inventory();
        itemTable.setEditable(true);

        itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemName.setCellFactory(TextFieldTableCell.forTableColumn());
        itemName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<item, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<item, String> event) {
                item item = event.getRowValue();
                item.setItemName(event.getNewValue());
            }
        });

        itemSerialNum.setCellValueFactory(new PropertyValueFactory<>("itemSerialNum"));
        itemSerialNum.setCellFactory(TextFieldTableCell.forTableColumn());
        itemSerialNum.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<item, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<item, String> event) {
                item item = event.getRowValue();
                item.setItemSerialNum(event.getNewValue());
            }
        });

        itemValue.setCellValueFactory(new PropertyValueFactory<>("itemValue"));
        itemValue.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        itemValue.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<item, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<item, Double> event) {
                item item = event.getRowValue();
                item.setItemValue(event.getNewValue());
            }
        });

        inventory.addItem(new item("egg", "egg0001", 2));
        inventory.addItem(new item("car", "car0001", 3));
        inventory.addItem(new item("meme", "memey420", 100));
        inventory.addItem(new item("bruh", "bruv0k", 5));

        itemList = inventory.getItemList();

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

