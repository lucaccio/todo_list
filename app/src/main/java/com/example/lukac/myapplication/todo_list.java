package com.example.lukac.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.net.Uri;

import java.util.ArrayList;

public class todo_list extends AppCompatActivity {


    private Toolbar t;

    static final int PICK_CONTACT_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


/*
       Context context = getApplicationContext();

       // final ApplicationInfo info = context.getApplicationInfo();

        RelativeLayout rl = new RelativeLayout(context);

       // setContentView(R.layout.activity_todo_list);
        final AlertDialog.Builder ad = new AlertDialog.Builder(context);

        Button b = new Button(this);
        b.setText("Close");
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ad.setMessage( "hai fatto click" ).show();
            }
        });
        rl.addView(b);

*/
        setContentView(R.layout.activity_todo_list);
        Toolbar t = (Toolbar) findViewById(R.id.toolbar);
        // setto la Toolbar come una ActionBar
        setSupportActionBar(t);
        //t.inflateMenu(R.menu.main_menu);

        /**
         * Creo la lista delle citta
         */
        final ListView listview = (ListView) findViewById(R.id.list_view);
        String[] values = new String[]{"Cagliari","Sassari","Nuoro","Oristano"};
        ArrayList<String>   al = new ArrayList<String>();
        for(int x = 0; x < values.length; ++x) {
            al.add(values[x]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, al);
        try {
            listview.setAdapter(adapter);
        } catch(Exception e) {
            Toast.makeText(this, e.toString(),Toast.LENGTH_SHORT);
        }



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adattatore, final View componente, int pos, long id){
                // recupero il titolo memorizzato nella riga tramite l'ArrayAdapter
                final String titoloriga = (String) adattatore.getItemAtPosition(pos);
                Log.d("List", "Ho cliccato sull'elemento con titolo " + titoloriga);
                Log.d("List", "Ho cliccato sull'elemento con id " + id);
            }
        }) ;

/*
        listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getIntenti();
            }
        });
        */

}
/*
    public boolean getIntenti() {
        Intent i = new Intent();

        startActivityForResult(
                new Intent(Intent.ACTION_PICK, new Uri() {
                }) {
                }),
                PICK_CONTACT_REQUEST);
        return true;
    }
*/

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user.
             //   startActivity(new Intent(Intent.ACTION_VIEW, data));
            }
        }
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
