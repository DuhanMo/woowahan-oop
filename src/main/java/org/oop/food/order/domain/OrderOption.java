package org.oop.food.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import org.oop.food.generic.money.domain.Money;
import org.oop.food.generic.money.infra.MoneyConverter;
import org.oop.food.shop.domain.OptionData;


@Embeddable
@Getter
public class OrderOption {

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    @Convert(converter = MoneyConverter.class)
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
