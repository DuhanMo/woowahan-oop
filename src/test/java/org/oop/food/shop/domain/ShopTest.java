package org.oop.food.shop.domain;

import org.junit.jupiter.api.Test;
import org.oop.food.common.money.domain.Money;
import org.oop.food.common.money.domain.Ratio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.oop.food.Fixtures.aShop;

class ShopTest {

    @Test
    void 최소주문금액_체크() {
        Shop shop = aShop().minOrderAmount(Money.wons(15000)).build();

        assertThat(shop.isValidOrderAmount(Money.wons(14000))).isFalse();
        assertThat(shop.isValidOrderAmount(Money.wons(15000))).isTrue();
        assertThat(shop.isValidOrderAmount(Money.wons(16000))).isTrue();
    }

    @Test
    void 수수료_부과() {
        Shop shop = aShop()
                .commissionRate(Ratio.valueOf(0.1))
                .commission(Money.ZERO)
                .build();

        shop.billCommissionFee(Money.wons(1000));

        assertThat(shop.getCommission()).isEqualTo(Money.wons(100));
    }
}