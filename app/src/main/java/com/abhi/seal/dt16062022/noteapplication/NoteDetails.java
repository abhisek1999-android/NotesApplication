package com.abhi.seal.dt16062022.noteapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.abhi.seal.dt16062022.noteapplication.adapters.ImageRecyclerAdapter;
import com.abhi.seal.dt16062022.noteapplication.adapters.ImageRecyclerAdapterDetails;
import com.abhi.seal.dt16062022.noteapplication.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class NoteDetails extends AppCompatActivity {

    EditText title,subTitle;
    RecyclerView imageRecyclerView;
    String titleText,description;
    List<String>images;
    ImageRecyclerAdapterDetails imageRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        title=findViewById(R.id.title);
        subTitle=findViewById(R.id.subTitle);
        imageRecyclerView=findViewById(R.id.imageRecyclerView);
        titleText=getIntent().getStringExtra("title");
        description=getIntent().getStringExtra("desc");

        title.setText(titleText);
        subTitle.setText(description);
        images=new ArrayList<>();

        images=new Utility().sToList(getIntent().getStringExtra("images"));

        imageRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        imageRecyclerView.setHasFixedSize(true);
        imageRecyclerAdapter=new ImageRecyclerAdapterDetails(images,getApplicationContext());
        imageRecyclerView.setAdapter(imageRecyclerAdapter);


    }
}