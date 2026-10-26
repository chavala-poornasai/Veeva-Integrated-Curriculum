package com.ecommerce.enums;

public enum OrderStatus {
    PLACED,
    DISPATCHED,
    IN_TRANSIT,
    DELIVERED,
    RETURN_REQUESTED,
    RETURNED,
    CANCELLED;

    public boolean canTransitionTo(OrderStatus nextStatus) {
        if (this == nextStatus) {
            return true;
        }
        return switch (this) {
            case PLACED -> nextStatus == DISPATCHED || nextStatus == CANCELLED;
            case DISPATCHED -> nextStatus == IN_TRANSIT;
            case IN_TRANSIT -> nextStatus == DELIVERED || nextStatus == RETURN_REQUESTED;
            case DELIVERED -> nextStatus == RETURN_REQUESTED;
            case RETURN_REQUESTED -> nextStatus == RETURNED;
            case RETURNED, CANCELLED -> false;
        };
    }
}
