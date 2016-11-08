package com.egco28.a13262;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Natamon Tangmo on 04-Nov-16.
 */
public class SQLHelper extends SQLiteOpenHelper {
    public static final String TABLE_FORTUNES = "fortunes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RESULT = "results";
    public static final String COLUMN_DATETIME = "datetime";
    public static final String COLUMN_PICINDEX = "picindex";

    private static final String DATABASE_NAME = "Fortunes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_FORTUNES + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_RESULT + " text not null, "
            + COLUMN_DATETIME + " text not null, "
            + COLUMN_PICINDEX + " integer default 0);";

    public SQLHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){ database.execSQL(DATABASE_CREATE); }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORTUNES);
        onCreate(db);
    }




}
