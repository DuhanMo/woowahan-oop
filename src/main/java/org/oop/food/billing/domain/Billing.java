package org.oop.food.billing.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.oop.food.generic.money.domain.Money;
import org.oop.food.generic.money.infra.MoneyConverter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "BILLINGS")
@Getter
public class Billing {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "BILLING_ID")
    private Long id;

    @Column(name = "SHOP_ID")
    private Long shopId;

    @Column(name = "COMMISSION")
    @Convert(converter = MoneyConverter.class)
    private Money commission = Money.ZERO;

    public Billing(Long shopId) {
        this(null, shopId, Money.ZERO);
    }

    @Builder
    public Billing(Long id, Long shopId, Money commission) {
        this.id = id;
        this.shopId = shopId;
        this.commission = commission;
    }

    Billing() {
    }

    public void billCommissionFee(Money commission) {
        this.commission = this.commission.plus(commission);
    }
}