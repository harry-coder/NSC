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

    public TicketListAdapter(Context context, Cursor listDataHeader, RelativeLayout relativeLayout, RelativeLayout relativeLayout2) {
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
            relativeLayout.setVisibility(View.GONE);
            textView.setText("No Data Available");
            relativeLayout2.addView(textView);
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
        final TextView seqNo, tokenNo,_Name;
        inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(R.layout.ticket_list_view, parent, false);
        resultp = data.get(position);
        seqNo = (TextView) itemView.findViewById(R.id.SeqNo);
        tokenNo = (TextView) itemView.findViewById(R.id.TokenNo);
        _Name = (TextView) itemView.findViewById(R.id.CustomerName);
        seqNo.setText(resultp.get("SeqNo")+".");
        tokenNo.setText(resultp.get("TokenNo"));
        _Name.setText(resultp.get("CustomerName"));

        return itemView;
    }
}

