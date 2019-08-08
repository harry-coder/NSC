package feedback.mpnsc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
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

/**
 * Created by nitinb on 09-02-2016.
 */
public class SiteInspection_Activity2 extends Activity {

    ConnectionDetector connectionDetector;

    SQLiteMasterTableAdapter sqLiteMasterTableAdapter;
    MultipleTicketManager sqLiteMultipleTicketList;

    String response;
    SessionManager sessionManager;

    Button next_btn;
    Button back_btn;
    Spannable tv_division,tv_dc,tv_name,tv_fanme,tv_address,tv_landmark,tv_Mobile;

    TextView txt_divison,txt_dc,txt_Name,txt_address,txt_fname,txt_landmark,txt_Mobile;
    Spinner spinner_project, spinner_geographic, spinner_subarea_one, spinner_subarea_two, spinner_subarea_three, spinner_inspection;

    ArrayList<String> project_code_list,project_name_list,project_id_list;
    ArrayList<String> geographic_project_code_list,geographic_name_list,geographic_code_list;
    ArrayList<String> subareaone_geographic_code_list,subareaone_name_list,subareaone_code_list;
    ArrayList<String> subareatwo_one_code_list,subareatwo_name_list,subareatwo_code_list;
    ArrayList<String> subareathree_two_code_list,subareathree_name_list,subareathree_code_list;
    ArrayList<String> inspection_id_list,inspection_name_list,inspection_code_list;


    ArrayAdapter<String> project_adapter,geographic_adapter,subareaone_adapter,subareatwo_adapter,subareathree_adapter,inspection_adapter;

    String project_code, project_id, geographic_project_code, geographic_id,geographic_name,
            subareaone_geographic_code,subarea_one_code,subarea_one_name,subareatwo_one_code,subarea_two_code,subarea_two_name,
            subareathree_two_code,subarea_three_code,subarea_three_name,inspection_id;

    Cursor project_cursor,geographic_cursor,subareaone_cursor,subareatwo_cursor,subareathree_cursor,inspection_cursor;
    SQLiteAdapter sqLiteAdapter;

    String georaphiclevel, level1, level2, level3, level4;
    EditText ed_name,ed_address,ed_phone,ed_father,ed_landmark;
    String str_name,str_address,str_phone,str_father,str_landmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_ticket_number);

        connectionDetector=new ConnectionDetector(this);
        sessionManager=new SessionManager(SiteInspection_Activity2.this);
        sqLiteMasterTableAdapter= new SQLiteMasterTableAdapter(SiteInspection_Activity2.this);
        sqLiteMultipleTicketList=new MultipleTicketManager(SiteInspection_Activity2.this);
        spinner_project = (Spinner) findViewById(R.id.division_spinner);
        spinner_geographic = (Spinner) findViewById(R.id.spn_subdivion);
        next_btn=(Button)findViewById(R.id.tv_button);
        txt_divison=(TextView)findViewById(R.id.textView);
        txt_dc=(TextView)findViewById(R.id.tv_section);
        txt_Name=(TextView)findViewById(R.id.tv_name);
        txt_address=(TextView)findViewById(R.id.tv_address);
        txt_fname=(TextView)findViewById(R.id.tv_father);
        txt_landmark=(TextView)findViewById(R.id.tv_landmark);
        txt_Mobile=(TextView)findViewById(R.id.tv_mobile);

        spinner_geographic.setVisibility(View.GONE);

        spinner_subarea_one = (Spinner) findViewById(R.id.section);
        spinner_subarea_one.setVisibility(View.GONE);

        ed_name=(EditText)findViewById(R.id.et_name);
        ed_address=(EditText)findViewById(R.id.et_address);
        ed_father=(EditText)findViewById(R.id.et_father);
        ed_landmark=(EditText)findViewById(R.id.et_landmark);
        ed_phone=(EditText)findViewById(R.id.et_mobile);

          try {
              tv_division = new SpannableString(txt_divison.getText().toString());
              tv_dc = new SpannableString(txt_dc.getText().toString());
              tv_name = new SpannableString(txt_Name.getText().toString());
              tv_fanme = new SpannableString(txt_fname.getText().toString());
              tv_address = new SpannableString(txt_address.getText().toString());
              tv_landmark = new SpannableString(txt_landmark.getText().toString());
              tv_Mobile = new SpannableString(txt_Mobile.getText().toString());
              tv_division.setSpan(new ForegroundColorSpan(Color.RED), 8, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
              tv_dc.setSpan(new ForegroundColorSpan(Color.RED), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
              tv_name.setSpan(new ForegroundColorSpan(Color.RED), 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
              tv_fanme.setSpan(new ForegroundColorSpan(Color.RED), 23, 24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
              tv_address.setSpan(new ForegroundColorSpan(Color.RED), 7, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
              tv_landmark.setSpan(new ForegroundColorSpan(Color.RED), 8, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
              tv_Mobile.setSpan(new ForegroundColorSpan(Color.RED), 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
              txt_divison.setText(tv_division);
              txt_dc.setText(tv_dc);
              txt_Name.setText(tv_name);
              txt_fname.setText(tv_fanme);
              txt_address.setText(tv_address);
              txt_landmark.setText(tv_landmark);
              txt_Mobile.setText(tv_Mobile);
              ed_name.addTextChangedListener(new TextWatcher() {
                  @Override
                  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  }
                  @Override
                  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                      ed_name.setBackgroundColor(getResources().getColor(R.color.themecolor));

                  }

                  @Override
                  public void afterTextChanged(Editable editable) {
                      ed_name.setBackgroundColor(getResources().getColor(R.color.themecolor));
                  }
              });
              ed_address.addTextChangedListener(new TextWatcher() {
                  @Override
                  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  }
                  @Override
                  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                      ed_address.setBackgroundColor(getResources().getColor(R.color.themecolor));

                  }

                  @Override
                  public void afterTextChanged(Editable editable) {
                      ed_address.setBackgroundColor(getResources().getColor(R.color.themecolor));
                  }
              });
              ed_father.addTextChangedListener(new TextWatcher() {
                  @Override
                  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  }
                  @Override
                  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                      ed_father.setBackgroundColor(getResources().getColor(R.color.themecolor));

                  }

                  @Override
                  public void afterTextChanged(Editable editable) {
                      ed_father.setBackgroundColor(getResources().getColor(R.color.themecolor));
                  }
              });
              ed_landmark.addTextChangedListener(new TextWatcher() {
                  @Override
                  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  }
                  @Override
                  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                      ed_landmark.setBackgroundColor(getResources().getColor(R.color.themecolor));

                  }

                  @Override
                  public void afterTextChanged(Editable editable) {
                      ed_landmark.setBackgroundColor(getResources().getColor(R.color.themecolor));
                  }
              });
              ed_phone.addTextChangedListener(new TextWatcher() {
                  @Override
                  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  }
                  @Override
                  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                      ed_phone.setBackgroundColor(getResources().getColor(R.color.themecolor));

                  }

                  @Override
                  public void afterTextChanged(Editable editable) {
                      ed_phone.setBackgroundColor(getResources().getColor(R.color.themecolor));
                  }
              });

          }catch (Exception ex)
          {
              System.out.print(ex.toString());
          }



      //        back button initialized


        project_code_list=new ArrayList<String>();
        project_name_list=new ArrayList<String>();
        project_id_list=new ArrayList<String>();

        geographic_name_list=new ArrayList<String>();
        geographic_code_list=new ArrayList<String>();

        subareaone_name_list=new ArrayList<String>();
        subareaone_code_list=new ArrayList<String>();

        subareatwo_name_list=new ArrayList<String>();
        subareatwo_code_list=new ArrayList<String>();


        new Project_Value(SiteInspection_Activity2.this).execute();
        //new Inspection_Value(SiteInspection_Activity2.this).execute();

        spinner_project.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                spinner_project.setBackgroundColor(getResources().getColor(R.color.themecolor));
                //project_code = project_code_list.get(position).toString();
                project_id = project_id_list.get(position).toString();

//                  Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_LONG).show();
             /*   if (position > 0) {
                    new Geographic_Value(SiteInspection_Activity2.this).execute();

                } else {

                    spinner_geographic.setAdapter(null);
                    geographic_code_list.clear();
                    geographic_name_list.clear();

                }*/
                if (position > 0) {
                    new SubAreaOne_Value(SiteInspection_Activity2.this).execute();
                } else {
                    spinner_subarea_one.setVisibility(View.GONE);
                    //spinner_subarea_one.setAdapter(null);
                    //subareaone_geographic_code_list.clear();
                    subareaone_code_list.clear();
                    subareaone_name_list.clear();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });



        spinner_geographic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                //geographic_project_code=geographic_project_code_list.get(position).toString();
                geographic_id=geographic_code_list.get(position).toString();
                geographic_name=geographic_name_list.get(position).toString();
//                Toast.makeText(getApplicationContext(),""+subdivision_code,Toast.LENGTH_LONG).show();

                if (position > 0) {
                    new SubAreaOne_Value(SiteInspection_Activity2.this).execute();
                } else {
                    spinner_subarea_one.setVisibility(View.GONE);
                    //spinner_subarea_one.setAdapter(null);
                    //subareaone_geographic_code_list.clear();
                    subareaone_code_list.clear();
                    subareaone_name_list.clear();

                }



            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        spinner_subarea_one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                spinner_subarea_one.setBackgroundColor(getResources().getColor(R.color.themecolor));
                //subareaone_geographic_code = subareaone_geographic_code_list.get(position).toString();
                subarea_one_code=subareaone_code_list.get(position).toString();
                subarea_one_name=subareaone_name_list.get(position).toString();
//                Toast.makeText(getApplicationContext(),""+subdivision_code,Toast.LENGTH_LONG).show();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });







        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_name = ed_name.getText().toString().trim();
                str_address=ed_address.getText().toString().trim();
                str_father = ed_father.getText().toString().trim();
                str_landmark=ed_landmark.getText().toString().trim();
                str_phone=ed_phone.getText().toString().trim();

                DataHolderClass.getInstance().set_name(str_name);



                if(spinner_project.getSelectedItemPosition() == 0){ spinner_project.setBackgroundColor(Color.parseColor("#412b55"));}
                else if (spinner_geographic.getSelectedItemPosition() == 0){spinner_geographic.setBackgroundColor(Color.parseColor("#412b55")); }
                else if (spinner_subarea_one.getSelectedItemPosition() == 0 ){spinner_subarea_one.setBackgroundColor(Color.parseColor("#412b55"));}
                else if (str_name.equals("")){
                    ed_name.setBackgroundColor(Color.parseColor("#412b55"));
                }else if (str_father.equals("")){
                    ed_father.setBackgroundColor(Color.parseColor("#412b55"));
                }else if (str_address.equals("")){
                    ed_address.setBackgroundColor(Color.parseColor("#412b55"));
                }else if (str_landmark.equals("")){
                    ed_landmark.setBackgroundColor(Color.parseColor("#412b55"));
                }else if (str_phone.equals("") || str_phone.length()!=10){
                    ed_phone.setBackgroundColor(Color.parseColor("#412b55"));
                }else if((Integer.parseInt(str_phone.substring(0,1)) < 7) || (Integer.parseInt(str_phone.substring(0,1)) > 9)){
                    Toast.makeText(getApplicationContext(), "Mobile number must be start form 7,8,9 Number", Toast.LENGTH_SHORT).show();
                }else
                {
                    if (connectionDetector.isConnectingToInternet()) {
                        //send data
                        new SendToServer().execute();
                    } else {
                        Toast.makeText(getApplicationContext(),"Data Not sending to server",Toast.LENGTH_SHORT).show();
                    }

                }
              /*  if (spinner_project.getSelectedItemPosition() == 0 ||
                        spinner_geographic.getSelectedItemPosition() == 0 ||
                        spinner_subarea_one.getSelectedItemPosition() == 0 ) {
                    Toast.makeText(SiteInspection_Activity2.this, "Enter all mandatory fields", Toast.LENGTH_SHORT).show();



                }

                else
                         {
                           startActivity(new Intent(SiteInspection_Activity2.this, Options.class));

                }*/
            }
        });


    }


    @Override
    public void onBackPressed() {
        finish(); // finish activity
        //this.overridePendingTransition(R.anim.right_in, R.anim.left_out);

    }

    //...........Project Class...........................................//
    public class Project_Value extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        Context _context;
        Project_Value(Context ctx) { _context=ctx;}

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            // ShowAlertagain();
            super.onPreExecute();
            pd = new ProgressDialog(SiteInspection_Activity2.this,R.style.MyAlertDialogStyle);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();}

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            sqLiteAdapter=new SQLiteAdapter(SiteInspection_Activity2.this);
            try {
                sqLiteAdapter.openToRead();
                sqLiteAdapter.openToWrite();
                project_cursor=sqLiteAdapter.masterdivisioncursorAll();
                if(project_cursor!=null && project_cursor.moveToFirst()){
                    project_name_list.add("Select Division ");
                    project_id_list.add("Select Division ");
                    do {
                        String project_id=project_cursor.getString(2);
                        String project_name=project_cursor.getString(3);
                       /* Log.e("dist_code",dist_code);
                        Log.e("dist_name",dist_name);*/
                        project_id_list.add(project_id);
                        project_name_list.add(project_name);
                    } while (project_cursor.moveToNext());
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
                project_adapter=new ArrayAdapter<String>(SiteInspection_Activity2.this,android.R.layout.simple_spinner_item,project_name_list);
                project_adapter.setDropDownViewResource(R.layout.spinner_item);
                spinner_project.setAdapter(project_adapter);
                sqLiteAdapter.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    //.....................Geographic Area.................................................//

    public class Geographic_Value extends AsyncTask<String, String, String> {
        ProgressDialog pd;Context _context;
        Geographic_Value(Context ctx) {_context=ctx;}
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(SiteInspection_Activity2.this,R.style.MyAlertDialogStyle);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();}

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {


                sqLiteAdapter.openToRead();
                sqLiteAdapter.openToWrite();
                if(geographic_cursor!=null){
                    geographic_cursor=null;
                }

                geographic_cursor=sqLiteAdapter.subdivisionqueueOne(sqLiteAdapter.DIVISION_CODE + " = '" + project_id + "'");

                geographic_code_list.clear();
                geographic_name_list.clear();
                if(geographic_cursor!=null && geographic_cursor.moveToFirst()){
                    geographic_code_list.add("Select SubDivision");
                    geographic_name_list.add("Select SubDivision ");

                    do {
                        //String geographic_project_code=geographic_cursor.getString(1);
                        String geographic_code=geographic_cursor.getString(2);
                        String geographic_name=geographic_cursor.getString(3);
                       /* Log.e("code",code);
                        Log.e("name",name);*/
                       // geographic_project_code_list.add(geographic_project_code);
                        geographic_code_list.add(geographic_code);
                        geographic_name_list.add(geographic_name);
                    } while (geographic_cursor.moveToNext());
                }
            }catch (Exception e){
                e.printStackTrace();
                // Log.e("exception",e.getMessage());
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
                spinner_geographic.setVisibility(View.VISIBLE);
                geographic_adapter=new ArrayAdapter<String>(SiteInspection_Activity2.this,android.R.layout.simple_spinner_item,geographic_name_list);
                geographic_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               /* geographic_adapter = new ArrayAdapter<String>(
                        getApplicationContext(), R.layout.custom_spinner, R.id.textView1,geographic_name_list);*/

                spinner_geographic.setAdapter(geographic_adapter);
                sqLiteAdapter.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    //..................... SubAreaOne_Value .................................................//

    public class SubAreaOne_Value extends AsyncTask<String, String, String> {
        ProgressDialog pd;Context _context;
        SubAreaOne_Value(Context ctx) {_context=ctx;}
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(SiteInspection_Activity2.this,R.style.MyAlertDialogStyle);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();}

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                sqLiteAdapter.openToRead();
                sqLiteAdapter.openToWrite();
                if(subareaone_cursor!=null){
                    subareaone_cursor=null;
                }

                subareaone_cursor = sqLiteAdapter.sectionqueueOne(sqLiteAdapter.SUB_DIV_CODE + " = '" + project_id + "'");
                subareaone_code_list.clear();
                subareaone_name_list.clear();
                if(subareaone_cursor!=null && subareaone_cursor.moveToFirst()){
                    //subareaone_geographic_code_list.add("Select ");
                    subareaone_code_list.add("Select DC ");
                    subareaone_name_list.add("Select DC ");
                    do {
                        //String subareaone_geographic_code=subareaone_cursor.getString(1);
                        String subarea_one_code=subareaone_cursor.getString(2);
                        String subarea_one_name=subareaone_cursor.getString(3);
                       /* Log.e("code",code);
                        Log.e("name",name);*/

                        subareaone_code_list.add(subarea_one_code);
                        subareaone_name_list.add(subarea_one_name);

                    } while (subareaone_cursor.moveToNext());
                }
            }catch (Exception e){
                e.printStackTrace();
                // Log.e("exception",e.getMessage());
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

                spinner_subarea_one.setVisibility(View.VISIBLE);
                subareaone_adapter=new ArrayAdapter<String>(SiteInspection_Activity2.this,android.R.layout.simple_spinner_item,subareaone_name_list);
              //  subareaone_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subareaone_adapter.setDropDownViewResource(R.layout.spinner_item);
                spinner_subarea_one.setAdapter(subareaone_adapter);

                sqLiteAdapter.close();
            }catch (Exception e){
                e.printStackTrace();
            }
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
            pd=new ProgressDialog(SiteInspection_Activity2.this,R.style.MyAlertDialogStyle);
            pd.setMessage("record sending");
            pd.setCancelable(false);
            pd.show();
        }
        @Override
        protected String doInBackground(String... params)
        {
            try{
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("division", project_id));
                nameValuePairs.add(new BasicNameValuePair("subdivision", "0"));
                nameValuePairs.add(new BasicNameValuePair("section", subarea_one_code));

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
            SiteInspection_Activity2.this.runOnUiThread(new Runnable() {
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SiteInspection_Activity2.this);
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
        SiteInspection_Activity2.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(SiteInspection_Activity2.this,R.style.MyAlertDialogStyle);
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
