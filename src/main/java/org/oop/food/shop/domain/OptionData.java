package org.oop.food.shop.domain;

import lombok.Builder;
import lombok.Data;
import org.oop.food.common.money.domain.Money;

@Data
public class OptionData {
    private String name;
    private Money price;

    @Builder
    public OptionData(String name, Money price) {
        this.name = name;
        this.price = price;
    }
}
