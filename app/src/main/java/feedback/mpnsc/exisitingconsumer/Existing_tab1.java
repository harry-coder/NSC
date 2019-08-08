package feedback.mpnsc.exisitingconsumer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import org.json.JSONArray;
import org.json.JSONObject;

import feedback.mpnsc.ConnectionDetector;
import feedback.mpnsc.CustomClasses.ConnectivityDialog;
import feedback.mpnsc.CustomClasses.TicketPojo;
import feedback.mpnsc.DataHolderClass;
import feedback.mpnsc.Feasibility;
import feedback.mpnsc.MeterDetail;
import feedback.mpnsc.MeterImage;
import feedback.mpnsc.Options;
import feedback.mpnsc.R;
import feedback.mpnsc.SQLiteAdapter;
import feedback.mpnsc.SearchTicket;
import feedback.mpnsc.TicketDialogFragment.FeasibilityTicketListBottomDialog;
import feedback.mpnsc.TicketDialogFragment.MeterTicketBottomDialog;
import feedback.mpnsc.TicketDialogFragment.TicketListBottomDialog;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by swatiG on 02-07-2015.
 */
public class Existing_tab1 extends AppCompatActivity {


    // Spinner cycle,route,ownerships,cons_type, department,ed_exception,meter_status;

    Spinner division_spinner, subdivision_spinner, section_spinner;
    String str_division, str_subdivion, str_section, str_cycle, str_route,
            str_ownerships, str_cons_type, str_department, str_ed_exception, str_meter_status;

    SQLiteAdapter sqlAdapter;
    String feasibility, meter;

    //-----------------------    ArrayList   ---------------------------------//
    ArrayList <String> divisionArraycode, divisionArrayname;
    ArrayList <String> subdivisionArraycode, subdivisionArrayname;
    ArrayList <String> sectionArraycode, sectionArrayname;

    //------------------------ ArrayAdapter    ----------------------------//
    ArrayAdapter <String> divisionAdapter;
    ArrayAdapter <String> subdivisionAdapter;
    ArrayAdapter <String> sectionAdapter;
    //ArrayList<String> routeArraycode;
    //--------------------------  Cursor    -----------------------------//
    Cursor divisiontablecursor;
    Cursor subdivisiontablecursor;
    Cursor sectiontablecursor;
    // ArrayAdapter<String> routeAdapter;
    //------------------------- Add select   -----------------------------//

    HashSet <String> set, set1, set2, set3;
    //----------------------- spinner position  ------------------------------//
    String division_code, subdivision_code, section_code;

    Button submit;

    int int_division, int_subdivion, int_section, int_cycle, int_route,
            int_ownerships, int_cons_type;
    /**
     * Called when the activity is first created.
     */
    String str_division_code = "0";
    String str_subdivision_code;
    String str_sec_code = "0";
    int count1 = 0, count = 0, count2;
    boolean check = true;
    TextView TVTicketnum;

    ConnectionDetector connectionDetector;

    EditText et_ticketNo;
    String ticket;

    ImageView im_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature ( Window.FEATURE_NO_TITLE );
        getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.tab1 );
        TextView TVTicketnum1 = findViewById ( R.id.textViewTicketValue );

        TVTicketnum = findViewById ( R.id.textViewTicket );
        division_spinner = findViewById ( R.id.division_spinner );
        // subdivision_spinner=(Spinner)findViewById(R.id.spn_subdivion);
        section_spinner = findViewById ( R.id.section );

        /* cycle=(Spinner)findViewById(R.id.spinner2);
        route=(Spinner)findViewById(R.id.spinner3);
        ownerships=(Spinner)findViewById(R.id.spinner4);
        cons_type=(Spinner)findViewById(R.id.spinner5);
        department=(Spinner)findViewById(R.id.spinner6);
        ed_exception=(Spinner)findViewById(R.id.spinner7);
        meter_status=(Spinner)findViewById(R.id.spinner8);*/

        submit = findViewById ( R.id.submit );
        TVTicketnum.setText ( DataHolderClass.getInstance ( ).get_new_meter_ticket_no ( ) );
        submit = findViewById ( R.id.btn_submit );
        str_division_code = DataHolderExisting.getInstance ( ).getDivision_code ( );
        str_subdivision_code = DataHolderExisting.getInstance ( ).getSub_division_code ( );
        str_sec_code = DataHolderExisting.getInstance ( ).getSection_code ( );
        divisionArraycode = new ArrayList <String> ( );
        divisionArrayname = new ArrayList <String> ( );
        subdivisionArrayname = new ArrayList <String> ( );
        subdivisionArraycode = new ArrayList <String> ( );
        sectionArrayname = new ArrayList <String> ( );
        sectionArraycode = new ArrayList <String> ( );

        connectionDetector = new ConnectionDetector ( this );

        et_ticketNo = findViewById ( R.id.et_ticketNo );

        im_back=findViewById ( R.id.im_back );

        im_back.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                ShowAlertonBack ();
            }
        } );

        // routeArraycode=new ArrayList<String>();

        //  submit.setEnabled ( false );


        String div_code = getIntent ( ).getStringExtra ( "div_code" );
        String sub_div_code = getIntent ( ).getStringExtra ( "sub_div_code" );
        String sec_code = getIntent ( ).getStringExtra ( "sec_code" );


        System.out.println ( "This is is tha div " + div_code );
        System.out.println ( "This is is tha sub " + sub_div_code );
        System.out.println ( "This is is tha sec " + sec_code );


        feasibility = getIntent ( ).getStringExtra ( "feasibility" );
        meter = getIntent ( ).getStringExtra ( "Meter" );


        et_ticketNo.addTextChangedListener ( new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

               // section_spinner.set

                System.out.println ( "Inside the text changed" );
                str_division_code = "0";
                str_sec_code = "0";
                ticket = editable.toString ( );

            }
        } );
        submit.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                if(str_sec_code==null||str_division_code==null){
                    str_division_code="0";
                    str_sec_code="0";
                }

                //ticket=et_ticketNo.getText ().toString ();

                if (connectionDetector.isConnectingToInternet ( )) {

                    /*if (ticket!=null) {


                        new SearchTicketNumber ( ).execute ( ticket );

                    }*/

                    //if ((!TextUtils.isEmpty ( str_division_code ) && !TextUtils.isEmpty ( str_sec_code ))) {


                    // str_division = division_spinner.getSelectedItem ( ).toString ( );
                    //str_subdivion=subdivision_spinner.getSelectedItem().toString();
                    // str_section = section_spinner.getSelectedItem ( ).toString ( );
                    Log.e ( "spinner tab", "" + str_division + str_division + str_section + "" );
                    DataHolderClass dataHolderClass = DataHolderClass.getInstance ( );
                    dataHolderClass.set_division ( division_code );//str_division
                    dataHolderClass.set_subdivision ( subdivision_code );//str_subdivion
                    dataHolderClass.set_section ( section_code );//str_section


                    int_division = division_spinner.getSelectedItemPosition ( );
                    //int_subdivion=subdivision_spinner.getSelectedItemPosition();
                    int_section = section_spinner.getSelectedItemPosition ( );


                        /*if (division_spinner.getSelectedItemPosition ( ) == 0 || section_spinner.getSelectedItemPosition ( ) == 0) {

                            Toast.makeText ( Existing_tab1.this, "Enter all mandatory fields", Toast.LENGTH_SHORT ).show ( );


                        } else {*/


                    System.out.println ( "div_code " + str_division_code );
                    System.out.println ( "sec_code " + str_sec_code );


                    if (feasibility != null) {

                        System.out.println ( "This is in feasibility " + feasibility );
                        FeasibilityTicketListBottomDialog mySheetDialog = FeasibilityTicketListBottomDialog.newInstance ( "" + str_division_code, "" + str_sec_code, "" + ticket );
                        android.support.v4.app.FragmentManager fm = getSupportFragmentManager ( );
                        mySheetDialog.show ( fm, "modal" );
                        // feasibility=null;



                    } else if (meter != null) {

                        MeterTicketBottomDialog mySheetDialog = MeterTicketBottomDialog.newInstance ( "" + str_division_code, "" + str_sec_code, "" + ticket );
                        android.support.v4.app.FragmentManager fm = getSupportFragmentManager ( );
                        mySheetDialog.show ( fm, "modal" );
                       // ticket = null;

                    } else {
                        System.out.println ( "This is in ticket" );


                        System.out.println ( "This is division " + int_division );
                        System.out.println ( "This is section " + int_section );

                        TicketListBottomDialog mySheetDialog = TicketListBottomDialog.newInstance ( "" + str_division_code, "" + str_sec_code, "" + ticket );
                        android.support.v4.app.FragmentManager fm = getSupportFragmentManager ( );
                        mySheetDialog.show ( fm, "modal" );

                      //  ticket = null;
                            /*    str_sec_code=null;
                                str_division_code=null;
                                ticket=null;
*/

                    }

                    ticket = null;
                    et_ticketNo.setText ( "" );
                    division_spinner.setSelection ( 0 );
                    section_spinner.setSelection ( 0 );
                    //Ticket List BottomSheet Dialog

                  /*  Intent i = new Intent(Existing_tab1.this, Existing_CurrentDetailtest.class);
                    startActivity(i);
*/


                    // }
               /* Intent i = new Intent(Existing_tab1.this, Existing_CurrentDetail.class);
                startActivity(i);*/


                    /*} else {

                        Toast.makeText ( Existing_tab1.this, "Please Select the values first!!", Toast.LENGTH_SHORT ).show ( );

                    }*/
                } else {
                    new ConnectivityDialog ( Existing_tab1.this ).showConnectivityDialog ( );
                }
            }


        } );

        new DIVISIONTABLEMANAGER ( Existing_tab1.this ).execute ( );
        division_spinner.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener ( ) {


            @Override
            public void onItemSelected(AdapterView <?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                //  Log.e("from","spinner1");
                division_spinner.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
                et_ticketNo.setText ( "" );

//                System.out.println ( "This is division " + divisionArraycode.get ( position ) );
                if (position > 0) {
                    str_division_code = divisionArraycode.get ( position );

                 //   et_ticketNo.setText ( "" );

                } else {

                    str_division_code = null;
                }

                new SECTIONTABLEMANAGER ( Existing_tab1.this ).execute ( );

               /* System.out.println ("Inside the on item division" );
                division_spinner.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
                if (count > 1) {

                    str_division_code = divisionArraycode.get ( position );
                    //   Log.e("str_division_code",str_division_code);
//                  Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_LONG).show();
                   *//* if (position > 0) {
                        new SUBDIVISIONTABLEMANAGER(Existing_tab1.this).execute();
                    } else {
                        subdivision_spinner.setAdapter(null);
                        section_spinner.setAdapter(null);
                        subdivisionArraycode.clear();
                        subdivisionArrayname.clear();
                        sectionArraycode.clear();
                        sectionArrayname.clear();
                    }*//*
                    new SECTIONTABLEMANAGER ( Existing_tab1.this ).execute ( );
                    *//*if (position > 0) {
                        new SECTIONTABLEMANAGER ( Existing_tab1.this ).execute ( );
                    } else {
                        section_spinner.setAdapter ( null );
                        sectionArraycode.clear ( );
                        sectionArrayname.clear ( );
                    }*//*
                }
                count++;
             */   //  Log.e("count",""+count);
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {
                // TODO Auto-generated method stub
            }
        } );



       /* subdivision_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                if(count1>1) {
                    str_subdivision_code = subdivisionArraycode.get(position).toString();
                  //  Log.e("str_subdivision_code",str_subdivision_code);
//                Toast.makeText(getApplicationContext(),""+subdivision_code,Toast.LENGTH_LONG).show();
                    if (position > 0) {
                        new SECTIONTABLEMANAGER(Existing_tab1.this).execute();
                    } else {
                        section_spinner.setAdapter(null);
                        sectionArraycode.clear();
                        sectionArrayname.clear();
                    }
                   // count2
                }

               // Log.e("from","spinner2");
              count1++;
              //  Log.e("count1",""+count1);


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });*/
        section_spinner.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener ( ) {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                str_sec_code = sectionArraycode.get ( position );


                //

//   Toast.makeText(getApplicationContext(),""+section_code,Toast.LENGTH_LONG).show();
                if (position > 0) {

                }

             //   01244169108

                //  Log.e("from", "spinner3");

            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {
                // TODO Auto-generated method stub
            }
        } );

    }


    public class SearchTicketNumber extends AsyncTask <String, String, String> {
        ProgressDialog pd;
        String response;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute ( );
            pd = new ProgressDialog ( Existing_tab1.this, R.style.MyAlertDialogStyle );
            pd.setMessage ( "searching..." );
            pd.setCancelable ( false );
            pd.show ( );
        }

        @Override
        protected String doInBackground(String... params) {

            String ticketNo = params[0];
            ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
            // nameValuePairs.add(new BasicNameValuePair("tag", "get_consumer_detail"));
            nameValuePairs.add ( new BasicNameValuePair ( "table", ticketNo ) );

            Log.e ( "namepairvalue", "" + nameValuePairs );
            try {
                HttpClient httpclient = new DefaultHttpClient ( );

                // HttpPost httppost = new HttpPost(sessionManager.GET_URL());
               // HttpPost httppost = new HttpPost ( "http://dlenhanceuat.phed.com.ng/dlenhanceapi/nscapi/get_existing" );
                HttpPost httppost = new HttpPost ( "http://dlenhanceuat.phed.com.ng/dlenhanceapi/nscapi/get_existing" );


               // http://dlenhanceuat.phed.com.ng/dlenhanceapi
                httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
                ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
                response = httpclient.execute ( httppost, responseHandler );
            } catch (Exception e) {
                Log.e ( "log_tag", "Error in http connection " + e.toString ( ) );
                //   network_interrupt = e.getMessage ( );
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
                JSONArray infoArray = user_info.getJSONArray ( "Table" );

                System.out.println ( "This is the response " + response );
                if (infoArray.length ( ) > 0) {
                    Intent intent;
                    JSONObject infoObject = infoArray.getJSONObject ( 0 );
                    String div_code = infoObject.getString ( "DIV_CODE" );
                    String sec_code = infoObject.getString ( "SEC_CODE" );
                    String father = infoObject.getString ( "FATHERNAME" );
                    String address1 = infoObject.getString ( "ADDRESS1" );
                    String address2 = infoObject.getString ( "ADDRESS2" );
                    String mobile = infoObject.getString ( "MOBILE_NUMBER" );
                    String name = infoObject.getString ( "NEWFIRSTNAME" );

                    if (feasibility != null) {

                        intent = new Intent ( Existing_tab1.this, Feasibility.class );


                    } else if (meter != null) {
                        intent = new Intent ( Existing_tab1.this, MeterDetail.class );


                    } else {
                        intent = new Intent ( Existing_tab1.this, Existing_CurrentDetailtest.class );


                    }
                    intent.putExtra ( "div_code", div_code );
                    intent.putExtra ( "sec_code", sec_code );
                    intent.putExtra ( "ticket", ticket );
                    intent.putExtra ( "father", father );
                    intent.putExtra ( "address1", address1 );
                    intent.putExtra ( "address2", address2 );
                    intent.putExtra ( "mobile", mobile );
                    intent.putExtra ( "name", name );

                    startActivity ( intent );


                    ticket = null;

                } else {
                    ShowAlert ( "Ticket number not found. Please check" );
                }
            } catch (Exception e) {
                Log.e ( " final exception", "" + e.getMessage ( ) );

                ShowAlert ( "Something went wrong. Try Again!!" );
            }
        }
    }

    public void ShowAlert(final String message) {
        Existing_tab1.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( Existing_tab1.this, R.style.MyAlertDialogStyle );
                builder.setTitle ( message );
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


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack ( );
    }

    public void ShowAlertonBack() {
        Existing_tab1.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( Existing_tab1.this, R.style.MyAlertDialogStyle );
                builder.setTitle ( "Are you sure to go back:" );
                builder.setPositiveButton ( "YES", new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity ( new Intent ( Existing_tab1.this, Options.class ) );
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


    public class DIVISIONTABLEMANAGER extends AsyncTask <String, String, String> {
        ProgressDialog pd;
        Context _context;

        DIVISIONTABLEMANAGER(Context ctx) {
            _context = ctx;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            // ShowAlertagain();
            super.onPreExecute ( );
            pd = new ProgressDialog ( Existing_tab1.this, R.style.MyAlertDialogStyle );
            pd.setMessage ( "Please wait..." );
            pd.setCancelable ( false );
            pd.show ( );
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            sqlAdapter = new SQLiteAdapter ( Existing_tab1.this );
            try {
                sqlAdapter.openToRead ( );
                sqlAdapter.openToWrite ( );
                divisiontablecursor = sqlAdapter.masterdivisioncursorAll ( );
                if (divisiontablecursor != null && divisiontablecursor.moveToFirst ( )) {
                    divisionArraycode.add ( "---select---" );
                    divisionArrayname.add ( "---select---" );
                    do {
                        String scheme = divisiontablecursor.getString ( 2 );
                        String schemename = divisiontablecursor.getString ( 3 );
                        divisionArraycode.add ( scheme );
                        divisionArrayname.add ( schemename );
                    } while (divisiontablecursor.moveToNext ( ));
                }

            } catch (Exception e) {
                e.printStackTrace ( );
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute ( result );
            pd.hide ( );
            pd.dismiss ( );
            try {
                divisionAdapter = new ArrayAdapter <String> ( Existing_tab1.this, android.R.layout.simple_spinner_item, divisionArrayname );
                divisionAdapter.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
                division_spinner.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
                division_spinner.setAdapter ( divisionAdapter );
                sqlAdapter.close ( );
                if (check) {
                    //  Log.e("str_division_code", "" + str_division_code);
                    int position = divisionArraycode.indexOf ( str_division_code );
                    // Log.e("position", "" + position);
                    division_spinner.setSelection ( position );

                    // new SUBDIVISIONTABLEMANAGER(Existing_tab1.this).execute();
                    new SECTIONTABLEMANAGER ( Existing_tab1.this ).execute ( );
                }


            } catch (Exception e) {
                e.printStackTrace ( );
            }
        }
    }

    public class SUBDIVISIONTABLEMANAGER extends AsyncTask <String, String, String> {
        ProgressDialog pd;
        Context _context;

        SUBDIVISIONTABLEMANAGER(Context ctx) {
            _context = ctx;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute ( );
            pd = new ProgressDialog ( Existing_tab1.this );
            pd.setMessage ( "Please wait..." );
            pd.setCancelable ( false );
            pd.show ( );
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                sqlAdapter.openToRead ( );
                sqlAdapter.openToWrite ( );
                if (subdivisiontablecursor != null) {
                    subdivisiontablecursor = null;
                }
                //  Log.e("str_sub_division",""+str_division_code);
                subdivisiontablecursor = sqlAdapter.subdivisionqueueOne ( SQLiteAdapter.DIVISION_CODE + " = '" + str_division_code + "'" );
                subdivisionArraycode.clear ( );
                subdivisionArrayname.clear ( );
                if (subdivisiontablecursor != null && subdivisiontablecursor.moveToFirst ( )) {
                    subdivisionArraycode.add ( "---select---" );
                    subdivisionArrayname.add ( "---select---" );
                    do {
                        String code = subdivisiontablecursor.getString ( 2 );
                        String name = subdivisiontablecursor.getString ( 3 );
                        //  Log.e(" sub division code",code);
                        // Log.e(" sub division name",name);
                        subdivisionArraycode.add ( code );
                        subdivisionArrayname.add ( name );
                    } while (subdivisiontablecursor.moveToNext ( ));
                }
            } catch (Exception e) {
                e.printStackTrace ( );
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute ( result );
            pd.hide ( );
            pd.dismiss ( );
            try {
                subdivisionAdapter = new ArrayAdapter <String> ( Existing_tab1.this, android.R.layout.simple_spinner_item, subdivisionArrayname );
                subdivisionAdapter.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
                subdivision_spinner.setAdapter ( null );
                subdivision_spinner.setAdapter ( subdivisionAdapter );
                sqlAdapter.close ( );
                if (check) {
                    // Log.e("check", "" + check);
                    int position = subdivisionArraycode.indexOf ( str_subdivision_code );
                    //   Log.e("position", "" + position);
                    subdivision_spinner.setSelection ( position );
                    new SECTIONTABLEMANAGER ( Existing_tab1.this ).execute ( );
                }
            } catch (Exception e) {
                e.printStackTrace ( );
            }
        }
    }

    public class SECTIONTABLEMANAGER extends AsyncTask <String, String, String> {
        ProgressDialog pd;
        Context _context;

        SECTIONTABLEMANAGER(Context ctx) {
            _context = ctx;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute ( );
            pd = new ProgressDialog ( Existing_tab1.this, R.style.MyAlertDialogStyle );
            pd.setMessage ( "Please wait..." );
            pd.setProgressStyle ( ProgressDialog.STYLE_SPINNER );
            pd.setCancelable ( false );
            pd.show ( );
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                sqlAdapter.openToRead ( );
                sqlAdapter.openToWrite ( );
                /*if (sectiontablecursor != null) {

                    System.out.println ("Inside section cursor null" );
                    sectiontablecursor = null;
                }*/


                System.out.println ( "This is the code " + SQLiteAdapter.SUB_DIV_CODE + " = '" + str_division_code + "'" );
                sectiontablecursor = sqlAdapter.sectionqueueOne ( SQLiteAdapter.SUB_DIV_CODE + " = '" + str_division_code + "'" );
                sectionArraycode.clear ( );
                sectionArrayname.clear ( );
                if (sectiontablecursor != null && sectiontablecursor.moveToFirst ( )) {
                    sectionArraycode.add ( "---select---" );
                    sectionArrayname.add ( "---select---" );
                    do {

                        String code = sectiontablecursor.getString ( 2 );
                        String name = sectiontablecursor.getString ( 3 );
                        // Log.e(" section code",code);
                        sectionArraycode.add ( code );
                        sectionArrayname.add ( name );
                    } while (sectiontablecursor.moveToNext ( ));
                }
            } catch (Exception e) {
                e.printStackTrace ( );
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute ( result );
            pd.hide ( );
            pd.dismiss ( );
            try {
                sectionAdapter = new ArrayAdapter <String> ( Existing_tab1.this, android.R.layout.simple_spinner_item, sectionArrayname );
                sectionAdapter.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );

                section_spinner.setAdapter ( sectionAdapter );
                sqlAdapter.close ( );
                if (check) {
                    Log.e ( "str_section_code", "" + str_sec_code );
                    int position = sectionArraycode.indexOf ( str_sec_code );
                    //  Log.e("position", "" + position);
                    section_spinner.setSelection ( position );
                    //  Log.e("position double", "" + position);
                    check = false;
                }


            } catch (Exception e) {
                e.printStackTrace ( );
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause ( );
        // Log.e("tab1", "onpause");
        //getValidate ( );
    }


    public void getValue() {


        str_division = division_spinner.getSelectedItem ( ).toString ( );
//        str_subdivion=subdivision_spinner.getSelectedItem().toString();
        str_section = section_spinner.getSelectedItem ( ).toString ( );
       /* str_cycle=cycle.getSelectedItem().toString();
        str_route=route.getSelectedItem().toString();
        str_ownerships=ownerships.getSelectedItem().toString();
        str_cons_type=cons_type.getSelectedItem().toString();
        str_department=department.getSelectedItem().toString();
        str_ed_exception=ed_exception.getSelectedItem().toString();
        str_meter_status=meter_status.getSelectedItem().toString();*/

        //   Log.e("spinner tab",""+str_division+str_division+str_section+"");


        DataHolderClass dataHolderClass = DataHolderClass.getInstance ( );
        dataHolderClass.set_division ( str_division_code );//str_division
        dataHolderClass.set_subdivision ( str_subdivision_code );//str_subdivion
        dataHolderClass.set_section ( str_sec_code );//str_section
        dataHolderClass.set_cycle ( str_cycle );
        dataHolderClass.set_route ( str_route );
        dataHolderClass.set_ownerships ( str_ownerships );
        dataHolderClass.set_cons_type ( str_cons_type );
        dataHolderClass.set_department ( str_department );
        dataHolderClass.set_ed_exception ( str_ed_exception );
        dataHolderClass.set_meter_status ( str_meter_status );

    }

    public void getValidate() {

        int_division = division_spinner.getSelectedItemPosition ( );
        //int_subdivion=subdivision_spinner.getSelectedItemPosition();
        int_section = section_spinner.getSelectedItemPosition ( );


        System.out.println ( "This is the division " + int_division );
        System.out.println ( "This is the division " + int_section );
        /*int_cycle=cycle.getSelectedItemPosition();
        int_route=route.getSelectedItemPosition();
        int_ownerships=ownerships.getSelectedItemPosition();
        int_cons_type=cons_type.getSelectedItemPosition();*/

        //  Log.e("postion",""+int_division+int_subdivion+int_section+int_cycle+int_route+int_ownerships+int_cons_type+"");
        //  Log.e("postion",""+int_division+int_subdivion+int_section+"");
       /* if((int_division==0)||(int_subdivion==0)||(int_section==0)||
                (int_cycle==0)||(int_route==0)||(int_ownerships==0)||(int_cons_type==0))*/


        //if((int_division==0)||(int_subdivion==0)||(int_section==0))

        /*{   Log.e("from","if");
            division_spinner.setBackgroundColor(Color.YELLOW);
            subdivision_spinner.setBackgroundColor(Color.YELLOW);
            section_spinner.setBackgroundColor(Color.YELLOW);
            *//*cycle.setBackgroundColor(Color.YELLOW);
            route.setBackgroundColor(Color.YELLOW);
            ownerships.setBackgroundColor(Color.YELLOW);
            cons_type.setBackgroundColor(Color.YELLOW);*//*
            Toast.makeText(getApplicationContext(), "Enter mandatory fields HIRARCHY", Toast.LENGTH_SHORT).show();



        }
        else{
            getValue();
            Log.e("from","else");
        }*/

        if (int_division == 0) {
            division_spinner.setBackgroundColor ( getResources ( ).getColor ( R.color.edittexttheme ) );
        }
        // else if (int_subdivion==0){subdivision_spinner.setBackgroundColor(Color.YELLOW); }
        else if (int_section == 0) {
            section_spinner.setBackgroundColor ( getResources ( ).getColor ( R.color.edittexttheme ) );
        } else if (int_division != 0 || int_section != 0) {
            getValue ( );
        }
    }
}