package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Scanner;

// creates a console based application which allows users to interact with Inventories filled with Items
public class InventoryApp {
    private Inventory inventory;
    private Scanner scanner;

    private static final String TARGET_JSON_FILE = "./data/inventory.json";
    private JsonWriter printer;
    private JsonReader reader;


    // EFFECTS: runs the inventory application
    public InventoryApp() throws FileNotFoundException {
        this.inventory = new Inventory("New Inventory");
        this.scanner = new Scanner(System.in);

        this.printer = new JsonWriter(TARGET_JSON_FILE);
        this.reader = new JsonReader(TARGET_JSON_FILE);

        displayLoadPreviousInventory();
    }


    // EFFECTS: displays menu of options to user, and allows them to make a selection based on the choices shown
    public void displayMenu() {
        System.out.println("\nMain Menu");
        System.out.println("---------");
        System.out.println("[ Current Inventory: " + inventory.getInventoryName() + " ]");
        System.out.print("[1] - Item Lookup by barcode\n"
                        + "[2] - Item lookup by name\n"
                        + "[3] - Add new item\n"
                        + "[4] - View all items\n"
                        + "[5] - Change inventory name\n"
                        + "[6] - Load inventory\n"
                        + "[7] - Save inventory\n"
                        + "[8] - Exit\n\n");
        System.out.print("Selection: ");
        int selection = scanner.nextInt();
        scanner.nextLine();

        if (selection < 5) {
            handleSelection(selection);
        } else {
            handleSelection2(selection);
        }
    }


    // EFFECTS: displays menu which allows users to decide whether or not to load previously saved inventory
    public void displayLoadPreviousInventory() {
        System.out.println("Would you like to load previous inventory?");
        System.out.println("------------------------------------------");
        System.out.print("[1] - Yes\n"
                       + "[2] - No\n\n");
        System.out.print("Selection: ");
        int selection = scanner.nextInt();
        scanner.nextLine();

        handleLoadPreviousInventory(selection);
    }


    // EFFECTS: displays menu which allows users to change the name of their current inventory
    public void displayChangeInventoryName() {
        System.out.println("\nPlease Enter a New Inventory Name");
        System.out.println("----------------------------------");
        System.out.print("Name: ");
        String newName = scanner.nextLine();

        inventory.setInventoryName(newName);

        System.out.println("\nSuccess: Inventory name changed to: " + newName);
        System.out.print("Press enter to return to main menu...");
        scanner.nextLine();
        displayMenu();
    }


    // EFFECTS:  displays menu of options to user when they exit the program
    public void displayExitMenu() {
        System.out.println("\nBefore Exiting The Inventory Manager!");
        System.out.println("-------------------------------------");
        System.out.print("[1] - Save before exiting\n"
                       + "[2] - Exit without saving\n\n");
        System.out.print("Selection: ");
        int selection = scanner.nextInt();
        scanner.nextLine();

        handleApplicationExit(selection);
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
    //          part of the inventory
    public void displayBarcodeFound(Item item) {
        System.out.println("\nWhat would you like to do...");
        System.out.println("----------------------------");
        System.out.println("[1] Remove item from inventory\n"
                         + "[2] Return to main menu\n");
        System.out.print("Selection: ");
        int selection = scanner.nextInt();
        scanner.nextLine();

        handleBarcodeFound(selection, item);
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


    // EFFECTS: displays a menu that allows users to make another selection when an item with the inputted name is
    //          part of the inventory
    public void displayNameFound(Item item) {
        System.out.println("\nWhat would you like to do...");
        System.out.println("----------------------------");
        System.out.println("[1] Remove item from inventory\n"
                         + "[2] Return to main menu\n");
        System.out.print("Selection: ");
        int selection = scanner.nextInt();

        handleNameFound(selection, item);

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
        System.out.println("New item added to inventory, press enter to return to main menu...");
        scanner.nextLine();
        displayMenu();
    }

    /* MODIFIES: this
     * EFFECTS: creates the necessary menus that allow for users to remove their desired quantity of an item
     */
    public void displayItemRemoval(Item item) {
        System.out.println("\nWhat is the quantity you would like to remove?");
        System.out.println("----------------------------------------------");
        System.out.print("Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        if (item.isDeductible(quantity)) {
            System.out.println("\nPress enter to confirm removal of item(s)...");
            scanner.nextLine();
            inventory.removeItem(item, quantity);

            System.out.print("Item(s) have been removed from inventory, press enter to return to main menu...");
            scanner.nextLine();
            displayMenu();
        } else {
            System.out.println("\nYour desired quantity surpassed the quantity in the inventory, please input"
                             + " another quantity");
            displayItemRemoval(item);
        }
    }


    /*
     * REQUIRES: selection be an integer
     * EFFECTS: handles the selections made by user when to decide whether or not they want to load previous inventory
     */
    private void handleLoadPreviousInventory(int selection) {
        switch (selection) {
            case 1:
                loadInventory();
                break;
            case 2:
                displayMenu();
                break;
            default:
                displayLoadPreviousInventory();
        }
    }

    /*
     * REQUIRES: selection be an integer
     * EFFECTS: handles the selections made by user when they exit the application
     */
    private void handleApplicationExit(int selection) {
        switch (selection) {
            case 1:
                saveInventoryExit();
                System.out.print("Press enter to exit...");
                scanner.nextLine();
                System.exit(0);
                break;
            case 2:
                System.exit(0);
            default:
                displayExitMenu();
        }
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
            displayBarcodeFound(item);
            /*System.out.println("Press enter to return to main menu...");
            scanner.nextLine();
            displayMenu();*/
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
            displayNameFound(item);
            /*System.out.println("Press enter to return to main menu...");
            scanner.nextLine();
            displayMenu();*/
        } else {
            displayNameNotFound();
        }
    }

    /*
     * REQUIRES: selection be an integer
     * EFFECTS: handles the selections made by user when their inputted item barcode is found
     */
    private void handleBarcodeFound(int selection, Item item) {
        switch (selection) {
            case 1:
                displayItemRemoval(item);
                break;
            case 2:
                displayMenu();
                break;
            default:
                displayBarcodeFound(item);
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
     * EFFECTS: handles the selections made by user when their inputted item name is found
     */
    private void handleNameFound(int selection, Item item) {
        switch (selection) {
            case 1:
                displayItemRemoval(item);
                break;
            case 2:
                displayMenu();
                break;
            default:
                displayNameFound(item);
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
            default:
                displayMenu();
        }
    }

    /*
     * REQUIRES: selection be an integer
     * EFFECTS: also handles the selections made by user from the main menu of the program, created because
     *          checkstyle has maximum 25 lines per method
     */
    public void handleSelection2(int selection) {
        switch (selection) {
            case 5:
                displayChangeInventoryName();
                break;
            case 6:
                loadInventory();
                break;
            case 7:
                saveInventory();
                break;
            case 8:
                displayExitMenu();
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


    // EFFECTS: saves the newly created or edited inventory to a file
    public void saveInventory() {
        try {
            printer.open();
            printer.write(inventory);
            printer.close();

            System.out.println("\nSuccess: Your inventory has been saved as: " + TARGET_JSON_FILE + "\n");
            System.out.print("Press enter to return to main menu...");
            scanner.nextLine();
            displayMenu();

        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("\nError: Unable to write to the file: " + TARGET_JSON_FILE + "\n");
            System.out.print("Press enter to return to main menu...");
            scanner.nextLine();
            displayMenu();
        }
    }


    // EFFECTS: saves the newly created or edited inventory to a file before exiting from the inventory manager app
    public void saveInventoryExit() {
        try {
            printer.open();
            printer.write(inventory);
            printer.close();

            System.out.println("\nSuccess: Your inventory has been saved as: " + TARGET_JSON_FILE);

        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("\nError: Unable to write to the file: " + TARGET_JSON_FILE + "\n");
            System.out.print("Press enter to return to main menu...");
            scanner.nextLine();
            displayMenu();
        }
    }


    /* MODIFIES: this
     * EFFECTS: loads a previously created inventory from target file
     */
    public void loadInventory() {
        try {
            if (inventory.isEmpty()) {
                inventory = reader.read();
            } else {
                displayLoadInventory();
            }

            System.out.println("\nSuccess: Your inventory has been loaded from: " + TARGET_JSON_FILE + "\n");
            System.out.print("Press enter to return to main menu...");
            scanner.nextLine();
            displayMenu();

        } catch (IOException ioException) {
            System.out.println("\nError: Unable to read from the file: " + TARGET_JSON_FILE + "\n");
            System.out.print("Press enter to return to main menu...");
            scanner.nextLine();
            displayMenu();
        }
    }


    // EFFECTS: displays the menu presented to user when they try to load an inventory on top of their current
    //          non-empty inventory
    public void displayLoadInventory() {
        System.out.println("\nAre you sure you want to overwrite your current inventory?");
        System.out.println("----------------------------------------------------------");
        System.out.println("[1] - Yes\n"
                         + "[2] - No");
        System.out.print("Selection: ");

        int selection = scanner.nextInt();
        scanner.nextLine();

        handleLoadInventory(selection);
    }


    /*
     * REQUIRES: selection be an integer
     * EFFECTS: handles the selections from user when they try to load an inventory on top of their current non-empty
     *          inventory
     */
    public void handleLoadInventory(int selection) {
        switch (selection) {
            case 1:
                inventory.removeAllItems();
                loadInventory();
                break;
            case 2:
                System.out.print("\nInventory not loaded, press enter to return to main menu...\n");
                scanner.nextLine();
                displayMenu();
                break;
            default:
                displayLoadInventory();
        }
    }


}
