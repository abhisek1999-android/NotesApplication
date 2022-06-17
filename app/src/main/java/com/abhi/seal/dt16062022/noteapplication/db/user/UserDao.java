package com.abhi.seal.dt16062022.noteapplication.db.user;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getUsers();


    @Query("SELECT * FROM user WHERE email= :userId AND password=:password")
    List<User> fetchAllData(String userId,String password);

    @Insert
    void insertUser(User...users);

    @Delete
    void delete(User user);
}
