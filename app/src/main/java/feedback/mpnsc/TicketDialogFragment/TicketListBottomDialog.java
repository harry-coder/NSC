package feedback.mpnsc.TicketDialogFragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import feedback.mpnsc.CustomClasses.DialogBox;
import feedback.mpnsc.CustomClasses.TicketPojo;
import feedback.mpnsc.R;
import feedback.mpnsc.exisitingconsumer.Existing_CurrentDetailtest;


public class TicketListBottomDialog extends BottomSheetDialogFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String IBC;
    private String BSC;
    private String response;

    private RecyclerView rv_ticketContainer;
    private TicketAdapter ticketAdapter;

    private ProgressBar progressBar;

    TextView tv_noTicket;

    public TicketListBottomDialog() {
        // Required empty public constructor
    }

    public static TicketListBottomDialog newInstance(String param1, String param2) {
        TicketListBottomDialog fragment = new TicketListBottomDialog ( );
        Bundle args = new Bundle ( );
        args.putString ( ARG_PARAM1, param1 );
        args.putString ( ARG_PARAM2, param2 );
        fragment.setArguments ( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        if (getArguments ( ) != null) {
            IBC = getArguments ( ).getString ( ARG_PARAM1 );
            BSC = getArguments ( ).getString ( ARG_PARAM2 );
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_blank, container, false );

        ticketAdapter = new TicketAdapter ( getActivity ( ) );
        rv_ticketContainer = view.findViewById ( R.id.rv_ticketContainer );
        rv_ticketContainer.setLayoutManager ( new LinearLayoutManager ( getActivity ( ) ) );
        rv_ticketContainer.setAdapter ( ticketAdapter );

        progressBar = view.findViewById ( R.id.progressBar );

        tv_noTicket = view.findViewById ( R.id.tv_noTicket );

        new FetchTickets ( ).execute ( );

        return view;
    }

    public class FetchTickets extends AsyncTask <Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute ( );

            progressBar.setVisibility ( View.VISIBLE );
        }

        @Override
        protected Void doInBackground(Void... params) {

            ArrayList <NameValuePair> nameValuePairs = new ArrayList <NameValuePair> ( );
            //  nameValuePairs.add(new BasicNameValuePair("tag","set_feasibility"));

            nameValuePairs.add ( new BasicNameValuePair ( "IBC", IBC ) );
            nameValuePairs.add ( new BasicNameValuePair ( "BSC", BSC ) );


            System.out.println ( "This is IBC " + IBC );
            System.out.println ( "This is BSC " + BSC );
            Log.e ( "namevaluepair", "" + nameValuePairs );
            try {
                HttpClient httpclient = new DefaultHttpClient ( );

                // HttpPost httppost = new HttpPost(sessionManager.GET_URL());
                HttpPost httppost = new HttpPost ( "http://wcrm.fedco.co.in/phedapi/nscapi/getAllTickets" );

                httppost.setEntity ( new UrlEncodedFormEntity ( nameValuePairs ) );
                ResponseHandler <String> responseHandler = new BasicResponseHandler ( );
                response = httpclient.execute ( httppost, responseHandler );

                System.out.println ( "This is the response " + response );
            } catch (Exception e) {
                // network_interrupt = e.getMessage ( ).toString ( );
                Log.e ( "log_tag", "Error in http connection " + e.toString ( ) );
            }
            return null;
        }


        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute ( result );

            try {


                JSONObject responseObject = new JSONObject ( response );

                if (responseObject.getJSONArray ( "Table" ).length ( ) > 0) {
                    ticketAdapter.setSource ( parseJsonResponse ( responseObject ) );
                    tv_noTicket.setVisibility ( View.GONE );

                } else {

                    tv_noTicket.setVisibility ( View.VISIBLE );
                    progressBar.setVisibility ( View.GONE );

                }

            } catch (Exception e) {

                Log.d ( "Request Exception", e.getMessage ( ) );
            }


        }
    }

    private ArrayList <TicketPojo> parseJsonResponse(JSONObject response) throws JSONException {
        JSONArray responseArray = response.getJSONArray ( "Table" );
        ArrayList <TicketPojo> ticketList = new ArrayList <> ( );

        for (int i = 0; i < responseArray.length ( ); i++) {
            JSONObject ticketObject = responseArray.getJSONObject ( i );

            TicketPojo ticketPojo = new TicketPojo ( );
            ticketPojo.setTicketNumber ( ticketObject.getString ( "TICKETNUMBER" ) );
            ticketPojo.setName ( ticketObject.getString ( "NAME" ) );
            ticketPojo.setArea ( ticketObject.getString ( "DIV_NAME" ) );
            ticketPojo.setApplicationStatus ( ticketObject.getString ( "APPLICATIONSTATUS" ) );
            ticketPojo.setTicketGeneratedDate ( ticketObject.getString ( "TICKETGENDATE" ) );
            ticketPojo.setMobile ( ticketObject.getString ( "MOBILE_NUMBER" ) );
            ticketPojo.setFatherName ( ticketObject.getString ( "FATHERNAME" ) );
            ticketPojo.setAddress ( ticketObject.getString ( "ADDRESS" ) );

            ticketList.add ( ticketPojo );


        }

        return ticketList;

    }

    public class TicketAdapter extends RecyclerView.Adapter <TicketAdapter.TicketHolder> {

        ArrayList <TicketPojo> ticketList = new ArrayList <> ( );

        LayoutInflater inflater;
        Context context;

        TicketAdapter(Context context) {
            inflater = LayoutInflater.from ( context );

            this.context = context;
        }

        @Override
        public TicketAdapter.TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate ( R.layout.ticket_list_single_item, parent, false );

            progressBar.setVisibility ( View.GONE );
            return new TicketAdapter.TicketHolder ( view );
        }

        @Override
        public void onBindViewHolder(final TicketAdapter.TicketHolder holder, int position) {


            TicketPojo itemList = ticketList.get ( position );
            String status = itemList.getApplicationStatus ( );

          //  if(status.equalsIgnoreCase ( "1" )){
                String date[] = itemList.getTicketGeneratedDate ( ).split ( "T" );
                holder.tv_area.setText ( itemList.getArea ( ) );
                holder.tv_date.setText ( date[0] );
                holder.tv_ticket.setText ( itemList.getTicketNumber ( ) );



                holder.tv_status.setText ( "Status: Ticket Generated" +status  );

                holder.im_menu.setOnClickListener ( new View.OnClickListener ( ) {
                    @Override
                    public void onClick(View v) {

                        PopupMenu popup = new PopupMenu ( context, holder.im_menu );
                        //inflating menu from xml resource
                        popup.inflate ( R.menu.call_status_menu );
                        //adding click listener
                        popup.setOnMenuItemClickListener ( new PopupMenu.OnMenuItemClickListener ( ) {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId ( )) {
                                    case R.id.changeStatus:


                                        //  showPaymentDialog ( R.layout.rwa_payment_cheque, true, true );
                                        return true;
                                    case R.id.ticketInfo:
                                        //  showManagerDialog ( );


                                       // showStatusDialog ();
                                        return true;

                                    default:
                                        return false;
                                }
                            }
                        } );
                        //displaying the popup
                        popup.show ( );
                    }
                } );


            /*}
            else{

                System.out.println ("Inside the else part" );
                tv_noTicket.setVisibility ( View.VISIBLE );
            }
*/

        }

        @Override
        public int getItemCount() {

            // return 10;

            System.out.println ( "This is the size " + ticketList.size ( ) );
            return ticketList.size ( );
        }

        public void setSource(ArrayList <TicketPojo> list) {
            if (list.size ( ) != 0) {
                this.ticketList = list;

                notifyItemRangeRemoved ( 0, ticketList.size ( ) );

                notifyDataSetChanged ( );
            }

        }

        class TicketHolder extends RecyclerView.ViewHolder {

            TextView tv_ticket, tv_area, tv_date, tv_status;
            View view;
            CardView cv_taskCard;
            ImageView im_menu, im_call;


            public TicketHolder(View itemView) {
                super ( itemView );
                tv_ticket = itemView.findViewById ( R.id.tv_ticket );
                tv_area = itemView.findViewById ( R.id.tv_area );
                im_menu = itemView.findViewById ( R.id.im_menu );

                tv_date = itemView.findViewById ( R.id.tv_date );
                tv_status = itemView.findViewById ( R.id.status );
                im_call = itemView.findViewById ( R.id.im_call );
                cv_taskCard = itemView.findViewById ( R.id.cv_ticket );
                cv_taskCard.setOnClickListener ( new View.OnClickListener ( ) {
                    @Override
                    public void onClick(View v) {

                        TicketPojo pojo = ticketList.get ( getAdapterPosition ( ) );
                        Intent intent = new Intent ( getActivity ( ), Existing_CurrentDetailtest.class );

                        intent.putExtra ( "div_code", IBC );
                        intent.putExtra ( "sec_code", BSC );
                        intent.putExtra ( "ticket", pojo.getTicketNumber ( ) );
                        intent.putExtra ( "father", pojo.getFatherName ( ) );
                        intent.putExtra ( "address", pojo.getAddress ( ) );
                        intent.putExtra ( "mobile", pojo.getMobile ( ) );
                        intent.putExtra ( "name", pojo.getName ( ) );

                        startActivity ( intent );


                    }
                } );

                im_call.setOnClickListener ( new View.OnClickListener ( ) {
                    @Override
                    public void onClick(View view) {
                        String phone = ticketList.get ( getAdapterPosition ( ) ).getMobile ();
                        Intent intent = new Intent ( Intent.ACTION_DIAL, Uri.fromParts ( "tel", phone, null ) );
                        startActivity ( intent );
                    }
                } );


            }
        }


    }
    public void showStatusDialog(String name,String mobile,String address,String ticketDate){

        // RadioButton rb_reject,rb_accept,rb_hold;
        DialogBox dialogBox=new DialogBox ( getActivity () );

        Dialog dialog=dialogBox.setRequestedDialog ( true,R.layout.user_info_dialog );

        TextView tv_name,tv_mobile, tv_address,tv_ticketDate;

        tv_name=dialog.findViewById ( R.id.tv_name );
        tv_mobile=dialog.findViewById ( R.id.tv_mobile );
        tv_address=dialog.findViewById ( R.id.tv_address );
        tv_ticketDate=dialog.findViewById ( R.id.tv_ticket );


        tv_name.setText ( name );
        tv_address.setText ( address );
        tv_ticketDate.setText ( ticketDate );
        tv_mobile.setText ( mobile );






        dialog.show ();


    }



}
