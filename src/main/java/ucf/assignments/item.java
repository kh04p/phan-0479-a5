package ucf.assignments;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class item {
    private SimpleStringProperty itemName = new SimpleStringProperty("");
    private SimpleStringProperty itemSerialNum = new SimpleStringProperty("");
    private SimpleDoubleProperty itemValue = new SimpleDoubleProperty(0);

    item(String itemName, String itemSerialNum, double itemValue) {
        this.itemName = new SimpleStringProperty(itemName);
        this.itemSerialNum = new SimpleStringProperty(itemSerialNum);
        this.itemValue = new SimpleDoubleProperty(itemValue);
    }

    public String getItemName() {
        return itemName.get();
    }

    public SimpleStringProperty itemNameProperty() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public String getItemSerialNum() {
        return itemSerialNum.get();
    }

    public SimpleStringProperty itemSerialNumProperty() {
        return itemSerialNum;
    }

    public void setItemSerialNum(String itemSerialNum) {
        this.itemSerialNum.set(itemSerialNum);
    }

    public double getItemValue() {
        return itemValue.get();
    }

    public SimpleDoubleProperty itemValueProperty() {
        return itemValue;
    }

    public void setItemValue(double itemValue) {
        this.itemValue.set(itemValue);
    }
}
