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
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import feedback.mpnsc.exisitingconsumer.Existing_tab1;

/**
 * Created by swatiG on 29-06-2015.
 */
public class Feasibility extends Activity {


    Cursor project_cursor;
    SQLiteAdapter sqLiteAdapter_name;


    TextView tv_lat_home, tv_long_home, tv_lat_pole, tv_long_pole, tv_feasibility;
    Button btn_home_nw, btn_home_gps, btn_pole_nw, btn_pole_gps, btn_feasibility, submit;
    Spinner spinner_ds, spinner_ndsb, spinner_tarrif_name;
    String str_spinner_ds, str_spinner_ndsb;
    Spannable transfer_cpty, trnsfr_code, pole_num, loan_req, adj_consumer_no, adj_mru_no, wroting_status, sp_tariffName, sp_ibc, sp_bsc;
    TextView trnfer_cap, trnsfer_code, poleno, loan, consumerno, adj_mru, wrt_status;

    AppLocationService appLocationService;
    double home_lat, home_long, pole_lat, pole_long;
    RadioGroup radio_consumer_type, radio_wiring_status, radio_tariff_type;
    RadioButton radio_wiring_yes, radio_wiring_no;

    SessionManager sessionManager;
    String response, network_interrupt, value_feasibility, str_ticket_no, tariff_id, tariff_load_id;

    SQLiteMasterTableAdapter sqLiteMasterTableAdapter;

    ConnectionDetector connectionDetector;


    EditText edt_manual_fes, ed_spinner_load_ds;
    EditText tranformer_capacity, pole_no, mru_no, n_cons_no;
    String str_tranformer_capacity, str_tranformer_code, str_pole_no, str_mru_no, str_n_cons_no, str_n_mru_no, tariff_ds, tariff_ndsa, tariff_ndsb;
    String str_manual_fes;
    String value_levied, value_wiring, value_tariff;
    LinearLayout linear_distance;
    SQLiteAdapter sqLiteAdapter;
    ArrayList routeArraycode;
    ArrayList <String> project_name_list, project_id_list, tariff_load, tariff_load_unit;
    ArrayAdapter <String> routeAdapter, project_adapter;
    Spinner route_spinner;

    Spinner sp_division, sp_section;
    String str_route;

    ImageView im_back;
    SQLiteAdapter sqlAdapter;

    EditText et_dtc;
    String dtcNo;

    Spinner sp_transformerCode;


    //-----------------------    ArrayList   ---------------------------------//
    ArrayList <String> divisionArraycode, divisionArrayname;
    // ArrayList <String> subdivisionArraycode, subdivisionArrayname;
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
    boolean check = true;
    String str_division_code, str_subdivision_code, str_sec_code;

    String ticket;
    String techStatus;

    String ticketInfoResponse;

    TextView tv_tariffName;
    TextView tv_ibc, tv_bsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.feasibility );
        callView ( );

        Intent intent = getIntent ( );
        Bundle bundle = intent.getExtras ( );
        if (bundle != null) {
            ticket = bundle.getString ( "ticket" );
            techStatus = bundle.getString ( "techStatus" );

        }


        tv_bsc = findViewById ( R.id.tv_bsc );
        tv_ibc = findViewById ( R.id.tv_ibc );

        appLocationService = new AppLocationService ( Feasibility.this );
        sessionManager = new SessionManager ( Feasibility.this );
        sqLiteMasterTableAdapter = new SQLiteMasterTableAdapter ( Feasibility.this );
        connectionDetector = new ConnectionDetector ( Feasibility.this );
        linear_distance = findViewById ( R.id.linear_distance );
        trnfer_cap = findViewById ( R.id.view_transformer_capa );
        trnsfer_code = findViewById ( R.id.view_transformer_code );
        poleno = findViewById ( R.id.view_pole_no );
        loan = findViewById ( R.id.tv_ds_tariff );
        consumerno = findViewById ( R.id.et_ncon_no );
        // adj_mru = findViewById ( R.id.tv_nmru_no );
        wrt_status = findViewById ( R.id.tv_wiring_status );

        spinner_tarrif_name = findViewById ( R.id.spinner_tarrif_name );
        tv_tariffName = findViewById ( R.id.tv_tariffName );

        project_name_list = new ArrayList <String> ( );
        project_id_list = new ArrayList <String> ( );
        tariff_load = new ArrayList <String> ( );
        tariff_load_unit = new ArrayList <String> ( );

        transfer_cpty = new SpannableString ( trnfer_cap.getText ( ).toString ( ) );
        trnsfr_code = new SpannableString ( trnsfer_code.getText ( ).toString ( ) );
        pole_num = new SpannableString ( poleno.getText ( ).toString ( ) );
        loan_req = new SpannableString ( loan.getText ( ).toString ( ) );
        adj_consumer_no = new SpannableString ( consumerno.getText ( ).toString ( ) );
        sp_bsc = new SpannableString ( tv_bsc.getText ( ).toString ( ) );
        sp_ibc = new SpannableString ( tv_ibc.getText ( ).toString ( ) );


//        adj_mru_no = new SpannableString ( adj_mru.getText ( ).toString ( ) );
        wroting_status = new SpannableString ( wrt_status.getText ( ).toString ( ) );
        sp_tariffName = new SpannableString ( tv_tariffName.getText ( ).toString ( ) );

        et_dtc = findViewById ( R.id.et_dtc );

        transfer_cpty.setSpan ( new ForegroundColorSpan ( Color.RED ), 20, 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        trnsfr_code.setSpan ( new ForegroundColorSpan ( Color.RED ), 16, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        pole_num.setSpan ( new ForegroundColorSpan ( Color.RED ), 11, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        loan_req.setSpan ( new ForegroundColorSpan ( Color.RED ), 13, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        adj_consumer_no.setSpan ( new ForegroundColorSpan ( Color.RED ), 15, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        sp_bsc.setSpan ( new ForegroundColorSpan ( Color.RED ), 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        sp_ibc.setSpan ( new ForegroundColorSpan ( Color.RED ), 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );


//        adj_mru_no.setSpan ( new ForegroundColorSpan ( Color.RED ), 10, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        wroting_status.setSpan ( new ForegroundColorSpan ( Color.RED ), 13, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        sp_tariffName.setSpan ( new ForegroundColorSpan ( Color.RED ), 18, 19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );


        trnfer_cap.setText ( transfer_cpty );
        trnsfer_code.setText ( trnsfr_code );
        poleno.setText ( pole_num );
        loan.setText ( loan_req );

        tv_ibc.setText ( sp_ibc );
        tv_bsc.setText ( sp_bsc );


        tv_tariffName.setText ( sp_tariffName );
        consumerno.setText ( adj_consumer_no );
        //      adj_mru.setText ( adj_mru_no );
        wrt_status.setText ( wroting_status );
        ed_spinner_load_ds.setEnabled ( true );

        divisionArraycode = new ArrayList <String> ( );
        divisionArrayname = new ArrayList <String> ( );
        sectionArrayname = new ArrayList <String> ( );
        sectionArraycode = new ArrayList <String> ( );


        new TicketInfo ( ).execute ( );


        new Project_Value ( Feasibility.this ).execute ( );
        //route_spinner=(Spinner)findViewById(R.id.route_spinner);
        //tariff_ds = spinner_ds.getSelectedItem().toString();
//        tariff_ndsa = spinner_ndsb.getSelectedItem().toString();

        linear_distance.setVisibility ( View.INVISIBLE );
       /* spinner_ndsb.setVisibility(View.GONE);
        spinner_ds.setVisibility(View.GONE);
      */
        sqLiteAdapter = new SQLiteAdapter ( Feasibility.this );
        routeArraycode = new ArrayList <String> ( );

       /* spinner_ds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 spinner_ds.setBackgroundColor(getResources().getColor(R.color.themecolor));
                tariff_ds = spinner_ds.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
        //new RouteNo().execute();

        str_ticket_no = getIntent ( ).getStringExtra ( "ticket" );
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

        spinner_tarrif_name.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener ( ) {
            @Override
            public void onItemSelected(AdapterView <?> adapterView, View view, int position, long l) {

                if (position > 0) {


                    tariff_ds = spinner_tarrif_name.getSelectedItem ( ).toString ( );

                    tariff_id = project_id_list.get ( position ).toString ( );
                    tariff_load_id = tariff_load.get ( position ).toString ( );
                    ed_spinner_load_ds.setText ( tariff_load.get ( position ).toString ( ) + " " + tariff_load_unit.get ( position ).toString ( ) );

                    //  System.out.println ( "This is the id " + tariff_id );
                    DataHolderClass.getInstance ( ).set_connect_load ( tariff_id );
                    DataHolderClass.getInstance ( ).setFeasibility_tariff_load ( tariff_load_id );
                }

            }

            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {

            }
        } );


        im_back = findViewById ( R.id.im_back );
        im_back.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                ShowAlertonBack ( );
            }
        } );

   /*     radio_tariff_type = (RadioGroup) findViewById(R.id.radio_tariff_type);
        radio_tariff_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId == R.id.radio_ds_yes) {
                    value_tariff = "1000";

                    spinner_ds.setVisibility(View.VISIBLE);
                    spinner_ndsb.setVisibility(View.INVISIBLE);
                   *//* tariff_ds = spinner_ds.getSelectedItem().toString();
                    Toast.makeText(getApplicationContext(),": \nTariff: " + value_tariff + "\n Load: " + tariff_ds,Toast.LENGTH_LONG).show();
                    DataHolderClass.getInstance().setFeasibility_tariff_load(tariff_ds);*//*
                    //spinner_ndsb.setVisibility(View.INVISIBLE);
                    DataHolderClass.getInstance().set_connect_load(value_tariff);

                } else if (checkedId == R.id.radio_ndsa_no) {
                    value_tariff = "2000";

                   *//* tariff_ndsa = spinner_ndsb.getSelectedItem().toString();
                    DataHolderClass.getInstance().setFeasibility_tariff_load(tariff_ndsa);*//*
                    spinner_ndsb.setVisibility(View.VISIBLE);

                   // Toast.makeText(getApplicationContext(),": \nTariff: " + value_tariff + "\n Load: " + tariff_ndsa,Toast.LENGTH_LONG).show();

                    spinner_ds.setVisibility(View.INVISIBLE);

                    DataHolderClass.getInstance().set_connect_load(value_tariff);
                }else if (checkedId == R.id.radio_ndsb_no) {
                    value_tariff = "3000";
                    spinner_ds.setVisibility(View.VISIBLE);
                    spinner_ndsb.setVisibility(View.INVISIBLE);
*//*
                    tariff_ds = spinner_ds.getSelectedItem().toString();
                    Toast.makeText(getApplicationContext(),": \nTariff: " + value_tariff + "\n Load: " + tariff_ds,Toast.LENGTH_LONG).show();
                     DataHolderClass.getInstance().setFeasibility_tariff_load(tariff_ds);*//*
                    DataHolderClass.getInstance().set_connect_load(value_tariff);
                }

            }
        });*/


        radio_consumer_type = findViewById ( R.id.radio_consumer_type );
        radio_consumer_type.setOnCheckedChangeListener ( new RadioGroup.OnCheckedChangeListener ( ) {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId == R.id.radio_con_yes) {
                    value_levied = "0";
                    n_cons_no.setVisibility ( View.VISIBLE );
                    //n_mru_no.setVisibility ( View.VISIBLE );
                    DataHolderClass.getInstance ( ).setRadio_adjacent_cons ( value_levied );

                } else if (checkedId == R.id.radio_con_no) {
                    value_levied = "1";
                    n_cons_no.setVisibility ( View.VISIBLE );
                    // n_mru_no.setVisibility ( View.VISIBLE );

                    DataHolderClass.getInstance ( ).setRadio_adjacent_cons ( value_levied );
                }

            }
        } );


        radio_wiring_status = findViewById ( R.id.radio_wiring_type );
        radio_wiring_status.setOnCheckedChangeListener ( new RadioGroup.OnCheckedChangeListener ( ) {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId == R.id.radio_wiring_yes) {
                    value_wiring = "0";
                    DataHolderClass.getInstance ( ).setRadio_wiring_status ( value_wiring );

                } else if (checkedId == R.id.radio_wiring_no) {
                    value_wiring = "1";
                    DataHolderClass.getInstance ( ).setRadio_wiring_status ( value_wiring );
                }

            }
        } );


        btn_feasibility.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {


                home_lat = Double.parseDouble ( tv_lat_home.getText ( ).toString ( ).trim ( ) );
                home_long = Double.parseDouble ( tv_long_home.getText ( ).toString ( ).trim ( ) );

                pole_lat = Double.parseDouble ( tv_lat_pole.getText ( ).toString ( ).trim ( ) );
                pole_long = Double.parseDouble ( tv_long_pole.getText ( ).toString ( ).trim ( ) );

                if (home_lat == 0.0 || home_long == 0.0 || pole_lat == 0.0 || pole_long == 0.0) {
                    Log.e ( "from", "if" );
                    Toast.makeText ( getApplicationContext ( ), "get all location", Toast.LENGTH_SHORT ).show ( );
                    //btn_feasibility.setClickable(false);

                } else {
                    //btn_feasibility.setClickable(true);
                    float[] result = new float[5];
                    Log.e ( "from", "else" );
                    Location.distanceBetween ( home_lat, home_long, pole_lat, pole_long, result );
                    // Location.distanceBetween(28.4940004,77.0949538,28.4939842,77.0949632,result);

                    String d = Float.toString ( result[0] );
                    tv_feasibility.setText ( d );
                    value_feasibility = tv_feasibility.getText ( ).toString ( ).trim ( );
                    linear_distance.setVisibility ( View.VISIBLE );
                    submit.setVisibility ( View.VISIBLE );
                    // edt_manual_fes.setVisibility(View.VISIBLE);
                    submit.setClickable ( true );
                    //   Toast.makeText(Feasibility.this, "Distance is " + d,Toast.LENGTH_SHORT).show();
                }
            }
        } );

        sp_transformerCode.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener ( ) {
            @Override
            public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {


                if (i > 0) {
                    str_tranformer_code = sp_transformerCode.getSelectedItem ( ).toString ( );
                    System.out.println ( "This is code " + sp_transformerCode.getSelectedItem ( ).toString ( ) );

                }


            }

            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {

            }
        } );


        submit.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                str_manual_fes = edt_manual_fes.getText ( ).toString ( ).trim ( );
                str_tranformer_capacity = tranformer_capacity.getText ( ).toString ( ).trim ( );

                str_pole_no = pole_no.getText ( ).toString ( ).trim ( );
                str_n_cons_no = n_cons_no.getText ( ).toString ( ).trim ( );

                dtcNo = et_dtc.getText ( ).toString ( );


                // str_n_mru_no = n_mru_no.getText ( ).toString ( ).trim ( );




               /* Toast.makeText(getApplicationContext(),": \nTariff: " + value_tariff + "\n Load: " + tariff_ds,Toast.LENGTH_LONG).show();
                DataHolderClass.getInstance().setFeasibility_tariff_load(tariff_ds);*/

                // tariff_ndsa = spinner_ndsb.getSelectedItem().toString();
               /* Toast.makeText(getApplicationContext(),": \nTariff: " + value_tariff + "\n Load: " + tariff_ndsa,Toast.LENGTH_LONG).show();
                DataHolderClass.getInstance().setFeasibility_tariff_load_b(tariff_ndsa);*/

                Pattern p = Pattern.compile ( "[0-9]*\\.?[0-9]+" );
                Matcher m = p.matcher ( tariff_ds + "" + tariff_ndsa );
                while (m.find ( )) {
                   /* System.out.println(m.group());
                    Log.e("from1234",m.group());*/
                    DataHolderClass.getInstance ( ).setFeasibility_tariff_load ( m.group ( ) );
                }

               /* tariff_ds = spinner_ds.getSelectedItem().toString();
                DataHolderClass.getInstance().setFeasibility_tariff_load(tariff_ds);

                tariff_ndsa = spinner_ndsb.getSelectedItem().toString();
                DataHolderClass.getInstance().setFeasibility_tariff_load(tariff_ndsa);*/
                //DataHolderClass.getInstance().setFeasibility_tariff_load_b(tariff_ndsa);

                DataHolderClass.getInstance ( ).setTicket_no ( str_ticket_no );
                DataHolderClass.getInstance ( ).setHome_lat ( String.valueOf ( home_lat ) );
                DataHolderClass.getInstance ( ).setHome_long ( String.valueOf ( home_long ) );
                DataHolderClass.getInstance ( ).setPole_lat ( String.valueOf ( pole_lat ) );
                DataHolderClass.getInstance ( ).setPole_long ( String.valueOf ( pole_long ) );
                DataHolderClass.getInstance ( ).setValue_feasibility ( value_feasibility );
                DataHolderClass.getInstance ( ).setStr_manual_fes ( str_manual_fes );
                DataHolderClass.getInstance ( ).setStr_transformer_capacity ( str_tranformer_capacity );
                DataHolderClass.getInstance ( ).setStr_transformer_code ( str_tranformer_code );
                DataHolderClass.getInstance ( ).setPole_number ( str_pole_no );
                DataHolderClass.getInstance ( ).setMru_number ( str_mru_no );
                DataHolderClass.getInstance ( ).setAdjacent_cons_no ( str_n_cons_no );
                DataHolderClass.getInstance ( ).setAdjacent_mru_no ( str_n_mru_no );
                DataHolderClass.getInstance ( ).set_connect_load ( tariff_id );
                DataHolderClass.getInstance ( ).setFeasibility_tariff_load ( tariff_load_id );

                str_manual_fes = edt_manual_fes.getText ( ).toString ( ).trim ( );

                if (TextUtils.isEmpty ( str_tranformer_capacity )) {
                    Toast.makeText ( getApplicationContext ( ), "Enter Tranformer Capacity* ", Toast.LENGTH_SHORT ).show ( );
                    tranformer_capacity.setError ( "Field Required" );
                    tranformer_capacity.requestFocus ( );

                } else if (TextUtils.isEmpty ( str_tranformer_code )) {

                    Toast.makeText ( getApplicationContext ( ), "Enter Transformer Code* ", Toast.LENGTH_LONG ).show ( );

                } else if (TextUtils.isEmpty ( str_pole_no )) {

                    pole_no.requestFocus ( );
                    pole_no.setError ( "Field Required" );
                    Toast.makeText ( getApplicationContext ( ), "Enter Pole Number* ", Toast.LENGTH_SHORT ).show ( );

                }

                /*else if (spinner_tarrif_name.getSelectedItem ( ).toString ( ).trim ( ).equals ( "select" )) {
                    Toast.makeText ( getApplicationContext ( ), "Select Tariff Name", Toast.LENGTH_LONG ).show ( );

                }*/

                else if (ed_spinner_load_ds.getText ( ).toString ( ).trim ( ).equals ( "" )) {
                    ed_spinner_load_ds.setError ( "Field Required" );
                    ed_spinner_load_ds.requestFocus ( );
                    Toast.makeText ( getApplicationContext ( ), "Select Category Load Required", Toast.LENGTH_SHORT ).show ( );

                } else if (radio_consumer_type.getCheckedRadioButtonId ( ) == -1) {
                    Toast.makeText ( getApplicationContext ( ), "Select Previous connection in same premises ", Toast.LENGTH_SHORT ).show ( );

                } else if (TextUtils.isEmpty ( str_n_cons_no )) {

                    n_cons_no.setError ( "Field Required" );
                    n_cons_no.requestFocus ( );
                    Toast.makeText ( getApplicationContext ( ), "Enter Adj Consumer Number* ", Toast.LENGTH_SHORT ).show ( );

                } else if (TextUtils.isEmpty ( tariff_ds )) {

                    Toast.makeText ( getApplicationContext ( ), " Select Tariff Name* ", Toast.LENGTH_SHORT ).show ( );


                } else if (TextUtils.isEmpty ( str_division_code )) {

                    Toast.makeText ( getApplicationContext ( ), " Select IBC* ", Toast.LENGTH_SHORT ).show ( );


                } else if (TextUtils.isEmpty ( str_sec_code )) {

                    Toast.makeText ( getApplicationContext ( ), " Select BSC ", Toast.LENGTH_SHORT ).show ( );


                }


                /*else if (TextUtils.isEmpty ( str_n_mru_no )) {
                    Toast.makeText ( getApplicationContext ( ), "Enter Adj MRU Number* ", Toast.LENGTH_SHORT ).show ( );

                }
                */
                else if (radio_wiring_status.getCheckedRadioButtonId ( ) == -1) {
                    Toast.makeText ( getApplicationContext ( ), "Select Wiring Status ", Toast.LENGTH_SHORT ).show ( );

                } else if (TextUtils.isEmpty ( str_manual_fes )) {

                    //Toast.makeText ( getApplicationContext ( ), "Enter Distance* ", Toast.LENGTH_SHORT ).show ( );

                    edt_manual_fes.requestFocus ( );
                    edt_manual_fes.setError ( "Field Required" );

                }/*else if(Integer.parseInt(str_manual_fes)>31){
                    Toast.makeText(getApplicationContext(), "Distance Not More than 30 meter* ", Toast.LENGTH_SHORT).show();

                }*/ else {
                   /* if (connectionDetector.isConnectingToInternet()) {
                         new SendFeasibility().execute();
                    } else {
                        sqLiteMasterTableAdapter.openToRead();
                        sqLiteMasterTableAdapter.openToWrite();
                        sqLiteMasterTableAdapter.insert_feasibility("set_feasibility", "str_ticket_no",
                                String.valueOf(home_lat),
                                String.valueOf(home_long),
                                String.valueOf(pole_lat),
                                String.valueOf(pole_long),
                                str_route,
                                value_feasibility, str_manual_fes
                        );
                        sqLiteMasterTableAdapter.close();
                        Toast.makeText(getApplicationContext(), "Record Saved ", Toast.LENGTH_SHORT).show();
                        finish();

                    }*/

                    System.out.println ( "This is tariff name " + tariff_ds );


                    new SendFeasibility ( ).execute ( );
                    /*startActivity ( new Intent ( Feasibility.this, Feasibility_photo.class ) );
                    finish ( );*/
                }
            }
        } );

        sp_division = findViewById ( R.id.sp_divisionSpinner );
        sp_section = findViewById ( R.id.sp_section );

        new DIVISIONTABLEMANAGER ( Feasibility.this ).execute ( );


        sp_division.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener ( ) {


            @Override
            public void onItemSelected(AdapterView <?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                //  Log.e("from","spinner1");
                sp_division.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );

                if (position > 0) {

                    str_division_code = divisionArraycode.get ( position );
                    System.out.println ( "This is division code " + str_division_code );


                } else {

                    str_division_code = null;
                }

                new SECTIONTABLEMANAGER ( Feasibility.this ).execute ( );

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


        sp_section.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener ( ) {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub


                System.out.println ( "sec code " + str_sec_code );
//                Toast.makeText(getApplicationContext(),""+section_code,Toast.LENGTH_LONG).show();
                if (position > 0) {
                    str_sec_code = sectionArraycode.get ( position );
                }


                //  Log.e("from", "spinner3");

            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {
                // TODO Auto-generated method stub
            }
        } );

    }

    class TicketInfo extends AsyncTask <String, String, String> {
        String network_interrupt = null;
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute ( );
            pd = new ProgressDialog ( Feasibility.this, R.style.MyAlertDialogStyle );
            pd.setMessage ( "record sending" );
            pd.setCancelable ( false );
            pd.show ( );


        }

        //9087506057

        @Override
        protected String doInBackground(String... params) {
            try {

                System.out.println ( "Inside this back" );
                ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
                // nameValuePairs.add(new BasicNameValuePair("tag",  "update_consumer"));


                nameValuePairs.add ( new BasicNameValuePair ( "IBC", "0" ) );
                nameValuePairs.add ( new BasicNameValuePair ( "BSC", "0" ) );
                nameValuePairs.add ( new BasicNameValuePair ( "TICKETNO", ticket ) );
                nameValuePairs.add ( new BasicNameValuePair ( "COND", "12" ) );


                Log.e ( "values", "" + nameValuePairs );
                HttpClient httpclient = new DefaultHttpClient ( );

                //HttpPost httppost = new HttpPost(sessionManager.GET_URL());
                HttpPost httppost = new HttpPost ( "http://dlenhanceuat.phed.com.ng/dlenhanceapi/nscapi/getAllTickets" );


                // HttpPost httppost = new HttpPost("http://sbm.fieldpm.com/sb/handset_reading");
                httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
                ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
                ticketInfoResponse = httpclient.execute ( httppost, responseHandler );
                Log.e ( "Response7", response );


                System.out.println ( "Inside the records back" );

            } catch (Exception e) {


                System.out.println ( "This is exception " + e );
                // Log.e("Response4","->"+e);
                Log.e ( "Response5", "->" + response );
                network_interrupt = e.getMessage ( );
                // Toast.makeText(getApplicationContext(), "There is something wrong cannot send data",Toast.LENGTH_SHORT).show();
                // Toast.makeText(getApplicationContext(), "There is something wrong cannot send data",Toast.LENGTH_SHORT).show();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute ( result );
            pd.dismiss ( );
            pd.hide ( );

            //  if(ticketInfoResponse!=null){

            try {
                JSONObject ticketObject = new JSONObject ( ticketInfoResponse );


                JSONArray ticketArray = ticketObject.getJSONArray ( "Table" );

                if (ticketArray.length ( ) != 0) {
                    JSONObject ticketInfoObject = ticketArray.getJSONObject ( 0 );

                    tranformer_capacity.setText ( ticketInfoObject.getString ( "TRANSFORMER_CAPACITY" ) );
                    pole_no.setText ( ticketInfoObject.getString ( "POLE_NUMBER" ) );
                    ed_spinner_load_ds.setText ( ticketInfoObject.getString ( "LOADREQUIRED" ) );

                    System.out.println ( "This is load required " + ticketInfoObject.getString ( "LOADREQUIRED" ) );
                    n_cons_no.setText ( ticketInfoObject.getString ( "NEARCONSNO" ) );
                    edt_manual_fes.setText ( ticketInfoObject.getString ( "DISTANCE" ) );


                }


            } catch (JSONException e) {
                e.printStackTrace ( );

                System.out.println ( "Exception " + e.getMessage ( ) );
                Log.e ( "TicketInfoException ", e.getMessage ( ) );
            }
            // }

        }


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
            pd = new ProgressDialog ( Feasibility.this, R.style.MyAlertDialogStyle );
            pd.setMessage ( "Please wait..." );
            pd.setCancelable ( false );
            pd.show ( );
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            sqlAdapter = new SQLiteAdapter ( Feasibility.this );
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
                divisionAdapter = new ArrayAdapter <String> ( Feasibility.this, android.R.layout.simple_spinner_item, divisionArrayname );
                divisionAdapter.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );

                sp_division.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
                sp_division.setAdapter ( divisionAdapter );
                sqlAdapter.close ( );
                if (check) {
                    //  Log.e("str_division_code", "" + str_division_code);
                    int position = divisionArraycode.indexOf ( str_division_code );
                    // Log.e("position", "" + position);
                    sp_division.setSelection ( position );

                    // new SUBDIVISIONTABLEMANAGER(Existing_tab1.this).execute();
                    new SECTIONTABLEMANAGER ( Feasibility.this ).execute ( );
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
            pd = new ProgressDialog ( Feasibility.this, R.style.MyAlertDialogStyle );
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


                //  System.out.println ( "This is the code " + SQLiteAdapter.SUB_DIV_CODE + " = '" + str_division_code + "'" );
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
                sectionAdapter = new ArrayAdapter <String> ( Feasibility.this, android.R.layout.simple_spinner_item, sectionArrayname );
                sectionAdapter.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );

                sp_section.setAdapter ( sectionAdapter );
                sqlAdapter.close ( );
                if (check) {
                    Log.e ( "str_section_code", "" + str_sec_code );
                    int position = sectionArraycode.indexOf ( str_sec_code );

                    //  Log.e("position", "" + position);
                    sp_section.setSelection ( position );
                    //  Log.e("position double", "" + position);
                    check = false;
                }


            } catch (Exception e) {
                e.printStackTrace ( );
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack ( );
    }

    public void ShowAlertonBack() {
        Feasibility.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( Feasibility.this, R.style.MyAlertDialogStyle );
                builder.setCancelable ( false );
                builder.setTitle ( "Are you sure to go back:" );
                builder.setPositiveButton ( "YES", new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent ( Feasibility.this, Existing_tab1.class );
                        intent.putExtra ( "feasibility", "Technical Feasibility" );
                        intent.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        startActivity ( intent );


                        //onNavigateUp ();
                        finish ( );

                        //onBackPressed ();

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

        radio_wiring_yes = findViewById ( R.id.radio_wiring_yes );

        radio_wiring_no = findViewById ( R.id.radio_wiring_no );


        tv_lat_home = findViewById ( R.id.tv_lat_home );
        tv_long_home = findViewById ( R.id.tv_long_home );
        tv_lat_pole = findViewById ( R.id.tv_lat_pole );
        tv_long_pole = findViewById ( R.id.tv_long_pole );
        tv_feasibility = findViewById ( R.id.tv_feasibility );

        //spinner_ds=(Spinner)findViewById(R.id.spinner_load_ds);
        //spinner_ds.setVisibility(View.GONE);
        // spinner_ndsb=(Spinner)findViewById(R.id.spinner_load_ndsa);
//        spinner_ndsb.setVisibility(View.GONE);

        ed_spinner_load_ds = findViewById ( R.id.ed_spinner_load_ds );
        tranformer_capacity = findViewById ( R.id.tranformer_capacity );

        // tranformer_code = findViewById ( R.id.tranformer_code );
        sp_transformerCode = findViewById ( R.id.sp_transformerCode );

        pole_no = findViewById ( R.id.pole_no );
        //mru_no=(EditText)findViewById(R.id.mru_no);

        n_cons_no = findViewById ( R.id.et_ncons_no );
        n_cons_no.setVisibility ( View.GONE );
        // n_mru_no = findViewById ( R.id.et_nmru_no );
        // n_mru_no.setVisibility ( View.GONE );

        btn_home_nw = findViewById ( R.id.home_network );
        btn_home_gps = findViewById ( R.id.home_gps );
        btn_pole_nw = findViewById ( R.id.pole_network );
        btn_pole_gps = findViewById ( R.id.pole_gps );
        btn_feasibility = findViewById ( R.id.btn_feasibility );


        submit = findViewById ( R.id.submit );

        edt_manual_fes = findViewById ( R.id.edt_manual_fes );
    }

    //.....................home location........................//
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
                Feasibility.this, R.style.MyAlertDialogStyle );

        alertDialog.setTitle ( provider + " SETTINGS" );

        alertDialog
                .setMessage ( provider + " is not enabled! Want to go to settings menu?" );

        alertDialog.setPositiveButton ( "Settings",
                new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent (
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                        Feasibility.this.startActivity ( intent );
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

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute ( );
            pd = new ProgressDialog ( Feasibility.this, R.style.MyAlertDialogStyle );
            pd.setMessage ( "record sending" );
            pd.setCancelable ( false );
            pd.show ( );
        }

        @Override
        protected String doInBackground(String... params) {

            /*ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
            //  nameValuePairs.add(new BasicNameValuePair("tag","set_feasibility"));
            nameValuePairs.add ( new BasicNameValuePair ( "TicketNumber", str_ticket_no ) );
            nameValuePairs.add ( new BasicNameValuePair ( "lat_home", String.valueOf ( home_lat ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "long_home", String.valueOf ( home_long ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "lat_pole", String.valueOf ( pole_lat ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "long_pole", String.valueOf ( pole_long ) ) );

            nameValuePairs.add ( new BasicNameValuePair ( "transformer_capacity", str_tranformer_capacity ) );
            nameValuePairs.add ( new BasicNameValuePair ( "transformer_code", str_tranformer_code ) );
            nameValuePairs.add ( new BasicNameValuePair ( "pole_number", str_pole_no ) );
            nameValuePairs.add ( new BasicNameValuePair ( "NearConssStatus", DataHolderClass.getInstance ( ).getRadio_adjacent_cons ( ) ) );

            nameValuePairs.add ( new BasicNameValuePair ( "NearConsNO", str_n_cons_no ) );
            nameValuePairs.add ( new BasicNameValuePair ( "mru_number", "" ) );

            nameValuePairs.add ( new BasicNameValuePair ( "WiringStatus", DataHolderClass.getInstance ( ).getRadio_wiring_status ( ) ) );

            nameValuePairs.add ( new BasicNameValuePair ( "feasibility", String.valueOf ( value_feasibility ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "DISTANCE", str_manual_fes ) );
            nameValuePairs.add ( new BasicNameValuePair ( "NearConsMRUNO", "" ) );
            nameValuePairs.add ( new BasicNameValuePair ( "feasibility_remark", "" ) );
            nameValuePairs.add ( new BasicNameValuePair ( "CustGroup", "" ) );
            nameValuePairs.add ( new BasicNameValuePair ( "LoadRequired", ed_spinner_load_ds.toString ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "ApplicationStatus", "" ) );*/


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
            nameValuePairs.add ( new BasicNameValuePair ( "feasibility_remark", "" ) );
            nameValuePairs.add ( new BasicNameValuePair ( "feasibility", DataHolderClass.getInstance ( ).getValue_feasibility ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "DISTANCE", DataHolderClass.getInstance ( ).getStr_manual_fes ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "CustGroup", DataHolderClass.getInstance ( ).get_connect_load ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "LoadRequired", DataHolderClass.getInstance ( ).getFeasibility_tariff_load ( ) ) );
            nameValuePairs.add ( new BasicNameValuePair ( "ApplicationStatus", "" ) );
            nameValuePairs.add ( new BasicNameValuePair ( "NearConsMRUNO", "" ) );
            nameValuePairs.add ( new BasicNameValuePair ( "ibc", str_division_code ) );
            nameValuePairs.add ( new BasicNameValuePair ( "bsc", str_sec_code ) );
            nameValuePairs.add ( new BasicNameValuePair ( "TECHNICAL_FEASIBILITY_REMARKS", "" ) );
            nameValuePairs.add ( new BasicNameValuePair ( "technical_feasibility_by", "" ) );
           // nameValuePairs.add ( new BasicNameValuePair ( "feasibility_status", techStatus ) );
            nameValuePairs.add ( new BasicNameValuePair ( "feasibility_status", "1" ) );
            nameValuePairs.add ( new BasicNameValuePair ( "con_category", tariff_id ) );


            //tariff code send,

            //   tariff code


            Log.e ( "namevaluepair", "" + nameValuePairs );
            try {
                HttpClient httpclient = new DefaultHttpClient ( );

                // HttpPost httppost = new HttpPost(sessionManager.GET_URL());
                HttpPost httppost = new HttpPost ( "http://dlenhanceuat.phed.com.ng/dlenhanceapi/nscapi/set_feasibility" );

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
                        Intent intent = new Intent ( Feasibility.this, LoadExpenseActivity.class );
                        intent.putExtra ( "ticketNo", str_ticket_no );
                        System.out.println ( "Ticket no. " + str_ticket_no );

                        startActivity ( intent );
                      //  startActivity ( new Intent ( Feasibility.this, LoadExpenseActivity.class ) );

                        finish ( );

                    } else {
                        ShowAlert ( );

                    }


                } else {
                    sqLiteMasterTableAdapter.openToRead ( );
                    sqLiteMasterTableAdapter.openToWrite ( );
                    sqLiteMasterTableAdapter.insert_feasibility ( "set_feasibility", str_ticket_no,
                            String.valueOf ( home_lat ),
                            String.valueOf ( home_long ),
                            String.valueOf ( pole_lat ),
                            String.valueOf ( pole_long ),
                            str_route,
                            value_feasibility, str_manual_fes
                    );
                    sqLiteMasterTableAdapter.close ( );
                    Toast.makeText ( getApplicationContext ( ), "Record Saved due to internet interruption", Toast.LENGTH_SHORT ).show ( );
                    finish ( );


                }
            } catch (Exception e) {
                ShowAlert ( );
            }


        }
    }

    public void ShowAlert() {
        Feasibility.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( Feasibility.this, R.style.MyAlertDialogStyle );
                builder.setTitle ( "Do you want to..." );
                builder.setPositiveButton ( "Try Again", new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {
                        new SendFeasibility ( ).execute ( );
                    }
                } );
                builder.setNegativeButton ( "Save Record", new DialogInterface.OnClickListener ( ) {

                    public void onClick(DialogInterface dialog, int id) {
                        sqLiteMasterTableAdapter.openToRead ( );
                        sqLiteMasterTableAdapter.openToWrite ( );
                        sqLiteMasterTableAdapter.insert_feasibility ( "tag", "str_ticket_no",
                                String.valueOf ( home_lat ),
                                String.valueOf ( home_long ),
                                String.valueOf ( pole_lat ),
                                String.valueOf ( pole_long ),
                                str_route,
                                value_feasibility, str_manual_fes
                        );
                        sqLiteMasterTableAdapter.close ( );
                        Toast.makeText ( getApplicationContext ( ), "record saved", Toast.LENGTH_SHORT ).show ( );
                        finish ( );
                    }
                } );
                AlertDialog alert = builder.create ( );
                alert.show ( );
            }
        } );

    }

    //tariff_name_Load from sqlite
    public class Project_Value extends AsyncTask <String, String, String> {
        ProgressDialog pd;
        Context _context;

        Project_Value(Context ctx) {
            _context = ctx;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            // ShowAlertagain();
            super.onPreExecute ( );
            pd = new ProgressDialog ( Feasibility.this, R.style.MyAlertDialogStyle );
            pd.setMessage ( "Please wait..." );
            pd.setCancelable ( false );
            pd.show ( );
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            sqLiteAdapter_name = new SQLiteAdapter ( Feasibility.this );
            try {
                sqLiteAdapter_name.openToRead ( );
                sqLiteAdapter_name.openToWrite ( );
                project_cursor = sqLiteAdapter_name.tariffname ( );
                if (project_cursor != null && project_cursor.moveToFirst ( )) {
                    project_name_list.add ( "Select Tariff Name " );
                    project_id_list.add ( "Select Tariff Name " );
                    tariff_load.add ( " " );
                    tariff_load_unit.add ( " " );
                    do {

                        project_id_list.add ( project_cursor.getString ( 2 ).toString ( ) );

                        project_name_list.add ( project_cursor.getString ( 3 ).toString ( ) );

                        tariff_load.add ( project_cursor.getString ( 4 ) );
                        tariff_load_unit.add ( project_cursor.getString ( 5 ) );

                       /* Log.e("dist_code",dist_code);
                        Log.e("dist_name",dist_name);*/

                    } while (project_cursor.moveToNext ( ));
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
                project_adapter = new ArrayAdapter <String> ( Feasibility.this, android.R.layout.simple_spinner_item, project_id_list );
                project_adapter.setDropDownViewResource ( R.layout.spinner_item );
                spinner_tarrif_name.setAdapter ( project_adapter );
                sqLiteAdapter.close ( );
            } catch (Exception e) {
                e.printStackTrace ( );
            }
        }
    }


}