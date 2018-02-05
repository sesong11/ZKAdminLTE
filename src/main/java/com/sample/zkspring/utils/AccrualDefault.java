package com.sample.zkspring.utils;

import org.joda.time.LocalDate;

import com.sample.zkspring.entity.employment.Allowance;

import lombok.Data;

public @Data class AccrualDefault implements AccrualFormula {
    private final double value = 1;
    private Allowance allowance;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate fromDate;

    @Override public double accrualToday() {
        return 0;
    }

    @Override public double accrualEnd() {
        return 0;
    }

    @Override
    public double accrual() {
        return 0;
    }
}
