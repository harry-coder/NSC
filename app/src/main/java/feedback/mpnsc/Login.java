package feedback.mpnsc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by swatiG on 29-06-2015.
 */
public class Login extends Activity {
    EditText edt_login;
    EditText edt_pwd;
    TextView txt_resetLogin;
    Button btn_login;
    String str_login, str_pwd;
    ProgressDialog pd;
    ConnectionDetector cd;
    SessionManager sessionManager;
    TextView tv_reset;
    String response, network_interrupt;
    String str_username, str_old_password, str_new_password;
    Dialog login;
    ConnectionDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.login );
        loadView ( );
        detector = new ConnectionDetector ( Login.this );
        cd = new ConnectionDetector ( Login.this );
        sessionManager = new SessionManager ( Login.this );
        btn_login.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                str_login = edt_login.getText ( ).toString ( ).trim ( );
                str_pwd = edt_pwd.getText ( ).toString ( ).trim ( );
                if ((str_login.trim ( ).length ( ) > 0) && (str_pwd.trim ( ).length ( ) > 0)) {
                    if (cd.isConnectingToInternet ( )) {
                        //  Log.e("URL",""+sessionManager.GET_URL());
                        // Toast.makeText(getBaseContext(),""+sessionManager.GET_URL(),Toast.LENGTH_LONG).show();
                        pd = new ProgressDialog ( Login.this, R.style.MyAlertDialogStyle );

                        new ShowProgressLogin ( str_login, str_pwd ).execute ( );

                    } else {
                        Toast.makeText ( getBaseContext ( ), "internet not connected", Toast.LENGTH_LONG ).show ( );
                    }
                } else {
                    Toast.makeText ( getBaseContext ( ), "some text missing", Toast.LENGTH_LONG ).show ( );
                }
            }
        } );
        txt_resetLogin.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                reset_value ( );
            }
        } );
    }

    public void reset_value() {
        login = new Dialog ( Login.this );
        // Set GUI of login screen
        login.setContentView ( R.layout.reset_login );
        login.setTitle ( "Reset password..." );
        final EditText edt_username = (EditText) login.findViewById ( R.id.username );
        final EditText edt_old_password = (EditText) login.findViewById ( R.id.old_password );
        final EditText edt_new_password = (EditText) login.findViewById ( R.id.new_password );
        final Button btn_reset = (Button) login.findViewById ( R.id.btn_reset );

        btn_reset.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                str_username = edt_username.getText ( ).toString ( ).trim ( );
                str_old_password = edt_old_password.getText ( ).toString ( ).trim ( );
                str_new_password = edt_new_password.getText ( ).toString ( ).trim ( );
                if (TextUtils.isEmpty ( str_username ) || TextUtils.isEmpty ( str_old_password ) || TextUtils.isEmpty ( str_new_password )) {
                    Toast.makeText ( getApplicationContext ( ), "all fields are mandatory", Toast.LENGTH_SHORT ).show ( );
                } else {
                    if (detector.isConnectingToInternet ( )) {
                        //async
                        new ResetPassword ( ).execute ( );
                    } else {
                        ShowAlertUpdate ( "Internet not connected" );
                    }
                }
            }
        } );
        login.show ( );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater ( );
        inflater.inflate ( R.menu.menu_main, menu );
        return super.onCreateOptionsMenu ( menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ( )) {
            // action with ID action_refresh was selected
            case R.id.action_settings:
                ShowAlertSetting ( );
                break;
            default:
                break;
        }
        return true;
    }

    public void ShowAlertSetting() {
        Login.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( Login.this );
                builder.setTitle ( "Enter master password:" );
                final EditText input = new EditText ( Login.this );
                input.setSingleLine ( );

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams (
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT );
                input.setLayoutParams ( lp );
                builder.setView ( input )
                        .setPositiveButton ( "OK", new DialogInterface.OnClickListener ( ) {
                            public void onClick(DialogInterface dialog, int id) {
                                if (input.getText ( ).toString ( ).equals ( "102901" )) {
                                    ShowAlertAgain ( );
                                } else {
                                    ShowAlertChange ( "password not matched" );
                                }
                            }
                        } );
                AlertDialog alert = builder.create ( );
                alert.show ( );
            }
        } );
    }

    public void ShowAlertAgain() {
        Login.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( Login.this, R.style.MyAlertDialogStyle );
                builder.setTitle ( "Change URL" );
                final EditText input = new EditText ( Login.this );
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams (
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT );
                input.setLayoutParams ( lp );
                builder.setView ( input )
                        .setPositiveButton ( "OK", new DialogInterface.OnClickListener ( ) {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.e ( "input", "." + input.getText ( ).toString ( ) );
                                if (TextUtils.isEmpty ( input.getText ( ).toString ( ) )) {
                                    ShowAlertChange ( "URL cannot be blank" );
                                } else {
                                    sessionManager.change_url ( input.getText ( ).toString ( ) );
                                    ShowAlertChange ( "successfully changed" );
                                }
                            }
                        } );
                AlertDialog alert = builder.create ( );
                alert.show ( );
            }
        } );
    }

    public void ShowAlertChange(final String str_msg) {
        Login.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( Login.this, R.style.MyAlertDialogStyle );
                builder.setCancelable ( false );
                builder.setTitle ( "Alert" );
                builder.setMessage ( str_msg );
                builder.setPositiveButton ( "OK", new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                } );

                AlertDialog alert = builder.create ( );
                alert.show ( );
            }
        } );
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack ( );
    }

    public void ShowAlertonBack() {
        Login.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( Login.this, R.style.MyAlertDialogStyle );
                builder.setTitle ( "Do you want to quit:" );
                builder.setPositiveButton ( "YES", new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {
                        finish ( );
                    }
                } );
                builder.setNegativeButton ( "NO", new DialogInterface.OnClickListener ( ) {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel ( );
                    }
                } );
                AlertDialog alert = builder.create ( );
                alert.show ( );
            }
        } );
    }

    public void ShowAlert() {
        Login.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( Login.this );
                builder.setTitle ( "Update Login:" );
                final EditText edt_newusername = new EditText ( Login.this );
                final EditText edt_newpassward = new EditText ( Login.this );
                edt_newusername.setHint ( "username" );
                edt_newpassward.setHint ( "password" );
                LinearLayout lp = new LinearLayout ( Login.this );
                lp.setOrientation ( LinearLayout.VERTICAL );
                lp.addView ( edt_newusername );
                lp.addView ( edt_newpassward );
                builder.setView ( lp );
                builder.setPositiveButton ( "OK", new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {
                        if ((edt_newusername.getText ( ).toString ( ).trim ( ).length ( ) > 0) && (edt_newpassward.getText ( ).toString ( ).trim ( ).length ( ) > 0)) {
                            if (cd.isConnectingToInternet ( )) {
                                new ShowUpdateLogin ( edt_newusername.getText ( ).toString ( ).trim ( ), edt_newpassward.getText ( ).toString ( ).trim ( ) ).execute ( );
                            } else {
                                Toast.makeText ( getBaseContext ( ), "internet not connected", Toast.LENGTH_LONG ).show ( );
                            }
                        } else {
                            Toast.makeText ( getApplicationContext ( ), " some text missing", Toast.LENGTH_LONG ).show ( );
                        }
                    }
                } );
                builder.setNegativeButton ( "EXIT", new DialogInterface.OnClickListener ( ) {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel ( );
                    }
                } );
                AlertDialog alert = builder.create ( );
                alert.show ( );
            }
        } );
    }

    private void loadView() {
        edt_login = (EditText) findViewById ( R.id.edt_username );
        edt_pwd = (EditText) findViewById ( R.id.edt_passward );
        txt_resetLogin = (TextView) findViewById ( R.id.reset );
        txt_resetLogin.setPaintFlags ( txt_resetLogin.getPaintFlags ( ) | Paint.UNDERLINE_TEXT_FLAG );
        btn_login = (Button) findViewById ( R.id.login );
        tv_reset = (TextView) findViewById ( R.id.reset );
    }

    private class ShowProgressLogin extends AsyncTask <String, String, String> {
        String loginvalue, passwardvalue;
        String response = null;
        String network_interrupt;

        ShowProgressLogin(String Login, String Passward) {
            loginvalue = Login;
            passwardvalue = Passward;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute ( );
            pd.setCancelable ( true );
            pd.setMessage ( "please wait..." );
            pd.setProgressStyle ( ProgressDialog.STYLE_SPINNER );
            pd.setProgress ( 0 );
            pd.setMax ( 100 );
            pd.show ( );
            /*pd = new ProgressDialog(Login.this);
            pd.show();
            pd.setMessage("please wait...");
         //   Log.e("from", "" + "pre");
            pd.setCancelable(true);*/
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.e ( "from", "" + "background" );
            ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
            nameValuePairs.add ( new BasicNameValuePair ( "tag", "get_usermaster" ) );
            nameValuePairs.add ( new BasicNameValuePair ( "UserName", loginvalue ) );
            nameValuePairs.add ( new BasicNameValuePair ( "Password", passwardvalue ) );

            Log.e ( "namepairvalue", "" + nameValuePairs );
            try {
                if (cd.isConnectingToInternet ( )) {
                    HttpClient httpclient = new DefaultHttpClient ( );

                    //  HttpPost httppost = new HttpPost(sessionManager.GET_URL());
                    HttpPost httppost = new HttpPost ( "http://wcrm.fedco.co.in/phedapi/nscapi/get_usermaster" );

                    httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
                    ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
                    response = httpclient.execute ( httppost, responseHandler );
                }
            } catch (Exception e) {
                Log.e ( "log_tag", "Error in http connection " + e.toString ( ) );
                network_interrupt = e.getMessage ( );
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute ( s );
            pd.cancel ( );
            pd.hide ( );
            try {
                Log.e ( "response", response.trim ( ) );
                if (response.length ()>0) {
                    //if (response.trim ( ).equals ( "success" )) {
                        Intent intent = new Intent ( Login.this, Options.class );
                        startActivity ( intent );
                        finish ( );
                   // }
                    if (response.trim ( ).equals ( "failed" )) {
                        Toast.makeText ( getBaseContext ( ), "username or password not matched", Toast.LENGTH_LONG ).show ( );
                    }
                } else {
                    Toast.makeText ( getBaseContext ( ), "username or password not matched", Toast.LENGTH_LONG ).show ( );

                  //  Toast.makeText ( getBaseContext ( ), "internet not connected", Toast.LENGTH_LONG ).show ( );
                }
            } catch (Exception e) {
                Log.e ( "log_tag", "Error in http connection " + e.toString ( ) );
            }
        }
    }

    private class ShowUpdateLogin extends AsyncTask <String, String, String> {
        String loginvalue, passwardvalue;
        ProgressDialog pd;
        String response = null;
        String network_interrupt;

        ShowUpdateLogin(String Login, String Passward) {
            loginvalue = Login;
            passwardvalue = Passward;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute ( );
            pd = new ProgressDialog ( Login.this );
            pd.show ( );
            pd.setMessage ( "please wait..." );
            Log.e ( "from", "" + "pre" );
            pd.setCancelable ( true );
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.e ( "from", "" + "background" );
            ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
            nameValuePairs.add ( new BasicNameValuePair ( "tag", "get_usermaster" ) );
            nameValuePairs.add ( new BasicNameValuePair ( "username", loginvalue ) );
            nameValuePairs.add ( new BasicNameValuePair ( "passward", passwardvalue ) );
            Log.e ( "namepairvalue", "" + nameValuePairs );
            try {
                if (cd.isConnectingToInternet ( )) {
                    HttpClient httpclient = new DefaultHttpClient ( );
                    HttpPost httppost = new HttpPost ( sessionManager.GET_URL ( ) );
                    httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
                    ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
                    response = httpclient.execute ( httppost, responseHandler );

                }
            } catch (Exception e) {
                Log.e ( "log_tag", "Error in http connection " + e.toString ( ) );
                network_interrupt = e.getMessage ( );
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute ( s );
            pd.cancel ( );
            pd.hide ( );
            if (network_interrupt == null) {
                try {
                    Log.e ( "response login", response.trim ( ) );
                    if (!response.equals ( null )) {

                        Log.e ( "response login", response.trim ( ) );
                        if (response.trim ( ).equals ( "success" )) {
                            Toast.makeText ( getBaseContext ( ), "successfully update", Toast.LENGTH_LONG ).show ( );
                        }
                        if (response.trim ( ).equals ( "failed" )) {
                            Toast.makeText ( getBaseContext ( ), "username or password not matched", Toast.LENGTH_LONG ).show ( );
                        }
                    } else {
                        Toast.makeText ( getBaseContext ( ), "internet not connected", Toast.LENGTH_LONG ).show ( );
                    }
                } catch (Exception e) {
                }
            }
//        Log.e("RESPONSE",response);
//        Toast.makeText(getBaseContext(),response,Toast.LENGTH_LONG).show();
        }
    }

    public class ResetPassword extends AsyncTask <String, String, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute ( );
            pd = new ProgressDialog ( Login.this );
            pd.setMessage ( "record sending" );
            pd.setCancelable ( false );
            pd.show ( );
        }

        @Override
        protected String doInBackground(String... params) {

            ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
            nameValuePairs.add ( new BasicNameValuePair ( "tag", "change_password" ) );
            nameValuePairs.add ( new BasicNameValuePair ( "user_name", str_username ) );
            nameValuePairs.add ( new BasicNameValuePair ( "old_password", str_old_password ) );
            nameValuePairs.add ( new BasicNameValuePair ( "new_password", str_new_password ) );

            Log.e ( "namevaluepair", "" + nameValuePairs );
            try {
                HttpClient httpclient = new DefaultHttpClient ( );
                HttpPost httppost = new HttpPost ( sessionManager.GET_URL ( ) );
                httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
                ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
                response = httpclient.execute ( httppost, responseHandler );
            } catch (Exception e) {
                network_interrupt = e.getMessage ( ).toString ( );
                Log.e ( "log_tag", "Error in http connection " + e.toString ( ) );
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute ( s );
            pd.hide ( );
            pd.dismiss ( );
            try {
                Log.e ( "reponse", "" + response );

                if (response.trim ( ).equals ( "1" )) {
                    ShowAlertUpdate ( "Password update successfully" );
                } else {
                    ShowAlertUpdate ( "Cannot update password" );
                }
            } catch (Exception e) {
                ShowAlertUpdate ( "Not ready to update" );
            }
        }
    }


    public void ShowAlertUpdate(String msg) {
        final String str = msg;

        Login.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( Login.this, R.style.MyAlertDialogStyle );
                builder.setTitle ( str );
                builder.setCancelable ( false );
                builder.setPositiveButton ( "OK", new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss ( );
                        login.dismiss ( );

                    }
                } );
                AlertDialog alert = builder.create ( );
                alert.show ( );
            }
        } );
    }

}