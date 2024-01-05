package org.oop.food.delivery.domain;

import org.oop.food.order.domain.OrderDeliveredEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CompleteDeliveryWithOrderDeliveredEventHandler {
    private final DeliveryRepository deliveryRepository;

    public CompleteDeliveryWithOrderDeliveredEventHandler(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Async
    @EventListener
    @Transactional
    public void handle(OrderDeliveredEvent event) {
        Delivery delivery = deliveryRepository.findByOrderId(event.getOrderId()).orElseThrow(IllegalArgumentException::new);
        delivery.complete();
    }
}
