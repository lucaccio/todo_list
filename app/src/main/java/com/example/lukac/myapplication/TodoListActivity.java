package com.example.lukac.myapplication;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.lukac.myapplication.adapter.ItemAdapter;
import com.example.lukac.myapplication.database.DatabaseOpenHelper;
import com.example.lukac.myapplication.entity.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TodoListActivity extends BaseActivity  {




    static final int PICK_CONTACT_REQUEST = 0;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add_btn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
                logga(getLocalClassName(), "clicco il tasto aggiungi");
                Intent i = new Intent(getApplicationContext(), NewActivity.class);
                startActivity(i);
            }
        });

        Toolbar t = getToolbar(R.id.my_toolbar);
        setSupportActionBar(t);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //t .setNavigationIcon(R.mipmap.ic_launcher);
        //getSupportActionBar().setIcon(R.mipmap.ic_launcher);

    }


    /**
     *  Ricarico la lista dal database ad ogni riavvio dell'A
     */
    protected void onResume() {
        super.onResume();
        this.getItemsList();
    }


    protected void getItemsList() {
        try {
            ArrayList<Item>  arrayItems = new ArrayList<>();
            SQLiteDatabase  db = new DatabaseOpenHelper(getApplicationContext()).getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM todo", null);
            //Log.d("Todo", "Tuple presenti: " + c.getCount());
            if(c.getCount() > 0)
            {
                try {
                    Map tupla = new HashMap();
                    while(c.moveToNext())
                    {
                        for (int index = 0; index < c.getColumnCount(); ++index) {
                           // Log.d("Todo", "Index: " + index + c.getColumnName(index) + " -> " +  c.getString(index));
                            tupla.put(c.getColumnName(index) , c.getString(index));
                        }
                        Item item = new Item(tupla);
                        arrayItems.add( item );
                        //Log.d("Todo", "Aggiungo item: " + item.toString());
                      //  Log.d("Todo", "Item title: " + item.getTitle());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ItemAdapter adapter = new ItemAdapter(getApplicationContext(), R.layout.list_item, arrayItems);
            ListView listview = (ListView) findViewById(R.id.list_view);
            listview.setAdapter(adapter);
            listview.setLongClickable(true);
            listview.setOnItemClickListener(this.onItemClick());
            listview.setOnLongClickListener(new View.OnLongClickListener() {

                /**
                 * Called when a view has been clicked and held.
                 *
                 * @param v The view that was clicked and held.
                 * @return true if the callback consumed the long click, false otherwise.
                 */
                @Override
                public boolean onLongClick(View v) {
                   // logga("on   long click senza item", "true");
                    return false;
                }
            });

            listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                /**
                 * Callback method to be invoked when an item in this view has been
                 * clicked and held.
                 * <p>
                 * Implementers can call getItemAtPosition(position) if they need to access
                 * the data associated with the selected item.
                 *
                 * @param parent   The AbsListView where the click happened
                 * @param view     The view within the AbsListView that was clicked
                 * @param position The position of the view in the list
                 * @param id       The row id of the item that was clicked
                 * @return true if the callback consumed the long click, false otherwise
                 */
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                   // logga("on item long", "true");
                    return false;
                }
            });

        } catch (Exception e) {
            Log.e("Todo Error: ", e.getMessage());
        }
    }

    /**
     * click della listview
     *
     * @return AdapterView.OnItemClickListener
     */
    public AdapterView.OnItemClickListener onItemClick() {

            return new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Item item = (Item)parent.getAdapter().getItem(position);
                    //logga("item", "position: " + position+ " - item id: " + item.getId());
                    String s = item.getId();
                    Intent i = new Intent(getApplicationContext(), NewActivity.class);
                    i.putExtra("ID",  s );

                    startActivity(i);
                }
            };

    }



    /**
     * @NON UTILIZZATO
     */
    public void popolaLista() {

        /** alori da inserire nella lista dei todo */
        String[] values = new String[]{};
        ArrayList<String> al = new ArrayList<>();
        try {
            DatabaseOpenHelper dbh = new DatabaseOpenHelper(getApplicationContext());
            SQLiteDatabase  db = dbh.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM todo", null);

            if(c.getCount() == 0)
            {
                //Log.d("Database Record: ", "No records found");
                Toast mToast = Toast.makeText(this, "No record found", Toast.LENGTH_LONG);
                mToast.show();

            } else {
                while(c.moveToNext())
                {
                    String value = c.getString(1);
                    al.add( value );
                   // Log.d("TodoActivity:add ", value);
                    //String title =  c.getString(1);
                }


                try {
                    ListView listview = (ListView) findViewById(R.id.list_view);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, al);

                    listview.setAdapter(adapter);
                    // la firma è una class anonima che implementa l'interfaccia Adap..OnItem...
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> adattatore, final View componente, int pos, long id) {

                            /*
                            Log.d("ListView", "hai selezionato la riga " + pos);

                            //creo una mappa per salvare i dati
                            final SharedPreferences pref = getSharedPreferences("TODO_PREF", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("prova", "prova eseguita con successo");
                            editor.apply();

                            Intent i = new Intent(getApplicationContext(), NewActivity.class);
                            i.putExtra("id", id);
                            startActivity(i);
                                */

                        }
                    });
                } catch (Exception e) {
                    Log.d("TODO",  e.toString() );
                    Toast mToast = Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT);
                    mToast.show();
                }
            }
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }




    public boolean getIntenti() {
        startActivityForResult(
                new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI),
                PICK_CONTACT_REQUEST);
        return true;
    }




    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user.
                startActivity(new Intent(Intent.ACTION_VIEW));
            } else {
                Log.d("a", "b");
            }
        }
    }







    /*
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

    */

    /**
     * Evita di mostrare l'activity  con i dati inseriti precedentemente alla pressione del tasto back
     *
     * @Override
     */
    protected void onPause() {
        super.onPause();
        //finish();
    }




}
