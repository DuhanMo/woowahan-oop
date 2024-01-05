package org.oop.food.billing.domain;

import org.oop.food.order.domain.OrderDeliveredEvent;
import org.oop.food.shop.domain.Shop;
import org.oop.food.shop.domain.ShopRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BillShopWithOrderDeliveredEventHandler {
    private final ShopRepository shopRepository;
    private final BillingRepository billingRepository;

    public BillShopWithOrderDeliveredEventHandler(ShopRepository shopRepository, BillingRepository billingRepository) {
        this.shopRepository = shopRepository;
        this.billingRepository = billingRepository;
    }

    @Async
    @EventListener
    @Transactional
    public void handle(OrderDeliveredEvent event) {
        Shop shop = shopRepository.findById(event.getShopId())
                .orElseThrow(IllegalArgumentException::new);
        Billing billing = billingRepository.findByShopId(shop.getId())
                .orElse(new Billing(event.getShopId()));
        billing.billCommissionFee(shop.calculateCommissionFee(event.getTotalPrice()));
    }
}
