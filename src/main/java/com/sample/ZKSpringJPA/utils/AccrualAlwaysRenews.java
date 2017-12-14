package com.sample.ZKSpringJPA.utils;

import org.joda.time.LocalDate;

import com.sample.ZKSpringJPA.entity.employment.Allowance;

import lombok.Data;

public @Data class AccrualAlwaysRenews implements AccrualFormula{
    private final double value = 1;
    private Allowance allowance;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate fromDate;

    @Override
    public double accrualToday() {
        return accrual();
    }

    @Override
    public double accrualEnd() {
        return accrual();
    }

    @Override
    public double accrual() {
        double balance = allowance.getEndBalance();
        if(LocalDate.now().isBefore(fromDate)){
            balance = 0;
        }
        return balance;
    }
}
