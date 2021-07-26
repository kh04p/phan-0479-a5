/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Khoa Phan
 */
import org.junit.jupiter.api.Test;
import ucf.assignments.inventory;

import static org.junit.jupiter.api.Assertions.*;

public class inventoryTest {
    @Test
    void importHTMLTest() {
        inventory inventory = new inventory();
        String filePath1 = "testTable.html";
        String filePath2 = "nonexistent.html";

        String actualOutput1 = inventory.readFile(filePath1);
        String expectedOutput1 = "Import successful! Please click \"Go Back\" to return to main screen.";

        String actualOutput2 = inventory.readFile(filePath2);
        String expectedOutput2 = "File cannot be imported. Please try again.";

        assertEquals(expectedOutput1, actualOutput1);
        assertEquals(expectedOutput2, actualOutput2);
    }

    @Test
    void exportHTMLTest() {
        inventory inventory = new inventory();
        String filePath1 = "exportedTable.html";
        String filePath2 = "random.random";

        String actualOutput1 = inventory.exportFile(filePath1);
        String expectedOutput1 = String.format("Generated file at %s%n", filePath1);

        String actualOutput2 = inventory.exportFile(filePath2);
        String expectedOutput2 = "Unable to export file.";

        assertEquals(expectedOutput1, actualOutput1);
        assertEquals(expectedOutput2, actualOutput2);
    }

    @Test
    void importTSVTest() {
        inventory inventory = new inventory();
        String filePath1 = "testTable.txt";
        String filePath2 = "nonexistent.txt";

        String actualOutput1 = inventory.readFile(filePath1);
        String expectedOutput1 = "Import successful! Please click \"Go Back\" to return to main screen.";

        String actualOutput2 = inventory.readFile(filePath2);
        String expectedOutput2 = "File cannot be imported. Please try again.";

        assertEquals(expectedOutput1, actualOutput1);
        assertEquals(expectedOutput2, actualOutput2);
    }

    @Test
    void exportTSVTest() {
        inventory inventory = new inventory();
        String filePath1 = "exportedTable.txt";
        String filePath2 = "random.random";

        String actualOutput1 = inventory.exportFile(filePath1);
        String expectedOutput1 = String.format("Generated file at %s%n", filePath1);

        String actualOutput2 = inventory.exportFile(filePath2);
        String expectedOutput2 = "Unable to export file.";

        assertEquals(expectedOutput1, actualOutput1);
        assertEquals(expectedOutput2, actualOutput2);
    }

    @Test
    void addItemTest() {
        inventory inventory = new inventory();
        inventory.addItem("egg", "3gg", 10.25);

        String expectedOutput1 = "Item has been added.";
        String expectedOutput2 = "Item already exists.";
        String expectedOutput3 = "Duplicate found. Please check name and serial number.";

        String actualOutput1 = inventory.addItem("milk", "m1lk", 5.98);
        String actualOutput2 = inventory.addItem("egg", "3gg", 10.25);
        String actualOutput3 = inventory.addItem("egg", "3gg0", 64);

        assertEquals(expectedOutput1, actualOutput1);
        assertEquals(expectedOutput2, actualOutput2);
        assertEquals(expectedOutput3, actualOutput3);
    }

    @Test
    void deleteItemTest() {
        inventory inventory = new inventory();
        inventory.addItem("egg", "3gg", 10.25);
        inventory.addItem("Acura NSX", "NSX34002XF", 4.99);

        String expectedOutput1 = "Item has been removed";
        String expectedOutput2 = "Item cannot be found.";

        String actualOutput1 = inventory.deleteItem("Acura NSX");
        String actualOutput2 = inventory.deleteItem("Audi R8");

        assertEquals(expectedOutput1, actualOutput1);
        assertEquals(expectedOutput2, actualOutput2);
    }
}
