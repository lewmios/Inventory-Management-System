package ui.panels;

import model.Inventory;
import model.InventoryState;

import javax.swing.*;
import java.awt.*;

// represents the right panel of the GUI
public class RightPanel extends JPanel {

    private InventoryPanel inventoryPanel;
    private LeftPanel leftPanel;


    // creates a JPanel which has an InventoryPanel in the CENTER
    public RightPanel(Inventory inventory, InventoryState inventoryState) {
        this.inventoryPanel = new InventoryPanel(inventory, inventoryState);
        setLayout(new BorderLayout());

        add(inventoryPanel);
    }


    // EFFECTS: returns a LeftPanel that is able to communicate with the RightPanel through a ListSelectionListener
    public LeftPanel updatedLeftPanel(Inventory inventory, InventoryState inventoryState) {
        this.leftPanel = new LeftPanel(inventory, inventoryState);

        inventoryPanel.itemList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !inventoryState.isEmpty()) {
                leftPanel.itemDetailsPanel.itemUpdate(inventoryPanel.getSelectedItem());
            }
        });
        return leftPanel;
    }
}
