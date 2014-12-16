package com.bramble.kickback.util;

import android.content.res.Resources;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

public class Util {

    private static DateTimeFormatter st = new DateTimeFormatterBuilder()
                                                .appendDayOfWeekText()
                                                .appendLiteral(", ")
                                                .appendMonthOfYearText()
                                                .appendLiteral(" ")
                                                .appendDayOfMonth(1)
                                                .appendLiteral("st")

                                                .toFormatter();
    private static DateTimeFormatter nd = new DateTimeFormatterBuilder()
                                                .appendDayOfWeekText()
                                                .appendLiteral(", ")
                                                .appendMonthOfYearText()
                                                .appendLiteral(" ")
                                                .appendDayOfMonth(1)
                                                .appendLiteral("nd")
                                                .toFormatter();
    private static DateTimeFormatter rd = new DateTimeFormatterBuilder()
                                                .appendDayOfWeekText()
                                                .appendLiteral(", ")
                                                .appendMonthOfYearText()
                                                .appendLiteral(" ")
                                                .appendDayOfMonth(1)
                                                .appendLiteral("rd")
                                                .toFormatter();

    private static DateTimeFormatter th = new DateTimeFormatterBuilder()
                                                .appendDayOfWeekText()
                                                .appendLiteral(", ")
                                                .appendMonthOfYearText()
                                                .appendLiteral(" ")
                                                .appendDayOfMonth(1)
                                                .appendLiteral("th")
                                                .toFormatter();

    public static int dpToPixels(Resources res, int dp) {
        final float scale = res.getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static String getFormattedStringFromDateTime(DateTime aParticularDay) {
        int j = aParticularDay.getDayOfMonth() % 10;
        String formatted;
        switch (j) {
            case 1:
                formatted = aParticularDay.toString(st);
                break;
            case 2:
                formatted = aParticularDay.toString(nd);
                break;
            case 3:
                formatted = aParticularDay.toString(rd);
                break;
            default:
                formatted = aParticularDay.toString(th);
        }
        return formatted.toUpperCase();
    }

}
