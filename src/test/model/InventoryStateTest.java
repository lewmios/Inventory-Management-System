package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryStateTest {

    private Inventory testInventory;
    private InventoryState testInventoryState;
    private Item testItem1;
    private Item testItem2;


    @BeforeEach
    public void runBefore() {
        testItem1 = new Item("Nintendo Switch", 120, 1, "Electronics",
                "Nintendo Game Console");
        testItem2 = new Item("Playstation 5", 121, 1, "Electronics",
                "Sony Game Console");

        testInventory = new Inventory("Test Inventory");
        testInventory.addItem(testItem1);
        testInventoryState = new InventoryState(testInventory);
    }


    @Test
    public void testConstructor() {
        assertEquals(testInventory, testInventoryState.getInventory());
    }


    @Test
    public void testRefreshListModel() {
        assertEquals(testInventory, testInventoryState.getInventory());
        testInventory.addItem(testItem2);
        testInventoryState.refreshListModel();
        assertEquals(testInventory, testInventoryState.getInventory());
    }

}
