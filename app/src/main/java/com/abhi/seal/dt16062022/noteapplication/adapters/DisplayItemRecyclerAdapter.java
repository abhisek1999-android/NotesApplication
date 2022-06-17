package com.abhi.seal.dt16062022.noteapplication.adapters;

import static android.provider.Telephony.Mms.Part._DATA;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi.seal.dt16062022.noteapplication.MainActivity;
import com.abhi.seal.dt16062022.noteapplication.NoteDetails;
import com.abhi.seal.dt16062022.noteapplication.R;
import com.abhi.seal.dt16062022.noteapplication.utility.Utility;
import com.abhi.seal.dt16062022.noteapplication.db.notes.Notes;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class DisplayItemRecyclerAdapter extends RecyclerView.Adapter<DisplayItemRecyclerAdapter.ViewHolder> {

    List<Notes> notesList;
    List<String> images=new ArrayList<>();
    Context mContext;

    public DisplayItemRecyclerAdapter( Context mContext) {

        this.mContext=mContext;
    }

    public void setNotesList(List<Notes> list){

        this.notesList=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notes_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.title.setText(notesList.get(position).title);
        holder.subTitle.setText(notesList.get(position).desc);

        images=new Utility().sToList(notesList.get(position).images);


   Glide.with(mContext).load(images.get(0).replace("[","").replace("]","").trim()).into(holder.thumbImage);


        holder.mView.setOnClickListener(v->{
           Intent intent=new Intent(mContext, NoteDetails.class);
           intent.putExtra("title",notesList.get(position).title);
           intent.putExtra("desc",notesList.get(position).desc);
           intent.putExtra("images",notesList.get(position).images);
           mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public String getImagePath(String uriString){
        String realPath = null;


        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat) {
            Cursor cursor =  mContext.getContentResolver().query(Uri.parse(uriString), null, null, null, null);
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":")+1);
            cursor.close();

            cursor =  mContext.getContentResolver().query(
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            cursor.moveToFirst();
            @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            realPath = path;
            cursor.close();
        }else{

            if (uriString.startsWith("content://")) {
                String[] proj = { _DATA };
                Cursor cursor =	((MainActivity)mContext).managedQuery(Uri.parse(uriString), proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(_DATA);
                cursor.moveToFirst();
                realPath = cursor.getString(column_index);
                if (realPath == null) {
//                    Log.e("LOG_TAG", "Could get real path for URI string %s", uriString);
                }
            } else if (uriString.startsWith("file://")) {
                realPath = uriString.substring(7);
                if (realPath.startsWith("/android_asset/")) {
//                    Log.e("LOG_TAG", "Cannot get real path for URI string %s because it is a file:///android_asset/ URI.", uriString);
                    realPath = null;
                }
            } else {
                realPath = uriString;
            }

        }
        return realPath;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,subTitle;
        ImageView thumbImage;
        View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            title = itemView.findViewById(R.id.title);
            subTitle = itemView.findViewById(R.id.subTitle);
            thumbImage=itemView.findViewById(R.id.thumbImage);

        }
    }
}
