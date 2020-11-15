package model;

import model.Inventory;
import model.Item;

import javax.swing.*;

public class InventoryState extends DefaultListModel<Item> {

    private Inventory inventory;

    public InventoryState(Inventory inventory) {
        this.inventory = inventory;

        for (Item item : inventory.getAllItems().values()) {
            this.addElement(item);
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public DefaultListModel<Item> getListModel() {
        return this;
    }

    public void removeItem(Item item) {
        this.removeElement(item);
    }

    public void refreshListModel() {
        this.removeAllElements();
        for (Item item : inventory.getAllItems().values()) {
            this.addElement(item);
        }
    }

    public int getItemIndex(Item item) {
        if (containsItem(item)) {
            for (int i = 0; i < this.getSize(); i++) {
                if (item.getItemBarcode() == this.get(i).getItemBarcode()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void addItem(Item item) {
        if (!this.containsItem(item)) {
            this.addElement(item);
        } else if (this.containsItem(item)) {
            this.get(getItemIndex(item)).increaseItemQuantity(item.getItemQuantity());
        }
    }

    public Boolean containsItem(Item item) {
        for (int i = 0; i < this.getSize(); i++) {
            if (item.getItemBarcode() == this.get(i).getItemBarcode()) {
                return true;
            }
        }
        return false;
    }



}
