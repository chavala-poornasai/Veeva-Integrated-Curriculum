
package service;

import repository.DataStore;

public class InventoryService {

    // Get available stock
    public int getAvailableStock(int productId) {

        Integer stock = DataStore.getInventory().get(productId);

        if (stock == null) {
            System.out.println("Product not found in inventory!");
            return -1;
        }

        return stock;
    }

    // Check stock availability
    public boolean checkStock(int productId, int quantity) {

        if (quantity <= 0) {
            System.out.println("Invalid quantity!");
            return false;
        }

        int available = getAvailableStock(productId);

        if (available == -1) {
            return false;
        }

        if (available == 0) {
            System.out.println("Product is out of stock!");
            return false;
        }

        if (quantity > available) {
            System.out.println("Insufficient stock! Only "
                    + available + " units available.");
            return false;
        }

        return true;
    }

    // Update stock after successful sale
    public void updateStock(int productId, int quantity) {

        if (!checkStock(productId, quantity)) {
            return;
        }

        int available = getAvailableStock(productId);

        DataStore.getInventory().put(productId, available - quantity);

        System.out.println("Stock updated successfully!");
    }

    // Restock an existing product

    public void restockProduct(int productId, int quantity) {

        if (quantity <= 0) {
            System.out.println("Invalid restock quantity!");
            return;
        }

        int available = getAvailableStock(productId);

        if (available == -1) {
            return;
        }

        try {
            int updatedStock = Math.addExact(available, quantity);

            DataStore.getInventory().put(productId, updatedStock);

            System.out.println("Stock replenished successfully!");
            System.out.println("Available stock: " + updatedStock);

        } catch (ArithmeticException e) {
            System.out.println("Stock limit exceeded!");
        }
    }

}
