package feedback.mpnsc.CustomClasses;

import android.app.Dialog;
import android.content.Context;

import feedback.mpnsc.R;

public class ConnectivityDialog {

    DialogBox dialogBox;
    Context context;


    public ConnectivityDialog(Context context) {
        this.context=context;
    }

    public  void showConnectivityDialog(){

        dialogBox=new DialogBox ( context );

        Dialog dialog=dialogBox.setRequestedDialog ( true, R.layout.no_internet_dialog);

        dialog.show ();


    }
}
