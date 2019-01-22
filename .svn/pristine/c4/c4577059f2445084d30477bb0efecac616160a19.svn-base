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
import android.widget.Spinner;
import android.widget.Toast;

import feedback.mpnsc.exisitingconsumer.ExistingConsumer_Search;

import java.util.ArrayList;
import java.util.HashSet;


public class Tab1 extends Activity {


   // Spinner cycle,route,ownerships,cons_type, department,ed_exception,meter_status;

    Spinner division_spinner,subdivision_spinner,section_spinner,route_spinner;
    String str_division,str_subdivion,str_section, str_cycle, str_route,
           str_ownerships, str_cons_type, str_department, str_ed_exception, str_meter_status;

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
    Cursor routetablecursor;
    //------------------------- Add select   -----------------------------//

    HashSet< String> set,set1,set2,set3;
    //----------------------- spinner position  ------------------------------//
    String division_code,subdivision_code,section_code,route_code;

   Button submit;

   int int_division,int_subdivion,int_section, int_cycle, int_route,
           int_ownerships, int_cons_type;
	/** Called when the activity is first created. */
	  

    @Override
	public void onCreate(Bundle savedInstanceState) {

	      super.onCreate(savedInstanceState);
          setContentView(R.layout.tab1);
          callView();
          divisionArraycode=new ArrayList<String>();
          divisionArrayname=new ArrayList<String>();
          subdivisionArrayname=new ArrayList<String>();
          subdivisionArraycode=new ArrayList<String>();
          sectionArrayname=new ArrayList<String>();
          sectionArraycode=new ArrayList<String>();
        // routeArraycode=new ArrayList<String>();
          new DIVISIONTABLEMANAGER(Tab1.this).execute();
         division_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> parent, View view,
                                         int position, long id) {
                  // TODO Auto-generated method stub
               //   division_code.setBackgroundColor(getResources().getColor(R.color.themecolor));
                  division_code=divisionArraycode.get(position).toString();
//                  Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_LONG).show();
                  if(position>0){
                      new SUBDIVISIONTABLEMANAGER(Tab1.this).execute();
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
                    new SECTIONTABLEMANAGER(Tab1.this).execute();
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
                section_spinner.setBackgroundColor(getResources().getColor(R.color.themecolor));
                section_code=sectionArraycode.get(position).toString();
               Toast.makeText(getApplicationContext(),""+section_code, Toast.LENGTH_LONG).show();
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
        Tab1.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Tab1.this);
                builder.setTitle("Are you sure to go back:");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Tab1.this, ExistingConsumer_Search.class));
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
            pd = new ProgressDialog(Tab1.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);pd.show();}

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            sqlAdapter=new SQLiteAdapter(Tab1.this);
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
                divisionAdapter=new ArrayAdapter<String>(Tab1.this,android.R.layout.simple_spinner_item,divisionArrayname);
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
            super.onPreExecute();pd = new ProgressDialog(Tab1.this);pd.setMessage("Please wait...");pd.setCancelable(false);pd.show();}
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
                subdivisionArraycode.clear();
                subdivisionArrayname.clear();
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
                subdivisionAdapter=new ArrayAdapter<String>(Tab1.this,android.R.layout.simple_spinner_item,subdivisionArrayname);
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
             super.onPreExecute();pd = new ProgressDialog(Tab1.this);pd.setMessage("Please wait...");pd.setCancelable(false);pd.show();}
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
                sectionAdapter = new ArrayAdapter<String>(Tab1.this, android.R.layout.simple_spinner_item, sectionArrayname);
                sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                section_spinner.setAdapter(sectionAdapter);
                sqlAdapter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

   /* public  class RouteValue extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        Context _context;
        RouteValue(Context ctx) {_context = ctx;}
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();pd = new ProgressDialog(Tab1.this);pd.setMessage("Please wait...");pd.setCancelable(false);pd.show();}
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                sqlAdapter.openToRead();
                sqlAdapter.openToWrite();
                if (routetablecursor != null) {
                    routetablecursor = null;
                }
                routetablecursor = sqlAdapter.routequeueOne(sqlAdapter.SECTIONID + " = '" + section_code + "'");
                routeArraycode.clear();
               // sectionArrayname.clear();
                if (routetablecursor != null && routetablecursor.moveToFirst()) {
                    routeArraycode.add("select");
                   // sectionArrayname.add("select");
                    do {
                        String code = routetablecursor.getString(1);
                        Log.e("route value", ""+code);
                       // String name = sectiontablecursor.getString(3);
                        routeArraycode.add(code);
                       // sectionArrayname.add(name);
                    } while (routetablecursor.moveToNext());
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
                routeAdapter = new ArrayAdapter<String>(Tab1.this, android.R.layout.simple_spinner_item, routeArraycode);
                sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                route_spinner.setAdapter(routeAdapter);
                sqlAdapter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/
    public void callView()
    {
        division_spinner=(Spinner)findViewById(R.id.division_spinner);
        subdivision_spinner=(Spinner)findViewById(R.id.spn_subdivion);
        section_spinner=(Spinner)findViewById(R.id.section);
       // route_spinner=(Spinner)findViewById(R.id.spinner_route);
       /* cycle=(Spinner)findViewById(R.id.spinner2);
        route=(Spinner)findViewById(R.id.spinner3);
        ownerships=(Spinner)findViewById(R.id.spinner4);
        cons_type=(Spinner)findViewById(R.id.spinner5);
        department=(Spinner)findViewById(R.id.spinner6);
        ed_exception=(Spinner)findViewById(R.id.spinner7);
        meter_status=(Spinner)findViewById(R.id.spinner8);*/
     //   submit=(Button)findViewById(R.id.submit);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("tab1", "onpause");
        getValidate();
    }
    public void getValue()
    {


        str_division=division_spinner.getSelectedItem().toString();
        str_subdivion=subdivision_spinner.getSelectedItem().toString();
        str_section=section_spinner.getSelectedItem().toString();
       // str_route=route_spinner.getSelectedItem().toString();
       /* str_cycle=cycle.getSelectedItem().toString();

        str_ownerships=ownerships.getSelectedItem().toString();
        str_cons_type=cons_type.getSelectedItem().toString();
        str_department=department.getSelectedItem().toString();
        str_ed_exception=ed_exception.getSelectedItem().toString();
        str_meter_status=meter_status.getSelectedItem().toString();*/

        Log.e("spinner tab",""+str_division+str_division+str_section+"");


        DataHolderClass dataHolderClass=DataHolderClass.getInstance();
        dataHolderClass.set_division(division_code);//str_division
        dataHolderClass.set_subdivision(subdivision_code);//str_subdivion
        dataHolderClass.set_section(section_code);//str_section
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
        int_subdivion=subdivision_spinner.getSelectedItemPosition();
        int_section=section_spinner.getSelectedItemPosition();
      //  int_route=route_spinner.getSelectedItemPosition();
        /*int_cycle=cycle.getSelectedItemPosition();

        int_ownerships=ownerships.getSelectedItemPosition();
        int_cons_type=cons_type.getSelectedItemPosition();*/

        Log.e("postion",""+int_division+int_subdivion+int_section+int_cycle+int_route+int_ownerships+int_cons_type+"");
        Log.e("postion",""+int_division+int_subdivion+int_section+"");
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

            if(int_division==0){ division_spinner.setBackgroundColor(Color.YELLOW);}
            else if (int_subdivion==0){subdivision_spinner.setBackgroundColor(Color.YELLOW); }
            else if (int_section==0){section_spinner.setBackgroundColor(Color.YELLOW);}

            else if(int_division!=0 || int_subdivion!=0 || int_section !=0 ){
                getValue();
            }
    }
}