package k25.datle.gymania.Observer;

import java.util.Vector;


/**
 * Created by Dat Le on 9/12/2016.
 */

public class ObserverController {
    static ObserverController m_Instance = null;
    Vector<ObserverObject> m_ObserverList;

    ObserverController() {

    }

    public void Notify() {
        for (int i=0 ; i < m_ObserverList.size(); i++) {
            m_ObserverList.elementAt(i).update();
        }
    }

    public static ObserverController GetInstance() {
        if (m_Instance == null){
            m_Instance = new ObserverController();
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
