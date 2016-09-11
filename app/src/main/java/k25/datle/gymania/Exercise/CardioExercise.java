package k25.datle.gymania.Exercise;

/**
 * Created by Nguyen on 9/11/2016.
 */

public class CardioExercise extends Exercise {

    int m_PracticeTime;

    public CardioExercise(String name, int breakTime, int setCount, int practiceTime) {
        m_Name = name;
        m_BreakTime = breakTime;
        m_SetCount = setCount;
        m_PracticeTime = practiceTime;
    }

    public int GetPracticeTime() {
        return m_PracticeTime;
    }
}
