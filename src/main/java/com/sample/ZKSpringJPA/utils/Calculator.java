package com.sample.ZKSpringJPA.utils;

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

    public static double hourBetween(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toHours(Math.abs(end - start));
    }

    public static long workingDayBetween(Date start, Date end){
        //Ignore argument check

        Calendar c1 = Calendar.getInstance();
        c1.setTime(start);
        int w1 = c1.get(Calendar.DAY_OF_WEEK);
        c1.add(Calendar.DAY_OF_WEEK, -w1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(end);
        int w2 = c2.get(Calendar.DAY_OF_WEEK);
        c2.add(Calendar.DAY_OF_WEEK, -w2);

        //end Saturday to start Saturday
        long days = (c2.getTimeInMillis()-c1.getTimeInMillis())/(1000*60*60*24);
        long daysWithoutWeekendDays = days-(days*2/7);

        // Adjust days to add on (w2) and days to subtract (w1) so that Saturday
        // and Sunday are not included
        if (w1 == Calendar.SUNDAY && w2 != Calendar.SATURDAY) {
            w1 = Calendar.MONDAY;
        } else if (w1 == Calendar.SATURDAY && w2 != Calendar.SUNDAY) {
            w1 = Calendar.FRIDAY;
        }

        if (w2 == Calendar.SUNDAY) {
            w2 = Calendar.MONDAY;
        } else if (w2 == Calendar.SATURDAY) {
            w2 = Calendar.FRIDAY;
        }

        return daysWithoutWeekendDays-w1+w2;
    }
}
