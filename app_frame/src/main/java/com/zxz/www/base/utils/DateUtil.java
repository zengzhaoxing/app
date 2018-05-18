package com.zxz.www.base.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author wujing
 */
public class DateUtil {

    private static final String TAG = "DateUtil";

    public static String dateToString(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(format);
        return simpleDateFormat.format(date.getTime());
    }

    public static String calendarToString(Calendar calendar, String format) {
        if (calendar == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(format);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String longTimeToString(long ms,String format) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(ms);
        return calendarToString(c, format);
    }

    public static Date stringToDate(String date, String format) {
        if (StringUtil.isEmpty(date)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Calendar stringToCalendar(String date, String format) {
        if (StringUtil.isEmpty(date)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(stringToDate(date,format));
        return calendar;
    }

    public static String exChangeDateString(String dateStr, String fromStringFormat, String toStringFormat) {
        if (!StringUtil.isBlank(dateStr)) {
            Date date = stringToDate(dateStr, fromStringFormat);
            if (date != null) {
                SimpleDateFormat simpleDateFormat = getSimpleDateFormat(toStringFormat);
                return simpleDateFormat.format(date.getTime());
            }
            return null;
        }
        return null;
    }

    @NonNull
    private static SimpleDateFormat getSimpleDateFormat(String pattern) {
        Locale locale = Locale.getDefault();
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(2, 2, locale);
        simpleDateFormat.applyPattern(pattern);
        return simpleDateFormat;
    }

    public static boolean isSameDay(Calendar c1, Calendar c2) {
        if(c1 == null || c2 == null){
            return false;
        }
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.DATE) == c2.get(Calendar.DATE);
    }

    /**
     * 指定某年中的某月的第一天是星期几
     */
    public static int getWeekOfFirstDayInMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return dayOfWeek;
    }

    /**
     * 指定某年中的某月的最后一天是星期几
     */
    public static int getWeekOfLastDayInMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, getDaysOfMonth(year, month));
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return dayOfWeek;
    }

    /**
     * 判断是否为闰年
     */
    public static boolean isLeapYear(int year) {
        if (year % 100 == 0 && year % 400 == 0) {
            return true;
        } else if (year % 100 != 0 && year % 4 == 0) {
            return true;
        }
        return false;
    }

    /**
     * 得到某年某月有多少天数
     */
    public static int getDaysOfMonth(int year, int month) {
        int daysOfMonth = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                daysOfMonth = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                daysOfMonth = 30;
                break;
            case 2:
                if (isLeapYear(year)) {
                    daysOfMonth = 29;
                } else {
                    daysOfMonth = 28;
                }

        }
        return daysOfMonth;
    }

    public static int getDaysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 返回相差的月份个数
     * @param c1
     * @param c2
     * @return
     */
    public static int offsetMonth(Calendar c1 ,Calendar c2){
        if(c1 == null || c2 == null){
            return 0;
        }
        int sum1 = c1.get(Calendar.YEAR) * 12 + c1.get(Calendar.MONTH);
        int sum2 = c2.get(Calendar.YEAR) * 12 + c2.get(Calendar.MONTH);
        return sum1 - sum2;
    }



}
