package org.oop.food.order.domain;

public class OrderPayedEvent {
    private final Order order;

    public OrderPayedEvent(Order order) {
        this.order = order;
    }

    public Long getOrderId() {
        return order.getId();
    }
}
