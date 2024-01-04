package org.oop.food.order.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.oop.food.Fixtures.aShop;
import static org.oop.food.Fixtures.anOrder;

class OrderTest {
    @Test
    void 결제완료() {
        Order order = anOrder().status(Order.OrderStatus.ORDERED).build();

        order.payed();

        assertThat(order.getOrderStatus()).isEqualTo(Order.OrderStatus.PAYED);
    }

    @Test
    void 배송완료() {
        Order order = anOrder()
                .shopId(aShop().build().getId())
                .status(Order.OrderStatus.PAYED)
                .build();

        order.delivered();

        assertThat(order.getOrderStatus()).isEqualTo(Order.OrderStatus.DELIVERED);
    }
}