package com.sample.ZKSpringJPA.utils;

import org.joda.time.LocalDate;
import java.util.Calendar;

import com.sample.ZKSpringJPA.entity.employment.Allowance;

import lombok.Data;

public @Data class AccrualNeverRenew implements AccrualFormula {
    private final double value = 1000000;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate fromDate;
    private Allowance allowance;

    @Override
    public double accrual() {
        double balance;
        switch (allowance.getFrequencyAccrual()){
        case NEVER:
            balance = allowance.getEndBalance();
            break;
        case ALWAYS:
            balance = allowance.getEndBalance();
            break;
        default:
            int dayBetween = (int)Calculator.daysBetween(startDate.toDate(), endDate.toDate());
            balance = allowance.getAccrualBalance() * (int)(dayBetween/(allowance.getFrequencyAccrual().getValue()*allowance.getNFrequencyAccrual()));
            break;
        }
        LocalDate now = LocalDate.now();
        if(now.isBefore(fromDate)){
            balance = 0;
        }
        return balance;
    }

    @Override
    public double accrualToday() {
        Calendar start = Calendar.getInstance();
        start.setTime(fromDate.toDate());
        startDate.fromCalendarFields(start);

        Calendar end = Calendar.getInstance();
        switch (allowance.getFrequencyAccrual()){
        case YEARLY:
            end.set(Calendar.DAY_OF_YEAR, 0);
            break;
        case MONTHLY:
            end.set(Calendar.DAY_OF_MONTH, 0);
            break;
        case WEEKLY:
            end.set(Calendar.DAY_OF_WEEK, 0);
            break;
        case NEVER:
            break;
        case ALWAYS:
            break;
        case DAILY:
            break;
        default:
            break;
        }

        endDate.fromCalendarFields(end);

        double accrual = accrual();
        return accrual;
    }

    @Override
    public double accrualEnd() {
        Calendar start = Calendar.getInstance();
        start.setTime(fromDate.toDate());
        startDate = LocalDate.fromCalendarFields(start);

        Calendar end = Calendar.getInstance();
        switch (allowance.getFrequencyAccrual()) {
        case YEARLY:
            end.set(Calendar.DAY_OF_YEAR, 0);
            break;
        case MONTHLY:
            end.set(Calendar.DAY_OF_MONTH, 0);
            break;
        case WEEKLY:
            end.set(Calendar.DAY_OF_WEEK, 0);
            break;
        case NEVER:
            break;
        case ALWAYS:
            break;
        case DAILY:
            break;
        default:
            break;
        }
        endDate = LocalDate.fromCalendarFields(end);

        double accrual = accrual();
        return accrual;
    }
}