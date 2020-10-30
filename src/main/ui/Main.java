package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new InventoryApp();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Application unable to start: Inventory file not found... "
                    + "please reload application with correct file");
        }

    }
}
