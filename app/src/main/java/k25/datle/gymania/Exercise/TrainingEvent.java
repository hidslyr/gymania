package k25.datle.gymania.Exercise;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;

import k25.datle.gymania.Fragment.PracticeFragment;
import k25.datle.gymania.Utils.CustomCountDownTimer;
import k25.datle.gymania.Utils.TimeSequence;
import k25.datle.gymania.Utils.Utils;
import k25.datle.gymania.R;

/**
 * Created by Nguyen on 9/11/2016.
 */

public class TrainingEvent extends Observable {
    public class TrainingType {
        public static final int Exercise = 1;
        public static final int ExerciseTemplate = 2;
    }

    public class State {
        public static final int COUNTDOWN =1;
        public static final int WAITING = 2;
    }

    State m_State;
    boolean m_IsCurrentCountDownDone = false;
    boolean m_IsCurrentExerciseDone;
    int m_CurrentCountDownIdx = 0;

    int m_TrainingType;
    Exercise m_Exercise;
    View m_View;

    public TrainingEvent() {

    }

    public TrainingEvent(Exercise ex) {
        m_TrainingType = TrainingType.Exercise;
        m_Exercise = ex;
    }

    public TrainingEvent(ExerciseTemplate exTemplate) {
        m_TrainingType = TrainingType.ExerciseTemplate;
    }

    public void SetView(View view) {
        m_View = view;
    }

    public void Start() {
        Log.i("TraningEvent", "start");
        TimeSequence sequence = Utils.GetTimeSequenceFromExercise(m_Exercise);

        Log.i("TraningEvent" ,"TimeSequence Size "+ sequence.m_Size);
        for (int i= 0; i< sequence.m_Size; i++) {
            Log.i("TraningEvent" ,"TimeSequence " + i + " " + sequence.m_Sequence[i]);
        }

        TextView nameTextView = (TextView)m_View.findViewById(R.id.ex_name_text);
        nameTextView.setText(m_Exercise.GetName());
        CountDownByTimeSequence(sequence);

    }

    private void CountDownByTimeSequence(final TimeSequence timeSequence) {
        int size = timeSequence.m_Size;
        int[] sequence = timeSequence.m_Sequence;

        if (m_CurrentCountDownIdx >= size) {
            Log.i("TraningEvent","Done");
            return;
        }

        Button break_btn = (Button) m_View.findViewById(R.id.start_break_button);
        if (m_CurrentCountDownIdx%2 == 0) {
            if (sequence[m_CurrentCountDownIdx] == -1) {
                break_btn.setText("START BREAK");
            } else {
                break_btn.setText("PRACTICING");
            }
        } else {
            break_btn.setText("BREAKING");
        }

        Log.i("TraningEvent","Current CoundDowntIdx : " + m_CurrentCountDownIdx);

        final TextView tv = (TextView)m_View.findViewById(R.id.timer_text);
        Log.i("TraningEvent" ,"CountDownByTimeSequence "+ sequence[m_CurrentCountDownIdx]);
        CustomCountDownTimer timer = new CustomCountDownTimer(sequence[m_CurrentCountDownIdx]*1000-1,250) {
            @Override
            public void onTick(long millisUntilFinished) {
                super.onTick(millisUntilFinished);
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;

                Log.i("TraningEvent" ,"CountDownTimer "+ millisUntilFinished);
                tv.setText(minutes + ":" + seconds);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                m_CurrentCountDownIdx++;
                CountDownByTimeSequence(timeSequence);

            }
        };
        timer.start();
    }

    private void SetState(PracticeFragment.State state) {

    }

    private void SetTimeCountDown(int index, int[] sequence) {
        if (index%2 == 0) {
            //Practice countdown

        } else {
            //Breaking countdown
        }
    }
}
