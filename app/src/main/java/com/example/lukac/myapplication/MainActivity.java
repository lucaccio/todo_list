package com.example.lukac.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
public class MainActivity extends AppCompatActivity  {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar t = (Toolbar) findViewById(R.id.my_toolbar);
        // setto la Toolbar come una ActionBar
        setSupportActionBar(t);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
     //   getSupportActionBar().setIcon(R.mipmap.ic_launcher);

    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        //Log.d("Main", "onPrepare");
       // getMenuInflater().inflate(R.menu.main_menu, menu);


       // menu.add("prova");

        return super.onPrepareOptionsMenu(menu);
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       //Log.d("Main", "OnCreate");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){


        switch (item.getItemId()) {
            case R.id.list:
                Log.d("Item", "List");

                Intent i = new Intent(this, TodoListActivity.class);
                startActivity(i);

                break;
            case R.id.fragment:
                Log.d("Item", "Fragment");
                break;

        }

        return super.onOptionsItemSelected(item);
    }




}
