package ui.swing;

import model.Inventory;
import model.InventoryState;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;


public class InventoryGUI extends JFrame {

    private InventoryState inventoryState;
    private Inventory inventory;

    private static final String TARGET_JSON_FILE = "./data/inventory.json";
    private JsonReader reader;
    private JsonWriter printer;

    private JPanel backgroundPanel;
    private LeftPanel leftPanel;
    private RightPanel rightPanel;

    public InventoryGUI(Inventory inventory) {
        this.inventory = inventory;
        loadOnStartup();
        this.inventoryState = new InventoryState(this.inventory);

        setTitle("Inventory Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.backgroundPanel = new JPanel();
        this.rightPanel = new RightPanel(this.inventory, inventoryState);
        this.leftPanel = rightPanel.updatedLeftPanel(this.inventory, inventoryState);

        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        backgroundPanel.add(rightPanel, BorderLayout.EAST);
        backgroundPanel.add(leftPanel, BorderLayout.WEST);

        add(backgroundPanel, BorderLayout.CENTER);

        setPreferredSize(new Dimension(850, 550));
        setResizable(false);
        setCloseOperation();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void loadOnStartup() {
        this.reader = new JsonReader(TARGET_JSON_FILE);

        int result = JOptionPane.showConfirmDialog(null, "Would you like to load previous inventory?",
                "Load Inventory?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            try {
                this.inventory = reader.read();
            } catch (IOException ioException) {
                System.out.println("\nError: Unable to read from the file: " + TARGET_JSON_FILE + "\n");
            }
        }
    }


    public void setCloseOperation() {
        this.printer = new JsonWriter(TARGET_JSON_FILE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Save before exiting?", "Before Exiting",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    try {
                        printer.open();
                        printer.write(inventory);
                        printer.close();
                        JOptionPane.showMessageDialog(null, "[ " + inventory.getInventoryName() + " ]"
                                + " Successfully Saved...Goodbye uwu");
                    } catch (FileNotFoundException fileNotFoundException) {
                        System.out.println("\nError: Unable to write to the file: " + TARGET_JSON_FILE + "\n");
                    }

                }
            }
        });
    }
}