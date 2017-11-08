package com.example.lukac.myapplication;


import android.content.ContentValues;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Toast;


import com.example.lukac.myapplication.database.DatabaseOpenHelper;
import com.example.lukac.myapplication.entity.Item;
import com.example.lukac.myapplication.fragment.DatePickerFragment;
import com.example.lukac.myapplication.fragment.TimePickerFragment;
import com.example.lukac.myapplication.service.Tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewActivity extends BaseActivity {

    String id;

    EditText title,
             phone,
             notes,
             dateTxt,
             timeTxt
                    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        createUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        id = getIntent().getStringExtra("ID");
        if(id == null) {

            // imposto la data e l'ora attuale nella ui
            Date date = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
            String tm = sf.format(date);
            timeTxt.setText( tm );
            sf.applyPattern("dd/MM/yyyy");
            tm = sf.format(date);
            dateTxt.setText(tm);

            saveAction();
        } else {
            /** carico i dati nella UI */
            fillFormForUpdate();
            updateAction();
        }
    }


    /**
     * Impostazioni dell'interfaccia utente
     *
     */
    protected void createUI() {
        Log.w("formatted", "createui");
        ImageButton backButton = (ImageButton) findViewById(R.id.back_btn);
        title   = (EditText) findViewById(R.id.et_title);
        phone   = (EditText) findViewById(R.id.et_phone);
        notes   = (EditText) findViewById(R.id.et_notes);
        dateTxt = (EditText) findViewById(R.id.et_date);
        timeTxt    = (EditText) findViewById(R.id.et_time);

        dateTxt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialogFragment();
            }
        });

        dateTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    showDateDialogFragment();
                }
            }
        });

        timeTxt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialogFragment();
            }
        });

        timeTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    showTimeDialogFragment();
                }
            }
        });


        backButton.setOnClickListener(new View.OnClickListener(){
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                onPause();
            }
        });
    }


    /**
     * Mostra il fragment con il calendario
     *
     */
    protected void showDateDialogFragment() {

        String formattedDate = dateTxt.getText().toString();
        //Log.w("formatted", "" + formattedDate);
        DialogFragment newFragment = new DatePickerFragment( );
        if(!formattedDate.isEmpty()) {
            Bundle args = new Bundle();
            args.putString("formattedDate", formattedDate);
            newFragment.setArguments(args);
        }
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     *
     */
    protected void showTimeDialogFragment() {

        String formattedTime = timeTxt.getText().toString();
        //Log.w("formatted", "" + formattedDate);
        DialogFragment newFragment = new TimePickerFragment();
        if(!formattedTime.isEmpty()) {
            Bundle args = new Bundle();
            args.putString("formattedTime", formattedTime);
            newFragment.setArguments(args);
        }
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }


    /**
     * Inserisco una nuova voce nel database
     *
     */
    protected void saveAction() {

        Button saveBtn = (Button)  findViewById(R.id.saveButton);
        saveBtn.setText("Salva");
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().isEmpty()) {
                    Toast mToast = Toast.makeText(getApplicationContext(), "Inserire un Titolo", Toast.LENGTH_SHORT);
                    mToast.show();
                    return;
                }
                Toast mToast = Toast.makeText(getApplicationContext(), "Salvataggio in corso...", Toast.LENGTH_SHORT);
                mToast.show();
                ContentValues values = new ContentValues();
                values.put("title", title.getText().toString());
                values.put("phone", phone.getText().toString());
                values.put("notes", notes.getText().toString());
                // converto la data in long timestamp
                Log.d("testo time: ", "" + timeTxt.getText().toString());
                String myInsertedDate = dateTxt.getText().toString() + " " + timeTxt.getText().toString();
                Log.d("my", "" + myInsertedDate);
                Long timestamp = Tools.getTimestamp( myInsertedDate, "dd/MM/yyyyy HH:mm");
                Log.d("my", "" + timestamp );
                values.put("timestamp", timestamp);

                SQLiteDatabase db = new DatabaseOpenHelper(getApplicationContext()).getWritableDatabase();
                try {
                    db.insert(DatabaseOpenHelper.DATABASE_TABLE,null, values);
                    onPause();
                } catch (Exception e) {
                    Log.e("DB.insert: ", e.getMessage());
                }

            }
        });
    }

    /**
     * Riempe le textbox per l'aggiornamento dei dati
     *
     */
    protected void fillFormForUpdate() {
        SQLiteDatabase db = new DatabaseOpenHelper(getApplicationContext()).getReadableDatabase() ;
        Cursor c = db.rawQuery("SELECT * FROM todo WHERE id = " + id, null);
        if (c.getCount() > 0)
        {
            Map tupla = new HashMap();
            while(c.moveToNext())
            {
                for (int index = 0; index < c.getColumnCount(); ++index) {
                    tupla.put(c.getColumnName(index) , c.getString(index));
                }
                try {
                    Item item = new Item(tupla);
                    title.setText(item.getTitle());
                    phone.setText(item.getPhone());
                    notes.setText(item.getNotes());
                    String dateToTxt = Tools.getFormattedDate( item.getDate(), "dd/MM/yyyy" );

                    Log.d("x", "" + dateToTxt);
                    dateTxt.setText(dateToTxt);
                    String timeToTxt = Tools.getFormattedDate( item.getDate(), "HH:mm" );
                    timeTxt.setText(timeToTxt);

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Aggiorno la voce del database
     */
    protected void updateAction() {

        Button updateBtn = (Button)  findViewById(R.id.saveButton);

        updateBtn.setText("Aggiorna");
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().isEmpty()) {
                    Log.d("Button Save", "Manca il titolo...salvataggio non eseguito");
                    Toast mToast = Toast.makeText(getApplicationContext(), "Inserire un Titolo", Toast.LENGTH_SHORT);
                    mToast.show();
                    return;
                }
                Log.d("Button Update", "Aggiorna");
                Toast mToast = Toast.makeText(getApplicationContext(), "Aggiornamento in corso...", Toast.LENGTH_SHORT);
                mToast.show();
                ContentValues values = new ContentValues();
                values.put("title", title.getText().toString());
                values.put("phone", phone.getText().toString());
                values.put("notes", notes.getText().toString());

                String myInsertedDate = dateTxt.getText().toString() + " " + timeTxt.getText().toString();
                // converto il valore in timestamp
                Long timestamp = Tools.getTimestamp( myInsertedDate, "dd/MM/yyyyy HH:mm");
                values.put("timestamp", timestamp);

                SQLiteDatabase db = new DatabaseOpenHelper(getApplicationContext()).getWritableDatabase();
                try {
                    String where = "id = ?";
                    String[] arg = new String[]{ new String().valueOf(id)  };
                    db.update(DatabaseOpenHelper.DATABASE_TABLE,  values,where, arg );
                   logga("update", "agiorno");
                    onPause();
                } catch (Exception e) {
                    Log.e("DB.update", e.getMessage());
                }

            }
        });
    }

    /**
     * Evita di mostrare l'activity  con i dati inseriti precedentemente alla pressione del tasto back
     *
     * @Override
     */
    protected void onPause() {
        super.onPause();
        finish();
    }



}
