package k25.datle.gymania.Exercise;

/**
 * Created by Dat Le on 9/10/2016.
 */




public class Exercise {
    public class Type {
        public static final int NORMAL = 1;
        public static final int CARDIO = 2;
    }

    String m_Name;
    int m_BreakTime;
    int m_PracticeTime;
    int m_SetCount;
    int m_Type;

    //Abstract class
    protected Exercise() {

    }

    public String GetName() {
        return m_Name;
    }

    public int GetBreakTime() {
        return m_BreakTime;
    }

    public int GetPracticeTime() {
        return m_PracticeTime;
    }

    public int GetSetCount() {
        return m_SetCount;
    }

    public int GetType() { return m_Type; }
}
