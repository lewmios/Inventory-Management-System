package ui.panels;

import model.Inventory;
import model.Item;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

// represents a JPanel that allows the user to search for an item in the inventory
public class ItemLookupPanel extends JPanel {

    private JLabel barcodeLabel;
    private JLabel nameLabel;
    private JLabel exception;

    public JTextField barcodeSearch;
    public JTextField nameSearch;

    public Item searchedItem;


    // EFFECTS: creates a JPanel that has necessary JTextFields allowing users to search for an item by barcode or name
    public ItemLookupPanel() {
        setLayout(null);
        Border titleBorder = new TitledBorder("Enter Barcode or Name of Item");
        Border emptyBorder = new EmptyBorder(5, 5, 0, 5);
        setBorder(new CompoundBorder(titleBorder, emptyBorder));
        setPreferredSize(new Dimension(300, 150));

        barcodeLabel = new JLabel("Barcode* : ");
        barcodeLabel.setBounds(10, 30, 80, 25);
        add(barcodeLabel);

        barcodeSearch = new JTextField();
        barcodeSearch.setBounds(100, 30, 165, 25);
        add(barcodeSearch);

        nameLabel = new JLabel("Name : ");
        nameLabel.setBounds(10, 60, 80, 25);
        add(nameLabel);

        nameSearch = new JTextField();
        nameSearch.setBounds(100, 60, 165, 25);
        add(nameSearch);

        exception = new JLabel("* Requires numerical inputs only");
        exception.setBounds(10, 90, 300, 25);
        add(exception);

    }


    // EFFECTS: returns true if the user has only inputted a barcode value
    public Boolean isBarcode() {
        return !barcodeSearch.getText().isEmpty() && nameSearch.getText().isEmpty();
    }


    // EFFECTS: returns true if the user has only inputted a name value
    public Boolean isName() {
        return !nameSearch.getText().isEmpty() && barcodeSearch.getText().isEmpty();
    }


    /* REQUIRES: isBarcode is true
     * EFFECTS: takes the inputted barcode and if matching item is in inventory, returns item, else tells user that
     *          item is not found and returns null
     */
    public Item searchBarcode(Inventory inventory) throws NumberFormatException {
        int barcode = Integer.parseInt(barcodeSearch.getText());
        if (inventory.getAllItems().containsKey(barcode)) {
            this.searchedItem = inventory.getItemByBarcode(barcode);
            return searchedItem;
        } else {
            JOptionPane.showMessageDialog(null, "Barcode Invalid: Item not found");
            return null;
        }
    }


    /* REQUIRES: isName is true
     * EFFECTS: takes the inputted name and if matching item is in inventory, returns item, else tells user that
     *          item is not found and returns null
     */
    public Item searchName(Inventory inventory) {
        Item foundItem = inventory.getItemByName(nameSearch.getText());
        if (foundItem != null) {
            this.searchedItem = foundItem;
            return searchedItem;
        } else {
            JOptionPane.showMessageDialog(null, "Name Invalid: Item not found");
            return null;
        }
    }

}
