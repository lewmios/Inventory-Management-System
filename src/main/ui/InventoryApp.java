package ui;

import model.*;

import java.util.Scanner;

public class InventoryApp {
    private Inventory inventory;
    private Scanner scanner;


    // EFFECTS: runs the inventory application
    public InventoryApp() {
        this.inventory = new Inventory();
        this.scanner = new Scanner(System.in);

        displayMenu();
    }


    // EFFECTS: displays menu of options to user, and allows them to make a selection based on the choices shown
    public void displayMenu() {
        System.out.println("Main Menu");
        System.out.println("---------");
        System.out.print("[1] - Item Lookup by barcode\n"
                        + "[2] - Item lookup by name\n"
                        + "[3] - Add new item\n"
                        + "[4] - View all items\n"
                        + "[5] - Exit\n\n");
        System.out.print("Selection: ");
        int selection = scanner.nextInt();
        scanner.nextLine();

        handleSelection(selection);
    }


    // EFFECTS: displays a menu that allows users to input an item barcode they want to lookup in the inventory
    public void displayBarcodeLookup() {
        System.out.println("Enter Item Barcode");
        System.out.println("------------------");
        System.out.print("Search: ");
        int barcode = scanner.nextInt();
        scanner.nextLine();

        handleBarcodeSelection(barcode);
    }


    // EFFECTS: displays a menu that allows users to make another selection when an item with the inputted barcode is
    //          not part of the inventory
    public void displayBarcodeNotFound() {
        System.out.println("\nBarcode not found...");
        System.out.println("--------------------");
        System.out.println("[1] Search for another barcode\n"
                         + "[2] Return to main menu\n");
        System.out.print("Selection: ");
        int selection = scanner.nextInt();
        scanner.nextLine();

        handleBarcodeNotFound(selection);
    }


    // EFFECTS: displays a menu that allows users to input an item name they want to lookup in the inventory
    public void displayNameLookup() {
        System.out.println("Enter Item Name");
        System.out.println("----------------");
        System.out.print("Search: ");
        String name = scanner.nextLine();

        handleNameSelection(name);
    }


    // EFFECTS: displays a menu that allows users to make another selection when an item with the inputted name is not
    //          part of the inventory
    public void displayNameNotFound() {
        System.out.println("\nName not found...");
        System.out.println("--------------------");
        System.out.println("[1] Search for another name\n"
                         + "[2] Return to main menu\n");
        System.out.print("Selection: ");
        int selection = scanner.nextInt();
        scanner.nextLine();

        handleNameNotFound(selection);
    }


    /* MODIFIES: this
     * EFFECTS: creates the necessary menus that allow for users to create a new item and add it to the inventory
     */
    public void displayNewItemCreation() {
        System.out.println("Asterisks(*) denote required fields");
        System.out.print("Name*: ");
        String name = scanner.nextLine();
        System.out.print("Barcode*: ");
        int barcode = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Quantity*: ");
        int qty = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Category: ");
        String category = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();

        Item item = new Item(name, barcode, qty, category, description);
        inventory.addItem(item);

        System.out.println("\n" + item + "\n");
        System.out.println("New item added to inventory, press enter to return to menu...");
        scanner.nextLine();
        displayMenu();
    }

    /* MODIFIES: this
     * REQUIRES: barcode must be greater than zero
     * EFFECTS: searches for inputted barcode in the inventory; if an item with that barcode is found, it will output
     *          the item as a string and bring user back to displayMenu; if an item with that barcode is not found,
     *          then brings user to displayBarcodeNotFound menu
     */
    private void handleBarcodeSelection(int barcode) {
        Item item = inventory.getItemByBarcode(barcode);

        if (item != null) {
            System.out.println("\n\n" + item.toString());
            System.out.println("Press enter to return to main menu...");
            scanner.nextLine();
            displayMenu();
        } else {
            displayBarcodeNotFound();
        }
    }

    /* MODIFIES: this
     * REQUIRES: name must have a length greater than zero
     * EFFECTS: searches for inputted name in the inventory; if an item with that name is found, it will output
     *          the item as a string and bring user back to displayMenu; if an item with that name is not found,
     *          then brings user to displayNameNotFound menu
     */
    private void handleNameSelection(String name) {
        Item item = inventory.getItemByName(name);

        if (item != null) {
            System.out.println(("\n\n" + item.toString()));
            System.out.println("Press enter to return to main menu...");
            scanner.nextLine();
            displayMenu();
        } else {
            displayNameNotFound();
        }
    }

    /*
     * REQUIRES: selection be an integer
     * EFFECTS: handles the selections made by user when their inputted item barcode is not found
     */
    private void handleBarcodeNotFound(int selection) {
        switch (selection) {
            case 1:
                displayBarcodeLookup();
                break;
            case 2:
                displayMenu();
                break;
            default:
                displayBarcodeNotFound();
        }
    }

    /*
     * REQUIRES: selection be an integer
     * EFFECTS: handles the selections made by user when their inputted item name is not found
     */
    private void handleNameNotFound(int selection) {
        switch (selection) {
            case 1:
                displayNameLookup();
                break;
            case 2:
                displayMenu();
                break;
            default:
                displayNameNotFound();
        }
    }

    /*
     * REQUIRES: selection be an integer
     * EFFECTS: handles the selections made by user from the main menu of the program
     */
    private void handleSelection(int selection) {
        switch (selection) {
            case 1:
                displayBarcodeLookup();
                break;
            case 2:
                displayNameLookup();
                break;
            case 3:
                displayNewItemCreation();
                break;
            case 4:
                viewAllItems();
                break;
            case 5:
                System.exit(0);
            default:
                displayMenu();
        }
    }

    /*
     * EFFECTS: prints out all the items currently in the inventory, and if inventory is empty then prints out
     *          message that lets user know that inventory is empty
     */
    public void viewAllItems() {
        if (inventory.isEmpty()) {
            System.out.println("\nThere are no item currently in the inventory\n");
        } else {
            for (Item i : inventory.getAllItems().values()) {
                System.out.println(i);
            }
        }
        System.out.print("Press enter to return to main menu...");
        scanner.nextLine();
        displayMenu();
    }


}
