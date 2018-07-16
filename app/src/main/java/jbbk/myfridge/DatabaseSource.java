package jbbk.myfridge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**@deprecated **/
public class DatabaseSource {

    private static final String LOG_TAG = DatabaseSource.class.getSimpleName();

    private DatabaseHandler databaseHandler;
    private SQLiteDatabase database;

    public DatabaseSource(Context context) {
        Log.d(LOG_TAG, " DataHandler wird erzeugt.");
        databaseHandler = new DatabaseHandler(context);
    }

    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = databaseHandler.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        databaseHandler.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des databaseHandlers geschlossen.");
    }
}
