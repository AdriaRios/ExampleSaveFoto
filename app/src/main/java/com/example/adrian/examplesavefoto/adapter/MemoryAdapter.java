package com.example.adrian.examplesavefoto.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adrian.examplesavefoto.R;

import java.util.List;

/**
 * Created by Adrian on 22/03/2015.
 */
public class MemoryAdapter extends BaseAdapter {

    // Application context
    Context context;

    // List of memories that will be rendered in the GridView
    List<MemoryData> memoriesList;
    private LruCache<String, Bitmap> mMemoryCache;


    // Set the context and user list from the constructor
    public MemoryAdapter(Context context, List<MemoryData> memoriesList) {
        this.context = context;
        this.memoriesList = memoriesList;


    }

    // AdapterViews call this method to know how many objects are to be displayed
    @Override
    public int getCount() {
        return memoriesList.size();
    }

    // AdapterViews call this method to retrieve an object at the specified position
    @Override
    public Object getItem(int position) {
        return memoriesList.get(position);
    }

    // AdapterViews call this method to get the object's ID at the specified position
    @Override
    public long getItemId(int position) {
        return position;
    }

    // AdapterViews call this method to retrieve the row View that they must
    // display for the object at the specified position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // The View corresponding to the ListView's row layout
        View currentView = convertView;

        // If the convertView element is not null, the ListView is asking to
        // recycle the existing View, so there is no need to create the View; just update its content
        if ( currentView == null ) {
            LayoutInflater inflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            currentView = inflater.inflate(R.layout.memory_item, parent, false);
        }

        MemoryData memoryData = memoriesList.get(position);
        ((TextView) currentView.findViewById(R.id.textViewTest)).setText(memoryData.getImagePath());
       setPic(((ImageView) currentView.findViewById(R.id.imageView2)), memoryData.getImagePath());

        return currentView;
    }

    private void setPic(ImageView mImageView, String mCurrentPhotoPath) {
        if (ImagesData.getInstance().getBitmapFromMemCache(mCurrentPhotoPath)==null) {
            BitmapWorkerTask task = new BitmapWorkerTask(mImageView,mCurrentPhotoPath);
            task.execute();
            // Get the dimensions of the bitmap
           /* BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

            // Determine how much to scale down the image
            int scaleFactor = 5;

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            // bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

            mImageView.setImageBitmap(bitmap);
            addBitmapToMemoryCache (mCurrentPhotoPath, bitmap);*/
        }else{
            mImageView.setImageBitmap(ImagesData.getInstance().getBitmapFromMemCache(mCurrentPhotoPath));
        }
    }


}
