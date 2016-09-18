package k25.datle.gymania.Fragment;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.RelativeLayout;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import k25.datle.gymania.Exercise.Exercise;
import k25.datle.gymania.MainActivity;
import k25.datle.gymania.R;
import k25.datle.gymania.Utils.DataManager;
import k25.datle.gymania.Utils.ExerciseListViewAdapter;
import k25.datle.gymania.Utils.GalleryGridViewAdapter;
import k25.datle.gymania.Utils.ListViewItem;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Nguyen on 9/18/2016.
 */


public class PhotoFragment extends Fragment {

    View m_RootView;
    GridView m_GridView;
    FloatingActionButton m_Fab;
    MainActivity m_ActivityRef;
    GalleryGridViewAdapter m_GridAdapter;
    RelativeLayout m_LoadingPanel;
    boolean resumeHasRun = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (m_RootView == null) {
            m_RootView = inflater.inflate(R.layout.fragment_photo_layout, container, false);
            m_GridView = (GridView) m_RootView.findViewById(R.id.photo_grid_view);
            m_ActivityRef = (MainActivity) getActivity();
            m_LoadingPanel = (RelativeLayout) m_RootView.findViewById(R.id.photo_loadingPanel);
            m_Fab = (FloatingActionButton) m_RootView.findViewById(R.id.open_camera_button);
            m_Fab.setOnClickListener(new FabClickListener());
            m_Fab.setVisibility(View.INVISIBLE);
            m_GridView.setVisibility(View.INVISIBLE);

            new LongOperation().execute("");
        }


        return m_RootView;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "k25.datle.gymania.fileprovider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

        }
    }


    public class FabClickListener implements View.OnClickListener {
        public void onClick(View v) {
            dispatchTakePictureIntent();
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private ArrayList<GalleryGridViewAdapter.ImageItem> getData() {
        final ArrayList<GalleryGridViewAdapter.ImageItem> imageItems = new ArrayList<>();

        String path = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();
        Log.d("Files", "Path: " + path);
        File f = new File(path);
        File file[] = f.listFiles();

        for (int i=0; i< file.length ; i++) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(file[i].getAbsolutePath());
            Log.d("Files", "Path " + i + " : " + file[i].getAbsolutePath());

            if (bitmap != null) {
                bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);

                GalleryGridViewAdapter.ImageItem imageItem = new GalleryGridViewAdapter.
                                                ImageItem(bitmap,file[i].getAbsolutePath());


                imageItems.add(imageItem);
            }
        }

        return imageItems;
    }

    public void SetupGridView() {
        m_GridAdapter = new GalleryGridViewAdapter(getActivity(),
                R.layout.gallery_gridview_item, getData());
        m_GridView.setAdapter(m_GridAdapter);

    }
    private class LongOperation extends AsyncTask<String, Void, String> {
    @Override

    protected String doInBackground(String... params) {
        Vector<Exercise> exercises = DataManager.GetInstance().GetExerciseFromDatabase();

        Vector<ListViewItem> items = new Vector<ListViewItem>();

        for (int i = 0; i < exercises.size(); i++) {
            ListViewItem item = new ListViewItem(exercises.elementAt(i));
            items.add(item);
        }

        m_GridAdapter = new GalleryGridViewAdapter(getActivity(),
                R.layout.gallery_gridview_item, getData());

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
                m_GridView.setAdapter(m_GridAdapter);
                m_Fab.setVisibility(View.VISIBLE);
                m_LoadingPanel.setVisibility(View.GONE);
                m_GridView.setVisibility(View.VISIBLE);
            }
        });

        super.onPostExecute(result);
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}
    }

    public void onResume() {
        if (resumeHasRun) {
            SetupGridView();
        }
        resumeHasRun = true;
        super.onResume();
    }

}