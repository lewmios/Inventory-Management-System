package ui.panels;

import model.Inventory;
import model.InventoryState;
import model.Item;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MenuPanel extends JPanel implements ActionListener {

    private Inventory inventory;
    private InventoryState inventoryState;

    private JsonReader reader;
    private JsonWriter printer;
    private static final String TARGET_JSON_FILE = "./data/inventory.json";

    private JLabel inventoryNameLabel;
    private AddNewItemPanel addNewItemPanel;

    private ItemLookupPanel itemLookupPanel;
    private Item foundItem;
    private ItemFoundPanel foundItemPanel;

    private JButton itemLookup;
    private JButton addItem;
    private JButton changeInvName;
    private JButton loadInventory;
    private JButton saveInventory;
    private Map<String, JButton> menuButtons;


    public MenuPanel(Inventory inventory, InventoryState inventoryState) {
        this.inventory = inventory;
        this.inventoryState = inventoryState;

        this.reader = new JsonReader(TARGET_JSON_FILE);
        this.printer = new JsonWriter(TARGET_JSON_FILE);

        GridLayout menuLayout = new GridLayout(6, 1);
        setLayout(menuLayout);
        menuLayout.setVgap(10);
        Border titleBorder = new TitledBorder("Main Menu");
        Border emptyBorder = new EmptyBorder(10, 10, 10, 10);
        setBorder(new CompoundBorder(titleBorder, emptyBorder));
        setPreferredSize(new Dimension(350, 240));

        createButtons();

        this.menuButtons = new LinkedHashMap<>();
        menuButtons.put("itemLookup", itemLookup);
        menuButtons.put("addItem", addItem);
        menuButtons.put("changeInvName", changeInvName);
        menuButtons.put("loadInv", loadInventory);
        menuButtons.put("saveInv", saveInventory);

        add(createInventoryNameLabel(), BorderLayout.NORTH);

        for (JButton menuButton : menuButtons.values()) {
            add(menuButton);
        }
    }


    public void createButtons() {
        this.itemLookup = new JButton("Item Lookup");
        this.addItem = new JButton("Add New Item");
        this.changeInvName = new JButton("Change Inventory Name");
        this.loadInventory = new JButton("Load Inventory");
        this.saveInventory = new JButton("Save Inventory");

        this.itemLookup.addActionListener(this);
        this.addItem.addActionListener(this);
        this.changeInvName.addActionListener(this);
        this.loadInventory.addActionListener(this);
        this.saveInventory.addActionListener(this);
    }


    public JLabel createInventoryNameLabel() {
        String inventoryName = this.inventory.getInventoryName();
        this.inventoryNameLabel = new JLabel("Current Inventory: [ " + inventoryName + " ]");

        return inventoryNameLabel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == itemLookup) {
            displayItemLookup();

        } else if (e.getSource() == addItem) {
            displayAddItem();

        } else if (e.getSource() == changeInvName) {
            displayChangeInventoryName();

        } else if (e.getSource() == loadInventory) {
            displayLoadInventory();
            this.inventoryState.refreshListModel();

        } else if (e.getSource() == saveInventory) {
            displaySaveInventory();
        }
    }


    public void displayItemLookup() {
        this.itemLookupPanel = new ItemLookupPanel();
        this.foundItemPanel = new ItemFoundPanel();

        int result = JOptionPane.showConfirmDialog(null, this.itemLookupPanel, "Enter Barcode or Name of Item",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (itemLookupPanel.isBarcode()) {
                try {
                    this.foundItem = itemLookupPanel.searchBarcode(this.inventory);
                    this.foundItemPanel.itemUpdate(foundItem);
                    JOptionPane.showMessageDialog(null, foundItemPanel, "Item Found", JOptionPane.PLAIN_MESSAGE);

                } catch (NumberFormatException numberFormatException) {
                    JOptionPane.showMessageDialog(null, "Please re-enter valid barcode");
                    displayItemLookup();

                }
            } else if (itemLookupPanel.isName()) {
                this.foundItem = itemLookupPanel.searchName(this.inventory);
                this.foundItemPanel.itemUpdate(foundItem);
                JOptionPane.showMessageDialog(null, foundItemPanel, "Item Found", JOptionPane.PLAIN_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, "Please enter either barcode or name, not both");
                displayItemLookup();
            }
        }
    }


    public void displayAddItem() {
        this.addNewItemPanel = new AddNewItemPanel();

        int result = JOptionPane.showConfirmDialog(null, this.addNewItemPanel, "Please Enter Item Details",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (this.addNewItemPanel.isProperlyInputted()) {
                try {
                    String name = addNewItemPanel.itemName.getText();
                    int barcode = Integer.parseInt(addNewItemPanel.itemBarcode.getText());
                    int quantity = Integer.parseInt(addNewItemPanel.itemQuantity.getText());
                    String category = addNewItemPanel.itemCategory.getText();
                    String description = addNewItemPanel.itemDescription.getText();

                    Item newItem = new Item(name, barcode, quantity, category, description);
                    this.inventory.addItem(newItem);
                    this.inventoryState.refreshListModel();

                } catch (NumberFormatException numberFormatException) {
                    JOptionPane.showMessageDialog(null, "Please re-enter item details properly");
                    displayAddItem();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please re-enter item details properly");
                displayAddItem();
            }
        }
    }


    public void displayChangeInventoryName() {
        String newInvName = JOptionPane.showInputDialog("New Inventory Name");

        if (!newInvName.isEmpty()) {
            this.inventory.setInventoryName(newInvName);
            this.inventoryNameLabel.setText("Current Inventory: [ " + newInvName + " ]");
        } else {
            JOptionPane.showMessageDialog(null, "Please re-enter new inventory name properly");
            displayChangeInventoryName();
        }
    }


    public void displayLoadInventory() {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to load inventory?", "Load Inventory",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            try {
                Inventory fileInventory = reader.read();
                this.inventory.replaceInventory(fileInventory);
                this.inventory.setInventoryName(fileInventory.getInventoryName());
                this.inventoryNameLabel.setText("Current Inventory: [ " + fileInventory.getInventoryName() + " ]");
                JOptionPane.showMessageDialog(null, "[ " + fileInventory.getInventoryName() + " ]"
                        + " Successfully Loaded!");
            } catch (IOException ioException) {
                System.out.println("\nError: Unable to read from the file: " + TARGET_JSON_FILE + "\n");
            }
        }
    }


    public void displaySaveInventory() {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to save inventory?", "Save Inventory",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            try {
                printer.open();
                printer.write(this.inventory);
                printer.close();
                JOptionPane.showMessageDialog(null, "[ " + this.inventory.getInventoryName() + " ]"
                        + " Successfully Saved!");
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("\nError: Unable to write to the file: " + TARGET_JSON_FILE + "\n");
            }
        }
    }


}
