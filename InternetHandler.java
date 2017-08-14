package com.crash.boozl.boozl.code;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class InternetHandler{//} extends BroadcastReceiver{

    Context context;
    Search activity;
    String LOG_TAG = InternetHandler.class.getSimpleName();

    public InternetHandler(){

    }


    public InternetHandler(Context context, Search activity){
        this.context = context;
        this.activity = activity;
    }


    public boolean networkCheck() {

        // If WiFi and Data are both disabled, prompt the user to enable them
//        if( !isWifiOn() && !isDataEnabled() ){
//            promptNetworkSettings();
//        }

        try {
            ConnectivityManager nInfo = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);

            nInfo.getActiveNetworkInfo().isConnectedOrConnecting();

            Log.d(LOG_TAG, "Net avail:"
                    + nInfo.getActiveNetworkInfo().isConnectedOrConnecting());
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);

            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {


                Log.d(LOG_TAG, "Network available:true");
                Toast.makeText(context, "Connected to Internet!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Log.d(LOG_TAG, "Network available:false");
                Toast.makeText(context, "No network connection! Enable network connection", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    // Check if the Wi-Fi is enabled
    public boolean isWifiOn() {
        if(context != null) {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            return wifi.isWifiEnabled();
        }
        else{
            return false;
        }
    }
    // Check if Network Data is enabled
    public boolean isDataEnabled() {
        if(context != null) {
            final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            if (networkInfo == null) {
                return false;
            } else {
                return networkInfo.isConnected();
            }
        }
        else{
            return false;
        }

    }
}
