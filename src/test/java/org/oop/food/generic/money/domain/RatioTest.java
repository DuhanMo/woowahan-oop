package org.oop.food.generic.money.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RatioTest {

    @Test
    void 퍼센트() {
        Ratio tenPercent = Ratio.valueOf(0.1);
        Money thousandWon = Money.wons(1000);

        assertThat(tenPercent.of(thousandWon)).isEqualTo(Money.wons(100));
    }
}