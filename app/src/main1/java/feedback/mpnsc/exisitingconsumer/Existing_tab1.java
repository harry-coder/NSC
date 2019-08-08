package feedback.mpnsc.exisitingconsumer;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import feedback.mpnsc.DataHolderClass;
import feedback.mpnsc.R;
import feedback.mpnsc.SQLiteAdapter;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by swatiG on 02-07-2015.
 */
public class Existing_tab1 extends Activity {


    // Spinner cycle,route,ownerships,cons_type, department,ed_exception,meter_status;

    Spinner division_spinner,subdivision_spinner,section_spinner;
    String str_division,str_subdivion,str_section, str_cycle, str_route,
            str_ownerships, str_cons_type, str_department, str_ed_exception, str_meter_status;

    SQLiteAdapter sqlAdapter;

    //-----------------------    ArrayList   ---------------------------------//
    ArrayList<String> divisionArraycode,divisionArrayname;
    ArrayList<String> subdivisionArraycode,subdivisionArrayname;
    ArrayList<String> sectionArraycode,sectionArrayname;

    //------------------------ ArrayAdapter    ----------------------------//
    ArrayAdapter<String> divisionAdapter;
    ArrayAdapter<String> subdivisionAdapter;
    ArrayAdapter<String> sectionAdapter;
    //ArrayList<String> routeArraycode;
    //--------------------------  Cursor    -----------------------------//
    Cursor divisiontablecursor;
    Cursor subdivisiontablecursor;
    Cursor sectiontablecursor;
   // ArrayAdapter<String> routeAdapter;
    //------------------------- Add select   -----------------------------//

    HashSet< String> set,set1,set2,set3;
    //----------------------- spinner position  ------------------------------//
    String division_code,subdivision_code,section_code;

    Button submit;

    int int_division,int_subdivion,int_section, int_cycle, int_route,
            int_ownerships, int_cons_type;
    /** Called when the activity is first created. */
    String str_division_code,str_subdivision_code,str_sec_code;
    int count1=0, count=0,count2;
    boolean check=true;
    TextView TVTicketnum;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1);
        callView();
        TVTicketnum.setText(DataHolderClass.getInstance().get_new_meter_ticket_no());
        submit=(Button)findViewById(R.id.btn_submit);
        str_division_code=DataHolderExisting.getInstance().getDivision_code();
        str_subdivision_code=DataHolderExisting.getInstance().getSub_division_code();
        str_sec_code=DataHolderExisting.getInstance().getSection_code();
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

                str_division=division_spinner.getSelectedItem().toString();
                //str_subdivion=subdivision_spinner.getSelectedItem().toString();
                str_section=section_spinner.getSelectedItem().toString();
                Log.e("spinner tab",""+str_division+str_division+str_section+"");
                DataHolderClass dataHolderClass=DataHolderClass.getInstance();
                dataHolderClass.set_division(division_code);//str_division
                dataHolderClass.set_subdivision(subdivision_code);//str_subdivion
                dataHolderClass.set_section(section_code);//str_section

                if(division_spinner.getSelectedItemPosition() == 0 ||  section_spinner.getSelectedItemPosition() == 0 ) {

                    Toast.makeText(Existing_tab1.this, "Enter all mandatory fields", Toast.LENGTH_SHORT).show();


                } else {

                    DataHolderClass.getInstance().set_division(division_code);
                    DataHolderClass.getInstance().set_subdivision(subdivision_code);
                    DataHolderClass.getInstance().set_section(section_code);

                    Intent i = new Intent(Existing_tab1.this, Existing_CurrentDetailtest.class);
                    startActivity(i);

                }
               /* Intent i = new Intent(Existing_tab1.this, Existing_CurrentDetail.class);
                startActivity(i);*/


            }
        });

          new DIVISIONTABLEMANAGER(Existing_tab1.this).execute();
          division_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                //  Log.e("from","spinner1");
                division_spinner.setBackgroundColor(getResources().getColor(R.color.themecolor));
                if(count>1) {

                    str_division_code = divisionArraycode.get(position).toString();
                 //   Log.e("str_division_code",str_division_code);
//                  Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_LONG).show();
                   /* if (position > 0) {
                        new SUBDIVISIONTABLEMANAGER(Existing_tab1.this).execute();
                    } else {
                        subdivision_spinner.setAdapter(null);
                        section_spinner.setAdapter(null);
                        subdivisionArraycode.clear();
                        subdivisionArrayname.clear();
                        sectionArraycode.clear();
                        sectionArrayname.clear();
                    }*/
                    if (position > 0) {
                        new SECTIONTABLEMANAGER(Existing_tab1.this).execute();
                    } else {
                        section_spinner.setAdapter(null);
                        sectionArraycode.clear();
                        sectionArrayname.clear();
                    }
                }
                count++;
              //  Log.e("count",""+count);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });



       /* subdivision_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                if(count1>1) {
                    str_subdivision_code = subdivisionArraycode.get(position).toString();
                  //  Log.e("str_subdivision_code",str_subdivision_code);
//                Toast.makeText(getApplicationContext(),""+subdivision_code,Toast.LENGTH_LONG).show();
                    if (position > 0) {
                        new SECTIONTABLEMANAGER(Existing_tab1.this).execute();
                    } else {
                        section_spinner.setAdapter(null);
                        sectionArraycode.clear();
                        sectionArrayname.clear();
                    }
                   // count2
                }

               // Log.e("from","spinner2");
              count1++;
              //  Log.e("count1",""+count1);


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });*/
        section_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                str_sec_code=sectionArraycode.get(position).toString();
//                Toast.makeText(getApplicationContext(),""+section_code,Toast.LENGTH_LONG).show();
                if(position>0){

                }


              //  Log.e("from", "spinner3");

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
        Existing_tab1.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Existing_tab1.this,R.style.MyAlertDialogStyle);
                builder.setTitle("Are you sure to go back:");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Existing_tab1.this, ExistingConsumer_Search.class));
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
            pd = new ProgressDialog(Existing_tab1.this,R.style.MyAlertDialogStyle);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);pd.show();}

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            sqlAdapter=new SQLiteAdapter(Existing_tab1.this);
            try {
                sqlAdapter.openToRead();
                sqlAdapter.openToWrite();
                divisiontablecursor=sqlAdapter.masterdivisioncursorAll();
                if(divisiontablecursor!=null && divisiontablecursor.moveToFirst()){
                    divisionArraycode.add("---select---");
                    divisionArrayname.add("---select---");
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
                divisionAdapter=new ArrayAdapter<String>(Existing_tab1.this,android.R.layout.simple_spinner_item,divisionArrayname);
                divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                division_spinner.setBackgroundColor(getResources().getColor(R.color.themecolor));
                division_spinner.setAdapter(divisionAdapter);
                sqlAdapter.close();
                if(check) {
                  //  Log.e("str_division_code", "" + str_division_code);
                    int position = divisionArraycode.indexOf(str_division_code);
                   // Log.e("position", "" + position);
                    division_spinner.setSelection(position);

                   // new SUBDIVISIONTABLEMANAGER(Existing_tab1.this).execute();
                    new SECTIONTABLEMANAGER(Existing_tab1.this).execute();
                }


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
            super.onPreExecute();pd = new ProgressDialog(Existing_tab1.this);pd.setMessage("Please wait...");pd.setCancelable(false);pd.show();}
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                sqlAdapter.openToRead();
                sqlAdapter.openToWrite();
                if(subdivisiontablecursor!=null){
                    subdivisiontablecursor=null;
                }
              //  Log.e("str_sub_division",""+str_division_code);
                subdivisiontablecursor=sqlAdapter.subdivisionqueueOne(sqlAdapter.DIVISION_CODE + " = '" + str_division_code + "'");
                subdivisionArraycode.clear();
                subdivisionArrayname.clear();
                if(subdivisiontablecursor!=null && subdivisiontablecursor.moveToFirst()){
                    subdivisionArraycode.add("---select---");
                    subdivisionArrayname.add("---select---");
                    do {
                        String code=subdivisiontablecursor.getString(2);
                        String name=subdivisiontablecursor.getString(3);
                      //  Log.e(" sub division code",code);
                       // Log.e(" sub division name",name);
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
                subdivisionAdapter=new ArrayAdapter<String>(Existing_tab1.this,android.R.layout.simple_spinner_item,subdivisionArrayname);
                subdivisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subdivision_spinner.setAdapter(null);
                subdivision_spinner.setAdapter(subdivisionAdapter);
                sqlAdapter.close();
                   if(check) {
                      // Log.e("check", "" + check);
                       int position = subdivisionArraycode.indexOf(str_subdivision_code);
                    //   Log.e("position", "" + position);
                       subdivision_spinner.setSelection(position);
                      new SECTIONTABLEMANAGER(Existing_tab1.this).execute();
                   }
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
            super.onPreExecute();
            pd = new ProgressDialog(Existing_tab1.this,R.style.MyAlertDialogStyle);
            pd.setMessage("Please wait...");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(false);pd.show();
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                sqlAdapter.openToRead();
                sqlAdapter.openToWrite();
                if (sectiontablecursor != null) {
                    sectiontablecursor = null;
                }
                sectiontablecursor = sqlAdapter.sectionqueueOne(sqlAdapter.SUB_DIV_CODE + " = '" + str_division_code + "'");
                sectionArraycode.clear();
                sectionArrayname.clear();
                if (sectiontablecursor != null && sectiontablecursor.moveToFirst()) {
                    sectionArraycode.add("---select---");
                    sectionArrayname.add("---select---");
                    do {
                        String code = sectiontablecursor.getString(2);
                        String name = sectiontablecursor.getString(3);
                       // Log.e(" section code",code);
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
                sectionAdapter = new ArrayAdapter<String>(Existing_tab1.this, android.R.layout.simple_spinner_item, sectionArrayname);
                sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                section_spinner.setAdapter(sectionAdapter);
                sqlAdapter.close();
                   if(check) {
                      Log.e("str_section_code", "" + str_sec_code);
                       int position = sectionArraycode.indexOf(str_sec_code);
                     //  Log.e("position", "" + position);
                       section_spinner.setSelection(position);
                     //  Log.e("position double", "" + position);
                       check=false;
                   }



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void callView()
    {
       TextView TVTicketnum1 = (TextView) findViewById(R.id.textViewTicketValue);
        TVTicketnum = (TextView) findViewById(R.id.textViewTicket);
        division_spinner=(Spinner)findViewById(R.id.division_spinner);
       // subdivision_spinner=(Spinner)findViewById(R.id.spn_subdivion);
        section_spinner=(Spinner)findViewById(R.id.section);
       /* cycle=(Spinner)findViewById(R.id.spinner2);
        route=(Spinner)findViewById(R.id.spinner3);
        ownerships=(Spinner)findViewById(R.id.spinner4);
        cons_type=(Spinner)findViewById(R.id.spinner5);
        department=(Spinner)findViewById(R.id.spinner6);
        ed_exception=(Spinner)findViewById(R.id.spinner7);
        meter_status=(Spinner)findViewById(R.id.spinner8);*/
          submit=(Button)findViewById(R.id.submit);
    }
    @Override
    protected void onPause() {
        super.onPause();
       // Log.e("tab1", "onpause");
        getValidate();
    }



    public void getValue()
    {


        str_division=division_spinner.getSelectedItem().toString();
//        str_subdivion=subdivision_spinner.getSelectedItem().toString();
        str_section=section_spinner.getSelectedItem().toString();
       /* str_cycle=cycle.getSelectedItem().toString();
        str_route=route.getSelectedItem().toString();
        str_ownerships=ownerships.getSelectedItem().toString();
        str_cons_type=cons_type.getSelectedItem().toString();
        str_department=department.getSelectedItem().toString();
        str_ed_exception=ed_exception.getSelectedItem().toString();
        str_meter_status=meter_status.getSelectedItem().toString();*/

     //   Log.e("spinner tab",""+str_division+str_division+str_section+"");


        DataHolderClass dataHolderClass=DataHolderClass.getInstance();
        dataHolderClass.set_division(str_division_code);//str_division
        dataHolderClass.set_subdivision(str_subdivision_code);//str_subdivion
        dataHolderClass.set_section(str_sec_code);//str_section
        dataHolderClass.set_cycle(str_cycle);
        dataHolderClass.set_route(str_route);
        dataHolderClass.set_ownerships(str_ownerships);
        dataHolderClass.set_cons_type(str_cons_type);
        dataHolderClass.set_department(str_department);
        dataHolderClass.set_ed_exception(str_ed_exception);
        dataHolderClass.set_meter_status(str_meter_status);

    }

    public void getValidate()
    {

        int_division=division_spinner.getSelectedItemPosition();
        //int_subdivion=subdivision_spinner.getSelectedItemPosition();
        int_section=section_spinner.getSelectedItemPosition();
        /*int_cycle=cycle.getSelectedItemPosition();
        int_route=route.getSelectedItemPosition();
        int_ownerships=ownerships.getSelectedItemPosition();
        int_cons_type=cons_type.getSelectedItemPosition();*/

      //  Log.e("postion",""+int_division+int_subdivion+int_section+int_cycle+int_route+int_ownerships+int_cons_type+"");
      //  Log.e("postion",""+int_division+int_subdivion+int_section+"");
       /* if((int_division==0)||(int_subdivion==0)||(int_section==0)||
                (int_cycle==0)||(int_route==0)||(int_ownerships==0)||(int_cons_type==0))*/


        //if((int_division==0)||(int_subdivion==0)||(int_section==0))

        /*{   Log.e("from","if");
            division_spinner.setBackgroundColor(Color.YELLOW);
            subdivision_spinner.setBackgroundColor(Color.YELLOW);
            section_spinner.setBackgroundColor(Color.YELLOW);
            *//*cycle.setBackgroundColor(Color.YELLOW);
            route.setBackgroundColor(Color.YELLOW);
            ownerships.setBackgroundColor(Color.YELLOW);
            cons_type.setBackgroundColor(Color.YELLOW);*//*
            Toast.makeText(getApplicationContext(), "Enter mandatory fields HIRARCHY", Toast.LENGTH_SHORT).show();



        }
        else{
            getValue();
            Log.e("from","else");
        }*/

        if(int_division==0){ division_spinner.setBackgroundColor(getResources().getColor(R.color.edittexttheme));}
       // else if (int_subdivion==0){subdivision_spinner.setBackgroundColor(Color.YELLOW); }
        else if (int_section==0){section_spinner.setBackgroundColor(getResources().getColor(R.color.edittexttheme));}
        else if(int_division!=0 || int_section !=0){
            getValue();
        }
    }
}