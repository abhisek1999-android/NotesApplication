package com.abhi.seal.dt16062022.noteapplication.db.user;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="email")
    public String email;

    @ColumnInfo(name="phone")
    public String phone;

    @ColumnInfo(name="password")
    public String password;
}
