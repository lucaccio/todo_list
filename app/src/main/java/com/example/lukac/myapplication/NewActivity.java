package com.example.lukac.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.lukac.myapplication.database.DatabaseOpenHelper;
import com.example.lukac.myapplication.entity.Item;
import com.example.lukac.myapplication.fragment.DatePickerFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NewActivity extends BaseActivity {



    EditText title, phone, notes, dateText, time;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        id = getIntent().getStringExtra("ID");
        ImageButton backButton = (ImageButton) findViewById(R.id.back_btn);
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




    @Override
    protected void onStart() {
        super.onStart();
        title = (EditText) findViewById(R.id.et_title);
        phone = (EditText) findViewById(R.id.et_phone);
        notes = (EditText) findViewById(R.id.et_notes);
        dateText = (EditText) findViewById(R.id.et_date);
        time = (EditText) findViewById(R.id.et_time);

        dateText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        dateText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
              if(hasFocus) {
                  DialogFragment newFragment = new DatePickerFragment();
                  newFragment.show(getSupportFragmentManager(), "datePicker");
              }

            }
        });


        if(id == null) {
            saveAction();
        } else {

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
                        /**
                         * @TODO
                         */
                         dateText.setText(item.getDate().toString());
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
            updateAction();
        }
    }


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
               // values.put("timestamp", dateText.getText().toString());


                Date date = null;
                try {
                    String datePattern = dateText.getText().toString();
                    // String timePattern = time.getText().toString();
                    DateFormat dateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");

                    date = dateFormat.parse(datePattern );
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // inutile
                long time = date.getTime();
                Log.i("DATE", ": " + time);
                Log.i("DATE", ": " + new java.sql.Timestamp(time));

                values.put("timestamp", time);

/*
                DateFormat dFormat =   DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
                try {
                    Date parsed = dFormat.parse(when);
                    Log.i("date", "" + parsed);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
*/
                SQLiteDatabase db = new DatabaseOpenHelper(getApplicationContext()).getWritableDatabase();
                try {
                    db.insert(DatabaseOpenHelper.DATABASE_TABLE,null, values);
                    //Intent i = new Intent(getApplicationContext(), TodoListActivity.class);
                    //startActivity(i);
                    onPause();
                } catch (Exception e) {
                    Log.e("DB.insert", e.getMessage());
                }

            }
        });
    }

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
                values.put("date", dateText.getText().toString());
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
