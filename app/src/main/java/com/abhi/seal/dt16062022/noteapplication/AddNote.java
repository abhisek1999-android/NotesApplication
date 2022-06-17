package com.abhi.seal.dt16062022.noteapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abhi.seal.dt16062022.noteapplication.SP.PrefConfig;
import com.abhi.seal.dt16062022.noteapplication.adapters.ImageRecyclerAdapter;
import com.abhi.seal.dt16062022.noteapplication.db.notes.Notes;
import com.abhi.seal.dt16062022.noteapplication.db.notes.NotesDataBase;
import com.abhi.seal.dt16062022.noteapplication.utility.Utility;

import java.io.File;
import java.util.ArrayList;

public class AddNote extends AppCompatActivity {

    EditText title,subTitle;
    TextView addImage,addImageCamera;
    RecyclerView imageRecyclerView;
    ImageRecyclerAdapter imageRecyclerAdapter;
    ArrayList<Uri> imagesList;
    String id;
    Uri imageURI;
    String uris;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        title=findViewById(R.id.title);
        subTitle=findViewById(R.id.subTitle);
        addImage=findViewById(R.id.addImage);
        addImageCamera=findViewById(R.id.addImageCamera);
        imageRecyclerView=findViewById(R.id.imageRecyclerView);
        id= PrefConfig.readIdInPref(getApplicationContext(),"id");
        imagesList=new ArrayList<>();
        addImage.setOnClickListener(v->{

            Intent intent;

            if (Build.VERSION.SDK_INT < 19) {
                intent = new Intent();
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 1);
            } else {
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, 1);
            }
        });


        addImageCamera.setOnClickListener(v->{

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
// Declare mUri as globel varibale in class
            imageURI = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "pic_"+ String.valueOf(System.currentTimeMillis()) + ".jpg"));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
            startActivityForResult(intent, 2);
        });

        imageRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3, GridLayoutManager.HORIZONTAL, false));
        imageRecyclerView.setHasFixedSize(true);


    }
    public String getRealPathFromURI(Uri contentUri)
    {
        try
        {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            System.out.println("column_index of selected image is:"+column_index);
            cursor.moveToFirst();
            System.out.println("selected image path is:"+cursor.getString(column_index));
            return cursor.getString(column_index);
        }
        catch (Exception e)
        {
            return contentUri.getPath();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 && resultCode== Activity.RESULT_OK){

            if (data.getClipData() !=null){
                int x= data.getClipData().getItemCount();
                for(int i=0;i<x;i++){

                    if (imagesList.size()<10){
                        imagesList.add(data.getClipData().getItemAt(i).getUri());
                    }

                }
            }else if(data.getData() !=null){

                Uri imgUri = data.getData();

               imagesList.add(imgUri);
            }

            imageRecyclerAdapter=new ImageRecyclerAdapter(imagesList,getApplicationContext());
            imageRecyclerView.setAdapter(imageRecyclerAdapter);

        }
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {

                if (imageURI != null) {
                    // use the same uri, that you initialized while calling camera intent
                    // do whatever you want to do with this Uri
                    if (imagesList.size()<10){
                    imagesList.add(imageURI);
                    }

                }
            }

            if (imagesList.size()>10){
                Toast.makeText(this, "More then 10 images are selected only first 10 will be stored", Toast.LENGTH_SHORT).show();
            }
            imageRecyclerAdapter=new ImageRecyclerAdapter(imagesList,getApplicationContext());
            imageRecyclerView.setAdapter(imageRecyclerAdapter);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        try{
            if (imagesList.size()>0)
                PrefConfig.writeListInPref(getApplicationContext(),imagesList,"uris");
        }catch (Exception e){}

    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            if (PrefConfig.readListFromPref(getApplicationContext(),"uris").size()>0)
                imagesList=PrefConfig.readListFromPref(getApplicationContext(),"uris");
        }catch (Exception e){}

    }



    public void addNote(View view) {

        if (TextUtils.isEmpty(title.getText().toString()) | title.getText().toString().length() < 4) {
            title.setError("Minimum 4 characters required");
            return;
        }

        if (TextUtils.isEmpty(subTitle.getText().toString()) | subTitle.getText().toString().length() < 100) {
            subTitle.setError("Minimum 100 characters required");
            return;
        }

        NotesDataBase db=NotesDataBase.getDbInstance(this.getApplicationContext());
        Notes note=new Notes();
        note.title=title.getText().toString();
        note.desc=subTitle.getText().toString();
        note.user=id;
        note.images=imagesList+"";
        db.notesDao().insertNotes(note);
        imagesList.clear();
        PrefConfig.writeListInPref(getApplicationContext(),imagesList,"uris");
        finish();

    }



}