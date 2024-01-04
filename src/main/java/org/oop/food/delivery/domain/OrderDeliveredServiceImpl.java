package org.oop.food.delivery.domain;

import org.oop.food.order.domain.Order;
import org.oop.food.order.domain.OrderDeliveredService;
import org.oop.food.order.domain.OrderPayedService;
import org.oop.food.order.domain.OrderRepository;
import org.oop.food.shop.domain.Shop;
import org.oop.food.shop.domain.ShopRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderDeliveredServiceImpl implements OrderPayedService, OrderDeliveredService {
    private final OrderRepository orderRepository;
    private final ShopRepository shopRepository;
    private final DeliveryRepository deliveryRepository;

    public OrderDeliveredServiceImpl(OrderRepository orderRepository, ShopRepository shopRepository, DeliveryRepository deliveryRepository) {
        this.orderRepository = orderRepository;
        this.shopRepository = shopRepository;
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public void payOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        order.payed();

        Delivery delivery = Delivery.started(orderId);
        deliveryRepository.save(delivery);
    }

    @Override
    public void deliverOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        Shop shop = shopRepository.findById(order.getShopId()).orElseThrow(IllegalArgumentException::new);
        Delivery delivery = deliveryRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);

        order.delivered();
        shop.billCommissionFee(order.calculateTotalPrice());
        delivery.complete();
    }
}
