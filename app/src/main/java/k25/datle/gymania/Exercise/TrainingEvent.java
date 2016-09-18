package k25.datle.gymania.Exercise;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;

import k25.datle.gymania.Fragment.PracticeFragment;
import k25.datle.gymania.MainActivity;
import k25.datle.gymania.Utils.CustomCountDownTimer;
import k25.datle.gymania.Utils.TimeSequence;
import k25.datle.gymania.Utils.Utils;
import k25.datle.gymania.R;

/**
 * Created by Dat Le on 9/11/2016.
 */

public class TrainingEvent{



    public class TrainingType {
        public static final int Exercise = 1;
        public static final int ExerciseTemplate = 2;
    }

    public class State {
        public static final int COUNTDOWN =1;
        public static final int WAITING = 2;
    }

    int m_CurrentCountDownIdx = 0;


    Context m_Context;
    boolean m_IsDone = false;
    int m_TrainingType;
    Exercise m_Exercise;
    View m_View;
    MainActivity m_MainActiviyRef;

    public TrainingEvent() {

    }

    public TrainingEvent(Context ctx , Exercise ex) {
        m_TrainingType = TrainingType.Exercise;
        m_Exercise = ex;
        m_Context = ctx;
    }

    public TrainingEvent(ExerciseTemplate exTemplate) {
        m_TrainingType = TrainingType.ExerciseTemplate;
    }

    public void SetMainActivityRef(MainActivity activity) {
        m_MainActiviyRef = activity;
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
        CountDownByTimeSequence(sequence);

    }

    private void CountDownByTimeSequence(final TimeSequence timeSequence) {

        int size = timeSequence.m_Size;
        int[] sequence = timeSequence.m_Sequence;

        if (m_CurrentCountDownIdx >= size) {
            Log.i("TraningEvent","Done");
            m_IsDone = true;

            if (m_MainActiviyRef != null) {
                m_MainActiviyRef.DisplayPracticeView();
            }

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

        break_btn.setEnabled(false);

        Log.i("TraningEvent","Current CoundDowntIdx : " + m_CurrentCountDownIdx);

        final TextView tv = (TextView)m_View.findViewById(R.id.timer_text);
        Log.i("TraningEvent" ,"CountDownByTimeSequence "+ sequence[m_CurrentCountDownIdx]);
        CustomCountDownTimer timer = new CustomCountDownTimer(sequence[m_CurrentCountDownIdx]*1000-1,250) {
            @Override
            public void onTick(long millisUntilFinished) {
                super.onTick(millisUntilFinished);
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;

                seconds = seconds - minutes * 60;

                Log.i("TraningEvent" ,"CountDownTimer "+ millisUntilFinished);

                String secondsString;
                if (seconds < 10) {
                    secondsString = "0" + seconds;
                } else {
                    secondsString = "" + seconds;
                }


                tv.setText(minutes + ":" + secondsString);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                m_CurrentCountDownIdx++;
                CountDownByTimeSequence(timeSequence);
                Button break_btn = (Button) m_View.findViewById(R.id.start_break_button);
                final MediaPlayer mp = MediaPlayer.create(m_Context,R.raw.sound_done);
                mp.start();

                CustomCountDownTimer timer = new CustomCountDownTimer(2000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        // Nothing to do
                    }

                    @Override
                    public void onFinish() {
                        if (mp.isPlaying()) {
                            mp.stop();
                            mp.release();
                        }
                    }
                };
                timer.start();
            }
        };
        timer.start();
    }

    public Exercise GetExercise() {
        return m_Exercise;
    }

    public boolean IsDone() {
        return m_IsDone;
    }
}
