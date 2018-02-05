package com.sample.zkspring.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class Calculator {
    public static long daysBetween(final Date d1, final Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
    public static long daysBetween(final Calendar startDate, final Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
    }
    public static int daysOfMonth(Calendar calendar){
        Calendar nextMonth = Calendar.getInstance();
        nextMonth.setTime(calendar.getTime());
        nextMonth.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        nextMonth.set(Calendar.DAY_OF_MONTH, 1);

        Calendar currentMonth = Calendar.getInstance();
        currentMonth.setTime(calendar.getTime());
        currentMonth.set(Calendar.DAY_OF_MONTH, 1);
        int totalDay = (int)Calculator.daysBetween(currentMonth, nextMonth);
        return totalDay;
    }

    public static int daysOfYear(Calendar calendar){
        Calendar nextYear = Calendar.getInstance();
        nextYear.setTime(calendar.getTime());
        nextYear.set(Calendar.YEAR, nextYear.get(Calendar.YEAR) + 1);
        nextYear.set(Calendar.DAY_OF_YEAR, 1);

        Calendar currentYear = Calendar.getInstance();
        currentYear.setTime(calendar.getTime());
        currentYear.set(Calendar.DAY_OF_YEAR, 1);
        int totalDay = (int)Calculator.daysBetween(currentYear, nextYear);
        return totalDay;
    }
}
