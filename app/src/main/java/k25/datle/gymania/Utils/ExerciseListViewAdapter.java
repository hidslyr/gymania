package k25.datle.gymania.Utils;



import java.util.ArrayList;
import java.util.List;

import k25.datle.gymania.Exercise.Exercise;
import k25.datle.gymania.MainActivity;
import k25.datle.gymania.R;
import k25.datle.gymania.Utils.ListViewItem;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Vector;

import k25.datle.gymania.Utils.ListViewItem;

public class ExerciseListViewAdapter extends BaseAdapter {

    private MainActivity m_ActivityRef;
    private Vector<ListViewItem> m_Items;
    private ViewHolder holder;

    public ExerciseListViewAdapter(Activity activity, Vector<ListViewItem> items){
        m_ActivityRef = (MainActivity) activity;
        m_Items = items;
    }

    @Override
    public int getCount() {
        return m_Items.size();
    }

    @Override
    public Object getItem(int position) {
        return m_Items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imgIcon;
        TextView txtFirstLine;
        TextView txtSecondLine;
        Button startButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    m_ActivityRef.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listview_item, null);

            holder = new ViewHolder();
            holder.imgIcon = (ImageView) convertView.findViewById(R.id.listView_icon);
            holder.txtFirstLine = (TextView) convertView.findViewById(R.id.listView_firstLine);
            holder.txtSecondLine = (TextView) convertView.findViewById(R.id.listView_secondLine);

            holder.startButton = (Button) convertView.findViewById(R.id.start_exercise_button);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        //holder.imgIcon.setImageResource(m_Items.get(position).getIcon());
        holder.txtFirstLine.setText(m_Items.get(position).GetExerciseName());
        holder.txtSecondLine.setText(m_Items.get(position).GetDescription());

        StartButtonOnClickListener clickListener = new StartButtonOnClickListener();
        clickListener.SetExercise(m_Items.get(position).GetExercise());
        holder.startButton.setOnClickListener(clickListener);


        StartButtonOnLongClickListener longClickListener = new StartButtonOnLongClickListener();
        longClickListener.SetExercise(m_Items.get(position).GetExercise());
        holder.startButton.setLongClickable(true);
        holder.startButton.setOnLongClickListener(longClickListener);


        return convertView;
    }

    public class StartButtonOnClickListener implements View.OnClickListener {
        Exercise m_Exercise;

        public void SetExercise(Exercise ex) {
            m_Exercise = ex;
        }
        @Override
        public void onClick(View v) {
            m_ActivityRef.DisplayTimeCountDownView(m_Exercise);
        }
    }

    public class StartButtonOnLongClickListener implements  View.OnLongClickListener {
        Exercise m_Exercise;

        public void SetExercise(Exercise ex) {
            m_Exercise = ex;
        }

        public boolean onLongClick(View v) {
            Log.i("ListViewAdapter","LongClickListener");
            if (m_Exercise == null) {
                Log.i("ListViewAdapter","ex null");
                return false;
            } else {
                DataManager.GetInstance().DeleteExercise(m_Exercise.GetName());
                m_ActivityRef.GetPracticeFragment().SetupGUI();
                return true;
            }
        }
    }

}
