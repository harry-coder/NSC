package feedback.mpnsc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nitinb on 23-06-2015.
 */
public class MeterDetail extends Activity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    EditText meter_serial_no, reading, ed_seal_number, ed_seal_number1, ed_seal_number2, ed_seal_number3, ed_seal_number4, ed_seal_number5, et_installerName;
    Spinner manufacture_code, meter_type, phase, bill_basis,
            meter_digit, ownership, metered, meter_make;
    TextView date, tv_lat_home, tv_long_home, tv_lat_pole, tv_long_pole;

    AppLocationService appLocationService;
    double home_lat, home_long, pole_lat, pole_long;
    //ConnectionDetector connectionDetector;

    String str_ticket_no, str_meter_no, str_date, str_reading, str_manufacture_code, str_meter_type,
            str_meter_phase, str_bill_basis, str_meter_digit, str_ownership, str_metered, str_seal_number, str_meter_make,
            str_seal_number1, str_seal_number2, str_seal_number3, str_seal_number4, str_seal_number5, meterInstaller;

    Button submit, btn_google_map, meter_btn, btn_home_nw, btn_home_gps, btn_pole_nw, btn_pole_gps;
    TextView tv_division, tv_subdivision, tv_section, tv_route;
    TextView tv_serial, tv_seal1, tv_seal2, tv_mm, tv_date, tv_reading;

    String response, network_interrupt = null;

    TextView tv_ticket_no, tv_consumer_name, tv_consumer_add, tv_consumer_number;
    ConnectionDetector connectionDetector;
    SQLiteMasterTableAdapter sqLiteMasterTableAdapter;
    SessionManager sessionManager;
    SpannableString sp_meter_serial, sp_sealno1, sp_sealno2, sp_makemter, sp_meter_installed, sp_reading;
    SQLiteAdapter sqLiteAdapter;

    Cursor cursor_manufacturer_code, cursor_meter_type, cursor_meter_phase, cursor_bill_basis,
            cursor_meter_digit, cursor_meter_ownership, cursor_metered, cursor_division, cursor_subdivision, cursor_section, cursor_route;
    ArrayList <String> array_manufacturer_code, array_meter_type, array_meter_phase, array_bill_basis,
            array_digit, array_ownership, array_metered;


    ArrayAdapter <String> adapter_manufacturer_code, adapter_meter_type, adapter_meter_phase, adapter_bill_basis,
            adapter_digit, adapter_ownership, adapter_metered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature ( Window.FEATURE_NO_TITLE );
        getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.meterdetail );
        appLocationService = new AppLocationService (
                MeterDetail.this );
        sessionManager = new SessionManager ( MeterDetail.this );
        sqLiteMasterTableAdapter = new SQLiteMasterTableAdapter ( MeterDetail.this );
        connectionDetector = new ConnectionDetector ( MeterDetail.this );

       /* home_lat= Double.parseDouble(tv_lat_home.getText().toString().trim()) ;
        home_long= Double.parseDouble(tv_long_home.getText().toString().trim()) ;

        pole_lat= Double.parseDouble(tv_lat_pole.getText().toString().trim()) ;
        pole_long= Double.parseDouble(tv_long_pole.getText().toString().trim()) ;*/

        tv_lat_home = findViewById ( R.id.tv_lat_home );
        tv_long_home = findViewById ( R.id.tv_long_home );
        tv_lat_pole = findViewById ( R.id.tv_lat_pole );
        tv_long_pole = findViewById ( R.id.tv_long_pole );
        tv_serial = findViewById ( R.id.tv_mobile_no );
        tv_seal1 = findViewById ( R.id.tv_seal_no );
        tv_seal2 = findViewById ( R.id.tv_seal_no1 );
        tv_mm = findViewById ( R.id.tv_meter_make );
        tv_date = findViewById ( R.id.textView23 );
        tv_reading = findViewById ( R.id.textView25 );

        btn_home_nw = findViewById ( R.id.home_network );
        btn_home_gps = findViewById ( R.id.home_gps );
        btn_pole_nw = findViewById ( R.id.pole_network );
        btn_pole_gps = findViewById ( R.id.pole_gps );

        btn_home_nw.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                getHomeLocationNetwork ( );
            }
        } );

        btn_home_gps.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                getHomeLocationGPS ( );
            }
        } );

        btn_pole_nw.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                getPoleLocationNetwork ( );
            }
        } );


        btn_pole_gps.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                getPoleLocationGPS ( );
            }
        } );


        meter_serial_no = findViewById ( R.id.et_serial_no );


        date = findViewById ( R.id.et_date );
        reading = findViewById ( R.id.et_initial_reading );
        ed_seal_number = findViewById ( R.id.et_seal_no );
        ed_seal_number1 = findViewById ( R.id.et_seal_no1 );
        ed_seal_number2 = findViewById ( R.id.et_seal_no2 );
        ed_seal_number3 = findViewById ( R.id.et_seal_no3 );
        ed_seal_number4 = findViewById ( R.id.et_seal_no4 );
        ed_seal_number5 = findViewById ( R.id.et_seal_no5 );

        et_installerName = findViewById ( R.id.et_installerName );

        // manufacture_code = (Spinner) findViewById(R.id.spinner_manufacturer_code);
        meter_make = findViewById ( R.id.spinner_meter_make );
        meter_type = findViewById ( R.id.spinner_metertype );
        phase = findViewById ( R.id.spinner_phase );
        //bill_basis = (Spinner) findViewById(R.id.spinner_bill_basis);
        meter_digit = findViewById ( R.id.spinner_meter_digit );
        //ownership = (Spinner) findViewById(R.id.spinner_ownership);
        //metered = (Spinner) findViewById(R.id.spinner_metered);
        submit = findViewById ( R.id.submit );
        Button dateButton = findViewById ( R.id.date_button );

        btn_google_map = findViewById ( R.id.btn_google_map );

        tv_ticket_no = findViewById ( R.id.tv_ticket_no );
        tv_consumer_name = findViewById ( R.id.tv_consumer_name );
        tv_consumer_number = findViewById ( R.id.tv_consumer_number );
        tv_consumer_add = findViewById ( R.id.tv_consumer_address );

        tv_division = findViewById ( R.id.tv_division );
        tv_subdivision = findViewById ( R.id.tv_subdivision );
        tv_section = findViewById ( R.id.tv_section );
        tv_route = findViewById ( R.id.tv_route );

        sp_meter_serial = new SpannableString ( tv_serial.getText ( ).toString ( ) );
        sp_sealno1 = new SpannableString ( tv_seal1.getText ( ).toString ( ) );
        sp_sealno2 = new SpannableString ( tv_seal2.getText ( ).toString ( ) );
        sp_makemter = new SpannableString ( tv_mm.getText ( ).toString ( ) );
        sp_meter_installed = new SpannableString ( tv_date.getText ( ).toString ( ) );
        sp_reading = new SpannableString ( tv_reading.getText ( ).toString ( ) );
        sp_meter_serial.setSpan ( new ForegroundColorSpan ( Color.RED ), 19, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        sp_sealno1.setSpan ( new ForegroundColorSpan ( Color.RED ), 12, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        sp_sealno2.setSpan ( new ForegroundColorSpan ( Color.RED ), 12, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        sp_makemter.setSpan ( new ForegroundColorSpan ( Color.RED ), 10, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        sp_meter_installed.setSpan ( new ForegroundColorSpan ( Color.RED ), 20, 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        sp_reading.setSpan ( new ForegroundColorSpan ( Color.RED ), 25, 26, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        tv_serial.setText ( sp_meter_serial );
        tv_seal1.setText ( sp_sealno1 );
        tv_seal2.setText ( sp_sealno2 );
        tv_mm.setText ( sp_makemter );
        tv_date.setText ( sp_meter_installed );
        tv_reading.setText ( sp_reading );


        meter_make.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener ( ) {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                //geographic_project_code=geographic_project_code_list.get(position).toString();
                meter_make.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );

            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {
                // TODO Auto-generated method stub
            }
        } );

        // meter_btn=(Button)findViewById(R.id.meter_photo);

        str_ticket_no = DataHolderClass.getInstance ( ).get_new_meter_ticket_no ( );


        //......set value in textview.......//
        tv_ticket_no.setText ( DataHolderClass.getInstance ( ).get_new_meter_ticket_no ( ) );
        tv_consumer_name.setText ( DataHolderClass.getInstance ( ).get_new_meter_cons_name ( ) );
        tv_consumer_number.setText ( DataHolderClass.getInstance ( ).getStr_consumer_number ( ) );
        tv_consumer_add.setText ( DataHolderClass.getInstance ( ).get_new_meter_cons_add ( ) );
       /* Log.e("ticket",DataHolderClass.getInstance().get_new_meter_ticket_no());
        Log.e("tv_consumer_name",DataHolderClass.getInstance().get_new_meter_cons_name());
        Log.e("tv_consumer_add",DataHolderClass.getInstance().get_new_meter_cons_add());*/


        array_manufacturer_code = new ArrayList <String> ( );
        array_meter_type = new ArrayList <String> ( );
        array_meter_phase = new ArrayList <String> ( );
        array_bill_basis = new ArrayList <String> ( );
        array_digit = new ArrayList <String> ( );
        array_ownership = new ArrayList <String> ( );
        array_metered = new ArrayList <String> ( );

        tv_route.setText ( DataHolderClass.getInstance ( ).get_new_meter_cons_route ( ) );
        connectionDetector = new ConnectionDetector ( MeterDetail.this );
        sessionManager = new SessionManager ( MeterDetail.this );
        sqLiteAdapter = new SQLiteAdapter ( MeterDetail.this );
        sqLiteMasterTableAdapter = new SQLiteMasterTableAdapter ( MeterDetail.this );
        sqLiteMasterTableAdapter.openToRead ( );
        sqLiteMasterTableAdapter.openToWrite ( );
        new Value_Spinner ( MeterDetail.this ).execute ( );

        meter_serial_no.addTextChangedListener ( new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                meter_serial_no.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );

            }

            @Override
            public void afterTextChanged(Editable editable) {
                meter_serial_no.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
            }
        } );
        ed_seal_number.addTextChangedListener ( new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ed_seal_number.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ed_seal_number.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
            }
        } );
        ed_seal_number1.addTextChangedListener ( new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ed_seal_number1.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ed_seal_number1.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
            }
        } );
        reading.addTextChangedListener ( new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                reading.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );

            }

            @Override
            public void afterTextChanged(Editable editable) {
                reading.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
            }
        } );


        submit.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                if (validateEntries ( )) {

                    pushingDataToDataHolder ( );
                    startActivity ( new Intent ( MeterDetail.this, MeterImage.class ) );
                    finish ( );

                } /*else {

                }*/

                //c_name= DataHolderClass.getInstance().getConsumer_image_name();

              /*  startActivity(new Intent(MeterDetail.this, MeterImage.class));
                finish();
*/


            }
        } );


        btn_google_map.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
               /* Log.e("lat", ""+DataHolderClass.getInstance().get_lat());
                Log.e("long", ""+DataHolderClass.getInstance().get_long());
*/
                if ((DataHolderClass.getInstance ( ).get_lat ( ).trim ( ).equals ( "null" ))) {
                    ShowAlertNoLocation ( );


                } else {


                    Intent intent = new Intent ( MeterDetail.this, MapsActivity.class );
                    startActivity ( intent );
                }
            }
        } );
        /*submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValue();
                if (str_meter_no.equals("") || str_date.equals("") ||
                        str_meter_type.equals("null") || str_meter_phase.equals("null") ||
                        str_manufacture_code.equals("null") ||
                        str_bill_basis.equals("null") || str_meter_digit.equals("null") ||
                        str_ownership.equals("null") || str_metered.equals("null") || str_reading.equals("")) {
                    ShowAlertReading();
                    // Toast.makeText(getApplicationContext(),"enter all the fields",Toast.LENGTH_SHORT).show();
                } else {
                    if ((str_meter_no.length() < 8) || (str_meter_no.length() > 8)) {
                        showAlertMeterNo();
                    } else {
                      *//*  if (str_reading.equals("") ) {
                            ShowAlertReading();


                        } else {*//*
                        if (connectionDetector.isConnectingToInternet()) {
                            new SendData().execute();

                        } else {

                            sqLiteMasterTableAdapter.insert_new_meter("set_meter", str_ticket_no, str_meter_no,
                                    str_date, str_reading,
                                    str_manufacture_code, str_meter_type,
                                    str_meter_phase, str_bill_basis,
                                    str_meter_digit, str_ownership,
                                    str_metered);
                            Toast.makeText(getApplicationContext(), "record saved", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                        // }

                    }
                }

            }
        });*/
        dateButton.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance ( );
                DatePickerDialog dpd = DatePickerDialog.newInstance (
                        MeterDetail.this,
                        now.get ( Calendar.YEAR ),
                        now.get ( Calendar.MONTH ),
                        now.get ( Calendar.DAY_OF_MONTH )
                );
                dpd.show ( getFragmentManager ( ), "Datepickerdialog" );
            }
        } );

    }


    public void pushingDataToDataHolder() {
        DataHolderClass.getInstance ( ).setTicket_no ( str_ticket_no );

        meterInstaller = et_installerName.getText ( ).toString ( ).trim ( );
        DataHolderClass.getInstance ( ).setMeterInstaller ( meterInstaller );


        str_meter_no = meter_serial_no.getText ( ).toString ( ).trim ( );
        DataHolderClass.getInstance ( ).setStr_meter_no ( str_meter_no );

        str_date = date.getText ( ).toString ( ).trim ( );
        DataHolderClass.getInstance ( ).setStr_date ( str_date );
        str_reading = reading.getText ( ).toString ( ).trim ( );
        DataHolderClass.getInstance ( ).setStr_reading ( str_reading );

        str_seal_number = ed_seal_number.getText ( ).toString ( ).trim ( );
        DataHolderClass.getInstance ( ).setStr_seal_number ( str_seal_number );
        str_seal_number1 = ed_seal_number1.getText ( ).toString ( ).trim ( );
        DataHolderClass.getInstance ( ).setStr_seal1 ( str_seal_number1 );
        str_seal_number2 = ed_seal_number2.getText ( ).toString ( ).trim ( );
        DataHolderClass.getInstance ( ).setStr_seal2 ( str_seal_number2 );
        str_seal_number3 = ed_seal_number3.getText ( ).toString ( ).trim ( );
        DataHolderClass.getInstance ( ).setStr_seal3 ( str_seal_number3 );
        str_seal_number4 = ed_seal_number4.getText ( ).toString ( ).trim ( );
        DataHolderClass.getInstance ( ).setStr_seal4 ( str_seal_number4 );
        str_seal_number5 = ed_seal_number5.getText ( ).toString ( ).trim ( );
        DataHolderClass.getInstance ( ).setStr_seal5 ( str_seal_number5 );

        str_meter_type = meter_type.getSelectedItem ( ).toString ( ).trim ( );
        DataHolderClass.getInstance ( ).setStr_meter_type ( str_meter_type );
        str_meter_phase = phase.getSelectedItem ( ).toString ( ).trim ( );
        DataHolderClass.getInstance ( ).setStr_meter_phase ( str_meter_phase );
        str_meter_digit = meter_digit.getSelectedItem ( ).toString ( ).trim ( );
        DataHolderClass.getInstance ( ).setStr_meter_digit ( str_meter_digit );
        str_meter_make = meter_make.getSelectedItem ( ).toString ( );
        DataHolderClass.getInstance ( ).setStr_meter_make ( str_meter_make );

        home_lat = Double.parseDouble ( tv_lat_home.getText ( ).toString ( ).trim ( ) );
        home_long = Double.parseDouble ( tv_long_home.getText ( ).toString ( ).trim ( ) );

        pole_lat = Double.parseDouble ( tv_lat_pole.getText ( ).toString ( ).trim ( ) );
        pole_long = Double.parseDouble ( tv_long_pole.getText ( ).toString ( ).trim ( ) );

        DataHolderClass.getInstance ( ).setHome_lat ( String.valueOf ( home_lat ) );
        DataHolderClass.getInstance ( ).setHome_long ( String.valueOf ( home_long ) );
        DataHolderClass.getInstance ( ).setPole_lat ( String.valueOf ( pole_lat ) );
        DataHolderClass.getInstance ( ).setPole_long ( String.valueOf ( pole_long ) );
    }

    public boolean validateEntries() {

        getValue ( );

        if (home_lat == 0.0 || home_long == 0.0 || pole_lat == 0.0 || pole_long == 0.0) {
            Toast.makeText ( getApplicationContext ( ), "Please select the location", Toast.LENGTH_SHORT ).show ( );
            return false;

        } else if (meter_serial_no.getText ( ).toString ( ).length ( ) == 0) {
            meter_serial_no.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
            meter_serial_no.requestFocus ( );
            meter_serial_no.setError ( "Meter Serial No. Required" );
            return false;
        } else if (ed_seal_number.getText ( ).toString ( ).length ( ) == 0) {
            ed_seal_number.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
            ed_seal_number.requestFocus ( );
            ed_seal_number.setError ( "Seal Number Req." );
            return false;
        } else if (ed_seal_number1.getText ( ).toString ( ).length ( ) == 0) {
            ed_seal_number1.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
            ed_seal_number1.requestFocus ( );
            ed_seal_number1.setError ( "Seal Number Req." );
            return false;

        } else if (meter_make.getSelectedItem ( ).toString ( ).trim ( ).equals ( "Select Meter Make" )) {
            meter_make.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
            //Toast.makeText(getApplicationContext(), "Select Meter Make* ", Toast.LENGTH_SHORT).show();
            return false;

        } else if (TextUtils.isEmpty ( str_date )) {
            Toast.makeText ( getApplicationContext ( ), "Pick Date* ", Toast.LENGTH_SHORT ).show ( );
            return false;

        } else if (reading.getText ( ).toString ( ).equals ( "" )) {
            reading.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
            reading.requestFocus ( );
            reading.setError ( "Reading Req." );
            return false;
            //   Toast.makeText(getApplicationContext(), "Please Enter Meter Reading* ", Toast.LENGTH_SHORT).show();

        } else if (str_seal_number.equalsIgnoreCase ( str_seal_number1 )) {
            ed_seal_number.setError ( "Duplicate Serial No." );
            ed_seal_number.requestFocus ( );
            return false;

        }
        else if (str_meter_no.equalsIgnoreCase ( str_seal_number)) {
            meter_serial_no.setError ( "Duplicate Meter No." );
            meter_serial_no.requestFocus ( );
            return false;

        }
        else if (str_meter_no.equalsIgnoreCase ( str_seal_number1 )) {
            meter_serial_no.setError ( "Duplicate Meter No." );
            meter_serial_no.requestFocus ( );
            return false;

        }


        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack ( );
    }

    public void ShowAlertonBack() {
        MeterDetail.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( MeterDetail.this, R.style.MyAlertDialogStyle );
                builder.setCancelable ( false );
                builder.setTitle ( "Are you sure to go back:" );
                builder.setPositiveButton ( "YES", new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity ( new Intent ( MeterDetail.this, SearchTicket.class ) );
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

    public void showAlertMeterNo() {
        MeterDetail.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( MeterDetail.this );
                builder.setCancelable ( false );
                builder.setTitle ( "Meter serial number accept only 8 digit:" );
                builder.setPositiveButton ( "OK", new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel ( );
                    }
                } );


                AlertDialog alert = builder.create ( );
                alert.show ( );
            }
        } );
    }

    public void ShowAlertNoLocation() {
        MeterDetail.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( MeterDetail.this );
                builder.setCancelable ( false );
                builder.setTitle ( "Feasibility is not completed:" );
                builder.setPositiveButton ( "OK", new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel ( );
                    }
                } );


                AlertDialog alert = builder.create ( );
                alert.show ( );
            }
        } );
    }

    public void ShowAlertReading() {
        MeterDetail.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( MeterDetail.this );
                builder.setCancelable ( false );
                builder.setTitle ( "Fill all mandatory field:" );
                builder.setPositiveButton ( "OK", new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel ( );
                    }
                } );


                AlertDialog alert = builder.create ( );
                alert.show ( );
            }
        } );
    }

    public void getValue() {
        str_meter_no = meter_serial_no.getText ( ).toString ( ).trim ( );
        str_date = date.getText ( ).toString ( ).trim ( );
        str_reading = reading.getText ( ).toString ( ).trim ( );
        str_seal_number = ed_seal_number.getText ( ).toString ( ).trim ( );
        str_seal_number1 = ed_seal_number1.getText ( ).toString ( ).trim ( );
        str_seal_number2 = ed_seal_number2.getText ( ).toString ( ).trim ( );
        str_seal_number3 = ed_seal_number3.getText ( ).toString ( ).trim ( );
        str_seal_number4 = ed_seal_number4.getText ( ).toString ( ).trim ( );
        str_seal_number5 = ed_seal_number5.getText ( ).toString ( ).trim ( );
        str_meter_type = meter_type.getSelectedItem ( ).toString ( ).trim ( );
        str_meter_phase = phase.getSelectedItem ( ).toString ( ).trim ( );
        str_meter_digit = meter_digit.getSelectedItem ( ).toString ( ).trim ( );
        str_meter_make = meter_make.getSelectedItem ( ).toString ( );

        home_lat = Double.parseDouble ( tv_lat_home.getText ( ).toString ( ).trim ( ) );
        home_long = Double.parseDouble ( tv_long_home.getText ( ).toString ( ).trim ( ) );

        pole_lat = Double.parseDouble ( tv_lat_pole.getText ( ).toString ( ).trim ( ) );
        pole_long = Double.parseDouble ( tv_long_pole.getText ( ).toString ( ).trim ( ) );


        Log.e ( "str_meter_no", "" + str_meter_no );
        Log.e ( "str_date", "" + str_date );
        Log.e ( "str_reading", "" + str_reading );
        Log.e ( "str_manufacture_code", "" + str_manufacture_code );
        Log.e ( "str_meter_type", "" + str_meter_type );
        Log.e ( "str_meter_phase", "" + str_meter_phase );
        Log.e ( "str_bill_basis", "" + str_bill_basis );
        Log.e ( "str_meter_digit", "" + str_meter_digit );
        Log.e ( "str_ownership", "" + str_ownership );
        Log.e ( "str_metered", "" + str_metered );

    }

    public class SendData extends AsyncTask <String, String, String> {
        ProgressDialog pd;


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute ( );
            pd = new ProgressDialog ( MeterDetail.this );
            pd.setMessage ( "record sending" );
            pd.setCancelable ( false );
            pd.show ( );
        }


        @Override
        protected String doInBackground(String... params) {

            ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
            nameValuePairs.add ( new BasicNameValuePair ( "tag", "set_meter" ) );
           /* nameValuePairs.add(new BasicNameValuePair("value", str_ticket_no + "," + str_meter_no + ","
                    + str_date + "," + str_reading + "," + str_manufacture_code + "," + str_meter_type + "," + str_meter_phase + "," +
                    str_bill_basis + "," + str_meter_digit + "," + str_ownership + "," + str_metered));*/
            nameValuePairs.add ( new BasicNameValuePair ( "ticket_number", str_ticket_no ) );
            nameValuePairs.add ( new BasicNameValuePair ( "meter_no", str_meter_no ) );
            nameValuePairs.add ( new BasicNameValuePair ( "seal_no", str_seal_number ) );
            nameValuePairs.add ( new BasicNameValuePair ( "meter_inst_date", str_date ) );
            nameValuePairs.add ( new BasicNameValuePair ( "meter_digit", str_meter_digit ) );
            nameValuePairs.add ( new BasicNameValuePair ( "reading", str_reading ) );
            nameValuePairs.add ( new BasicNameValuePair ( "meter_make", str_meter_make ) );
            nameValuePairs.add ( new BasicNameValuePair ( "meter_type", str_meter_type ) );
            nameValuePairs.add ( new BasicNameValuePair ( "meter_phase", str_meter_phase ) );
            nameValuePairs.add ( new BasicNameValuePair ( "meter_image_name", DataHolderClass.getInstance ( ).getStr_meterinsimageName ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "meter_image", DataHolderClass.getInstance ( ).getStr_meterinsimage ( ) ) );

            nameValuePairs.add ( new BasicNameValuePair ( "meter_status", "" ) );
            nameValuePairs.add ( new BasicNameValuePair ( "lat_home", "" + home_lat ) );
            nameValuePairs.add ( new BasicNameValuePair ( "long_home", "" + home_long ) );
            nameValuePairs.add ( new BasicNameValuePair ( "lat_pole", "" + pole_lat ) );
            nameValuePairs.add ( new BasicNameValuePair ( "long_pole", "" + pole_long ) );

            nameValuePairs.add ( new BasicNameValuePair ( "seal_no1", str_seal_number1 ) );
            nameValuePairs.add ( new BasicNameValuePair ( "seal_no2", str_seal_number2 ) );
            nameValuePairs.add ( new BasicNameValuePair ( "seal_no3", str_seal_number3 ) );
            nameValuePairs.add ( new BasicNameValuePair ( "seal_no4", str_seal_number4 ) );
            nameValuePairs.add ( new BasicNameValuePair ( "seal_no5", str_seal_number5 ) );
            nameValuePairs.add ( new BasicNameValuePair ( "meter_installer_Name", meterInstaller ) );


            /*ticket_number,
                    meter_no,
                    seal_no,
                    seal_no1,
                    seal_no2,
                    seal_no3,
                    seal_no4,
                    seal_no5,
                    meter_inst_date,
                    meter_digit,
                    reading,
                    meter_make,
                    meter_type,
                    meter_phase,
                    lat_home,
                    long_home,
                    lat_pole,
                    long_pole,
                    meter_image_name,
                    meter_image,
                    meter_status)*/


            Log.e ( "namevaluepair", "" + nameValuePairs );
            try {
                HttpClient httpclient = new DefaultHttpClient ( );

                // HttpPost httppost = new HttpPost(sessionManager.GET_URL());

                HttpPost httppost = new HttpPost ( "http://wcrm.fedco.co.in/phedapi/nscapi/set_meter" );

                httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
                ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
                response = httpclient.execute ( httppost, responseHandler );

            } catch (Exception e) {
                network_interrupt = e.getMessage ( ).toString ( );
                Log.e ( "log_tag", "Error in http connection " + e.toString ( ) );
            }
            return response;
        }


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
                        Toast.makeText ( getApplicationContext ( ), "record send successfully", Toast.LENGTH_SHORT ).show ( );
                        finish ( );
                    } else {
                        ShowAlert ( );

                    }


                } else {

                    sqLiteMasterTableAdapter.insert_new_meter ( "set_meter", str_ticket_no, str_meter_no,
                            str_date, str_reading,
                            str_manufacture_code, str_meter_type,
                            str_meter_phase, str_bill_basis,
                            str_meter_digit, str_ownership,
                            str_metered );
                    Toast.makeText ( getApplicationContext ( ), "Record Saved due to internet interruption", Toast.LENGTH_SHORT ).show ( );
                    finish ( );


                }
            } catch (Exception e) {
                ShowAlert ( );
            }


        }


    }

    public void ShowAlert() {
        MeterDetail.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( MeterDetail.this );
                builder.setTitle ( "Do you want to..." );
                builder.setPositiveButton ( "Try Again", new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {
                        new SendData ( ).execute ( );
                    }
                } );
                builder.setNegativeButton ( "Save Record", new DialogInterface.OnClickListener ( ) {

                    public void onClick(DialogInterface dialog, int id) {
                        sqLiteMasterTableAdapter.insert_new_meter ( "set_meter", str_ticket_no, str_meter_no,
                                str_date, str_reading,
                                str_manufacture_code, str_meter_type,
                                str_meter_phase, str_bill_basis,
                                str_meter_digit, str_ownership,
                                str_metered );
                        Toast.makeText ( getApplicationContext ( ), "record saved", Toast.LENGTH_SHORT ).show ( );
                        finish ( );
                    }
                } );
                AlertDialog alert = builder.create ( );
                alert.show ( );
            }
        } );
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date_value = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        System.out.println ( "Pre date is " + date_value );
        Calendar c = Calendar.getInstance ( );
        System.out.println ( "Year is :" + c.get ( Calendar.YEAR ) );
        System.out.println ( "Month is :" + c.get ( Calendar.MONTH ) );
        System.out.println ( "Date is :" + c.get ( Calendar.DAY_OF_MONTH ) );

        String date_valuenow = c.get ( Calendar.DAY_OF_MONTH ) + "-" + ((c.get ( Calendar.MONTH )) + 1) + "-" + c.get ( Calendar.YEAR );
        try {

            SimpleDateFormat formatter = new SimpleDateFormat ( "dd-MM-yyyy" );


            //  String str1 = date_value;
            Date date1 = formatter.parse ( date_value );

            //  String str2 = "13/10/2013";
            Date date2 = formatter.parse ( date_valuenow );

            /*if (date1.compareTo ( date2 ) != 0) {
                Toast.makeText ( getApplicationContext ( ), "DATE MUST BE PRESENT DATE", Toast.LENGTH_SHORT ).show ( );
                System.out.println ( "date2 is Greater than my date1" );
            } else {
                date.setText ( date_value );
            }*/


                date.setText ( date_value );



        } catch (ParseException e1) {
            e1.printStackTrace ( );
        }
//        date.setText(date_value);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String time = "You picked the following time: " + hourString + "h" + minuteString;
        // timeTextView.setText(time);
    }


    public class Value_Spinner extends AsyncTask <String, String, String> {
        ProgressDialog pd;
        Context _context;

        Value_Spinner(Context ctx) {
            _context = ctx;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            // ShowAlertagain();
            super.onPreExecute ( );
            pd = new ProgressDialog ( MeterDetail.this );
            pd.setMessage ( "Please wait..." );
            pd.setCancelable ( false );
            pd.show ( );
            Log.e ( "from", "pre" );
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            //sqlAdapter=new SQLiteAdapter(MeterDetail.this);
            try {
                sqLiteAdapter.openToRead ( );
                sqLiteAdapter.openToWrite ( );
                // cursor_manufacturer_code = sqLiteAdapter.cursor_manufacturer_code();
                cursor_meter_type = sqLiteAdapter.cursor_meter_type ( );
                cursor_meter_phase = sqLiteAdapter.cursor_meter_phase ( );
                // cursor_bill_basis = sqLiteAdapter.cursor_bill_basis();
                cursor_meter_digit = sqLiteAdapter.cursor_meter_digit ( );
                // cursor_meter_ownership = sqLiteAdapter.cursor_meter_ownership();
                cursor_metered = sqLiteAdapter.cursor_meter_metered ( );

                cursor_division = sqLiteAdapter.get_division_name ( DataHolderClass.getInstance ( ).get_new_meter_division ( ) );
                cursor_subdivision = sqLiteAdapter.get_subdivision_name ( DataHolderClass.getInstance ( ).get_new_meter_division ( ),
                        DataHolderClass.getInstance ( ).get_new_meter_cons_subdivision ( ) );
                cursor_section = sqLiteAdapter.get_section_name ( DataHolderClass.getInstance ( ).get_new_meter_cons_subdivision ( ),
                        DataHolderClass.getInstance ( ).get_new_meter_cons_section ( ) );

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

                if (cursor_division != null && cursor_division.moveToFirst ( )) {
                    do {
                        tv_division.setText ( cursor_division.getString ( 1 ).toString ( ).trim ( ) );

                    } while (cursor_division.moveToNext ( ));
                }

                if (cursor_subdivision != null && cursor_subdivision.moveToFirst ( )) {

                    do {
                        tv_subdivision.setText ( cursor_subdivision.getString ( 1 ).toString ( ).trim ( ) );
                    } while (cursor_subdivision.moveToNext ( ));
                }

                if (cursor_section != null && cursor_section.moveToFirst ( )) {

                    do {
                        tv_section.setText ( cursor_section.getString ( 1 ).toString ( ).trim ( ) );

                    } while (cursor_section.moveToNext ( ));
                }

                if (cursor_manufacturer_code != null && cursor_manufacturer_code.moveToFirst ( )) {

                    do {
                        array_manufacturer_code.add ( cursor_manufacturer_code.getString ( 1 ) );

                    } while (cursor_manufacturer_code.moveToNext ( ));
                }

                if (cursor_meter_type != null && cursor_meter_type.moveToFirst ( )) {

                    do {
                        array_meter_type.add ( cursor_meter_type.getString ( 1 ) );
                    } while (cursor_meter_type.moveToNext ( ));
                }

                if (cursor_meter_phase != null && cursor_meter_phase.moveToFirst ( )) {

                    do {
                        array_meter_phase.add ( cursor_meter_phase.getString ( 1 ) );
                    } while (cursor_meter_phase.moveToNext ( ));
                }

                if (cursor_bill_basis != null && cursor_bill_basis.moveToFirst ( )) {

                    do {
                        array_bill_basis.add ( cursor_bill_basis.getString ( 1 ) );
                    } while (cursor_bill_basis.moveToNext ( ));
                }


                if (cursor_meter_ownership != null && cursor_meter_ownership.moveToFirst ( )) {

                    do {
                        array_ownership.add ( cursor_meter_ownership.getString ( 1 ) );
                    } while (cursor_meter_ownership.moveToNext ( ));
                }
                if (cursor_meter_digit != null && cursor_meter_digit.moveToFirst ( )) {

                    do {
                        array_digit.add ( cursor_meter_digit.getString ( 1 ) );
                    } while (cursor_meter_digit.moveToNext ( ));
                }

                if (cursor_metered != null && cursor_metered.moveToFirst ( )) {

                    do {
                        array_metered.add ( cursor_metered.getString ( 1 ) );
                    } while (cursor_metered.moveToNext ( ));
                }


                //adapter_manufacturer_code = new ArrayAdapter<String>(MeterDetail.this, android.R.layout.simple_spinner_item, array_manufacturer_code);
                // adapter_manufacturer_code.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //manufacture_code.setAdapter(adapter_manufacturer_code);


                adapter_meter_type = new ArrayAdapter <String> ( MeterDetail.this, android.R.layout.simple_spinner_item, array_meter_type );
                adapter_meter_type.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
                meter_type.setAdapter ( adapter_meter_type );

                adapter_meter_phase = new ArrayAdapter <String> ( MeterDetail.this, android.R.layout.simple_spinner_item, array_meter_phase );
                adapter_meter_phase.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
                phase.setAdapter ( adapter_meter_phase );


               /* adapter_bill_basis = new ArrayAdapter<String>(MeterDetail.this, android.R.layout.simple_spinner_item, array_bill_basis);
                adapter_bill_basis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                bill_basis.setAdapter(adapter_bill_basis);*/

                adapter_digit = new ArrayAdapter <String> ( MeterDetail.this, android.R.layout.simple_spinner_item, array_digit );
                adapter_digit.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
                meter_digit.setAdapter ( adapter_digit );


              /*  adapter_ownership = new ArrayAdapter<String>(MeterDetail.this, android.R.layout.simple_spinner_item, array_ownership);
                adapter_ownership.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ownership.setAdapter(adapter_ownership);*/

                adapter_metered = new ArrayAdapter <String> ( MeterDetail.this, android.R.layout.simple_spinner_item, array_metered );
                adapter_metered.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
                metered.setAdapter ( adapter_metered );
                sqLiteAdapter.close ( );
            } catch (Exception e) {
                e.printStackTrace ( );
            }
        }
    }


    public void getHomeLocationNetwork() {


        if (appLocationService.getLocation ( ) != null) {
            double latitude = appLocationService.getLatitude ( );
            double longitude = appLocationService.getLongitude ( );
            tv_lat_home.setText ( "" );
            tv_lat_home.setText ( String.valueOf ( latitude ) );
            tv_long_home.setText ( "" );
            tv_long_home.setText ( String.valueOf ( longitude ) );
           /* Toast.makeText(
                    getApplicationContext(),
                    "Mobile Location (NW): \nLatitude: " + latitude
                            + "\nLongitude: " + longitude,
                    Toast.LENGTH_LONG).show();*/
            Log.e ( "Location", "Mobile Location (NW): \nLatitude: " + latitude
                    + "\nLongitude: " + longitude );
        } else {
            showSettingsAlert ( "NETWORK" );
        }

    }

    public void getHomeLocationGPS() {

        if (appLocationService.getLocation ( ) != null) {
            double latitude = appLocationService.getLatitude ( );
            double longitude = appLocationService.getLongitude ( );
            tv_lat_home.setText ( "" );
            tv_lat_home.setText ( String.valueOf ( latitude ) );
            tv_long_home.setText ( "" );
            tv_long_home.setText ( String.valueOf ( longitude ) );
          /*  Toast.makeText(
                    getApplicationContext(),
                    "Mobile Location (GPS): \nLatitude: " + latitude
                            + "\nLongitude: " + longitude,
                    Toast.LENGTH_LONG).show();
*/
        } else {
            showSettingsAlert ( "GPS" );
        }
    }

    //.....................home location........................//
    public void getPoleLocationNetwork() {


        if (appLocationService.getLocation ( ) != null) {
            double latitude = appLocationService.getLatitude ( );
            double longitude = appLocationService.getLongitude ( );
            tv_lat_pole.setText ( "" );
            tv_lat_pole.setText ( String.valueOf ( latitude ) );
            tv_long_pole.setText ( "" );
            tv_long_pole.setText ( String.valueOf ( longitude ) );
            /*Toast.makeText(
                    getApplicationContext(),
                    "Mobile Location (NW): \nLatitude: " + latitude
                            + "\nLongitude: " + longitude,
                    Toast.LENGTH_LONG).show();*/
            Log.e ( "Location", "Mobile Location (NW): \nLatitude: " + latitude
                    + "\nLongitude: " + longitude );
        } else {
            showSettingsAlert ( "NETWORK" );
        }

    }

    public void getPoleLocationGPS() {


        if (appLocationService.getLocation ( ) != null) {
            double latitude = appLocationService.getLatitude ( );
            double longitude = appLocationService.getLongitude ( );
            tv_lat_pole.setText ( "" );
            tv_lat_pole.setText ( String.valueOf ( latitude ) );
            tv_long_pole.setText ( "" );
            tv_long_pole.setText ( String.valueOf ( longitude ) );
           /* Toast.makeText(
                    getApplicationContext(),
                    "Mobile Location (GPS): \nLatitude: " + latitude
                            + "\nLongitude: " + longitude,
                    Toast.LENGTH_LONG).show();
*/
        } else {
            showSettingsAlert ( "GPS" );
        }
    }

    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder (
                MeterDetail.this );

        alertDialog.setTitle ( provider + " SETTINGS" );

        alertDialog
                .setMessage ( provider + " is not enabled! Want to go to settings menu?" );

        alertDialog.setPositiveButton ( "Settings",
                new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent (
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                        MeterDetail.this.startActivity ( intent );
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


}
