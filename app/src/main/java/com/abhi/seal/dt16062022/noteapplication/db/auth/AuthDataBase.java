package com.abhi.seal.dt16062022.noteapplication.db.auth;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.abhi.seal.dt16062022.noteapplication.db.user.User;
import com.abhi.seal.dt16062022.noteapplication.db.user.UserDao;
import com.abhi.seal.dt16062022.noteapplication.db.user.UserDataBase;
@Database(entities = {Auth.class},version = 1)
public abstract class AuthDataBase extends RoomDatabase {
    public abstract AuthDao authDao();

    private static AuthDataBase INSTANCE;

    public static  AuthDataBase getDbInstance(Context context){

        if (INSTANCE==null){

            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),AuthDataBase.class,"auth_db")
                    .allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
