package com.example.lukac.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DetailsActivity extends BaseActivity {

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.activity_details);

    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent i = getIntent();
        String citta = i.getStringExtra("Città");
        long ID = i.getLongExtra("Id",-1);
        Log.d("Details", "Hai selezionato la riga " + citta);
        TextView t = (TextView) findViewById(R.id.detailstextView);
        t.append(citta);
    }


}
