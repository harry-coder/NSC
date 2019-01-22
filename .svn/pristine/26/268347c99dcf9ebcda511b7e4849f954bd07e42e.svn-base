package feedback.mpnsc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MeterImage extends Activity {
    // ..................................................Camera Variables................................//

    byte[] byteArray;
    Button btn_submit;
    Button btn_photograph1;
    private ImageView mImageView;
    private Bitmap mImageBitmap;
    String image;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;

    private static final String IMAGE_DIRECTORY_NAME = "NSC";
    public Uri fileUri;
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
        if (MeterImage.this.getPackageManager().hasSystemFeature(
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
        setContentView(R.layout.meterimage);
        connectionDetector=new ConnectionDetector(this);
        sessionManager=new SessionManager(MeterImage.this);
        sqLiteMasterTableAdapter= new SQLiteMasterTableAdapter(MeterImage.this);
        sqLiteMultipleTicketList=new MultipleTicketManager(MeterImage.this);
        mImageView = (ImageView) findViewById(R.id.imageView1);
        mImageBitmap = null;
        checkbox_person_avaiable=(CheckBox)findViewById(R.id.checkBox2);
        btn_photograph1 = (Button) findViewById(R.id.imageButton1);
        btn_submit=(Button)findViewById(R.id.button1);
        checkbox_person_avaiable.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox_person_avaiable.isChecked())
                {DataHolderClass.getInstance().set_person_available("unavailable");
                    image=null;
                    meterimageName=null;
                    mImageView.setVisibility(View.INVISIBLE);
                    mImageView.setVisibility(View.GONE);
                    btn_photograph1.setClickable(false);}
                else{
                    DataHolderClass.getInstance().set_person_available("available");
                    mImageView.setVisibility(View.VISIBLE);
                    btn_photograph1.setClickable(true);
                }
            }
        });
        btn_submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DataHolderClass.getInstance().get_person_available() == "available" && image == null){
                    Toast.makeText(getApplicationContext(),"capture the image",Toast.LENGTH_SHORT).show();
                }else{
                    if (connectionDetector.isConnectingToInternet()) {
                        //send data
                        new SendToServer().execute();
                    } else {
                        sqLiteMasterTableAdapter.openToRead();
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
                                        DataHolderClass.getInstance().get_pin_no1()


                                );
                        Toast.makeText(getApplicationContext(), "Record Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }


                }
            }
        });
        btn_photograph1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                captureImage();
            }
        });
        if(!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),"Camera not supported",Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent intent) {
        // Choose what to do based on the request code
        switch (requestCode) {
            // If the request code matches the code sent in onConnectionFailed
            case CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        previewCapturedImage();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(MeterImage.this,
                                "Err:463" + "\n" + "Image cancelled",
                                Toast.LENGTH_SHORT).show();
                }
                break;

            // If any other request code was received

        }
    }
    // ==============================================Find Image start===================================//
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        URI=fileUri.toString();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fileUri = savedInstanceState.getParcelable("file_uri");
        try {
            Bitmap bitmap1 = null;
            try {
                bitmap1 = getPortraitViewBitmap(fileUri.getPath());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mImageBitmap = bitmap1;
            image = BitMapToString(mImageBitmap);
            mImageView.setImageBitmap(bitmap1);
            mImageView.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    private void previewCapturedImage() {
        try {

            Bitmap bitmap1 = null;
            try {
                bitmap1 = getPortraitViewBitmap(fileUri.getPath());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mImageBitmap = bitmap1;
            image = BitMapToString(mImageBitmap);
            byteArray=getByteArray(mImageBitmap);
            mImageView.setImageBitmap(bitmap1);
            mImageView.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            meterimageName = timeStamp+".jpg";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }
    public Bitmap getPortraitViewBitmap(String filePath) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap resizedBitmap = BitmapFactory.decodeFile(filePath, options);
//                Log.e("IMAGE",""+String.valueOf(byteArray));
        ExifInterface exif = new ExifInterface(filePath);
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString)
                : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
            rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
            rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
            rotationAngle = 270;
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) resizedBitmap.getWidth() / 2,
                (float) resizedBitmap.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(resizedBitmap, 0, 0,
                resizedBitmap.getWidth(), resizedBitmap.getHeight(), matrix,
                true);
        return rotatedBitmap;
    }
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++Change Image into Bitmap++++++++++++++++++++++++++++++++++//
    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        bmeter = baos.toByteArray();
        String strBitMap = Base64.encodeToString(bmeter, Base64.DEFAULT);
               /* String bitma=String.valueOf(bitmap);
                Log.e("STRING",String.valueOf(bitmap));
                String strBitMap=new String(bmeter,StandardCharsets.);
                Log.e("IMAGEIMAGE",strBitMap);*/
        return strBitMap;
    }


    public byte[] getByteArray(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bos);
        return bos.toByteArray();
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        Debug.stopMethodTracing();
        super.onDestroy();
    }
    public class SendToServer extends AsyncTask<String, String,String>
    {    String network_interrupt=null;
        ProgressDialog pd;
        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd=new ProgressDialog(MeterImage.this);
            pd.setMessage("record sending");
            pd.setCancelable(false);
            pd.show();
        }
        @Override
        protected String doInBackground(String... params) {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("tag","set_meter"));
            nameValuePairs.add(new BasicNameValuePair("ticket_no",DataHolderClass.getInstance().getTicket_no()));
            nameValuePairs.add(new BasicNameValuePair("home_lat",DataHolderClass.getInstance().getHome_lat()));
            nameValuePairs.add(new BasicNameValuePair("home_long",DataHolderClass.getInstance().getHome_long()));
            nameValuePairs.add(new BasicNameValuePair("pole_lat",DataHolderClass.getInstance().getPole_lat()));
            nameValuePairs.add(new BasicNameValuePair("pole_long",DataHolderClass.getInstance().getPole_long()));

            nameValuePairs.add(new BasicNameValuePair("meter_no",DataHolderClass.getInstance().getStr_meter_no()));
            nameValuePairs.add(new BasicNameValuePair("seal_no",DataHolderClass.getInstance().getStr_seal_number()));
            nameValuePairs.add(new BasicNameValuePair("seal_no1",DataHolderClass.getInstance().getStr_seal1()));
            nameValuePairs.add(new BasicNameValuePair("seal_no2",DataHolderClass.getInstance().getStr_seal2()));
            nameValuePairs.add(new BasicNameValuePair("seal_no3",DataHolderClass.getInstance().getStr_seal3()));
            nameValuePairs.add(new BasicNameValuePair("seal_no4",DataHolderClass.getInstance().getStr_seal4()));
            nameValuePairs.add(new BasicNameValuePair("seal_no5",DataHolderClass.getInstance().getStr_seal5()));
            nameValuePairs.add(new BasicNameValuePair("date",DataHolderClass.getInstance().getStr_date()));
            nameValuePairs.add(new BasicNameValuePair("meter_digit",DataHolderClass.getInstance().getStr_meter_digit()));
            nameValuePairs.add(new BasicNameValuePair("reading",DataHolderClass.getInstance().getStr_reading()));
            nameValuePairs.add(new BasicNameValuePair("meter_make",DataHolderClass.getInstance().getStr_meter_make()));
            nameValuePairs.add(new BasicNameValuePair("meter_type",DataHolderClass.getInstance().getStr_meter_type()));
            nameValuePairs.add(new BasicNameValuePair("meter_phase",DataHolderClass.getInstance().getStr_meter_phase()));
            nameValuePairs.add(new BasicNameValuePair("meter_image_name",meterimageName));
            nameValuePairs.add(new BasicNameValuePair("meter_image",image));

            Log.e("namevaluepair",""+nameValuePairs);
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
                network_interrupt=e.getMessage().toString();
                Log.e("log_tag", "Error in http connection "+e.toString());
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pd.dismiss();
            pd.hide();
            try {
                if(network_interrupt==null) {

                    response = response.trim();
                    Log.e("response",response);
                    if (response.equalsIgnoreCase("1")) {
                        Toast.makeText(getApplicationContext(), "record send successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MeterImage.this, Options.class));
                        finish();
                    } else {
                        ShowAlert();

                    }



                }else{
                    /*sqLiteMasterTableAdapter.openToRead();
                    sqLiteMasterTableAdapter.openToWrite();
                    sqLiteMasterTableAdapter.insert_feasibility("set_feasibility", str_ticket_no,
                            String.valueOf(home_lat),
                            String.valueOf(home_long),
                            String.valueOf(pole_lat),
                            String.valueOf(pole_long),
                            str_route,
                            value_feasibility,str_manual_fes
                    );
                    sqLiteMasterTableAdapter.close();*/
                    Toast.makeText(getApplicationContext(),"Record not send due to internet connectivity", Toast.LENGTH_SHORT).show();
                    finish();


                }
            } catch (Exception e) {
                ShowAlert();
            }


        }
        public void ShowAlert() {
            MeterImage.this.runOnUiThread(new Runnable() {
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MeterImage.this);
                    builder.setTitle("Do you want to...");
                    builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            new SendToServer().execute();
                        }
                    });
                    builder.setNegativeButton("Record not send due to internet connectivity", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(),"Record not send due to internet connectivity",Toast.LENGTH_SHORT).show();
                            sqLiteMasterTableAdapter.openToRead();
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
                                            DataHolderClass.getInstance().get_pin_no1());

                            Toast.makeText(getApplicationContext(),"Record not send due to internet connectivity",Toast.LENGTH_LONG).show();
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
        MeterImage.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MeterImage.this);
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
