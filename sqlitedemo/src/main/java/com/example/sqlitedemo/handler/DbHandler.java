package com.example.sqlitedemo.handler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlitedemo.domain.User;

/**
 * Created by zcm on 2016/1/14.
 */
public class DbHandler extends SQLiteOpenHelper {


    public DbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version ) {
        super( context, name, factory, version );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "create table t_user( id integer  primary key autoincrement,  name varchar(20), age int)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
