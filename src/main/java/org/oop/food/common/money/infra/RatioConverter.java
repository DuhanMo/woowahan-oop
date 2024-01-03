package org.oop.food.common.money.infra;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.oop.food.common.money.domain.Ratio;

@Converter(autoApply = true)
public class RatioConverter implements AttributeConverter<Ratio, Double> {
    @Override
    public Double convertToDatabaseColumn(Ratio ratio) {
        return ratio.getRate();
    }

    @Override
    public Ratio convertToEntityAttribute(Double rate) {
        return Ratio.valueOf(rate);
    }
}
