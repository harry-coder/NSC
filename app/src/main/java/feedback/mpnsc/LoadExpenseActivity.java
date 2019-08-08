package feedback.mpnsc;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import feedback.mpnsc.databinding.ActivityLoadExpenseBinding;

public class LoadExpenseActivity extends AppCompatActivity {

    EditText et_bulbNo, et_bulbEnergy, et_fanNo, et_fanEnergy, et_fridgeNo, et_fridgeEnergy, et_kettleNo, et_kettleEnergy, et_ironNo, et_ironEnergy,
            et_tvNo, et_tvEnergy, et_videoSetNo, et_videoSetEnergy, et_soundSetNo, et_soundSetEnergy, et_acNo, et_acEnergy, et_machineNo, et_machineEnergy,
            et_pumpNo, et_pumpEnergy, et_otherNo, et_otherEnergy;

    Button bt_submit;
    ArrayList <String> charList;
    ArrayList <String> numberList, eneryList;
    String ticketNo;
    ActivityLoadExpenseBinding binding;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        requestWindowFeature ( Window.FEATURE_NO_TITLE );
        getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );


        binding = DataBindingUtil.setContentView ( this, R.layout.activity_load_expense );

        charList = new ArrayList <> ( );
        numberList = new ArrayList <> ( );
        eneryList = new ArrayList <> ( );

        ticketNo = getIntent ( ).getExtras ( ).getString ( "ticketNo" );

        binding.textViewTicketValue.setText ( "Ticket No." + ticketNo );

        progressDialog = new ProgressDialog ( LoadExpenseActivity.this, R.style.MyAlertDialogStyle );


        binding.btnSubmit.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                saveData ( );

            }
        } );

    }


    public void saveData() {
        String alpha = "abcdefghijklmnopqrstuvwxyz";

        numberList.add ( binding.etBulbNo.getText ( ).toString ( ) );
        eneryList.add ( binding.etBulbEnergy.getText ( ).toString ( ) );

        numberList.add ( binding.etFanNo.getText ( ).toString ( ) );
        eneryList.add ( binding.etFanEnergy.getText ( ).toString ( ) );

        numberList.add ( binding.etFridgeNo.getText ( ).toString ( ) );
        eneryList.add ( binding.etFridgeEnergy.getText ( ).toString ( ) );

        numberList.add ( binding.etKettleNumber.getText ( ).toString ( ) );
        eneryList.add ( binding.etKettleEnergy.getText ( ).toString ( ) );

        numberList.add ( binding.etIronNo.getText ( ).toString ( ) );
        eneryList.add ( binding.etIronEnergy.getText ( ).toString ( ) );

        numberList.add ( binding.etTvNo.getText ( ).toString ( ) );
        eneryList.add ( binding.etTvEnergy.getText ( ).toString ( ) );

        numberList.add ( binding.etVideoSetNo.getText ( ).toString ( ) );
        eneryList.add ( binding.etVideoSetEnergy.getText ( ).toString ( ) );

        numberList.add ( binding.etSountSetNo.getText ( ).toString ( ) );
        eneryList.add ( binding.etSoundSetEnergy.getText ( ).toString ( ) );

        numberList.add ( binding.etAcNo.getText ( ).toString ( ) );
        eneryList.add ( binding.etAcEnergy.getText ( ).toString ( ) );

        numberList.add ( binding.etMachineNo.getText ( ).toString ( ) );
        eneryList.add ( binding.etMachineEnergy.getText ( ).toString ( ) );

        numberList.add ( binding.etPumpNo.getText ( ).toString ( ) );
        eneryList.add ( binding.etPumpEnergy.getText ( ).toString ( ) );

        numberList.add ( binding.etOtherNo.getText ( ).toString ( ) );
        eneryList.add ( binding.etOtherEnergy.getText ( ).toString ( ) );

        progressDialog.setMessage ( "Sending Data" );
        progressDialog.show ( );
        for (int i = 0; i < 11; i++) {

            charList.add ( "" + alpha.charAt ( i ) );

            new SendToServer ( ).execute ( "" + alpha.charAt ( i ), numberList.get ( i ), eneryList.get ( i ) );

        }


    }


    class SendToServer extends AsyncTask <String, String, String> {
        String network_interrupt = null;
        ProgressDialog pd;
        String response;

        String sn;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute ( );
/*
            pd.setMessage ( "record sending" );
            pd.setCancelable ( false );
            pd.show ( );
*/


        }

        @Override
        protected String doInBackground(String... params) {
            try {


                sn = params[0];
                System.out.println ( "This is char  " + params[0] );
                System.out.println ( "This is number  " + params[1] );
                System.out.println ( "This is energy  " + params[2] );


                ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
                // nameValuePairs.add(new BasicNameValuePair("tag",  "update_consumer"));


                nameValuePairs.add ( new BasicNameValuePair ( "TICKETNO", ticketNo ) );
                nameValuePairs.add ( new BasicNameValuePair ( "SN", params[0] ) );


                if (TextUtils.isEmpty ( params[1] )) {
                    nameValuePairs.add ( new BasicNameValuePair ( "NOS", "0" ) );
                } else {
                    nameValuePairs.add ( new BasicNameValuePair ( "NOS", params[1] ) );

                }
                if (TextUtils.isEmpty ( params[2] )) {
                    nameValuePairs.add ( new BasicNameValuePair ( "KWH", "0" ) );
                } else {
                    nameValuePairs.add ( new BasicNameValuePair ( "KWH", params[2] ) );

                }


                Log.e ( "values", "" + nameValuePairs );
                HttpClient httpclient = new DefaultHttpClient ( );

                //HttpPost httppost = new HttpPost(sessionManager.GET_URL());
               // HttpPost httppost = new HttpPost ( "http://wcrm.fedco.co.in/phedapi/nscapi/setLoadExpenses" );
               HttpPost httppost = new HttpPost ( "http://dlenhanceuat.phed.com.ng/dlenhanceapi/NscApi/setLoadExpenses" );


                // HttpPost httppost = new HttpPost("http://sbm.fieldpm.com/sb/handset_reading");
                httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
                ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
                response = httpclient.execute ( httppost, responseHandler );
                Log.e ( "Response7", response );


                System.out.println ( "Inside the records back" );

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
          /*  pd.dismiss ( );
            pd.hide ( );
          */
            try {

                // System.out.println ( "This is the result " + response );

                if (network_interrupt == null) {
                    //   Log.e("network_interrupt",network_interrupt);

                    String networkResult = result.replace ( "\"", "" );
                    System.out.println ( this.getClass ().getName ()+"This is the result async " + networkResult );

                    try {
                        if (networkResult.trim ( ).equals ( "1" )) {
                            //        Log.e("RESPONSE1",response);

                            if (sn.equalsIgnoreCase ( "k" )) {

                                progressDialog.dismiss ( );
                                Toast.makeText ( getApplicationContext ( ), "Data Sent Successfully", Toast.LENGTH_SHORT ).show ( );
                                Intent intent = new Intent ( LoadExpenseActivity.this, Feasibility_photo.class );
                                startActivity ( intent );
                                finish ( );
//
                            }

                        } else {

                            Toast.makeText ( LoadExpenseActivity.this, "Something went wrong try again 123", Toast.LENGTH_SHORT ).show ( );
                            //    Log.e("Response2", "->" + response);
                            // ShowAlert ( );
                            //    Log.e("RESPONSE8",""+response.length());

                        }
                    } catch (Exception e) {
                        //   Log.e("Response3", "->" + e);
                    }
                } else {
                    Toast.makeText ( LoadExpenseActivity.this, "Something went wrong try again network", Toast.LENGTH_SHORT ).show ( );

                    //ShowAlert ( );

                }
            } catch (Exception e) {
            }
        }

        public void ShowAlert() {
            LoadExpenseActivity.this.runOnUiThread ( new Runnable ( ) {
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder ( LoadExpenseActivity.this, R.style.MyAlertDialogStyle );
                    builder.setTitle ( "Do you want to..." );
                    builder.setPositiveButton ( "Try Again", new DialogInterface.OnClickListener ( ) {
                        public void onClick(DialogInterface dialog, int id) {
                            new SendToServer ( ).execute ( );
                        }
                    } );

                    AlertDialog alert = builder.create ( );
                    alert.show ( );
                }
            } );
        }
    }
}
