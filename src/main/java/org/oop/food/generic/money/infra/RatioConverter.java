package org.oop.food.generic.money.infra;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.oop.food.generic.money.domain.Ratio;

@Converter
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
