package k25.datle.gymania.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import java.util.Set;
import java.util.Vector;

import k25.datle.gymania.Exercise.Exercise;
import k25.datle.gymania.NewExerciseActivity;
import k25.datle.gymania.Utils.*;

import k25.datle.gymania.R;
import android.os.AsyncTask;
/**
 * Created by Nguyen on 9/7/2016.
 */

public class PracticeFragment extends Fragment implements OnClickListener{
    View m_RootView;

    ListView m_ListView;
    RelativeLayout m_LoadingPanel;
    FloatingActionButton m_fab;


    boolean m_resumeHasRun = false;
    public class State {
        public static final int PRACTICING = 1;
        public static final int WAITING_TO_BREAK = 2;
        public static final int BREAKING = 3;
    }


    public PracticeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (m_RootView == null) {
            m_RootView = inflater.inflate(R.layout.fragment_practice_layout, container, false);

            m_ListView = (ListView) m_RootView.findViewById(R.id.exercise_list_view);
            m_LoadingPanel = (RelativeLayout) m_RootView.findViewById(R.id.loadingPanel);
            m_fab = (FloatingActionButton) m_RootView.findViewById(R.id.new_exercise_button);
            m_fab.setVisibility(View.INVISIBLE);
            m_ListView.setVisibility(View.INVISIBLE);

            new LongOperation(getActivity()).execute("");
        }


        m_fab.setOnClickListener(this);

        return m_RootView;
    }

    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.new_exercise_button) {
            Intent intent = new Intent(getActivity(),NewExerciseActivity.class);
            startActivity(intent);
        }
    }

    public void SetupGUI() {
        Vector<Exercise> exercises = DataManager.GetInstance().GetExerciseFromDatabase();

        Vector<ListViewItem> items = new Vector<ListViewItem>();

        for (int i = 0; i < exercises.size(); i++) {
            ListViewItem item = new ListViewItem(exercises.elementAt(i));
            items.add(item);
        }

        ExerciseListViewAdapter adapter = new ExerciseListViewAdapter(getActivity(), items);
        m_ListView.setAdapter(adapter);
    }

    public void onResume() {
        if (m_resumeHasRun) {
            SetupGUI();
        }
        m_resumeHasRun = true;

        super.onResume();
    }

    private class LongOperation extends AsyncTask<String, Void, String> {
        Activity m_ActivityRef;
        ExerciseListViewAdapter m_Adapter;

        public LongOperation(Activity activity) {
            m_ActivityRef = activity;
        }


        @Override
        protected String doInBackground(String... params) {
            Vector<Exercise> exercises = DataManager.GetInstance().GetExerciseFromDatabase();

            Vector<ListViewItem> items = new Vector<ListViewItem>();

            for (int i = 0; i < exercises.size(); i++) {
                ListViewItem item = new ListViewItem(exercises.elementAt(i));
                items.add(item);
            }

            m_Adapter = new ExerciseListViewAdapter(getActivity(), items);

            try{
                Thread.sleep(500);
            } catch (InterruptedException e){
                Log.i("PracticeFragment","Interrupted");
            }

            return "Done";
        }

        @Override
        protected void onPostExecute(String result) {
            m_ActivityRef.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    m_ListView.setAdapter(m_Adapter);
                    m_fab.setVisibility(View.VISIBLE);
                    m_LoadingPanel.setVisibility(View.GONE);
                    m_ListView.setVisibility(View.VISIBLE);
                }
            });

            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}