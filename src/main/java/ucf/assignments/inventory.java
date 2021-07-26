package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Scanner;

public class inventory {
    //Program will start an Observable list containing items or an empty list if nothing is imported
    private static ObservableList<item> itemList = FXCollections.observableArrayList();

    //Getter for itemList for usage in controllers
    public static ObservableList<item> getItemList() {
        return itemList;
    }

    public String addItem(String name, String serialNum, double value) {
        //program will check for item list capacity first
        if (itemList.size() > 100) {
            return "List is at maximum capacity.";
        }

        //program will loop through list to check for duplicates
        for (item temp : itemList) {
            //program will return an error if a duplicate name/SR is found or if the item already exists
            if (temp.getItemName().equals(name) && temp.getItemSerialNum().equals(serialNum) && temp.getItemValue() == value) {
                return "Item already exists.";
            } else if (temp.getItemName().equals(name) || temp.getItemSerialNum().equals(serialNum)) {
                return "Duplicate found. Please check name and serial number.";
            }
        }

        //proceeds to add new item if requirements above are met
        item item = new item(name, serialNum, value);
        itemList.add(item);

        //returns confirmation
        return "Item has been added.";
    }

    public String deleteItem(String name) {
        //program will loop through to find item with matching name as there cannot be duplicate names
        for (int i = 0; i < itemList.size(); i++) {
            item temp = itemList.get(i);

            //removes item if matching name is found and return confirmation
            if (temp.getItemName().equals(name)) {
                itemList.remove(i);
                return "Item has been removed";
            }
        }

        //returns error if loop cannot find item
        return "Item cannot be found.";
    }

    public String readFile(String filePath) {
        //program will return confirmation or error after trying to read file
        //function will start by scanning user input to see which file type is being used
        String output = "";
        if (filePath.contains(".html")) {
            output = readHTML(filePath);
        } else if (filePath.contains(".txt")) {
            output = readTSV(filePath);
        } else if (filePath.contains(".json")) {
            output = readJSON(filePath);
        } else {
            output = "Unable to read file.";
        }

        return output;
    }

    public String exportFile(String filePath) {
        //program will return confirmation or error after trying to export file
        //function will start by scanning user input to see which file type is being used
        String output = "";
        if (filePath.contains(".html")) {
            output = exportHTML(filePath);
        } else if (filePath.contains(".txt")) {
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

        //program will start process by scanning through entire input file and remove unnecessary text
        StringBuilder input = new StringBuilder();

        while (in.hasNextLine()) {
            String myLine = in.nextLine();
            if (myLine.contains("table") || myLine.contains("<th>") || myLine.contains("<\th>")|| myLine.contains("<tr>")) {
                continue; //skip if line contains words above to clean up file for importing later
            }
            else {
                myLine = myLine.replaceAll("<td>","").replaceAll("</td>", "").trim();
                input.append(myLine).append("\n"); //replaces all unneeded text before and after each item in list
            }
        }

        Scanner in2 = new Scanner(String.valueOf(input));//scans through edited item list
        String line = in2.nextLine(); //scans through each line of text

        while (in2.hasNextLine()) {
            if (line.equals("</tr>")) { //skip to next item if condition is met
                line= in2.nextLine();
            } else {
                //imports text into each individual category inside a temporary array
                String[] temp = new String[3];
                int counter;

                for (counter = 0; counter < temp.length; counter++) {
                    temp[counter] = line;
                    line = in2.nextLine();
                }

                //if 3 fields have been scanned in, program will check requirements before adding them to itemList
                if (counter == 3) {
                    String name = temp[0];
                    if (name.length() > 256 || name.length() < 2) {
                        name = "Invalid Name.";
                    }

                    String serialNum = temp[1];
                    if (!serialNum.matches("[a-zA-Z0-9]{10,}")) {
                        serialNum = "Invalid Serial Number.";
                    }

                    double value;
                    try {
                        value = Double.parseDouble(temp[2]);
                    } catch (NumberFormatException e) {
                        value = 0;
                    }

                    itemList.add(new item(name, serialNum, value));
                }
                else { //if program could not find 3 fields to scan for item, item with error codes will be added
                    itemList.add(new item("Incomplete Item " + itemList.size() + 1, "XXXXXXXXXX", 0));
                }
            }
        }

        return "Import successful! Please click \"Go Back\" to return to main screen.";
    }

    public String exportHTML(String filePath) {
        //program will loop through itemList to put content into StringBuilder then write to file

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

        in.nextLine();
        while (in.hasNextLine()) {
            Scanner lineIn = new Scanner(in.nextLine());
            lineIn.useDelimiter("\t"); //needed for tsv files to separate item fields

            String[] temp = new String[3];
            int counter;
            for (counter = 0; counter < 3; counter++) {
                String word = lineIn.next();
                temp[counter] = word;
            }

            if (counter == 3) {
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
             else {
                System.out.println("Incomplete data found.");
            }
        }

        return "Import successful! Please click \"Go Back\" to return to main screen.";
    }

    public String exportTSV(String filePath) {
        //program will loop through itemList to put content into StringBuilder then write to file
        StringBuilder content = new StringBuilder();
        content.append("Name\tSerial Number\tValue\n");
        for (item temp : itemList) {
            content.append(String.format("%s", temp.getItemName())).append("\t");
            content.append(String.format("%s", temp.getItemSerialNum())).append("\t");
            content.append(String.format("%.1f", temp.getItemValue())).append("\t");
            content.append("\n");
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
