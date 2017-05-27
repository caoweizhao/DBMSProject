package com.example.administrator.dbmsproject.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017-5-23.
 */

public class DateFormatUtil {
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");

    /**
     * 2017-05-24 13:35
     * @param date
     * @return
     */
    public static String detailDateToString(Date date) {
        return sdf1.format(date);
    }

    /**
     * 2017-05-24
     * @param date
     * @return
     */
    public static String normalDateToString(Date date) {
        return sdf2.format(date);
    }

    /**
     * 18:00
     * @param date
     * @return
     */
    public static String hourMinuteToString(Date date) {
        return sdf3.format(date);
    }

    /**
     * 2017-05-24 13:35
     * @param s
     * @return
     */
    public static Date stringToDetailDate(String s) {
        try {
            return sdf1.parse(s);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 2017-05-24
     * @param s
     * @return
     */
    public static Date stringToNormalDate(String s) {
        try {
            return sdf2.parse(s);
        } catch (ParseException e) {
            return null;
        }
    }

}
