package org.oop.food.order.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.oop.food.generic.money.domain.Money;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDER_LINE_ITEMS")
@Getter
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_LINE_ITEM_ID")
    private Long id;

    @Column(name = "MENU_ID")
    private Long menuId;

    @Column(name = "FOOD_NAME")
    private String name;

    @Column(name = "FOOD_COUNT")
    private int count;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_LINE_ITEM_ID")
    private final List<OrderOptionGroup> groups = new ArrayList<>();

    public OrderLineItem(Long menuId, String name, int count, List<OrderOptionGroup> groups) {
        this(null, menuId, name, count, groups);
    }

    @Builder
    public OrderLineItem(Long id, Long menuId, String name, int count, List<OrderOptionGroup> groups) {
        this.id = id;
        this.menuId = menuId;
        this.name = name;
        this.count = count;
        this.groups.addAll(groups);
    }

    OrderLineItem() {
    }

    public Money calculatePrice() {
        return Money.sum(groups, OrderOptionGroup::calculatePrice).times(count);
    }
}
