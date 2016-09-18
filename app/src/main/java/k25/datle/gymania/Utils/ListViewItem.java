package k25.datle.gymania.Utils;

import k25.datle.gymania.Exercise.Exercise;

public class ListViewItem {

    private String m_ExerciseName;
    private String m_Description;
    private int icon;
    private Exercise m_Exercise;

    public ListViewItem(){}

    public ListViewItem(Exercise exercise){
        m_ExerciseName = exercise.GetName();
        m_Description = "Description";
        m_Exercise = exercise;
        m_Description = "Practice time : " + exercise.GetPracticeTime()
                                     + "\nBreak time : " + exercise.GetBreakTime()
                                     + "\nSet count : " + exercise.GetSetCount();
    }
    public String GetExerciseName(){
        return m_ExerciseName;
    }

    public int GetIcon(){
        return this.icon;
    }

    public void SetExerciseName(String name){
        m_ExerciseName = name;
    }

    public String GetDescription() {
        return m_Description;
    }

    public void SetIcon(int icon){
        this.icon = icon;
    }

    public Exercise GetExercise() {
        return m_Exercise;
    }
}