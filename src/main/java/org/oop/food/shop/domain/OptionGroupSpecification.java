package org.oop.food.shop.domain;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "OPTION_GROUP_SPECS")
@Getter
public class OptionGroupSpecification {

    @Id
    @GeneratedValue
    @Column(name = "OPTION_GROUP_SPEC_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EXCLUSIVE")
    private boolean exclusive;

    @Column(name = "BASIC")
    private boolean basic;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "OPTION_GROUP_SPEC_ID")
    private final List<OptionSpecification> optionSpecs = new ArrayList<>();

    public OptionGroupSpecification(String name, boolean exclusive, boolean basic, OptionSpecification... options) {
        this(null, name, exclusive, basic, Arrays.asList(options));
    }

    @Builder
    public OptionGroupSpecification(Long id, String name, boolean exclusive, boolean basic, List<OptionSpecification> options) {
        this.id = id;
        this.name = name;
        this.exclusive = exclusive;
        this.basic = basic;
        this.optionSpecs.addAll(options);
    }

    OptionGroupSpecification() {
    }

    public boolean isSatisfiedBy(OptionGroupData optionGroupData) {
        return isSatisfiedBy(optionGroupData.getName(), satisfied(optionGroupData.getOptionDataList()));
    }

    private boolean isSatisfiedBy(String groupName, List<OptionData> satisfied) {
        if (!name.equals(groupName)) {
            return false;
        }
        if (satisfied.isEmpty()) {
            return false;
        }
        return !exclusive || satisfied.size() <= 1;
    }

    private List<OptionData> satisfied(List<OptionData> optionDataList) {
        return optionSpecs
                .stream()
                .flatMap(spec -> optionDataList.stream().filter(spec::isSatisfiedBy))
                .collect(toList());
    }
}
