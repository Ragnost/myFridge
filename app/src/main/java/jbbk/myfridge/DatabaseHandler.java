package jbbk.myfridge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;    // Database Name
    private static final String DATABASE_NAME = "myFridge.db";
    DatabaseHelper dbHelper = new DatabaseHelper();


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /*Tabelle fuer Anfang beinhaltet: [id, primaryKey] + [name]*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FOOD = "CREATE TABLE " + dbHelper.DATABASE_NAME + "("
                + dbHelper.ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + dbHelper.name + " TEXT, ";
        db.execSQL(CREATE_TABLE_FOOD);
        System.out.println("Datenbank wurde angelegt");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + dbHelper.DATABASE_NAME);
        System.out.println("Datenbank wurde geloescht");
        onCreate(db);
    }
}


