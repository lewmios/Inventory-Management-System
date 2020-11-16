package ui.panels;

import model.Inventory;
import model.InventoryState;

import javax.swing.*;
import java.awt.*;

// represents the left panel of the GUI
public class LeftPanel extends JPanel {

    public ItemDetailsPanel itemDetailsPanel;
    public MenuPanel menuPanel;


    // creates a JPanel which has a DetailsPanel in the NORTH and MenuPanel in the SOUTH
    public LeftPanel(Inventory inventory, InventoryState inventoryState) {
        this.itemDetailsPanel = new ItemDetailsPanel(inventoryState);
        this.menuPanel = new MenuPanel(inventory, inventoryState);

        setLayout(new BorderLayout());

        add(itemDetailsPanel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.SOUTH);
    }
}
