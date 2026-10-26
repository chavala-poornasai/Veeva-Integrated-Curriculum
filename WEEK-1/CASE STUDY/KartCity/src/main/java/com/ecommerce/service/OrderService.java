package com.ecommerce.service;

import com.ecommerce.enums.OrderStatus;
import com.ecommerce.enums.PaymentStatus;
import com.ecommerce.exception.PaymentFailedException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Customer;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.Payment;
import com.ecommerce.payment.PaymentMethod;
import com.ecommerce.payment.PaymentService;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.util.IdGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderService {
    private static final BigDecimal DEFAULT_SHIPPING_COST = new BigDecimal("50.00");
    private final CustomerService customerService;
    private final ProductService productService;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final TrackingService trackingService;
    private final DiscountService discountService;
    private final GiftCardService giftCardService;
    private final PaymentService paymentService;
    private final SubscriptionService subscriptionService;
    private final BigDecimal shippingCost;
    private final IdGenerator orderIdGenerator;

    public OrderService(CustomerService customerService,
                       ProductService productService,
                       CartService cartService,
                       OrderRepository orderRepository,
                       TrackingService trackingService,
                       DiscountService discountService,
                       GiftCardService giftCardService,
                       PaymentService paymentService,
                       SubscriptionService subscriptionService) {
        this(customerService, productService, cartService, orderRepository, trackingService, discountService,
                giftCardService, paymentService, subscriptionService, DEFAULT_SHIPPING_COST);
    }

    public OrderService(CustomerService customerService,
                       ProductService productService,
                       CartService cartService,
                       OrderRepository orderRepository,
                       TrackingService trackingService,
                       DiscountService discountService,
                       GiftCardService giftCardService,
                       PaymentService paymentService,
                       SubscriptionService subscriptionService,
                       BigDecimal shippingCost) {
        this(customerService, productService, cartService, orderRepository, trackingService, discountService,
                giftCardService, paymentService, subscriptionService, shippingCost, new IdGenerator("ORD", 3001));
    }

    public OrderService(CustomerService customerService,
                       ProductService productService,
                       CartService cartService,
                       OrderRepository orderRepository,
                       TrackingService trackingService,
                       DiscountService discountService,
                       GiftCardService giftCardService,
                       PaymentService paymentService,
                       SubscriptionService subscriptionService,
                       BigDecimal shippingCost,
                       IdGenerator orderIdGenerator) {
        if (shippingCost == null || shippingCost.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Shipping cost cannot be negative");
        }
        this.customerService = customerService;
        this.productService = productService;
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.trackingService = trackingService;
        this.discountService = discountService;
        this.giftCardService = giftCardService;
        this.paymentService = paymentService;
        this.subscriptionService = subscriptionService;
        this.shippingCost = shippingCost;
        this.orderIdGenerator = orderIdGenerator;
    }

    public Order checkout(String customerId, PaymentMethod paymentMethod, String couponCode, String giftCardCode) {
        Customer customer = customerService.getCustomerById(customerId);
        Cart cart = cartService.getCart(customerId);
        if (cart.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty. Add products before checkout.");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        Map<String, Integer> quantities = new LinkedHashMap<>();
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getItems()) {
            var product = productService.getProductById(cartItem.getProductId());
            if (cartItem.getQuantity() <= 0 || product.getStock() < cartItem.getQuantity()) {
                throw new com.ecommerce.exception.InsufficientStockException(
                        "Not enough stock for product: " + product.getName());
            }
            OrderItem orderItem = new OrderItem(product.getProductId(), product.getName(),
                    cartItem.getQuantity(), product.getPrice());
            orderItems.add(orderItem);
            quantities.put(product.getProductId(), cartItem.getQuantity());
            subtotal = subtotal.add(orderItem.getLineTotal());
        }
        BigDecimal shippingCost = subscriptionService.isCustomerSubscribed(customerId)
                ? BigDecimal.ZERO
                : this.shippingCost;

        List<Order> completedOrders = orderRepository.findByCustomerId(customerId).stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .toList();
        long completedOrderCount = completedOrders.size();
        BigDecimal cumulativeSpend = completedOrders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(subtotal);
        BigDecimal couponDiscount = discountService.calculateDiscount(customer, couponCode, subtotal,
                completedOrderCount, cumulativeSpend);
        BigDecimal total = subtotal.add(shippingCost).subtract(couponDiscount);
        BigDecimal amountDue = total;

        BigDecimal giftCardAmount = BigDecimal.ZERO;
        if (giftCardCode != null && !giftCardCode.isBlank()) {
            giftCardAmount = giftCardService.previewGiftCard(giftCardCode, amountDue);
            amountDue = amountDue.subtract(giftCardAmount);
        }

        if (amountDue.compareTo(BigDecimal.ZERO) < 0) {
            amountDue = BigDecimal.ZERO;
        }

        Payment payment = paymentService.processPayment(paymentMethod, amountDue);
        if (payment.getStatus() == PaymentStatus.FAILED) {
            throw new PaymentFailedException("Payment failed. Order checkout could not be completed.");
        }

        productService.reduceStockForCheckout(quantities);
        if (giftCardAmount.compareTo(BigDecimal.ZERO) > 0) {
            giftCardService.redeemGiftCard(giftCardCode, giftCardAmount);
        }
        if (couponCode != null && !couponCode.isBlank() && couponDiscount.compareTo(BigDecimal.ZERO) > 0) {
            discountService.recordRedemption(couponCode, customerId);
        }

        String shippingAddress = customer.getAddress() == null ? "" : customer.getAddress().toString();
        String orderId = orderIdGenerator.nextId(orderRepository.findAll().stream()
                .map(Order::getOrderId)
                .toList());
        Order order = new Order(orderId, customerId, orderItems,
                subtotal, shippingCost, couponDiscount, total, shippingAddress, payment, giftCardAmount);
        orderRepository.add(order);
        cartService.clearCart(customerId);
        return order;
    }

    public List<Order> getOrdersByCustomer(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
    }

    public Order updateOrderStatus(String orderId, OrderStatus nextStatus) {
        Order order = getOrderById(orderId);
        if (nextStatus == OrderStatus.DISPATCHED && order.getTrackingInfo() == null) {
            throw new IllegalArgumentException("Assign courier and tracking information before dispatch");
        }
        trackingService.updateOrderStatus(order, nextStatus);
        if (nextStatus == OrderStatus.DELIVERED
                && order.getPayment() != null
                && "CASH_ON_DELIVERY".equals(order.getPayment().getPaymentMethod())) {
            order.getPayment().setStatus(PaymentStatus.SUCCESS);
        }
        if (nextStatus == OrderStatus.RETURNED || nextStatus == OrderStatus.CANCELLED) {
            restockOrder(order);
        }
        orderRepository.save(order);
        return order;
    }

    public Order assignTracking(String orderId, String courierName, String trackingNumber) {
        Order order = getOrderById(orderId);
        trackingService.assignTracking(order, courierName, trackingNumber);
        orderRepository.save(order);
        return order;
    }

    public Order cancelOrder(String customerId, String orderId) {
        Order order = getOrderById(orderId);
        requireOrderOwner(order, customerId);
        if (order.getStatus() != OrderStatus.PLACED) {
            throw new IllegalArgumentException("Only placed orders can be cancelled");
        }
        order.updateStatus(OrderStatus.CANCELLED);
        restockOrder(order);
        orderRepository.save(order);
        return order;
    }

    public Order requestReturn(String customerId, String orderId) {
        Order order = getOrderById(orderId);
        requireOrderOwner(order, customerId);
        if (order.getStatus() != OrderStatus.DELIVERED) {
            throw new IllegalArgumentException("Only delivered orders can be returned");
        }
        order.updateStatus(OrderStatus.RETURN_REQUESTED);
        trackingService.addTrackingEvent(order, "Customer requested a return");
        orderRepository.save(order);
        return order;
    }

    private void requireOrderOwner(Order order, String customerId) {
        if (!order.getCustomerId().equals(customerId)) {
            throw new IllegalArgumentException("You can only access your own order");
        }
    }

    private void restockOrder(Order order) {
        if (order.markInventoryRestocked()) {
            for (var item : order.getItems()) {
                productService.addStock(item.getProductId(), item.getQuantity());
            }
        }
    }
}
