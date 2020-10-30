package persistence;

import java.io.*;
import java.util.Iterator;

import model.Inventory;
import org.json.JSONObject;

// represents a writer that prints json representation of an inventory to a file * MODELED AFTER
// JsonSerializationDemo provided by CPSC 210 course
public class JsonWriter {
    private static final int INDENT = 2;
    private PrintWriter printer;
    private String targetFile;


    // EFFECTS: constructs a writer to print given data to a destination file
    public JsonWriter(String targetFile) {
        this.targetFile = targetFile;
    }


    /* MODIFIES: this
     * EFFECTS: opens the printer, and throws the FileNotFoundException if the target file cannot be found
     */
    public void open() throws FileNotFoundException {
        printer = new PrintWriter(new File(targetFile));
    }


    /* MODIFIES: this
     * EFFECTS: prints the json representation of an inventory to the file
     */
    public void write(Inventory inv) {
        JSONObject inventory = inv.toJson();
        printToFile(inventory.toString(INDENT));
    }


    /* MODIFIES: this
     * EFFECTS: closes the printer
     */
    public void close() {
        printer.close();
    }


    /* MODIFIES: this
     * EFFECTS: takes given string and prints it to the file
     */
    public void printToFile(String data) {
        printer.print(data);
    }






















}
