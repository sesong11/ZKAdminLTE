package com.sample.zkspring.utils;

import org.joda.time.LocalDate;

import com.sample.zkspring.entity.employment.Allowance;

public interface AccrualFormula {
    double getValue();
    void setAllowance(Allowance allowance);
    void setStartDate(LocalDate startDate);
    LocalDate getStartDate();
    void setEndDate(LocalDate endDate);
    LocalDate getEndDate();

    void setFromDate(LocalDate fromDate);
    LocalDate getFromDate();

    double accrualToday();
    double accrualEnd();
    double accrual();
}
