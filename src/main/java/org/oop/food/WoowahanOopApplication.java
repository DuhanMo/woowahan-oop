package org.oop.food;

import org.oop.food.common.money.domain.Money;
import org.oop.food.order.service.Cart;
import org.oop.food.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.oop.food.order.service.Cart.CartLineItem;
import static org.oop.food.order.service.Cart.CartOption;
import static org.oop.food.order.service.Cart.CartOptionGroup;

@SpringBootApplication
public class WoowahanOopApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(WoowahanOopApplication.class);

    private final OrderService orderService;

    public WoowahanOopApplication(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        Cart cart = new Cart(1L, 1L,
                new CartLineItem(1L, "삼겹살 1인세트", 2,
                        new CartOptionGroup("기본",
                                new CartOption("소(250g)", Money.wons(12000)))));

        LOGGER.info("===ORDER START");
        orderService.placeOrder(cart);

        orderService.payOrder(1L);

        orderService.deliverOrder(1L);

        LOGGER.info("===ORDER FINISH");
    }

    public static void main(String[] args) {
        LOGGER.info("STARTING THE APPLICATION");
        SpringApplication.run(WoowahanOopApplication.class, args);
        LOGGER.info("APPLICATION FINISHED");
    }
}
