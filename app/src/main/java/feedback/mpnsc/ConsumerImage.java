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
import android.widget.Spinner;
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

public class ConsumerImage extends Activity {

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
    public String FTPURL = "ftp://103.251.62.42/sushil/";
    static String empid, c_code;
    String URI;

    CheckBox checkbox_person_avaiable;
    ConnectionDetector connectionDetector;

    SQLiteMasterTableAdapter sqLiteMasterTableAdapter;
    MultipleTicketManager sqLiteMultipleTicketList;

    DataHolderClass dataHolderClass;

    String response;
    SessionManager sessionManager;

    ImageView im_back;

    private boolean isDeviceSupportCamera() {
        if (ConsumerImage.this.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            return true;

        } else {
            return false;
        }
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.consumerimage);
        connectionDetector = new ConnectionDetector(this);
        sessionManager = new SessionManager(ConsumerImage.this);
        sqLiteMasterTableAdapter = new SQLiteMasterTableAdapter(ConsumerImage.this);
        sqLiteMultipleTicketList = new MultipleTicketManager(ConsumerImage.this);
        mImageView = (ImageView) findViewById(R.id.imageView1);
        mImageBitmap = null;
        checkbox_person_avaiable = (CheckBox) findViewById(R.id.checkBox2);
        btn_photograph1 = (Button) findViewById(R.id.imageButton1);
        btn_submit = (Button) findViewById(R.id.button1);
        checkbox_person_avaiable.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox_person_avaiable.isChecked()) {
                    DataHolderClass.getInstance().set_person_available("unavailable");
                    image = null;
                    meterimageName = null;
                    mImageView.setVisibility(View.INVISIBLE);
                    mImageView.setVisibility(View.GONE);
                    btn_photograph1.setClickable(false);
                } else {
                    DataHolderClass.getInstance().set_person_available("available");
                    mImageView.setVisibility(View.VISIBLE);
                    btn_photograph1.setClickable(true);
                }
            }
        });

        btn_submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                DataHolderClass.getInstance().setConsumer_image(image);
                DataHolderClass.getInstance().setConsumer_image_name(meterimageName);

                if (DataHolderClass.getInstance().get_person_available() == "available" && image == null) {
                    Toast.makeText(getApplicationContext(), "capture the image", Toast.LENGTH_SHORT).show();
                } else {
                    if (connectionDetector.isConnectingToInternet()) {
                        //send data
                        new SendToServer().execute();
                    } else {

                        //Toast.makeText(getApplicationContext(), "Record Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }


                }

                //onBackPressed();
                //startActivity(new Intent(LandImage.this, Feasibility_photo.class));

            }
        });
        btn_photograph1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                captureImage();

            }
        });
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(), "Camera not supported", Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }

        im_back=findViewById ( R.id.im_back );
        im_back.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                ShowAlertonBack ();
            }
        } );


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // Choose what to do based on the request code
        switch (requestCode) {
            // If the request code matches the code sent in onConnectionFailed
            case CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        previewCapturedImage();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(ConsumerImage.this,
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
        URI = fileUri.toString();
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
            mImageBitmap = getResizedBitmap(bitmap1, 80, 100);
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
            byteArray = getByteArray(mImageBitmap);
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
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);
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
            meterimageName = timeStamp + ".jpg";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
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


    public void show_ticket(String ticketNumber) {
        ConsumerImage.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConsumerImage.this,R.style.MyAlertDialogStyle);
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack();
    }

    public void ShowAlertonBack() {
        ConsumerImage.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConsumerImage.this,R.style.MyAlertDialogStyle);
                builder.setCancelable(false);
                builder.setTitle("Are you sure to go back:");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent ( ConsumerImage.this, Feasibility_photo.class );
                        intent.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        startActivity ( intent );


                        finish ( );

                        //onBackPressed ();
                        //startActivity(new Intent(ConsumerImage.this, Feasibility_photo.class));
                       // finish();

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


    public class SendToServer extends AsyncTask<String, String, String> {
        String network_interrupt = null;
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(ConsumerImage.this,R.style.MyAlertDialogStyle);
            pd.setMessage("record sending");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("tag","set_consumer"));
            nameValuePairs.add(new BasicNameValuePair("TicketNumber",DataHolderClass.getInstance().getTicket_no()));
            nameValuePairs.add(new BasicNameValuePair("ImageName",meterimageName));
            nameValuePairs.add(new BasicNameValuePair("cimage",image));





            Log.e("namevaluepair",""+nameValuePairs);
            try
            {
                HttpClient httpclient = new DefaultHttpClient();

            //    HttpPost httppost = new HttpPost(sessionManager.GET_URL());

                HttpPost httppost = new HttpPost ( "http://wcrm.fedco.co.in/phedapi/nscapi/set_consumer" );

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
                        startActivity(new Intent(ConsumerImage.this, Feasibility_photo.class));
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
                    Toast.makeText(getApplicationContext(),"Record not send due to internet interruption", Toast.LENGTH_SHORT).show();
                    finish();


                }
            } catch (Exception e) {
                ShowAlert();
            }


        }



    }


    public void ShowAlert() {
        ConsumerImage.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConsumerImage.this,R.style.MyAlertDialogStyle);
                builder.setTitle("Do you want to...");
                builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new SendToServer ().execute();
                    }
                });
                builder.setNegativeButton("Record not send due to internet interruption", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        /*sqLiteMasterTableAdapter.openToRead();
                        sqLiteMasterTableAdapter.openToWrite();
                        sqLiteMasterTableAdapter.insert_feasibility("tag", "str_ticket_no",
                                String.valueOf(home_lat),
                                String.valueOf(home_long),
                                String.valueOf(pole_lat),
                                String.valueOf(pole_long),
                                str_route,
                                value_feasibility,str_manual_fes
                        );
                        sqLiteMasterTableAdapter.close();*/
                        Toast.makeText(getApplicationContext(),"Record not send due to internet interruption", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

}
