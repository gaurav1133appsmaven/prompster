package com.scriptively.app.Utils;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

public class YourService extends IntentService {

    public YourService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        boolean isNetworkConnected = extras.getBoolean("isNetworkConnected");
        // your code

    }
}
