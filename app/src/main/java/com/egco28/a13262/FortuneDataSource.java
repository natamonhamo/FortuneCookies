package com.egco28.a13262;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natamon Tangmo on 04-Nov-16.
 */
public class FortuneDataSource{
    private SQLiteDatabase database;
    private SQLHelper dbhelper;
    private String[] allColumns = {SQLHelper.COLUMN_ID, SQLHelper.COLUMN_RESULT, SQLHelper.COLUMN_DATETIME, SQLHelper.COLUMN_PICINDEX};

    public FortuneDataSource(Context context){
        dbhelper = new SQLHelper(context);
    }

    public void open() throws SQLException {
        database = dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }

    public Fortune createResult(String result, String datetime, int picindex){
        ContentValues values = new ContentValues();
        values.put(SQLHelper.COLUMN_RESULT, result);
        values.put(SQLHelper.COLUMN_DATETIME, datetime);
        values.put(SQLHelper.COLUMN_PICINDEX, picindex);
        long insertID = database.insert(SQLHelper.TABLE_FORTUNES,null,values);
        Cursor cursor = database.query(SQLHelper.TABLE_FORTUNES,allColumns, SQLHelper.COLUMN_ID+ " = " +insertID,null,null,null,null);
        cursor.moveToFirst();
        Fortune newResult = cursorToResult(cursor);
        cursor.close();
        return newResult;
    }

    public void deleteResult(Fortune selectedfortune){
        long id = selectedfortune.getId();
        System.out.println("Comment deleted with id: "+id);
        database.delete(SQLHelper.TABLE_FORTUNES, SQLHelper.COLUMN_ID+ " = "+id,null);
    }

    public List<Fortune> getAllFortunes(){
        List<Fortune> fortunes = new ArrayList<Fortune>();
        Cursor cursor = database.query(SQLHelper.TABLE_FORTUNES, allColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Fortune fortuneResult = cursorToResult(cursor);
            fortunes.add(fortuneResult);
            cursor.moveToNext();
        }
        return fortunes;
    }

    public Fortune cursorToResult(Cursor cursor){
        Fortune fortuneResult = new Fortune(cursor.getLong(0), cursor.getString(1), cursor.getString(2),cursor.getInt(3));
        return fortuneResult;
    }
}
