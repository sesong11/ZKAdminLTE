package com.sample.zkspring.utils;

import org.joda.time.LocalDate;
import java.util.Calendar;

import com.sample.zkspring.entity.employment.Allowance;

import lombok.Data;

public @Data class AccrualYearlyRenew implements AccrualFormula {
    private final double value = 365;
    private Allowance allowance;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate fromDate;

    @Override
    public double accrual() {
        double balance = 0;
        if(!startDate.isBefore(endDate) ||
                (startDate.getMonthOfYear() == endDate.getMonthOfYear() && startDate.getYear() == endDate.getYear())){
            return 0;
        }
        switch (allowance.getFrequencyAccrual()) {
        case YEARLY:
            balance = allowance.getAccrualBalance() / allowance.getNoFrequencyAccrual();
            break;
        case MONTHLY:
            Calendar start = Calendar.getInstance();
            start.setTime(startDate.toDate());
            int dayOfMonth = Calculator.daysOfMonth(start);
            int startDay = start.get(Calendar.DAY_OF_MONTH);

            if(startDay>1){
                start.set(Calendar.MONTH, start.get(Calendar.MONTH) + 1);
                start.set(Calendar.DAY_OF_MONTH, 1);
                if(dayOfMonth*allowance.getAccrualOn()>=startDay){
                    balance = balance + allowance.getAccrualBalance();
                }
            }
            Calendar end = Calendar.getInstance();
            end.setTime(endDate.toDate());
            dayOfMonth = Calculator.daysOfMonth(end);
            int endDay = end.get(Calendar.DAY_OF_MONTH);

            if(endDay<=dayOfMonth){
                end.set(Calendar.MONTH, end.get(Calendar.MONTH) -1 );
                end.set(Calendar.DAY_OF_MONTH, Calculator.daysOfMonth(end));
                if(endDay<dayOfMonth && dayOfMonth*allowance.getAccrualOn()<endDay){
                    balance = balance + allowance.getAccrualBalance();
                }
            }
            int dayBetween = (int)Calculator.daysBetween(start, end)+1;
            int accrualCount = (int)(dayBetween/Frequency.MONTHLY.getValue()+0.5);
            balance = balance + allowance.getAccrualBalance() * accrualCount;
            break;
        case NEVER:
            balance = allowance.getEndBalance();
            break;
        case WEEKLY:
            balance = allowance.getAccrualBalance() * 52.14;
            break;
        case DAILY:
            balance = allowance.getAccrualBalance() * value;
            break;
        case ALWAYS:
            balance = allowance.getEndBalance();
            break;
        default:
            balance = 0;
            break;
        }
        return balance;
    }

    @Override
    public double accrualToday() {
        Calendar start = Calendar.getInstance();
        switch (allowance.getFrequencyRenew()) {
        case YEARLY:
            start.set(Calendar.DAY_OF_YEAR, 1);
            start.set(Calendar.YEAR, start.get(Calendar.YEAR)-allowance.getNoFrequencyRenew()+1);
            break;
        case MONTHLY:
            start.set(Calendar.DAY_OF_MONTH, 1);
            start.set(Calendar.MONTH, start.get(Calendar.MONTH)-allowance.getNoFrequencyRenew()+1);
            break;
        }

        if(!fromDate.isBefore(startDate)){
            start.setTime(fromDate.toDate());
        }
        startDate = LocalDate.fromCalendarFields(start);
        endDate = LocalDate.now();
        return accrual();
    }
    @Override
    public double accrualEnd() {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        switch (allowance.getFrequencyRenew()) {
        case YEARLY:
            start.set(Calendar.DAY_OF_YEAR, 1);
            start.set(Calendar.YEAR, start.get(Calendar.YEAR)-allowance.getNoFrequencyRenew()+1);
            end.set(Calendar.DAY_OF_YEAR, Calculator.daysOfYear(end));
            break;
        case MONTHLY:
            start.set(Calendar.DAY_OF_MONTH, 1);
            start.set(Calendar.MONTH, start.get(Calendar.MONTH)-allowance.getNoFrequencyRenew()+1);
            end.set(Calendar.DAY_OF_MONTH, Calculator.daysOfMonth(end));
            break;
        case WEEKLY:
            start.set(Calendar.DAY_OF_WEEK, 1);
            start.set(Calendar.WEEK_OF_MONTH, start.get(Calendar.WEEK_OF_MONTH)-allowance.getNoFrequencyRenew()+1);
            end.set(Calendar.DAY_OF_WEEK, 7);
            break;
        }

        if(!fromDate.isBefore(LocalDate.fromCalendarFields(start))){
            start.setTime(fromDate.toDate());
        }
        startDate = LocalDate.fromCalendarFields(start);
        endDate = LocalDate.fromCalendarFields(end);
        return accrual();
    }
}
