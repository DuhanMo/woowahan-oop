package org.oop.food;

import org.oop.food.common.money.domain.Money;
import org.oop.food.common.money.domain.Ratio;
import org.oop.food.delivery.domain.Delivery;
import org.oop.food.order.domain.Order;
import org.oop.food.order.domain.OrderLineItem;
import org.oop.food.order.domain.OrderOption;
import org.oop.food.order.domain.OrderOptionGroup;
import org.oop.food.shop.domain.Menu;
import org.oop.food.shop.domain.OptionData;
import org.oop.food.shop.domain.OptionGroupData;
import org.oop.food.shop.domain.OptionGroupSpecification;
import org.oop.food.shop.domain.OptionSpecification;
import org.oop.food.shop.domain.Shop;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.oop.food.delivery.domain.Delivery.DeliveryBuilder;
import static org.oop.food.delivery.domain.Delivery.DeliveryStatus;
import static org.oop.food.order.domain.Order.OrderBuilder;
import static org.oop.food.order.domain.OrderLineItem.OrderLineItemBuilder;
import static org.oop.food.order.domain.OrderOption.OrderOptionBuilder;
import static org.oop.food.order.domain.OrderOptionGroup.OrderOptionGroupBuilder;
import static org.oop.food.shop.domain.Menu.MenuBuilder;
import static org.oop.food.shop.domain.OptionData.OptionDataBuilder;
import static org.oop.food.shop.domain.OptionGroupData.OptionGroupDataBuilder;
import static org.oop.food.shop.domain.OptionGroupSpecification.OptionGroupSpecificationBuilder;
import static org.oop.food.shop.domain.OptionSpecification.OptionSpecificationBuilder;
import static org.oop.food.shop.domain.Shop.ShopBuilder;

public class Fixtures {
    public static ShopBuilder aShop() {
        return Shop.builder()
                .id(1L)
                .name("오겹돼지")
                .commissionRate(Ratio.valueOf(0.01))
                .open(true)
                .minOrderAmount(Money.wons(13000))
                .commission(Money.ZERO);
    }

    public static MenuBuilder aMenu() {
        return Menu.builder()
                .id(1L)
                .shopId(aShop().build().getId())
                .name("삼겹살 1인세트")
                .description("삼겹살 + 야채세트 + 김치찌개")
                .basic(anOptionGroupSpec()
                        .name("기본")
                        .options(Collections.singletonList(anOptionSpec().name("소(250g)").price(Money.wons(12000)).build()))
                        .build())
                .additives(Collections.singletonList(
                        anOptionGroupSpec()
                                .basic(false)
                                .name("맛선택")
                                .options(Collections.singletonList(anOptionSpec().name("매콤 맛").price(Money.wons(1000)).build()))
                                .build()));
    }

    public static OptionGroupSpecificationBuilder anOptionGroupSpec() {
        return OptionGroupSpecification.builder()
                .basic(true)
                .exclusive(true)
                .name("기본")
                .options(Collections.singletonList(anOptionSpec().build()));
    }

    public static OptionSpecificationBuilder anOptionSpec() {
        return OptionSpecification.builder()
                .name("소(250g)")
                .price(Money.wons(12000));
    }

    public static OptionGroupDataBuilder anOptionGroupData() {
        return OptionGroupData.builder()
                .name("기본")
                .optionDataList(Collections.singletonList(anOptionData().build()));
    }

    public static OptionDataBuilder anOptionData() {
        return OptionData.builder()
                .name("소(250g)")
                .price(Money.wons(12000));
    }

    public static OrderBuilder anOrder() {
        return Order.builder()
                .id(1L)
                .userId(1L)
                .shopId(aShop().build().getId())
                .status(Order.OrderStatus.ORDERED)
                .orderedTime(LocalDateTime.of(2020, 1, 1, 12, 0))
                .items(Collections.singletonList(anOrderLineItem().build()));
    }

    public static OrderLineItemBuilder anOrderLineItem() {
        return OrderLineItem.builder()
                .menuId(aMenu().build().getId())
                .name("삼겹살 1인세트")
                .count(1)
                .groups(Arrays.asList(
                        anOrderOptionGroup()
                                .name("기본")
                                .options(Collections.singletonList(anOrderOption().name("소(250g)").price(Money.wons(12000)).build()))
                                .build(),
                        anOrderOptionGroup()
                                .name("맛선택")
                                .options(Collections.singletonList(anOrderOption().name("매콤 맛").price(Money.wons(1000)).build()))
                                .build()));
    }

    public static OrderOptionGroupBuilder anOrderOptionGroup() {
        return OrderOptionGroup.builder()
                .name("기본")
                .options(Collections.singletonList(anOrderOption().build()));
    }

    public static OrderOptionBuilder anOrderOption() {
        return OrderOption.builder()
                .name("소(250g)")
                .price(Money.wons(12000));
    }

    public static DeliveryBuilder aDelivery() {
        return Delivery.builder()
                .id(1L)
                .deliveryStatus(DeliveryStatus.DELIVERING)
                .orderId(anOrder().build().getId());
    }
}