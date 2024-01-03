package org.oop.food.shop.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class OptionGroupData {
    private String name;
    private List<OptionData> optionDataList;

    @Builder
    public OptionGroupData(String name, List<OptionData> optionDataList) {
        this.name = name;
        this.optionDataList = optionDataList;
    }
}

