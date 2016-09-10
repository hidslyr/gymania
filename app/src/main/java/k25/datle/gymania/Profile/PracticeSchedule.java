package k25.datle.gymania.Profile;

import java.util.Vector;

/**
 * Created by Nguyen on 9/10/2016.
 */


public class PracticeSchedule {

    public class Day {
        public static final int MONDAY = 2;
        public static final int TUESDAY = 3;
        public static final int WEDNESDAY = 4;
        public static final int THURSDAY = 5;
        public static final int FRIDAY = 6;
        public static final int SATURDAY = 7;
        public static final int SUNDAY = 8;
    }

    private class ScheduledExerciseTemplate {
        int m_Day;
        ExerciseTemplate m_ExerciseTemplate;
        public ScheduledExerciseTemplate(ExerciseTemplate exTemplate, int day) {
            m_ExerciseTemplate = exTemplate;
            m_Day = day;
        }

        public int GetDay() {
            return m_Day;
        }
    }

    Vector<ScheduledExerciseTemplate> m_TemplateList;

    public PracticeSchedule() {

    }

    private boolean IsDayScheduled(int day) {
        for (int i =0; i < m_TemplateList.size(); i++) {
            if (m_TemplateList.elementAt(i).GetDay() == day) {
                return true;
            }
        }

        return false;
    }

    public void AddTemplate(ExerciseTemplate exTemplate, int day) {
        m_TemplateList.add(new ScheduledExerciseTemplate(exTemplate,day) );
    }



}
