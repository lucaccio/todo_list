package com.example.lukac.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class BaseActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState, int layoutId) {
        super.onCreate(savedInstanceState);
        //initToolbar();
        setContentView(  layoutId);
        initToolbar();
    }

    protected void initToolbar() {
        Log.d("Base", "Init Toolbar");
        Toolbar t = (Toolbar) findViewById(R.id.toolbar);
        //t.inflateMenu(R.menu.main_menu);
        // setto la Toolbar come una ActionBar
        setSupportActionBar(t);
    }




}
