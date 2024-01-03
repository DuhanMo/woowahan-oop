package org.oop.food.shop.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "MENUS")
@Getter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ID")
    private Long id;

    @Column(name = "FOOD_NAME")
    private String name;

    @Column(name = "FOOD_DESCRIPTION")
    private String description;

    @OneToOne
    @JoinColumn(name = "MENU_ID")
    private Shop shop;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "MENU_ID")
    private final List<OptionGroupSpecification> optionGroupSpecs = new ArrayList<>();

    public Menu(Shop shop, String name, String description, OptionGroupSpecification basic, OptionGroupSpecification... groups) {
        this(null, shop, name, description, basic, Arrays.asList(groups));
    }

    @Builder
    public Menu(Long id, Shop shop, String name, String description, OptionGroupSpecification basic, List<OptionGroupSpecification> additives) {
        this.id = id;
        this.shop = shop;
        this.name = name;
        this.description = description;

        this.shop.addMenu(this);
        this.optionGroupSpecs.add(basic);
        this.optionGroupSpecs.addAll(additives);
    }

    Menu() {
    }

    public void validateOrder(String menuName, List<OptionGroupData> optionGroupDataList) {
        if (!name.equals(menuName)) {
            throw new IllegalArgumentException("기본 상품이 변경됐습니다.");
        }
        if (!isSatisfiedBy(optionGroupDataList)) {
            throw new IllegalArgumentException("메뉴가 변경됐습니다.");
        }
    }

    private boolean isSatisfiedBy(List<OptionGroupData> cartOptionGroupDataList) {
        return cartOptionGroupDataList.stream().anyMatch(this::isSatisfiedBy);
    }

    private boolean isSatisfiedBy(OptionGroupData optionGroupData) {
        return optionGroupSpecs.stream().anyMatch(spec -> spec.isSatisfiedBy(optionGroupData));
    }
}
