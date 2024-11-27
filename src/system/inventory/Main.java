package system.inventory;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

// Features I learnt
// automated generation approach for 100000 items
// logging functionality using in-built java.util.logging functionality
// log the adding of items and restocking in inventory.log file in the root directory

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            // Create a FileHandler to save logs to a file
            FileHandler fileHandler = new FileHandler("inventory.log", true); // 'true' for append mode
            fileHandler.setLevel(Level.ALL); // Log all levels
            fileHandler.setFormatter(new SimpleFormatter()); // Use simple text format
            LOGGER.addHandler(fileHandler);

            //  log message
            LOGGER.info("Starting Inventory Management System");

            InventoryManager inventoryManager = new InventoryManager();

            // Generating 100,000 items by autoated approach
            for (int i = 1; i <= 100_000; i++) {
                String category = "Category" + (i%10);
                String name = "Item" + i;
                int quantity = (int) (Math.random()*1000);
                inventoryManager.addItem(new Item( i, name, category, quantity ));
            }

            LOGGER.info("100,000 items added successfully.");

            // below 50, ping a restock notification
            inventoryManager.checkRestockThreshold(50);

            System.out.println("Top 5 Items:");
            inventoryManager.getTopKItems(5).forEach(item ->
                    System.out.println(item.getName() + " - Quantity: " + item.getQuantity())
            );
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred: ", e);
        }
    }
}
