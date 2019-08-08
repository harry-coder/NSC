package feedback.mpnsc.exisitingconsumer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import feedback.mpnsc.ConnectionDetector;
import feedback.mpnsc.DataHolderClass;
import feedback.mpnsc.R;
import feedback.mpnsc.SessionManager;
import feedback.mpnsc.TabBar;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by swatiG on 01-07-2015.
 */
public class ExistingConsumer_Search extends Activity {

    EditText ticket_no;
    Button search,newTicketstart;
    String str_ticket_no;
    String response;
    String consumer_name,consumer_add;
    String network_interrupt=null;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    HashMap<String,String> user_detail;
    CheckBox create_new_radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_ticket);
        ticket_no=(EditText)findViewById(R.id.ticket_no);
        search=(Button)findViewById(R.id.search);
        newTicketstart=(Button)findViewById(R.id.button7);
        connectionDetector=new ConnectionDetector(ExistingConsumer_Search.this);
        sessionManager= new SessionManager(ExistingConsumer_Search.this);
        create_new_radio=(CheckBox)findViewById(R.id.radioButton);
        user_detail=new HashMap<>();
        if(android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        newTicketstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(create_new_radio.isChecked()) {
                    Intent intent = new Intent(ExistingConsumer_Search.this, TabBar.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_ticket_no=ticket_no.getText().toString().trim();
                DataHolderClass.getInstance().set_new_meter_ticket_no(str_ticket_no);
                if(TextUtils.isEmpty(str_ticket_no))
                {
                    Toast.makeText(getApplicationContext(), "enter ticket no", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(connectionDetector.isConnectingToInternet()) {
                        new SearchTicketNumber().execute();
                    }else{
                        Toast.makeText(getApplicationContext(),"internet not connected", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }
    public  class SearchTicketNumber extends AsyncTask<String, String,String>
    {
        ProgressDialog pd;
        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd=new ProgressDialog(ExistingConsumer_Search.this,R.style.MyAlertDialogStyle);
            pd.setMessage("searching...");
            pd.setCancelable(false);
            pd.show();
        }
        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("tag","get_existing"));
            nameValuePairs.add(new BasicNameValuePair("ticket",str_ticket_no));
            Log.e("namepairvalue", "" + nameValuePairs);
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
                Log.e("log_tag", "Error in http connection "+e.toString());
                network_interrupt=e.getMessage();
            }
            return response;
        }
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pd.dismiss();
            pd.hide();
            String div_code,Sub_div_code,sec_code,NewFirstName,NewMiddleName,NewLastName,FatherName,PLOT,Building,Street,
                    District,Block,GP,Village,EmaiL_ID,LANDLINE_NUMBER,
                    LoadRequired,CustGroup,Mobile_number,PIN,HouseName,KhataNo,Ward,Address1,Address2,str_update_status;
            try {
                if(network_interrupt==null) {
                    response = response.trim();
                    Log.e("response", "" + response);
                    JSONObject user_info = new JSONObject(response);
                    JSONArray userinfo_array = user_info.getJSONArray("resp");
                    Log.e("array", "" + userinfo_array);
                    Log.e("array_lenght",""+userinfo_array.length());
                    for (int i = 0; i < userinfo_array.length(); i++) {
                        JSONObject c = userinfo_array.getJSONObject(i);

                        div_code = c.getString("Div_code");
                        user_detail.put("Div_code",div_code);
                        DataHolderExisting.getInstance().setDivision_code(div_code);

                        Sub_div_code = c.getString("Sub_div_code");
                        user_detail.put("Sub_div_code",Sub_div_code);
                        DataHolderExisting.getInstance().setSub_division_code(Sub_div_code);

                        sec_code = c.getString("sec_code");
                        user_detail.put("sec_code",sec_code);
                        DataHolderExisting.getInstance().setSection_code(sec_code);

                        NewFirstName = c.getString("NewFirstName");
                        user_detail.put("NewFirstName",NewFirstName);
                        DataHolderExisting.getInstance().setName(NewFirstName);

                        NewMiddleName = c.getString("middle_name");
                        user_detail.put("NewMiddleName",NewMiddleName);
                        DataHolderExisting.getInstance().setMiddle_name(NewMiddleName);

                        NewLastName = c.getString("last_name");
                        user_detail.put("NewLastName",NewLastName);
                        DataHolderExisting.getInstance().setLast_name(NewLastName);

                        FatherName = c.getString("FatherName");
                        user_detail.put("FatherName",FatherName);
                        DataHolderExisting.getInstance().setFather_husband_name(FatherName);

                        Building = c.getString("Building");
                        user_detail.put("Building",Building);
                        DataHolderExisting.getInstance().setBuilding(Building);

                        PLOT = c.getString("PLOT");
                        user_detail.put("PLOT",PLOT);
                        DataHolderExisting.getInstance().setPlot(PLOT);

                        HouseName = c.getString("HouseName");
                        user_detail.put("HouseName",HouseName);
                        DataHolderExisting.getInstance().setHousename(HouseName);

                        KhataNo = c.getString("KhataNo");
                        user_detail.put("KhataNo",KhataNo);
                        DataHolderExisting.getInstance().setKhatano(KhataNo);

                        Ward = c.getString("Ward");
                        user_detail.put("Ward",Ward);
                        DataHolderExisting.getInstance().setWard(Ward);

                        Street = c.getString("Street");
                        user_detail.put("Street",Street);
                        DataHolderExisting.getInstance().setStreet(Street);

                        Block = c.getString("Block");
                        user_detail.put("Block",Block);
                        DataHolderExisting.getInstance().setBlock(Block);

                        GP = c.getString("GP");
                        user_detail.put("GP",GP);
                        DataHolderExisting.getInstance().setGP(GP);

                        Address1 = c.getString("Address1");
                        user_detail.put("Address1",Address1);
                        DataHolderExisting.getInstance().setAddress1(Address1);

                        PIN = c.getString("PIN");
                        user_detail.put("PIN",PIN);
                        DataHolderExisting.getInstance().setpin(PIN);


                        Address2 = c.getString("Address2");
                        user_detail.put("Address2",Address2);
                        DataHolderExisting.getInstance().setAddress2(Address2);

                        Village = c.getString("village");
                        user_detail.put("Village",Village);
                        DataHolderExisting.getInstance().setVillage(Village);

                        District = c.getString("District");
                        user_detail.put("District",District);
                        DataHolderExisting.getInstance().setDistrict(District);

                        LANDLINE_NUMBER = c.getString("LANDLINE_NUMBER");
                        user_detail.put("LANDLINE_NUMBER",LANDLINE_NUMBER);
                        DataHolderExisting.getInstance().setLand_line_no(LANDLINE_NUMBER);

                        Mobile_number = c.getString("Mobile_number");
                        user_detail.put("Mobile_number",Mobile_number);
                        DataHolderExisting.getInstance().setMobile_no(Mobile_number);


                        EmaiL_ID = c.getString("EmaiL_ID");
                        user_detail.put("EmaiL_ID",EmaiL_ID);
                        DataHolderExisting.getInstance().setEmailid(EmaiL_ID);


                        LoadRequired = c.getString("LoadRequired");
                        user_detail.put("LoadRequired",LoadRequired);
                        System.out.println("ExistingLoadreq"+LoadRequired);
                        DataHolderExisting.getInstance().setLoadreq(LoadRequired);

                        CustGroup = c.getString("CustGroup");
                        user_detail.put("CustGroup",CustGroup);
                        DataHolderExisting.getInstance().setLoadcategory(CustGroup);

                        str_update_status = c.getString("update_status").trim();


                        Log.e("hasmap",""+user_detail);
                        Log.e("sec_code",sec_code);

                        if(str_update_status.equals("1"))
                        {
                            ShowAlertGetConsUpdation();
                        }else {
                            Intent intent = new Intent(ExistingConsumer_Search.this, Existing_tab1.class);
                            intent.putExtra("user_detail", user_detail);
                            intent.putExtra("div_code", div_code);
                            intent.putExtra("Sub_div_code", Sub_div_code);
                            intent.putExtra("sec_code", sec_code);
                            DataHolderClass.getInstance().set_ticket_no(str_ticket_no);
                            startActivity(intent);
                            finish();

                        }
                    }
                }else{
                    Log.e("network interuption",""+network_interrupt);
                    ShowAlert();
                }
            }catch (Exception e)
            {
                Log.e(" final exception", ""+e.getMessage());
                ShowAlert();
            }
        }
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack();
    }
    public void ShowAlertonBack(){
        ExistingConsumer_Search.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ExistingConsumer_Search.this,R.style.MyAlertDialogStyle);
                builder.setCancelable(false);
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
    public void ShowAlert() {
        ExistingConsumer_Search.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ExistingConsumer_Search.this,R.style.MyAlertDialogStyle);
                builder.setTitle("Ticket number not found. Please check");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                              AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }


    public void ShowAlertGetConsUpdation() {
        ExistingConsumer_Search.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ExistingConsumer_Search.this,R.style.MyAlertDialogStyle);
                builder.setTitle("Consumer Updation Details is Completed.");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}