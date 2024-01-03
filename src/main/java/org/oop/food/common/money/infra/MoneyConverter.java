package org.oop.food.common.money.infra;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.oop.food.common.money.domain.Money;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, Long> {
    @Override
    public Long convertToDatabaseColumn(Money money) {
        return money.getAmount().longValue();
    }

    @Override
    public Money convertToEntityAttribute(Long amount) {
        return Money.wons(amount);
    }
}