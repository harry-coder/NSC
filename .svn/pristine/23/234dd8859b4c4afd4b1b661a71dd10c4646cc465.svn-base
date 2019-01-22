package feedback.mpnsc.exisitingconsumer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import feedback.mpnsc.DataHolderClass;
import feedback.mpnsc.R;

/**
 * Created by swatiG on 02-07-2015.
 */
public class Exisiting_BillingDetail extends Activity {
    EditText house_no,bulding_no,street,city,district,tehsil,block,gp,village,pin_no,email,
            mobile,landline,pan_no;
    String str_house_no,str_bulding_no,str_street,str_city,str_district,str_tehsil,str_block,
            str_gp,str_village,str_pin_no,str_email,
            str_mobile,str_landline,str_pan_no;
    boolean same_add=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billing_add);
        callView();

       // Log.e("value plot", DataHolderExisting.getInstance().getPlot1());
        house_no.setText( DataHolderExisting.getInstance().getPlot1());
        bulding_no.setText( DataHolderExisting.getInstance().getBuilding1());
        street.setText( DataHolderExisting.getInstance().getStreet1());
        city.setText( DataHolderExisting.getInstance().getCity1());
        district.setText( DataHolderExisting.getInstance().getDistrict1());
        tehsil.setText( DataHolderExisting.getInstance().getTehsil1());
        block.setText( DataHolderExisting.getInstance().getBlock1());
        gp.setText( DataHolderExisting.getInstance().getGP1());
        village.setText(DataHolderExisting.getInstance().getVillage1());
        pin_no.setText( DataHolderExisting.getInstance().getpin1());
        email.setText( DataHolderExisting.getInstance().getEmailid());
        mobile.setText( DataHolderExisting.getInstance().getMobile_no());
        landline.setText( DataHolderExisting.getInstance().getLand_line_no());
        pan_no.setText( DataHolderExisting.getInstance().getPancode());


       /* same_add= DataHolderClass.getInstance().get_same_add();
        if(same_add)
        {
            house_no.setText( DataHolderClass.getInstance().get_house_no());
            bulding_no.setText( DataHolderClass.getInstance().get_bulding_no());
            street.setText( DataHolderClass.getInstance().get_street());
            city.setText( DataHolderClass.getInstance().get_city());
            district.setText( DataHolderClass.getInstance().get_district());
            tehsil.setText( DataHolderClass.getInstance().get_tehsil());
            block.setText( DataHolderClass.getInstance().get_block());
            gp.setText( DataHolderClass.getInstance().get_gp());
            village.setText( DataHolderClass.getInstance().get_village());
            pin_no.setText( DataHolderClass.getInstance().get_pin_no());
            email.setText( DataHolderClass.getInstance().get_email());
            mobile.setText( DataHolderClass.getInstance().get_mobile());
            landline.setText( DataHolderClass.getInstance().get_landline());
            pan_no.setText( DataHolderClass.getInstance().get_pan_no());

        }*/


    }


    public void callView()
    {
        house_no=(EditText)findViewById(R.id.et_plotno);
        bulding_no=(EditText)findViewById(R.id.et_buildng_no);
        street=(EditText)findViewById(R.id.et_street);
        city=(EditText)findViewById(R.id.et_city);
        district=(EditText)findViewById(R.id.et_district);
        tehsil=(EditText)findViewById(R.id.et_tehsil);
        block=(EditText)findViewById(R.id.et_block);
        gp=(EditText)findViewById(R.id.et_gp);
        village=(EditText)findViewById(R.id.et_villlage);
        pin_no=(EditText)findViewById(R.id.et_pin);
        email=(EditText)findViewById(R.id.et_email_id);
        mobile=(EditText)findViewById(R.id.et_mobile);
        landline=(EditText)findViewById(R.id.et_landline);
        pan_no=(EditText)findViewById(R.id.et_pan_no);

    }


    @Override
    protected void onPause() {
        super.onPause();
        str_house_no=house_no.getText().toString().trim();
        str_bulding_no=bulding_no.getText().toString().trim();
        str_street=street.getText().toString().trim();
        str_city=city.getText().toString().trim();
        str_district=district.getText().toString().trim();
        str_tehsil=tehsil.getText().toString().trim();
        str_block=block.getText().toString().trim();
        str_gp=gp.getText().toString().trim();
        str_village=village.getText().toString().trim();
        str_pin_no=pin_no.getText().toString().trim();
        str_email=email.getText().toString().trim();
        str_mobile=mobile.getText().toString().trim();
        str_pan_no=pan_no.getText().toString().trim();
        str_landline=landline.getText().toString().trim();

        DataHolderClass.getInstance().set_house_no1(str_house_no);
        DataHolderClass.getInstance().set_bulding_no1(str_bulding_no);
        DataHolderClass.getInstance().set_street1(str_street);
        DataHolderClass.getInstance().set_city1(str_city);
        DataHolderClass.getInstance().set_district1(str_district);
        DataHolderClass.getInstance().set_tehsil1(str_tehsil);
        DataHolderClass.getInstance().set_block1(str_block);
        DataHolderClass.getInstance().set_gp1(str_gp);
        DataHolderClass.getInstance().set_village1(str_village);
        DataHolderClass.getInstance().set_pin_no1(str_pin_no);
        DataHolderClass.getInstance().set_email1(str_email);
        DataHolderClass.getInstance().set_mobile1(str_mobile);
        DataHolderClass.getInstance().set_landline1(str_landline);
        DataHolderClass.getInstance().set_pan_no1(str_pan_no);
        Log.e("from bill", "onPause");
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack();
    }
    public void ShowAlertonBack(){
        Exisiting_BillingDetail.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Exisiting_BillingDetail.this);
                builder.setTitle("Are you sure to go back:");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Exisiting_BillingDetail.this, ExistingConsumer_Search.class));
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

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("from", "onResume");
        same_add=DataHolderClass.getInstance().get_same_add();
        Log.e("boolean resume",""+DataHolderClass.getInstance().get_same_add());
        if(same_add)
        {
            house_no.setText( DataHolderClass.getInstance().get_house_no());
            bulding_no.setText( DataHolderClass.getInstance().get_bulding_no());
            street.setText( DataHolderClass.getInstance().get_street());
            city.setText( DataHolderClass.getInstance().get_city());
            district.setText( DataHolderClass.getInstance().get_district());
            tehsil.setText( DataHolderClass.getInstance().get_tehsil());
            block.setText( DataHolderClass.getInstance().get_block());
            gp.setText( DataHolderClass.getInstance().get_gp());
            village.setText( DataHolderClass.getInstance().get_village());
            pin_no.setText( DataHolderClass.getInstance().get_pin_no());
            email.setText( DataHolderClass.getInstance().get_email());
            mobile.setText( DataHolderClass.getInstance().get_mobile());
            landline.setText( DataHolderClass.getInstance().get_landline());
            pan_no.setText( DataHolderClass.getInstance().get_pan_no());


        }else{

            house_no.setText( DataHolderExisting.getInstance().getPlot1());
            bulding_no.setText( DataHolderExisting.getInstance().getBuilding1());
            street.setText( DataHolderExisting.getInstance().getStreet1());
            city.setText( DataHolderExisting.getInstance().getCity1());
            district.setText( DataHolderExisting.getInstance().getDistrict1());
            tehsil.setText( DataHolderExisting.getInstance().getTehsil1());
            block.setText( DataHolderExisting.getInstance().getBlock1());
            gp.setText( DataHolderExisting.getInstance().getGP1());
            village.setText(DataHolderExisting.getInstance().getVillage1());
            pin_no.setText( DataHolderExisting.getInstance().getpin1());
            email.setText( DataHolderExisting.getInstance().getEmailid());
            mobile.setText( DataHolderExisting.getInstance().getMobile_no());
            landline.setText( DataHolderExisting.getInstance().getLand_line_no());
            pan_no.setText( DataHolderExisting.getInstance().getPancode());
        }

    }
}

