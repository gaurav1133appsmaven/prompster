package com.scriptively.app.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.scriptively.app.Pojo.DeleteListStory;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Util {

    public static boolean flag = false;
    public static boolean flagPos = false;
    public static boolean flagFirst = true;


    public static boolean checkDropbox = false;
    public static List<DeleteListStory> deleteListStory = new ArrayList<DeleteListStory>();

    public static int pos = 0;

    public static boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

}
