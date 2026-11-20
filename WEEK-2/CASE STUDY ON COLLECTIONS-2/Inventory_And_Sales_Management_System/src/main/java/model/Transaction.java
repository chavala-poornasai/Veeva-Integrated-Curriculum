
package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Transaction {

    private String transactionId;
    private List<OrderItem> orderItems;
    private BigDecimal totalAmount;
    private LocalDateTime timestamp;

    public Transaction(String transactionId, List<OrderItem> orderItems,
                       BigDecimal totalAmount, LocalDateTime timestamp) {

        this.transactionId = transactionId;

        this.orderItems = new ArrayList<>();

        for (OrderItem item : orderItems) {
            this.orderItems.add(
                    new OrderItem(item.getProductId(), item.getQuantity())
            );
        }

        this.totalAmount = totalAmount;
        this.timestamp = timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public List<OrderItem> getOrderItems() {

        List<OrderItem> copy = new ArrayList<>();

        for (OrderItem item : orderItems) {
            copy.add(
                    new OrderItem(item.getProductId(), item.getQuantity())
            );
        }

        return copy;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
