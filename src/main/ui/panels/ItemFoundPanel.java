package ui.panels;

import model.InventoryState;
import model.Item;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

// represents a JPanel containing item details such as name, barcode, quantity, category, and description
public class ItemFoundPanel extends JPanel {

    private static Item selectedItem = null;

    private static JLabel itemName;
    private static JLabel itemBarcode;
    private static JLabel itemQuantity;
    private static JLabel itemCategory;
    private static JLabel itemDescription;


    // EFFECTS: creates a JPanel that displays all the fields associated with the Item object found in the inventory
    public ItemFoundPanel() {
        setLayout(new GridLayout(5, 1));
        Border displayBorder = new EmptyBorder(20, 20, 20, 20);
        Border displayTitle = new TitledBorder("Item Details");
        setBorder(new CompoundBorder(displayTitle, displayBorder));
        setPreferredSize(new Dimension(350, 220));

        this.itemName = new JLabel();
        this.itemBarcode = new JLabel();
        this.itemQuantity = new JLabel();
        this.itemCategory = new JLabel();
        this.itemDescription = new JLabel();

        add(this.itemName);
        add(this.itemBarcode);
        add(this.itemQuantity);
        add(this.itemCategory);
        add(this.itemDescription);

    }


    /* MODIFIES: this
     * EFFECTS: updates the JLabels so that when an Item is found, the ItemFoundPanel will display its
     *          associated fields
     */
    public static void itemUpdate(Item item) {
        try {
            selectedItem = item;

            itemName.setText("Name: " + selectedItem.getItemName());
            itemBarcode.setText("Barcode: " + selectedItem.getItemBarcode());
            itemQuantity.setText("Quantity: " + selectedItem.getItemQuantity());
            itemCategory.setText("Category: " + selectedItem.getItemCategory());
            itemDescription.setText("Description: " + selectedItem.getItemDescription());
        } catch (Exception e) {
            // do nothing
        }
    }
}
