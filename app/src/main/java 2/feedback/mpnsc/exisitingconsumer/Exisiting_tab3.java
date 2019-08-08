package feedback.mpnsc.exisitingconsumer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashSet;

import feedback.mpnsc.ConnectionDetector;
import feedback.mpnsc.DataHolderClass;
import feedback.mpnsc.MultipleTicketManager;
import feedback.mpnsc.Options;
import feedback.mpnsc.R;
import feedback.mpnsc.SQLiteAdapter;
import feedback.mpnsc.SQLiteMasterTableAdapter;
import feedback.mpnsc.SessionManager;

/**
 * Created by swatiG on 02-07-2015.
 */
public class Exisiting_tab3 extends Activity {


    ConnectionDetector connectionDetector;

    SQLiteMasterTableAdapter sqLiteMasterTableAdapter;
    MultipleTicketManager sqLiteMultipleTicketList;

    String response;
    SessionManager sessionManager;




    EditText connect_load;
    EditText transformer_capacity, transformer_code, feeder_name;
    Spinner tariff_cat,tariff_spinner_subcat,tariff_spinner_subcat_a;
    TextView load_unit;
    String str_supply_type, str_supply_level, str_load_unit, str_metering_side, str_trans_ownership, str_trans_capacity,
            str_tariff_cat, str_bill_demand, str_scheme, str_connection, str_connect_load, str_other,
            str_transformer_capacity, str_transformer_code, str_feeder_name,str_tariff_subcat,str_tariff_subcat_a;
    /**
     * Called when the activity is first created.
     */


    int int_tariff_cat, int_connection;


    // Spinner cycle,route,ownerships,cons_type, department,ed_exception,meter_status;

    Spinner tariff_spinner, feeder_spinner;
    String str_tariff, str_feeder;

    SQLiteAdapter sqlAdapter;

    //-----------------------    ArrayList   ---------------------------------//
    ArrayList<String> tariffArraycode, tariffArrayname;
    ArrayList<String> feederArraycode, feederArrayname;
    // ArrayList<String> routeArraycode;
    //------------------------ ArrayAdapter    ----------------------------//
    ArrayAdapter<String> tariffAdapter;
    ArrayAdapter<String> feederAdapter;

    //--------------------------  Cursor    -----------------------------//
    Cursor tarifftablecursor;
    Cursor feedertablecursor;

    //------------------------- Add select   -----------------------------//

    HashSet<String> set, set1, set2, set3;
    //----------------------- spinner position  ------------------------------//
    String tariff_code, feeder_code;

    Button submit;

    int int_division, int_feeder, int_section, int_cycle, int_route,
            int_ownerships, int_cons_type;
    String str_tariff_code, str_feeder_code, str_sec_code;
    int count1 = 0, count = 0, count2;
    boolean check = true;
    TextView TVTicketnum;
    //TextView load_unit,TVTicketnum;
    TextView tv_subcat,tv_subcat_a;
    String str_connect_load1;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3);
        callView();
        connectionDetector=new ConnectionDetector(this);
        sessionManager=new SessionManager(Exisiting_tab3.this);
        sqLiteMasterTableAdapter= new SQLiteMasterTableAdapter(Exisiting_tab3.this);
        sqLiteMultipleTicketList=new MultipleTicketManager(Exisiting_tab3.this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   str_connect_load1 = tariff_spinner_subcat.getSelectedItem().toString();
                str_connect_load1 = tariff_spinner_subcat_a.getSelectedItem().toString();

                if(tariff_spinner_subcat.getSelectedItem().toString().trim().equals("Select Load") ) {

                    Toast.makeText(Exisiting_tab3.this, "Enter all mandatory fields", Toast.LENGTH_SHORT).show();


                } else {


                    DataHolderClass.getInstance().set_connect_load(str_connect_load1);
                    Intent i = new Intent(Exisiting_tab3.this, Existing_CaptureImage.class);
                    startActivity(i);

                } */
                //str_connect_load = tariff_spinner_subcat.getSelectedItem().toString();
                //DataHolderClass.getInstance().set_connect_load(str_connect_load);

                getValidate();


            }
        });
        TVTicketnum.setText(DataHolderClass.getInstance().get_new_meter_ticket_no());
        //connect_load.setText(DataHolderExisting.getInstance().getLoadreq());
       /* transformer_capacity.setText(DataHolderExisting.getInstance().getTransformer_capacity());
        transformer_code.setText(DataHolderExisting.getInstance().getTransformer_code());*/
        tv_subcat = (TextView) findViewById(R.id.textView_subcat);
        tv_subcat.setVisibility(View.GONE);
        tv_subcat_a = (TextView) findViewById(R.id.textView_subcat_a);
        tv_subcat_a.setVisibility(View.GONE);
        tariff_spinner_subcat = (Spinner) findViewById(R.id.spinner_subcat);
        tariff_spinner_subcat.setVisibility(View.GONE);
        tariff_spinner_subcat_a = (Spinner) findViewById(R.id.spinner_subcat_a);
        tariff_spinner_subcat_a.setVisibility(View.GONE);

        str_tariff_code = DataHolderExisting.getInstance().getLoadcategory();
        //str_feeder_code = DataHolderExisting.getInstance().getFeeder_name();

        tariffArraycode = new ArrayList<String>();
        tariffArrayname = new ArrayList<String>();

        new TARIFFTABLEMANAGER(Exisiting_tab3.this).execute();


        tariff_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                //  Log.e("from","spinner1");
                if (count > 1) {

                    str_tariff_code = tariffArraycode.get(position).toString();

                    if (position == 1) {

                        tariff_spinner_subcat = (Spinner) findViewById(R.id.spinner_subcat);
                        tariff_spinner_subcat.setVisibility(View.VISIBLE);
                        tv_subcat.setVisibility(View.VISIBLE);

                        //str_connect_load = tariff_spinner_subcat.getSelectedItem().toString();
                        //DataHolderClass.getInstance().set_connect_load(str_connect_load);
                        tariff_spinner_subcat_a.setVisibility(View.GONE);
                        tv_subcat_a.setVisibility(View.GONE);
                    } else if (position == 2 ) {
                        tariff_spinner_subcat_a = (Spinner) findViewById(R.id.spinner_subcat_a);
                        tariff_spinner_subcat_a.setVisibility(View.VISIBLE);
                        tv_subcat_a.setVisibility(View.VISIBLE);

                        str_connect_load = tariff_spinner_subcat_a.getSelectedItem().toString();
                        //DataHolderClass.getInstance().set_connect_load(str_connect_load);
                        tariff_spinner_subcat.setVisibility(View.GONE);
                        tv_subcat.setVisibility(View.GONE);
                    } else if (position == 3 ) {
                        tariff_spinner_subcat = (Spinner) findViewById(R.id.spinner_subcat);
                        tariff_spinner_subcat.setVisibility(View.VISIBLE);
                        tv_subcat.setVisibility(View.VISIBLE);

                        str_connect_load = tariff_spinner_subcat.getSelectedItem().toString();
                        //DataHolderClass.getInstance().set_connect_load(str_connect_load);
                        tariff_spinner_subcat_a.setVisibility(View.GONE);
                        tv_subcat_a.setVisibility(View.GONE);
                    } else {

                        tariff_spinner_subcat.setVisibility(View.GONE);
                        tariff_spinner_subcat_a.setVisibility(View.GONE);
                        tv_subcat.setVisibility(View.GONE);
                        tv_subcat_a.setVisibility(View.GONE);
                        //tariff_spinner_subcat.setVisibility(View.INVISIBLE);

                    }
                    //   Log.e("str_division_code",str_division_code);
//                  Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_LONG).show();
                    if (position > 0) {
                        // new SUBDIVISIONTABLEMANAGER(Existing_tab1.this).execute();
                    } else {

                    }
                }
                count++;
                //  Log.e("count",""+count);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        feederArraycode = new ArrayList<String>();
        feederArrayname = new ArrayList<String>();

        new FEEDERTABLEMANAGER(Exisiting_tab3.this).execute();


        feeder_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                //  Log.e("from","spinner1");
                if (count > 1) {

                    str_feeder_code = feederArraycode.get(position).toString();
                    //   Log.e("str_division_code",str_division_code);
//                  Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_LONG).show();
                    if (position > 0) {
                        // new SUBDIVISIONTABLEMANAGER(Existing_tab1.this).execute();
                    } else {

                    }
                }
                count++;
                //  Log.e("count",""+count);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


    }


    public class TARIFFTABLEMANAGER extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        Context _context;

        TARIFFTABLEMANAGER(Context ctx) {
            _context = ctx;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            // ShowAlertagain();
            super.onPreExecute();
            pd = new ProgressDialog(Exisiting_tab3.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            sqlAdapter = new SQLiteAdapter(Exisiting_tab3.this);
            try {
                sqlAdapter.openToRead();
                sqlAdapter.openToWrite();
                tarifftablecursor = sqlAdapter.mastertariffcursorAll();
                if (tarifftablecursor != null && tarifftablecursor.moveToFirst()) {
                    tariffArraycode.add("---select---");
                    tariffArrayname.add("---select---");
                    do {
                        String scheme = tarifftablecursor.getString(2);
                        String schemename = tarifftablecursor.getString(3);
                        tariffArraycode.add(scheme);
                        tariffArrayname.add(schemename);
                    } while (tarifftablecursor.moveToNext());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();
            try {
                tariffAdapter = new ArrayAdapter<String>(Exisiting_tab3.this, android.R.layout.simple_spinner_item, tariffArrayname);
                tariffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tariff_spinner.setAdapter(tariffAdapter);
                sqlAdapter.close();
                if (check) {
                    //  Log.e("str_division_code", "" + str_division_code);
                    int position = tariffArraycode.indexOf(str_tariff_code);
                    // Log.e("position", "" + position);
                    tariff_spinner.setSelection(position);

                    // new SUBDIVISIONTABLEMANAGER(Existing_tab1.this).execute();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class FEEDERTABLEMANAGER extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        Context _context;

        FEEDERTABLEMANAGER(Context ctx) {
            _context = ctx;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            // ShowAlertagain();
            super.onPreExecute();
            pd = new ProgressDialog(Exisiting_tab3.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            sqlAdapter = new SQLiteAdapter(Exisiting_tab3.this);
            try {
                sqlAdapter.openToRead();
                sqlAdapter.openToWrite();
                feedertablecursor = sqlAdapter.masterfeedercursorAll();
                if (feedertablecursor != null && feedertablecursor.moveToFirst()) {
                    feederArraycode.add("---select---");
                    feederArrayname.add("---select---");
                    do {
                        String scheme = feedertablecursor.getString(2);
                        String schemename = feedertablecursor.getString(3);
                        feederArraycode.add(scheme);
                        feederArrayname.add(schemename);
                    } while (feedertablecursor.moveToNext());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();
            try {
                feederAdapter = new ArrayAdapter<String>(Exisiting_tab3.this, android.R.layout.simple_spinner_item, feederArrayname);
                feederAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                feeder_spinner.setAdapter(feederAdapter);
                sqlAdapter.close();
                if (check) {
                    //  Log.e("str_division_code", "" + str_division_code);
                    int position = feederArraycode.indexOf(str_feeder_code);
                    // Log.e("position", "" + position);
                    feeder_spinner.setSelection(position);
                 }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack();
    }

    public void ShowAlertonBack() {
             Exisiting_tab3.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Exisiting_tab3.this);
                builder.setTitle("Are you sure to go back:");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Exisiting_tab3.this, ExistingConsumer_Search.class));
                        finish();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void callView() {
        TVTicketnum = (TextView) findViewById(R.id.textViewTicketValue);
        load_unit = (TextView) findViewById(R.id.spinner13);
        tariff_spinner = (Spinner) findViewById(R.id.spinner17);
        feeder_spinner = (Spinner) findViewById(R.id.spinner20);
        // ....... edit text........//

        /*connect_load = (EditText) findViewById(R.id.spinner10);
         transformer_capacity = (EditText) findViewById(R.id.spinner12);
         transformer_code = (EditText) findViewById(R.id.spinner14);*/
        //feeder_name=(EditText)findViewById(R.id.spinner15);

        tariff_spinner_subcat = (Spinner) findViewById(R.id.spinner_subcat);
        tariff_spinner_subcat_a = (Spinner) findViewById(R.id.spinner_subcat_a);

        submit = (Button) findViewById(R.id.submit);
    }


    public void getValue() {

        //str_load_unit = load_unit.getText().toString();

        //str_tariff_cat = tariff_cat.getSelectedItem().toString();
        str_tariff = tariff_spinner.getSelectedItem().toString();
        //str_feeder = feeder_spinner.getSelectedItem().toString();

        // str_connect_load = connect_load.getText().toString().trim();
        //  str_other = other.getText().toString().trim();
       /*   str_transformer_capacity = transformer_capacity.getText().toString().trim();
            str_transformer_code = transformer_code.getText().toString().trim();*/

        str_tariff_subcat = tariff_spinner_subcat.getSelectedItem().toString();
        str_tariff_subcat_a = tariff_spinner_subcat_a.getSelectedItem().toString();
        //str_feeder_name = feeder_name.getText().toString().trim();

        System.out.println("str_str_tariff " + str_tariff);

        DataHolderClass dataHolderClass = DataHolderClass.getInstance();
        dataHolderClass.set_supply_type(str_supply_type);
        dataHolderClass.set_supply_level(str_supply_level);
        dataHolderClass.set_load_unit(str_load_unit);
        dataHolderClass.set_metering_side(str_metering_side);
        dataHolderClass.set_trans_ownership(str_trans_ownership);
        dataHolderClass.set_trans_capacity(str_trans_capacity);
        dataHolderClass.set_tariff_cat(str_tariff_code);
        //  dataHolderClass.set_tariff_cat(str_division);
        dataHolderClass.set_bill_demand(str_bill_demand);
        dataHolderClass.set_scheme(str_scheme);
        dataHolderClass.set_connection(str_connection);
        //dataHolderClass.set_connect_load(str_tariff_subcat);
        //dataHolderClass.set_connect_load(str_tariff_subcat_a);
        dataHolderClass.set_other(str_other);
        dataHolderClass.setStr_transformer_capacity(str_transformer_capacity);
        dataHolderClass.setStr_transformer_code(str_transformer_code);
        dataHolderClass.setFeeder_name(str_feeder_code);

        Log.e("tab3", str_supply_type + str_supply_level + str_load_unit + str_metering_side + str_trans_ownership + str_trans_capacity +
                str_tariff_cat + str_bill_demand + str_scheme + str_connection + str_connect_load + str_other + str_transformer_capacity + str_transformer_code + feeder_code);

    }

    public void getValidate() {
        //str_connect_load = connect_load.getText().toString().trim();
        // int_connection=connection.getSelectedItemPosition();
        int_tariff_cat = tariff_spinner.getSelectedItemPosition();
       /* str_transformer_capacity = transformer_capacity.getText().toString().trim();
          str_transformer_code = transformer_code.getText().toString().trim();*/
        /*str_tariff_cat = tariff_spinner_subcat.getSelectedItem().toString();
         str_tariff_cat = tariff_spinner_subcat_a.getSelectedItem().toString();*/

        str_tariff_subcat = tariff_spinner_subcat.getSelectedItem().toString();
        str_tariff_subcat = tariff_spinner_subcat_a.getSelectedItem().toString();
        //str_tariff_subcat_a = tariff_spinner_subcat_a.getSelectedItem().toString();



//        int_tariff = tariff_spinner.getSelectedItemPosition();
        //int_division=division_spinner.getSelectedItemPosition();
       // int_feeder = feeder_spinner.getSelectedItemPosition();
        // Log.e("postion1", String.valueOf(int_connection));
        Log.e("postion2", String.valueOf(int_tariff_cat));
        Log.e("postion_t_load", String.valueOf(str_tariff_subcat));
        //Log.e("postion_t_load_a", String.valueOf(str_tariff_subcat_a));
        Log.e("postion_t_load_a", String.valueOf(str_tariff_subcat));
        DataHolderClass.getInstance().set_connect_load(str_tariff_subcat);
       /* if(str_connect_load.equals("")||(int_connection==0)||(int_tariff_cat==0))
        {*/

//        if(str_connect_load.equals("")||(int_tariff_cat==0))
        if ((int_tariff_cat == 0)) {
          /*  connect_load.setBackgroundColor(Color.YELLOW);
            //  connection.setBackgroundColor(Color.YELLOW);
            tariff_spinner.setBackgroundColor(Color.YELLOW);*/
           /* connect_load.setBackgroundColor(Color.YELLOW);
            feeder_spinner.setBackgroundColor(Color.YELLOW);*/
            tariff_spinner.setBackgroundColor(Color.YELLOW);
            // transformer_capacity.setBackgroundColor(Color.YELLOW);
            //transformer_code.setBackgroundColor(Color.YELLOW);

            Toast.makeText(getApplicationContext(), "Enter the mandatory fields LOAD PROFILE", Toast.LENGTH_SHORT).show();

        } else {


            getValue();
            Intent intent = new Intent(Exisiting_tab3.this, Existing_DataFinish.class);
            startActivity(intent);
            finish();
           /* // int int_connect_load=Integer.parseInt(str_connect_load);
            double double_connect_load = Double.parseDouble(str_connect_load);
            Log.e("double value", String.valueOf(double_connect_load))
            ;
            if ((double_connect_load < 0.5) || (double_connect_load > 5.0)) {
                Toast.makeText(getApplicationContext(), "Load must be between 0.5 and 5", Toast.LENGTH_SHORT).show();
            } else {
                System.out.println("calling validate all");
                validateAll();

            }*/
        }
    }
    ////////////////
    public void validateAll() {
        String str_division_spinner, str_subdivision, str_section, str_cycle, str_route,
                str_ownerships, str_cons_type, str_department, str_ed_exception, str_meter_status;

        //..... for tab1..........//
        str_division_spinner = DataHolderClass.getInstance().get_division();
        str_subdivision = DataHolderClass.getInstance().get_subdivion();
        str_section = DataHolderClass.getInstance().get_section();
        str_cycle = DataHolderClass.getInstance().get_cycle();
        str_route = DataHolderClass.getInstance().get_route();
        str_ownerships = DataHolderClass.getInstance().get_ownerships();
        str_cons_type = DataHolderClass.getInstance().get_cons_type();
        str_department = DataHolderClass.getInstance().get_department();
        str_ed_exception = DataHolderClass.getInstance().get_ed_exception();
        str_meter_status = DataHolderClass.getInstance().get_meter_status();


        //..........for tab2...........//
        String str_title, str_name, str_father_name, str_title_org, str_designation, str_type_org,
                str_name_org, str_name_org_corp;
        boolean boolean_value;
        String str_type;


        str_type = String.valueOf(DataHolderClass.getInstance().get_boolean_value());
        str_title = DataHolderClass.getInstance().get_title();
        str_name = DataHolderClass.getInstance().get_name();
        str_father_name = DataHolderClass.getInstance().get_father_name();
        str_title_org = DataHolderClass.getInstance().get_title_org();
        str_designation = DataHolderClass.getInstance().get_name_org();
        str_type_org = DataHolderClass.getInstance().get_designation();
        str_name_org = DataHolderClass.getInstance().get_type_org();
        str_name_org_corp = DataHolderClass.getInstance().get_name_org_corp();


        String str_house_no1, str_bulding_no1, str_street1, str_city1, str_district1, str_tehsil1, str_block1,
                str_gp1, str_village1, str_pin_no1, str_email1, str_mobile1, str_landline1, str_pan_no1;


        str_house_no1 = DataHolderClass.getInstance().get_house_no1();
        str_bulding_no1 = DataHolderClass.getInstance().get_bulding_no1();
        str_street1 = DataHolderClass.getInstance().get_street1();
        str_city1 = DataHolderClass.getInstance().get_city1();
        str_district1 = DataHolderClass.getInstance().get_district1();
        str_tehsil1 = DataHolderClass.getInstance().get_tehsil1();
        str_block1 = DataHolderClass.getInstance().get_block1();
        str_gp1 = DataHolderClass.getInstance().get_gp1();
        str_village1 = DataHolderClass.getInstance().get_village1();
        str_pin_no1 = DataHolderClass.getInstance().get_pin_no1();
        str_email1 = DataHolderClass.getInstance().get_email1();
        str_mobile1 = DataHolderClass.getInstance().get_mobile1();
        str_landline1 = DataHolderClass.getInstance().get_landline1();
        str_pan_no1 = DataHolderClass.getInstance().get_pan_no1();
/*
*
* //            Log.e("spinner",""+str_division_spinner);
//            Log.e("spinner",""+str_subdivision);
//            Log.e("spinner",""+str_section);
//            Log.e("spinner",""+str_cycle);
//            Log.e("spinner",""+str_route);
//            Log.e("spinner",""+str_ownerships);
//            Log.e("spinner",""+str_title);
//            Log.e("spinner",""+str_name);
//            Log.e("spinner",""+str_father_name);
// for individual
            if ((str_division_spinner==null) ||
                    str_subdivision==null ||
                    str_section==null ||
                    str_cycle==null ||
                    str_route==null ||
                    str_ownerships==null ||
                    str_cons_type==null||
                    str_ownerships==null ||
                    str_title==null ||
                    str_name==null ||
                    str_father_name==null ) {

//            Log.e("tab value",""+str_division_spinner+str_subdivision+str_section+str_title+str_name+
//                    str_father_name+"");
//            Log.e("str_mobile",""+str_mobile);
*
* */
        String str_house_no, str_bulding_no, str_street, str_city, str_district, str_tehsil, str_block,
                str_gp, str_village, str_pin_no, str_email,
                str_mobile, str_landline, str_pan_no, str_electrical_address, str_ed_percentage,str_adrs1,str_adrs2,str_adrs3;
        str_adrs1=DataHolderClass.getInstance().getStr_adres1();
        str_adrs2=DataHolderClass.getInstance().getStr_adres2();
        str_adrs3=DataHolderClass.getInstance().getStr_adres3();

        str_house_no = DataHolderClass.getInstance().get_house_no();
        str_bulding_no = DataHolderClass.getInstance().get_bulding_no();
        str_street = DataHolderClass.getInstance().get_street();
        str_city = DataHolderClass.getInstance().get_city();
        str_district = DataHolderClass.getInstance().get_district();
        str_tehsil = DataHolderClass.getInstance().get_tehsil();
        str_block = DataHolderClass.getInstance().get_block();
        str_gp = DataHolderClass.getInstance().get_gp();
        str_village = DataHolderClass.getInstance().get_village();
        str_pin_no = DataHolderClass.getInstance().get_pin_no();
        str_email = DataHolderClass.getInstance().get_email();
        str_mobile = DataHolderClass.getInstance().get_mobile();
        str_landline = DataHolderClass.getInstance().get_landline();
        str_pan_no = DataHolderClass.getInstance().get_pan_no();
        str_electrical_address = DataHolderClass.getInstance().getElectrical_address();
        str_ed_percentage = DataHolderClass.getInstance().getStr_ed_percentage();
        Log.e("boolean", "" + DataHolderClass.getInstance().get_boolean_value());

        if (DataHolderClass.getInstance().get_boolean_value()) {
            if ((str_division_spinner .equals(null)) ||
                    str_subdivision.equals(null)||
                    str_section .equals(null)||
//                    str_title == null ||
//                    str_name == null ||
                    str_father_name .equals(null)||
                    str_mobile .equals(null) ||
                    str_adrs1 .equals(null)||
                    str_adrs2 .equals(null)||
                    str_adrs3 .equals(null)||
//                    str_house_no == null ||
//                    str_bulding_no == null ||
//                    str_street == null ||
//                    str_city == null ||
//                    str_district == null ||
//                    str_tehsil == null ||
//                    str_block == null ||
//                    str_gp == null ||
//                    str_village == null ||
                    str_pin_no .equals(null)||
                    str_email .equals(null)||
                    str_landline .equals(null)||
                    str_electrical_address .equals(null) ||
                    str_ed_percentage .equals(null)||
                    str_pan_no .equals(null)) {
                Toast.makeText(getApplicationContext(), "still mandatory fields to be filled", Toast.LENGTH_SHORT).show();
            } else {

                // Toast.makeText(getApplicationContext(), "send data", Toast.LENGTH_SHORT).show();
//                Log.e("tab1 value",str_division_spinner+str_subdivision+str_section+str_cycle+str_route+
//                        str_ownerships+str_cons_type+str_department+str_ed_exception+str_meter_status);
//                Log.e("tab2 value",str_type+str_title+str_father_name+str_house_no+str_bulding_no+str_street+str_city+str_district+str_tehsil+str_block+
//                        str_gp+str_village+str_pin_no+str_email+
//                        str_mobile+str_landline+str_pan_no+str_house_no1+str_bulding_no1+str_street1+str_city1+str_district1+str_tehsil1+str_block1+
//                        str_gp1+str_village1+str_pin_no1+str_email1+
//                        str_mobile1+str_landline1+str_pan_no1);

                getValue();
                Intent intent = new Intent(Exisiting_tab3.this, Existing_CaptureImage.class);
                startActivity(intent);
                finish();

            }
        } else

        {// for organisational

//            Log.e("spinner", "" + str_division_spinner);
            if ((str_division_spinner == null) ||
                    str_subdivision == null ||
                    str_section == null ||
                    str_title_org == null ||
                    str_designation == null ||
                    str_type_org == null ||
                    str_name_org == null ||
                    str_name_org_corp == null ||
                    str_mobile == null ||
                    str_adrs1 == null ||
                    str_adrs2 == null ||
                    str_adrs3 == null ||
                    str_bulding_no == null ||
                    str_street == null ||
                    str_city == null ||
                    str_district == null ||
                    str_tehsil == null ||
                    str_block == null ||
                    str_gp == null ||
                    str_village == null ||
                    str_pin_no == null ||
                    str_email == null ||
                    str_landline == null ||
                    str_electrical_address == null ||
                    str_ed_percentage == null ||
                    str_pan_no == null)

            /*if((str_division_spinner==null) ||
                    str_subdivision==null ||
                    str_section==null ||
                    str_cycle==null ||
                    str_route==null ||
                    str_ownerships==null ||
                    str_cons_type==null||
                    str_ownerships==null ||
                    str_title_org==null ||
                    str_designation==null ||
                    str_type_org==null||
                    str_name_org==null||
                    str_name_org_corp==null
                    )*/ {
                Toast.makeText(getApplicationContext(), "still mandatory fields to be filled", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(getApplicationContext(), "send data", Toast.LENGTH_SHORT).show();
//                Log.e("tab1 value",str_division_spinner+str_subdivision+str_section+str_cycle+str_route+
//                        str_ownerships+str_cons_type+str_department+str_ed_exception+str_meter_status);
//                Log.e("tab2 value",str_type+str_title_org+str_designation+str_type_org+
//                        str_name_org+str_name_org_corp+str_house_no+str_bulding_no+str_street+str_city+str_district+str_tehsil+str_block+
//                        str_gp+str_village+str_pin_no+str_email+
//                        str_mobile+str_landline+str_pan_no+str_house_no1+str_bulding_no1+str_street1+str_city1+str_district1+str_tehsil1+str_block1+
//                        str_gp1+str_village1+str_pin_no1+str_email1+
//                        str_mobile1+str_landline1+str_pan_no1);
                getValue();
                Intent intent = new Intent(Exisiting_tab3.this, Existing_CaptureImage.class);
                startActivity(intent);
                finish();
            }
        }

    }

    //////////////////////////
//
//    public void validateAll() {
//        String str_division_spinner, str_subdivision, str_section, str_cycle, str_route,
//                str_ownerships, str_cons_type, str_department, str_ed_exception, str_ed_adress, str_meter_status;
//        System.out.println("in validate all" + DataHolderClass.getInstance().get_division());
//
//        //..... for tab1..........//
//        str_division_spinner = DataHolderClass.getInstance().get_division();
//        System.out.println("in validate all" + str_division_spinner);
//        str_subdivision = DataHolderClass.getInstance().get_subdivion();
//        str_section = DataHolderClass.getInstance().get_section();
//        str_cycle = DataHolderClass.getInstance().get_cycle();
//        str_route = DataHolderClass.getInstance().get_route();
//        str_ownerships = DataHolderClass.getInstance().get_ownerships();
//        str_cons_type = DataHolderClass.getInstance().get_cons_type();
//        str_department = DataHolderClass.getInstance().get_department();
//        str_ed_exception = DataHolderClass.getInstance().getStr_ed_percentage();
//        str_ed_adress = DataHolderClass.getInstance().getElectrical_address();
//        str_meter_status = DataHolderClass.getInstance().get_meter_status();
//
//
//        //..........for tab2...........//
//        String str_title, str_name, str_father_name, str_title_org, str_designation, str_type_org,
//                str_name_org, str_name_org_corp;
//        boolean boolean_value;
//        String str_type;
//
//
//        str_type = String.valueOf(DataHolderClass.getInstance().get_boolean_value());
//        str_title = DataHolderClass.getInstance().get_title();
//        str_name = DataHolderClass.getInstance().get_name();
//        str_father_name = DataHolderClass.getInstance().get_father_name();
//        str_title_org = DataHolderClass.getInstance().get_title_org();
//        str_designation = DataHolderClass.getInstance().get_name_org();
//        str_type_org = DataHolderClass.getInstance().get_designation();
////        str_name_org=DataHolderClass.getInstance().get_type_org() ;
//        str_name_org = DataHolderClass.getInstance().get_name();
//        str_name_org_corp = DataHolderClass.getInstance().get_name_org_corp();
//
//
//        String str_house_no1, str_bulding_no1, str_street1, str_city1, str_district1, str_tehsil1, str_block1,
//                str_gp1, str_village1, str_pin_no1, str_email1, str_mobile1, str_landline1, str_pan_no1;
//
//
//        str_house_no1 = DataHolderClass.getInstance().get_house_no1();
//        str_bulding_no1 = DataHolderClass.getInstance().get_bulding_no1();
//        str_street1 = DataHolderClass.getInstance().get_street1();
//        str_city1 = DataHolderClass.getInstance().get_city1();
//        str_district1 = DataHolderClass.getInstance().get_district1();
//        str_tehsil1 = DataHolderClass.getInstance().get_tehsil1();
//        str_block1 = DataHolderClass.getInstance().get_block1();
//        str_gp1 = DataHolderClass.getInstance().get_gp1();
//        str_village1 = DataHolderClass.getInstance().get_village1();
//        str_pin_no1 = DataHolderClass.getInstance().get_pin_no1();
//        str_email1 = DataHolderClass.getInstance().get_email1();
//        str_mobile1 = DataHolderClass.getInstance().get_mobile1();
//        str_landline1 = DataHolderClass.getInstance().get_landline1();
//        str_pan_no1 = DataHolderClass.getInstance().get_pan_no1();
///*
//*
//* //            Log.e("spinner",""+str_division_spinner);
////            Log.e("spinner",""+str_subdivision);
////            Log.e("spinner",""+str_section);
////            Log.e("spinner",""+str_cycle);
////            Log.e("spinner",""+str_route);
////            Log.e("spinner",""+str_ownerships);
////            Log.e("spinner",""+str_title);
////            Log.e("spinner",""+str_name);
////            Log.e("spinner",""+str_father_name);
//// for individual
//            if ((str_division_spinner==null) ||
//                    str_subdivision==null ||
//                    str_section==null ||
//                    str_cycle==null ||
//                    str_route==null ||
//                    str_ownerships==null ||
//                    str_cons_type==null||
//                    str_ownerships==null ||
//                    str_title==null ||
//                    str_name==null ||
//                    str_father_name==null ) {
//
////            Log.e("tab value",""+str_division_spinner+str_subdivision+str_section+str_title+str_name+
////                    str_father_name+"");
////            Log.e("str_mobile",""+str_mobile);
//*
//* */
//        String str_house_no, str_bulding_no, str_street, str_city, str_district, str_tehsil, str_block,
//                str_gp, str_village, str_pin_no, str_email,
//                str_mobile, str_landline, str_pan_no;
//        str_house_no = DataHolderClass.getInstance().get_house_no();
//        str_bulding_no = DataHolderClass.getInstance().get_bulding_no();
//        str_street = DataHolderClass.getInstance().get_street();
//        str_city = DataHolderClass.getInstance().get_city();
//        str_district = DataHolderClass.getInstance().get_district();
//        str_tehsil = DataHolderClass.getInstance().get_tehsil();
//        str_block = DataHolderClass.getInstance().get_block();
//        str_gp = DataHolderClass.getInstance().get_gp();
//        str_village = DataHolderClass.getInstance().get_village();
//        str_pin_no = DataHolderClass.getInstance().get_pin_no();
//        str_email = DataHolderClass.getInstance().get_email();
//        str_mobile = DataHolderClass.getInstance().get_mobile();
//        str_landline = DataHolderClass.getInstance().get_landline();
//        str_pan_no = DataHolderClass.getInstance().get_pan_no();
//
//        Log.e("boolean", "" + DataHolderClass.getInstance().get_boolean_value());
//
//        if (DataHolderClass.getInstance().get_boolean_value())
//
//        {
//
//            System.out.println("in validate all4" + str_division_spinner.equals(null));
//            System.out.println("in validate all5" + str_subdivision.equals(null));
//            System.out.println("in validate all6" + str_section.equals(null));
//
//            System.out.println("in validate all7" + str_name_org);
//            System.out.println("in validate all8" + str_father_name);
//            System.out.println("in validate all9" + str_mobile);
//
////            System.out.println("in validate all"+str_title.equals(null));
//            System.out.println("in validate all1" + str_name_org.equals(null));
//            System.out.println("in validate all2" + str_father_name.equals(null));
//            System.out.println("in validate all3" + str_mobile.equals(null));
////            System.out.println("in validate all"+str_house_no.equals(null));
////            System.out.println("in validate all"+str_bulding_no.equals(null));
////            System.out.println("in validate all"+str_street.equals(null));
////            System.out.println("in validate all"+str_city.equals(null));
////            System.out.println("in validate all"+str_district.equals(null));
////            System.out.println("in validate all"+str_tehsil.equals(null));
////            System.out.println("in validate all"+str_block.equals(null));
////            System.out.println("in validate all"+str_gp.equals(null));
////            System.out.println("in validate all"+str_village.equals(null));
//            System.out.println("in validate all" + str_pin_no.equals(null));
////            System.out.println("in validate all"+str_email.equals(null));
////            System.out.println("in validate all"+str_landline.equals(null));
////            System.out.println("in validate all"+str_pan_no.equals(null));
//
//            if ((str_division_spinner.equals(null)) ||
//                    str_subdivision.equals(null) ||
//                    str_section.equals(null) ||
//                    str_name.equals(null) ||
//                    str_father_name.equals(null) ||
//                    str_mobile.equals(null) ||
//                    str_ed_exception.equals(null) ||
//                    str_ed_adress.equals(null) ||
//                    str_pin_no.equals(null)
//                    ) {
///*
//            Log.e("str_subdivision",str_subdivision);
//            Log.e("str_section",str_section);
//            Log.e("str_title",str_title);
//            Log.e("str_name",str_name);
//            Log.e("str_mobile",str_mobile);
//            Log.e("str_house_no",str_house_no);
//            Log.e("str_bulding_no",str_bulding_no);
//            Log.e("str_street",str_street);
//            Log.e("str_city",str_city);
//            Log.e("str_district",str_district);
//            Log.e("str_tehsil",str_tehsil);
//            Log.e("str_block",str_block);
//            Log.e("str_gp",str_gp);
//            Log.e("str_village",str_village);
//            Log.e("str_email",str_email);
//            Log.e("str_landline",str_landline);
//            Log.e("str_pan_no",str_pan_no);*/
//
//                Toast.makeText(getApplicationContext(), "still mandatory fields to be filled", Toast.LENGTH_SHORT).show();
//            } else {
//
//                // Toast.makeText(getApplicationContext(), "send data", Toast.LENGTH_SHORT).show();
////                Log.e("tab1 value",str_division_spinner+str_subdivision+str_section+str_cycle+str_route+
////                        str_ownerships+str_cons_type+str_department+str_ed_exception+str_meter_status);
////                Log.e("tab2 value",str_type+str_title+str_father_name+str_house_no+str_bulding_no+str_street+str_city+str_district+str_tehsil+str_block+
////                        str_gp+str_village+str_pin_no+str_email+
////                        str_mobile+str_landline+str_pan_no+str_house_no1+str_bulding_no1+str_street1+str_city1+str_district1+str_tehsil1+str_block1+
////                        str_gp1+str_village1+str_pin_no1+str_email1+
////                        str_mobile1+str_landline1+str_pan_no1);
//
//                getValue();
//                Intent intent = new Intent(Exisiting_tab3.this, Existing_CaptureImage.class);
//                startActivity(intent);
//                finish();
//
//            }
//        } else
//
//        {// for organisational
//
////            Log.e("spinner", "" + str_division_spinner);
//            if ((str_division_spinner == null) ||
//                    str_subdivision == null ||
//                    str_section == null ||
//                    str_title_org == null ||
//                    str_designation == null ||
//                    str_type_org == null ||
//                    str_name_org == null ||
//                    str_name_org_corp == null ||
//                    str_mobile == null ||
//                    str_bulding_no == null ||
//                    str_street == null ||
//                    str_city == null ||
//                    str_district == null ||
//                    str_tehsil == null ||
//                    str_block == null ||
//                    str_gp == null ||
//                    str_village == null ||
//                    str_pin_no == null ||
//                    str_email == null ||
//                    str_landline == null ||
//                    str_pan_no == null)
//
//            /*if((str_division_spinner==null) ||
//                    str_subdivision==null ||
//                    str_section==null ||
//                    str_cycle==null ||
//                    str_route==null ||
//                    str_ownerships==null ||
//                    str_cons_type==null||
//                    str_ownerships==null ||
//                    str_title_org==null ||
//                    str_designation==null ||
//                    str_type_org==null||
//                    str_name_org==null||
//                    str_name_org_corp==null
//                    )*/ {
//                Toast.makeText(getApplicationContext(), "still mandatory fields to be filled", Toast.LENGTH_SHORT).show();
//            } else {
//                //Toast.makeText(getApplicationContext(), "send data", Toast.LENGTH_SHORT).show();
////                Log.e("tab1 value",str_division_spinner+str_subdivision+str_section+str_cycle+str_route+
////                        str_ownerships+str_cons_type+str_department+str_ed_exception+str_meter_status);
////                Log.e("tab2 value",str_type+str_title_org+str_designation+str_type_org+
////                        str_name_org+str_name_org_corp+str_house_no+str_bulding_no+str_street+str_city+str_district+str_tehsil+str_block+
////                        str_gp+str_village+str_pin_no+str_email+
////                        str_mobile+str_landline+str_pan_no+str_house_no1+str_bulding_no1+str_street1+str_city1+str_district1+str_tehsil1+str_block1+
////                        str_gp1+str_village1+str_pin_no1+str_email1+
////                        str_mobile1+str_landline1+str_pan_no1);
//                getValue();
//                Intent intent = new Intent(Exisiting_tab3.this, Existing_CaptureImage.class);
//                startActivity(intent);
//                finish();
//            }
//        }
//
//    }

    public class SendToServer extends AsyncTask<String, String,String>
    {    String network_interrupt=null;
        ProgressDialog pd;
        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd=new ProgressDialog(Exisiting_tab3.this);
            pd.setMessage("record sending");
            pd.setCancelable(false);
            pd.show();
        }
        @Override
        protected String doInBackground(String... params) {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("tag","update_consumer"));

            nameValuePairs.add(new BasicNameValuePair("ticket",DataHolderClass.getInstance().get_new_meter_ticket_no()));
            nameValuePairs.add(new BasicNameValuePair("division", DataHolderClass.getInstance().get_division()));
            nameValuePairs.add(new BasicNameValuePair("subdivision", DataHolderClass.getInstance().get_subdivion()));
            nameValuePairs.add(new BasicNameValuePair("section", DataHolderClass.getInstance().get_section()));

            nameValuePairs.add(new BasicNameValuePair("title", DataHolderClass.getInstance().get_title()));
            nameValuePairs.add(new BasicNameValuePair("first_name", DataHolderClass.getInstance().get_name()));
            nameValuePairs.add(new BasicNameValuePair("middle_name", DataHolderClass.getInstance().getStr_middle_name()));
            nameValuePairs.add(new BasicNameValuePair("last_name", DataHolderClass.getInstance().getStr_last_name()));
            nameValuePairs.add(new BasicNameValuePair("father_name", DataHolderClass.getInstance().get_father_name()));

            nameValuePairs.add(new BasicNameValuePair("building_no", DataHolderClass.getInstance().get_bulding_no()));
            nameValuePairs.add(new BasicNameValuePair("house_flatno", DataHolderClass.getInstance().getHouse_flatno()));
            nameValuePairs.add(new BasicNameValuePair("housename", DataHolderClass.getInstance().getHousename()));
            nameValuePairs.add(new BasicNameValuePair("khatano", DataHolderClass.getInstance().getKhata_no()));
            nameValuePairs.add(new BasicNameValuePair("ward_no", DataHolderClass.getInstance().getWard_no()));
            nameValuePairs.add(new BasicNameValuePair("street", DataHolderClass.getInstance().get_street()));
            nameValuePairs.add(new BasicNameValuePair("block", DataHolderClass.getInstance().get_block()));
            nameValuePairs.add(new BasicNameValuePair("panchayat", DataHolderClass.getInstance().get_gp()));
            nameValuePairs.add(new BasicNameValuePair("address", DataHolderClass.getInstance().getStr_adres1()));
            nameValuePairs.add(new BasicNameValuePair("pin_no", DataHolderClass.getInstance().get_pin_no()));
            nameValuePairs.add(new BasicNameValuePair("landmark", DataHolderClass.getInstance().getLandmark()));

            nameValuePairs.add(new BasicNameValuePair("village", DataHolderClass.getInstance().get_city()));
            nameValuePairs.add(new BasicNameValuePair("district", DataHolderClass.getInstance().get_district()));
            nameValuePairs.add(new BasicNameValuePair("telephone", DataHolderClass.getInstance().get_landline()));
            nameValuePairs.add(new BasicNameValuePair("mobile",DataHolderClass.getInstance().get_mobile()));
            nameValuePairs.add(new BasicNameValuePair("email",DataHolderClass.getInstance().get_email()));
            nameValuePairs.add(new BasicNameValuePair("category",DataHolderClass.getInstance().get_tariff_cat()));
            nameValuePairs.add(new BasicNameValuePair("get_connect_load",DataHolderClass.getInstance().get_connect_load()));

            Log.e("namevaluepair",""+nameValuePairs);
            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(sessionManager.GET_URL());
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response = httpclient.execute(httppost,responseHandler);
            }
            catch(Exception e)
            {
                network_interrupt=e.getMessage().toString();
                Log.e("log_tag", "Error in http connection "+e.toString());
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pd.dismiss();
            pd.hide();
            try {
                if(network_interrupt==null) {

                    response = response.trim();
                    Log.e("response",response);
                    if (response.equalsIgnoreCase("1")) {
                        Toast.makeText(getApplicationContext(), "record send successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Exisiting_tab3.this, Options.class));
                        finish();
                    } else {
                        ShowAlert();

                    }



                }else{
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
                    Toast.makeText(getApplicationContext(),"Record not send due to internet interruption", Toast.LENGTH_SHORT).show();
                    finish();


                }
            } catch (Exception e) {
                ShowAlert();
            }


        }


        public void ShowAlert() {
            Exisiting_tab3.this.runOnUiThread(new Runnable() {
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Exisiting_tab3.this);
                    builder.setTitle("Do you want to...");
                    builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            new SendToServer().execute();
                        }
                    });
                    builder.setNegativeButton("Save Record", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(),"Record Saved",Toast.LENGTH_SHORT).show();
                           /* sqLiteMasterTableAdapter.openToRead();
                            sqLiteMasterTableAdapter.openToWrite();
                            sqLiteMasterTableAdapter.mastersubdivision_insert_new
                                    (DataHolderClass.getInstance().get_division(),
                                            DataHolderClass.getInstance().get_subdivion(),
                                            DataHolderClass.getInstance().get_section(),
                                            "",
                                            DataHolderClass.getInstance().get_name(),
                                            DataHolderClass.getInstance().get_father_name(),
                                            DataHolderClass.getInstance().get_name_org_corp(),
                                            DataHolderClass.getInstance().get_type_org(),
                                            DataHolderClass.getInstance().get_name_org(),
                                            DataHolderClass.getInstance().get_block(),//signat_name
                                            DataHolderClass.getInstance().get_bulding_no(),
                                            DataHolderClass.getInstance().get_city(),
                                            DataHolderClass.getInstance().get_district(),
                                            DataHolderClass.getInstance().get_gp(),
                                            DataHolderClass.getInstance().get_house_no(),//plot
                                            DataHolderClass.getInstance().get_street(),
                                            DataHolderClass.getInstance().get_tehsil(),
                                            DataHolderClass.getInstance().get_village(),
                                            DataHolderClass.getInstance().get_block1(),
                                            DataHolderClass.getInstance().get_city1(),
                                            DataHolderClass.getInstance().get_district1(),
                                            DataHolderClass.getInstance().get_gp1(),
                                            DataHolderClass.getInstance().get_house_no1(),
                                            DataHolderClass.getInstance().get_street1(),
                                            DataHolderClass.getInstance().get_tehsil1(),
                                            DataHolderClass.getInstance().get_village1(),
                                            DataHolderClass.getInstance().get_bulding_no1(),
                                            DataHolderClass.getInstance().get_mobile(),
                                            DataHolderClass.getInstance().get_email1(),
                                            DataHolderClass.getInstance().get_landline1(),
                                            DataHolderClass.getInstance().get_designation(),
                                            DataHolderClass.getInstance().get_connect_load(),
                                            DataHolderClass.getInstance().get_tariff_cat(),
                                            "0.0",
                                            "0.0",
                                            "0.0",
                                            "0.0",
                                            image, meterimageName+","+DataHolderClass.getInstance().get_person_available(),
                                            DataHolderClass.getInstance().get_pan_no(),
                                            DataHolderClass.getInstance().get_pin_no(),
                                            DataHolderClass.getInstance().get_pin_no1());*/

                            Toast.makeText(getApplicationContext(),"record saved",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }
    }


}
