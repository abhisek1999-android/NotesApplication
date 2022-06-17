package com.abhi.seal.dt16062022.noteapplication.db.notes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes")
    List<Notes> getAllNotes();

    @Query("SELECT * FROM notes WHERE user=:id")
    List<Notes> getNotes(String id);

    @Insert
    void insertNotes(Notes...notes);

    @Delete
    void delete(Notes notes);
}
