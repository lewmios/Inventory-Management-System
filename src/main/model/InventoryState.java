package model;

import model.Inventory;
import model.Item;

import javax.swing.*;

// represents an DefaultListModel filled with items
public class InventoryState extends DefaultListModel<Item> {

    private Inventory inventory;


    // EFFECTS: converts the given inventory into a DefaultListModel<Inventory>
    public InventoryState(Inventory inventory) {
        this.inventory = inventory;

        for (Item item : inventory.getAllItems().values()) {
            this.addElement(item);
        }
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    /* MODIFIES: this
     * EFFECTS: removes all items from this and then adds all them all back (makes sure all items added to inventory are
     *          also added to this)
     */
    public void refreshListModel() {
        this.removeAllElements();
        for (Item item : inventory.getAllItems().values()) {
            this.addElement(item);
        }
    }


    /* MODIFIES: this
     * EFFECTS: checks to see if given item is in this based on its barcode and returns its index
     *//*
    public int getItemIndex(Item item) {
        if (containsItem(item)) {
            for (int i = 0; i < this.getSize(); i++) {
                if (item.getItemBarcode() == this.get(i).getItemBarcode()) {
                    return i;
                }
            }
        }
        return -1;
    }*/


    /*// EFFECTS: returns true if the given item is in this based on its barcode, false otherwise
    public Boolean containsItem(Item item) {
        for (int i = 0; i < this.getSize(); i++) {
            if (item.getItemBarcode() == this.get(i).getItemBarcode()) {
                return true;
            }
        }
        return false;
    }*/



}
