package feedback.mpnsc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by swatiG on 23-06-2015.
 */
public class SearchTicket extends Activity {
    EditText ticket_no;
    Button search;
    String str_ticket_no;
    String response;
    String consumer_name, consumer_add, str_lat, str_long, str_feasibility_status, str_division, str_subdivision, str_section, str_route, str_account_no;
    String network_interrupt = null;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;

    ImageView im_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.search_ticket_meter );
        ticket_no = (EditText) findViewById ( R.id.ticket_no );
        search = (Button) findViewById ( R.id.search );
        connectionDetector = new ConnectionDetector ( SearchTicket.this );
        sessionManager = new SessionManager ( SearchTicket.this );
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder ( ).permitAll ( ).build ( );
            StrictMode.setThreadPolicy ( policy );
        }
        search.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                str_ticket_no = ticket_no.getText ( ).toString ( ).trim ( );
                DataHolderClass.getInstance ( ).set_new_meter_ticket_no ( str_ticket_no );
                if (TextUtils.isEmpty ( str_ticket_no )) {
                    Toast.makeText ( getApplicationContext ( ), "enter ticket no", Toast.LENGTH_SHORT ).show ( );
                } else {
                    if (connectionDetector.isConnectingToInternet ( )) {


                        new SearchTicketNumber ( ).execute ( );
                    } else {
                        Toast.makeText ( getApplicationContext ( ), "internet not connected", Toast.LENGTH_SHORT ).show ( );
                    }
                }
            }
        } );

        im_back=findViewById ( R.id.im_back );
        im_back.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                ShowAlertonBack ();
            }
        } );

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack ( );
    }

    public void ShowAlertonBack() {
        SearchTicket.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( SearchTicket.this, R.style.MyAlertDialogStyle );
                builder.setCancelable ( false );
                builder.setTitle ( "Are you sure to go back:" );
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

    public class SearchTicketNumber extends AsyncTask <String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute ( );
            pd = new ProgressDialog ( SearchTicket.this, R.style.MyAlertDialogStyle );
            pd.setMessage ( "searching..." );
            pd.setCancelable ( false );
            pd.show ( );
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
            // nameValuePairs.add(new BasicNameValuePair("tag", "get_consumer_detail"));
            nameValuePairs.add ( new BasicNameValuePair ( "ticketno", str_ticket_no ) );
            Log.e ( "namepairvalue", "" + nameValuePairs );
            try {
                HttpClient httpclient = new DefaultHttpClient ( );

                // HttpPost httppost = new HttpPost(sessionManager.GET_URL());
                HttpPost httppost = new HttpPost ( "http://wcrm.fedco.co.in/phedapi/nscapi/checkCon" );


                httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
                ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
                response = httpclient.execute ( httppost, responseHandler );
            } catch (Exception e) {
                Log.e ( "log_tag", "Error in http connection " + e.toString ( ) );
                network_interrupt = e.getMessage ( );
            }
            return response;
        }

        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute ( result );
            pd.dismiss ( );
            pd.hide ( );
            try {
                JSONObject user_info = new JSONObject ( response );

                System.out.println ( "This is the response " + response );
                if (user_info.getJSONArray ( "Table" ).length ( ) > 0) {

                    new SearchTicketPayment ( ).execute ( );




                    /*response = response.trim ( );
                    Log.e ( "response", "" + response );
                    if (response.equalsIgnoreCase ( "0" )) {

                        ShowAlert ( );
                        Toast.makeText ( getApplicationContext ( ), "invalid ticket no", Toast.LENGTH_SHORT ).show ( );
                    } else {
                        JSONArray userinfo_array = user_info.getJSONArray ( "Table" );
                        Log.e ( "array", "" + userinfo_array );
                        Log.e ( "array_lenght", "" + userinfo_array.length ( ) );
                        for (int i = 0; i < userinfo_array.length ( ); i++) {
                            JSONObject c = userinfo_array.getJSONObject ( i );
                            consumer_name = c.getString ( "NAME" );
                            consumer_add = c.getString ( "ADDRESS" );
                            str_lat = c.getString ( "LAT" );
                            str_long = c.getString ( "LONNG" );
                            str_feasibility_status = c.getString ( "FEASIBILITY_STATUS" ).trim ( );
                            str_division = c.getString ( "DIV_CODE" );
                            str_subdivision = c.getString ( "SUB_DIV_CODE" );
                            str_section = c.getString ( "SEC_CODE" );
                            str_route = c.getString ( "ROUTENO" );
                            str_account_no = c.getString ( "ACCOUNTNO" );
                            DataHolderClass.getInstance ( ).set_lat ( str_lat );
                            DataHolderClass.getInstance ( ).set_long ( str_long );
                            Log.e ( "consumer_name", c.getString ( "NAME" ) );
                            Log.e ( "consumer_add", consumer_add );
                            //  Log.e("ticket no", str_ticket_no);
                            DataHolderClass.getInstance ( ).set_new_meter_cons_name ( consumer_name );
                            DataHolderClass.getInstance ( ).set_new_meter_cons_add ( consumer_add );
                            DataHolderClass.getInstance ( ).set_new_meter_ticket_no ( str_ticket_no );

                            DataHolderClass.getInstance ( ).set_new_meter_cons_division ( str_division );
                            DataHolderClass.getInstance ( ).set_new_meter_cons_subdivision ( str_subdivision );
                            DataHolderClass.getInstance ( ).set_new_meter_cons_section ( str_section );
                            DataHolderClass.getInstance ( ).set_new_meter_cons_route ( str_route );
                            DataHolderClass.getInstance ( ).setStr_consumer_number ( str_account_no );
                       *//* Intent intent = new Intent(SearchTicket.this, MeterDetail.class);
                        startActivity(intent);
                        finish();*//*
                            if (str_feasibility_status.equals ( "null" )) {
                                ShowAlertGetFeasibility ( );

                            } else if (str_feasibility_status.equals ( "Y" )) {

                                DataHolderClass.getInstance ( ).set_new_meter_cons_division ( str_division );
                                DataHolderClass.getInstance ( ).set_new_meter_cons_subdivision ( str_subdivision );
                                DataHolderClass.getInstance ( ).set_new_meter_cons_section ( str_section );
                                DataHolderClass.getInstance ( ).set_new_meter_cons_route ( str_route );
                                DataHolderClass.getInstance ( ).setStr_consumer_number ( str_account_no );
                                Intent intent = new Intent ( SearchTicket.this, MeterDetail.class );
                                startActivity ( intent );
                                finish ( );
                            }
                        }
                    }
*/
                } else {
                    ShowAlert ();
                }
            } catch (Exception e) {
                Log.e ( " final exception", "" + e.getMessage ( ) );
                ShowAlert ( );
            }
        }
    }


    public class SearchTicketPayment extends AsyncTask <String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute ( );

        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
            // nameValuePairs.add(new BasicNameValuePair("tag", "get_consumer_detail"));
            nameValuePairs.add ( new BasicNameValuePair ( "ticketno", str_ticket_no ) );
            Log.e ( "namepairvalue", "" + nameValuePairs );
            try {
                HttpClient httpclient = new DefaultHttpClient ( );

                // HttpPost httppost = new HttpPost(sessionManager.GET_URL());
                HttpPost httppost = new HttpPost ( "http://wcrm.fedco.co.in/phedapi/nscapi/get_consumer_detail" );


                httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
                ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
                response = httpclient.execute ( httppost, responseHandler );
            } catch (Exception e) {
                Log.e ( "log_tag", "Error in http connection " + e.toString ( ) );
                network_interrupt = e.getMessage ( );
            }
            return response;
        }

        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute ( result );
//            pd.dismiss ( );
  //          pd.hide ( );
            try {
                JSONObject user_info = new JSONObject ( response );

                System.out.println ( "This is the response " + response );
                if (user_info.getJSONArray ( "Table" ).length ( ) > 0) {
                    response = response.trim ( );
                    Log.e ( "response", "" + response );
                    if (response.equalsIgnoreCase ( "0" )) {

                        ShowAlert ( );
                        Toast.makeText ( getApplicationContext ( ), "invalid ticket no", Toast.LENGTH_SHORT ).show ( );
                    } else {
                        JSONArray userinfo_array = user_info.getJSONArray ( "Table" );
                        Log.e ( "array", "" + userinfo_array );
                        Log.e ( "array_lenght", "" + userinfo_array.length ( ) );
                        for (int i = 0; i < userinfo_array.length ( ); i++) {
                            JSONObject c = userinfo_array.getJSONObject ( i );
                            consumer_name = c.getString ( "NAME" );
                            consumer_add = c.getString ( "ADDRESS" );
                            str_lat = c.getString ( "LAT" );
                            str_long = c.getString ( "LONNG" );
                            str_feasibility_status = c.getString ( "FEASIBILITY_STATUS" ).trim ( );
                            str_division = c.getString ( "DIV_CODE" );
                            str_subdivision = c.getString ( "SUB_DIV_CODE" );
                            str_section = c.getString ( "SEC_CODE" );
                            str_route = c.getString ( "ROUTENO" );
                            str_account_no = c.getString ( "ACCOUNTNO" );
                            DataHolderClass.getInstance ( ).set_lat ( str_lat );
                            DataHolderClass.getInstance ( ).set_long ( str_long );
                            Log.e ( "consumer_name", c.getString ( "NAME" ) );
                            Log.e ( "consumer_add", consumer_add );
                            //  Log.e("ticket no", str_ticket_no);
                            DataHolderClass.getInstance ( ).set_new_meter_cons_name ( consumer_name );
                            DataHolderClass.getInstance ( ).set_new_meter_cons_add ( consumer_add );
                            DataHolderClass.getInstance ( ).set_new_meter_ticket_no ( str_ticket_no );

                            DataHolderClass.getInstance ( ).set_new_meter_cons_division ( str_division );
                            DataHolderClass.getInstance ( ).set_new_meter_cons_subdivision ( str_subdivision );
                            DataHolderClass.getInstance ( ).set_new_meter_cons_section ( str_section );
                            DataHolderClass.getInstance ( ).set_new_meter_cons_route ( str_route );
                            DataHolderClass.getInstance ( ).setStr_consumer_number ( str_account_no );
                       /* Intent intent = new Intent(SearchTicket.this, MeterDetail.class);
                        startActivity(intent);
                        finish();*/
                            if (str_feasibility_status.equals ( "null" )) {
                                ShowAlertGetFeasibility ( );

                            } else if (str_feasibility_status.equals ( "Y" )) {

                                DataHolderClass.getInstance ( ).set_new_meter_cons_division ( str_division );
                                DataHolderClass.getInstance ( ).set_new_meter_cons_subdivision ( str_subdivision );
                                DataHolderClass.getInstance ( ).set_new_meter_cons_section ( str_section );
                                DataHolderClass.getInstance ( ).set_new_meter_cons_route ( str_route );
                                DataHolderClass.getInstance ( ).setStr_consumer_number ( str_account_no );
                                Intent intent = new Intent ( SearchTicket.this, MeterDetail.class );
                                startActivity ( intent );
                                finish ( );
                            }
                        }
                    }

                } else {
                    ShowAlertGetFeasibility ( );
                }
            } catch (Exception e) {
                Log.e ( " final exception", "" + e.getMessage ( ) );
                ShowAlert ( );
            }
        }
    }

    public void ShowAlert() {
        SearchTicket.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( SearchTicket.this, R.style.MyAlertDialogStyle );
                builder.setTitle ( "Ticket number not found. Please check" );
                builder.setCancelable ( false );
                builder.setPositiveButton ( "OK", new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss ( );
                    }
                } );
                AlertDialog alert = builder.create ( );
                alert.show ( );
            }
        } );
    }

    public void ShowAlertGetFeasibility() {
        SearchTicket.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( SearchTicket.this, R.style.MyAlertDialogStyle );
                builder.setTitle ( "Payment is due." );
                builder.setCancelable ( false );
                builder.setPositiveButton ( "OK", new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss ( );
                    }
                } );
                AlertDialog alert = builder.create ( );
                alert.show ( );
            }
        } );
    }
}