package srauhbaasch.chucks;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class InternetConnectionService {
    public static boolean hasConnection(Context context){
        if(context == null){
            return false;
        }

        boolean status = false;

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(cm != null){
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                status = networkInfo != null && networkInfo.isConnectedOrConnecting();
            }
        return status;
    }
}
