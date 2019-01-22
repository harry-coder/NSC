package feedback.mpnsc;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;


/**
 * Created by Harpreet on 21/02/2017.
 */

public class MyApplication extends Application {

    static MyApplication application = null;

    @Override
    public void onCreate() {
        super.onCreate ( );
        application = this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder ( );
        StrictMode.setVmPolicy ( builder.build ( ) );
    }

    public static MyApplication getInstance() {
        return application;

    }

    public static Context getContext() {

        return application.getApplicationContext ( );
    }


}
