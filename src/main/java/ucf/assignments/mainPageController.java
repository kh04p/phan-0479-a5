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

public class mainPageController {

    @FXML private TableView<item> itemTable;
    @FXML private TextField searchField;
    @FXML private TableColumn<item, String> itemName;
    @FXML private TableColumn<item, String> itemSerialNum;
    @FXML private TableColumn<item, Double> itemValue;

    @FXML
    public void initialize() {
        ObservableList<item> itemList = inventory.getItemList();
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
        itemSerialNum.setOnEditCommit(event -> {
            item item = event.getRowValue();
            item.setItemSerialNum(event.getNewValue());
        });

        itemValue.setCellValueFactory(new PropertyValueFactory<>("itemValue"));
        itemValue.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        itemValue.setOnEditCommit(event -> {
            item item = event.getRowValue();
            item.setItemValue(event.getNewValue());
        });

        FilteredList<item> filteredItems = new FilteredList<>(itemList, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filteredItems.setPredicate(item -> {
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
        }));

        SortedList<item> sortedItems = new SortedList<>(filteredItems);
        sortedItems.comparatorProperty().bind(itemTable.comparatorProperty());
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
    void newItem(ActionEvent event) {
        inventory inventory = new inventory();

        String name = String.format("Item #%d", inventory.getItemList().size() + 1);
        String serialNum = "Item_Serial_Number";
        double value = 0;

        String output = inventory.addItem(name, serialNum, value);
        System.out.println(output);
        initialize();
    }

    @FXML
    void deleteItem(ActionEvent event) {
        inventory inventory = new inventory();
        item item = itemTable.getSelectionModel().getSelectedItem();

        String name = item.getItemName();

        String output = inventory.deleteItem(name);
        System.out.println(output);
        initialize();
    }
}

