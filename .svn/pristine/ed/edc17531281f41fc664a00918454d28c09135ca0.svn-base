package feedback.mpnsc.exisitingconsumer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.HashSet;

import feedback.mpnsc.DataHolderClass;
import feedback.mpnsc.R;
import feedback.mpnsc.SQLiteAdapter;

/**
 * Created by swatiG on 02-07-2015.
 */
public class Exisiting_tab4 extends Activity {


    EditText connect_load;
    EditText transformer_capacity, transformer_code, feeder_name;
    Spinner tariff_cat;
    TextView load_unit;
    String str_supply_type, str_supply_level, str_load_unit, str_metering_side, str_trans_ownership, str_trans_capacity,
            str_tariff_cat, str_bill_demand, str_scheme, str_connection, str_connect_load, str_other,
            str_transformer_capacity, str_transformer_code, str_feeder_name;
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

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3);
        callView();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getValidate();


            }
        });
        TVTicketnum.setText(DataHolderClass.getInstance().get_new_meter_ticket_no());
        connect_load.setText(DataHolderExisting.getInstance().getLoadreq());

        str_tariff_code = DataHolderExisting.getInstance().getLoadcategory();

        tariffArraycode = new ArrayList<String>();
        tariffArrayname = new ArrayList<String>();

        //new TARIFFTABLEMANAGER(Exisiting_tab4.this).execute();


        tariff_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                //  Log.e("from","spinner1");
                if (count > 1) {

                    str_tariff_code = tariffArraycode.get(position).toString();
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

        //new FEEDERTABLEMANAGER(Exisiting_tab4.this).execute();



    }






    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack();
    }

    public void ShowAlertonBack() {
        Exisiting_tab4.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Exisiting_tab4.this);
                builder.setTitle("Are you sure to go back:");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Exisiting_tab4.this, ExistingConsumer_Search.class));
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

        //supply_type=(Spinner)findViewById(R.id.spinner11);
        // supply_level=(Spinner)findViewById(R.id.spinner12);
        load_unit = (TextView) findViewById(R.id.spinner13);
        /*metering_side=(Spinner)findViewById(R.id.spinner14);
        trans_ownership=(Spinner)findViewById(R.id.spinner15);
        trans_capacity=(Spinner)findViewById(R.id.spinner16);*/
        //tariff_cat=(Spinner)findViewById(R.id.spinner17);
        tariff_spinner = (Spinner) findViewById(R.id.spinner17);

        // ....... edit text........//
        connect_load = (EditText) findViewById(R.id.spinner10);
        // other=(EditText)findViewById(R.id.editText);

        submit = (Button) findViewById(R.id.submit);
    }


    public void getValue() {

        str_load_unit = load_unit.getText().toString();

        //str_tariff_cat = tariff_cat.getSelectedItem().toString();
        str_tariff = tariff_spinner.getSelectedItem().toString();
        str_feeder = feeder_spinner.getSelectedItem().toString();

        str_connect_load = connect_load.getText().toString().trim();
        // str_other = other.getText().toString().trim();
        str_transformer_capacity = transformer_capacity.getText().toString().trim();
        str_transformer_code = transformer_code.getText().toString().trim();
        //str_feeder_name = feeder_name.getText().toString().trim();

        System.out.println("str_feeder_code " + str_feeder_code);

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
        dataHolderClass.set_connect_load(str_connect_load);
        dataHolderClass.set_other(str_other);

        Log.e("tab3", str_supply_type + str_supply_level + str_load_unit + str_metering_side + str_trans_ownership + str_trans_capacity +
                str_tariff_cat + str_bill_demand + str_scheme + str_connection + str_connect_load + str_other + str_transformer_capacity + str_transformer_code + feeder_code);

    }

    public void getValidate() {
        str_connect_load = connect_load.getText().toString().trim();
        // int_connection=connection.getSelectedItemPosition();
        int_tariff_cat = tariff_spinner.getSelectedItemPosition();
        str_transformer_capacity = transformer_capacity.getText().toString().trim();
        str_transformer_code = transformer_code.getText().toString().trim();

//        int_tariff = tariff_spinner.getSelectedItemPosition();
        //int_division=division_spinner.getSelectedItemPosition();
        int_feeder = feeder_spinner.getSelectedItemPosition();
        // Log.e("postion1", String.valueOf(int_connection));
        Log.e("postion2", String.valueOf(int_tariff_cat));
       /* if(str_connect_load.equals("")||(int_connection==0)||(int_tariff_cat==0))
        {*/

//        if(str_connect_load.equals("")||(int_tariff_cat==0))
        if (str_connect_load.equals("") || (int_tariff_cat == 0) || str_transformer_code.equals("") || (int_feeder == 0)) {
          /*  connect_load.setBackgroundColor(Color.YELLOW);
            //  connection.setBackgroundColor(Color.YELLOW);
            tariff_spinner.setBackgroundColor(Color.YELLOW);*/
            connect_load.setBackgroundColor(Color.YELLOW);
            feeder_spinner.setBackgroundColor(Color.YELLOW);
            tariff_spinner.setBackgroundColor(Color.YELLOW);
            // transformer_capacity.setBackgroundColor(Color.YELLOW);
            transformer_code.setBackgroundColor(Color.YELLOW);

            Toast.makeText(getApplicationContext(), "Enter the mandatory fields LOAD PROFILE", Toast.LENGTH_SHORT).show();

        } else {

            // int int_connect_load=Integer.parseInt(str_connect_load);
            double double_connect_load = Double.parseDouble(str_connect_load);
            Log.e("double value", String.valueOf(double_connect_load))
            ;
            if ((double_connect_load < 0.5) || (double_connect_load > 5.0)) {
                Toast.makeText(getApplicationContext(), "Load must be between 0.5 and 5", Toast.LENGTH_SHORT).show();
            } else {
                System.out.println("calling validate all");
                validateAll();

            }
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
        Log.e("boolean", "" + DataHolderClass.getInstance().get_boolean_value());

        if (DataHolderClass.getInstance().get_boolean_value()) {
            if ((str_division_spinner .equals(null)) ||
                    str_subdivision.equals(null)||
                    str_section .equals(null)||
//                    str_title == null ||
//                    str_name == null ||
                    str_father_name .equals(null)||
                    str_mobile .equals(null) ||

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
                Intent intent = new Intent(Exisiting_tab4.this, Existing_CaptureImage.class);
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
                Intent intent = new Intent(Exisiting_tab4.this, Existing_CaptureImage.class);
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


}
