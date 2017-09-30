package fi.jamk.l3329.shoppinglistsqliteex;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by peter on 30.9.2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "peter_db";
    private final String DATABASE_TABLE = "shopping_list";
    private final String NAME = "name";
    private final String COUNT = "count";
    private final String PRICE = "price";

    public DatabaseOpenHelper(Context context) {
        //context, nazov databazy, dobrovolny parameter cursor factory, verzia databazy
        super(context, DATABASE_NAME, null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create a new table
        db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
        + NAME + " TEXT, " + COUNT + " REAL, " + PRICE + " REAL);");

        ContentValues values = new ContentValues();
        values.put(NAME, "water");
        values.put(COUNT, "1");
        values.put(PRICE, "3.5");

        db.insert(DATABASE_TABLE, null, values);

        values.put(NAME, "bread");
        values.put(COUNT, "2");
        values.put(PRICE, "2.0");

        db.insert(DATABASE_TABLE, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }
}
