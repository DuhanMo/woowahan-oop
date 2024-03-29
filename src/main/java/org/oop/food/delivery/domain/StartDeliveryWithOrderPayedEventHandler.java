package org.oop.food.delivery.domain;

import org.oop.food.order.domain.OrderPayedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StartDeliveryWithOrderPayedEventHandler {
    private final DeliveryRepository deliveryRepository;

    public StartDeliveryWithOrderPayedEventHandler(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Async
    @EventListener
    @Transactional
    public void handle(OrderPayedEvent event) {
        Delivery delivery = Delivery.started(event.getOrderId());
        deliveryRepository.save(delivery);
    }
}
