package com.abhi.seal.dt16062022.noteapplication.db.auth;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Auth {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    public String email;

    @ColumnInfo(name="pass")
    public String pass;
}
