package k25.datle.gymania.Fragment;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;

import k25.datle.gymania.R;

/**
 * Created by Nguyen on 9/11/2016.
 */

public class TimerFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        
        return inflater.inflate(R.layout.fragment_timer_layout, container, false);
    }
}
