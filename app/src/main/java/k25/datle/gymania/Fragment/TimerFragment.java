package k25.datle.gymania.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import k25.datle.gymania.MainActivity;
import k25.datle.gymania.Utils.CustomCountDownTimer;

import android.util.Log;

import k25.datle.gymania.R;

/**
 * Created by Nguyen on 9/11/2016.
 */

public class TimerFragment extends Fragment{

    final int SECOND = 1000;

    View m_RootView;
    TextView m_TimerText;
    Button m_StartBreakButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        m_RootView = inflater.inflate(R.layout.fragment_timer_layout, container, false);

        m_TimerText = (TextView) m_RootView.findViewById(R.id.timer_text);
        m_StartBreakButton = (Button) m_RootView.findViewById(R.id.start_break_button);
        m_StartBreakButton.setEnabled(false);

        CreateTimer();
        return m_RootView;
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
}
