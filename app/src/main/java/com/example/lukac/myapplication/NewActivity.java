package com.example.lukac.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lukac.myapplication.database.DatabaseOpenHelper;
import com.example.lukac.myapplication.entity.Item;

import java.util.HashMap;
import java.util.Map;

public class NewActivity extends BaseActivity {

    //SharedPreferences pref;

    EditText title, phone, notes, date;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        //pref = getSharedPreferences("TODO_PREF", Context.MODE_PRIVATE);

       // db.query(DatabaseOpenHelper.DATABASE_TABLE, );
        id = getIntent().getStringExtra("ID");
    }


    @Override
    protected void onStart() {
        super.onStart();
        title = (EditText) findViewById(R.id.et_title);
        phone = (EditText) findViewById(R.id.et_phone);
        notes = (EditText) findViewById(R.id.et_notes);
        date = (EditText) findViewById(R.id.et_date);

        if(id == null) {
            saveAction();
        } else {
            logga("id", " " + id);
           // logga("itemId", "" + id);
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
                        Item item = new Item(tupla );
                        title.setText(item.getTitle());
                        phone.setText(item.getPhone());
                        notes.setText(item.getNotes());
                        date.setText(item.getDate());

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
        Button updateBtn = (Button)  findViewById(R.id.updateBtn);
        updateBtn.setText("Salva");
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().isEmpty()) {
                    Log.d("Button Save", "Manca il titolo...salvataggio non eseguito");
                    Toast mToast = Toast.makeText(getApplicationContext(), "Inserire un Titolo", Toast.LENGTH_SHORT);
                    mToast.show();
                    return;
                }
                Log.d("Button Save", "Save");
                Toast mToast = Toast.makeText(getApplicationContext(), "Salvataggio in corso...", Toast.LENGTH_SHORT);
                mToast.show();
                ContentValues values = new ContentValues();
                values.put("title", title.getText().toString());
                values.put("phone", phone.getText().toString());
                values.put("notes", notes.getText().toString());
                //values.put("date", notes.getText().toString());
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
        Button updateBtn = (Button)  findViewById(R.id.updateBtn);
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
                values.put("date", date.getText().toString());
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
