package feedback.mpnsc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import feedback.mpnsc.exisitingconsumer.ExistingConsumer_Search;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * Created by swatiG on 31-05-2015.
 */
public class Options extends Activity {
    Button export, upload;
    ImageButton btn_ticket,start,btn_feasibility,meter_entry,details,btn_quit;
    SQLiteMasterTableAdapter sqLiteMasterTableAdapter;
    MultipleTicketManager SqLiteMultipleTicker;
    ConnectionDetector connectionDetector;
    Cursor cursor_pending,cursor_new_meter,cursor_feasibility,cursor_update;
    SessionManager sessionManager;
    HttpClient httpclient;
    HttpPost httppost;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_layout);
        getIntent();
//      Log.e("from", "menus");
        sessionManager=new SessionManager(Options.this);
        sqLiteMasterTableAdapter = new SQLiteMasterTableAdapter(Options.this);
        //export=(Button)findViewById(R.id.button);
        //upload=(Button)findViewById(R.id.button2);
        start=(ImageButton)findViewById(R.id.button3);
       // change_url=(Button)findViewById(R.id.button4);
        details=(ImageButton)findViewById(R.id.button5);
        meter_entry=(ImageButton)findViewById(R.id.button6);
        btn_quit=(ImageButton)findViewById(R.id.btn_quit);
        btn_feasibility=(ImageButton)findViewById(R.id.btn_feasibility);
        btn_ticket=(ImageButton)findViewById(R.id.btn_ticket);
        connectionDetector=new ConnectionDetector(this);
      //  Toast.makeText(getApplicationContext(),sessionManager.GET_URL(),Toast.LENGTH_LONG).show();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_existing=new Intent(Options.this, ExistingConsumer_Search.class);
               //Intent intent_existing=new Intent(Options.this, MeterImage.class);
                startActivity(intent_existing);

            }
        });


        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),TicketList.class);
                startActivity(intent);
            }
        });

        meter_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Options.this,SearchTicket.class);
                startActivity(intent);
            }
        });

        btn_feasibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Options.this,SearchTicket_Feasibility.class);
                startActivity(intent);
            }
        });

        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAlertQUIT();
            }
        });


        btn_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),SiteInspection_Activity2.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack();
    }
    public void ShowAlertonBack(){
        Options.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Options.this,R.style.MyAlertDialogStyle);
                builder.setCancelable(false);
                builder.setTitle("Are you sure to logout:");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent=new Intent(Options.this,Login.class);
                        startActivity(intent);
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
    public void ShowAlertQUIT(){
        Options.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Options.this,R.style.MyAlertDialogStyle);
                builder.setCancelable(false);
                builder.setTitle("Are you sure to logout:");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent=new Intent(Options.this,Login.class);
                        startActivity(intent);
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


    public class ExportData extends AsyncTask<String, String, String>
    {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog= new ProgressDialog(Options.this,R.style.MyAlertDialogStyle);
            progressDialog.setMessage("File is being exported");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            FileChannel source=null;
            FileChannel destination=null;
            String currentDBPath = "/data/"+ "com.example.swatig.connectionmanagment" +"/databases/"+"MYDATABASE_MASTERTABLE";
            String backupDBPath = "MYDATABASE_MASTERTABLE_export";
            File currentDB = new File(data, currentDBPath);
            File backupDB = new File(sd, backupDBPath);


            try {
                source = new FileInputStream(currentDB).getChannel();
                destination = new FileOutputStream(backupDB).getChannel();
                destination.transferFrom(source, 0, source.size());
                source.close();
                destination.close();
//                Log.e("data", "exported");
                // Toast.makeText(LoginPass.this, "DB Exported!", Toast.LENGTH_LONG).show();
            } catch(IOException e) {

                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }
    public class StartProgressBar extends AsyncTask<String, String,String>{
        ProgressDialog pd;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pd=new ProgressDialog(Options.this,R.style.MyAlertDialogStyle);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try{
                if(connectionDetector.isConnectingToInternet()){
                httpclient = new DefaultHttpClient();
                httppost = new HttpPost(sessionManager.GET_URL());
                senddata();}
                else{Toast.makeText(getApplicationContext(),"Internet is not connected", Toast.LENGTH_SHORT).show();}
            }catch(Exception e){
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
            //Log.e("response", response);
            if(connectionDetector.isConnectingToInternet()){
				//Toast.makeText(getBaseContext(),"send complete records",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getBaseContext(),"Internet not connected",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void senddata() {
        // TODO Auto-generated method stub
            try { //send from cursor
//                Log.e("from", "senddata");
                sqLiteMasterTableAdapter.openToRead();
                sqLiteMasterTableAdapter.openToWrite();
                cursor_pending = sqLiteMasterTableAdapter.mastersubdivisioncursorAll_new();
                cursor_new_meter=sqLiteMasterTableAdapter.new_meter_value();
                cursor_feasibility=sqLiteMasterTableAdapter.feasibility_value();
                cursor_update=sqLiteMasterTableAdapter.updated_record_value();
               // cursor_pending.getCount();
//                Log.e("no of record", ""+cursor_pending.getCount());
                if(cursor_pending.getCount()==0&&cursor_new_meter.getCount()==0
                        && cursor_feasibility.getCount()==0 && cursor_update.getCount()==0) {
//                    Log.e("from","if");
                    runOnUiThread(new Runnable() {

                        public void run() {
                            Toast.makeText(Options.this,"there is no pending record",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
//                    Log.e("from","else");
                    call_cursor();}
            } catch (Exception e) {
            }
    }
    public void call_cursor()
    {
        if(cursor_pending!=null && cursor_pending.moveToFirst()){
            do {
                int   id=cursor_pending.getInt(0);
                String division=cursor_pending.getString(1);
                String subdivion=cursor_pending.getString(2);
                String section=cursor_pending.getString(3);
                //String route=cursor_pending.getString(4);
                String name=cursor_pending.getString(5);
                String father_name=cursor_pending.getString(6);
                String name_org_corp=cursor_pending.getString(7);
                String type_org=cursor_pending.getString(8);
                String name_org =cursor_pending.getString(9);
                String block=cursor_pending.getString(10);
                String bulding_no=cursor_pending.getString(11);
                String city=cursor_pending.getString(12);
                String district=cursor_pending.getString(13);
                String gp=cursor_pending.getString(14);
                String house_no=cursor_pending.getString(15);
                String street=cursor_pending.getString(16);
                String tehsil=cursor_pending.getString(17);
                String village=cursor_pending.getString(18);
                String block1=cursor_pending.getString(19);
                String city1=cursor_pending.getString(20);
                String district1=cursor_pending.getString(21);
                String gp1=cursor_pending.getString(22);
                String house_no1=cursor_pending.getString(23);
                String street1=cursor_pending.getString(24);
                String tehsil1=cursor_pending.getString(25);
                String village1=cursor_pending.getString(26);
                String bulding_no1=cursor_pending.getString(27);
                String mobile1=cursor_pending.getString(28);
                String email1=cursor_pending.getString(29);
                String landline1=cursor_pending.getString(30);
                String designation=cursor_pending.getString(31);
                String connect_load=cursor_pending.getString(32);
                String tariff_cat=cursor_pending.getString(33);
                String plot_lat=cursor_pending.getString(34);
                String plot_long=cursor_pending.getString(35);
                String home_lat=cursor_pending.getString(36);
                String home_long=cursor_pending.getString(37);
                String image=cursor_pending.getString(38);
                String image_name=cursor_pending.getString(39);
                String pan_no=cursor_pending.getString(40);
                String pin_no=cursor_pending.getString(41);
                String pin_no1=cursor_pending.getString(42);
                String ticket=cursor_pending.getString(43);

                if(ticket.equals(null)) {
//                Log.e("from Cursor", ""+cursorSend.getInt(0));
                    try {
                        new PendingData(getBaseContext(), id, division, subdivion, section, name, father_name,
                                name_org_corp, type_org, name_org, block, bulding_no, city, district, gp, house_no,
                                street, tehsil, village, block1, city1, district1, gp1,
                                house_no1, street1, tehsil1, village1, bulding_no1, mobile1,
                                email1, landline1, designation, connect_load, tariff_cat, plot_lat, plot_long, home_lat,
                                home_long, image, image_name, pan_no, pin_no, pin_no1).execute();
                        image = null;
                    } catch (Exception e) {
                    }
                }
            } while (cursor_pending.moveToNext());
        }
        if(cursor_new_meter!=null&& cursor_new_meter.moveToFirst())
        {
            do{
                int new_meter_id=cursor_new_meter.getInt(0);
                String new_meter_tag=cursor_new_meter.getString(1);
                String new_meter_ticket_no=cursor_new_meter.getString(2);
                String new_meter_no=cursor_new_meter.getString(3);
                String new_meter_manufacturer_code=cursor_new_meter.getString(4);
                String new_meter_type=cursor_new_meter.getString(5);
                String new_meter_phase=cursor_new_meter.getString(6);
                String new_meter_initial_date=cursor_new_meter.getString(7);
                String new_meter_reading=cursor_new_meter.getString(8);
                String new_meter_bill_basis=cursor_new_meter.getString(9);
                String new_meter_digit=cursor_new_meter.getString(10);
                String new_meter_ownership=cursor_new_meter.getString(11);
                String new_meter_metered=cursor_new_meter.getString(12);
                Log.e("metered", ""+new_meter_metered);
                try{
                    new Pending_New_Meter(getApplicationContext(),new_meter_id,new_meter_tag,new_meter_ticket_no,
                            new_meter_no,new_meter_manufacturer_code,new_meter_type,new_meter_phase,
                            new_meter_initial_date,new_meter_reading,new_meter_bill_basis,new_meter_digit,
                            new_meter_ownership,new_meter_metered).execute();
                }catch(Exception e){}
            }while (cursor_new_meter.moveToNext());
        }


        if(cursor_feasibility!=null&& cursor_feasibility.moveToFirst())
        {
            do{
                int feasibility_id=cursor_feasibility.getInt(0);
                String feasibility_tag=cursor_feasibility.getString(1);
                String feasibility_ticket_no=cursor_feasibility.getString(2);
                String feasibility_home_lat=cursor_feasibility.getString(3);
                String feasibility_home_long=cursor_feasibility.getString(4);
                String feasibility_pole_lat=cursor_feasibility.getString(5);
                String feasibility_pole_long=cursor_feasibility.getString(6);
                String feasibility_route=cursor_feasibility.getString(7);
                String feasibility_value=cursor_feasibility.getString(8);
                String feasibility_manual=cursor_feasibility.getString(9);


                try{
                    new Pending_Feasibility(getApplicationContext(),feasibility_id,feasibility_tag,feasibility_ticket_no,
                            feasibility_home_lat,feasibility_home_long,feasibility_pole_lat,feasibility_pole_long,feasibility_route,
                            feasibility_value,feasibility_manual).execute();
                }catch(Exception e){}
            }while (cursor_new_meter.moveToNext());
        }



        if(cursor_update!=null && cursor_update.moveToFirst()){
            do {
                Log.e("cursor",""+cursor_update);
                int   id_update=cursor_update.getInt(0);
                String division_update=cursor_update.getString(1);
                String subdivion_update=cursor_update.getString(2);
                String section_update=cursor_update.getString(3);
                //String route_update=cursor_update.getString(4);
                String name_update=cursor_update.getString(5);
                String father_name_update=cursor_update.getString(6);
                String name_org_corp_update=cursor_update.getString(7);
                String type_org_update=cursor_update.getString(8);
                String name_org_update =cursor_update.getString(9);
                String block_update=cursor_update.getString(10);
                String bulding_no_update=cursor_update.getString(11);
                String city_update=cursor_update.getString(12);
                String district_update=cursor_update.getString(13);
                String gp_update=cursor_update.getString(14);
                String house_no_update=cursor_update.getString(15);
                String street_update=cursor_update.getString(16);
                String tehsil_update=cursor_update.getString(17);
                String village_update=cursor_update.getString(18);
                String block1_update=cursor_update.getString(19);
                String city1_update=cursor_update.getString(20);
                String district1_update=cursor_update.getString(21);
                String gp1_update=cursor_update.getString(22);
                String house_no1_update=cursor_update.getString(23);
                String street1_update=cursor_update.getString(24);
                String tehsil1_update=cursor_update.getString(25);
                String village1_update=cursor_update.getString(26);
                String bulding_no1_update=cursor_update.getString(27);
                String mobile1_update=cursor_update.getString(28);
                String email1_update=cursor_update.getString(29);
                String landline1_update=cursor_update.getString(30);
                String designation_update=cursor_update.getString(31);
                String connect_load_update=cursor_update.getString(32);
                String tariff_cat_update=cursor_update.getString(33);
                String plot_lat_update=cursor_update.getString(34);
                String plot_long_update=cursor_update.getString(35);
                String home_lat_update=cursor_update.getString(36);
                String home_long_update=cursor_update.getString(37);
                String image_update=cursor_update.getString(38);
                String image_name_update=cursor_update.getString(39);
                String pan_no_update=cursor_update.getString(40);
                String pin_no_update=cursor_update.getString(41);
                Log.e("from pin_no_update", ""+pin_no_update);
                String pin_no1_update=cursor_update.getString(42);
                Log.e("from pin_no1_update", ""+pin_no1_update);
                String ticket_update=cursor_update.getString(43);

                if((!ticket_update.equals(null))) {

                    try {
                        new PendingData_update(getBaseContext(), id_update, division_update, subdivion_update, section_update, name_update, father_name_update,
                                name_org_corp_update, type_org_update, name_org_update, block_update, bulding_no_update, city_update, district_update, gp_update, house_no_update,
                                street_update, tehsil_update, village_update, block1_update, city1_update, district1_update, gp1_update,
                                house_no1_update, street1_update, tehsil1_update, village1_update, bulding_no1_update, mobile1_update,
                                email1_update, landline1_update, designation_update, connect_load_update, tariff_cat_update, plot_lat_update, plot_long_update, home_lat_update,
                                home_long_update, image_update, image_name_update, pan_no_update, pin_no_update, pin_no1_update, ticket_update).execute();
                        image_update = null;
                    } catch (Exception e) {
                    }
                }
            } while (cursor_update.moveToNext());
        }
    }
    public class PendingData extends AsyncTask<String, String,String> {
        String response;
        SQLiteMasterTableAdapter adapter;
        Context contect;
        String network_interrupt=null;
        int id;
        String division,subdivion,section,name,route,father_name,
                name_org_corp,type_org,name_org,block,bulding_no,city,district,gp,house_no,
                street,tehsil,village,block1,city1,district1,gp1,
                house_no1,street1,tehsil1,village1,bulding_no1,mobile1,
                email1,landline1,designation,connect_load,tariff_cat,plot_lat,plot_long,home_lat,
                home_long,image,image_name,pan_no,pin_no,pin_no1;
        public  PendingData(Context c,int int_id,String str_division,String str_subdivion,String str_section,String str_name,String str_father_name,
                            String str_name_org_corp,String str_type_org,String str_name_org,String str_block,String str_bulding_no,String str_city,String str_district,String str_gp,String str_house_no,
                            String str_street,String str_tehsil,String str_village,String str_block1,String str_city1,String str_district1,String str_gp1,
                            String str_house_no1,String str_street1,String str_tehsil1,String str_village1,String str_bulding_no1,String str_mobile1,
                            String str_email1,String str_landline1,String str_designation,String str_connect_load,String str_tariff_cat,String str_plot_lat,
                            String str_plot_long,String str_home_lat,
                            String str_home_long,String str_image,String str_image_name,
                            String str_pan_no,String str_pin_no,String str_pin_no1){
            // TODO Auto-generated constructor stub
            try{
                contect=c;
                adapter=new SQLiteMasterTableAdapter(contect);
//		adapter.openToRead();
                adapter.openToWrite();
                id=int_id;
                division=str_division;
                subdivion=str_subdivion;
                section=str_section;
              //  route=str_route;
                name=str_name;
                father_name=str_father_name;
                name_org_corp=str_name_org_corp;
                type_org=str_type_org;
                name_org=str_name_org;
                block=str_block;
                bulding_no=str_bulding_no;
                city=str_city;
                district=str_district;
                gp=str_gp;
                house_no=str_house_no;
                street=str_street;
                tehsil=str_tehsil;
                village=str_village;
                block1=str_block1;
                city1=str_city1;
                district1=str_district1;
                gp1=str_gp1;
                house_no1=str_house_no1;
                street1=str_street1;
                tehsil1=str_tehsil1;
                village1=str_village1;
                bulding_no1=str_bulding_no1;
                mobile1=str_mobile1;
                email1=str_email1;
                landline1=str_landline1;
                designation=str_designation;
                connect_load=str_connect_load;
                tariff_cat=str_tariff_cat;
                plot_lat=str_plot_lat;
                plot_long=str_plot_long;
                home_lat=str_home_lat;
                home_long=str_home_long;
                image=str_image;
                image_name=str_image_name;
                pan_no=str_pan_no;
                pin_no=str_pin_no;
                pin_no1=str_pin_no1;
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("value", division+","+subdivion+","+section+","+name+","+father_name+","+
                    name_org_corp+","+type_org+","+name_org+","+designation+","+connect_load+","+tariff_cat));


            nameValuePairs.add(new BasicNameValuePair("block", block));
            nameValuePairs.add(new BasicNameValuePair("building", bulding_no));
            nameValuePairs.add(new BasicNameValuePair("city", city));
            nameValuePairs.add(new BasicNameValuePair("district", district));
            nameValuePairs.add(new BasicNameValuePair("gp", gp));
            nameValuePairs.add(new BasicNameValuePair("house_no",house_no));
            nameValuePairs.add(new BasicNameValuePair("street", street));
            nameValuePairs.add(new BasicNameValuePair("tehsil", tehsil));
            nameValuePairs.add(new BasicNameValuePair("village",village));
            nameValuePairs.add(new BasicNameValuePair("block1", block1));
            nameValuePairs.add(new BasicNameValuePair("city1",city1));
            nameValuePairs.add(new BasicNameValuePair("district1", district1));
            nameValuePairs.add(new BasicNameValuePair("gp1",gp1));
            nameValuePairs.add(new BasicNameValuePair("house_no1",house_no1));
            nameValuePairs.add(new BasicNameValuePair("street1",street1));
            nameValuePairs.add(new BasicNameValuePair("tehsil1",  tehsil1));
            nameValuePairs.add(new BasicNameValuePair("village1", village1));
            nameValuePairs.add(new BasicNameValuePair("building1",bulding_no1));
            nameValuePairs.add(new BasicNameValuePair("mobile",mobile1));
            nameValuePairs.add(new BasicNameValuePair("email1", email1));
            nameValuePairs.add(new BasicNameValuePair("landline1",landline1));
            nameValuePairs.add(new BasicNameValuePair("pan_no", pan_no));
            nameValuePairs.add(new BasicNameValuePair("pin_no", pin_no));
            nameValuePairs.add(new BasicNameValuePair("pin_no1",  pin_no1));

            nameValuePairs.add(new BasicNameValuePair("plot_lat", plot_lat));
            nameValuePairs.add(new BasicNameValuePair("plot_long", plot_long));
            nameValuePairs.add(new BasicNameValuePair("home_lat",home_lat));
            nameValuePairs.add(new BasicNameValuePair("home_long", home_long));
            nameValuePairs.add(new BasicNameValuePair("image", image));
            nameValuePairs.add(new BasicNameValuePair("image_name", image_name));
 //           Log.e("namepairpending",""+nameValuePairs);
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response = httpclient.execute(httppost, responseHandler);
            } catch (Exception e) {
                Log.e("Exception doinback","->"+e);
                Log.e("response doinback","->"+response);
                network_interrupt=e.getMessage();
            }
            return response.trim();
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                if (network_interrupt == null) {
                    try {
                        if (response.length()<20) {
                          //  Toast.makeText(contect, "record send", Toast.LENGTH_SHORT).show();
                            SqLiteMultipleTicker.ticketlist_insert(response,name);
                            adapter.delete_byID(id);
                            image = null;
                            response = null;
                        }
                    } catch (Exception e) {
                    } finally {
                        adapter.close();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "try again", Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e){
           }
        }
    }

    public class PendingData_update extends AsyncTask<String, String,String> {
        String response;
        SQLiteMasterTableAdapter adapter;
        Context contect;
        String network_interrupt=null;
        int id;
        String division,subdivion,section,route,name,father_name,
                name_org_corp,type_org,name_org,block,bulding_no,city,district,gp,house_no,
                street,tehsil,village,block1,city1,district1,gp1,
                house_no1,street1,tehsil1,village1,bulding_no1,mobile1,
                email1,landline1,designation,connect_load,tariff_cat,plot_lat,plot_long,home_lat,
                home_long,image,image_name,pan_no,pin_no,pin_no1,ticket;
        public  PendingData_update(Context c,int int_id,String str_division,String str_subdivion,String str_section,String str_name,String str_father_name,
                            String str_name_org_corp,String str_type_org,String str_name_org,String str_block,String str_bulding_no,String str_city,String str_district,String str_gp,String str_house_no,
                            String str_street,String str_tehsil,String str_village,String str_block1,String str_city1,String str_district1,String str_gp1,
                            String str_house_no1,String str_street1,String str_tehsil1,String str_village1,String str_bulding_no1,String str_mobile1,
                            String str_email1,String str_landline1,String str_designation,String str_connect_load,String str_tariff_cat,String str_plot_lat,
                            String str_plot_long,String str_home_lat,
                            String str_home_long,String str_image,String str_image_name,
                            String str_pan_no,String str_pin_no,String str_pin_no1,String str_ticket){
            // TODO Auto-generated constructor stub
            try{
                contect=c;
                adapter=new SQLiteMasterTableAdapter(contect);
//		adapter.openToRead();
                adapter.openToWrite();
                id=int_id;
                division=str_division;
                subdivion=str_subdivion;
                section=str_section;
                //route=str_route;
                name=str_name;
                father_name=str_father_name;
                name_org_corp=str_name_org_corp;
                type_org=str_type_org;
                name_org=str_name_org;
                block=str_block;
                bulding_no=str_bulding_no;
                city=str_city;
                district=str_district;
                gp=str_gp;
                house_no=str_house_no;
                street=str_street;
                tehsil=str_tehsil;
                village=str_village;
                block1=str_block1;
                city1=str_city1;
                district1=str_district1;
                gp1=str_gp1;
                house_no1=str_house_no1;
                street1=str_street1;
                tehsil1=str_tehsil1;
                village1=str_village1;
                bulding_no1=str_bulding_no1;
                mobile1=str_mobile1;
                email1=str_email1;
                landline1=str_landline1;
                designation=str_designation;
                connect_load=str_connect_load;
                tariff_cat=str_tariff_cat;
                plot_lat=str_plot_lat;
                plot_long=str_plot_long;
                home_lat=str_home_lat;
                home_long=str_home_long;
                image=str_image;
                image_name=str_image_name;
                pan_no=str_pan_no;
                pin_no=str_pin_no;
                pin_no1=str_pin_no1;
                ticket=str_ticket;
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("tag",  "update_consumer"));

            nameValuePairs.add(new BasicNameValuePair("ticket",  ticket));

            nameValuePairs.add(new BasicNameValuePair("value", division+","+subdivion+","+section+","+name+","+father_name+","+
                    name_org_corp+","+type_org+","+name_org+","+designation+","+connect_load+","+tariff_cat));


            nameValuePairs.add(new BasicNameValuePair("block", block));
            nameValuePairs.add(new BasicNameValuePair("building", bulding_no));
            nameValuePairs.add(new BasicNameValuePair("city", city));
            nameValuePairs.add(new BasicNameValuePair("district", district));
            nameValuePairs.add(new BasicNameValuePair("gp", gp));
            nameValuePairs.add(new BasicNameValuePair("house_no",house_no));
            nameValuePairs.add(new BasicNameValuePair("street", street));
            nameValuePairs.add(new BasicNameValuePair("tehsil", tehsil));
            nameValuePairs.add(new BasicNameValuePair("village",village));
            nameValuePairs.add(new BasicNameValuePair("block1", block1));
            nameValuePairs.add(new BasicNameValuePair("city1",city1));
            nameValuePairs.add(new BasicNameValuePair("district1", district1));
            nameValuePairs.add(new BasicNameValuePair("gp1",gp1));
            nameValuePairs.add(new BasicNameValuePair("house_no1",house_no1));
            nameValuePairs.add(new BasicNameValuePair("street1",street1));
            nameValuePairs.add(new BasicNameValuePair("tehsil1",  tehsil1));
            nameValuePairs.add(new BasicNameValuePair("village1", village1));
            nameValuePairs.add(new BasicNameValuePair("building1",bulding_no1));
            nameValuePairs.add(new BasicNameValuePair("mobile",mobile1));
            nameValuePairs.add(new BasicNameValuePair("email1", email1));
            nameValuePairs.add(new BasicNameValuePair("landline1",landline1));
            nameValuePairs.add(new BasicNameValuePair("pan_no", pan_no));
            nameValuePairs.add(new BasicNameValuePair("pin_no", pin_no));
            nameValuePairs.add(new BasicNameValuePair("pin_no1",  pin_no1));

            nameValuePairs.add(new BasicNameValuePair("plot_lat", plot_lat));
            nameValuePairs.add(new BasicNameValuePair("plot_long", plot_long));
            nameValuePairs.add(new BasicNameValuePair("home_lat",home_lat));
            nameValuePairs.add(new BasicNameValuePair("home_long", home_long));
            nameValuePairs.add(new BasicNameValuePair("image", image));
            nameValuePairs.add(new BasicNameValuePair("image_name", image_name));

            //           Log.e("namepairpending",""+nameValuePairs);
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response = httpclient.execute(httppost, responseHandler);
            } catch (Exception e) {
                Log.e("Exception doinback","->"+e);
                Log.e("response doinback","->"+response);
                network_interrupt=e.getMessage();
            }
            return response.trim();
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                if (network_interrupt == null) {
                    try {
                        if (response.trim().equals("1")) {
                            //  Toast.makeText(contect, "record send", Toast.LENGTH_SHORT).show();
                            Toast.makeText(contect, "record send", Toast.LENGTH_SHORT).show();
                            adapter.delete_updated_byID(id);
                            image = null;
                            response = null;
                        }
                        else{
                            Toast.makeText(contect, "try again", Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                    } finally {
                        adapter.close();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "try again", Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e){
            }
        }
    }
    // .................. pending new record...........................//
    public class Pending_New_Meter extends AsyncTask<String, String,String> {
        String response;
        SQLiteMasterTableAdapter adapter;
        Context contect;
        String network_interrupt=null;
        int id;
        String ticket_no,meter_no,manufacturer_code,meter_type,meter_phase,installed_date,
                initial_reading,bill_basis,digit,ownership,metered,tag;
        public  Pending_New_Meter(Context c,int int_id,String str_tag,String str_ticket_no,String str_meter_no,String str_manufacturer_code,String str_meter_type,String str_meter_phase,
                            String str_installed_date,String str_initial_reading,String str_bill_basis,String str_digit,String str_ownership,String str_metered
                           ){
            // TODO Auto-generated constructor stub
            try{
                contect=c;
                adapter=new SQLiteMasterTableAdapter(contect);
//		adapter.openToRead();
                adapter.openToWrite();
                id=int_id;
                tag=str_tag;
                ticket_no=str_ticket_no;
                meter_no=str_meter_no;
                manufacturer_code=str_manufacturer_code;
                meter_type=str_meter_type;
                meter_phase=str_meter_phase;
                installed_date=str_installed_date;
                initial_reading=str_initial_reading;
                bill_basis=str_bill_basis;
                digit=str_digit;
                ownership=str_ownership;
                metered=str_metered;

            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("tag",tag));
            nameValuePairs.add(new BasicNameValuePair("value", ticket_no+","+meter_no+","+manufacturer_code+","+meter_type+","+meter_phase+","+
                    installed_date+","+initial_reading+","+bill_basis+","+digit+","+ownership+","+metered));
                      Log.e("namepairpending",""+nameValuePairs);
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response = httpclient.execute(httppost, responseHandler);
            } catch (Exception e) {
                Log.e("Exception doinback","->"+e);
                Log.e("response doinback","->"+response);
                network_interrupt=e.getMessage();
            }
            return response.trim();
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                if (network_interrupt == null) {
                    try {
                        response=response.trim();
                        Log.e("response",response);
                        if (response.equalsIgnoreCase("1")) {
                            Toast.makeText(contect, "record send", Toast.LENGTH_SHORT).show();
                            adapter.delete_byID_new_meter(id);
                            response = null;
                        }
                        else{
                            Toast.makeText(contect, "try again", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                    } finally {
                        adapter.close();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "try again", Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e){
            }
        }
    }

    public class Pending_Feasibility extends AsyncTask<String, String,String> {
        String response;
        SQLiteMasterTableAdapter adapter;
        Context contect;
        String network_interrupt=null;
        int id;
        String tag, ticket_no,home_lat,home_long, pole_lat, pole_long,feasibility_value,feasibility_manual,route;
        public  Pending_Feasibility(Context c,int int_id,String str_tag,String str_ticket_no,String str_home_lat,String str_home_long,String str_pole_lat,String str_pole_long,
                                    String str_route,String str_feasibility_value,String str_feasibility_manual
        ){
            // TODO Auto-generated constructor stub
            try{
                contect=c;
                adapter=new SQLiteMasterTableAdapter(contect);
//		adapter.openToRead();
                adapter.openToWrite();
                id=int_id;
                tag=str_tag;
                ticket_no=str_ticket_no;
                home_lat=str_home_lat;
                home_long=str_home_long;
                pole_lat=str_pole_lat;
                pole_long=str_pole_long;
                route=str_route;
                feasibility_value=str_feasibility_value;
                feasibility_manual=str_feasibility_manual;
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("tag",tag));
            nameValuePairs.add(new BasicNameValuePair("ticket_no",ticket_no));
            nameValuePairs.add(new BasicNameValuePair("home_lat",String.valueOf(home_lat)));
            nameValuePairs.add(new BasicNameValuePair("home_long",String.valueOf(home_long)));
            nameValuePairs.add(new BasicNameValuePair("pole_lat",String.valueOf(pole_lat)));
            nameValuePairs.add(new BasicNameValuePair("pole_long",String.valueOf(pole_long)));
            nameValuePairs.add(new BasicNameValuePair("route",route));
            nameValuePairs.add(new BasicNameValuePair("feasibility",feasibility_value));
            nameValuePairs.add(new BasicNameValuePair("manual_fes",feasibility_manual));
            Log.e("namevaluepair",""+nameValuePairs);
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response = httpclient.execute(httppost, responseHandler);
            } catch (Exception e) {
                Log.e("Exception doinback","->"+e);
                Log.e("response doinback","->"+response);
                network_interrupt=e.getMessage();
            }
            return response.trim();
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                if (network_interrupt == null) {
                    try {
                        response=response.trim();
                        Log.e("response",response);
                        if (response.equalsIgnoreCase("1")) {
                            Toast.makeText(contect, "record send", Toast.LENGTH_SHORT).show();
                            adapter.delete_byID_feasibility(id);
                            response = null;
                        }
                        else{
                            Toast.makeText(contect, "try again", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                    } finally {
                        adapter.close();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "try again", Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e){
            }
        }
    }
}