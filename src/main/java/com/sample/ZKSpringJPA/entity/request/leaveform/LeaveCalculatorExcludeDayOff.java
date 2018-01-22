package com.sample.ZKSpringJPA.entity.request.leaveform;

import com.sample.ZKSpringJPA.services.SpringContext;
import com.sample.ZKSpringJPA.services.employment.DayOffService;
import com.sample.ZKSpringJPA.utils.Calculator;
import org.springframework.context.ApplicationContext;

import java.util.Calendar;

public class LeaveCalculatorExcludeDayOff implements LeaveCalculator {

    @Override
    public double calculate(LeaveForm leaveForm) {
        ApplicationContext context = SpringContext.getAppContext();
        DayOffService dayOffService = (DayOffService) context.getBean("dayOffService");
        Calendar start = Calendar.getInstance();
        start.setTime(leaveForm.getFromDate());

        Calendar to = Calendar.getInstance();
        to.setTime(leaveForm.getToDate());

        //if return date before start date return 0
        if(to.before(start)){
            return 0;
        }
        double totalDays = 0;
        //leave on working day period
        totalDays += start.get(Calendar.HOUR_OF_DAY) >= 12 ? 0.5 : 1;
        //return on working day period
        if(to.get(Calendar.HOUR_OF_DAY)<=12)
            totalDays -= to.get(Calendar.HOUR_OF_DAY) < 8 ? 1 : 0.5;
        //add all working days
        totalDays += Calculator.workingDayBetween(start.getTime(), to.getTime());
        //exclude holidays
        totalDays -= dayOffService.countDayOff(start.getTime(), to.getTime());
        if(totalDays<0){
            totalDays = 0;
        }
        return totalDays;
    }
}
