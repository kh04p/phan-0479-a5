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
        String filePath = "C:\\Users\\khoa1\\Desktop\\testTable.html";
        File file = new File(filePath); //takes in file path to read
        Scanner in = new Scanner(file);
        StringBuilder input = new StringBuilder();

        while (in.hasNextLine()) {
            String myLine = in.nextLine();
            if (myLine.contains("table") || myLine.contains("<th>") || myLine.contains("<\th>")|| myLine.contains("<tr>")) {
                continue;
            }
            else {
                myLine = myLine.replaceAll("<td>","").replaceAll("</td>", "").trim();
                input.append(myLine + "\n");
            }
        }

        System.out.println(input);

        Scanner in2 = new Scanner(String.valueOf(input));
        String line = in2.nextLine();

        while (in2.hasNextLine()) {
            if (line.equals("</tr>")) {
                line= in2.nextLine();
            } else {
                String[] temp = new String[3];
                for (int i = 0; i < temp.length; i++) {
                    if (line.equals("</tr>")) {
                        temp[i] = "";
                    } else {
                        temp[i] = line;
                        line = in2.nextLine();
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
