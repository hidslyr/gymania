package k25.datle.gymania.Fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import k25.datle.gymania.Exercise.CardioExercise;
import k25.datle.gymania.Exercise.Exercise;
import k25.datle.gymania.Utils.*;

import k25.datle.gymania.Exercise.TrainingEvent;
import k25.datle.gymania.R;

/**
 * Created by Nguyen on 9/7/2016.
 */

public class PracticeFragment extends Fragment implements OnClickListener {

    Activity m_MainActivityRef;
    View m_RootView;
    public class State {
        public static final int PRACTICING = 1;
        public static final int WAITING_TO_BREAK = 2;
        public static final int BREAKING = 3;
    }

    EditText m_NameET = null;
    EditText m_PracticeTimeET = null;
    EditText m_BreakTimeET = null;
    Spinner m_SetCountSpinner = null;

    Button m_SaveBtn = null;

    public PracticeFragment() {}

    public void GetMainActivityRef(Activity mainActivityRef) {
        m_MainActivityRef = mainActivityRef;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (m_RootView == null) {
            m_RootView = inflater.inflate(R.layout.fragment_practice_layout, container, false);
            SetWidget();
        }

        return m_RootView;
    }

    public void SetWidget() {
        if (m_NameET == null) {
            m_NameET = (EditText) m_RootView.findViewById(R.id.new_exercise_name_et);
        }

        if (m_PracticeTimeET == null) {
            m_PracticeTimeET = (EditText) m_RootView.findViewById(R.id.new_exercise_practice_time_et);
        }

        if (m_BreakTimeET == null) {
            m_BreakTimeET = (EditText) m_RootView.findViewById(R.id.new_exercise_break_time_et);
        }

        if (m_SetCountSpinner == null) {
            m_SetCountSpinner = (Spinner) m_RootView.findViewById(R.id.new_exercise_set_count_spinner);
        }

        if (m_SaveBtn == null) {
            m_SaveBtn = (Button) m_RootView.findViewById(R.id.new_exercise_save_button);
            m_SaveBtn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        String msg;
        if (viewId == R.id.new_exercise_save_button) {

            String name = m_NameET.getText().toString();
            Integer practiceTime = Integer.parseInt(m_PracticeTimeET.getText().toString());
            Integer breakTime = Integer.parseInt(m_BreakTimeET.getText().toString());
            Integer setCount = Integer.parseInt(m_SetCountSpinner.getSelectedItem().toString());

            CardioExercise ex =  new CardioExercise(name,practiceTime,breakTime,setCount);

            DataManager.GetInstance().AddExercise(ex);


            DataManager.GetInstance().GetExerciseFromDatabase();
        }
    }

}