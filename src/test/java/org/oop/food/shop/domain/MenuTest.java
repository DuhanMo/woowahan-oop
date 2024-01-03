package org.oop.food.shop.domain;

import org.junit.jupiter.api.Test;
import org.oop.food.common.money.domain.Money;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.oop.food.Fixtures.aMenu;
import static org.oop.food.Fixtures.anOptionData;
import static org.oop.food.Fixtures.anOptionGroupData;
import static org.oop.food.Fixtures.anOptionGroupSpec;
import static org.oop.food.Fixtures.anOptionSpec;

class MenuTest {

    @Test
    void 메뉴이름_변경_오류() {
        Menu menu = aMenu().name("삼겹살").build();

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                menu.validateOrder("오겹살", Collections.singletonList(anOptionGroupData().build()))
        );

        assertThat(e.getMessage()).isEqualTo("기본 상품이 변경됐습니다.");
    }

    @Test
    void 옵션그룹이름_변경_오류() {
        Menu menu = aMenu().name("메뉴이름").basic(anOptionGroupSpec().name("기본").build()).build();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                menu.validateOrder("메뉴이름", Collections.singletonList(anOptionGroupData().name("변경 그룹 메뉴").build()))
        );

        assertThat(e.getMessage()).isEqualTo("메뉴가 변경됐습니다.");
    }

    @Test
    void 옵션이름_변경_오류() {
        Menu menu = aMenu()
                .name("메뉴이름")
                .basic(anOptionGroupSpec().options(Collections.singletonList(anOptionSpec().name("1인분").build()))
                        .build()
                ).build();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                menu.validateOrder("메뉴이름", Collections.singletonList(anOptionGroupData().optionDataList(Collections.singletonList(anOptionData().name("1.5인분").build())).build()))
        );

        assertThat(e.getMessage()).isEqualTo("메뉴가 변경됐습니다.");
    }

    @Test
    void 옵션가격_변경_오류() {
        Menu menu = aMenu()
                .name("메뉴이름")
                .basic(anOptionGroupSpec().options(Collections.singletonList(anOptionSpec().name("1인분").price(Money.wons(10000)).build()))
                        .build()
                ).build();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                menu.validateOrder("메뉴이름", Collections.singletonList(anOptionGroupData().optionDataList(Collections.singletonList(anOptionData().name("1인분").price(Money.wons(12000)).build())).build()))
        );

        assertThat(e.getMessage()).isEqualTo("메뉴가 변경됐습니다.");
    }
}