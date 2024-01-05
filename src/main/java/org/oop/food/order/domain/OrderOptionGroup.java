package org.oop.food.order.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.oop.food.generic.money.domain.Money;
import org.oop.food.shop.domain.OptionGroupData;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "ORDER_OPTION_GROUPS")
@Getter
public class OrderOptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_OPTION_GROUP_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ElementCollection
    @CollectionTable(name = "ORDER_OPTIONS", joinColumns = @JoinColumn(name = "ORDER_OPTION_GROUP_ID"))
    private List<OrderOption> orderOptions;

    public OrderOptionGroup(String name, List<OrderOption> options) {
        this(null, name, options);
    }

    @Builder
    public OrderOptionGroup(Long id, String name, List<OrderOption> options) {
        this.id = id;
        this.name = name;
        this.orderOptions = options;
    }

    OrderOptionGroup() {
    }

    public OptionGroupData convertToOptionGroupData() {
        return new OptionGroupData(name, orderOptions.stream().map(OrderOption::convertToOptionData).collect(toList()));
    }

    public Money calculatePrice() {
        return Money.sum(orderOptions, OrderOption::getPrice);
    }
}
