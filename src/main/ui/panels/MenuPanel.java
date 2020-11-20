package ui.panels;

import model.Inventory;
import model.InventoryState;
import model.Item;
import persistence.JsonReader;
import persistence.JsonWriter;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

// represents a JPanel that allows users to interact with their inventory
public class MenuPanel extends JPanel implements ActionListener {

    private Inventory inventory;
    private InventoryState inventoryState;

    private JsonReader reader;
    private JsonWriter printer;
    private static final String TARGET_JSON_FILE = "./data/inventory.json";

    private static final String TARGET_SOUND_FILE = "data/SuccessSound.wav";

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


    // EFFECTS: creates a JPanel that displays all the buttons required for the users to interact with their
    //          inventory and also displays the name of the current inventory at the top of the panel
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


    /* MODIFIES: this
     * EFFECTS: initializes and creates all the JButtons and ActionListeners for the buttons
     */
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


    /* MODIFIES: this
     * EFFECTS: returns the JLabel that displays the name of the current inventory
     */
    public JLabel createInventoryNameLabel() {
        String inventoryName = this.inventory.getInventoryName();
        this.inventoryNameLabel = new JLabel("Current Inventory: [ " + inventoryName + " ]");

        return inventoryNameLabel;
    }


    // EFFECTS: ActionListener for all the buttons on the panel, and opens different panels depending on the button
    //          pressed by the user
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


    /* MODIFIES: this
     * EFFECTS: displays ItemLookUpPanel if user pressed itemLookup button and if the item is found, displays a
     *          ItemFoundPanel that shows user the details of that item
     */
    public void displayItemLookup() {
        this.itemLookupPanel = new ItemLookupPanel();
        this.foundItemPanel = new ItemFoundPanel();

        int result = JOptionPane.showConfirmDialog(null, this.itemLookupPanel, "Enter Barcode or Name of Item",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (itemLookupPanel.isBarcode()) {
                try {
                    this.foundItem = itemLookupPanel.searchBarcode(this.inventory);
                    ifIsItemThenDisplay(this.foundItem);

                } catch (NumberFormatException numberFormatException) {
                    JOptionPane.showMessageDialog(null, "Please re-enter valid barcode");
                    displayItemLookup();

                }
            } else if (itemLookupPanel.isName()) {
                this.foundItem = itemLookupPanel.searchName(this.inventory);
                ifIsItemThenDisplay(this.foundItem);

            } else {
                JOptionPane.showMessageDialog(null, "Please enter either barcode or name, not both");
                displayItemLookup();

            }
        }
    }


    // EFFECTS: if the item found by the ItemFoundPanel is not null then returns true, else false
    public void ifIsItemThenDisplay(Item item) {
        if (item != null) {
            this.foundItemPanel.itemUpdate(item);
            playSound(TARGET_SOUND_FILE);
            JOptionPane.showMessageDialog(null, foundItemPanel, "Item Found", JOptionPane.PLAIN_MESSAGE);
        }
    }


    /* MODIFIES: this, inventory, inventoryState
     * EFFECTS: displays AddNewItemPanel if user pressed addItem button and if the details entered are valid, adds
     *          the newly created item to the inventory and inventoryState, else tells user to re-enter valid details
     */
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


    /* MODIFIES: this, inventory
     * EFFECTS: displays a JOptionPane that allows the user to change the name of the current inventory and if the
     *          entered name is valid then changes the inventory name, else tells user to re-enter valid name
     */
    public void displayChangeInventoryName() {
        String newInvName = JOptionPane.showInputDialog("New Inventory Name");

        if (newInvName != null) {
            if (!newInvName.isEmpty()) {
                this.inventory.setInventoryName(newInvName);
                this.inventoryNameLabel.setText("Current Inventory: [ " + newInvName + " ]");
            } else {
                JOptionPane.showMessageDialog(null, "Please re-enter new inventory name properly");
                displayChangeInventoryName();
            }
        }
    }


    /* MODIFIES: this, inventory, inventoryState
     * EFFECTS: displays a JOptionPane that asks user if they want to load inventory from file, if YES option is
     *          selected, inventory will be loaded from the file, else if NO option is selected, does nothing
     */
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


    /* MODIFIES: this
     * EFFECTS: displays a JOptionPane that asks user if they want to save inventory to file, if YES option is
     *          selected, inventory will be saved to the file, else if NO option is selected, does nothing
     */
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


    // EFFECTS: reads the given file path and initializes an AudioPlayer that plays the sound from the given file
    //          whenever this method is called; *code modeled after https://www.youtube.com/watch?v=3q4f6I5zi2w
    public void playSound(String path) {
        InputStream ss;
        try {
            ss = new FileInputStream(new File(path));
            AudioStream sa = new AudioStream(ss);
            AudioPlayer.player.start(sa);

        } catch (Exception e) {
            System.out.println("\nError: unable to play sound from file: " + TARGET_SOUND_FILE + "\n");
        }
    }


}
