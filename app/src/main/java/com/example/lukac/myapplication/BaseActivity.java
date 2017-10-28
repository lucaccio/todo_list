package com.example.lukac.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        initToolbar();
    }

    protected void initToolbar() {

        Toolbar t = (Toolbar) findViewById(R.id.toolbar);
        //t.inflateMenu(R.menu.main_menu);
        // setto la Toolbar come una ActionBar
        setSupportActionBar(t);
        Log.d("Base", "Init Toolbar");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }



}
