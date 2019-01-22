package feedback.mpnsc;


import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;


public class TabBar extends TabActivity implements OnTabChangeListener{

	/** Called when the activity is first created. */
	TabHost tabHost;
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.tab_bar);

	      // Get TabHost Refference
	      tabHost = getTabHost();
	      // Set TabChangeListener called when tab changed
	      tabHost.setOnTabChangedListener(this);
	      TabHost.TabSpec spec;
	      Intent intent;
	       /************* TAB1 ************/
	      // Create  Intents to launch an Activity for the tab (to be reused)
	      intent = new Intent().setClass(this, Tab1.class);
	      spec = tabHost.newTabSpec("First").setIndicator("OFFICE DETAILS").setContent(intent);
	      
	      //Add intent to tab
	      tabHost.addTab(spec);
	
	      /************* TAB2 ************/
	      intent = new Intent().setClass(this, Tab2.class);
	      spec = tabHost.newTabSpec("Second").setIndicator("CONSUMER DETAILS")
	                    .setContent(intent);  
	      tabHost.addTab(spec);
	
	      /************* TAB3 ************/
	      intent = new Intent().setClass(this, Tab3.class);
	      spec = tabHost.newTabSpec("Third").setIndicator("LOAD REQUIREMENT")
	                    .setContent(intent);
	      tabHost.addTab(spec);
	
	      // Set drawable images to tab
		  tabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#00769B"));//.setBackgroundResource(R.drawable.tab2);
		  tabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.parseColor("#00769B"));//.setBackgroundResource(R.drawable.tab3);
          tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#00769B"));//.setBackgroundResource(R.drawable.tab1_over);
		  // Set Tab1 as Default tab and change image

          tabHost.getTabWidget().setCurrentTab(0);
       }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack();
    }
    public void ShowAlertonBack(){
        TabBar.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(TabBar.this);
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
    @Override
	public void onTabChanged(String tabId) {
    	
    	/************ Called when tab changed *************/
    	
		//********* Check current selected tab and change according images *******/
    	
	    for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
		{
	    	if(i==0)
	    	    tabHost.getTabWidget().getChildAt(i);//.setBackgroundResource(R.drawable.tab1);
	    	else if(i==1)
	    		tabHost.getTabWidget().getChildAt(i);//.setBackgroundResource(R.drawable.tab2);
	    	else if(i==2)
	    		tabHost.getTabWidget().getChildAt(i);//.setBackgroundResource(R.drawable.tab3);
	    }
	    Log.i("tabs", "CurrentTab: "+tabHost.getCurrentTab());
	    if(tabHost.getCurrentTab()==0)
	    	tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());//.setBackgroundResource(R.drawable.tab1_over);
	    else if(tabHost.getCurrentTab()==1)
	    	tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());//.setBackgroundResource(R.drawable.tab2_over);
	    else if(tabHost.getCurrentTab()==2)
	    	tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());//.setBackgroundResource(R.drawable.tab3_over);
	    
	}




}
