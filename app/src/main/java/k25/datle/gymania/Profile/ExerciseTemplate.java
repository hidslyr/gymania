package k25.datle.gymania.Profile;
import java.util.Vector;

/**
 * Created by Nguyen on 9/10/2016.
 */

public class ExerciseTemplate {
    Vector<Exercise> m_ExerciseList;
    int m_BreakTimePerExercise;

    public ExerciseTemplate() {

    }

    public void AddExercise(Exercise exercise) {
        m_ExerciseList.add(exercise);
    }

    public Exercise GetExercise(int index) {
        return m_ExerciseList.elementAt(index);
    }

    public void RemoveExercise(int index) {
        m_ExerciseList.removeElementAt(index);
    }

    public int GetBreakTimePerEx() {
        return m_BreakTimePerExercise;
    }

    public void SetBreakTimePerEx(int breakTime) {
        m_BreakTimePerExercise = breakTime;
    }
}
