package AdapterClass;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import feedback.mpnsc.R;

public class TicketListAdapter extends BaseAdapter {
    public Context _context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();
    customButtonListener customListner;
    public ArrayList<HashMap<String, String>> arraylist1=new ArrayList<HashMap<String, String>>();

    public TicketListAdapter(Context context, Cursor listDataHeader) {
        this._context= context;
        if (listDataHeader!=null&&listDataHeader.getCount()>0) {
            int m=listDataHeader.getCount();
            for (listDataHeader.moveToNext();!listDataHeader.isAfterLast();listDataHeader.moveToNext()) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("SeqNo", listDataHeader.getString(0));
                map.put("TokenNo", listDataHeader.getString(1).trim());
                map.put("CustomerName", listDataHeader.getString(2));
                arraylist1.add(map);
            }
        }
        else
        {
            TextView textView=new TextView(_context);
            textView.setGravity(Gravity.CENTER);

            textView.setText("No Data Available");
        }

        data = arraylist1;
        this._context = context;

    }
    public interface customButtonListener {
        void onButtonClickListner(int position,List<String> list);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }



    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return (data.get(position));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        final TextView tv_seqNo, tv_tokenNo,tv_name;
        inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(R.layout.single_ticket, parent, false);
        resultp = data.get(position);
        tv_seqNo = itemView.findViewById(R.id.tv_serialNo);
        tv_tokenNo = itemView.findViewById(R.id.tv_ticketNo);
        tv_name = itemView.findViewById(R.id.tv_name);
        tv_seqNo.setText(resultp.get("SeqNo"));
        tv_tokenNo.setText("Ticket No:"+resultp.get("TokenNo"));
        tv_name.setText("Customer Name: "+resultp.get("CustomerName"));

        return itemView;
    }
}

