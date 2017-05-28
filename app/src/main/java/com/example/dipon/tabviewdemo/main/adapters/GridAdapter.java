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

import com.example.dipon.tabviewdemo.R;
import com.example.dipon.tabviewdemo.main.UI_utility.ImageUtil;
import com.example.dipon.tabviewdemo.main.data.ContactInfo;

import java.util.ArrayList;

/**
 * @author Dipon
 *         on 5/18/2017.
 */

public class GridAdapter extends ArrayAdapter {
    private static final String TAG = "Grid Adapter" ;
    private Context context;
    private int resourceId;
    private ArrayList data = new ArrayList();
    private Fragment fragment;
    private Cursor cursor;
    private GridClickCallback gridClickCallback;

    public GridAdapter(@NonNull Context context, @LayoutRes int resource , Fragment fragment) {
        super(context, resource);
        this.context = context;
        this.resourceId = resource;
        this.fragment = fragment;
    }

    public interface GridClickCallback {
        void gridItemClick(ContactInfo contactInfo);
        void detailGridItemClick(ContactInfo contactInfo);
    }

    public void swapCursor (Cursor cursor) {
        if (cursor == null) {
            Log.d(TAG, "swapCursor: null parameter");
        }
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    public GridClickCallback getGridClickCallback() {
        return gridClickCallback;
    }

    public void setGridClickCallback(GridClickCallback gridClickCallback) {
        this.gridClickCallback = gridClickCallback;
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
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resourceId, parent, false);
            holder = new ViewHolder();
            holder.imageName = (TextView) row.findViewById(R.id.contact_name_grid);
            holder.imageView = (ImageView) row.findViewById(R.id.contact_image_grid);
            holder.gridContainer = row.findViewById(R.id.grid_container);
            holder.gridContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContactInfo contactInfo = new ContactInfo();
                    contactInfo = contactInfo.getContactInfoFromCursor(position,context,cursor) ;
                    gridClickCallback.gridItemClick(contactInfo);
                }
            });
            row.setTag(holder);
            row.setTag(R.id.grid_container, position);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        ContactInfo contactInfo = new ContactInfo();
        contactInfo = contactInfo.getContactInfoFromCursor(position,context,cursor) ;
        holder.imageName.setText(contactInfo.getContactName());
        ImageUtil.setImageButTextImageOnException(fragment,contactInfo.getContactImage(),holder.imageView,contactInfo.getContactName());
        return row;
    }

//    @Override
//    public void onClick(View v) {
//        if(v.getId() == R.id.grid_container) {
//            ViewHolder holder = (ViewHolder) v.getTag();
//            int pos = (int) v.getTag(R.id.grid_container);
//            ContactInfo contactInfo = new ContactInfo();
//            contactInfo = contactInfo.getContactInfoFromCursor(pos,context,cursor) ;
//            gridClickCallback.gridItemClick(contactInfo);
//        } else {
//            ViewHolder holder = (ViewHolder) v.getTag();
//            int pos = (Integer) holder.gridContainer.getTag();
//            ContactInfo contactInfo = new ContactInfo();
//            contactInfo = contactInfo.getContactInfoFromCursor(pos,context,cursor) ;
//            gridClickCallback.detailGridItemClick(contactInfo);
//        }
//    }


    public static class ViewHolder{
        TextView imageName;
        ImageView imageView;
        View gridContainer;
    }
}
