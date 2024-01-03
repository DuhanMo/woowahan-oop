package org.oop.food.shop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.oop.food.common.money.domain.Money;

import java.util.Objects;

@Entity
@Table(name = "OPTION_SPECS")
@Getter
public class OptionSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPTION_SPEC_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Money price;

    public OptionSpecification(String name, Money price) {
        this(null, name, price);
    }

    @Builder
    public OptionSpecification(Long id, String name, Money price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    OptionSpecification() {
    }

    public boolean isSatisfiedBy(OptionData optionData) {
        return Objects.equals(name, optionData.getName()) && Objects.equals(price, optionData.getPrice());
    }
}
