package persistence;

import model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

// EFFECTS: method used in tests to check whether or not the Item read from the json object has correctly matching
//          specifications
public class JsonTest {
    protected void checkItem(String name, int barcode, int quantity, Item item) {
        assertEquals(name, item.getItemName());
        assertEquals(barcode, item.getItemBarcode());
        assertEquals(quantity, item.getItemQuantity());
    }
}
