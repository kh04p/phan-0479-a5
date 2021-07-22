package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Scanner;

public class inventory {
    private static ObservableList<item> itemList = FXCollections.observableArrayList();

    public static ObservableList<item> getItemList() {
        return itemList;
    }

    public String addItem(String name, String serialNum, double value) {
        for (item temp : itemList) {
            if (temp.getItemName().equals(name) && temp.getItemSerialNum().equals(serialNum) && temp.getItemValue() == value) {
                return "Item already exists.";
            }
        }
        item item = new item(name, serialNum, value);
        itemList.add(item);
        return "Item has been added.";
    }

    public String deleteItem(String name) {
        for (int i = 0; i < itemList.size(); i++) {
            item temp = itemList.get(i);
            if (temp.getItemName().equals(name)) {
                itemList.remove(i);
                return "Item has been removed";
            }
        }
        return "Item cannot be found.";
    }

    public String readFile(String filePath) {
        String output = "";
        if (filePath.contains(".html")) {
            output = readHTML(filePath);
        } else if (filePath.contains(".tsv")) {
            output = readTSV(filePath);
        } else if (filePath.contains(".json")) {
            output = readJSON(filePath);
        } else {
            output = "Unable to read file.";
        }

        return output;
    }

    public String exportFile(String filePath) {
        String output = "";
        if (filePath.contains(".html")) {
            output = exportHTML(filePath);
        } else if (filePath.contains(".tsv")) {
            output = exportTSV(filePath);
        } else if (filePath.contains(".json")) {
            output = exportJSON(filePath);
        } else {
            output = "Unable to export file.";
        }

        return output;
    }

    public String readHTML(String filePath) {
        File file = new File(filePath); //takes in file path to read
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            return "File cannot be imported. Please try again.";
        }

        StringBuilder input = new StringBuilder();

        while (in.hasNextLine()) {
            String myLine = in.nextLine();
            if (myLine.contains("table") || myLine.contains("<th>") || myLine.contains("<\th>")|| myLine.contains("<tr>")) {
                continue;
            }
            else {
                myLine = myLine.replaceAll("<td>","").replaceAll("</td>", "").trim();
                input.append(myLine).append("\n");
            }
        }

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

        return "Import successful! Please click \"Go Back\" to return to main screen.";
    }

    public String exportHTML(String filePath) {
        StringBuilder content = new StringBuilder();
        content.append("<table border=1>\n\t<tr>\n\t\t<th>Name</th>\n\t\t<th>Serial Number</th>\n\t\t<th>Value</th>\n\t</tr>\n");
        for (item temp : itemList) {
            content.append("\t<tr>\n");
            content.append("\t\t").append(String.format("<td>%s</td>%n", temp.getItemName()));
            content.append("\t\t").append(String.format("<td>%s</td>%n", temp.getItemSerialNum()));
            content.append("\t\t").append(String.format("<td>%s</td>%n", temp.getItemValue()));
            content.append("\t</tr>\n");
        }
        content.append("</table>");

        try {
            File file = new File(filePath);
            //creates bufferedWriter to write to html file and close afterwards.
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(String.valueOf(content));
            output.close();
            return String.format("Generated file at %s%n", filePath); //returns confirmation after creating file.
        } catch (IOException e ) {
            return "Unable to generate HTML file."; //returns confirmation if unable to create file.
        }
    }

    public String readTSV(String filePath) {
        File file = new File(filePath);
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            return "File cannot be imported. Please try again.";
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

        return "Import successful! Please click \"Go Back\" to return to main screen.";
    }

    public String exportTSV(String filePath) {
        StringBuilder content = new StringBuilder();
        content.append("Name\tSerial Number\tValue\t");
        for (item temp : itemList) {
            content.append(String.format("%s", temp.getItemName())).append("\t");
            content.append(String.format("%s", temp.getItemSerialNum())).append("\t");
            content.append(String.format("%.1f", temp.getItemValue())).append("\t");
        }

        try {
            File file = new File(filePath);
            //creates bufferedWriter to write to html file and close afterwards.
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(String.valueOf(content));
            output.close();
            return String.format("Generated file at %s%n", filePath); //returns confirmation after creating file.
        } catch (IOException e ) {
            return "Unable to generate TSV file."; //returns confirmation if unable to create file.
        }
    }

    public String readJSON(String filePath) {
        return "";
    }

    public String exportJSON(String filePath) {
        return "";
    }
}
