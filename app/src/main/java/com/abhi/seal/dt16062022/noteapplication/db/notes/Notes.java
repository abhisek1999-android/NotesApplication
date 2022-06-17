package com.abhi.seal.dt16062022.noteapplication.db.notes;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Notes {


    @PrimaryKey(autoGenerate = true)
    public int note_id;

    @ColumnInfo(name="title")
    public String title;

    @ColumnInfo(name="desc")
    public String desc;

    @ColumnInfo(name="user")
    public String user;

    @ColumnInfo(name="images")
    public String images;

}
