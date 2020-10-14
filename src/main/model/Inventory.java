package model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Inventory {
    private Map<Integer, Item> items;

    /*
     * EFFECTS: creates an inventory of items through a linked hash map
     */
    public Inventory() {
        this.items = new LinkedHashMap<>();
    }


    /* MODIFIES: this
     * REQUIRES: nothing
     * EFFECTS: adds an item to the inventory and if item is already in the inventory based on matching barcodes then
     *          it increases the quantity of that item by the given quantity
     */
    public void addItem(Item item) {
        if (!items.containsKey(item.getItemBarcode())) {
            items.put(item.getItemBarcode(), item);
        } else {
            items.get(item.getItemBarcode()).increaseItemQuantity(item.getItemQuantity());
        }
    }


    public Map<Integer, Item> getAllItems() {
        return items;
    }

    /*
     * REQUIRES: barcode must be greater than zero
     * EFFECTS: returns the item in an inventory corresponding to the given barcode, and if no such item is found
     *          then returns null
     */
    public Item getItemByBarcode(int barcode) {
        return items.get(barcode);
    }

    /*
     * REQUIRES: name has a length greater than zero
     * EFFECTS: returns the item in an inventory corresponding to the given name disregarding case sensitivity, and
     *          if no such item is found then returns null
     */
    public Item getItemByName(String name) {
        for (Item item : items.values()) {
            if (item.getItemName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    /*
     * REQUIRES: barcode must be greater than 0
     * EFFECTS: returns true if item with specified barcode exists in the inventory, false otherwise
     */
    public boolean isInInventory(int barcode) {
        if (items.get(barcode) != null) {
            return true;
        }
        return false;
    }

    /*
     * EFFECTS: returns true if inventory is empty, false otherwise
     */
    public boolean isEmpty() {
        return items.size() == 0;
    }

}
