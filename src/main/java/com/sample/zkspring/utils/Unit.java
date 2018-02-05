package com.sample.zkspring.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum Unit {
    DAY("Day", 1),
    MONTH("Month", 30),
    YEAR("Year", 365);

    @Getter @Setter
    private String name;

    @Getter @Setter
    private double value;
}
