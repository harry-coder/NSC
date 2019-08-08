package feedback.mpnsc.exisitingconsumer;

/**
 * Created by swatiG on 03-07-2015.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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

import feedback.mpnsc.ConnectionDetector;
import feedback.mpnsc.DataHolderClass;
import feedback.mpnsc.MultipleTicketManager;
import feedback.mpnsc.Options;
import feedback.mpnsc.R;
import feedback.mpnsc.SQLiteMasterTableAdapter;
import feedback.mpnsc.SessionManager;

public class Existing_DataFinish extends Activity {
    // ..................................................Camera Variables................................//

    byte[] byteArray;
    Button btn_submit;
    Button btn_photograph1;
    private ImageView mImageView;
    private Bitmap mImageBitmap;
    String image;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    Camera mCamera;
    private static final String IMAGE_DIRECTORY_NAME = "NSC";
    public Uri fileUri;
    String   cust_id, meter_num, cons_name, cons_add,  cons_contact;
    static String meterimageName, meter_name;
    SessionManager session;
    byte[] bmeter;
    public String FTPURL="ftp://103.251.62.42/sushil/";
    static String empid,c_code;
    String URI;

    CheckBox checkbox_person_avaiable;
    ConnectionDetector connectionDetector;

    SQLiteMasterTableAdapter sqLiteMasterTableAdapter;
    MultipleTicketManager sqLiteMultipleTicketList;

    DataHolderClass dataHolderClass;

    String response;
    SessionManager sessionManager;

    private boolean isDeviceSupportCamera() {
        if (Existing_DataFinish.this.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            return true;

        } else {
            return false;
        }
    }
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.finishdata);
        connectionDetector=new ConnectionDetector(this);
        sessionManager=new SessionManager(Existing_DataFinish.this);
        sqLiteMasterTableAdapter= new SQLiteMasterTableAdapter(Existing_DataFinish.this);
        sqLiteMultipleTicketList=new MultipleTicketManager(Existing_DataFinish.this);

        btn_submit=(Button)findViewById(R.id.button1);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (connectionDetector.isConnectingToInternet()) {
                        //send data
                        new SendToServer().execute();
                    } else {

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
                                        DataHolderClass.getInstance().get_ticket_no()

                                );
                        Toast.makeText(getApplicationContext(), "Record Saved", Toast.LENGTH_SHORT).show();
                        finish();*/
                    }


            }
        });

        if(!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),"Camera not supported",Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack();
    }
    public void ShowAlertonBack(){
        Existing_DataFinish.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Existing_DataFinish.this,R.style.MyAlertDialogStyle);
                builder.setTitle("Are you sure to go back:");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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

    // ==============================================Find Image start===================================//



    public class SendToServer extends AsyncTask<String, String,String>
    {    String network_interrupt=null;
        ProgressDialog pd;
        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd=new ProgressDialog(Existing_DataFinish.this,R.style.MyAlertDialogStyle);
            pd.setMessage("record sending");
            pd.setCancelable(false);
            pd.show();
        }
        @Override
        protected String doInBackground(String... params)
        {
            try{
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("tag",  "update_consumer"));

                nameValuePairs.add(new BasicNameValuePair("ticket",  DataHolderClass.getInstance().get_ticket_no()));
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
                Log.e("values", ""+nameValuePairs);
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(sessionManager.GET_URL());
                // HttpPost httppost = new HttpPost("http://sbm.fieldpm.com/sb/handset_reading");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response = httpclient.execute(httppost,responseHandler);
                Log.e("Response7",response);
               }catch(Exception e){
                // Log.e("Response4","->"+e);
                Log.e("Response5","->"+response);
                network_interrupt=e.getMessage();
                // Toast.makeText(getApplicationContext(), "There is something wrong cannot send data",Toast.LENGTH_SHORT).show();
                // Toast.makeText(getApplicationContext(), "There is something wrong cannot send data",Toast.LENGTH_SHORT).show();
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result)
        {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pd.dismiss();
            pd.hide();
            //     Log.e("RESPONSE6",""+response);
//    	       // Toast.makeText(getApplicationContext(), rrrr, Toast.LENGTH_LONG).show();
            try {
                //   response.trim();
                if (network_interrupt == null) {
                    //   Log.e("network_interrupt",network_interrupt);
                    try {
                        if (response.trim().equals("1")) {
                            //        Log.e("RESPONSE1",response);
                            Toast.makeText(getApplicationContext(),"send successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Existing_DataFinish.this, Options.class);
                            startActivity(intent);
                            finish();
//
                        } else {
                            //    Log.e("Response2", "->" + response);
                            ShowAlert();
                            //    Log.e("RESPONSE8",""+response.length());

                        }
                    } catch (Exception e) {
                        //   Log.e("Response3", "->" + e);
                    }
                } else {
                    ShowAlert();
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
            }catch (Exception e)
            {
            }
        }

        public void ShowAlert() {
            Existing_DataFinish.this.runOnUiThread(new Runnable() {
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Existing_DataFinish.this,R.style.MyAlertDialogStyle);
                    builder.setTitle("Do you want to...");
                    builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            new SendToServer().execute();
                        }
                    });
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
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }
    }
    public void show_ticket(String ticketNumber) {
        Existing_DataFinish.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Existing_DataFinish.this,R.style.MyAlertDialogStyle);
                builder.setTitle("Ticket Numer:");
                builder.setMessage(response);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
