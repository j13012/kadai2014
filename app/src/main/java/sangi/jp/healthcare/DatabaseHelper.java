package sangi.jp.healthcare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by J13012 on 2015/01/27.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pedometer.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "history";
    private static final String ID = "id";
    private static final String CREATED = "date";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE_NAME + "(" + ID + "INTEGER PRIMARY KEY AUTOINCREMENT," + CREATED + "TEXT," + LATITUDE + "TEXT," + LONGITUDE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
}
