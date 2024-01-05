package org.oop.food.order.service;

import org.oop.food.order.domain.Order;
import org.oop.food.order.domain.OrderRepository;
import org.oop.food.order.domain.OrderValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderValidator orderValidator, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public void placeOrder(Cart cart) {
        Order order = orderMapper.mapFrom(cart);
        order.place(orderValidator);
        orderRepository.save(order);
    }

    @Transactional
    public void payOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        order.payed();
        orderRepository.save(order); // Domain Event 발행
    }

    @Transactional
    public void deliverOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        order.delivered();
        orderRepository.save(order); // Domain Event 발행
    }
}
