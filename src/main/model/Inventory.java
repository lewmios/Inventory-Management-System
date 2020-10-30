package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

// represents an Inventory which contains a list of Items
public class Inventory implements Writable {
    private Map<Integer, Item> items;
    private String inventoryName;


    /*
     * EFFECTS: creates an named inventory of items through a linked hash map
     */
    public Inventory(String name) {
        this.items = new LinkedHashMap<>();
        this.inventoryName = name;
    }


    /* MODIFIES: this
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


    /* MODIFIES: this
     * REQUIRES: quantity must be greater than zero
     * EFFECTS: decreases the quantity of an item in the inventory, and if the quantity of the item in the inventory
     *          reaches zero, then removes the item from the inventory; if item is found in inventory, and item
     *          quantity is greater or equals to quantity given, then returns true, else returns false
     */
    public boolean removeItem(Item item, int quantity) {
        int barcode = item.getItemBarcode();
        Item itemInInv = items.get(barcode);

        if (items.containsKey(barcode)) {

            if (itemInInv.getItemQuantity() > quantity) {
                itemInInv.decreaseItemQuantity(quantity);
                return true;

            } else if (itemInInv.getItemQuantity() == quantity) {
                itemInInv.decreaseItemQuantity(quantity);
                items.remove(item.getItemBarcode());
                return true;
            }
        }
        return false;
    }


    public String getInventoryName() {
        return inventoryName;
    }


    /* MODIFIES: this
     * REQUIRES: newName has a length greater than zero
     * EFFECTS: replaces current inventory name with newName
     */
    public void setInventoryName(String newName) {
        this.inventoryName = newName;
    }


    public Map<Integer, Item> getAllItems() {
        return items;
    }

    /* MODIFIES: this
     * EFFECTS: removes all items and clears the inventory
     */
    public void removeAllItems() {
        items.clear();
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


    @Override
    // EFFECTS: takes an inventory and returns it as a json object
    public JSONObject toJson() {
        JSONObject jsonInventory = new JSONObject();

        jsonInventory.put("Inventory Name", inventoryName);
        jsonInventory.put("Items", itemsToJson());

        return jsonInventory;
    }


    // EFFECTS: takes the items in the inventory and returns them as a json array
    public JSONArray itemsToJson() {
        JSONArray jsonItems = new JSONArray();

        Iterator<Item> it = items.values().iterator();

        while (it.hasNext()) {
            Item itemInInv = it.next();
            jsonItems.put(itemInInv.toJson());
        }

        return jsonItems;
    }
}
