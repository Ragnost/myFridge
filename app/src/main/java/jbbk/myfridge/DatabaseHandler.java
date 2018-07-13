package jbbk.myfridge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//shoppingmemodbhelper
public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "myFridge.db";  // Database Name
    public static final int DATABASE_VERSION = 4;

    public static final String TABLE_FOOD_LIST = "food_list";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ABLAUFDATUM = "ablaufdatum";
    public static final String COLUMN_STUECKZAHL = "stueckzahl";
    public static final String COLUMN_IMAGE = "image";

    public static final String SQL_CREATE_FOOD_TABLE =
            "CREATE TABLE" + TABLE_FOOD_LIST +
                    "(" + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_ABLAUFDATUM + "TEXT NOT NULL," +
                    COLUMN_STUECKZAHL + " TEXT NOT NULL," +
                    COLUMN_IMAGE + "TEXT );";

    DatabaseHelper dbHelper = new DatabaseHelper();

    private static final String LOG_TAG = DatabaseHandler.class.getSimpleName();

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(LOG_TAG, "DataBaseHandler hat die Datenbank " + getDatabaseName() + "erzeugt.");
    }


    /*Tabelle fuer Anfang beinhaltet: [id, primaryKey] + [name]*/

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            Log.d(LOG_TAG,"Die Tabelle wird mit dem Befehl:"+ SQL_CREATE_FOOD_TABLE+" angelegt.");
            db.execSQL(SQL_CREATE_FOOD_TABLE);
        }
        catch (Exception ex){
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + dbHelper.DATABASE_NAME);
        System.out.println("Datenbank wurde geloescht");
        onCreate(db);
    }
}


