package com.example.sqlitedemo.domain.dao;

import android.content.Context;
import android.database.Cursor;

import com.example.sqlitedemo.domain.User;
import com.example.sqlitedemo.handler.DbHandler;

/**
 * Created by zcm on 2016/1/14.
 */
public class UserDao {

    DbHandler dbHandler;

    public UserDao( Context context ) {
        this.dbHandler = new DbHandler( context, "db.zcm", null, 1);
    }

    public void saveUser( User user ) {
        dbHandler.getWritableDatabase().execSQL( "insert into t_user (name, age) values(?, ?)", new Object[] { user.getUserName(), user.getAge()});
    }


    public User getByUserName(String userName ) {
        Cursor cursor = dbHandler.getReadableDatabase().rawQuery( "select * from t_user where name = ?", new String[] {userName} );
        if (cursor.moveToFirst()) {// 依次取出数据
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex("id")));
            user.setUserName(cursor.getString(cursor.getColumnIndex("name")));
            user.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            return user;
        }

        return null;
    }
}
