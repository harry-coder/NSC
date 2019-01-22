package feedback.mpnsc.exisitingconsumer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import feedback.mpnsc.Feasibility_photo;
import feedback.mpnsc.Options;
import feedback.mpnsc.R;

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
    private String ticket;

    String div_code, sec_code;
    private String response;
    private String user_father, user_mobile_no, user_landmark, user_address, user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.current_add );

        ticket = getIntent ( ).getStringExtra ( "ticket" );


        callView ( );


        new SearchTicketNumber ( ).execute ( );



       /* TVTicketnum.setText ( DataHolderClass.getInstance ( ).get_new_meter_ticket_no ( ) );
        Log.e ( "house_no", DataHolderExisting.getInstance ( ).getPlot ( ) );

        if (DataHolderExisting.getInstance ( ).getName ( ).equals ( "null" )) {
            name.setText ( "" );
        } else
            name.setText ( DataHolderExisting.getInstance ( ).getName ( ) );

        if (DataHolderExisting.getInstance ( ).getMiddle_name ( ).equals ( "null" )) {
            middle.setText ( "" );
        } else
            middle.setText ( DataHolderExisting.getInstance ( ).getMiddle_name ( ) );

        if (DataHolderExisting.getInstance ( ).getLast_name ( ).equals ( "null" )) {
            last.setText ( "" );
        } else
            last.setText ( DataHolderExisting.getInstance ( ).getLast_name ( ) );

        if (DataHolderExisting.getInstance ( ).getFather_husband_name ( ).equals ( "null" )) {
            father_name.setText ( "" );
        } else
            father_name.setText ( DataHolderExisting.getInstance ( ).getFather_husband_name ( ) );

        // plot_no=(EditText)findViewById(R.id.et_plotno);
        if (DataHolderExisting.getInstance ( ).getPlot ( ).equals ( "null" )) {
            house_no.setText ( "" );
        } else
            house_no.setText ( DataHolderExisting.getInstance ( ).getPlot ( ) );

        //house_no=(EditText)findViewById(R.id.et_houseno);
        if (DataHolderExisting.getInstance ( ).getBuilding ( ).equals ( "null" )) {
            plot_no.setText ( "" );
        } else
            plot_no.setText ( DataHolderExisting.getInstance ( ).getBuilding ( ) );

        //building_name=(EditText)findViewById(R.id.et_buildng_name);
        if (DataHolderExisting.getInstance ( ).getHousename ( ).equals ( "null" )) {
            building_name.setText ( "" );
        } else
            building_name.setText ( DataHolderExisting.getInstance ( ).getHousename ( ) );

        //holding_khata_no=(EditText)findViewById(R.id.et_holding_khata_no);
        if (DataHolderExisting.getInstance ( ).getKhatano ( ).equals ( "null" )) {
            holding_khata_no.setText ( "" );
        } else
            holding_khata_no.setText ( DataHolderExisting.getInstance ( ).getKhatano ( ) );

        // ward_no=(EditText)findViewById(R.id.et_ward_no);
        if (DataHolderExisting.getInstance ( ).getWard ( ).equals ( "null" )) {
            ward_no.setText ( "" );
        } else
            ward_no.setText ( DataHolderExisting.getInstance ( ).getWard ( ) );

        //street=(EditText)findViewById(R.id.et_street);
        if (DataHolderExisting.getInstance ( ).getStreet ( ).equals ( "null" )) {
            street.setText ( "" );
        } else
            street.setText ( DataHolderExisting.getInstance ( ).getStreet ( ) );

        //block=(EditText)findViewById(R.id.et_block);
        if (DataHolderExisting.getInstance ( ).getBlock ( ).equals ( "null" )) {
            block.setText ( "" );
        } else
            block.setText ( DataHolderExisting.getInstance ( ).getBlock ( ) );

        //gp=(EditText)findViewById(R.id.et_gp);
        if (DataHolderExisting.getInstance ( ).getGP ( ).equals ( "null" )) {
            gp.setText ( "" );
        } else
            gp.setText ( DataHolderExisting.getInstance ( ).getGP ( ) );

        //address=(EditText)findViewById(R.id.et_address);
        if (DataHolderExisting.getInstance ( ).getAddress1 ( ).equals ( "null" )) {
            address.setText ( "" );
        } else
            address.setText ( DataHolderExisting.getInstance ( ).getAddress1 ( ) );

        //pin_no=(EditText)findViewById(R.id.et_pin);
        if (DataHolderExisting.getInstance ( ).getpin ( ).equals ( "null" )) {
            pin_no.setText ( "" );
        } else
            pin_no.setText ( DataHolderExisting.getInstance ( ).getpin ( ) );

        //landmark=(EditText)findViewById(R.id.et_landmark);
        if (DataHolderExisting.getInstance ( ).getAddress2 ( ).equals ( "null" )) {
            landmark.setText ( "" );
        } else
            landmark.setText ( DataHolderExisting.getInstance ( ).getAddress2 ( ) );

        //city=(EditText)findViewById(R.id.et_city);
        if (DataHolderExisting.getInstance ( ).getVillage ( ).equals ( "null" )) {
            city.setText ( "" );
        } else
            city.setText ( DataHolderExisting.getInstance ( ).getVillage ( ) );

        //district=(EditText)findViewById(R.id.et_district);
       *//* if(DataHolderExisting.getInstance().getDistrict().equals("null")){
            district.setText("");
        }else
            district.setText(DataHolderExisting.getInstance().getDistrict());*//*

        //email=(EditText)findViewById(R.id.et_email_id);
        if (DataHolderExisting.getInstance ( ).getEmailid ( ).equals ( "null" )) {
            email.setText ( "" );
        } else
            email.setText ( DataHolderExisting.getInstance ( ).getEmailid ( ) );

        //mobile=(EditText)findViewById(R.id.et_mobile);
        if (DataHolderExisting.getInstance ( ).getMobile_no ( ).equals ( "null" )) {
            mobile.setText ( "" );
        } else
            mobile.setText ( DataHolderExisting.getInstance ( ).getMobile_no ( ) );

        //landline=(EditText)findViewById(R.id.et_landline);
        if (DataHolderExisting.getInstance ( ).getLand_line_no ( ).equals ( "null" )) {
            landline.setText ( "" );
        } else
            landline.setText ( DataHolderExisting.getInstance ( ).getLand_line_no ( ) );
*/
        tv_fname = findViewById ( R.id.tv_father );
        tv_block = findViewById ( R.id.tv_block );
        tv_address = findViewById ( R.id.tv_address );
        tv_pincode = findViewById ( R.id.tv_pin );
        tv_landmark = findViewById ( R.id.tv_landmark );
        tv_village = findViewById ( R.id.tv_city );
        tv_mobile = findViewById ( R.id.tv_mobile );

        Sp_fName = new SpannableString ( tv_fname.getText ( ).toString ( ) );
        Sp_block = new SpannableString ( tv_block.getText ( ).toString ( ) );
        Sp_address = new SpannableString ( tv_address.getText ( ).toString ( ) );
        Sp_pincode = new SpannableString ( tv_pincode.getText ( ).toString ( ) );
        Sp_landmark = new SpannableString ( tv_landmark.getText ( ).toString ( ) );
        Sp_village = new SpannableString ( tv_village.getText ( ).toString ( ) );
        Sp_mobile = new SpannableString ( tv_mobile.getText ( ).toString ( ) );
        Sp_fName.setSpan ( new ForegroundColorSpan ( Color.RED ), 23, 24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        Sp_block.setSpan ( new ForegroundColorSpan ( Color.RED ), 5, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        Sp_address.setSpan ( new ForegroundColorSpan ( Color.RED ), 7, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        Sp_pincode.setSpan ( new ForegroundColorSpan ( Color.RED ), 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        Sp_landmark.setSpan ( new ForegroundColorSpan ( Color.RED ), 8, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        Sp_village.setSpan ( new ForegroundColorSpan ( Color.RED ), 17, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        Sp_mobile.setSpan ( new ForegroundColorSpan ( Color.RED ), 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        tv_fname.setText ( Sp_fName );
        tv_block.setText ( Sp_block );
        tv_address.setText ( Sp_address );
        tv_pincode.setText ( Sp_pincode );
        tv_landmark.setText ( Sp_landmark );
        tv_village.setText ( Sp_village );
        tv_mobile.setText ( Sp_mobile );

        father_name.addTextChangedListener ( new TextWatcher ( ) {
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
        } );
        landmark.addTextChangedListener ( new TextWatcher ( ) {
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
        } );


        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        email.addTextChangedListener ( new TextWatcher ( ) {
            public void afterTextChanged(Editable s) {

                String emailStr = email.getEditableText ( ).toString ( ).trim ( );
                if (emailStr.matches ( emailPattern ) && s.length ( ) > 0) {
                    email.setBackgroundColor ( getResources ( ).getColor ( R.color.themecolor ) );
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
        } );


        submit = findViewById ( R.id.btn_submit );
        submit.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
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


                //str_mobile=mobile.getText().toString().trim();
/*
                int result = Integer.parseInt(str_mobile.substring(0,1));

                if ((result < 7) || (result > 9)) {
                    Toast.makeText(Existing_CurrentDetailtest.this, "Mobile number must be start form 7,8,9 Number", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), "Mobile number must be start form 7,8,9 Number", Toast.LENGTH_SHORT).show();
                }*/


                char mobileChar = str_mobile.charAt ( 1 );


                if (str_name.equals ( "" )) {
                    name.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
                }
                //else if (str_middle.equals("")){middle.setBackgroundColor(Color.YELLOW);}
                //else if (str_last.equals("")){last.setBackgroundColor(Color.YELLOW); }
                else if (str_father_name.equals ( "" )) {
                    father_name.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
                }

               /* else if(str_plot_no.equals("")){ plot_no.setBackgroundColor(Color.YELLOW);}
                else if (str_house_no.equals("")){house_no.setBackgroundColor(Color.YELLOW); }
                else if (str_building_name.equals("")){building_name.setBackgroundColor(Color.YELLOW);}
                else if (str_holding_khata_no.equals("")){holding_khata_no.setBackgroundColor(Color.YELLOW); }
                else if (str_ward_no.equals("")){ward_no.setBackgroundColor(Color.YELLOW);}
                else if (str_street.equals("")){street.setBackgroundColor(Color.YELLOW); }*/
                else if (str_block.equals ( "" )) {
                    block.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
                } else if (str_pin_no.equals ( "" )) {
                    pin_no.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
                }
                // else if (str_gp.equals("")){gp.setBackgroundColor(Color.YELLOW);}
                else if (str_address.equals ( "" )) {
                    address.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
                } else if (str_landmark.equals ( "" )) {
                    landmark.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
                } else if (str_city.equals ( "" )) {
                    city.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
                }
                // else if (district.getSelectedItem().toString().trim().equals("Select District")){district.setBackgroundColor(Color.YELLOW); }
                //else if (str_email.equals("")){email.setBackgroundColor(Color.YELLOW);}
                else if (str_mobile.equals ( "" ) || str_mobile.length ( ) != 11) {
                    mobile.setBackgroundColor ( Color.parseColor ( "#412b55" ) );
                }
               /* else if((Integer.parseInt(str_mobile.substring(0,1)) < 7) || (Integer.parseInt(str_mobile.substring(0,1)) > 9)){
                    Toast.makeText(getApplicationContext(), "Mobile number must be start form 7,8,9 Number", Toast.LENGTH_SHORT).show();
                }*/

                else if (mobileChar != '7' && mobileChar != '8' && mobileChar != '9') {
                    Toast.makeText ( getApplicationContext ( ), "Mobile number must be start form 7,8,9 Number", Toast.LENGTH_SHORT ).show ( );
                }
                //else if (str_landline.equals("")){landline.setBackgroundColor(Color.YELLOW);}
                else {

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
        Log.e ( "from cons", "onPause" );
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
        DataHolderClass.getInstance ( ).set_landline ( str_landline );
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
                HttpPost httppost = new HttpPost ( "http://wcrm.fedco.co.in/phedapi/nscapi/get_existing" );


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
                user_landmark = userInfoObject.getString ( "ADDRESS2" );
                user_address = userInfoObject.getString ( "ADDRESS1" );
                user_name = userInfoObject.getString ( "NEWFIRSTNAME" );

                div_code = userInfoObject.getString ( "DIV_CODE" );
                sec_code = userInfoObject.getString ( "SEC_CODE" );


             /*   for (int i = 0; i < userInfoArray.length ( ); i++) {
                    JSONObject userInfoObject = userInfoArray.getJSONObject ( i );


                    SingleTicketInfoPojo singleTicketInfoPojo = new SingleTicketInfoPojo ( );


                    singleTicketInfoPojo.setDivCode ( userInfoObject.getString ( "DIV_CODE" ) );
                    singleTicketInfoPojo.setName ( userInfoObject.getString ( "NEWFIRSTNAME" ) );
                    singleTicketInfoPojo.setSecCode ( userInfoObject.getString ( "SEC_CODE" ) );


                    div_code = userInfoObject.getString ( "DIV_CODE" );


                    DataHolderExisting.getInstance ( ).setDivision_code ( div_code );

                    Sub_div_code = c.getString ( "SUB_DIV_CODE" );
                    user_detail.put ( "Sub_div_code", Sub_div_code );
                    DataHolderExisting.getInstance ( ).setSub_division_code ( Sub_div_code );

                    sec_code = c.getString ( "SEC_CODE" );
                    user_detail.put ( "sec_code", sec_code );
                    DataHolderExisting.getInstance ( ).setSection_code ( sec_code );

                    NewFirstName = c.getString ( "NEWFIRSTNAME" );
                    user_detail.put ( "NewFirstName", NewFirstName );
                    DataHolderExisting.getInstance ( ).setName ( NewFirstName );

                    NewMiddleName = c.getString ( "NEWMIDDLENAME" );
                    user_detail.put ( "NewMiddleName", NewMiddleName );
                    DataHolderExisting.getInstance ( ).setMiddle_name ( NewMiddleName );

                    NewLastName = c.getString ( "LAST_NAME" );
                    user_detail.put ( "NewLastName", NewLastName );
                    DataHolderExisting.getInstance ( ).setLast_name ( NewLastName );

                    FatherName = c.getString ( "FATHERNAME" );
                    user_detail.put ( "FatherName", FatherName );
                    DataHolderExisting.getInstance ( ).setFather_husband_name ( FatherName );

                    Building = c.getString ( "BUILDING" );
                    user_detail.put ( "Building", Building );
                    DataHolderExisting.getInstance ( ).setBuilding ( Building );

                    PLOT = c.getString ( "PLOT" );
                    user_detail.put ( "PLOT", PLOT );
                    DataHolderExisting.getInstance ( ).setPlot ( PLOT );

                    HouseName = c.getString ( "HOUSENAME" );
                    user_detail.put ( "HouseName", HouseName );
                    DataHolderExisting.getInstance ( ).setHousename ( HouseName );

                    KhataNo = c.getString ( "KHATANO" );
                    user_detail.put ( "KhataNo", KhataNo );
                    DataHolderExisting.getInstance ( ).setKhatano ( KhataNo );

                    Ward = c.getString ( "WARD" );
                    user_detail.put ( "Ward", Ward );
                    DataHolderExisting.getInstance ( ).setWard ( Ward );

                    Street = c.getString ( "STREET" );
                    user_detail.put ( "Street", Street );
                    DataHolderExisting.getInstance ( ).setStreet ( Street );

                    Block = c.getString ( "BLOCK" );
                    user_detail.put ( "Block", Block );
                    DataHolderExisting.getInstance ( ).setBlock ( Block );

                    GP = c.getString ( "GP" );
                    user_detail.put ( "GP", GP );
                    DataHolderExisting.getInstance ( ).setGP ( GP );

                    Address1 = c.getString ( "ADDRESS1" );
                    user_detail.put ( "Address1", Address1 );
                    DataHolderExisting.getInstance ( ).setAddress1 ( Address1 );

                    PIN = c.getString ( "PIN" );
                    user_detail.put ( "PIN", PIN );
                    DataHolderExisting.getInstance ( ).setpin ( PIN );


                    Address2 = c.getString ( "ADDRESS2" );
                    user_detail.put ( "Address2", Address2 );
                    DataHolderExisting.getInstance ( ).setAddress2 ( Address2 );

                    Village = c.getString ( "VILLAGE" );
                    user_detail.put ( "Village", Village );
                    DataHolderExisting.getInstance ( ).setVillage ( Village );

                    District = c.getString ( "DISTRICT" );
                    user_detail.put ( "District", District );
                    DataHolderExisting.getInstance ( ).setDistrict ( District );

                    LANDLINE_NUMBER = c.getString ( "LANDLINE_NUMBER" );
                    user_detail.put ( "LANDLINE_NUMBER", LANDLINE_NUMBER );
                    DataHolderExisting.getInstance ( ).setLand_line_no ( LANDLINE_NUMBER );

                    Mobile_number = c.getString ( "MOBILE_NUMBER" );
                    user_detail.put ( "Mobile_number", Mobile_number );
                    DataHolderExisting.getInstance ( ).setMobile_no ( Mobile_number );


                    EmaiL_ID = c.getString ( "EMAIL_ID" );
                    user_detail.put ( "EmaiL_ID", EmaiL_ID );
                    DataHolderExisting.getInstance ( ).setEmailid ( EmaiL_ID );


                    LoadRequired = c.getString ( "LOADREQUIRED" );
                    user_detail.put ( "LoadRequired", LoadRequired );
                    System.out.println ( "ExistingLoadreq" + LoadRequired );
                    DataHolderExisting.getInstance ( ).setLoadreq ( LoadRequired );

                    CustGroup = c.getString ( "CUSTGROUP" );
                    user_detail.put ( "CustGroup", CustGroup );
                    DataHolderExisting.getInstance ( ).setLoadcategory ( CustGroup );

                    str_update_status = c.getString ( "UPDATE_STATUS" ).trim ( );


                    Log.e ( "hasmap", "" + user_detail );
                    Log.e ( "sec_code", sec_code );

                    if (str_update_status.equals ( "1" )) {
                        ShowAlertGetConsUpdation ( );
                    } else {


                        // Intent intent = new Intent(ExistingConsumer_Search.this, Existing_tab1.class);
                        Intent intent = new Intent ( ExistingConsumer_Search.this, Existing_CurrentDetailtest.class );

                        intent.putExtra ( "user_detail", user_detail );
                        intent.putExtra ( "div_code", div_code );
                        intent.putExtra ( "Sub_div_code", Sub_div_code );
                        intent.putExtra ( "sec_code", sec_code );


                        //        System.out.println ("This is sub div "+Sub_div_code );
                        DataHolderClass.getInstance ( ).set_ticket_no ( str_ticket_no );
                        startActivity ( intent );
                        finish ( );

                    }
                }

            }catch(Exception e)
            {
                Log.e ( " final exception", "" + e.getMessage ( ) );
                ShowAlert ( );
            }
*/
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
                nameValuePairs.add ( new BasicNameValuePair ( "NewMiddleName", str_middle ) );
                nameValuePairs.add ( new BasicNameValuePair ( "NewLastName", str_last ) );
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
                nameValuePairs.add ( new BasicNameValuePair ( "Address2", str_landmark ) );

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
                HttpPost httppost = new HttpPost ( "http://wcrm.fedco.co.in/phedapi/nscapi/update_consumer" );


                // HttpPost httppost = new HttpPost("http://sbm.fieldpm.com/sb/handset_reading");
                httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
                ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
                response = httpclient.execute ( httppost, responseHandler );
                Log.e ( "Response7", response );
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
                   /* sqLiteMasterTableAdapter.openToRead();
                    sqLiteMasterTableAdapter.openToWrite();
                    sqLiteMasterTableAdapter.insert_update_consumer
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
                                    DataHolderClass.getInstance().get_pin_no1(),
                                    DataHolderClass.getInstance().get_ticket_no());
                    Toast.makeText(getApplicationContext(), "Record Saved due to internet interrupt", Toast.LENGTH_SHORT).show();
                    sqLiteMasterTableAdapter.close();
                    finish();*/
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
                   /* builder.setNegativeButton(" ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(),"Record Saved",Toast.LENGTH_SHORT).show();
                            sqLiteMasterTableAdapter.openToRead();
                            sqLiteMasterTableAdapter.openToWrite();
                            sqLiteMasterTableAdapter.insert_update_consumer
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
                                            DataHolderClass.getInstance().get_pin_no1(),
                                            DataHolderClass.getInstance().get_ticket_no());

                            Toast.makeText(getApplicationContext(),"record saved",Toast.LENGTH_LONG).show();

                            finish();
                        }
                    });*/
                    AlertDialog alert = builder.create ( );
                    alert.show ( );
                }
            } );
        }
    }
}