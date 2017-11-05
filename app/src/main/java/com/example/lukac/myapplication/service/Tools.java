package com.example.lukac.myapplication.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lukac on 05/11/2017.
 */

public class Tools {


    /**
     *
     * @param dateValue
     * @param pattern
     * @return Long timestamp
     */
    public static Long getTimestamp(String dateValue, String pattern)  {
        Long timestamp = null;
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.ITALY);
        try {
            Date date = formatter.parse(dateValue);
            timestamp = date.getTime();
        } catch (ParseException pe) {
            pe.getStackTrace();
        }
        return timestamp;
    }


    /**
     *
     * @param timestamp attenzione deve essere in millisecondi
     * @return
     */
    public static Date timestampToDate(String timestamp) {
        java.sql.Timestamp timeStamp = new java.sql.Timestamp(Long.valueOf(timestamp));
        java.sql.Date date = new java.sql.Date(timeStamp.getTime());
        return date;
    }
    public static Date timestampToDate(Long timestamp) {
        java.sql.Timestamp timeStamp = new java.sql.Timestamp(Long.valueOf(timestamp));
        java.sql.Date date = new java.sql.Date(timeStamp.getTime());
        return date;
    }

    /**
     *
     * @param timestamp
     * @return
     */
    public static String getFormattedDate(String timestamp, String pattern) {
        Date date = timestampToDate(timestamp);
        SimpleDateFormat formatter = new java.text.SimpleDateFormat(pattern);
        String formatted = formatter.format(date);
        return formatted;
    }

    /**
     *
     * @param timestamp
     * @return
     */
    public static String getFormattedDate(String timestamp) {
        String defaultPattern = "dd/MM/yyyy HH:mm:ss";
        Date date = timestampToDate(timestamp);
        SimpleDateFormat formatter = new java.text.SimpleDateFormat(defaultPattern);
        String formatted = formatter.format(date);
        return formatted;
    }

    public static String getFormattedDate(Long timestamp) {
        String defaultPattern = "dd/MM/yyyy HH:mm:ss";
        Date date = timestampToDate(timestamp);
        SimpleDateFormat formatter = new java.text.SimpleDateFormat(defaultPattern);
        String formatted = formatter.format(date);
        return formatted;
    }

    }
