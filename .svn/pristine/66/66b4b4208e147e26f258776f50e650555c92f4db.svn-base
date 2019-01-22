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

import feedback.mpnsc.exisitingconsumer.ExistingConsumer_Search;


public class Tab3 extends Activity {

    EditText connect_load;
    Spinner tariff_spinner;
    TextView load_unit;
    String str_supply_type, str_supply_level, str_load_unit, str_metering_side, str_trans_ownership, str_trans_capacity,
            str_tariff_cat,str_bill_demand,str_scheme,str_connection,str_connect_load,str_other;
	/** Called when the activity is first created. */
     Button submit;



    ConnectionDetector connectionDetector;

    SQLiteMasterTableAdapter sqLiteMasterTableAdapter;
    MultipleTicketManager sqLiteMultipleTicketList;

    String response;
    SessionManager sessionManager;
    SQLiteAdapter sqlAdapter;


    //-----------------------    ArrayList   ---------------------------------//
    ArrayList<String> tariffArraycode, tariffArrayname;

    ArrayAdapter<String> tariffAdapter;

    Cursor tarifftablecursor;
    String tariff_code;

    int int_tariff;


    @Override
	  public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.tab3);

          connectionDetector=new ConnectionDetector(this);
          sessionManager=new SessionManager(Tab3.this);
          sqLiteMasterTableAdapter= new SQLiteMasterTableAdapter(Tab3.this);
          sqLiteMultipleTicketList=new MultipleTicketManager(Tab3.this);
          callView();
          //getValue();

          submit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  //str_tariff_cat = tariff_cat.getSelectedItem().toString();
                  str_load_unit = load_unit.getText().toString();
                  //str_connect_load = connect_load.getText().toString();

                 if (connectionDetector.isConnectingToInternet()) {
                      //send data
                      new SendToServer().execute();
                  } else {
                     Toast.makeText(getApplicationContext(),"Data Not sending to server",Toast.LENGTH_SHORT).show();
                  }


              }
          });

        tariffArraycode = new ArrayList<String>();
        tariffArrayname = new ArrayList<String>();

        new TARIFFTABLEMANAGER(Tab3.this).execute();
        tariff_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                try {
                    tariff_code = tariffArraycode.get(position).toString();
                } catch (Exception e) {

                }

//                  Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_LONG).show();
                if (position > 0) {
                    //new SUBDIVISIONTABLEMANAGER(Tab1.this).execute();
                } else {


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });



	    }


    public void callView()
    {

        load_unit=(TextView)findViewById(R.id.spinner13);
        tariff_spinner=(Spinner)findViewById(R.id.spinner17);
        //connect_load=(EditText)findViewById(R.id.spinner10);
        getValue();


        submit=(Button)findViewById(R.id.submit);
    }


    public void getValue() {

        str_load_unit = load_unit.getText().toString();
        //str_tariff_cat = tariff_cat.getSelectedItem().toString();
        //str_connect_load = connect_load.getText().toString().trim();
        DataHolderClass dataHolderClass = DataHolderClass.getInstance();
        dataHolderClass.set_supply_type(str_supply_type);
        dataHolderClass.set_supply_level(str_supply_level);
        dataHolderClass.set_load_unit(str_load_unit);
        dataHolderClass.set_metering_side(str_metering_side);
        dataHolderClass.set_trans_ownership(str_trans_ownership);
        dataHolderClass.set_trans_capacity(str_trans_capacity);
        dataHolderClass.set_tariff_cat(str_tariff_cat);
        dataHolderClass.set_bill_demand(str_bill_demand);
        dataHolderClass.set_scheme(str_scheme);
        dataHolderClass.set_connection(str_connection);
        dataHolderClass.set_connect_load(str_connect_load);
        dataHolderClass.set_other(str_other);

        Log.e("tab3", str_supply_type + str_supply_level + str_load_unit + str_metering_side + str_trans_ownership + str_trans_capacity +
                str_tariff_cat + str_bill_demand + str_scheme + str_connection + str_connect_load + str_other);

    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack();
    }
    public void ShowAlertonBack(){
        Tab3.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Tab3.this);
                builder.setTitle("Are you sure to go back:");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Tab3.this, ExistingConsumer_Search.class));
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


    public class SendToServer extends AsyncTask<String, String,String>
    {    String network_interrupt=null;
        ProgressDialog pd;
        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd=new ProgressDialog(Tab3.this);
            pd.setMessage("record sending");
            pd.setCancelable(false);
            pd.show();
        }
        @Override
        protected String doInBackground(String... params)
        {
            try{
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

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
                nameValuePairs.add(new BasicNameValuePair("category",tariff_code));
                nameValuePairs.add(new BasicNameValuePair("get_connect_load",str_connect_load));

                Log.e("values", ""+nameValuePairs);
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(sessionManager.GET_URL());
                //HttpPost httppost = new HttpPost("http://103.192.172.18:8083/oracletest3.php");
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
            return response.trim();
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
                        if (response.length()<20) {
                            //        Log.e("RESPONSE1",response);
//                            Toast.makeText(getApplicationContext(), "record send successfully"+"\n"+response, Toast.LENGTH_LONG).show();
                            sqLiteMultipleTicketList.openToRead();
                            sqLiteMultipleTicketList.openToWrite();
                            sqLiteMultipleTicketList.ticketlist_insert(response,DataHolderClass.getInstance().get_name());
                            show_ticket(response);
                        } else {
                            //    Log.e("Response2", "->" + response);
                            ShowAlert();
                            //    Log.e("RESPONSE8",""+response.length());

                        }
                    } catch (Exception e) {
                        //   Log.e("Response3", "->" + e);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Record Not send due to internet coonectivity", Toast.LENGTH_SHORT).show();

                }
            }catch (Exception e)
            {
            }
        }
        public void ShowAlert() {
            Tab3.this.runOnUiThread(new Runnable() {
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Tab3.this);
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
    public void show_ticket(String ticketNumber) {
        Tab3.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Tab3.this);
                builder.setTitle("Ticket Number:");
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
            pd = new ProgressDialog(Tab3.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            sqlAdapter = new SQLiteAdapter(Tab3.this);
            try {
                sqlAdapter.openToRead();
                sqlAdapter.openToWrite();
                tarifftablecursor = sqlAdapter.mastertariffcursorAll();
                if (tarifftablecursor != null && tarifftablecursor.moveToFirst()) {
                    tariffArraycode.clear();
                    tariffArrayname.clear();
                    tariffArraycode.add("select");
                    tariffArrayname.add("select");
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
                tariffAdapter = new ArrayAdapter<String>(Tab3.this, android.R.layout.simple_spinner_item, tariffArrayname);
                tariffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tariff_spinner.setAdapter(tariffAdapter);
                sqlAdapter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
