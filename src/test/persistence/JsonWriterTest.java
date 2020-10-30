package persistence;

import model.Inventory;
import model.Item;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

// testing methods modeled after tests shown in JsonSerializationDemo
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {

            JsonWriter printer = new JsonWriter("./data/fileNameNotPossible\0.json");

            printer.open();
            fail("IOException was not thrown");

        } catch (IOException e) {
            // expected outcome
        }
    }

    @Test
    void testWriterEmptyInventory() {
        try {
            Inventory inv = new Inventory();
            JsonWriter printer = new JsonWriter("./data/testWriterEmptyInventory.json");

            printer.open();
            printer.write(inv);
            printer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyInventory.json");
            inv = reader.read();

            assertTrue(inv.isEmpty());

        } catch (IOException e) {
            fail("IOException was not supposed to be thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Inventory inv = new Inventory();
            Item testItem1 = new Item("Nintendo Switch", 120, 1, "Electronics",
                    "Nintendo Game Console");
            Item testItem2 = new Item("Playstation 5", 121, 1, "Electronics",
                    "Sony Game Console");

            inv.addItem(testItem1);
            inv.addItem(testItem2);
            JsonWriter printer = new JsonWriter("./data/testWriterInventory.json");

            printer.open();
            printer.write(inv);
            printer.close();

            JsonReader reader = new JsonReader("./data/testWriterInventory.json");
            inv = reader.read();

            Map<Integer, Item> items = inv.getAllItems();
            assertEquals(2, items.size());

            checkItem("Nintendo Switch", 120, items.get(120));
            checkItem("Playstation 5", 121, items.get(121));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
