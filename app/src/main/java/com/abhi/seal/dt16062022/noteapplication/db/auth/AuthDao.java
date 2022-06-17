package com.abhi.seal.dt16062022.noteapplication.db.auth;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.abhi.seal.dt16062022.noteapplication.db.user.User;

import java.util.List;

@Dao
public interface AuthDao {
    @Query("SELECT * FROM auth")
    List<Auth> getUsers();

    @Insert
    void insertUser(Auth...auths);

    @Delete
    void delete(Auth auth);
}
