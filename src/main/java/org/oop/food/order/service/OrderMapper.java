package org.oop.food.order.service;

import org.oop.food.order.domain.Order;
import org.oop.food.order.domain.OrderLineItem;
import org.oop.food.order.domain.OrderOption;
import org.oop.food.order.domain.OrderOptionGroup;
import org.oop.food.shop.domain.Menu;
import org.oop.food.shop.domain.MenuRepository;
import org.oop.food.shop.domain.Shop;
import org.oop.food.shop.domain.ShopRepository;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
public class OrderMapper {

    private final MenuRepository menuRepository;
    private final ShopRepository shopRepository;

    public OrderMapper(MenuRepository menuRepository, ShopRepository shopRepository) {
        this.menuRepository = menuRepository;
        this.shopRepository = shopRepository;
    }

    public Order mapFrom(Cart cart) {
        Shop shop = shopRepository.findById(cart.getShopId())
                .orElseThrow(IllegalArgumentException::new);

        return new Order(
                cart.getUserId(),
                shop,
                cart.getCartLineItems()
                        .stream()
                        .map(this::toOrderLineItem)
                        .collect(toList())
        );
    }

    private OrderLineItem toOrderLineItem(Cart.CartLineItem cartLineItem) {
        Menu menu = menuRepository.findById(cartLineItem.getMenuId())
                .orElseThrow(IllegalArgumentException::new);

        return new OrderLineItem(
                menu,
                cartLineItem.getName(),
                cartLineItem.getCount(),
                cartLineItem.getGroups()
                        .stream()
                        .map(this::toOrderOptionGroup)
                        .collect(toList())
        );
    }

    private OrderOptionGroup toOrderOptionGroup(Cart.CartOptionGroup cartOptionGroup) {
        return new OrderOptionGroup(
                cartOptionGroup.getName(),
                cartOptionGroup.getOptions()
                        .stream()
                        .map(this::toOrderOption)
                        .collect(toList())
        );
    }

    private OrderOption toOrderOption(Cart.CartOption cartOption) {
        return new OrderOption(
                cartOption.getName(),
                cartOption.getPrice()
        );
    }
}
