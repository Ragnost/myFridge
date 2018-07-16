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

    private ArrayList<Integer> vitaly = new ArrayList<>();
    public static final String DATABASE_NAME = "myFridge.db";  // Database Name
    public static final int DATABASE_VERSION = 4;
    public static final String TABLE_FOOD_LIST = "food_list";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ABLAUFDATUM = "ablaufdatum";
    public static final String COLUMN_STUECKZAHL = "stueckzahl";
    public static final String COLUMN_VITALY = "vitaly";

    public static final String SQL_CREATE_FOOD_TABLE =
            "CREATE TABLE " + TABLE_FOOD_LIST +
                    "(" + COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_ABLAUFDATUM + " TEXT NOT NULL, " +
                    COLUMN_STUECKZAHL + " TEXT NOT NULL, " +
                    COLUMN_VITALY + " INTEGER );";

    private static final String LOG_TAG = DatabaseHandler.class.getSimpleName();
    private DatabaseHelper dbHelper = new DatabaseHelper();
    private SQLiteDatabase mSqLiteDatabase;
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> stueckzahl = new ArrayList<>();
    private ArrayList<String> ablaufdatum = new ArrayList<>();


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

    public void deleteRow(String name) {
        mSqLiteDatabase = this.getWritableDatabase();
        mSqLiteDatabase.delete(TABLE_FOOD_LIST, COLUMN_NAME + "=" + "\"" + name + "\";", null);
        mSqLiteDatabase.close();
    }

    public void insertFood(String name, String count, String datum, Integer vitaly) {
        System.out.println("Add to dataBase; " + name + " - " + count + " - " + datum);
        mSqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_STUECKZAHL, count);
        values.put(COLUMN_ABLAUFDATUM, datum);
        values.put(COLUMN_VITALY, vitaly);
        System.out.println("Datenbank: " + mSqLiteDatabase.insert(TABLE_FOOD_LIST, null, values));
        mSqLiteDatabase.close();
    }

    //Todo: auf id prüfen.
    public void changeStueckzahl(String name, String count, int i) {
        mSqLiteDatabase = this.getWritableDatabase();

        //decrease
        if (i == 0) {
            int countadjusted = Integer.parseInt(count) - 1;
            Integer.toString(countadjusted);

            if (Integer.parseInt(count) <= 0) {
                // deleteRow(name);
            }
            if (Integer.parseInt(count) > 0) {
                String updateStueckzahl = " UPDATE " + TABLE_FOOD_LIST + " SET " + COLUMN_STUECKZAHL + " = " + countadjusted + " WHERE " + COLUMN_NAME + " = " + "\'" + name + "\'";
                try {
                    Log.d(LOG_TAG, "Stueckzahl wird reduziert");
                    mSqLiteDatabase.execSQL(updateStueckzahl);
                } catch (Exception ex) {
                    Log.e(LOG_TAG, "Fehler beim reduzieren der Stueckzahl " + ex.getMessage());
                }

            }
        }
        //increase
        else if (i == 1) {
            int countadjusted = Integer.parseInt(count) + 1;
            Integer.toString(countadjusted);
            String updateStueckzahl = " UPDATE " + TABLE_FOOD_LIST + " SET " + COLUMN_STUECKZAHL + " = " + countadjusted + " WHERE " + COLUMN_NAME + " = " + "\'" + name + "\'";
            try {
                Log.d(LOG_TAG, "Stueckzahl wird erhöht.");
                mSqLiteDatabase.execSQL(updateStueckzahl);
            } catch (Exception ex) {
                Log.e(LOG_TAG, "Fehler beim erhöhen der Stueckzahl " + ex.getMessage());
            }
        } else {
            System.out.println("Falscher Parameter übergeben" + i);
        }
        mSqLiteDatabase.close();

    }

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
        name.clear();
        stueckzahl.clear();
        ablaufdatum.clear();
        vitaly.clear();
        if (c.moveToFirst()) {
            do {
                /* To Object */
                dbHelper.setName(c.getString(0));
                dbHelper.setStueckzahl(c.getString(1));
                dbHelper.setAblaufdatum(c.getString(2));
                dbHelper.setVitaly(c.getInt(3));

                System.out.println("###########");
                System.out.println(dbHelper.getName());
                System.out.println(dbHelper.getAblaufdatum());
                System.out.println(dbHelper.getStueckzahl());
                System.out.println(dbHelper.getVitaly());

                name.add(c.getString(0));
                stueckzahl.add(c.getString(2));
                ablaufdatum.add(c.getString(1));
                vitaly.add(c.getInt(3));

            } while (c.moveToNext());
        }
        mSqLiteDatabase.close();
    }

    public ArrayList<Integer> getVitaly() {
        return vitaly;
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


