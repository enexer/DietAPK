package com.example.as.dieta;

/**
 * Created by as on 08.05.2017.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */

    public boolean insertMeasurement (String calories, String date) {
       // SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("calories", calories);
        contentValues.put("date", date);
        database.insert("measurements", null, contentValues);
        return true;
    }
    public Cursor getLastMeasurements() {
       // SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  database.rawQuery( "select * from measurements order by id desc limit 35", null );
        return res;
    }

    public List<String> getQuotes() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select B.c0name || ', ' || A.kcal || ', ' || A.B || ', ' || A.T || ', ' || A.W\n" +
                "from product_names_content B, products A\n" +
                "where A._id=B.docid ORDER BY B.c0name ", null);
        //  Cursor cursor = database.rawQuery("select calories from measurements", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<String> findQuotes(String name) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select B.c0name || ', ' || A.kcal || ', ' || A.B || ', ' || A.T || ', ' || A.W\n" +
                "from product_names_content B, products A\n" +
                "where A._id=B.docid " +
                "and B.c0name LIKE '"+name+"%'" +
                "ORDER BY B.c0name", null);
        //  Cursor cursor = database.rawQuery("select calories from measurements", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public Cursor getProduct(String id) {
        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  database.rawQuery( "select A._id, A.kcal, A.B, A.T, A.W, B.c0name\n" +
                "from product_names_content B, products A\n" +
                "where A._id=B.docid " +
                "and B.docid='"+id+"' ORDER BY B.c0name", null );
        return res;
    }
    public Cursor getProducts() {
        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  database.rawQuery( "select A._id, A.kcal, A.B, A.T, A.W, B.c0name\n" +
                "from product_names_content B, products A\n" +
                "where A._id=B.docid ORDER BY B.c0name", null );
        return res;
    }
    public Cursor findProducts(String name) {
        Cursor res =  database.rawQuery( "select A._id, A.kcal, A.B, A.T, A.W, B.c0name\n" +
                "from product_names_content B, products A\n" +
                "where A._id=B.docid " +
                "and B.c0name LIKE '"+name+"%'" +
                "ORDER BY B.c0name", null);
        return res;
    }
    public boolean  insertFavorite (String code) {
        // SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("quote", code);
        database.insert("quotes", null, contentValues);
        return true;
    }
}