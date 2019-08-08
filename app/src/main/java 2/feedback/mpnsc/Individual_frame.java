package feedback.mpnsc;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by swatiG on 25-05-2015.
 */
public class Individual_frame extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */

        View view=inflater.inflate( R.layout.individual_frame1, container, false);

        return view;
    }
}
