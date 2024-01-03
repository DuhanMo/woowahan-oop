package org.oop.food.order.service;

import org.oop.food.delivery.domain.Delivery;
import org.oop.food.delivery.domain.DeliveryRepository;
import org.oop.food.order.domain.Order;
import org.oop.food.order.domain.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository,
                        DeliveryRepository deliveryRepository,
                        OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.deliveryRepository = deliveryRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public void placeOrder(Cart cart) {
        Order order = orderMapper.mapFrom(cart);
        order.place();
        orderRepository.save(order);
    }

    @Transactional
    public void payOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        order.payed();

        Delivery delivery = Delivery.started(order);
        deliveryRepository.save(delivery);
    }

    @Transactional
    public void deliverOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        order.delivered();

        Delivery delivery = deliveryRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        delivery.complete();
    }
}
