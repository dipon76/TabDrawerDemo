package com.example.dipon.tabviewdemo.main.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dipon.tabviewdemo.R;
import com.example.dipon.tabviewdemo.main.UI_utility.ImageUtil;
import com.example.dipon.tabviewdemo.main.data.ContactInfo;
import com.example.dipon.tabviewdemo.main.data.ContactSummary;
import com.example.dipon.tabviewdemo.main.data.ImageItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dipon
 *         on 5/18/2017.
 */

public class GridAdapter extends ArrayAdapter implements View.OnClickListener{
    private static final String TAG = "Grid Adapter" ;
    private Context context;
    private int resourceId;
    private ArrayList data = new ArrayList();
    private Fragment fragment;
    private Cursor cursor;


    public GridAdapter(@NonNull Context context, @LayoutRes int resource , Fragment fragment) {
        super(context, resource);
        this.context = context;
        this.resourceId = resource;
        this.fragment = fragment;
    }

    public void swapCursor (Cursor cursor) {
        if (cursor == null) {
            Log.d(TAG, "swapCursor: null parameter");
        }
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resourceId, parent, false);
            holder = new ViewHolder();
            holder.imageName = (TextView) row.findViewById(R.id.contact_name_grid);
            holder.imageView = (ImageView) row.findViewById(R.id.contact_image_grid);
            holder.gridContainer = row.findViewById(R.id.grid_container);
            holder.gridContainer.setOnClickListener(this);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ContactInfo contactInfo = new ContactInfo();
        contactInfo = contactInfo.getContactInfoFromCursor(position,context,cursor) ;
        holder.imageName.setText(contactInfo.getContactName());
        ImageUtil.setImageButTextImageOnException(fragment,contactInfo.getContactImage(),holder.imageView,contactInfo.getContactName());
        return row;
    }

    @Override
    public void onClick(View v) {

    }


    public static class ViewHolder{
        TextView imageName;
        ImageView imageView;
        View gridContainer;
    }
}
