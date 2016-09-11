package k25.datle.gymania.Exercise;

/**
 * Created by Nguyen on 9/10/2016.
 */

public class Exercise {
    String m_Name;
    int m_BreakTime;
    int m_SetCount;

    public Exercise() {

    }

    public Exercise(String name, int breakTime, int setCount) {
        m_Name = name;
        m_BreakTime = breakTime;
        m_SetCount = setCount;
    }

    public String GetName() {
        return m_Name;
    }

    public int GetBreakTime() {
        return m_BreakTime;
    }



    public int GetSetCount() {
        return m_BreakTime;
    }
}
