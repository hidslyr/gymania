package k25.datle.gymania;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import k25.datle.gymania.Exercise.CardioExercise;
import k25.datle.gymania.Utils.DataManager;

/**
 * Created by Nguyen on 9/17/2016.
 */

public class NewExerciseActivity extends AppCompatActivity implements View.OnClickListener{

    EditText m_NameET = null;
    EditText m_PracticeTimeET = null;
    EditText m_BreakTimeET = null;
    Spinner m_SetCountSpinner = null;
    Button m_SaveBtn = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise_layout);
        SetWidget();
    }

    public void SetWidget() {
        if (m_NameET == null) {
            m_NameET = (EditText) findViewById(R.id.new_exercise_name_et);
        }

        if (m_PracticeTimeET == null) {
            m_PracticeTimeET = (EditText) findViewById(R.id.new_exercise_practice_time_et);
        }

        if (m_BreakTimeET == null) {
            m_BreakTimeET = (EditText) findViewById(R.id.new_exercise_break_time_et);
        }

        if (m_SetCountSpinner == null) {
            m_SetCountSpinner = (Spinner) findViewById(R.id.new_exercise_set_count_spinner);
        }

        if (m_SaveBtn == null) {
            m_SaveBtn = (Button) findViewById(R.id.new_exercise_save_button);
            m_SaveBtn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.new_exercise_save_button) {

            String name = m_NameET.getText().toString();

            Integer practiceTime = -1;
            Integer breakTime = -1;
            Integer setCount = -1;

            boolean inputOK = true;

            String msg = "";

            try {
                setCount = Integer.parseInt(m_SetCountSpinner.getSelectedItem().toString());
            } catch (Exception e) {
                Log.i("NewExercise", "Cant parse set count");
                inputOK = false;
                msg = "Set count must be positive integer and not null";
            }

            try {
                breakTime = Integer.parseInt(m_BreakTimeET.getText().toString());
            } catch (Exception e) {
                Log.i("NewExercise", "Cant parse break time");
                inputOK = false;
                msg = "Break time must be positive integer and not null";
            }

            try {
                practiceTime = Integer.parseInt(m_PracticeTimeET.getText().toString());
            } catch (Exception e) {
                Log.i("NewExercise", "Cant parse practice time");
                inputOK = false;
                msg = "Practice time must be positive integer and not null";
            }

            Snackbar snackbar = Snackbar.make(v, msg, Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            if (!inputOK) {
                snackbar.show();
                return;
            }


            CardioExercise ex =  new CardioExercise(name,practiceTime,breakTime,setCount);
            boolean success = DataManager.GetInstance().AddExercise(ex);

            if (success) {
                msg = "Save exercise successful";

            } else {
                msg= "Save exercise failed";
            }

            tv.setText(msg);
            snackbar.show();

            if (success) {
                new Thread() {
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {

                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onBackPressed();
                            }
                        });
                    }
                }.start();
            }
        }
    }

}
