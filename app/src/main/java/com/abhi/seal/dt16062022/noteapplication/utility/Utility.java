package com.abhi.seal.dt16062022.noteapplication.utility;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utility {

    public List<String> sToList(String uris){

        String imageUri=uris.trim();
        imageUri.replace("[","");
        String[] sArr = uris.split(",");
        List<String> sList =  Arrays.asList(sArr);
        return sList;
    }

}
