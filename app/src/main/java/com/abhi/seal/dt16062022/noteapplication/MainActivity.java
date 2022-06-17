package com.abhi.seal.dt16062022.noteapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.abhi.seal.dt16062022.noteapplication.SP.PrefConfig;
import com.abhi.seal.dt16062022.noteapplication.adapters.DisplayItemRecyclerAdapter;
import com.abhi.seal.dt16062022.noteapplication.db.notes.Notes;
import com.abhi.seal.dt16062022.noteapplication.db.notes.NotesDataBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    FloatingActionButton addNoteButton;
    RecyclerView recyclerView;
    DisplayItemRecyclerAdapter displayItemRecyclerAdapter;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNoteButton=findViewById(R.id.addNote);
        recyclerView=findViewById(R.id.recyclerView);

        id= PrefConfig.readIdInPref(getApplicationContext(),"id");

        addNoteButton.setOnClickListener(v->{

             startActivityForResult(new Intent(MainActivity.this,AddNote.class),100);

        });




        displayItemRecyclerAdapter=new DisplayItemRecyclerAdapter(MainActivity.this);
        recyclerView.setAdapter(displayItemRecyclerAdapter);
        loadNoteList();
    }
    private void loadNoteList(){

        NotesDataBase db=NotesDataBase.getDbInstance(this.getApplicationContext());
        List<Notes> notesList=db.notesDao().getNotes(id);
        displayItemRecyclerAdapter.setNotesList(notesList);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==100){
            loadNoteList();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void logOut(View view) {


        PrefConfig.writeIdInPref(getApplicationContext(),"","id");
        finish();
    }
}