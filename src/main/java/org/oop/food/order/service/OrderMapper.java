package org.oop.food.order.service;

import org.oop.food.order.domain.Order;
import org.oop.food.order.domain.OrderLineItem;
import org.oop.food.order.domain.OrderOption;
import org.oop.food.order.domain.OrderOptionGroup;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
public class OrderMapper {
    public Order mapFrom(Cart cart) {
        return new Order(
                cart.getUserId(),
                cart.getShopId(),
                cart.getCartLineItems()
                        .stream()
                        .map(this::toOrderLineItem)
                        .collect(toList()));
    }

    private OrderLineItem toOrderLineItem(Cart.CartLineItem cartLineItem) {
        return new OrderLineItem(
                cartLineItem.getMenuId(),
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
