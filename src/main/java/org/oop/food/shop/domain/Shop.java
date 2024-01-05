package org.oop.food.shop.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.oop.food.generic.money.domain.Money;
import org.oop.food.generic.money.domain.Ratio;
import org.oop.food.generic.money.infra.MoneyConverter;
import org.oop.food.generic.money.infra.RatioConverter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "SHOPS")
@Getter
public class Shop {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "SHOP_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "OPEN")
    private boolean open;

    @Column(name = "MIN_ORDER_AMOUNT")
    @Convert(converter = MoneyConverter.class)
    private Money minOrderAmount;

    @Column(name = "COMMISSION_RATE")
    @Convert(converter = RatioConverter.class)
    private Ratio commissionRate;

    public Shop(String name, boolean open, Money minOrderAmount) {
        this(name, open, minOrderAmount, Ratio.valueOf(0), Money.ZERO);
    }

    public Shop(String name, boolean open, Money minOrderAmount, Ratio commissionRate, Money commission) {
        this(null, name, open, minOrderAmount, commissionRate, commission);
    }

    @Builder
    public Shop(Long id, String name, boolean open, Money minOrderAmount, Ratio commissionRate, Money commission) {
        this.id = id;
        this.name = name;
        this.open = open;
        this.minOrderAmount = minOrderAmount;
        this.commissionRate = commissionRate;
    }

    protected Shop() {
    }

    public boolean isValidOrderAmount(Money amount) {
        return amount.isGreaterThanOrEqual(minOrderAmount);
    }

    public Money calculateCommissionFee(Money price) {
        return commissionRate.of(price);
    }
}
