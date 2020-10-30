package persistence;

import model.Inventory;
import model.Item;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// represents a reader that reads json data stored in a file * MODELED AFTER
// JsonSerializationDemo provided by CPSC 210 course
public class JsonReader {
    private String targetFile;

    //EFFECTS: constructs a reader to read json data from target file
    public JsonReader(String targetFile) {
        this.targetFile = targetFile;
    }


    // EFFECTS: reads the inventory data from a target file and returns it as an inventory, and will throw an
    //          IOException if an error occurs while reading the target file
    public Inventory read() throws IOException {
        String jsonData = readTargetFile(targetFile);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseInventory(jsonObject);
    }


    // EFFECTS: reads the target file data and returns it as a string, and will throw an IOException if an error
    //          occurs while reading the target file
    private String readTargetFile(String targetFile) throws IOException {
        StringBuilder invBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(targetFile), StandardCharsets.UTF_8)) {
            stream.forEach(s -> invBuilder.append(s));
        }

        return invBuilder.toString();
    }


    // EFFECTS: parses inventory from the json object and returns it as an inventory
    private Inventory parseInventory(JSONObject items) {
        String name = items.getString("Inventory Name");

        Inventory inv = new Inventory(name);
        addItems(inv, items);
        return inv;
    }


    // MODIFIES: inv
    // EFFECTS: parses multiple items from the json object and adds them to inventory
    private void addItems(Inventory inv, JSONObject items) {
        JSONArray inventory = items.getJSONArray("Items");
        for (Object json : inventory) {
            JSONObject nextItem = (JSONObject) json;
            addItem(inv, nextItem);
        }
    }


    // MODIFIES: inv
    // EFFECTS: parses a single item from the json object and adds them to inventory
    private void addItem(Inventory inv, JSONObject item) {

        String itemName = item.getString("Name");
        int itemBarcode = item.getInt("Barcode");
        int itemQuantity = item.getInt("Quantity");
        String itemCategory = item.getString("Category");
        String itemDescription = item.getString("Description");

        Item newItem = new Item(itemName, itemBarcode, itemQuantity, itemCategory, itemDescription);
        inv.addItem(newItem);
    }
}

