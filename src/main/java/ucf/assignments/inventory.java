package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Scanner;

public class inventory {
    private static ObservableList<item> itemList = FXCollections.observableArrayList();

    public void addItem(item item) {
        itemList.add(item);
    }

    public static ObservableList<item> getItemList() {
        return itemList;
    }

    public String readFile(String filePath) {
        String output = "";
        if (filePath.contains(".html")) {
            output = readHTML(filePath);
        } else if (filePath.contains(".tsv")) {
            readTSV(filePath);
        } else if (filePath.contains(".json")) {
            readJSON(filePath);
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
                input.append(myLine + "\n");
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
                addItem(new item(name, serialNum, value));
            }
        }

        return "Import successful! Please click \"Go Back\" to return to main screen.";
    }

    public String exportHTML(String filePath) {
        StringBuilder content = new StringBuilder();
        content.append("<table border=1>\n\t<tr>\n\t\t<th>Name</th>\n\t\t<th>Serial Number</th>\n\t\t<th>Value</th>\n\t</tr>\n");
        for (int i = 0; i < itemList.size(); i++) {
            item temp = itemList.get(i);
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
        return "";
    }

    public String exportTSV(String filePath) {
        return "";
    }

    public String readJSON(String filePath) {
        return "";
    }

    public String exportJSON(String filePath) {
        return "";
    }
}
