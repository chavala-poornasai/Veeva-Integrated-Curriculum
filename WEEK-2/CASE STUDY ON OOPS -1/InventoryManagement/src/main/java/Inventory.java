
import java.util.HashMap;

public class Inventory {

    private HashMap<String, Integer> items = new HashMap<>();

    // Add item
    public void addItem(String itemName, int quantity) {

        if (quantity <= 0) {
            System.out.println("Quantity must be positive!");
            return;
        }

        items.put(
                itemName,
                items.getOrDefault(itemName, 0) + quantity
        );

        System.out.println("Item added successfully!");
    }

    // Remove item quantity
    public void removeItem(String itemName, int quantity) {

        if (quantity <= 0) {
            System.out.println("Quantity must be positive!");
            return;
        }

        if (!items.containsKey(itemName)) {
            System.out.println("Item not found!");
            return;
        }

        int currentQuantity = items.get(itemName);

        if (quantity > currentQuantity) {
            System.out.println("Insufficient quantity!");
            return;
        }

        int remainingQuantity = currentQuantity - quantity;

        if (remainingQuantity == 0) {
            items.remove(itemName);
        } else {
            items.put(itemName, remainingQuantity);
        }

        System.out.println("Item removed successfully!");
    }

    // Check item quantity
    public void checkQuantity(String itemName) {

        if (items.containsKey(itemName)) {

            System.out.println(
                    "Available quantity of " + itemName +
                            " : " + items.get(itemName)
            );

        } else {

            System.out.println("Item not found in inventory!");
        }
    }

    // Display all items
    public void displayInventory() {

        if (items.isEmpty()) {
            System.out.println("Inventory is empty!");
            return;
        }

        System.out.println("\n----- INVENTORY DETAILS -----");

        for (String item : items.keySet()) {

            System.out.println(
                    item + " : " + items.get(item)
            );
        }
    }
}
