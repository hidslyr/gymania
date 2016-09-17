package k25.datle.gymania.Utils;

import java.util.Vector;
import android.util.Log;

import k25.datle.gymania.Exercise.Exercise;

/**
 * Created by Nguyen on 9/11/2016.
 */

public class Utils {

    public static TimeSequence GetTimeSequenceFromExercise(Exercise ex) {
        TimeSequence ret = new TimeSequence();
        SetTimeSequenceByEx(ex,ret);

        Log.i("Utils" ,"TimeSequence Size "+ ret.m_Size);
        for (int i= 0; i< ret.m_Size; i++) {
            Log.i("Utils" ,"TimeSequence "+ ret.m_Sequence[i]);
        }

        return ret;
    }

    public static void SetTimeSequenceByEx(Exercise ex, TimeSequence timeSequence) {
        int size = ex.GetSetCount()*2;
        timeSequence.Init(size);

        for (int i =0; i< size; i++) {
            //One practice and one break follow each other
            if (i%2 == 0) {
                timeSequence.m_Sequence[i] = ex.GetPracticeTime();
            } else {
                timeSequence.m_Sequence[i] = ex.GetBreakTime();
            }
        }
    }

}
