package com.sample.ZKSpringJPA.entity.request.leaveform;

import com.sample.ZKSpringJPA.utils.Calculator;

import java.util.Calendar;

public class LeaveCalculatorOverall implements LeaveCalculator {
    @Override
    public double calculate(LeaveForm leaveForm) {
        Calendar start = Calendar.getInstance();
        start.setTime(leaveForm.getFromDate());

        Calendar to = Calendar.getInstance();
        to.setTime(leaveForm.getToDate());

        if(to.before(start)){
            return 0;
        }
        double totalDays = 0;

        double totalHours = Calculator.hourBetween(start, to);
        totalDays += (int)totalHours/24;
        totalDays += totalHours%24 > 4? 1: 0.5;

        return totalDays;
    }
}
