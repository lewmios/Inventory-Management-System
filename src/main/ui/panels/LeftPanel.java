package ui.panels;

import model.Inventory;
import model.InventoryState;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {

    public DetailsPanel detailsPanel;
    public MenuPanel menuPanel;

    public LeftPanel(Inventory inventory, InventoryState inventoryState) {
        this.detailsPanel = new DetailsPanel(inventoryState);
        this.menuPanel = new MenuPanel(inventory, inventoryState);

        setLayout(new BorderLayout());

        add(detailsPanel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.SOUTH);
    }
}
