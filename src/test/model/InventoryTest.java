package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    private Inventory testInventory;
    private Item testItem1;
    private Item testItem2;
    private Item testItem3;

    @BeforeEach
    public void runBefore() {
        testItem1 = new Item("Nintendo Switch", 120, 1, "Electronics",
                "Nintendo Game Console");
        testItem2 = new Item("Playstation 5", 121, 1, "Electronics",
                "Sony Game Console");
        testItem3 = new Item("Xbox Series X", 122, 1, "Electronics",
                "Microsoft Game Console");

        testInventory = new Inventory("Test Inventory");
        testInventory.addItem(testItem1);

    }


    @Test
    public void testAddItem() {
        assertTrue(testInventory.isInInventory(testItem1.getItemBarcode()));
        assertFalse(testInventory.isInInventory(testItem2.getItemBarcode()));

        testInventory.addItem(testItem2);
        assertTrue(testInventory.isInInventory(testItem2.getItemBarcode()));
    }


    @Test
    public void testAddSameItem() {
        assertEquals(1, testInventory.getItemByBarcode(testItem1.getItemBarcode()).getItemQuantity());

        testInventory.addItem(testItem1);
        assertEquals(2, testInventory.getItemByBarcode(testItem1.getItemBarcode()).getItemQuantity());
    }


    @Test
    public void testReplaceInventory() {
        Inventory replacementInventory = new Inventory("Replacement Inventory");
        replacementInventory.addItem(testItem2);
        assertFalse(testInventory.getAllItems().containsKey(testItem2.getItemBarcode()));
        testInventory.replaceInventory(replacementInventory);
        assertTrue(testInventory.getAllItems().containsKey(testItem2.getItemBarcode()));
    }


    @Test
    public void testRemoveItem() {
        assertEquals(1, testInventory.getItemByBarcode(testItem1.getItemBarcode()).getItemQuantity());
        testInventory.addItem(testItem1);
        assertEquals(2, testInventory.getItemByBarcode(testItem1.getItemBarcode()).getItemQuantity());

        testInventory.removeItem(testItem1, 1);
        assertEquals(1, testInventory.getItemByBarcode(testItem1.getItemBarcode()).getItemQuantity());

        assertFalse(testInventory.removeItem(testItem1, 10));

        testInventory.removeItem(testItem1, 1);
        assertFalse(testInventory.isInInventory(testItem1.getItemBarcode()));

        assertFalse(testInventory.removeItem(testItem1, 1));
    }


    @Test
    public void testSetInventoryName() {
        assertEquals("Test Inventory", testInventory.getInventoryName());

        testInventory.setInventoryName("Compare Inventory");

        assertEquals("Compare Inventory", testInventory.getInventoryName());
    }


    @Test
    public void testGetAllItems() {
        Inventory compareInventory = new Inventory("Compare Inventory");

        assertFalse(testInventory.getAllItems() == compareInventory.getAllItems());
        compareInventory.addItem(testItem1);
        assertEquals(compareInventory.getAllItems(), testInventory.getAllItems());
    }


    @Test
    public void testRemoveAllItems() {
        assertFalse(testInventory.isEmpty());
        testInventory.removeAllItems();
        assertTrue(testInventory.isEmpty());
    }


    @Test
    public void testGetItemByBarcode() {
        assertEquals(testItem1, testInventory.getItemByBarcode(testItem1.getItemBarcode()));
        assertEquals(null, testInventory.getItemByBarcode(testItem2.getItemBarcode()));

        testInventory.addItem(testItem2);
        assertEquals(testItem2, testInventory.getItemByBarcode(testItem2.getItemBarcode()));
    }


    @Test
    public void testGetItemByName() {
        assertEquals(testItem1, testInventory.getItemByName(testItem1.getItemName()));
        assertEquals(null, testInventory.getItemByName(testItem2.getItemName()));

        testInventory.addItem(testItem3);
        assertEquals(testItem3, testInventory.getItemByName(testItem3.getItemName()));
    }


    @Test
    public void testIsEmpty() {
        Inventory compareInventory = new Inventory("Compare Inventory");
        assertFalse(testInventory.isEmpty());
        assertTrue(compareInventory.isEmpty());
    }


    @Test
    public void testToJson() {
        JSONObject testInv = new JSONObject();
        assertTrue(testInv.isEmpty());

        testInv = testInventory.toJson();
        assertFalse(testInv.isEmpty());
        assertTrue(testInv.get("Inventory Name").equals(testInventory.getInventoryName()));

    }

}
