package com.abhi.seal.dt16062022.noteapplication.db.notes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.abhi.seal.dt16062022.noteapplication.db.user.User;
import com.abhi.seal.dt16062022.noteapplication.db.user.UserDao;
import com.abhi.seal.dt16062022.noteapplication.db.user.UserDataBase;

@Database(entities = {Notes.class},version = 1)
public abstract class NotesDataBase extends RoomDatabase {

    public abstract NotesDao notesDao();

    private static NotesDataBase INSTANCE;

    public static  NotesDataBase getDbInstance(Context context){

        if (INSTANCE==null){

            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),NotesDataBase.class,"notes_db")
                    .allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

}
