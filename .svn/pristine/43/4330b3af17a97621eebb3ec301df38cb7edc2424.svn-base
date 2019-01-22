package feedback.mpnsc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import AdapterClass.TicketListAdapter;

/**
 * Created by sushily on 09-06-2015.
 */
public class TicketList extends Activity {
    //ArrayList<String> list;
    ListView listView;
    MultipleTicketManager sqLiteMultipleTicketList;
    Cursor ticketListcursor;
    ArrayAdapter adapter;
    RelativeLayout relativeLayout,relativeLayout2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_list);
        listView=(ListView) findViewById(R.id.listView);
        relativeLayout=(RelativeLayout) findViewById(R.id.relativeLayout1);
        relativeLayout2=(RelativeLayout) findViewById(R.id.relativelayout2);
        //list=new ArrayList<String>();
        sqLiteMultipleTicketList=new MultipleTicketManager(TicketList.this);
        sqLiteMultipleTicketList.openToRead();
        sqLiteMultipleTicketList.openToWrite();
        ticketListcursor=sqLiteMultipleTicketList.ticketlistoncursorAll();
        listView.setAdapter(new TicketListAdapter(getApplicationContext(),ticketListcursor,relativeLayout,relativeLayout2));
        /*if(ticketListcursor!=null && ticketListcursor.moveToFirst()){
                 do {
                   String li= ticketListcursor.getString(0);
                String scheme=ticketListcursor.getString(1);
                String schemename=ticketListcursor.getString(2);
                     list.add(li+" \n"+scheme+"\n"+schemename+"\n");
            } while (ticketListcursor.moveToNext());
        }
        adapter=new ArrayAdapter<String>(TicketList.this,android.R.layout.simple_spinner_item,list);
        listView.setAdapter(adapter);*/
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack();
    }
    public void ShowAlertonBack(){
        TicketList.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(TicketList.this,R.style.MyAlertDialogStyle);
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
}
