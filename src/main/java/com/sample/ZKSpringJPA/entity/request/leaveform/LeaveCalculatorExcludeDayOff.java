package com.sample.ZKSpringJPA.entity.request.leaveform;

public class LeaveCalculatorExcludeDayOff implements LeaveCalculator {
    @Override
    public double calculate(LeaveForm leaveForm) {
        return 0;
    }
}
