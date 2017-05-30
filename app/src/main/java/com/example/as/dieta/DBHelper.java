//package com.example.as.dieta;
//
///**
// * Created by as on 06.05.2017.
// */
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.DatabaseUtils;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//
//public class DBHelper extends SQLiteOpenHelper {
//
//    public static final String DATABASE_NAME = "MyDB.db";
////    public static final String CONTACTS_TABLE_NAME = "contacts";
////    public static final String CONTACTS_COLUMN_ID = "id";
////    public static final String CONTACTS_COLUMN_CURRENCY = "currency";
////    public static final String CONTACTS_COLUMN_CODE = "code";
////    public static final String CONTACTS_COLUMN_MID = "mid";
////    public static final String CONTACTS_COLUMN_DATE = "date";
////
////    public static final String FAVORITE_TABLE_NAME = "favorite";
////    public static final String FAVORITE_COLUMN_CODE = "code";
//
//    private HashMap hp;
//
//    public DBHelper(Context context) {
//        super(context, DATABASE_NAME , null, 1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        // TODO Auto-generated method stub
//        db.execSQL(
//                "create table measurements " +
//                        "(id integer primary key, calories text, date text)"
//        );
//
////        db.execSQL(
////                "create table contacts " +
////                        "(id integer primary key, currency text,code text,mid text,date text)"
////        );
//
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // TODO Auto-generated method stub
//        db.execSQL("DROP TABLE IF EXISTS measurements");
////        db.execSQL("DROP TABLE IF EXISTS favorite");
//        onCreate(db);
//    }
//
////    public boolean insertContact (String currency, String code, String mid, String date) {
////        SQLiteDatabase db = this.getWritableDatabase();
////        ContentValues contentValues = new ContentValues();
////        contentValues.put("currency", currency);
////        contentValues.put("code", code);
////        contentValues.put("mid", mid);
////        contentValues.put("date", date);
////        db.insert("contacts", null, contentValues);
////        return true;
////    }
//
//    public boolean insertMeasurement (String calories, String date) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("calories", calories);
//        contentValues.put("date", date);
//        db.insert("measurements", null, contentValues);
//        return true;
//    }
////
////    public boolean insertFavorite (String code) {
////        SQLiteDatabase db = this.getWritableDatabase();
////        ContentValues contentValues = new ContentValues();
////        contentValues.put("code", code);
////        db.insert("favorite", null, contentValues);
////        return true;
////    }
//
//    public Cursor getLastMeasurements() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res =  db.rawQuery( "select * from measurements order by id desc limit 35", null );
//        return res;
//    }
////
////
////    public Cursor getData(int id) {
////        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
////        return res;
////    }
////
////    public Cursor getDistinctCode() {
////        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor res =  db.rawQuery( "select distinct code from contacts", null );
////        return res;
////    }
////
////    public Cursor getLastMidByCode(String code) {
////        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor res =  db.rawQuery( "select mid from contacts where code=? ORDER BY DATE DESC LIMIT 1", new String[] {code});
////        return res;
////    }
////
////    public Cursor getDataByCode(String code) {
////        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor res =  db.rawQuery( "select DISTINCT * from contacts where code =? ORDER BY date DESC", new String[] {code});
////        return res;
////        //Cursor cursor = db.rawQuery("SELECT id,lastname FROM people WHERE lastname = ?; ", new String[] {"John Kenedy"});
////    }
////
////    public Cursor getLastDate() {
////        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor res =  db.rawQuery( "select * from contacts order by id desc limit 1", null );
////        return res;
////    }
////    public Cursor getLastData() {
////        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor res =  db.rawQuery( "select * from contacts order by id desc limit 35", null );
////        return res;
////    }
////
////    public Cursor getLastDataFavorite() {
////        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor res =  db.rawQuery( "select * from contacts WHERE code IN(select code from favorite) order by id desc limit (select count(*) from favorite order by code)", null );
////        return res;
////    }
////
////    public Cursor getCountAll() {
////        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor res =  db.rawQuery( "select * from contacts", null );
////        return res;
////    }
//    //    public Cursor getAllData(int id) {
////        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor res =  db.rawQuery( "select * from contacts", null );
////        return res;
////    }
////    public Cursor getFavorite(String code) {
////        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor res =  db.rawQuery( "select code from favorite where code=?", new String[] {code} );
////        return res;
////    }
//
////    public Cursor getDataByDate(int id, String date) {
////        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor res =  db.rawQuery( "select * from contacts where date="+date+"", null );
////        return res;
////    }
////
////    public int numberOfRows(){
////        SQLiteDatabase db = this.getReadableDatabase();
////        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
////        return numRows;
////    }
////
////    public boolean updateCurrency (Integer id, String currency, String code, String mid, String date) {
////        SQLiteDatabase db = this.getWritableDatabase();
////        ContentValues contentValues = new ContentValues();
////        contentValues.put("currency", currency);
////        contentValues.put("code", code);
////        contentValues.put("mid", mid);
////        contentValues.put("date", date);
////        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
////        return true;
////    }
////
////    public Integer deleteCurrency (Integer id) {
////        SQLiteDatabase db = this.getWritableDatabase();
////        return db.delete("contacts",
////                "id = ? ",
////                new String[] { Integer.toString(id) });
////    }
////
////    public Integer deleteFavorite (String code) {
////        SQLiteDatabase db = this.getWritableDatabase();
////        return db.delete("favorite",
////                "code = ? ",
////                new String[] { code });
////    }
//
//
////    public ArrayList<String> getAllCurrency() {
////        ArrayList<String> array_list = new ArrayList<String>();
////
////        //hp = new HashMap();
////        SQLiteDatabase db = this.getReadableDatabase();
////        Cursor res =  db.rawQuery( "select * from contacts", null );
////        res.moveToFirst();
////
////        while(res.isAfterLast() == false){
////            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_CURRENCY)));
////            res.moveToNext();
////        }
////        return array_list;
////    }
//}