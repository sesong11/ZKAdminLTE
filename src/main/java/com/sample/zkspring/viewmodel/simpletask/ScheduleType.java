package com.sample.zkspring.viewmodel.simpletask;

import lombok.Getter;
import lombok.Setter;

public enum ScheduleType {
    SECOND(0, "Second", "/view/simple-task/second-scheduler.zul"),
    MINUTE(1, "Minute", ""),
    HOURLY(2, "Hourly", ""),
    DAILY(3, "Daily", ""),
    WEEKLY(4, "Weekly", ""),
    Monthly(6, "Monthly", ""),
    YEARLY(7, "Yearly", "");

    @Getter @Setter
    private String name;

    @Getter @Setter
    private int value;

    @Getter @Setter
    private String view;

    ScheduleType(final int value, final String name, final String view) {
        this.value = value;
        this.name = name;
        this.view = view;
    }
}
