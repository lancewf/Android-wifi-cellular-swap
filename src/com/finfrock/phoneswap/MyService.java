package com.finfrock.phoneswap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyService extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Tools tools = new Tools(context);
        tools.testMode(context);
    }
}
