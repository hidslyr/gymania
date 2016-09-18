package k25.datle.gymania.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;
import android.database.Cursor;

import java.util.Vector;

import k25.datle.gymania.Exercise.CardioExercise;
import k25.datle.gymania.Exercise.Exercise;
import k25.datle.gymania.Exercise.NormalExercise;
import k25.datle.gymania.Profile.Profile;
import k25.datle.gymania.Profile.ProfileManager;

/**
 * Created by Nguyen on 9/10/2016.
 */

public class DataManager {
    static DataManager m_Instance = null;
    SQLiteDatabase m_Database = null;
    Context m_Context;

    public static final String DATABASE_NAME           = "Gymania.db";
    public static final String EXERCISE_TABLE          = "exercise";

    public class ExerciseRecord {
        public static final String NAME                = "ex_name";
        public static final String PRACTICE_TIME       = "practice_time";
        public static final String BREAK_TIME          = "break_time";
        public static final String SET_COUNT           = "set_count";

        public static final int    NAME_IDX            = 0;
        public static final int    PRACTICE_TIME_IDX   = 1;
        public static final int    BREAK_TIME_IDX      = 2;
        public static final int    SET_COUNT_IDX       = 3;
    }

    DataManager() {
    }

    public void Init(Context context) {
        m_Context = context;
        if (m_Database == null) {
            m_Database = m_Context.openOrCreateDatabase(
                    DATABASE_NAME,
                    m_Context.MODE_PRIVATE,
                    null);
        }

        CreateTable();
    }

    public void CreateTable() {
        if (m_Database.isOpen() != true) {
            m_Database = m_Context.openOrCreateDatabase(
                    DATABASE_NAME,
                    m_Context.MODE_PRIVATE,
                    null);
        }
        try {
            Cursor c = m_Database.query(EXERCISE_TABLE,null,null,null,null,null,null);
        } catch (Exception e) {
            //No any table existed
            CreateExerciseTable();
        }
    }

    public void CreateExerciseTable() {
        String sql="CREATE TABLE " + EXERCISE_TABLE + " (";
        sql+=ExerciseRecord.NAME + " TEXT primary key,";
        sql+=ExerciseRecord.PRACTICE_TIME + " INTEGER,";
        sql+=ExerciseRecord.BREAK_TIME + " INTEGER,";
        sql+=ExerciseRecord.SET_COUNT + " INTEGER)";
        m_Database.execSQL(sql);
    }

    public static DataManager GetInstance() {
        if (m_Instance == null){
            m_Instance = new DataManager();
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

    public boolean AddExercise(Exercise ex) {
        ContentValues values = new ContentValues();
        String exName = ex.GetName();
        Integer practiceTime = ex.GetPracticeTime();
        Integer breakTime = ex.GetBreakTime();
        Integer setCount = ex.GetSetCount();
        values.put(ExerciseRecord.NAME,exName);
        values.put(ExerciseRecord.PRACTICE_TIME,practiceTime);
        values.put(ExerciseRecord.BREAK_TIME,breakTime);
        values.put(ExerciseRecord.SET_COUNT,setCount);

        String msg;

        if (m_Database.insert("exercise", null, values) == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Vector<Exercise> GetExerciseFromDatabase() {
        Vector<Exercise> exercises = new Vector<Exercise>();

        Cursor c = m_Database.query("Exercise", null, null, null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Exercise temp;
            if (c.getInt(ExerciseRecord.PRACTICE_TIME_IDX) == -1) {
                String name = c.getString(ExerciseRecord.NAME_IDX);
                Integer breakTime = c.getInt(ExerciseRecord.BREAK_TIME_IDX);
                Integer setCount = c.getInt(ExerciseRecord.SET_COUNT_IDX);

                Log.i("DataManager","name = " + name);
                Log.i("DataManager","breakTime = " + breakTime);
                Log.i("DataManager","setCount = " + setCount);

                temp = new NormalExercise(name,breakTime,setCount);
            } else {
                String name = c.getString(ExerciseRecord.NAME_IDX);
                Integer practiceTime = c.getInt(ExerciseRecord.PRACTICE_TIME_IDX);
                Integer breakTime = c.getInt(ExerciseRecord.BREAK_TIME_IDX);
                Integer setCount = c.getInt(ExerciseRecord.SET_COUNT_IDX);

                Log.i("DataManager","name = " + name);
                Log.i("DataManager","practiceTime = " + practiceTime);
                Log.i("DataManager","breakTime = " + breakTime);
                Log.i("DataManager","setCount = " + setCount);

                temp = new CardioExercise(name,practiceTime,breakTime,setCount);
            }

            exercises.add(temp);
            c.moveToNext();
        }
        c.close();

        return exercises;
    }

    public boolean DeleteExercise(String name) {
        if (m_Database.delete("exercise",ExerciseRecord.NAME + "=?", new String[] {name} ) == -1) {
            Log.i("DataManager", "Delete exercise failed");
            return false;
        } else {
            Log.i("DataManager","Delete exercise success");
            return true;
        }
    }
}
