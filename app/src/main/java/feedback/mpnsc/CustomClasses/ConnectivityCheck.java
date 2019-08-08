package feedback.mpnsc.CustomClasses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityCheck {

    Context context;
    public ConnectivityCheck(Context context){

       this.context=context;
    }

    //check internet connection
    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService ( Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = manager.getActiveNetworkInfo ( );
        if (networkInfo != null && networkInfo.isConnectedOrConnecting ( ) && networkInfo.getDetailedState ( ) == NetworkInfo.DetailedState.CONNECTED) {


            return true;

        } else if (networkInfo != null && networkInfo.getDetailedState ( ) == NetworkInfo.DetailedState.DISCONNECTED) {

            return false;
        } else return false;
    }

}
