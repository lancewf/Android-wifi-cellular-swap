package com.finfrock.phoneswap;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;

public class Tools
{
    private Context context;
    
    public Tools(Context context){
        this.context = context;
    }
    
    public List<String> getWifiSsidsAllowed(){
        WifiSsidDbAdapter wifiSsidDbAdapter =  new WifiSsidDbAdapter(context);
        wifiSsidDbAdapter.open();
        List<String> wifiSsids = wifiSsidDbAdapter.getAllWifiSsids();
        wifiSsidDbAdapter.close();
        
        return wifiSsids;
    }
    
    public void addCurrentWifiConnection(Context context)
    {
        WifiManager wifiMgr = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.isWifiEnabled())
        {
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();

            if (wifiInfo != null && wifiInfo.getSSID() != null
                    && !getWifiSsidsAllowed().contains(wifiInfo.getSSID()))
            {
                addWifiSsid(wifiInfo.getSSID());
                testMode(context);
            }
        }
    }
    
    private long addWifiSsid(String wifiSsid){
        WifiSsidDbAdapter wifiSsidDbAdapter =  new WifiSsidDbAdapter(context);
        wifiSsidDbAdapter.open();
        long id = wifiSsidDbAdapter.createWifiSsid(wifiSsid);
        wifiSsidDbAdapter.close();
        
        return id;
    }
    
    public void testMode(Context context){
        Log.w("DEBUG", "testMode");
        if(isAtHome(context)){
            atHome(context);
        }
        else {
            away(context);
        }
    }
    
    public void atHome(Context context){
        if (!isAirplaneModeOn(context))
        {
            Settings.System.putInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 1);
            Intent intent1 = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            intent1.putExtra("state", 1);
            context.sendBroadcast(intent1);
        }
    }
    
    public void away(Context context)
    {
        if (isAirplaneModeOn(context))
        {
            Settings.System.putInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0);
            Intent intent1 = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            intent1.putExtra("state", 0);
            context.sendBroadcast(intent1);
        }
    }
    
    public String getStatus(Context context)
    {
        String status = "";

        if (isAirplaneModeOn(context))
        {
            status += "Airplane Mode: On\n";
        } else
        {
            status += "Airplane Mode: Off\n";
        }

        if (isAtHome(context))
        {
            status += "Is At Home: Yes\n";
        } else
        {
            status += "Is At Home: No\n";
        }

        WifiManager wifiMgr = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.isWifiEnabled())
        {
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();

            if (wifiInfo != null && wifiInfo.getSSID() != null)
            {
                status += "Wifi Name: " + wifiInfo.getSSID();
            }
            else{
                status += "Wifi no wifi info";
            }
        }
        else{
            status += "Wifi not enabled";
        }
        
        status += "\n SSIDs: ";
        
        for(String wifiSsid : getWifiSsidsAllowed()){
            status += wifiSsid + ", ";
        }
        
        status = status.substring(0, status.length()-2);

        return status;
    }
    
    public boolean isAirplaneModeOn(Context context){
        return Settings.System.getInt(
                context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) == 1;
    }
    
    public boolean isAtHome(Context context)
    {
        WifiManager wifiMgr = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.isWifiEnabled())
        {
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();

            if (wifiInfo != null && wifiInfo.getSSID() != null
                    && getWifiSsidsAllowed().contains(wifiInfo.getSSID()))
            {
                return true;
            }
        }
        
        return false;
    }
}
