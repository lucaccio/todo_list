package com.example.lukac.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewActivity extends AppCompatActivity {

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        pref = getSharedPreferences("TODO_PREF", Context.MODE_PRIVATE);


    }


    @Override
    protected void onStart() {
        super.onStart();

        Button updateBtn = (Button)  findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button Save", "Save");
            }
        });

        EditText titolo = (EditText)  findViewById(R.id.editText2);

        Log.d("Shared preference", pref.getString("prova","no string"));

    }




}
