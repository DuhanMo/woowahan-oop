package org.oop.food.order.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.oop.food.common.money.domain.Money;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "ORDERS")
@Getter
public class Order {
    public enum OrderStatus {ORDERED, PAYED, DELIVERED}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "SHOP_ID")
    private Long shopId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    private final List<OrderLineItem> orderLineItems = new ArrayList<>();

    @Column(name = "ORDERED_TIME")
    private LocalDateTime orderedTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private OrderStatus orderStatus;

    public Order(Long userId, Long shopId, List<OrderLineItem> items) {
        this(null, userId, shopId, items, LocalDateTime.now(), null);
    }

    @Builder
    public Order(Long id, Long userId, Long shopId, List<OrderLineItem> items, LocalDateTime orderedTime, OrderStatus status) {
        this.id = id;
        this.userId = userId;
        this.shopId = shopId;
        this.orderedTime = orderedTime;
        this.orderStatus = status;
        this.orderLineItems.addAll(items);
    }

    Order() {
    }

    public void place(OrderValidator orderValidator) {
        orderValidator.validate(this);
        ordered();
    }

    public void payed() {
        orderStatus = OrderStatus.PAYED;
    }

    public void delivered() {
        orderStatus = OrderStatus.DELIVERED;
    }

    private void ordered() {
        orderStatus = OrderStatus.ORDERED;
    }

    public Money calculateTotalPrice() {
        return Money.sum(orderLineItems, OrderLineItem::calculatePrice);
    }

    public List<Long> getMenuIds() {
        return orderLineItems.stream().map(OrderLineItem::getMenuId).collect(toList());
    }
}
