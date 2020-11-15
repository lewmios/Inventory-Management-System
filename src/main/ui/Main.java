package ui;

import model.Inventory;
import model.Item;
import ui.swing.InventoryGUI;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new InventoryGUI(new Inventory("New Inventory"));
            //new InventoryApp();
        } catch (Exception fileNotFoundException) {
            //System.out.println("Application unable to start: Inventory file not found... "
            //        + "please reload application with correct file");
            fileNotFoundException.printStackTrace();
        }

    }
}


