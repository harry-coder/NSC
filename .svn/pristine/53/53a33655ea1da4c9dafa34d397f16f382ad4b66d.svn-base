package feedback.mpnsc;

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
import android.widget.EditText;
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
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by swatiG on 23-06-2015.
 */
public class SearchTicket_FeasibilityTest extends Activity {
    EditText ticket_no;
    Button search;
    String str_ticket_no;
    String response;
    String consumer_name,consumer_add;
    String network_interrupt=null;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_ticket_feasibility);
        ticket_no=(EditText)findViewById(R.id.ticket_no);
        search=(Button)findViewById(R.id.search);
        connectionDetector=new ConnectionDetector(SearchTicket_FeasibilityTest.this);
        sessionManager= new SessionManager(SearchTicket_FeasibilityTest.this);
        if(android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_ticket_no=ticket_no.getText().toString().trim();
                DataHolderClass.getInstance().set_new_meter_ticket_no(str_ticket_no);
                if(TextUtils.isEmpty(str_ticket_no))
                {
                    Toast.makeText(getApplicationContext(),"enter ticket no", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack();
    }
    public void ShowAlertonBack(){
        SearchTicket_FeasibilityTest.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchTicket_FeasibilityTest.this);
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
    public  class SearchTicketNumber extends AsyncTask<String, String,String>
    {
        ProgressDialog pd;
        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd=new ProgressDialog(SearchTicket_FeasibilityTest.this);
            pd.setMessage("searching...");
            pd.setCancelable(false);
            pd.show();
        }
        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("tag", "get_feasibility"));
            nameValuePairs.add(new BasicNameValuePair("ticket_no", str_ticket_no));
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
            try {
                if(network_interrupt==null) {
                    response = response.trim();
                    Log.e("response", "" + response);

                    JSONObject user_info = new JSONObject(response);
                    JSONArray userinfo_array = user_info.getJSONArray("resp");
                    Log.e("array", "" + userinfo_array);
                    Log.e("array_lenght",""+userinfo_array.length());

                    if (response.equalsIgnoreCase("failed")) {
                        ShowAlertnotFoundTicket();
                       // Toast.makeText(getApplicationContext(), "invalid ticket no", Toast.LENGTH_SHORT).show();
                    } else if(userinfo_array.length()!=0) {
                          /*  //  Log.e("ticket no", str_ticket_no);
                            DataHolderClass.getInstance().set_new_meter_cons_name(consumer_name);
                            DataHolderClass.getInstance().set_new_meter_cons_add(consumer_add);
                            DataHolderClass.getInstance().set_new_meter_ticket_no(str_ticket_no);*/
                        for (int i = 0; i < userinfo_array.length(); i++)
                        {
                            JSONObject c = userinfo_array.getJSONObject(i);
                            String  Div_code = c.getString("Div_code");
                            String Sub_div_code = c.getString("Sub_div_code");
                            String  sec_code = c.getString("sec_code");
                            DataHolderClass.getInstance().set_feasibility_division(Div_code);
                            DataHolderClass.getInstance().set_feasibility_subdivision(Sub_div_code);
                            DataHolderClass.getInstance().set_feasibility_section(sec_code);
                        }
                            Intent intent = new Intent(SearchTicket_FeasibilityTest.this, Feasibility.class);
                            intent.putExtra("ticket",str_ticket_no);
                            startActivity(intent);
                            finish();
                        }else{ShowAlertnotFoundTicket();}
                    }else{
                    ShowAlertnotFoundTicket();
                 }
            }catch (Exception e)
            {
                Log.e(" final exception", ""+e.getMessage());
                ShowAlertnotFoundTicket();
            }
        }
    }
   /* public void ShowAlert() {
        SearchTicket_Feasibility.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchTicket_Feasibility.this);
                builder.setTitle("Do you want to...");
                builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new SearchTicketNumber().execute();
                    }
                });
                builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }*/
    public void ShowAlertnotFoundTicket() {
        SearchTicket_FeasibilityTest.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchTicket_FeasibilityTest.this);
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
}