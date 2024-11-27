package system.inventory;

import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;


public class InventoryManager {
    private Map<Integer, Item> inventory;
    private Map<String, PriorityQueue<Item>> categoryMap;
    private static final Logger LOGGER = Logger.getLogger(InventoryManager.class.getName());

    public InventoryManager() {
        inventory = new HashMap<>();
        categoryMap = new HashMap<>();
    }

    public void addItem(Item item) {
        inventory.put(item.getId(), item);

        categoryMap.putIfAbsent(item.getCategory(), new PriorityQueue<>(
                (a, b) -> Integer.compare(b.getQuantity(), a.getQuantity())
        ));
        categoryMap.get(item.getCategory()).add(item);

        LOGGER.log(Level.INFO, "Added item: {0}", item.getName());
    }


    public List<Item> getItemsByCategory(String category) {
        PriorityQueue<Item> queue = categoryMap.getOrDefault(category, new PriorityQueue<>());
        return new ArrayList<>(queue);
    }

    public List<Item> getTopKItems(int k) {
        PriorityQueue<Item> allItems = new PriorityQueue<>(
                (a, b) -> Integer.compare(b.getQuantity(), a.getQuantity())
        );
        allItems.addAll(inventory.values());

        List<Item> topK = new ArrayList<>();
        while (k-- > 0 && !allItems.isEmpty()) {
            topK.add(allItems.poll());
        }
        return topK;
    }

    // the idea is that a certain threshold is to be provided, and if the item quantity lowers below that threshold,
    // a restock notification is trigggered
    public void checkRestockThreshold(int threshold) {
        for (Item item : inventory.values()) {
            if (item.getQuantity() < threshold) {
                LOGGER.log(Level.WARNING, "Restock needed for Item ID: {0}, Name: {1}",
                        new Object[]{item.getId(), item.getName()});
            }
        }
    }


    public void mergeInventory(InventoryManager other) {
        for (Item item : other.inventory.values()) {
            if (inventory.containsKey(item.getId())) {
                Item existing = inventory.get(item.getId());
                if (existing.getQuantity() < item.getQuantity()) {
                    existing.setQuantity(item.getQuantity());
                }
            } else {
                addItem(item);
            }
            LOGGER.log(Level.INFO, "Merged item: {0}", item.getName());
        }
    }

}
