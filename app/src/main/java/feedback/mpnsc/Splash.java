package feedback.mpnsc;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS;


public class Splash extends AppCompatActivity {

    String[] PERMISSIONS = {WRITE_EXTERNAL_STORAGE, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, ACCESS_LOCATION_EXTRA_COMMANDS, ACCESS_NETWORK_STATE};
    SQLiteAdapter sqlAdapter;
    SessionManager sessionManager;
    SQLiteMasterTableAdapter sqLiteMasterTableAdapter;
    MultipleTicketManager sqMultipleLiteTicketlist;
    HttpClient httpclient;
    HttpPost httppost;


    public static final int NOTIFICATION_ID = 1;
    public String success = "download successfully";
    public String failed = "download failed";

    public static boolean returnDivision, returnSubDivision, returnSection, returnRoute, returnTariff,
            returnFeeder, returnMANUFACTURECODE, returnDEPARTMENT;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        super.setTheme ( android.R.style.Theme_Black );
        setRequestedOrientation ( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        setContentView ( R.layout.splash );

       // ActivityCompat.requestPermissions ( this, PERMISSIONS, 1 );

        sessionManager = new SessionManager ( Splash.this );

        if (sessionManager.getdownload_completed ( )) {
            Log.e ( "if", "" + sessionManager.getdownload_completed ( ) );
            startActivity ( new Intent ( Splash.this, Login.class ) );
            finish ( );
        } else {
            Log.e ( "else-", "" + sessionManager.getdownload_completed ( ) );
            new SearchTicketNumber ( ).execute ( );
        }

        sqlAdapter = new SQLiteAdapter ( Splash.this );
    }

    public class SearchTicketNumber extends AsyncTask <String, String, String> {
        ProgressDialog pd;
        String response = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute ( );
            pd = new ProgressDialog ( Splash.this );
            pd.setMessage ( "data is downloading..." );
            pd.setCancelable ( false );
            pd.show ( );
        }

        @Override
        protected String doInBackground(String... params) {
            sqlAdapter = new SQLiteAdapter ( Splash.this );
            try {

                sqlAdapter.openToRead ( );
                sqlAdapter.openToWrite ( );


                sqlAdapter.insert_meter_type ( "Whole Current Meter" );
                sqlAdapter.insert_meter_type ( "LT CT Operated" );


                sqlAdapter.insert_meter_phase ( "Single Phase" );


                sqlAdapter.insert_meter_bill_basis ( "KW" );


                sqlAdapter.insert_meter_digit ( "7" );
                sqlAdapter.insert_meter_digit ( "4" );
                sqlAdapter.insert_meter_digit ( "5" );
                sqlAdapter.insert_meter_digit ( "6" );
                sqlAdapter.insert_meter_digit ( "8" );
                sqlAdapter.insert_meter_digit ( "9" );

                sqlAdapter.insert_meter_metered ( "Metered" );
                sqlAdapter.insert_meter_metered ( "Unmetered" );


                sqlAdapter.insert_meter_ownership ( "DF" );
                sqlAdapter.insert_meter_ownership ( "Consumer" );
                sqlAdapter.insert_meter_ownership ( "NESCO" );

                httpclient = new DefaultHttpClient ( );

                //URL FOR THE SERVER..
                httppost = new HttpPost ( "http://wcrm.fedco.co.in/phedapi/nscapi/get_master" );

                //  httppost = new HttpPost("http://103.192.172.18:8083/mpnsc.php");

                saveinDIVISION ( );
                saveinSECTION ( );
                //saveinRoute();

                saveinTARIFF ( );

                //saveinSUBDIVISION();
                //saveinFEEDER();
                // saveinDEPARTMENT();
                //saveinMANUFACTURECODE();
            } catch (Exception e) {
                Log.e ( "log_tag", "Error in http connection " + e.toString ( ) );

            }

            System.out.println ("This is the response "+response );
            return response;
        }

        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute ( result );
            pd.dismiss ( );
            pd.hide ( );

            System.out.println ("Division "+returnDivision );
            System.out.println ("Division "+returnSection );
            System.out.println ("Division "+returnTariff );

           // if (returnDivision  && returnSection && returnTariff) {
            if (returnDivision  && returnSection&&returnTariff ) {

                notification ( R.drawable.notification_success, success );
                sessionManager.download_completed ( );
                Intent intent = new Intent ( Splash.this, Login.class );
                startActivity ( intent );
                finish ( );
            } else {
                System.out.println ("This is failed now" );
                notification ( R.drawable.notification_failed, failed );
            }
            Log.e ( "database_data", "" + response );
        }
    }

    public void notification(int notification_id, String notification_contenttext) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder ( this );
        builder.setSmallIcon ( R.drawable.nesconata );
        builder.setLargeIcon ( BitmapFactory.decodeResource ( getResources ( ), notification_id ) );
        builder.setContentTitle ( "NSC" );
        builder.setContentText ( notification_contenttext );
        NotificationManager notificationManager = (NotificationManager) getSystemService ( NOTIFICATION_SERVICE );
        notificationManager.notify ( NOTIFICATION_ID, builder.build ( ) );
    }

    public void saveinDIVISION() {
        String response = null;
        ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
        // nameValuePairs.add(new BasicNameValuePair("tag", "get_master"));
        nameValuePairs.add ( new BasicNameValuePair ( "table", "division" ) );
        try {
            httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
            ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
            response = httpclient.execute ( httppost, responseHandler );
            if (response.equals ( null )) {

                returnDivision = false;
            } else {

                System.out.println ( "This is division " + response );


                JSONObject user_info = new JSONObject ( response );
                JSONArray userinfo_array = user_info.getJSONArray ( "Table" );
                for (int i = 0; i < userinfo_array.length ( ); i++) {
                    JSONObject c = userinfo_array.getJSONObject ( i );
                    String division_code = c.getString ( "DIVISION_CODE" );
                    String div_name = c.getString ( "DIV_NAME" );

                    sqlAdapter.openToRead ( );
                    sqlAdapter.openToWrite ( );
                    sqlAdapter.masterdivision_insert ( "", division_code, div_name, "", "" );
                    sqlAdapter.close ( );
                    returnDivision = true;


                    //Response coming from server..........
                   /* "MD_CODE": "000001",
                            "CO_CODE": "000001",
                            "DISCOM_CODE": "000001",
                            "CIRCLE_CODE": "000001",
                            "DIVISION_CODE": "000001",
                            "DIV_NAME": "Aurangabad",
                            "DISPLAY_CODE": "CAE",
                            "DIV_SHORTCODE": "CAE"*/
                }
            }
        } catch (Exception e) {
        }
    }


    public void saveinTARIFF() {
        String response = null;
        ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
        // nameValuePairs.add(new BasicNameValuePair("tag", "get_master"));
        nameValuePairs.add ( new BasicNameValuePair ( "table", "tariff" ) );
        Log.e ( "namepairvalue", "" + nameValuePairs );
        try {
            httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
            ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
            response = httpclient.execute ( httppost, responseHandler );

            System.out.println ("This is the tariff "+response );
            if (response.equals ( null )) {
                returnTariff = false;
            } else {

                System.out.println ( "This is Tariff " + response );


                JSONObject user_info = new JSONObject ( response );
                JSONArray userinfo_array = user_info.getJSONArray ( "Table" );
                Log.e ( "array", "" + userinfo_array );
                Log.e ( "array_lenght", "" + userinfo_array.length ( ) );
                for (int i = 0; i < userinfo_array.length ( ); i++) {
                    JSONObject c = userinfo_array.getJSONObject ( i );
                    String tariff_code = c.getString ( "TARIFF_CODE" );
                    String tariff_name = c.getString ( "TARIFF_NAME" );

                    /*String tariff_load=c.getString("tariff_load");
                    String tariff_loadunit=c.getString("tariff_loadunit");
                  */

                    sqlAdapter.openToRead ( );
                    sqlAdapter.openToWrite ( );
                  //  sqlAdapter.get_tariff_name (  )
                    sqlAdapter.mastertariff_insert ( "", tariff_code, tariff_name, "", "" );
                    sqlAdapter.close ( );
                    returnTariff = true;


                    //Response from server.....
                   /* "TARIFF_CODE": "1000",
                            "TARIFF_NAME": "DS-IID"
                            */

                }
            }
        } catch (Exception e) {
        }
    }


    public void saveinFEEDER() {
        String response = null;
        ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
        nameValuePairs.add ( new BasicNameValuePair ( "tag", "get_master" ) );
        nameValuePairs.add ( new BasicNameValuePair ( "table_name", "address_proof" ) );
        Log.e ( "namepairvalue", "" + nameValuePairs );
        try {
            httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
            ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
            response = httpclient.execute ( httppost, responseHandler );
            if (response.equals ( null )) {
                returnFeeder = false;
            } else {

                JSONObject user_info = new JSONObject ( response );
                JSONArray userinfo_array = user_info.getJSONArray ( "resp" );
                Log.e ( "array", "" + userinfo_array );
                Log.e ( "array_lenght", "" + userinfo_array.length ( ) );
                for (int i = 0; i < userinfo_array.length ( ); i++) {
                    JSONObject c = userinfo_array.getJSONObject ( i );
                    String feeder_code = c.getString ( "id" );
                    String feeder_name = c.getString ( "address_proof" );
                    sqlAdapter.openToRead ( );
                    sqlAdapter.openToWrite ( );
                    sqlAdapter.masterfeeder_insert ( "", feeder_code, feeder_name, "", "" );
                    sqlAdapter.close ( );
                    returnFeeder = true;
                }
            }
        } catch (Exception e) {
        }
    }


    public void saveinMANUFACTURECODE() {
        String response = null;
        ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
        nameValuePairs.add ( new BasicNameValuePair ( "tag", "get_master" ) );
        nameValuePairs.add ( new BasicNameValuePair ( "table_name", "manufacturer" ) );
        try {
            httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
            ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
            response = httpclient.execute ( httppost, responseHandler );
            if (response.equals ( null )) {
                returnMANUFACTURECODE = false;
            } else {

                JSONObject user_info = new JSONObject ( response );
                JSONArray userinfo_array = user_info.getJSONArray ( "resp" );
                for (int i = 0; i < userinfo_array.length ( ); i++) {
                    JSONObject c = userinfo_array.getJSONObject ( i );
                    String MeterTypeShortCode = c.getString ( "MeterTypeShortCode" );

                    sqlAdapter.openToRead ( );
                    sqlAdapter.openToWrite ( );
                    sqlAdapter.insert_manufacturer_code ( MeterTypeShortCode );
                    sqlAdapter.close ( );
                    returnMANUFACTURECODE = true;
                }
            }
        } catch (Exception e) {
        }
    }

    public void saveinSUBDIVISION() {
        String response = null;
        ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
        //  nameValuePairs.add(new BasicNameValuePair("tag", "get_master"));
        nameValuePairs.add ( new BasicNameValuePair ( "table_name", "sub_division" ) );

        try {
            httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
            ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
            response = httpclient.execute ( httppost, responseHandler );

            if (response.equals ( null )) {

                returnSubDivision = false;
            } else {

                Log.e ( "SUBDIVISION", response );
                JSONObject user_info = new JSONObject ( response );
                JSONArray userinfo_array = user_info.getJSONArray ( "resp" );
                Log.e ( "array", "" + userinfo_array );
                Log.e ( "array_lenght", "" + userinfo_array.length ( ) );
                for (int i = 0; i < userinfo_array.length ( ); i++) {
                    JSONObject c = userinfo_array.getJSONObject ( i );
                    String division_code = c.getString ( "division_code" );
                    String sub_div_code = c.getString ( "sub_div_code" );
                    String sub_div_name = c.getString ( "sub_div_name" );
                    String display_code = c.getString ( "display_code" );

                    sqlAdapter.openToRead ( );
                    sqlAdapter.openToWrite ( );
                    sqlAdapter.mastersubdivision_insert ( division_code, sub_div_code, sub_div_name, display_code );

                    sqlAdapter.close ( );
                    returnSubDivision = true;
                }
            }


        } catch (Exception e) {
            Log.e ( "log_tag", "Error in http connection " + e.toString ( ) );

        }

    }





    public void saveinSECTION() {
        String response = null;
        ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
        //  nameValuePairs.add(new BasicNameValuePair("tag", "get_master"));
        nameValuePairs.add ( new BasicNameValuePair ( "table", "section" ) );
        Log.e ( "namepairvalue", "" + nameValuePairs );
        try {
            httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
            ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
            response = httpclient.execute ( httppost, responseHandler );


            if (response.equals ( null )) {
                //dnld failed
                returnSection = false;
            } else {

                System.out.println ( "This is section " + response );
                //save in db
                Log.e ( "SECTION", response );
                JSONObject user_info = new JSONObject ( response );
                JSONArray userinfo_array = user_info.getJSONArray ( "Table" );
                Log.e ( "array", "" + userinfo_array );
                Log.e ( "array_lenght", "" + userinfo_array.length ( ) );


                for (int i = 0; i < userinfo_array.length ( ); i++) {
                    JSONObject c = userinfo_array.getJSONObject ( i );
                    String div_code = c.getString ( "CESU_DIV_CODE" );
                    String sec_code = c.getString ( "SEC_CODE" );
                    String sec_name = c.getString ( "SEC_NAME" );
                    sqlAdapter.openToRead ( );
                    sqlAdapter.openToWrite ( );
                    sqlAdapter.mastersection_insert ( div_code, sec_code, sec_name, "" );

                    sqlAdapter.close ( );
                    returnSection = true;


                    //Response From Server........
                    /*"SUB_DIV_CODE": "000001",
                            "SEC_CODE": "000001",
                            "SEC_NAME": "Aurangabad(T)-I"*/

                }
            }


        } catch (Exception e) {
            Log.e ( "log_tag", "Error in http connection " + e.toString ( ) );

        }


    }

    public void saveinRoute() {
        String response = null;
        ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
        nameValuePairs.add ( new BasicNameValuePair ( "tag", "get_master" ) );
        nameValuePairs.add ( new BasicNameValuePair ( "table_name", "cycle" ) );
        try {
            httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
            ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
            response = httpclient.execute ( httppost, responseHandler );

            if (response.equals ( null )) {
                //dnld failed
                returnRoute = false;
            } else {
                JSONObject user_info = new JSONObject ( response );
                JSONArray userinfo_array = user_info.getJSONArray ( "resp" );
                for (int i = 0; i < userinfo_array.length ( ); i++) {
                    JSONObject c = userinfo_array.getJSONObject ( i );
                    String CYCLEID = c.getString ( "CYCLEID" );
                    String DIVISIONIDATTACHED = c.getString ( "DIVISIONIDATTACHED" );
                    String ROUTENO = c.getString ( "ROUTENO" );
                    String SECTIONID = c.getString ( "SECTIONID" );
                    String SUBDIVATTACHED = c.getString ( "SUBDIVATTACHED" );
                    sqlAdapter.openToRead ( );
                    sqlAdapter.openToWrite ( );
                    sqlAdapter.mastersectioncycleroutemapping_insert ( CYCLEID, DIVISIONIDATTACHED, ROUTENO, SECTIONID, SUBDIVATTACHED, "", "", "", "", "", "", "" );

                    sqlAdapter.close ( );
                    returnRoute = true;
                }
            }

        } catch (Exception e) {
            Log.e ( "log_tag", "Error in http connection " + e.toString ( ) );
        }
    }


    @Override
    protected void onDestroy() {
        Debug.stopMethodTracing ( );
        super.onDestroy ( );
    }



}