package k25.datle.gymania.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;

import java.io.File;
import java.util.ArrayList;

import k25.datle.gymania.MainActivity;
import k25.datle.gymania.R;

/**
 * Created by Dat Le on 9/18/2016.
 */

public class GalleryGridViewAdapter extends ArrayAdapter {

    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public GalleryGridViewAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.gridview_image);
            holder.shareButton = (ShareButton) row.findViewById(R.id.share_facebook_btn);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ImageItem item = (ImageItem) data.get(position);
        holder.image.setImageBitmap(item.getImage());

        MyOnLongClickListener longClickListener = new MyOnLongClickListener();
        longClickListener.SetPath(item.GetPath());
        holder.image.setOnLongClickListener(longClickListener);

        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(item.getImage())
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        holder.shareButton.setShareContent(content);

        return row;
    }

    static class ViewHolder {
        ImageView image;
        ShareButton shareButton;
    }

    static public class ImageItem {
        private Bitmap image;
        private String path;

        public ImageItem(Bitmap image, String path) {
            super();
            this.image = image;
            this.path = path;
        }

        public Bitmap getImage() {
            return image;
        }

        public void setImage(Bitmap image) {
            this.image = image;
        }

        public String GetPath() {return path;}

    }

    public class MyOnClickListener implements View.OnClickListener{
        Bitmap image;

        public void SetImage(Bitmap image) {
            this.image = image;
        }

        public void onClick(View v) {


        }
    }

    public class MyOnLongClickListener implements  View.OnLongClickListener {

        String path;

        public void SetPath(String path) {
            this.path = path;
        }
        public boolean onLongClick(View v) {
            File newFile  = new File(path);
            if (newFile != null) {
                Log.i("OnLongClick","delete");
                newFile.delete();
                MainActivity activity = (MainActivity) context;
                activity.GetPhotoFragment().SetupGridView();
            }
            return true;
        }
    }

}