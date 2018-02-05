package com.sample.zkspring.viewmodel.simpletask.builder;

import lombok.Getter;
import lombok.Setter;

public class SecondScheduleBuilder implements ScheduleBuilder {

    @Getter @Setter
    private int second;
    @Override
    public String build() {
        return String.format("0 0/%s * 1/1 * ? *", second);
    }
}
