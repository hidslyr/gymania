package k25.datle.gymania.Utils;

/**
 * Created by Dat Le on 9/12/2016.
 */

public class TimeSequence {
    public TimeSequence() {
    }

    public void Init(int size) {
        m_Size = size;
        m_Sequence = new int[m_Size];
    }

    public int[] m_Sequence;
    public int m_Size;
}