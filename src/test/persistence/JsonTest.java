package persistence;

import model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkItem(String name, int barcode, Item item) {
        assertEquals(name, item.getItemName());
        assertEquals(barcode, item.getItemBarcode());
    }
}
