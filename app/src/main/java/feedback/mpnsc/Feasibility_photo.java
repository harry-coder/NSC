package feedback.mpnsc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
public class Feasibility_photo extends Activity {

    TextView tv_lat_home, tv_long_home, tv_lat_pole, tv_long_pole, tv_feasibility;
    Button btn_home_nw, btn_home_gps, btn_pole_nw, btn_pole_gps, btn_feasibility, submit,
            land_btn, consumer_btn, address_btn, aadhar_btn, no_dues_btn;

    AppLocationService appLocationService;
    double home_lat, home_long, pole_lat, pole_long;

    private static String edhar_number = "";

    SessionManager sessionManager;
    String response, network_interrupt, value_feasibility, str_ticket_no, str_ed_aadhar_no, str_ed_remark;

    SQLiteMasterTableAdapter sqLiteMasterTableAdapter;

    ConnectionDetector connectionDetector;

    EditText edt_manual_fes;
    EditText tranformer_capacity, tranformer_code, pole_no, mru_no, ed_aadhar, ed_remark, ed_address_id;
    String str_tranformer_capacity, str_tranformer_code, str_pole_no, str_mru_no;
    String str_manual_fes;
    LinearLayout linear_distance;
    String c_name, aa_name, ad_name, l_name, str_spinner_address, str_spinner_land;
    Spinner spinner_address, spinner_land;

    String aadhar_photo, consumer_photo, address_photo, land_photo, nodues_photo, str_address_id;


    SQLiteAdapter sqLiteAdapter;
    ArrayList routeArraycode;
    ArrayAdapter <String> routeAdapter;
    Spinner route_spinner;
    String str_route;
    RadioGroup radio_land_status;
    String value_land;
    ImageView im_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.feasibilityphoto );
        callView ( );
        appLocationService = new AppLocationService (
                Feasibility_photo.this );
        sessionManager = new SessionManager ( Feasibility_photo.this );
        sqLiteMasterTableAdapter = new SQLiteMasterTableAdapter ( Feasibility_photo.this );
        connectionDetector = new ConnectionDetector ( Feasibility_photo.this );

        sqLiteAdapter = new SQLiteAdapter ( Feasibility_photo.this );
        routeArraycode = new ArrayList <String> ( );


       /* aadhar_photo =DataHolderClass.getInstance().getAadhar_image();
        ed_aadhar.setText(DataHolderClass.getInstance().getStr_aadhar_no());
        str_ed_aadhar_no = ed_aadhar.getText().toString().trim();
        DataHolderClass.getInstance().setStr_aadhar_no(str_ed_aadhar_no);*/

        consumer_photo = DataHolderClass.getInstance ( ).getConsumer_image_name ( );
        aadhar_photo = DataHolderClass.getInstance ( ).getAadhar_image_name ( );
        address_photo = DataHolderClass.getInstance ( ).getAddress_image_name ( );
        land_photo = DataHolderClass.getInstance ( ).getLand_image_name ( );
        nodues_photo = DataHolderClass.getInstance ( ).getNodues_image_name ( );


        im_back = findViewById ( R.id.im_back );
        im_back.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                ShowAlertonBack ( );
            }
        } );

      /*  if (!edhar_number.equals ( "" ) && !edhar_number.equals ( null ))
            ed_aadhar.setText ( edhar_number );
*/
        if (TextUtils.isEmpty ( consumer_photo )) {
            consumer_btn.setVisibility ( View.VISIBLE );
            //consumer_btn.setClickable(true);
        } else {
            //consumer_btn.setVisibility(View.INVISIBLE);
            consumer_btn.setBackgroundColor ( getResources ( ).getColor ( R.color.edittexttheme ) );

        }

       /* if (TextUtils.isEmpty ( aadhar_photo )) {
            aadhar_btn.setVisibility ( View.VISIBLE );
            ed_aadhar.setVisibility ( View.VISIBLE );


        } else {
           *//* aadhar_btn.setVisibility(View.INVISIBLE);
            ed_aadhar.setVisibility(View.INVISIBLE);*//*
            aadhar_btn.setBackgroundColor ( getResources ( ).getColor ( R.color.edittexttheme ) );
            ed_aadhar.setBackgroundColor ( getResources ( ).getColor ( R.color.edittexttheme ) );
        }*/


        if (TextUtils.isEmpty ( address_photo )) {
            address_btn.setVisibility ( View.VISIBLE );
            spinner_address.setVisibility ( View.VISIBLE );
            ed_address_id.setVisibility ( View.VISIBLE );

        } else {
            /*address_btn.setVisibility(View.INVISIBLE);
            spinner_address.setVisibility(View.INVISIBLE);
            ed_address_id.setVisibility(View.INVISIBLE);*/

            address_btn.setBackgroundColor ( getResources ( ).getColor ( R.color.edittexttheme ) );
            ed_address_id.setBackgroundColor ( getResources ( ).getColor ( R.color.edittexttheme ) );
            spinner_address.setBackgroundColor ( getResources ( ).getColor ( R.color.edittexttheme ) );
        }

        if (TextUtils.isEmpty ( land_photo )) {
            land_btn.setVisibility ( View.VISIBLE );
            spinner_land.setVisibility ( View.VISIBLE );

        } else {
           /* land_btn.setVisibility(View.INVISIBLE);
            spinner_land.setVisibility(View.INVISIBLE);*/
            land_btn.setBackgroundColor ( getResources ( ).getColor ( R.color.edittexttheme ) );
            spinner_land.setBackgroundColor ( getResources ( ).getColor ( R.color.edittexttheme ) );
        }

        if (TextUtils.isEmpty ( nodues_photo )) {
            no_dues_btn.setVisibility ( View.VISIBLE );

        } else {
            //no_dues_btn.setVisibility(View.INVISIBLE);
            no_dues_btn.setBackgroundColor ( Color.parseColor ( "#412b55" ) );

        }

        submit = findViewById ( R.id.submit );
        submit.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                str_ed_remark = ed_remark.getText ( ).toString ( ).trim ( );
                DataHolderClass.getInstance ( ).setRemark_feasibility ( str_ed_remark );
                DataHolderClass.getInstance ( ).getConsumer_image_name ( );
                DataHolderClass.getInstance ( ).getLand_image_name ( );
                DataHolderClass.getInstance ( ).getAadhar_image_name ( );
                DataHolderClass.getInstance ( ).getAddress_image_name ( );

                if (TextUtils.isEmpty ( DataHolderClass.getInstance ( ).getConsumer_image_name ( ) )) {
                    Toast.makeText ( getApplicationContext ( ), "Click Consumer Image first* ", Toast.LENGTH_SHORT ).show ( );
                } /*else if (TextUtils.isEmpty ( DataHolderClass.getInstance ( ).getStr_aadhar_no ( ) )) {
                    Toast.makeText ( getApplicationContext ( ), "Enter Aadhar Number* ", Toast.LENGTH_SHORT ).show ( );

                }*/ /*else if (TextUtils.isEmpty ( DataHolderClass.getInstance ( ).getAadhar_image_name ( ) )) {
                    Toast.makeText ( getApplicationContext ( ), "Click Aadhar Image* ", Toast.LENGTH_SHORT ).show ( );

                }*/ else if (TextUtils.isEmpty ( DataHolderClass.getInstance ( ).getStr_spinner_address_proof ( ) )) {
                    Toast.makeText ( getApplicationContext ( ), "Select address proof* ", Toast.LENGTH_SHORT ).show ( );

                } else if (TextUtils.isEmpty ( DataHolderClass.getInstance ( ).getAdress_proof_name_input ( ) )) {
                    Toast.makeText ( getApplicationContext ( ), "Enter Address proof number* ", Toast.LENGTH_SHORT ).show ( );

                } else if (TextUtils.isEmpty ( DataHolderClass.getInstance ( ).getAddress_image_name ( ) )) {
                    Toast.makeText ( getApplicationContext ( ), "Click address Image* ", Toast.LENGTH_SHORT ).show ( );

                } else if (TextUtils.isEmpty ( DataHolderClass.getInstance ( ).getLand_record_type ( ) )) {
                    Toast.makeText ( getApplicationContext ( ), "Select Land Record* ", Toast.LENGTH_SHORT ).show ( );
                } else if (TextUtils.isEmpty ( DataHolderClass.getInstance ( ).getLand_image_name ( ) )) {
                    Toast.makeText ( getApplicationContext ( ), "Click Land Image* ", Toast.LENGTH_SHORT ).show ( );
                } else if (TextUtils.isEmpty ( DataHolderClass.getInstance ( ).getNodues_image_name ( ) )) {
                    Toast.makeText ( getApplicationContext ( ), "Click Declaration Certificate Image* ", Toast.LENGTH_SHORT ).show ( );
                } else if (TextUtils.isEmpty ( str_ed_remark )) {

                    Toast.makeText ( getApplicationContext ( ), "Enter Remark* ", Toast.LENGTH_SHORT ).show ( );
                } else {

                    if (connectionDetector.isConnectingToInternet ( )) {
                        new SendFeasibility ( ).execute ( );
                    } else {

                        Toast.makeText ( getApplicationContext ( ), "Internet not connecting ", Toast.LENGTH_SHORT ).show ( );

                    }
                }


            }
        } );


        consumer_btn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                //c_name= DataHolderClass.getInstance().getConsumer_image_name();

                startActivity ( new Intent ( Feasibility_photo.this, ConsumerImage.class ) );

                finish ( );


            }
        } );

        land_btn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {


                str_spinner_land = spinner_land.getSelectedItem ( ).toString ( );
                DataHolderClass.getInstance ( ).setLand_record_type ( str_spinner_land );
                if (spinner_land.getSelectedItem ( ).toString ( ).trim ( ).equals ( "Select Land/Rent Type" )) {
                    Toast.makeText ( getApplicationContext ( ), "Select land/Rent Type First", Toast.LENGTH_SHORT ).show ( );

                } else {
                    startActivity ( new Intent ( Feasibility_photo.this, LandImage.class ) );
                    finish ( );
                }

            }
        } );

        address_btn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                str_address_id = ed_address_id.getText ( ).toString ( ).trim ( );
                DataHolderClass.getInstance ( ).setAdress_proof_name_input ( str_address_id );

                str_spinner_address = spinner_address.getSelectedItem ( ).toString ( );
                DataHolderClass.getInstance ( ).setStr_spinner_address_proof ( str_spinner_address );
                if (spinner_address.getSelectedItem ( ).toString ( ).trim ( ).equals ( "Select Address Proof" ) || TextUtils.isEmpty ( str_address_id )) {
                    Toast.makeText ( getApplicationContext ( ), "Enter all Mandetory field", Toast.LENGTH_SHORT ).show ( );

                } else {
                    startActivity ( new Intent ( Feasibility_photo.this, AddressImage.class ) );
                    finish ( );
                }


            }
        } );

        /*aadhar_btn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {


                edhar_number = str_ed_aadhar_no = ed_aadhar.getText ( ).toString ( ).trim ( );
                DataHolderClass.getInstance ( ).setStr_aadhar_no ( str_ed_aadhar_no );
                if (TextUtils.isEmpty ( str_ed_aadhar_no ) || str_ed_aadhar_no.length ( ) != 12) {
                    Toast.makeText ( getApplicationContext ( ), "Enter 12 digit Aadhar Number", Toast.LENGTH_SHORT ).show ( );

                } else {
                    startActivity ( new Intent ( Feasibility_photo.this, AadharImage.class ) );
                    finish ( );

                }
            }
        } );*/

        no_dues_btn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                startActivity ( new Intent ( Feasibility_photo.this, NoDuesImage.class ) );
                finish ( );


            }
        } );


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack ( );
    }

    public void ShowAlertonBack() {
        Feasibility_photo.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( Feasibility_photo.this, R.style.MyAlertDialogStyle );
                builder.setCancelable ( false );
                builder.setTitle ( "Are you sure to go back:" );
                builder.setPositiveButton ( "YES", new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent ( Feasibility_photo.this, Feasibility.class);
                        intent.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        startActivity ( intent );


                        //onNavigateUp ();
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


    public void callView() {
        consumer_btn = findViewById ( R.id.consumer_btn );
        land_btn = findViewById ( R.id.land_btn );
        address_btn = findViewById ( R.id.address_btn );
       // aadhar_btn = (Button) findViewById ( R.id.aadhar_btn );
      //  ed_aadhar = (EditText) findViewById ( R.id.ed_aadharno );
        ed_remark = findViewById ( R.id.ed_remark );
        ed_address_id = findViewById ( R.id.ed_address );
        spinner_address = findViewById ( R.id.tv_address );
        spinner_land = findViewById ( R.id.tv_land_rent );

        no_dues_btn = findViewById ( R.id.dues_btn );

        consumer_btn = findViewById ( R.id.consumer_btn );
        land_btn = findViewById ( R.id.land_btn );
        address_btn = findViewById ( R.id.address_btn );
       // aadhar_btn = (Button) findViewById ( R.id.aadhar_btn );

        submit = findViewById ( R.id.submit );

        edt_manual_fes = findViewById ( R.id.edt_manual_fes );
    }


    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder (
                Feasibility_photo.this, R.style.MyAlertDialogStyle );

        alertDialog.setTitle ( provider + " SETTINGS" );

        alertDialog
                .setMessage ( provider + " is not enabled! Want to go to settings menu?" );

        alertDialog.setPositiveButton ( "Settings",
                new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent (
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                        Feasibility_photo.this.startActivity ( intent );
                    }
                } );

        alertDialog.setNegativeButton ( "Cancel",
                new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel ( );
                    }
                } );

        alertDialog.show ( );
    }

    public class SendFeasibility extends AsyncTask <String, String, String> {
        String network_interrupt = null;
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute ( );
            pd = new ProgressDialog ( Feasibility_photo.this, R.style.MyAlertDialogStyle );
            pd.setMessage ( "record sending" );
            pd.setProgressStyle ( ProgressDialog.STYLE_SPINNER );
            pd.setCancelable ( false );
            pd.show ( );
        }

        @Override
        protected String doInBackground(String... params) {

            ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
            nameValuePairs.add ( new BasicNameValuePair ( "TicketNumber", DataHolderClass.getInstance ( ).getTicket_no ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "lat_home", DataHolderClass.getInstance ( ).getHome_lat ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "long_home", DataHolderClass.getInstance ( ).getHome_long ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "lat_pole", DataHolderClass.getInstance ( ).getPole_lat ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "long_pole", DataHolderClass.getInstance ( ).getPole_long ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "transformer_capacity", DataHolderClass.getInstance ( ).getStr_transformer_capacity ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "transformer_code", DataHolderClass.getInstance ( ).getStr_transformer_code ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "pole_number", DataHolderClass.getInstance ( ).getPole_number ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "NearConssStatus", DataHolderClass.getInstance ( ).getRadio_adjacent_cons ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "NearConsNO", DataHolderClass.getInstance ( ).getAdjacent_cons_no ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "mru_number", DataHolderClass.getInstance ( ).getAdjacent_mru_no ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "WiringStatus", DataHolderClass.getInstance ( ).getRadio_wiring_status ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "feasibility_remark", str_ed_remark ) );
            nameValuePairs.add ( new BasicNameValuePair ( "feasibility", DataHolderClass.getInstance ( ).getValue_feasibility ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "DISTANCE", DataHolderClass.getInstance ( ).getStr_manual_fes ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "CustGroup", DataHolderClass.getInstance ( ).get_connect_load ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "LoadRequired", DataHolderClass.getInstance ( ).getFeasibility_tariff_load ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "ApplicationStatus", "" ) );
            nameValuePairs.add ( new BasicNameValuePair ( "NearConsMRUNO", "" ) );



            /*String qry = @"UPDATE tbl_nsc_ticketmaster SET transformer_code='" +
                    httpRequest.Params["transformer_code"].ToString() +
                    "',transformer_capacity='" + httpRequest.Params["transformer_capacity"].ToString() +
                    "',pole_number='" + httpRequest.Params["pole_number"].ToString() +
                    "',mru_number='" + httpRequest.Params["mru_number"].ToString()
                    + "',NearConsNO='" + httpRequest.Params["NearConsNO"].ToString() +
                    "',NearConsMRUNO='" + httpRequest.Params["NearConsMRUNO"].ToString() +
                    "',NearConssStatus='" + httpRequest.Params["NearConssStatus"].ToString() +

                    "',WiringStatus='" + httpRequest.Params["WiringStatus"].ToString() +
                    "',feasibility_remark='" + httpRequest.Params["feasibility_remark"].ToString() +
                    "',lat_home='" + httpRequest.Params["lat_home"].ToString() +
                    "',long_home='" + httpRequest.Params["long_home"].ToString() +
                    "',lat_pole='" + httpRequest.Params["lat_pole"].ToString() +
                    "',long_pole='" + httpRequest.Params["long_pole"].ToString() +
                    "',CustGroup='" + httpRequest.Params["CustGroup"].ToString() +
                    "',LoadRequired='" + httpRequest.Params["LoadRequired"].ToString() +
                    "',ApplicationStatus='" + httpRequest.Params["ApplicationStatus"].ToString() +
                    "',DISTANCE='" + httpRequest.Params["DISTANCE"].ToString() +
                    "',TechFeasibilityDate='" + DateTime.Now +
                    "',feasibility='" + httpRequest.Params["feasibility"].ToString() +
                    "',feasibility_status='1' WHERE TicketNumber='"
                    + httpRequest.Params["TicketNumber"].ToString() + "'";*/

            Log.e ( "namevaluepair", "" + nameValuePairs );
            try {
                HttpClient httpclient = new DefaultHttpClient ( );
//                HttpPost httppost = new HttpPost(sessionManager.GET_URL());

                HttpPost httppost = new HttpPost ( "http://wcrm.fedco.co.in/phedapi/nscapi/set_feasibility" );

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
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute ( result );
            pd.dismiss ( );
            pd.hide ( );
            try {
                if (network_interrupt == null) {

                    response = response.trim ( );
                    Log.e ( "response", response );
                    if (response.equalsIgnoreCase ( "1" )) {
                        edhar_number = "";
                        Toast.makeText ( getApplicationContext ( ), "record send successfully", Toast.LENGTH_SHORT ).show ( );
                        startActivity ( new Intent ( Feasibility_photo.this, Options.class ) );
                        finish ( );
                    } else {
                        ShowAlert ( );

                    }


                } else {
                    /*sqLiteMasterTableAdapter.openToRead();
                    sqLiteMasterTableAdapter.openToWrite();
                    sqLiteMasterTableAdapter.insert_feasibility("set_feasibility", str_ticket_no,
                            String.valueOf(home_lat),
                            String.valueOf(home_long),
                            String.valueOf(pole_lat),
                            String.valueOf(pole_long),
                            str_route,
                            value_feasibility,str_manual_fes
                    );
                    sqLiteMasterTableAdapter.close();*/
                    Toast.makeText ( getApplicationContext ( ), "Record Not send due to internet interruption", Toast.LENGTH_SHORT ).show ( );
                    finish ( );


                }
            } catch (Exception e) {
                ShowAlert ( );
            }


        }


    }
   /* public class SendFeasibility extends AsyncTask<String, String, String>
    {

        ProgressDialog pd;
        // public SendToServer() {
        public SendFeasibility() {
            // TODO Auto-generated constructor stub

//            Log.e("from", "sendtoserver");
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(Feasibility_photo.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {


                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(sessionManager.GET_URL());
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    // httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    nameValuePairs.add(new BasicNameValuePair("tag","set_feasibility"));

                    nameValuePairs.add(new BasicNameValuePair("ticket_no",DataHolderClass.getInstance().getTicket_no()));
                    nameValuePairs.add(new BasicNameValuePair("home_lat",DataHolderClass.getInstance().getHome_lat()));
                    nameValuePairs.add(new BasicNameValuePair("home_long",DataHolderClass.getInstance().getHome_long()));
                    nameValuePairs.add(new BasicNameValuePair("pole_lat",DataHolderClass.getInstance().getPole_lat()));
                    nameValuePairs.add(new BasicNameValuePair("pole_long",DataHolderClass.getInstance().getPole_long()));
                    nameValuePairs.add(new BasicNameValuePair("tranformer_capacity",DataHolderClass.getInstance().getStr_transformer_capacity()));
                    nameValuePairs.add(new BasicNameValuePair("tranformer_code",DataHolderClass.getInstance().getStr_transformer_code()));
                    nameValuePairs.add(new BasicNameValuePair("pole_no",DataHolderClass.getInstance().getPole_number()));
                    nameValuePairs.add(new BasicNameValuePair("radio_adjacent_status",DataHolderClass.getInstance().getRadio_adjacent_cons()));
                    nameValuePairs.add(new BasicNameValuePair("ncons_no",DataHolderClass.getInstance().getAdjacent_cons_no()));
                    nameValuePairs.add(new BasicNameValuePair("nmru_no",DataHolderClass.getInstance().getAdjacent_mru_no()));
                    nameValuePairs.add(new BasicNameValuePair("radio_wiring_status",DataHolderClass.getInstance().getRadio_wiring_status()));
                    nameValuePairs.add(new BasicNameValuePair("remark",str_ed_remark));
                    nameValuePairs.add(new BasicNameValuePair("feasibility",DataHolderClass.getInstance().getValue_feasibility()));
                    nameValuePairs.add(new BasicNameValuePair("manual_fes",DataHolderClass.getInstance().getStr_manual_fes()));
                    nameValuePairs.add(new BasicNameValuePair("tariff_cat",DataHolderClass.getInstance().get_connect_load()));
                    nameValuePairs.add(new BasicNameValuePair("load_required",DataHolderClass.getInstance().getFeasibility_tariff_load()));

                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    Log.e("name value pair", "" + nameValuePairs);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = httpclient.execute(httppost, responseHandler);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("response", ""+response);
                    //   Log.e("response", response + "+" + e.getMessage().toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();

            try
            {
                response = response.trim();
                Log.e("response",""+response);
                response = response.trim();

                if (response != null && response.equals("1")) {


                    Toast.makeText(getApplicationContext(), "record send successfully", Toast.LENGTH_SHORT).show();

                    DataHolderClass.getInstance().nullify_DataHolder_feasibility();
                    startActivity(new Intent(Feasibility_photo.this, Options.class));
                    finish();

                } else {

                    ShowAlert();

                    //Toast.makeText(getApplicationContext(), "record saved due to server error", Toast.LENGTH_SHORT).show();


                }
            } catch (Exception e) {
                //Toast.makeText(getApplicationContext(), "Due to low internet connectivity this data would be saved in database", Toast.LENGTH_SHORT).show();

                ShowAlert();

                response = null;

                Log.e("response exception", ""+e.getMessage());
            }
            response = null;
        }

    }
*/

    public void ShowAlert() {
        Feasibility_photo.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( Feasibility_photo.this, R.style.MyAlertDialogStyle );
                builder.setTitle ( "Do You Want to..." );
                builder.setPositiveButton ( "Try Again", new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {
                        new SendFeasibility ( ).execute ( );
                    }
                } );
                /*builder.setNegativeButton("Save Record", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        sqLiteMasterTableAdapter.openToRead();
                        sqLiteMasterTableAdapter.openToWrite();
                        sqLiteMasterTableAdapter.insert_feasibility("tag", "str_ticket_no",
                                String.valueOf(home_lat),
                                String.valueOf(home_long),
                                String.valueOf(pole_lat),
                                String.valueOf(pole_long),
                                str_route,
                                value_feasibility,str_manual_fes
                        );
                        sqLiteMasterTableAdapter.close();
                        Toast.makeText(getApplicationContext(),"record saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });*/
                AlertDialog alert = builder.create ( );
                alert.show ( );
            }
        } );
    }


    private void setVisibile() {

        c_name = DataHolderClass.getInstance ( ).getConsumer_image_name ( );
        aa_name = DataHolderClass.getInstance ( ).getAadhar_image ( );
        ad_name = DataHolderClass.getInstance ( ).getAddress_image_name ( );
        l_name = DataHolderClass.getInstance ( ).getLand_image_name ( );


        // if (pending_int > 0) {

        if (c_name == null || aa_name == null || ad_name == null || l_name == null) {
            // submit.setVisibility(View.INVISIBLE);
           /* consumer_btn.setBackgroundColor(Color.YELLOW);
            aadhar_btn.setBackgroundColor(Color.YELLOW);
            address_btn.setBackgroundColor(Color.YELLOW);
            land_btn.setBackgroundColor(Color.YELLOW);*/


        } else {

            consumer_btn.setBackgroundColor ( Color.RED );
            aadhar_btn.setBackgroundColor ( Color.RED );
            address_btn.setBackgroundColor ( Color.RED );
            land_btn.setBackgroundColor ( Color.RED );
        }
    }


}