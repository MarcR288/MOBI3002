package com.codelab.basics;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;


/**
 * Created by w0091766 on 4/29/2016.
 */
public class DBClass extends SQLiteOpenHelper implements DB_Interface {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "DB_Name.db";

    // If you change the database schema, you must increment the database version.
    private static final String TABLE_NAME = "sample_table";
    private static final String TEXT_TYPE = " TEXT";
    private static final String NUM_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String _ID = "id";
    private static final String _COL_1 = "name";
    private static final String _COL_2 = "type";
    private static final String _COL_3 = "access_count";


    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" + COMMA_SEP +
                    _COL_1 + TEXT_TYPE + COMMA_SEP +
                    _COL_2 + TEXT_TYPE + COMMA_SEP +
                    _COL_3 + NUM_TYPE + ")";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public DBClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    /**
     * This JavaDoc goes with this method type / * * and hit enter
     */
    public void onCreate(SQLiteDatabase db) {

        Log.d("DBClass", "DB onCreate() " + SQL_CREATE_TABLE);
        db.execSQL(SQL_CREATE_TABLE);
        Log.d("DBClass", "DB onCreate()");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        Log.d("DBClass", "DB onUpgrade() to version " + DATABASE_VERSION);
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    /////////// Implement Interface ///////////////////////////
    @Override
    public int count() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        Log.v("DBClass", "getCount=" + cnt);
        return cnt;
    }

    @Override
    public int save(DataModel dataModel) {
        //String command = "INSERT INTO CarModels(str_col,num_col) VALUES('" + carModel.getModelName() + "', " + carModel.getModelNumber() + ")";

        Log.v("DBClass", "add=>  " + dataModel.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(_COL_1, dataModel.getName());
        values.put(_COL_2, dataModel.getType());
        values.put(_COL_3, dataModel.getAccessCount());

        // 3. insert
        db.insert(TABLE_NAME, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();

        // debug output to see what we're doing
        dump();

        return 0;
    }

    @Override
    public int update(DataModel dataModel) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public int deleteById(int id) {
        return 0;
    }


    // Add Sample rows
    private void addDefaultRows() {
        // Call count once
        int doCount = this.count();
        if (doCount == 0) {
            Log.v("DBClass", "no rows in DB...add some");
            DataModel a = new DataModel(1, "Bulbasaur", "Grass", 0);
            this.save(a);
            a = new DataModel(2, "Charmander", "Fire", 0);
            this.save(a);
            a = new DataModel(3, "Squirtle", "Water", 0);
            this.save(a);
            a = new DataModel(4, "Caterpie", "Bug", 0);
            this.save(a);
            a = new DataModel(5, "Pidgey", "Normal", 0);
            this.save(a);
            a = new DataModel(6, "Rattata", "Normal", 0);
            this.save(a);

            /*DataModel favourite = findFavourite();
            if (favourite != null) {
                // If there is a favorite, save it with the highest access count
                Log.v("DBClass", "Setting Favourite to ID: " + favourite.getId());

            } else {
                Log.v("DBClass", "already rows in DB");
            }
            */
            //DataModel a = new DataModel(4, "Rusty Pokemon", "Rusty");
            //this.save(a);
            //Log.v("RUSTY", "ADDED RUSTY");
        }
    }
    @Override
    public List<DataModel> findAll() {
        List<DataModel> temp = new ArrayList<DataModel>();

        // if no rows, add
        addDefaultRows();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_NAME;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build and add it to list
        DataModel item;
        if (cursor.moveToFirst()) {
            do {
                // This code puts a dataModel object into the PlaceHolder for the fragment
                // if you had more columns in the DB, you'd format  them in the non-details
                // list here
                item = new DataModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
                temp.add(item);
            } while (cursor.moveToNext());
        }

        Log.v("DBClass", "findAll=> " + temp.toString());

        // Sort by accessCount
        temp.sort(Comparator.comparingInt(DataModel::getAccessCount).reversed());

        return temp;

    }



    @Override
    public String getNameById(Long id) {
        return "";
    }

    @Override
    public String getNameById(int id) {
        return null;
    }

    // Dump the DB as a test
    private void dump() {
    }  // oops, never got around to this...but findall is dump-ish


    public int updateAccessCount(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        int currentCount = getAccessCount(id);
        values.put(_COL_3, currentCount + 1);

        // Update row where id matches
        int rowsAffected = db.update(TABLE_NAME, values, _ID + "=?", new String[]{String.valueOf(id)});
        Log.d("DBClass", "Updated access count for ID: " + id + ", New Count: " + (currentCount + 1) + ", Rows Affected: " + rowsAffected);

        db.close();
        return rowsAffected;
    }

    private int getAccessCount(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{_COL_3}, _ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int accessCount = cursor.getInt(0);
            cursor.close();
            return accessCount;
        }

        return 0; // Return 0 if not found
    }

    public DataModel findFavourite(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + _COL_3 + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);

        DataModel favourite = null;
        if (cursor.moveToFirst()) {
            favourite = new DataModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        }

        cursor.close();
        return favourite;
    }

}
