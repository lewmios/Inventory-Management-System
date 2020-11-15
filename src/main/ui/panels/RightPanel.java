package ui.panels;

import model.Inventory;
import model.InventoryState;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {

    private InventoryPanel inventoryPanel;
    private LeftPanel leftPanel;

    public RightPanel(Inventory inventory, InventoryState inventoryState) {
        this.inventoryPanel = new InventoryPanel(inventory, inventoryState);
        setLayout(new BorderLayout());

        add(inventoryPanel);
    }

    public LeftPanel updatedLeftPanel(Inventory inventory, InventoryState inventoryState) {
        this.leftPanel = new LeftPanel(inventory, inventoryState);

        inventoryPanel.itemList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !inventoryState.isEmpty()) {
                leftPanel.detailsPanel.itemUpdate(inventoryPanel.getSelectedItem());
            }
        });
        return leftPanel;
    }
}
