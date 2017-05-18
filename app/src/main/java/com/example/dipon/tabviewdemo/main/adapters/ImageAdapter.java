package com.example.dipon.tabviewdemo.main.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dipon.tabviewdemo.R;

/**
 * Created by Acer on 3/23/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        TextView textView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images


    public Integer[] mThumbIds ={
            R.drawable.images, R.drawable.images0,
            R.drawable.images1, R.drawable.images2,
            R.drawable.images3, R.drawable.images4,
            R.drawable.images5, R.drawable.images,
            R.drawable.images0, R.drawable.images1,
            R.drawable.images2, R.drawable.images3,
            R.drawable.images4, R.drawable.images4,
            R.drawable.images, R.drawable.images0,
            R.drawable.images1, R.drawable.images2,
            R.drawable.images3, R.drawable.images4,
            R.drawable.images5, R.drawable.images,
    };


}
