package model;

import java.util.Objects;

public class Item {

    private static int nextItemId = 1;

    private int itemId;
    private int itemBarcode;
    private String itemName;
    private int itemQuantity;
    private String itemCategory;
    private String itemDescription;


    /* REQUIRES: itemName has a length greater than zero
     *           itemQuantity must be greater than zero
     *           itemCategory must have a length greater than zero
     *           itemBarcode must be a unique integer not already present in the inventory
     * EFFECTS: name of item is set to itemName;
     *          item id will be set to a positive integer not already assigned to another item;
     *          barcode of item will be set to itemBarcode;
     *          quantity of the item will be set to itemQuantity;
     */
    public Item(String itemName, int itemBarcode, int itemQuantity, String itemCategory, String itemDescription) {
        this.itemId = nextItemId++;
        this.itemName = itemName;
        this.itemBarcode = itemBarcode;
        this.itemQuantity = itemQuantity;
        this.itemCategory = itemCategory;
        this.itemDescription = itemDescription;
    }

    public String getItemName() {
        return this.itemName;
    }

    public int getItemId() {
        return this.itemId;
    }


    public int getItemBarcode() {
        return this.itemBarcode;
    }


    public int getItemQuantity() {
        return this.itemQuantity;
    }


    public String getItemCategory() {
        return this.itemCategory;
    }


    public String getItemDescription() {
        return this.itemDescription;
    }


    /* MODIFIES: this
     * REQUIRES: newName has a length greater than zero
     * EFFECTS: current name of an item is changed to newName
     */
    public void setItemName(String newName) {
        this.itemName = newName;
    }


    /* MODIFIES: this
     * REQUIRES: newQuantity must be greater than 0
     * EFFECTS: current quantity of the item is changed to newQuantity
     */
    public void setItemQuantity(int newQuantity) {
        this.itemQuantity = newQuantity;
    }


    /* MODIFIES: this
     * REQUIRES: toIncrease must be greater than 0
     * EFFECTS: toIncrease is added to the current item quantity
     */
    public void increaseItemQuantity(int toIncrease) {
        this.itemQuantity += toIncrease;
    }


    /* MODIFIES: this
     * REQUIRES: toDecrease must be greater than 0
     * EFFECTS: toDecrease is subtracted from the current item quantity
     */
    public void decreaseItemQuantity(int toDecrease) {
        this.itemQuantity -= toDecrease;
    }


    /*
     * EFFECTS: returns true if itemQuantity has enough to deduct the given value
     */
    public boolean isDeductible(int toDeduct) {
        return itemQuantity >= toDeduct;
    }


    /* MODIFIES: this
     * REQUIRES: newCategory has a length greater than zero
     * EFFECTS: current category of an item is changed to newCategory
     */
    public void setItemCategory(String newCategory) {
        this.itemCategory = newCategory;
    }


    /* MODIFIES: this
     * REQUIRES: newDescription has a length greater than zero
     * EFFECTS: current description of an item is changed to newDescription
     */
    public void setItemDescription(String newDescription) {
        this.itemDescription = newDescription;
    }


    /*
     * EFFECTS: returns a string representation of the item
     */
    public String toString() {
        return String.format("[ Name: %s | Barcode: %d | Quantity: %d | Category: %s ]", itemName,
                itemBarcode, itemQuantity, itemCategory);
    }


    /*
     * EFFECTS: checks to see if two given items are the same item based on the barcode of the items
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return itemBarcode == item.itemBarcode;
    }


    @Override
    public int hashCode() {
        return Objects.hash(itemBarcode);
    }

}
