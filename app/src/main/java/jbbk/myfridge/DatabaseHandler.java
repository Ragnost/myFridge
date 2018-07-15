package jbbk.myfridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

//shoppingmemodbhelper
public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "myFridge.db";  // Database Name
    public static final int DATABASE_VERSION = 4;
    private int primaryKeyID = 0;
    public static final String TABLE_FOOD_LIST = "food_list";


    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ABLAUFDATUM = "ablaufdatum";
    public static final String COLUMN_STUECKZAHL = "stueckzahl";
    public static final String COLUMN_IMAGE = "image";

    public static final String SQL_CREATE_FOOD_TABLE =
            "CREATE TABLE " + TABLE_FOOD_LIST +
                    "(" + COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_ABLAUFDATUM + " TEXT NOT NULL, " +
                    COLUMN_STUECKZAHL + " TEXT NOT NULL, " +
                    COLUMN_IMAGE + " TEXT );";

    DatabaseHelper dbHelper = new DatabaseHelper();

    private static final String LOG_TAG = DatabaseHandler.class.getSimpleName();
    SQLiteDatabase mSqLiteDatabase;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(LOG_TAG, "DataBaseHandler hat die Datenbank " + getDatabaseName() + " erzeugt.");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            Log.d(LOG_TAG, "Die Tabelle wird mit dem Befehl:" + SQL_CREATE_FOOD_TABLE + " angelegt.");
            db.execSQL(SQL_CREATE_FOOD_TABLE);
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + dbHelper.DATABASE_NAME);
        System.out.println("Datenbank wurde geloescht");
        onCreate(db);
    }

    public void deleteItem(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FOOD_LIST, COLUMN_NAME + "=" + name, null);
        db.close();
    }

    public void insertFood(String name, String count, String datum) {
        System.out.println("Add to dataBase; " + name + " - " + count + " - " + datum);
        mSqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_STUECKZAHL, count);
        values.put(COLUMN_ABLAUFDATUM, datum);
        primaryKeyID++;
        System.out.println("Datenbank: " + mSqLiteDatabase.insert(TABLE_FOOD_LIST, null, values));
        //mSqLiteDatabase.insert(TABLE_FOOD_LIST, null, values);
        mSqLiteDatabase.close();
    }

    public void clearDatabase(){
        mSqLiteDatabase.execSQL("delete from "+ TABLE_FOOD_LIST);
    }

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> stueckzahl = new ArrayList<>();
    ArrayList<String> ablaufdatum = new ArrayList<>();

    DatabaseHelper saves;

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FOOD_LIST;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    public void getFoodFromDB() {

        mSqLiteDatabase = this.getReadableDatabase();
        String selectAll = "select * from " + TABLE_FOOD_LIST;
        Cursor c = mSqLiteDatabase.rawQuery(selectAll, null);
        if (c.moveToFirst()) {
            do {

                /* To Object */
                dbHelper.setName(c.getString(0));
                dbHelper.setStueckzahl(c.getString(1));
                dbHelper.setAblaufdatum(c.getString(2));

                System.out.println("###########");
                System.out.println(dbHelper.getName());
                System.out.println(dbHelper.getAblaufdatum());
                System.out.println(dbHelper.getStueckzahl());


                String _name = c.getString(0);
                String _stueckzahl = c.getString(1);
                String _ablaufdatum = c.getString(2);



                name.add(_name);
                // schnell vertauscht
                stueckzahl.add(_ablaufdatum);
                ablaufdatum.add(_stueckzahl);
            } while (c.moveToNext());
        }
        mSqLiteDatabase.close();
    }

    public DatabaseHelper getSaves() {
        return saves;
    }

    public ArrayList<String> getStueckzahl() {
        return stueckzahl;
    }

    public ArrayList<String> getAblaufdatum() {
        return ablaufdatum;
    }

    public ArrayList<String> getName() {
        return name;
    }


}


