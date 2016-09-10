package k25.datle.gymania.Profile;

import java.util.Vector;

/**
 * Created by Nguyen on 9/10/2016.
 */

public class ExerciseManager {
    static ExerciseManager m_Instance = null;

    ExerciseManager() {

    }

    void Init() {

    }

    public static ExerciseManager GetInstance() {
        if (m_Instance == null){
            m_Instance = new ExerciseManager();
            m_Instance.Init();
        }

        return m_Instance;
    }

    public static boolean HasInstance() {
        if (m_Instance == null) {
            return false;
        } else {
            return true;
        }
    }
}
