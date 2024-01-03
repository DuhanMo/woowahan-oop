package org.oop.food.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import org.oop.food.common.money.domain.Money;
import org.oop.food.shop.domain.OptionData;


@Embeddable
@Getter
public class OrderOption {

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Money price;

    @Builder
    public OrderOption(String name, Money price) {
        this.name = name;
        this.price = price;
    }

    public OrderOption() {
    }

    public OptionData convertToOptionData() {
        return new OptionData(name, price);
    }
}
