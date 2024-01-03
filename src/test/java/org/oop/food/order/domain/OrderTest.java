package org.oop.food.order.domain;

import org.junit.jupiter.api.Test;
import org.oop.food.common.money.domain.Money;
import org.oop.food.common.money.domain.Ratio;
import org.oop.food.shop.domain.Shop;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.oop.food.Fixtures.aShop;
import static org.oop.food.Fixtures.anOrder;
import static org.oop.food.Fixtures.anOrderLineItem;
import static org.oop.food.Fixtures.anOrderOption;
import static org.oop.food.Fixtures.anOrderOptionGroup;

class OrderTest {

    @Test
    void 가게_미영업시_주문실패() {
        Order order = anOrder().shop(aShop().open(false).build()).build();

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> order.place());
        assertThat(e.getMessage()).isEqualTo("가게가 영업중이 아닙니다.");
    }

    @Test
    void 결제완료() {
        Order order = anOrder().status(Order.OrderStatus.ORDERED).build();

        order.payed();

        assertThat(order.getOrderStatus()).isEqualTo(Order.OrderStatus.PAYED);
    }

    @Test
    void 배송완료() {
        Shop shop = aShop()
                .commissionRate(Ratio.valueOf(0.02))
                .commission(Money.ZERO)
                .build();

        Order order = anOrder()
                .shop(shop)
                .status(Order.OrderStatus.PAYED)
                .items(Collections.singletonList(
                        anOrderLineItem()
                                .count(1)
                                .groups(Collections.singletonList(
                                        anOrderOptionGroup()
                                                .options(Collections.singletonList(
                                                        anOrderOption()
                                                                .price(Money.wons(10000))
                                                                .build())).build())).build())).build();

        order.delivered();

        assertThat(order.getOrderStatus()).isEqualTo(Order.OrderStatus.DELIVERED);
        assertThat(shop.getCommission()).isEqualTo(Money.wons(200));
    }
}