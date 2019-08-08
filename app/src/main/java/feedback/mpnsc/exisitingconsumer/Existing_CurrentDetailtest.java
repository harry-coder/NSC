package feedback.mpnsc.exisitingconsumer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import feedback.mpnsc.CustomClasses.SingleTicketInfoPojo;
import feedback.mpnsc.DataHolderClass;
import feedback.mpnsc.Feasibility;
import feedback.mpnsc.Feasibility_photo;
import feedback.mpnsc.Options;
import feedback.mpnsc.R;
import feedback.mpnsc.SQLiteAdapter;

/**
 * Created by swatiG on 02-07-2015.
 */
public class Existing_CurrentDetailtest extends Activity {
    CheckBox same_add;
    EditText house_no, street, city, block, gp, pin_no, email,
            mobile, landline, plot_no, building_name, holding_khata_no, ward_no, address, landmark, name, middle, last, father_name;
    String str_house_no, str_street, str_city, str_district, str_block,
            str_gp, str_pin_no, str_email,
            str_mobile, str_landline, str_plot_no, str_building_name, str_holding_khata_no, str_ward_no, str_address, str_landmark,
            str_name, str_middle, str_last, str_father_name, str_title;
    Spinner title, district;
    int title_position;
    Spannable Sp_fName, Sp_block, Sp_address, Sp_pincode, Sp_landmark, Sp_village, Sp_mobile;
    TextView tv_fname, tv_block, tv_address, tv_pincode, tv_landmark, tv_village, tv_mobile;
    int result;
    Button submit;
    TextView TVTicketnum;

    ImageView im_back;
    public static String ticket;

    String div_code, sec_code;
    private String response;
    private String user_father, user_mobile_no, user_landmark, user_address, user_name;

    EditText et_middleName, et_lastName, et_address2, et_address3;
    String str_middleName, str_lastName, str_address2, str_address3;


    String lastName, middleName, address2, address3;

    Spinner sp_MD;

    Spinner sp_appliedCategory;

    Cursor project_cursor;
    SQLiteAdapter sqLiteAdapter_name;
    ArrayList <String> project_name_list, project_id_list, tariff_load, tariff_load_unit;

    ArrayAdapter <String>  project_adapter;

    private SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.current_add );



        ticket = getIntent ( ).getExtras().getString ( "ticket" );



        callView ( );
        if(ticket!=null){
            TVTicketnum.setText ( ticket );

        }


        new SearchTicketNumber ( ).execute ( );


        tv_fname = findViewById ( R.id.tv_father );
        tv_block = findViewById ( R.id.tv_block );
        tv_address = findViewById ( R.id.tv_address );
        tv_pincode = findViewById ( R.id.tv_pin );
        tv_landmark = findViewById ( R.id.tv_landmark );
        tv_village = findViewById ( R.id.tv_city );
        tv_mobile = findViewById ( R.id.tv_mobile );


        et_lastName = findViewById ( R.id.et_lastName );
        et_middleName = findViewById ( R.id.et_middleName );
        et_address2 = findViewById ( R.id.et_address2 );
        et_address3 = findViewById ( R.id.et_address3 );

        Sp_fName = new SpannableString ( tv_fname.getText ( ).toString ( ) );
        Sp_block = new SpannableString ( tv_block.getText ( ).toString ( ) );
        Sp_address = new SpannableString ( tv_address.getText ( ).toString ( ) );
        Sp_pincode = new SpannableString ( tv_pincode.getText ( ).toString ( ) );

        sp_appliedCategory=findViewById ( R.id.sp_appliedCategory );

        // Sp_landmark = new SpannableString ( tv_landmark.getText ( ).toString ( ) );

        Sp_village = new SpannableString ( tv_village.getText ( ).toString ( ) );
        Sp_mobile = new SpannableString ( tv_mobile.getText ( ).toString ( ) );
        Sp_fName.setSpan ( new ForegroundColorSpan ( Color.RED ), 23, 24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        Sp_block.setSpan ( new ForegroundColorSpan ( Color.RED ), 5, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        Sp_address.setSpan ( new ForegroundColorSpan ( Color.RED ), 9, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        Sp_pincode.setSpan ( new ForegroundColorSpan ( Color.RED ), 11, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );

        //Sp_landmark.setSpan ( new ForegroundColorSpan ( Color.RED ), 8, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );

        Sp_village.setSpan ( new ForegroundColorSpan ( Color.RED ), 17, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        Sp_mobile.setSpan ( new ForegroundColorSpan ( Color.RED ), 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        tv_fname.setText ( Sp_fName );
        tv_block.setText ( Sp_block );
        tv_address.setText ( Sp_address );
        tv_pincode.setText ( Sp_pincode );


        sp_MD = findViewById ( R.id.sp_MD );
        // tv_landmark.setText ( Sp_landmark );

        tv_village.setText ( Sp_village );
        tv_mobile.setText ( Sp_mobile );

        project_name_list = new ArrayList <String> ( );
        project_id_list = new ArrayList <String> ( );
        tariff_load = new ArrayList <String> ( );
        tariff_load_unit = new ArrayList <String> ( );


       /* father_name.addTextChangedListener ( new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                father_name.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );

            }

            @Override
            public void afterTextChanged(Editable editable) {
                father_name.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
            }
        } );

        city.addTextChangedListener ( new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                city.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );

            }

            @Override
            public void afterTextChanged(Editable editable) {
                city.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
            }
        } );


        pin_no.addTextChangedListener ( new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pin_no.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );

            }

            @Override
            public void afterTextChanged(Editable editable) {
                pin_no.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
            }
        } );

        block.addTextChangedListener ( new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                block.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );

            }

            @Override
            public void afterTextChanged(Editable editable) {
                block.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
            }
        } );
        address.addTextChangedListener ( new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                address.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );

            }

            @Override
            public void afterTextChanged(Editable editable) {
                address.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
            }
        } );
        mobile.addTextChangedListener ( new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mobile.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mobile.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
            }
        } );*/
        /*landmark.addTextChangedListener ( new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                landmark.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );

            }

            @Override
            public void afterTextChanged(Editable editable) {
                landmark.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
            }
        } );*/


        /*final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        email.addTextChangedListener ( new TextWatcher ( ) {
            public void afterTextChanged(Editable s) {

                String emailStr = email.getEditableText ( ).toString ( ).trim ( );
                if (emailStr.matches ( emailPattern ) && s.length ( ) > 0) {
                    // email.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
                } else {
                    email.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        } );*/


        submit = findViewById ( R.id.btn_submit );
        submit.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                title_position = title.getSelectedItemPosition ( );
                str_title = title.getSelectedItem ( ).toString ( );
                str_name = name.getText ( ).toString ( ).trim ( );
//                str_middle = middle.getText ( ).toString ( ).trim ( );
                //              str_last = last.getText ( ).toString ( ).trim ( );
                str_father_name = father_name.getText ( ).toString ( ).trim ( );

                str_plot_no = plot_no.getText ( ).toString ( ).trim ( );
                str_house_no = house_no.getText ( ).toString ( ).trim ( );
                str_building_name = building_name.getText ( ).toString ( ).trim ( );
                str_holding_khata_no = holding_khata_no.getText ( ).toString ( ).trim ( );
                str_ward_no = ward_no.getText ( ).toString ( ).trim ( );
                str_street = street.getText ( ).toString ( ).trim ( );
                str_block = block.getText ( ).toString ( ).trim ( );
                str_gp = gp.getText ( ).toString ( ).trim ( );
                str_address = address.getText ( ).toString ( ).trim ( );
                str_pin_no = pin_no.getText ( ).toString ( ).trim ( );

                //str_landmark = landmark.getText ( ).toString ( ).trim ( );

                str_city = city.getText ( ).toString ( ).trim ( );

                str_district = district.getSelectedItem ( ).toString ( );
                str_email = email.getText ( ).toString ( ).trim ( );
                str_mobile = mobile.getText ( ).toString ( ).trim ( );
                str_landline = landline.getText ( ).toString ( ).trim ( );


                str_address2 = et_address2.getText ( ).toString ( ).trim ( );
                str_address3 = et_address3.getText ( ).toString ( ).trim ( );
                str_middleName = et_middleName.getText ( ).toString ( ).trim ( );
                str_lastName = et_lastName.getText ( ).toString ( ).trim ( );


                //str_mobile=mobile.getText().toString().trim();
/*
                int result = Integer.parseInt(str_mobile.substring(0,1));

                if ((result < 7) || (result > 9)) {
                    Toast.makeText(Existing_CurrentDetailtest.this, "Mobile number must be start form 7,8,9 Number", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), "Mobile number must be start form 7,8,9 Number", Toast.LENGTH_SHORT).show();
                }*/


                char mobileChar = str_mobile.charAt ( 1 );


                if (str_name.equals ( "" )) {

                    name.setError ( "Field Required" );
                    name.requestFocus ( );

                    //       name.setBackgroundColor ( Color.parseColor ( "#412b55" ) );

                }
                //else if (str_middle.equals("")){middle.setBackgroundColor(Color.YELLOW);}
                //else if (str_last.equals("")){last.setBackgroundColor(Color.YELLOW); }
                else if (str_father_name.equals ( "" )) {
                    father_name.setError ( "Field Required" );
                    father_name.requestFocus ( );
                    //father_name.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
                }

               /* else if(str_plot_no.equals("")){ plot_no.setBackgroundColor(Color.YELLOW);}
                else if (str_house_no.equals("")){house_no.setBackgroundColor(Color.YELLOW); }
                else if (str_building_name.equals("")){building_name.setBackgroundColor(Color.YELLOW);}
                else if (str_holding_khata_no.equals("")){holding_khata_no.setBackgroundColor(Color.YELLOW); }
                else if (str_ward_no.equals("")){ward_no.setBackgroundColor(Color.YELLOW);}
                else if (str_street.equals("")){street.setBackgroundColor(Color.YELLOW); }*/
                /*else if (str_block.equals ( "" )) {
                    block.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
                }*/

                else if(!TextUtils.isEmpty ( str_email )) {
                    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    String emailStr = email.getEditableText ( ).toString ( ).trim ( );
                    if (!emailStr.matches ( emailPattern )) {

                        email.setError ( "Invalid Email" );
                        email.requestFocus ();
                        // email.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
                    }
                }

                else if (str_pin_no.equals ( "" )) {

                    pin_no.setError ( "Field Required" );
                    pin_no.requestFocus ( );

                    //  pin_no.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
                }
                // else if (str_gp.equals("")){gp.setBackgroundColor(Color.YELLOW);}
                else if (str_address.equals ( "" )) {

                    address.setError ( "Field Required" );
                    address.requestFocus ( );

                    //address.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
                }
                /*else if (str_landmark.equals ( "" )) {
                    landmark.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
                }
                */
                else if (str_city.equals ( "" )) {
                    city.requestFocus ();
                    city.setError ( "Field Required" );
                   // city.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
                }
                // else if (district.getSelectedItem().toString().trim().equals("Select District")){district.setBackgroundColor(Color.YELLOW); }
                //else if (str_email.equals("")){email.setBackgroundColor(Color.YELLOW);}
                else if (str_mobile.equals ( "" ) || str_mobile.length ( ) != 11) {

                    mobile.setError ( "Field Required" );
                    mobile.requestFocus ( );

                    // mobile.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
                }
               /* else if((Integer.parseInt(str_mobile.substring(0,1)) < 7) || (Integer.parseInt(str_mobile.substring(0,1)) > 9)){
                    Toast.makeText(getApplicationContext(), "Mobile number must be start form 7,8,9 Number", Toast.LENGTH_SHORT).show();
                }*/

                else if (mobileChar != '7' && mobileChar != '8' && mobileChar != '9') {
                    Toast.makeText ( getApplicationContext ( ), "Mobile number must be start form 7,8,9 Number", Toast.LENGTH_SHORT ).show ( );
                }
                //else if (str_landline.equals("")){landline.setBackgroundColor(Color.YELLOW);}
                else {

                    System.out.println ("Inside send to server" );
                    new SendToServer ( ).execute ( );

                   /* Intent i = new Intent ( Existing_CurrentDetailtest.this, Existing_DataFinish.class );
                    //i.putExtra("user_detail", user_detail);
                    i.putExtra ( "div_code", div_code );
                    i.putExtra ( "Sub_div_code", Sub_div_code );
                    i.putExtra ( "sec_code", sec_code );
                    startActivity ( i );*/
                }

            }
        } );

        im_back = findViewById ( R.id.im_back );
        im_back.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                ShowAlertonBack ( );
            }
        } );


        //assigning the retrieved values of tickets to views

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack ( );
    }

    public void ShowAlertonBack() {
        Existing_CurrentDetailtest.this.runOnUiThread ( new Runnable ( ) {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder ( Existing_CurrentDetailtest.this, R.style.MyAlertDialogStyle );
                builder.setTitle ( "Are you sure to go back:" );
                builder.setPositiveButton ( "YES", new DialogInterface.OnClickListener ( ) {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity ( new Intent ( Existing_CurrentDetailtest.this, Existing_tab1.class ) );
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
        TVTicketnum = findViewById ( R.id.textViewTicket );
        title = findViewById ( R.id.spinner9 );
        name = findViewById ( R.id.editText1 );
        middle = findViewById ( R.id.editmiddle );
        last = findViewById ( R.id.editlast );
        father_name = findViewById ( R.id.editText2 );

        plot_no = findViewById ( R.id.et_plotno );
        house_no = findViewById ( R.id.et_houseno );
        building_name = findViewById ( R.id.et_buildng_name );
        holding_khata_no = findViewById ( R.id.et_holding_khata_no );
        ward_no = findViewById ( R.id.et_ward_no );
        street = findViewById ( R.id.et_street );
        block = findViewById ( R.id.et_block );
        gp = findViewById ( R.id.et_gp );
        address = findViewById ( R.id.et_address );
        pin_no = findViewById ( R.id.et_pin );
        landmark = findViewById ( R.id.et_landmark );
        city = findViewById ( R.id.et_city );
        //district=(EditText)findViewById(R.id.et_district);
        district = findViewById ( R.id.et_district );
        email = findViewById ( R.id.et_email_id );
        mobile = findViewById ( R.id.et_mobile );
        landline = findViewById ( R.id.et_landline );
        same_add = findViewById ( R.id.checkBox );
    }

    @Override
    protected void onPause() {
        super.onPause ( );
       /* Log.e ( "from cons", "onPause" );
        title_position = title.getSelectedItemPosition ( );
        str_title = title.getSelectedItem ( ).toString ( );
        str_name = name.getText ( ).toString ( ).trim ( );
        str_middle = middle.getText ( ).toString ( ).trim ( );
        str_last = last.getText ( ).toString ( ).trim ( );
        str_father_name = father_name.getText ( ).toString ( ).trim ( );

        str_plot_no = plot_no.getText ( ).toString ( ).trim ( );
        str_house_no = house_no.getText ( ).toString ( ).trim ( );
        str_building_name = building_name.getText ( ).toString ( ).trim ( );
        str_holding_khata_no = holding_khata_no.getText ( ).toString ( ).trim ( );
        str_ward_no = ward_no.getText ( ).toString ( ).trim ( );
        str_street = street.getText ( ).toString ( ).trim ( );
        str_block = block.getText ( ).toString ( ).trim ( );
        str_gp = gp.getText ( ).toString ( ).trim ( );
        str_address = address.getText ( ).toString ( ).trim ( );
        str_pin_no = pin_no.getText ( ).toString ( ).trim ( );
        str_landmark = landmark.getText ( ).toString ( ).trim ( );
        str_city = city.getText ( ).toString ( ).trim ( );
        str_district = district.getSelectedItem ( ).toString ( );
        str_email = email.getText ( ).toString ( ).trim ( );
        str_mobile = mobile.getText ( ).toString ( ).trim ( );
        str_landline = landline.getText ( ).toString ( ).trim ( );
        // Log.e("str_mobile",""+str_mobile);
        DataHolderClass.getInstance ( ).set_title ( str_title );
        DataHolderClass.getInstance ( ).set_name ( str_name );
        DataHolderClass.getInstance ( ).setStr_middle_name ( str_middle );
        DataHolderClass.getInstance ( ).setStr_last_name ( str_last );
        DataHolderClass.getInstance ( ).set_father_name ( str_father_name );

        DataHolderClass.getInstance ( ).set_bulding_no ( str_plot_no );
        DataHolderClass.getInstance ( ).setHouse_flatno ( str_house_no );
        DataHolderClass.getInstance ( ).setHousename ( str_building_name );
        DataHolderClass.getInstance ( ).setKhata_no ( str_holding_khata_no );
        DataHolderClass.getInstance ( ).setWard_no ( str_ward_no );
        DataHolderClass.getInstance ( ).set_street ( str_street );
        DataHolderClass.getInstance ( ).set_block ( str_block );
        DataHolderClass.getInstance ( ).set_gp ( str_gp );
        DataHolderClass.getInstance ( ).setStr_adres1 ( str_address );
        DataHolderClass.getInstance ( ).set_pin_no ( str_pin_no );
        DataHolderClass.getInstance ( ).setLandmark ( str_landmark );
        DataHolderClass.getInstance ( ).set_city ( str_city );
        DataHolderClass.getInstance ( ).set_district ( str_district );
        DataHolderClass.getInstance ( ).set_email ( str_email );
        DataHolderClass.getInstance ( ).set_mobile ( str_mobile );
        DataHolderClass.getInstance ( ).set_landline ( str_landline );*/
    }

    public class SearchTicketNumber extends AsyncTask <String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute ( );
            pd = new ProgressDialog ( Existing_CurrentDetailtest.this, R.style.MyAlertDialogStyle );
            pd.setMessage ( "searching..." );
            pd.setCancelable ( false );
            pd.show ( );
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );

            nameValuePairs.add ( new BasicNameValuePair ( "table", ticket ) );
            Log.e ( "namepairvalue", "" + nameValuePairs );
            try {
                HttpClient httpclient = new DefaultHttpClient ( );


                //  HttpPost httppost = new HttpPost(sessionManager.GET_URL());
                HttpPost httppost = new HttpPost ( "http://dlenhanceuat.phed.com.ng/dlenhanceapi/nscapi/get_existing" );

               // http://dlenhanceuat.phed.com.ng/dlenhanceapi
                httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
                ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
                response = httpclient.execute ( httppost, responseHandler );
                parseTicketResponse ( response );
            } catch (Exception e) {
                Log.e ( "log_tag", "Error in http connection " + e.toString ( ) );
                //  network_interrupt = e.getMessage ( );
            }
            return response;
        }

        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute ( result );
            pd.dismiss ( );
            pd.hide ( );
            String div_code, Sub_div_code, sec_code, NewFirstName, NewMiddleName, NewLastName, FatherName, PLOT, Building, Street,
                    District, Block, GP, Village, EmaiL_ID, LANDLINE_NUMBER,
                    LoadRequired, CustGroup, Mobile_number, PIN, HouseName, KhataNo, Ward, Address1, Address2, str_update_status;


            father_name.setText ( user_father );
            mobile.setText ( user_mobile_no );
            landmark.setText ( user_landmark );
            address.setText ( user_address );
            name.setText ( user_name );


            if(!address2.equalsIgnoreCase ( "null" )){
                et_address2.setText ( address2 );

            }
            if(!address3.equalsIgnoreCase ( "null" )) {
                et_address3.setText ( address3 );
            }
            if(!middleName.equalsIgnoreCase ( "null" )) {
               et_middleName.setText ( middleName );
            }
            if(!lastName.equalsIgnoreCase ( "null" )) {
                et_lastName.setText ( lastName );
            }

        }
    }

    public void parseTicketResponse(String response) {
        try {
            if (response != null) {
                response = response.trim ( );

                JSONObject userInfo = new JSONObject ( response );
                System.out.println ( "This is the response " + userInfo );


                JSONArray userInfoArray = userInfo.getJSONArray ( "Table" );
                JSONObject userInfoObject = userInfoArray.getJSONObject ( 0 );




                user_father = userInfoObject.getString ( "FATHERNAME" );
                user_mobile_no = userInfoObject.getString ( "MOBILE_NUMBER" );
                //  user_landmark = userInfoObject.getString ( "ADDRESS2" );
                user_address = userInfoObject.getString ( "ADDRESS1" );
                user_name = userInfoObject.getString ( "NEWFIRSTNAME" );

                div_code = userInfoObject.getString ( "DIV_CODE" );
                sec_code = userInfoObject.getString ( "SEC_CODE" );


                address2 = userInfoObject.getString ( "ADDRESS2" );
                address3 = userInfoObject.getString ( "ADDRESS3" );

                middleName = userInfoObject.getString ( "NEWMIDDLENAME" );
                lastName = userInfoObject.getString ( "LAST_NAME" );





            }
        } catch (JSONException e) {
            e.printStackTrace ( );
        }


    }


    class SendToServer extends AsyncTask <String, String, String> {
        String network_interrupt = null;
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute ( );
            pd = new ProgressDialog ( Existing_CurrentDetailtest.this, R.style.MyAlertDialogStyle );
            pd.setMessage ( "record sending" );
            pd.setCancelable ( false );
            pd.show ( );




        }

        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
                // nameValuePairs.add(new BasicNameValuePair("tag",  "update_consumer"));


                nameValuePairs.add ( new BasicNameValuePair ( "TicketNumber", ticket ) );


                nameValuePairs.add ( new BasicNameValuePair ( "Div_code", div_code ) );
                nameValuePairs.add ( new BasicNameValuePair ( "Sub_div_code", "" ) );
                nameValuePairs.add ( new BasicNameValuePair ( "sec_code", sec_code ) );


                nameValuePairs.add ( new BasicNameValuePair ( "ConsTitle", str_title ) );
                nameValuePairs.add ( new BasicNameValuePair ( "NewFirstName", str_name ) );
                nameValuePairs.add ( new BasicNameValuePair ( "NewMiddleName", str_middleName ) );
                nameValuePairs.add ( new BasicNameValuePair ( "NewLastName", str_lastName ) );
                nameValuePairs.add ( new BasicNameValuePair ( "FatherName", str_father_name ) );

                nameValuePairs.add ( new BasicNameValuePair ( "Building", str_building_name ) );
                nameValuePairs.add ( new BasicNameValuePair ( "PLOT", str_plot_no ) );
                nameValuePairs.add ( new BasicNameValuePair ( "HouseName", str_house_no ) );
                nameValuePairs.add ( new BasicNameValuePair ( "KhataNo", str_holding_khata_no ) );
                nameValuePairs.add ( new BasicNameValuePair ( "Ward", str_ward_no ) );
                nameValuePairs.add ( new BasicNameValuePair ( "Street", str_street ) );
                nameValuePairs.add ( new BasicNameValuePair ( "Block", str_block ) );
                nameValuePairs.add ( new BasicNameValuePair ( "GP", "" ) );
                nameValuePairs.add ( new BasicNameValuePair ( "Address1", str_address ) );
                nameValuePairs.add ( new BasicNameValuePair ( "PIN", str_pin_no ) );
                nameValuePairs.add ( new BasicNameValuePair ( "Address2", str_address2 ) );

                nameValuePairs.add ( new BasicNameValuePair ( "Address3", str_address3 ) );

                nameValuePairs.add ( new BasicNameValuePair ( "Village", str_city ) );
                nameValuePairs.add ( new BasicNameValuePair ( "District", str_district ) );
                nameValuePairs.add ( new BasicNameValuePair ( "LANDLINE_NUMBER", str_landline ) );
                nameValuePairs.add ( new BasicNameValuePair ( "Mobile_number", str_mobile ) );
                nameValuePairs.add ( new BasicNameValuePair ( "EmaiL_ID", str_email ) );
                nameValuePairs.add ( new BasicNameValuePair ( "category", DataHolderClass.getInstance ( ).get_tariff_cat ( ) ) );
                nameValuePairs.add ( new BasicNameValuePair ( "get_connect_load", DataHolderClass.getInstance ( ).get_connect_load ( ) ) );
                nameValuePairs.add ( new BasicNameValuePair ( "CustGroup", "" ) );
                nameValuePairs.add ( new BasicNameValuePair ( "LoadRequired", "" ) );

                Log.e ( "values", "" + nameValuePairs );
                HttpClient httpclient = new DefaultHttpClient ( );

                //HttpPost httppost = new HttpPost(sessionManager.GET_URL());
                HttpPost httppost = new HttpPost ( "http://dlenhanceuat.phed.com.ng/dlenhanceapi/nscapi/update_consumer" );


                // HttpPost httppost = new HttpPost("http://sbm.fieldpm.com/sb/handset_reading");
                httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
                ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
                response = httpclient.execute ( httppost, responseHandler );
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
            try {

                if (network_interrupt == null) {
                    //   Log.e("network_interrupt",network_interrupt);
                    try {
                        if (response.trim ( ).equals ( "1" )) {
                            //        Log.e("RESPONSE1",response);
                            Toast.makeText ( getApplicationContext ( ), "send successfully", Toast.LENGTH_SHORT ).show ( );
                            Intent intent = new Intent ( Existing_CurrentDetailtest.this, Feasibility_photo.class );
                            startActivity ( intent );
                            finish ( );
//
                        } else {
                            //    Log.e("Response2", "->" + response);
                            ShowAlert ( );
                            //    Log.e("RESPONSE8",""+response.length());

                        }
                    } catch (Exception e) {
                        //   Log.e("Response3", "->" + e);
                    }
                } else {
                    ShowAlert ( );

                }
            } catch (Exception e) {
            }
        }

        public void ShowAlert() {
            Existing_CurrentDetailtest.this.runOnUiThread ( new Runnable ( ) {
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder ( Existing_CurrentDetailtest.this, R.style.MyAlertDialogStyle );
                    builder.setTitle ( "Do you want to..." );
                    builder.setPositiveButton ( "Try Again", new DialogInterface.OnClickListener ( ) {
                        public void onClick(DialogInterface dialog, int id) {
                            new SendToServer ( ).execute ( );
                        }
                    } );

                    AlertDialog alert = builder.create ( );
                    alert.show ( );
                }
            } );
        }
    }
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
            pd = new ProgressDialog ( Existing_CurrentDetailtest.this, R.style.MyAlertDialogStyle );
            pd.setMessage ( "Please wait..." );
            pd.setCancelable ( false );
            pd.show ( );
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            sqLiteAdapter_name = new SQLiteAdapter ( Existing_CurrentDetailtest.this );
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
                project_adapter = new ArrayAdapter<String> ( Existing_CurrentDetailtest.this, android.R.layout.simple_spinner_item, project_id_list );
                project_adapter.setDropDownViewResource ( R.layout.spinner_item );
               sp_appliedCategory.setAdapter ( project_adapter );

            } catch (Exception e) {
                e.printStackTrace ( );
            }
        }
    }
    public Cursor tariffname()
    {
        Cursor  cursor = sqLiteDatabase.rawQuery("Select * from TBL_TARIFF_CATEGORY", null);
        return cursor;
    }

}