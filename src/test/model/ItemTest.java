package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    private Item testItem;

    @BeforeEach
    void runBefore() {
        testItem = new Item("Nintendo Switch", 123454321, 1, "Electronics",
                "Test item");
    }


    @Test
    void testConstructor() {
        assertTrue(testItem.getItemId() > 0);
        assertEquals("Nintendo Switch", testItem.getItemName());
        assertEquals(123454321, testItem.getItemBarcode());
        assertEquals(1, testItem.getItemQuantity());
        assertEquals("Electronics", testItem.getItemCategory());
        assertEquals("Test item", testItem.getItemDescription());
    }


    @Test
    void testSetName() {
        assertEquals("Nintendo Switch", testItem.getItemName());
        testItem.setItemName("Playstation 5");
        assertEquals("Playstation 5", testItem.getItemName());

    }


    @Test
    void testSetQuantity() {
        assertEquals(1, testItem.getItemQuantity());
        testItem.setItemQuantity(20);
        assertEquals(20, testItem.getItemQuantity());
    }


    @Test
    void testIncreaseQuantity() {
        assertEquals(1, testItem.getItemQuantity());
        testItem.increaseItemQuantity(9);
        assertEquals(10, testItem.getItemQuantity());
    }


    @Test
    void testSetCategory() {
        assertEquals("Electronics", testItem.getItemCategory());
        testItem.setItemCategory("Technology");
        assertEquals("Technology", testItem.getItemCategory());
    }


    @Test
    void testSetDescription() {
        assertEquals("Test item", testItem.getItemDescription());
        testItem.setItemDescription("Game console made by nintendo");
        assertEquals("Game console made by nintendo", testItem.getItemDescription());
    }


    @Test
    void testToString() {
        assertTrue(testItem.toString().contains("[ Name: Nintendo Switch | Barcode: 123454321 | Quantity: 1 |" +
                " Category: Electronics ]"));
    }


    @Test
    void testItemEquals() {
        Item item1 = new Item("Test Item 1", 112233, 1, "Tester1", "");
        Item item2 = new Item("Test Item 2", 112233, 1, "Tester2", "");
        Item item3 = new Item("Test Item 3", 332211, 1, "Tester3", "");
        String testS = "testS";

        assertTrue(item1.equals(item1));
        assertTrue(item1.equals(item2));
        assertFalse(item2.equals(item3));
        assertFalse(item1.equals(null));
        assertFalse(item1.equals(testS));

    }


    @Test
    void testHashCode() {
        Item item1 = new Item("Test Item 1", 112233, 1, "Tester1", "");
        Item item2 = new Item("Test Item 2", 112233, 1, "Tester2", "");
        Item item3 = new Item("Test Item 3", 332211, 1, "Tester3", "");

        assertEquals(item1.hashCode(), item2.hashCode());
        assertFalse(item2.hashCode() == item3.hashCode());
    }

}