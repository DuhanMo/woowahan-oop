package org.oop.food.order.domain;

import org.oop.food.generic.money.domain.Money;

public class OrderDeliveredEvent {
    private final Order order;

    public OrderDeliveredEvent(Order order) {
        this.order = order;
    }

    public Long getOrderId() {
        return order.getId();
    }

    public Long getShopId() {
        return order.getShopId();
    }

    public Money getTotalPrice() {
        return order.calculateTotalPrice();
    }
}
