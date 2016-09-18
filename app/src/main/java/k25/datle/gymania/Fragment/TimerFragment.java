package k25.datle.gymania.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import k25.datle.gymania.Exercise.TrainingEvent;
import k25.datle.gymania.MainActivity;
import k25.datle.gymania.Utils.CustomCountDownTimer;

import android.util.Log;

import k25.datle.gymania.R;

/**
 * Created by Dat Le on 9/11/2016.
 */

public class TimerFragment extends Fragment implements View.OnClickListener{

    final int SECOND = 1000;

    View m_RootView;
    TextView m_TimerText;
    Button m_StartBreakButton;
    TrainingEvent m_TrainingEvent;
    boolean m_Started = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (m_RootView == null) {
            Log.i("TimerFragmentOnCreate", "CreateView");
            m_RootView = inflater.inflate(R.layout.fragment_timer_layout, container, false);
            m_TrainingEvent.SetView(m_RootView);
            m_TrainingEvent.SetMainActivityRef((MainActivity)getActivity());
            m_TimerText = (TextView) m_RootView.findViewById(R.id.timer_text);
            m_StartBreakButton = (Button) m_RootView.findViewById(R.id.start_break_button);
            m_StartBreakButton.setEnabled(true);
            m_StartBreakButton.setOnClickListener(this);
            TextView nameTextView = (TextView)m_RootView.findViewById(R.id.ex_name_text);
            nameTextView.setText(m_TrainingEvent.GetExercise().GetName());
            m_StartBreakButton.setText("START");

        }

        Log.i("TimerFragmentOnCreate", "Start");


        //CreateTimer();
        return m_RootView;
    }

    public void InitTrainingEvent(TrainingEvent event) {
        m_TrainingEvent = event;
    }

    void CreateTimer() {
        CustomCountDownTimer timer = new CustomCountDownTimer(30*SECOND,500) {
            @Override
            public void onTick(long millisUntilFinished) {
                super.onTick(millisUntilFinished);

                int seconds = (int) (millisUntilFinished / SECOND);
                int minutes = seconds / 60;

                m_TimerText.setText(minutes + ":" + seconds);
                Log.i("timmer",millisUntilFinished + "");
            }

            @Override
            public void onFinish() {
                m_TimerText.setText("0:00");
                m_StartBreakButton.setEnabled(true);
            }
        };

        timer.start();
    }

    public void onClick(View v) {
        if (m_Started == false) {
            Log.i("TimerFragmentOnCreate", "StartEvent");
            m_TrainingEvent.Start();
            m_Started = true;
            m_StartBreakButton.setEnabled(false);
        }
    }

    public boolean IsDone() {
        return m_TrainingEvent.IsDone();
    }
}
