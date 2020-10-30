package persistence;

import model.Inventory;
import model.Item;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

// testing methods modeled after tests shown in JsonSerializationDemo
public class JsonReaderTest extends JsonTest {


    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/doesNotExist.json");
        try {
            Inventory inv = reader.read();
            fail("IOException was not thrown");

        } catch (IOException e) {
            // expected outcome
        }
    }


    @Test
    void testReaderEmptyInventory() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyInventory.json");
        try {
            Inventory inv = reader.read();
            assertTrue(inv.isEmpty());
            assertEquals("Test Inventory", inv.getInventoryName());

        } catch (IOException e) {
            fail("unable to read from an existing file");
        }
    }


    @Test
    void testReaderInventory() {
        JsonReader reader = new JsonReader("./data/testReaderInventory.json");
        try {
            Inventory inv = reader.read();

            Map<Integer, Item> items = inv.getAllItems();
            assertEquals(3, items.size());
            assertEquals("Consoles", inv.getInventoryName());

            checkItem("Nintendo", 123, 2, items.get(123));
            checkItem("Playstation", 321, 4, items.get(321));
            checkItem("Xbox", 231, 6, items.get(231));

        } catch (IOException e) {
            fail("unable to read from an existing file");
        }
    }
}
