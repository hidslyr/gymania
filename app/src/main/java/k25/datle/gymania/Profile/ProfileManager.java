package k25.datle.gymania.Profile;
import java.util.Vector;

/**
 * Created by Nguyen on 9/10/2016.
 */

public class ProfileManager {
    static ProfileManager m_Instance = null;
    Vector<Profile> m_ProfileList;

    ProfileManager() {

    }

    void Init() {

    }

    public static ProfileManager GetInstance() {
        if (m_Instance == null){
            m_Instance = new ProfileManager();
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
