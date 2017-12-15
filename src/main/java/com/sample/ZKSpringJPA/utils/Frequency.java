package com.sample.ZKSpringJPA.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor()
public enum Frequency {
    NEVER("Never", new AccrualNeverRenew()),
    ALWAYS("Always", new AccrualAlwaysRenews()),
    DAILY("Daily", new AccrualDefault()),
    WEEKLY("Weekly", new AccrualDefault()),
    MONTHLY("Monthly", new AccrualMonthly()),
    YEARLY("Yearly", new AccrualYearlyRenew());

    @Getter
    private String name;

    public double getValue(){
        return accrualFormula.getValue();
    }

    @Getter
    private AccrualFormula accrualFormula;
}
