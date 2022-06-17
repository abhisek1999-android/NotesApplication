package com.abhi.seal.dt16062022.noteapplication.db.user;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class},version = 1)
public abstract class UserDataBase extends RoomDatabase {

    public abstract UserDao userDao();

    private static UserDataBase INSTANCE;

    public static  UserDataBase getDbInstance(Context context){

        if (INSTANCE==null){

            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),UserDataBase.class,"user_db")
                    .allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

}
