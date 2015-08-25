package com.example.krishna.mygcmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by balakrishna on 30/6/15.
 */
public class GcmBroadCastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("GcmBroadCastReciever", "onReceive 14 ");

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String message = bundle.getString("message");
            Log.d("GcmBroadCastReciever", "onReceive 20 "+message  );
            Toast.makeText(context, "message: "+message, Toast.LENGTH_SHORT).show();

        }

    }
}
