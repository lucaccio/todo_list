package com.example.lukac.myapplication.entity;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lukac on 02/11/2017.
 */

public class Item {

    private String id;
    private String title;
    private String phone;
    private String notes;
    private String date;

    public Item(Map<String, String> values) throws NoSuchFieldException, IllegalAccessException {

        Iterator<String> iterator = values.keySet().iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            //Log.d("Item c name: ", key);

            String value = values.get(key);
            Field f = getClass().getDeclaredField(key);
            f.setAccessible(true);
            try {
                f.set(this, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
