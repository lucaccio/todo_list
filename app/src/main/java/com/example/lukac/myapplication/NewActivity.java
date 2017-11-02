package com.example.lukac.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lukac.myapplication.database.DatabaseOpenHelper;

public class NewActivity extends AppCompatActivity {

    //SharedPreferences pref;

    EditText title, phone, notes,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        //pref = getSharedPreferences("TODO_PREF", Context.MODE_PRIVATE);
        SQLiteDatabase db = new DatabaseOpenHelper(getApplicationContext()).getReadableDatabase() ;
        db.query(DatabaseOpenHelper.DATABASE_TABLE, );

    }


    @Override
    protected void onStart() {
        super.onStart();

        title = (EditText) findViewById(R.id.et_title);
        phone = (EditText) findViewById(R.id.et_phone);
        notes = (EditText) findViewById(R.id.et_notes);
        date = (EditText) findViewById(R.id.et_date);
        Button updateBtn = (Button)  findViewById(R.id.updateBtn);
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
                    Intent i = new Intent(getApplicationContext(), TodoListActivity.class);
                    startActivity(i);
                } catch (Exception e) {
                    Log.e("DB.insert", e.getMessage());
                }

            }
        });





    }




}
