package fi.jamk.l3329.shoppinglistsqliteex;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NewShoppingListItem.DialogListener{

    private ListView listView;
    private SQLiteDatabase db;
    private Cursor cursor;
    private static final String DATABASE_NAME = "peter_db";
    private final String DATABASE_TABLE = "shopping_list";
    private final String NAME = "name";
    private final String COUNT = "count";
    private final String PRICE = "price";
    private final int DELETE_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find listview
        listView = (ListView) findViewById(R.id.list_view);
        //register listView`s context menu (to delete row)
        registerForContextMenu(listView);

        //get database instance
        db = (new DatabaseOpenHelper(this)).getWritableDatabase();

        //get data with using own made queryData method
        queryData();

        //calculate total price
        float totalPrice = 0f;

        if (cursor.moveToFirst())   //return false if first row is empty
        {
            do {
                float times = cursor.getFloat(2);
                float price = cursor.getFloat(3); //columnIndex
                totalPrice += (price * times);
            } while (cursor.moveToNext()); //will return false if cursor is already at the end
            Toast.makeText(getBaseContext(), "Total price: " + totalPrice,
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void queryData(){
        String[] resultColumns = new String[]{"_id","name","count","price"};
        cursor = db.query(DATABASE_TABLE, resultColumns,null,null,null,null,null,null);

        //add data to adapter
        ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.list_item, cursor,
                new String[] {"name","count","price"},  //from
                new int[]{R.id.name, R.id.count, R.id.price}    //to
                ,0); //flags


        //show data in listView
        listView.setAdapter(adapter);
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String name, double count, double price) {
        ContentValues values = new ContentValues(3);
        values.put("name", name);
        values.put("count", count);
        values.put("price", price);
        db.insert(DATABASE_TABLE, null, values);

        //get data again
        queryData();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "Delete");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Handles item selections
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                NewShoppingListItem eDialog = new NewShoppingListItem();
                eDialog.show(getFragmentManager(), "Add a new item to the list");
        }
        return false;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                String[] args = {String.valueOf(info.id)};
                db.delete(DATABASE_TABLE, "_id=?", args);
                queryData();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // close cursor and db connection
        cursor.close();
        db.close();
    }
}
