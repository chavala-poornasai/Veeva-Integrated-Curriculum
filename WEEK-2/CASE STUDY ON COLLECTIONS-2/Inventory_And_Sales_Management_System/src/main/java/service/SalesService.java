
package service;

import exception.OutOfStockException;
import model.OrderItem;
import model.Product;
import model.Transaction;
import repository.DataStore;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import exception.InsufficientStockException;
public class SalesService {

    private InventoryService inventoryService = new InventoryService();

    // Process customer order
    public Transaction processSale(List<OrderItem> orderItems) {

        if (orderItems == null || orderItems.isEmpty()) {
            System.out.println("Order cannot be empty!");
            return null;
        }

        // Combine duplicate product entries
        Map<Integer, Integer> requested = new LinkedHashMap<>();

        try {
            for (OrderItem item : orderItems) {

                if (item == null || item.getQuantity() <= 0) {
                    System.out.println("Invalid order quantity!");
                    return null;
                }

                int id = item.getProductId();

                if (!DataStore.getProducts().containsKey(id)) {
                    System.out.println("Product not found: " + id);
                    return null;
                }

                int oldQuantity = requested.getOrDefault(id, 0);

                requested.put(id,
                        Math.addExact(oldQuantity, item.getQuantity()));
            }
        } catch (ArithmeticException e) {
            System.out.println("Requested quantity is too large!");
            return null;
        }

        // Phase 1: Validate stock and calculate bill
        BigDecimal total = BigDecimal.ZERO;

        List<OrderItem> confirmedItems = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : requested.entrySet()) {

            int id = entry.getKey();
            int quantity = entry.getValue();

            Product product = DataStore.getProducts().get(id);

            if (product.getUnitPrice() == null ||
                    product.getUnitPrice().signum() < 0) {
                System.out.println("Invalid product price!");
                return null;
            }

            int available = inventoryService.getAvailableStock(id);

            if (available == -1) {
                return null;
            }

            if (available == 0) {
                throw new OutOfStockException(
                        product.getProductName() + " is currently out of stock!"
                );
            }

            if (quantity > available) {
                throw new InsufficientStockException(
                        "Transaction failed: Only " + available +
                                " units of " + product.getProductName() + " are available."
                );
            }

            BigDecimal itemTotal = product.getUnitPrice()
                    .multiply(BigDecimal.valueOf(quantity));

            total = total.add(itemTotal);

            confirmedItems.add(new OrderItem(id, quantity));
        }

        // Prepare transaction record
        String transactionId = "TXN-" + UUID.randomUUID();
        LocalDateTime timestamp = LocalDateTime.now();

        Transaction transaction = new Transaction(
                transactionId, confirmedItems, total, timestamp
        );

        // Phase 2: Deduct stock after all validations pass
        for (OrderItem item : confirmedItems) {

            int id = item.getProductId();
            int quantity = item.getQuantity();

            int available = DataStore.getInventory().get(id);

            DataStore.getInventory().put(id, available - quantity);
        }

        // Record successful transaction
        DataStore.getTransactions().put(transactionId, transaction);

        System.out.println("\nTransaction successful!");
        System.out.println("Transaction ID: " + transactionId);

        for (OrderItem item : confirmedItems) {

            Product product = DataStore.getProducts()
                    .get(item.getProductId());

            System.out.println(product.getProductName()
                    + " x " + item.getQuantity());
        }

        System.out.println("Total Bill: $" + total);

        return transaction;
    }
}
