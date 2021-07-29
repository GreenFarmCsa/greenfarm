package com.callforcode.greenfarm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import com.callforcode.greenfarm.exception.GFException;

public class DateToolUtils {

    public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";

    protected static DateTimeFormatter dfymdhms = DateTimeFormatter.ofPattern(FORMAT_FULL);

    public static String getTimeStamp() {
        return dfymdhms.format(LocalDateTime.now());
    }

    public static Date getCurrDate() {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_FULL);
        String dateStr = format.format(new Date());
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw new GFException("DateTime format conversion failed;current exception: " + e.getMessage());
        }
    }

    public static String getDateStr(Date date, String formatStr) {
        if (Objects.isNull(date)) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }

    public static Date getCustomDate(String dateFormatStr, String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormatStr);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw new GFException(" Custom Date format conversion failed;current exception: " + e.getMessage());
        }
    }

    public static String getDateStrByDate(String dateFormatStr, Date date) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormatStr);
        return format.format(date);
    }

    public static Date getCurrDateWithoutTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(new Date());
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw new GFException("Date format conversion failed;current exception: " + e.getMessage());
        }
    }

    @SuppressWarnings("static-access")
    public static Date getAddDaysDateResult(Date srcDate, Integer adddays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(srcDate);
        calendar.add(calendar.DATE, adddays);
        Date target = calendar.getTime();
        return target;
    }
}
