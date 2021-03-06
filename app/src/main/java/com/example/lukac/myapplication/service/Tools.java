package com.example.lukac.myapplication.service;

import android.util.Log;

import java.sql.Timestamp;
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
     * @param dateValue la data selezionaa dal client
     * @param pattern il formato della data selezionata
     * @return Long returnedTimestamp  la data restituita, in timestamp
     */
    public static long getTimestamp(String dateValue, String pattern)  {
        long returnedTimestamp = 0;
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.ITALY);
        try {
            Date date = formatter.parse(dateValue);
            Log.d("LONGreturnedTimestamp", ""  + date.getTime());
            returnedTimestamp  = date.getTime();

        } catch (ParseException p) {
            Log.e("error", "" + p.getMessage());
            p.printStackTrace();
        }
        Log.d("returnedTimestamp", ""  + returnedTimestamp);
        return returnedTimestamp;
    }


    /**
     *
     * @param timestamp attenzione deve essere in millisecondi
     * @return
     */
    public static Date timestampToDate(String timestamp) {
        Log.d("timestampToData", "");
        if(null == timestamp) {
            throw new NullPointerException("Valore di timestamp nullo");
        }
        Long  longTimestamp = Long.valueOf(timestamp);
        Timestamp times  = new Timestamp(longTimestamp );
        Date date = new Date(times .getTime());
        //Long  longTimestamp = Long.valueOf(timestamp);
       // Date date = new Date(timestamp);
        //java.sql.Timestamp timeStamp = new java.sql.Timestamp(longTimestamp);
        //java.sql.Date date = new java.sql.Date(timeStamp.getTime());
        return date;
    }

    /**
     *
     * @param timestamp
     * @return
     */
    public static Date timestampToDate(Long timestamp) {
        if(null == timestamp) {
            throw new NullPointerException("Valore di timestamp nullo");
        }
        Long  longTimestamp = Long.valueOf(timestamp);
        //java.sql.Timestamp timeStamp = new java.sql.Timestamp(longTimestamp);
       // java.sql. date = new java.sql.Date(timeStamp.getTime());
        Date date = new Date(timestamp);
        return date;
    }

    /**
     *
     * @param timestamp
     * @return
     */
    public static String getFormattedDate(String timestamp, String pattern) {
        String formatted = "";
        if(null == timestamp) {
            return formatted;
        }
        Date date = timestampToDate(timestamp);
        SimpleDateFormat formatter = new java.text.SimpleDateFormat(pattern);
        formatted = formatter.format(date);
        Log.d("formatte ", "" + formatted);
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
