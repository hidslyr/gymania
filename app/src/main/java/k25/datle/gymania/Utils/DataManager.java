package k25.datle.gymania.Utils;

import java.util.Vector;

import k25.datle.gymania.Profile.Profile;
import k25.datle.gymania.Profile.ProfileManager;

/**
 * Created by Nguyen on 9/10/2016.
 */

public class DataManager {
    static DataManager m_Instance = null;

    DataManager() {
    }

    void Init() {

    }

    public static DataManager GetInstance() {
        if (m_Instance == null){
            m_Instance = new DataManager();
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
