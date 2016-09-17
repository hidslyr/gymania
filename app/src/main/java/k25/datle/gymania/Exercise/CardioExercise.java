package k25.datle.gymania.Exercise;

/**
 * Created by Nguyen on 9/11/2016.
 */

public class CardioExercise extends Exercise {

    public CardioExercise(String name, int practiceTime, int breakTime, int setCount ) {
        m_Name = name;
        m_BreakTime = breakTime;
        m_SetCount = setCount;
        m_PracticeTime = practiceTime;
        m_Type = Type.CARDIO;
    }

}
