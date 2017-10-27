package com.example.lukac.myapplication;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class todo_list extends AppCompatActivity {


    private Toolbar t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout rl = new RelativeLayout(this);

       // setContentView(R.layout.activity_todo_list);
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        Button b = new Button(this);
        b.setText("Close");
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ad.setMessage("Click").show();
            }
        });
        rl.addView(b);
        setContentView(rl);

         Toolbar t = (Toolbar) findViewById(R.id.toolbar);
         // setto la Toolbar come una ActionBar
         setSupportActionBar(t);
        //t.inflateMenu(R.menu.main_menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return true;
    }



}
