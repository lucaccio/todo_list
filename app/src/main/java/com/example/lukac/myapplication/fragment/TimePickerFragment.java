package com.example.lukac.myapplication.fragment;


import android.app.Dialog;
import android.app.TimePickerDialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import android.util.Log;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.lukac.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    int hourOfDay;
    int minute;

   @Override
   public Dialog onCreateDialog(Bundle savedInstanceState) {
       final Calendar c = Calendar.getInstance();

       Bundle args = getArguments();

       if(null != args) {
           String formattedTime = (String) args.getString("formattedTime") ;
           Date date = null;
           try {
               date = new SimpleDateFormat("H:m", Locale.ITALY).parse(formattedTime);
           } catch (ParseException e) {
               e.printStackTrace();
           }
           c.setTime(date);
       }
       hourOfDay  = c.get(Calendar.HOUR_OF_DAY);
       minute     = c.get(Calendar.MINUTE);
       Log.d("hourOfsday: ", "" + hourOfDay);
       Log.d("minute: ", "" + minute);
       return new TimePickerDialog(getContext(), this, hourOfDay, minute,true);
   }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        EditText timeTxt = (EditText) getActivity().findViewById(R.id.et_time);
        timeTxt.setText(String.format("%02d:%02d", hourOfDay, minute));
    }



}
