package feedback.mpnsc.exisitingconsumer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import feedback.mpnsc.R;

/**
 * Created by swatiG on 02-07-2015.
 */
public class Existing_IndividualFrame extends Fragment {

    EditText name,father_name,middle,last;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */

        View view=inflater.inflate( R.layout.individual_frame1, container, false);
        name =(EditText) view.findViewById(R.id.editText1);
        middle =(EditText) view.findViewById(R.id.editmiddle);
        last =(EditText) view.findViewById(R.id.editlast);
        father_name =(EditText) view.findViewById(R.id.editText2);
        //set focus
        name.setFocusable(true);
        name.setText(DataHolderExisting.getInstance().getName());
        middle.setText(DataHolderExisting.getInstance().getMiddle_name());
        last.setText(DataHolderExisting.getInstance().getLast_name());
        father_name.setText(DataHolderExisting.getInstance().getFather_husband_name());


        return view;
    }
}
