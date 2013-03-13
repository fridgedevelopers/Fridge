
package com.fridge.util;

import android.app.IntentService;
import android.content.Intent;

public class FridgeService extends IntentService
{
    public static final String INBOUND_MESSAGE = "in_msg";
    public static final String OUTBOUND_MESSAGE = "out_msg";
    public static final String DATA_APP_REQUEST = "";
    
    public FridgeService()
    {
        super("FridgeService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        
    }
}
