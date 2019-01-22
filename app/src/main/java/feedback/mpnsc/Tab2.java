package feedback.mpnsc;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import feedback.mpnsc.exisitingconsumer.ExistingConsumer_Search;


public class Tab2 extends TabActivity implements TabHost.OnTabChangeListener {

    Fragment individual_frg, organisation_frg;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    TabHost tabHost;
    RadioGroup type_detail;
    boolean radio_button;
    // CheckBox same_add;
    Button submit;

    View individual_frg_view,organisational_frag_view;
    Spinner title,title_org ;
    EditText name ,father_name,name_org,designation_sign,type_org,name_org_corp,ed_middle,ed_last;

    String str_title,str_name,str_father_name,str_title_org,
            str_name_org,str_designation_sign,str_type_org,str_name_org_corp,str_middle,str_last;

    int title_org_position, title_position;


    @Override
	  public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.tab2);
       tabHost = getTabHost();
       radio_button=true;

        // Set TabChangeListener called when tab changed
        tabHost.setOnTabChangedListener(this);
        submit=(Button)findViewById(R.id.submit);

        TabHost.TabSpec spec;
        Intent intent;

        /************* TAB1 ************/
        // Create  Intents to launch an Activity for the tab (to be reused)
        intent = new Intent();
        intent.setClass(this, CurrentDetail.class);

        spec = tabHost.newTabSpec("First").setIndicator("Current Address")
                .setContent(intent);

        //Add intent to tab
        tabHost.addTab(spec);


        /************* TAB2 ************/
        intent = new Intent();
        intent.setClass(this, BillingDetail.class);
        intent.putExtra("tabname","2");
        spec = tabHost.newTabSpec("Second").setIndicator("Bill Address")
                .setContent(intent);
        tabHost.addTab(spec);

        // Set drawable images to tab
        tabHost.getTabWidget().getChildAt(1);//.setBackgroundResource(R.drawable.tab2);

        // Set Tab1 as Default tab and change image
        tabHost.getTabWidget().setCurrentTab(0);
        tabHost.getTabWidget().getChildAt(0);//.setBackgroundResource(R.drawable.tab1_over);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        individual_frg = new Individual_frame();
        organisation_frg=new Organisation_frame();
        fragmentTransaction.replace(R.id.frame1, individual_frg);
        fragmentTransaction.commit();
        type_detail = (RadioGroup) findViewById(R.id.type_detail);
          type_detail.setOnCheckedChangeListener(
              new RadioGroup.OnCheckedChangeListener() {




                  @Override
                  public void onCheckedChanged(RadioGroup group, int checkedId) {
                      FragmentTransaction ft=fragmentManager.beginTransaction();
                     if(checkedId==R.id.radio_ind)
                     {   radio_button=true;
                         DataHolderClass.getInstance().set_boolean_value(radio_button);
                         ft.replace(R.id.frame1,individual_frg);

                     }
                      else if(checkedId==R.id.radio_org){
                         radio_button=false;
                         DataHolderClass.getInstance().set_boolean_value(radio_button);
                         ft.replace(R.id.frame1,organisation_frg);

                      }
                      ft.commit();

                  }


              }
      );




      }



    @Override
    public void onTabChanged(String tabId) {

        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++) {
            if (i == 0) {
                tabHost.getTabWidget().getChildAt(i);//.setBackgroundResource(R.drawable.tab1);
               // submit.setVisibility(View.INVISIBLE);
                //submit.setVisibility(View.GONE);
            } else if (i == 1) {
                tabHost.getTabWidget().getChildAt(i);
              //  submit.setVisibility(View.VISIBLE);
            }//.setBackgroundResource(R.drawable.tab2);

        }
        Log.i("tabs", "CurrentTab: " + tabHost.getCurrentTab());

        if(tabHost.getCurrentTab()==0)
            tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());//.setBackgroundResource(R.drawable.tab1_over);
        else if(tabHost.getCurrentTab()==1)
            tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());//.setBackgroundResource(R.drawable.tab2_over);
    }




    public void get_individual_value( ) {
        DataHolderClass.getInstance().set_title(str_title);
        DataHolderClass.getInstance().set_name(str_name);
        DataHolderClass.getInstance().setStr_middle_name(str_middle);
        DataHolderClass.getInstance().setStr_last_name(str_last);
        DataHolderClass.getInstance().set_father_name(str_father_name);
        Log.e("tile",str_title);
        Log.e("name",str_name);
        Log.e("father_name",str_father_name);
    }

    public void get_organisational_value( ) {
        DataHolderClass.getInstance().set_title_org(str_title_org);
        DataHolderClass.getInstance().set_name_org(str_name_org);
        DataHolderClass.getInstance().set_designation(str_designation_sign);
        DataHolderClass.getInstance().set_type_org(str_type_org);
        DataHolderClass.getInstance().set_name_org_corp(str_name_org_corp);
        Log.e("tile",str_title_org);
        Log.e("name",str_name_org);
        Log.e("father_name",str_designation_sign);
        Log.e("father_name",str_type_org);
        Log.e("father_name",str_name_org_corp);

    }

    public void getValidate_individual()
    {

        if(title_position==0&& str_name.equals("")&& str_father_name.equals(""))
        {
            title.setBackgroundColor(Color.YELLOW);
            name.setBackgroundColor(Color.YELLOW);
            father_name.setBackgroundColor(Color.YELLOW);
            Toast.makeText(getApplicationContext(), "Enter mandatory fields CONSUMER DETAIL", Toast.LENGTH_SHORT).show();
        }

        else{

            Log.e("from", "else get validate_ind");
            get_individual_value();
        }

    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack();
    }
    public void ShowAlertonBack(){
        Tab2.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Tab2.this);
                builder.setTitle("Are you sure to go back:");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Tab2.this, ExistingConsumer_Search.class));
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
    public void getValidate_organizational()
    {
        if(title_org_position==0|| str_name_org.equals("")||str_designation_sign.equals("")||str_type_org.equals("")
                || str_name_org_corp.equals("")) {
            title_org.setBackgroundColor(Color.YELLOW);
            name_org.setBackgroundColor(Color.YELLOW);
            designation_sign.setBackgroundColor(Color.YELLOW);
            type_org.setBackgroundColor(Color.YELLOW);
            name_org_corp.setBackgroundColor(Color.YELLOW);

            Toast.makeText(getApplicationContext(), "Enter mandatory fields CONSUMER DETAIL", Toast.LENGTH_SHORT).show();

        }else{

            get_organisational_value();
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.e("from tab2","omPause");

        if(radio_button)
        {
//            Log.e("from if","pause tab2");
            individual_frg_view=individual_frg.getView();
            title =(Spinner) individual_frg_view.findViewById(R.id.spinner9);
            name =(EditText) individual_frg_view.findViewById(R.id.editText1);
            ed_middle =(EditText) individual_frg_view.findViewById(R.id.editmiddle);
            ed_last =(EditText) individual_frg_view.findViewById(R.id.editlast);

            //set focus
            name.setFocusable(true);
            father_name =(EditText) individual_frg_view.findViewById(R.id.editText2);

            title_position=title.getSelectedItemPosition();

            str_title=title.getSelectedItem().toString();
            str_name=name.getText().toString();
            str_middle=ed_middle.getText().toString();
            str_last=ed_last.getText().toString();
            str_father_name=father_name.getText().toString();
            getValidate_individual();
        }
        else{
            Log.e("from if","pause tab2");
            organisational_frag_view=organisation_frg.getView();
            title_org =(Spinner) organisational_frag_view.findViewById(R.id.spinner10);
            name_org =(EditText) organisational_frag_view.findViewById(R.id.editText3);
            designation_sign =(EditText) organisational_frag_view.findViewById(R.id.editText4);
            type_org =(EditText) organisational_frag_view.findViewById(R.id.editText5);
            name_org_corp =(EditText) organisational_frag_view.findViewById(R.id.editText);

            title_org_position=title_org.getSelectedItemPosition();

            str_title_org=title_org.getSelectedItem().toString();
            str_name_org=name_org.getText().toString();
            str_designation_sign=designation_sign.getText().toString();
            str_type_org=type_org.getText().toString();
            str_name_org_corp=name_org_corp.getText().toString();
            getValidate_organizational();}
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("from tab2","onStop");
    }
}

