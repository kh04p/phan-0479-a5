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
        String filePath1 = "testTable.tsv";
        String filePath2 = "nonexistent.tsv";

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
        String filePath1 = "exportedTable.tsv";
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

    }

    @Test
    void deleteItemTest() {

    }
}
