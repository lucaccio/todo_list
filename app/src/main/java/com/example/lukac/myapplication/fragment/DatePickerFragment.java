package com.example.lukac.myapplication.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.lukac.myapplication.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public   class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    int year;
    int month;
    int day;




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        final Calendar c = Calendar.getInstance();
        if(null != args) {
            try {
                String oldstring = args.getString("formattedDate");
                Date date = new SimpleDateFormat("d/MM/yyyy", Locale.ITALY).parse(oldstring);
                c.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        EditText dateTxt = (EditText) getActivity().findViewById(R.id.et_date);
        dateTxt.setText(day + "/" + (month +1) + "/" + year);
        /*
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            EditText timeTxt = (EditText) getActivity().findViewById(R.id.et_time);
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                timeTxt.setText(String.format("%02d:%02d", hourOfDay, minute));
            }
        }, 0, 0, true);

        timePickerDialog.show();
*/

    }
}