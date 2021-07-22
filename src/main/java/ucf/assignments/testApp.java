package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class testApp {
    public static void main(String[] args) throws FileNotFoundException {
        ObservableList<item> itemList = FXCollections.observableArrayList();
        String filePath = "C:\\Users\\khoa1\\Desktop\\testTable.tsv";
        File file = new File(filePath); //takes in file path to read
        Scanner in = null;
        StringBuilder input = new StringBuilder();

        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be imported. Please try again.");;
        }

        in.useDelimiter("\t");
        while (in.hasNext()) {
            String word = in.next().toLowerCase();
            if (word.equals("name") || word.equals("serial number") || word.equals("value")) {
                continue;
            } else {
                String[] temp = new String[3];
                for (int i = 0; i < temp.length; i++) {
                    if (word.isEmpty() || word.isBlank()) {
                        temp[i] = "";
                    } else {
                        temp[i] = word;
                        if (i < temp.length - 1) {
                            word = in.next();
                        }
                    }
                }
                String name = temp[0];
                String serialNum = temp[1];
                double value;
                try {
                    value = Double.parseDouble(temp[2]);
                } catch (NumberFormatException e) {
                    value = 0;
                }
                itemList.add(new item(name, serialNum, value));
            }
        }

        System.out.println(itemList);
    }
}
