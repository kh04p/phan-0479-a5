/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Khoa Phan
 */
package ucf.assignments;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
        //program will get itemList from inventory.java if there is an existing/imported list
        ObservableList<item> itemList = inventory.getItemList();
        itemTable.setEditable(true); //sets table as editable

        itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemName.setCellFactory(TextFieldTableCell.forTableColumn());
        itemName.setOnEditCommit(event -> {
            item item = event.getRowValue();
            item.setItemName(event.getNewValue());
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

        //filteredList needed for search function
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

        //wraps filteredList in sortedList to allow users to sort items based on categories.
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
        //program will create new item with default values for user to edit afterwards
        inventory inventory = new inventory();

        String name = String.format("Item #%d", ucf.assignments.inventory.getItemList().size() + 1);
        String serialNum = "Item_Serial_Number";
        double value = 0;

        String output = inventory.addItem(name, serialNum, value);
        searchField.setText(output);
        initialize();
    }

    @FXML
    void deleteItem(ActionEvent event) {
        //program will get name of currently selected item to delete
        inventory inventory = new inventory();
        item item = itemTable.getSelectionModel().getSelectedItem();

        String name = item.getItemName();

        String output = inventory.deleteItem(name);
        searchField.setText(output);
        initialize();
    }
}

