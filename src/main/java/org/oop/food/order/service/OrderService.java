package org.oop.food.order.service;

import org.oop.food.order.domain.Order;
import org.oop.food.order.domain.OrderDeliveredService;
import org.oop.food.order.domain.OrderPayedService;
import org.oop.food.order.domain.OrderRepository;
import org.oop.food.order.domain.OrderValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderMapper orderMapper;
    private final OrderDeliveredService orderDeliveredService;
    private final OrderPayedService orderPayedService;

    public OrderService(OrderRepository orderRepository, OrderValidator orderValidator, OrderMapper orderMapper, OrderDeliveredService orderDeliveredService, OrderPayedService orderPayedService) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderMapper = orderMapper;
        this.orderDeliveredService = orderDeliveredService;
        this.orderPayedService = orderPayedService;
    }

    @Transactional
    public void placeOrder(Cart cart) {
        Order order = orderMapper.mapFrom(cart);
        order.place(orderValidator);
        orderRepository.save(order);
    }

    @Transactional
    public void payOrder(Long orderId) {
        orderPayedService.payOrder(orderId);
    }

    @Transactional
    public void deliverOrder(Long orderId) {
        orderDeliveredService.deliverOrder(orderId);
    }
}
