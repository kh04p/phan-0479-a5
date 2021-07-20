package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class inventory {
    private ObservableList<item> itemList = FXCollections.observableArrayList();

    public void addItem(item item) {
        itemList.add(item);
    }

    public ObservableList<item> getItemList() {
        return itemList;
    }
}
