package com.alee.paradow.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringRefactor {
    public static String getENstring(String word){

        Pattern p = Pattern.compile("[a-zA-Z']{2,}");

        Matcher m = p.matcher(word);

        StringBuilder res = new StringBuilder();
        while (m.find()){
            res.append(m.group()).append(" ");
        }
        // to remove _ar_ from last
        res = new StringBuilder(res.substring(2, res.length() - 4));
        return res.toString();
    }

    public static List<Long> getuserFavList(String userFav) {
        List<Long> res = new ArrayList<Long>();
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(userFav);
        while (m.find()) {
            res.add(Long.valueOf(m.group()));
            Log.i("Home frag",m.group() +" User Fav");
            Log.i("Home frag",userFav +" User Fav");
        }
        return res;
    }
}
