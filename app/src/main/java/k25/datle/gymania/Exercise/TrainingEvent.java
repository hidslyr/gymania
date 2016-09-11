package k25.datle.gymania.Exercise;

/**
 * Created by Nguyen on 9/11/2016.
 */

public class TrainingEvent {
    public class TrainingType {
        public static final int Exercise = 1;
        public static final int ExerciseTemplate = 2;
    }

    int m_TrainingType;

    public TrainingEvent() {
        
    }

    public TrainingEvent(Exercise ex) {
        m_TrainingType = TrainingType.Exercise;
    }

    public TrainingEvent(ExerciseTemplate exTemplate) {
        m_TrainingType = TrainingType.ExerciseTemplate;
    }
}
