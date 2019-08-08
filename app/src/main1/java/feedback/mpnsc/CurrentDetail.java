package feedback.mpnsc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import feedback.mpnsc.exisitingconsumer.ExistingConsumer_Search;

/**
 * Created by sushily on 23-05-2015.
 */
public class CurrentDetail extends Activity {
    CheckBox same_add;
    EditText house_no,street,city,block,gp,pin_no,email,
            mobile,landline,plot_no,building_name,holding_khata_no,ward_no,address,landmark;
    String str_house_no,str_street,str_city,str_district,str_block,
            str_gp,str_pin_no,str_email,
            str_mobile,str_landline,str_plot_no,str_building_name,str_holding_khata_no,str_ward_no,str_address,str_landmark,str_title;
    Spinner title,district;
    int title_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_add);
        callView();
    }

   /* @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack();
    }*/
    public void ShowAlertonBack(){
        CurrentDetail.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(CurrentDetail.this);
                builder.setTitle("Are you sure to go back:");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(CurrentDetail.this, ExistingConsumer_Search.class));
                        finish();
                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {

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
    public void callView()
    {

        //house_no=(EditText)findViewById(R.id.et_plotno);
        title =(Spinner)findViewById(R.id.spinner9);
        plot_no=(EditText)findViewById(R.id.et_plotno);
        house_no=(EditText)findViewById(R.id.et_houseno);
        building_name=(EditText)findViewById(R.id.et_buildng_name);
        holding_khata_no=(EditText)findViewById(R.id.et_holding_khata_no);
        ward_no=(EditText)findViewById(R.id.et_ward_no);
        street=(EditText)findViewById(R.id.et_street);
        block=(EditText)findViewById(R.id.et_block);
        gp=(EditText)findViewById(R.id.et_gp);
        address=(EditText)findViewById(R.id.et_address);
        pin_no=(EditText)findViewById(R.id.et_pin);
        landmark=(EditText)findViewById(R.id.et_landmark);
        city=(EditText)findViewById(R.id.et_city);
        //district=(EditText)findViewById(R.id.et_district);
        district=(Spinner)findViewById(R.id.et_district);
        email=(EditText)findViewById(R.id.et_email_id);
        mobile=(EditText)findViewById(R.id.et_mobile);
        landline=(EditText)findViewById(R.id.et_landline);

       //same_add=(CheckBox)findViewById(R.id.checkBox);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("from cons","onPause");
        title_position=title.getSelectedItemPosition();
        str_title=title.getSelectedItem().toString();
        str_plot_no=plot_no.getText().toString().trim();
        str_house_no=house_no.getText().toString().trim();
        str_building_name=building_name.getText().toString().trim();
        str_holding_khata_no=holding_khata_no.getText().toString().trim();
        str_ward_no=ward_no.getText().toString().trim();
        str_street=street.getText().toString().trim();
        str_block=block.getText().toString().trim();
        str_gp=gp.getText().toString().trim();
        str_address=address.getText().toString().trim();
        str_pin_no=pin_no.getText().toString().trim();
        str_landmark=landmark.getText().toString().trim();
        str_city=city.getText().toString().trim();
        //str_district=district.getText().toString().trim();
        str_district=district.getSelectedItem().toString();
        str_email=email.getText().toString().trim();
        str_mobile=mobile.getText().toString().trim();
        str_landline=landline.getText().toString().trim();

        DataHolderClass.getInstance().set_bulding_no(str_plot_no);
        DataHolderClass.getInstance().setHouse_flatno(str_house_no);
        DataHolderClass.getInstance().setHousename(str_building_name);
        DataHolderClass.getInstance().setKhata_no(str_holding_khata_no);
        DataHolderClass.getInstance().setWard_no(str_ward_no);
        DataHolderClass.getInstance().set_street(str_street);
        DataHolderClass.getInstance().set_block(str_block);
        DataHolderClass.getInstance().set_gp(str_gp);
        DataHolderClass.getInstance().setStr_adres1(str_address);
        DataHolderClass.getInstance().set_pin_no(str_pin_no);
        DataHolderClass.getInstance().setLandmark(str_landmark);
        DataHolderClass.getInstance().set_city(str_city);
        DataHolderClass.getInstance().set_district(str_district);
        DataHolderClass.getInstance().set_email(str_email);
        DataHolderClass.getInstance().set_mobile(str_mobile);
        DataHolderClass.getInstance().set_landline(str_landline);



        if(TextUtils.isEmpty(str_pin_no)){pin_no.setBackgroundColor(Color.YELLOW);str_pin_no=null;}
        if(TextUtils.isEmpty(str_address)){address.setBackgroundColor(Color.YELLOW);str_address=null;}
        if(TextUtils.isEmpty(str_mobile)){mobile.setBackgroundColor(Color.YELLOW);str_mobile=null;}
        if(TextUtils.isEmpty(str_city)){city.setBackgroundColor(Color.YELLOW);str_city=null;}
        //if(TextUtils.isEmpty(str_district)){district.setBackgroundColor(Color.YELLOW);str_district=null;}

       // Log.e("str_mobile",""+str_mobile);


    }
}
