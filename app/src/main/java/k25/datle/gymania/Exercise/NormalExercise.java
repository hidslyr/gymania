package k25.datle.gymania.Exercise;

import k25.datle.gymania.Exercise.Exercise;

/**
 * Created by Nguyen on 9/11/2016.
 */

public class NormalExercise extends Exercise {

    public NormalExercise(String name, int breakTime, int setCount) {
        m_Name = name;
        m_BreakTime = breakTime;
        m_SetCount = setCount;
        m_PracticeTime = -1; //Incase normal practice doesn't care about pratice time
        m_Type = Type.NORMAL;
    }

}
