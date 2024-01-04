package org.oop.food.delivery.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oop.food.common.money.domain.Money;
import org.oop.food.common.money.domain.Ratio;
import org.oop.food.order.domain.Order;
import org.oop.food.order.domain.OrderDeliveredService;
import org.oop.food.order.domain.OrderRepository;
import org.oop.food.shop.domain.Shop;
import org.oop.food.shop.domain.ShopRepository;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.oop.food.Fixtures.aDelivery;
import static org.oop.food.Fixtures.aShop;
import static org.oop.food.Fixtures.anOrder;
import static org.oop.food.Fixtures.anOrderLineItem;
import static org.oop.food.Fixtures.anOrderOption;
import static org.oop.food.Fixtures.anOrderOptionGroup;

@ExtendWith(MockitoExtension.class)
public class OrderDeliveredServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ShopRepository shopRepository;
    @Mock
    private DeliveryRepository deliveryRepository;

    private OrderDeliveredService orderDeliveredService;

    @BeforeEach
    public void setUp() {
        orderDeliveredService = new OrderDeliveredServiceImpl(orderRepository, shopRepository, deliveryRepository);
    }

    @Test
    public void 주문완료() {
        Shop shop = aShop()
                .commissionRate(Ratio.valueOf(0.01))
                .commission(Money.wons(1000))
                .build();
        Order order = anOrder().items(Collections.singletonList(
                        anOrderLineItem().groups(Collections.singletonList(
                                        anOrderOptionGroup().options(Collections.singletonList(
                                                anOrderOption().price(Money.wons(10000)).build())).build()))
                                .build()))
                .build();

        Delivery delivery = aDelivery().build();

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(shopRepository.findById(shop.getId())).thenReturn(Optional.of(shop));
        when(deliveryRepository.findById(delivery.getId())).thenReturn(Optional.of(delivery));

        orderDeliveredService.deliverOrder(order.getId());

        assertThat(order.getOrderStatus()).isEqualTo(Order.OrderStatus.DELIVERED);
        assertThat(delivery.getDeliveryStatus()).isEqualTo(Delivery.DeliveryStatus.DELIVERED);
        assertThat(shop.getCommission()).isEqualTo(Money.wons(1100));
    }
}
