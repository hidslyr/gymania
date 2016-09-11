package k25.datle.gymania.Profile;

import java.util.Vector;
import k25.datle.gymania.Exercise.*;

/**
 * Created by Nguyen on 9/10/2016.
 */

public class Profile {

    public class Stats {
        public float m_Weight;
        public float m_Height;
        public float m_BodyFat;
    }

    Vector<Exercise> ExerciseList;
    Vector<ExerciseTemplate> ExerciseTemplateList;
    Vector<PracticeSchedule> PracticeScheduleList;

    String m_Name;
    int m_Age;
    Stats m_Stats;

    public Profile() {

    }

    public String GetName() {
        return m_Name;
    }

    public void SetName(String name) {
        m_Name = name;
    }

    public int GetAge() {
        return m_Age;
    }

    public void SetAge(int age) {
        m_Age = age;
    }


    public Stats GetStats() {
        return m_Stats;
    }
}
