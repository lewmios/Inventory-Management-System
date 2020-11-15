package ui.panels;

import model.Inventory;
import model.InventoryState;
import model.Item;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryPanel extends JPanel {

    private Inventory inventory;

    public JList<Item> itemList;
    public InventoryState inventoryState;
    private JScrollPane itemScroll;

    public InventoryPanel(Inventory inventory, InventoryState inventoryState) {
        this.inventory = inventory;
        this.inventoryState = inventoryState;

        setLayout(new BorderLayout());
        Border inventoryBorder = new EmptyBorder(20, 20, 20, 20);
        Border inventoryTitle = new TitledBorder("Inventory List");
        setBorder(new CompoundBorder(inventoryTitle, inventoryBorder));
        setPreferredSize(new Dimension(425, 400));

        this.itemScroll = new JScrollPane();
        this.itemList = new JList(inventoryState);
        itemScroll.setViewportView(itemList);

        add(itemScroll);
    }

    public Item getSelectedItem() {
        return this.itemList.getSelectedValue();
    }
}
