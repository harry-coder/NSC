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
public class SearchTicket_Feasibility extends Activity {
    EditText ticket_no;
    Button search;
    String str_ticket_no;
    String response;
    String consumer_name,consumer_add,str_feasibility_status,str_update_status;
    String network_interrupt=null;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_ticket_feasibility);
        ticket_no=(EditText)findViewById(R.id.ticket_no);
        search=(Button)findViewById(R.id.search);
        connectionDetector=new ConnectionDetector(SearchTicket_Feasibility.this);
        sessionManager= new SessionManager(SearchTicket_Feasibility.this);
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
        SearchTicket_Feasibility.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchTicket_Feasibility.this,R.style.MyAlertDialogStyle);
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
            pd=new ProgressDialog(SearchTicket_Feasibility.this,R.style.MyAlertDialogStyle);
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
            try
            {
                if(network_interrupt==null)
                {
                    response = response.trim();
                    Log.e("response", "" + response);
                    if (response.equalsIgnoreCase("0")) {
                        Toast.makeText(getApplicationContext(), "invalid ticket no", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        JSONObject user_info = new JSONObject(response);
                        JSONArray userinfo_array = user_info.getJSONArray("resp");
                        Log.e("array", "" + userinfo_array);
                        Log.e("array_lenght",""+userinfo_array.length());
                        for (int i = 0; i < userinfo_array.length(); i++) {
                            JSONObject c = userinfo_array.getJSONObject(i);
                             str_feasibility_status = c.getString("feasibility_status").trim();
                             str_update_status = c.getString("update_status").trim();
                             if(str_feasibility_status.equals("1"))
                            {
                                ShowAlertGetFeasibility();
                            }
                            else if(str_update_status.equals("0"))
                            {
                                ShowAlertGetConsumerUpdate();
                            }
                            else {

                                Intent intent = new Intent(SearchTicket_Feasibility.this, Feasibility.class);
                                intent.putExtra("ticket",str_ticket_no);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                }else
                {
                    ShowAlert();
                }
            }
            catch (Exception e)
            {
                Log.e(" final exception", ""+e.getMessage());
                ShowAlert();
            }
        }
    }
    public void ShowAlert() {
        SearchTicket_Feasibility.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchTicket_Feasibility.this,R.style.MyAlertDialogStyle);
                builder.setTitle("Ticket number not found. Please check");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ticket_no.setText("");
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
    public void ShowAlertGetFeasibility() {
        SearchTicket_Feasibility.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchTicket_Feasibility.this,R.style.MyAlertDialogStyle);
                builder.setTitle("Feasibility is Completed.");
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

    public void ShowAlertGetConsumerUpdate() {
        SearchTicket_Feasibility.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchTicket_Feasibility.this,R.style.MyAlertDialogStyle);
                builder.setTitle("Consumer Information is Not Completed.");
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