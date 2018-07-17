package jbbk.myfridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * DatabaseHandler für die SQLite-Befehle zum Verwalten der Datenbank,
 * welche von dem Fragment_Fridge für die List_View verwendet wird.
 **/
public class DatabaseHandler extends SQLiteOpenHelper {


    //TODO Tabelle mit Namen erstellen - fertig

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

    public final String TABLE_NAME_LIST = "name_list";
    public final String SQL_CREATE_NAME_TABLE =
            "CREATE TABLE " + TABLE_NAME_LIST +
                    "(" + COLUMN_NAME + " TEXT NOT NULL);";

    private static final String LOG_TAG = DatabaseHandler.class.getSimpleName();
    private DatabaseHelper dbHelper = new DatabaseHelper();
    private SQLiteDatabase mSqLiteDatabase;
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> stueckzahl = new ArrayList<>();
    private ArrayList<String> ablaufdatum = new ArrayList<>();
    private ArrayList<String> nameShoppinglistElements = new ArrayList<>();


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(LOG_TAG, "DataBaseHandler hat die Datenbank " + getDatabaseName() + " erzeugt.");
    }

    /**
     * Erstellen der Datenbank falls diese noch nicht vorhanden ist.
     **/
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            Log.d(LOG_TAG, "Die Tabelle wird mit dem Befehl:" + SQL_CREATE_FOOD_TABLE + " angelegt.");
            db.execSQL(SQL_CREATE_FOOD_TABLE);
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle food_list: " + ex.getMessage());
        }
        try {
            Log.d(LOG_TAG, "Die zweite Tabelle wird mit dem Befehl:" + SQL_CREATE_NAME_TABLE + "angelegt.");
            db.execSQL(SQL_CREATE_NAME_TABLE);
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle name_list: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + dbHelper.DATABASE_NAME);
        System.out.println("Datenbank wurde geloescht");
        onCreate(db);
    }

    /**
     * Einen Eintrag mit allen Werten in der Tabelle food_list löschen
     **/
    public void deleteRow(String name) {
        addShoppingElement(name);
        mSqLiteDatabase = this.getWritableDatabase();
        mSqLiteDatabase.delete(TABLE_FOOD_LIST, COLUMN_NAME + "=" + "\"" + name + "\";", null);
        mSqLiteDatabase.close();
    }

    /**
     * Neuen Eintrag in die Tabelle food_list einfügen.
     **/
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

    /**
     * Ändern der Stückzahl eines Eintrags der Tabelle food_list.
     **/
    public void changeStueckzahl(String name, String count, int i) {
        mSqLiteDatabase = this.getWritableDatabase();

        //decrease
        if (i == 0) {
            int countadjusted = Integer.parseInt(count) - 1;
            Integer.toString(countadjusted);


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

    /**
     * Berechnet die Zahl an Elemente der Tabelle food_list .
     *
     * @return gibt die Anzahl der Reihen der Datenbank wieder.
     **/

    //TODO Counter fuer die andere Tabelle

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FOOD_LIST;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    /**
     * Die Datenbankeinträge der Tabelle food_list werden geholt und in 4 seperaten Arraylisten abgelegt.
     **/

    //TODO getNameFromDB
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

    /**
     * Getter Methoden für die ArrayListen in denen die Datenbankeinträge der Tabelle food_list zwischengespeichert werden.
     **/

    // TODO ArrayList mit geloeschten Objekten

    public void deleteShoppingName(String name) {
        mSqLiteDatabase = this.getWritableDatabase();
        mSqLiteDatabase.delete(TABLE_NAME_LIST, COLUMN_NAME + "=" + "\"" + name + "\";", null);
        mSqLiteDatabase.close();
    }


    public void addShoppingElement(String name) {
        mSqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(COLUMN_NAME, name);
        mSqLiteDatabase.insert(TABLE_NAME_LIST, null, value);
        mSqLiteDatabase.close();
    }

    public void getShoppingList(){
        mSqLiteDatabase = this.getReadableDatabase();
        String selectAll = "select * from " + TABLE_NAME_LIST;
        Cursor c = mSqLiteDatabase.rawQuery(selectAll, null);
        nameShoppinglistElements.clear();
        if (c.moveToFirst()) {
            do {
                /* To Object */
                dbHelper.setDeltedName(c.getString(0));
                System.out.println("#####DELETED NAME######");
                System.out.println("----> " + c.getString(0));
                nameShoppinglistElements.add(c.getString(0));

            } while (c.moveToNext());
        }
        mSqLiteDatabase.close();
    }


    public int getNumberofShoppingElements(){
        String countQuery = "SELECT  * FROM " + TABLE_NAME_LIST;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count;
    }
    public ArrayList<String> getNameShoppinglistElements() {
        return nameShoppinglistElements;
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


