package feedback.mpnsc;

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

import feedback.mpnsc.exisitingconsumer.ExistingConsumer_Search;


public class NewTicketNumber extends Activity {


    Spinner division_spinner,subdivision_spinner,section_spinner;
    String str_division,str_subdivion,str_section,str_routes;

    SQLiteAdapter sqlAdapter;

    //-----------------------    ArrayList   ---------------------------------//
    ArrayList<String> divisionArraycode,divisionArrayname;
    ArrayList<String> subdivisionArraycode,subdivisionArrayname;
    ArrayList<String> sectionArraycode,sectionArrayname;
   // ArrayList<String> routeArraycode;
    //------------------------ ArrayAdapter    ----------------------------//
    ArrayAdapter<String> divisionAdapter;
    ArrayAdapter<String> subdivisionAdapter;
    ArrayAdapter<String> sectionAdapter;
   // ArrayAdapter<String> routeAdapter;

    //--------------------------  Cursor    -----------------------------//
    Cursor divisiontablecursor;
    Cursor subdivisiontablecursor;
    Cursor sectiontablecursor;
    //------------------------- Add select   -----------------------------//

    HashSet< String> set,set1,set2,set3;
    //----------------------- spinner position  ------------------------------//
    String division_code,subdivision_code,section_code,route_code;

   Button submit;

   int int_division,int_subdivion,int_section, int_cycle, int_route,
           int_ownerships, int_cons_type;
	/** Called when the activity is first created. */

    EditText ed_name,ed_address,ed_phone,ed_father,ed_landmark;
    String str_name,str_address,str_phone,str_father,str_landmark;

    //TextView use for asterick color
    TextView txt_division,txt_dc,txt_Name,txt_fname,txt_address,txt_landmark,txt_mobileNo;

    ConnectionDetector connectionDetector;

    SQLiteMasterTableAdapter sqLiteMasterTableAdapter;
    MultipleTicketManager sqLiteMultipleTicketList;

    String response;
    SessionManager sessionManager;
    //SQLiteAdapter sqlAdapter;
	  

    @Override
	public void onCreate(Bundle savedInstanceState) {

	      super.onCreate(savedInstanceState);
          setContentView(R.layout.new_ticket_number);

        connectionDetector=new ConnectionDetector(this);
        sessionManager=new SessionManager(NewTicketNumber.this);
        sqLiteMasterTableAdapter= new SQLiteMasterTableAdapter(NewTicketNumber.this);
        sqLiteMultipleTicketList=new MultipleTicketManager(NewTicketNumber.this);

        division_spinner=(Spinner)findViewById(R.id.division_spinner);
        subdivision_spinner=(Spinner)findViewById(R.id.spn_subdivion);
        section_spinner=(Spinner)findViewById(R.id.section);

        ed_name=(EditText)findViewById(R.id.et_name);
        ed_address=(EditText)findViewById(R.id.et_address);
        ed_father=(EditText)findViewById(R.id.et_father);
        ed_landmark=(EditText)findViewById(R.id.et_landmark);
        ed_phone=(EditText)findViewById(R.id.et_mobile);
        submit=(Button)findViewById(R.id.tv_button);
          //callView();
          divisionArraycode=new ArrayList<String>();
          divisionArrayname=new ArrayList<String>();
          subdivisionArrayname=new ArrayList<String>();
          subdivisionArraycode=new ArrayList<String>();
          sectionArrayname=new ArrayList<String>();
          sectionArraycode=new ArrayList<String>();
        // routeArraycode=new ArrayList<String>();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //str_phone=ed_phone.getText().toString().trim();

                getValidate();
              /*  str_division=division_spinner.getSelectedItem().toString();
                str_subdivion=subdivision_spinner.getSelectedItem().toString();
                str_section=section_spinner.getSelectedItem().toString();
                str_name = ed_name.getText().toString().trim();
                str_address=ed_address.getText().toString().trim();
                str_phone=ed_phone.getText().toString().trim();

               Log.e("spinner tab",""+str_division+str_division+str_section+"");

                DataHolderClass dataHolderClass=DataHolderClass.getInstance();
                dataHolderClass.set_division(division_code);//str_division
                dataHolderClass.set_subdivision(subdivision_code);//str_subdivion
                dataHolderClass.set_section(section_code);//str_section*/

try{


    int result = Integer.parseInt(str_phone.substring(0,1));
    if ((result < 7) || (result > 9)) {
        Toast.makeText(NewTicketNumber.this, "Mobile number must be start form 7,8,9 Number", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), "Mobile number must be start form 7,8,9 Number", Toast.LENGTH_SHORT).show();
    } else {

        if (connectionDetector.isConnectingToInternet()) {
            //send data
            new SendToServer().execute();
        } else {
            Toast.makeText(getApplicationContext(),"Data Not sending to server",Toast.LENGTH_SHORT).show();
        }
    }

} catch (RuntimeException e) {
    System.out.print("RuntimeException: ");
    System.out.println(e.getMessage());
} finally {
    System.out.println("try-block entered.");
}


                     //getValidate();


               // getValidate();






            }
        });
          new DIVISIONTABLEMANAGER(NewTicketNumber.this).execute();
         division_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> parent, View view,
                                         int position, long id) {
                  // TODO Auto-generated method stub
                  division_code=divisionArraycode.get(position).toString();
//                  Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_LONG).show();
                  if(position>0){
                      new SUBDIVISIONTABLEMANAGER(NewTicketNumber.this).execute();
                  }else{

                      subdivision_spinner.setAdapter(null);
                      section_spinner.setAdapter(null);
                      subdivisionArraycode.clear();
                      subdivisionArrayname.clear();
                      sectionArraycode.clear();
                      sectionArrayname.clear();
                  }


              }
              @Override
              public void onNothingSelected(AdapterView<?> parent) {
                  // TODO Auto-generated method stub
              }
          });
        subdivision_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                subdivision_code=subdivisionArraycode.get(position).toString();
//                Toast.makeText(getApplicationContext(),""+subdivision_code,Toast.LENGTH_LONG).show();
                if(position>0){
                    new SECTIONTABLEMANAGER(NewTicketNumber.this).execute();
                }else{
                    section_spinner.setAdapter(null);
                    sectionArraycode.clear();
                    sectionArrayname.clear();
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        section_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                section_code=sectionArraycode.get(position).toString();
               //Toast.makeText(getApplicationContext(),""+section_code, Toast.LENGTH_LONG).show();
                if(position>0){
              //   new RouteValue(Tab1.this).execute();

                }else{
                   /* route_spinner.setAdapter(null);
                    routeArraycode.clear();
*/
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack();
    }
    public void ShowAlertonBack(){
        NewTicketNumber.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewTicketNumber.this);
                builder.setTitle("Are you sure to go back:");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(NewTicketNumber.this, ExistingConsumer_Search.class));
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
    public class DIVISIONTABLEMANAGER extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        Context _context;
        DIVISIONTABLEMANAGER(Context ctx) { _context=ctx;}

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            // ShowAlertagain();
            super.onPreExecute();
            pd = new ProgressDialog(NewTicketNumber.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);pd.show();}

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            sqlAdapter=new SQLiteAdapter(NewTicketNumber.this);
            try {
                sqlAdapter.openToRead();
                sqlAdapter.openToWrite();
                divisiontablecursor=sqlAdapter.masterdivisioncursorAll();
                if(divisiontablecursor!=null && divisiontablecursor.moveToFirst()){
                    divisionArraycode.add("select");
                    divisionArrayname.add("select");
                    do {
                        String scheme=divisiontablecursor.getString(2);
                        String schemename=divisiontablecursor.getString(3);
                        divisionArraycode.add(scheme);
                        divisionArrayname.add(schemename);
                    } while (divisiontablecursor.moveToNext());
                }
            }catch (Exception e){
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
                divisionAdapter=new ArrayAdapter<String>(NewTicketNumber.this,android.R.layout.simple_spinner_item,divisionArrayname);
                divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                division_spinner.setAdapter(divisionAdapter);
                sqlAdapter.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public class SUBDIVISIONTABLEMANAGER extends AsyncTask<String, String, String> {
        ProgressDialog pd;Context _context;
        SUBDIVISIONTABLEMANAGER(Context ctx) {_context=ctx;}
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();pd = new ProgressDialog(NewTicketNumber.this);pd.setMessage("Please wait...");pd.setCancelable(false);pd.show();}
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                sqlAdapter.openToRead();
                sqlAdapter.openToWrite();
                if(subdivisiontablecursor!=null){
                    subdivisiontablecursor=null;
                }
                subdivisiontablecursor=sqlAdapter.subdivisionqueueOne(sqlAdapter.DIVISION_CODE + " = '" + division_code + "'");
               /* subdivisionArraycode.clear();
                subdivisionArrayname.clear();*/
                if(subdivisiontablecursor!=null && subdivisiontablecursor.moveToFirst()){
                    subdivisionArraycode.add("select");
                    subdivisionArrayname.add("select");
                    do {
                        String code=subdivisiontablecursor.getString(2);
                        String name=subdivisiontablecursor.getString(3);
                        subdivisionArraycode.add(code);
                        subdivisionArrayname.add(name);
                    } while (subdivisiontablecursor.moveToNext());
                }
            }catch (Exception e){
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
                subdivisionAdapter=new ArrayAdapter<String>(NewTicketNumber.this,android.R.layout.simple_spinner_item,subdivisionArrayname);
                subdivisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subdivision_spinner.setAdapter(subdivisionAdapter);
                sqlAdapter.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public class SECTIONTABLEMANAGER extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        Context _context;
        SECTIONTABLEMANAGER(Context ctx) {_context = ctx;}
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
             super.onPreExecute();pd = new ProgressDialog(NewTicketNumber.this);pd.setMessage("Please wait...");pd.setCancelable(false);pd.show();}
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                sqlAdapter.openToRead();
                sqlAdapter.openToWrite();
                if (sectiontablecursor != null) {
                    sectiontablecursor = null;
                }
                sectiontablecursor = sqlAdapter.sectionqueueOne(sqlAdapter.SUB_DIV_CODE + " = '" + subdivision_code + "'");
                sectionArraycode.clear();
                sectionArrayname.clear();
                if (sectiontablecursor != null && sectiontablecursor.moveToFirst()) {
                    sectionArraycode.add("select");
                    sectionArrayname.add("select");
                    do {
                        String code = sectiontablecursor.getString(2);
                        String name = sectiontablecursor.getString(3);
                        sectionArraycode.add(code);
                        sectionArrayname.add(name);
                    } while (sectiontablecursor.moveToNext());
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
                sectionAdapter = new ArrayAdapter<String>(NewTicketNumber.this, android.R.layout.simple_spinner_item, sectionArrayname);
                sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                section_spinner.setAdapter(sectionAdapter);
                sqlAdapter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void callView()
    {
        division_spinner=(Spinner)findViewById(R.id.division_spinner);
        subdivision_spinner=(Spinner)findViewById(R.id.spn_subdivion);
        section_spinner=(Spinner)findViewById(R.id.section);

        submit=(Button)findViewById(R.id.tv_button);
    }
    @Override
     protected void onPause() {
         super.onPause();
         Log.e("tab1", "onpause");
         //getValidate();
     }
    public void getValue()
    {


        str_division=division_spinner.getSelectedItem().toString();
        str_subdivion=subdivision_spinner.getSelectedItem().toString();
        str_section=section_spinner.getSelectedItem().toString();
        str_name = ed_name.getText().toString().trim();
        str_address=ed_address.getText().toString().trim();
        str_father = ed_father.getText().toString().trim();
        str_landmark=ed_landmark.getText().toString().trim();
        str_phone=ed_phone.getText().toString().trim();

        Log.e("spinner tab",""+str_division+str_division+str_section+"");


        DataHolderClass dataHolderClass=DataHolderClass.getInstance();
        dataHolderClass.set_division(division_code);//str_division
        dataHolderClass.set_subdivision(subdivision_code);//str_subdivion
        dataHolderClass.set_section(section_code);//str_section

    }

    public void getValidate()
    {

        getValue();
        int_division=division_spinner.getSelectedItemPosition();
        int_subdivion=subdivision_spinner.getSelectedItemPosition();
        int_section=section_spinner.getSelectedItemPosition();

        /*str_division=division_spinner.getSelectedItem().toString();
        str_subdivion=subdivision_spinner.getSelectedItem().toString();
        str_section=section_spinner.getSelectedItem().toString();*/
        str_name = ed_name.getText().toString().trim();
        str_address=ed_address.getText().toString().trim();
        str_phone=ed_phone.getText().toString().trim();
        str_father = ed_father.getText().toString().trim();
        str_landmark=ed_landmark.getText().toString().trim();


       /* Log.e("postion",""+int_division+int_subdivion+int_section+int_cycle+int_route+int_ownerships+int_cons_type+"");
        Log.e("postion",""+int_division+int_subdivion+int_section+"");*/

            if(int_division==0){ division_spinner.setBackgroundColor(Color.YELLOW);}
            else if (int_subdivion==0){subdivision_spinner.setBackgroundColor(Color.YELLOW); }
            else if (int_section==0){section_spinner.setBackgroundColor(Color.YELLOW);}
            else if (str_name.equals("")){
                ed_name.setBackgroundColor(Color.YELLOW);
            }else if (str_father.equals("")){
                ed_father.setBackgroundColor(Color.YELLOW);
            }else if (str_address.equals("")){
                ed_address.setBackgroundColor(Color.YELLOW);
            }else if (str_landmark.equals("")){
                ed_landmark.setBackgroundColor(Color.YELLOW);
            }else if (str_phone.equals("") || str_phone.length()!=10){
                ed_phone.setBackgroundColor(Color.YELLOW);
            }

    }


    public  void send_data(){

        if (connectionDetector.isConnectingToInternet()) {
        //send data
        new SendToServer().execute();
    } else {
        Toast.makeText(getApplicationContext(),"Data Not sending to server",Toast.LENGTH_SHORT).show();
    }

    }




    public class SendToServer extends AsyncTask<String, String,String>
    {    String network_interrupt=null;
        ProgressDialog pd;
        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd=new ProgressDialog(NewTicketNumber.this);
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
                nameValuePairs.add(new BasicNameValuePair("first_name", str_name));
                nameValuePairs.add(new BasicNameValuePair("middle_name", DataHolderClass.getInstance().getStr_middle_name()));
                nameValuePairs.add(new BasicNameValuePair("last_name", DataHolderClass.getInstance().getStr_last_name()));
                nameValuePairs.add(new BasicNameValuePair("father_name", str_father));

                nameValuePairs.add(new BasicNameValuePair("building_no", DataHolderClass.getInstance().get_bulding_no()));
                nameValuePairs.add(new BasicNameValuePair("house_flatno", DataHolderClass.getInstance().getHouse_flatno()));
                nameValuePairs.add(new BasicNameValuePair("housename", DataHolderClass.getInstance().getHousename()));
                nameValuePairs.add(new BasicNameValuePair("khatano", DataHolderClass.getInstance().getKhata_no()));
                nameValuePairs.add(new BasicNameValuePair("ward_no", DataHolderClass.getInstance().getWard_no()));
                nameValuePairs.add(new BasicNameValuePair("street", DataHolderClass.getInstance().get_street()));
                nameValuePairs.add(new BasicNameValuePair("block", DataHolderClass.getInstance().get_block()));
                nameValuePairs.add(new BasicNameValuePair("panchayat", DataHolderClass.getInstance().get_gp()));
                nameValuePairs.add(new BasicNameValuePair("address", str_address));
                nameValuePairs.add(new BasicNameValuePair("pin_no", DataHolderClass.getInstance().get_pin_no()));
                nameValuePairs.add(new BasicNameValuePair("landmark", str_landmark));

                nameValuePairs.add(new BasicNameValuePair("village", DataHolderClass.getInstance().get_city()));
                nameValuePairs.add(new BasicNameValuePair("district", DataHolderClass.getInstance().get_district()));
                nameValuePairs.add(new BasicNameValuePair("telephone", DataHolderClass.getInstance().get_landline()));
                nameValuePairs.add(new BasicNameValuePair("mobile",str_phone));
                nameValuePairs.add(new BasicNameValuePair("email",DataHolderClass.getInstance().get_email()));
                nameValuePairs.add(new BasicNameValuePair("category",""));
                nameValuePairs.add(new BasicNameValuePair("get_connect_load",""));

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
            NewTicketNumber.this.runOnUiThread(new Runnable() {
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewTicketNumber.this);
                    builder.setTitle("Do you want to...");
                    builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            new  SendToServer().execute();
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
        NewTicketNumber.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewTicketNumber.this);
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


}