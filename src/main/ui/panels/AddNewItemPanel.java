package ui.panels;

import model.Inventory;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

// represents a JPanel containing required input fields to create a new item
public class AddNewItemPanel extends JPanel {

    private JLabel name;
    private JLabel barcode;
    private JLabel quantity;
    private JLabel category;
    private JLabel description;
    private JLabel exception;

    public JTextField itemName;
    public JTextField itemBarcode;
    public JTextField itemQuantity;
    public JTextField itemCategory;
    public JTextField itemDescription;


    // EFFECT: creates a new JPanel which has all necessary JLabels and JTextFields for users to add a new item
    public AddNewItemPanel() {
        setLayout(null);
        Border titleBorder = new TitledBorder("Please Enter Item Details");
        Border emptyBorder = new EmptyBorder(5, 5, 0, 5);
        setBorder(new CompoundBorder(titleBorder, emptyBorder));

        setLabelsAndFields();
        setAllBounds();
        addAllLabelsFields();

        setPreferredSize(new Dimension(340, 225));
    }


    /* MODIFIES: this
     * EFFECTS: initializes and creates all the JLabels and JTextFields
     */
    public void setLabelsAndFields() {
        this.name = new JLabel("Item Name: ");
        this.barcode = new JLabel("Barcode*: ");
        this.quantity = new JLabel("Quantity*: ");
        this.category = new JLabel("Category: ");
        this.description = new JLabel("Description: ");
        this.exception = new JLabel("* Requires numerical inputs only");

        this.itemName = new JTextField();
        this.itemBarcode = new JTextField();
        this.itemQuantity = new JTextField();
        this.itemCategory = new JTextField();
        this.itemDescription = new JTextField();
    }


    /* MODIFIES: this
     * EFFECTS: sets all the bounds for the JLabels and JTextFields
     */
    public void setAllBounds() {
        name.setBounds(10, 30, 80, 25);
        barcode.setBounds(10, 60, 80, 25);
        quantity.setBounds(10, 90, 80, 25);
        category.setBounds(10, 120, 80, 25);
        description.setBounds(10,150,100,25);
        exception.setBounds(10, 180, 300, 25);

        itemName.setBounds(120, 30, 165, 25);
        itemBarcode.setBounds(120, 60, 165, 25);
        itemQuantity.setBounds(120, 90, 165, 25);
        itemCategory.setBounds(120, 120, 165, 25);
        itemDescription.setBounds(120,150,165,25);
    }


    /* MODIFIES: this
     * EFFECTS: adds all the JLabels and JTextFields to the AddNewItemPanel
     */
    public void addAllLabelsFields() {
        this.add(name);
        this.add(itemName);

        this.add(barcode);
        this.add(itemBarcode);

        this.add(quantity);
        this.add(itemQuantity);

        this.add(category);
        this.add(itemCategory);

        this.add(description);
        this.add(itemDescription);

        this.add(exception);
    }


    // EFFECTS: if the values inputted into the JTextFields are able to create a valid Item object, returns true,
    //          else returns false
    public Boolean isProperlyInputted() {
        if (itemName.getText().isEmpty() || itemCategory.getText().isEmpty() || itemQuantity.getText().isEmpty()
                || itemCategory.getText().isEmpty() || itemDescription.getText().isEmpty()) {
            return false;
        }
        return true;
    }
}
