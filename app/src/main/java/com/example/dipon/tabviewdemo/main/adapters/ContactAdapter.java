package com.example.dipon.tabviewdemo.main.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dipon.tabviewdemo.R;
import com.example.dipon.tabviewdemo.main.UI_utility.ImageUtil;
import com.example.dipon.tabviewdemo.main.data.ContactInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dipon on 5/14/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private String TAG = "CONTACT_ADAPTER";
    private Context context;
    private ClickCallback clickCallback;
    private List<ContactInfo> dataList = null;
    private Cursor contactCursor = null;
    private Fragment fragment;


    public ContactAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
    }

    public ContactAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.dataList = new ArrayList<>();
    }

    public interface ClickCallback {
        void onContactItemClick(int p);
        void onContactItemLongClick(int p);
    }

    public void swapCursor(Cursor cursor) {
        this.contactCursor = cursor;
        notifyDataSetChanged();
    }

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }



    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item_list, null);
        ContactHolder contactViewHolder = new ContactHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo = contactInfo.getContactInfoFromCursor(position, context, contactCursor);
        this.dataList.add(contactInfo);
        if(contactInfo == null) {
            Log.e(TAG, "onBindViewHolder: contactInfo null" );
        }

        Log.d(TAG, "onBindViewHolder: " + contactInfo.getContactName() );
        if (contactInfo.getContactNumber() != null) {
            holder.contactName.setText(contactInfo.getContactName());
            holder.contactNumber.setText(contactInfo.getContactNumber());
            ImageUtil.setImageButTextImageOnException(fragment,contactInfo.getContactImage(),holder.contactImage,contactInfo.getContactName());

        }

    }

    @Override
    public int getItemCount() {
        if(contactCursor == null) {
            return 0;
        }
        return contactCursor.getCount();
    }

    public class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView contactName;
        TextView contactNumber;
        View container;
        ImageView contactImage;

        public ContactHolder(View itemView) {
            super(itemView);
            container = (View) itemView.findViewById(R.id.list_container);
            container.setOnClickListener(this);
            container.setOnLongClickListener(this);
            contactName = (TextView) itemView.findViewById(R.id.contact_name_text);
            contactNumber = (TextView) itemView.findViewById(R.id.contact_number_text);
            contactImage = (ImageView) itemView.findViewById(R.id.contact_profile_pic);
        }

        @Override
        public void onClick(View v) {
                clickCallback.onContactItemClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            clickCallback.onContactItemLongClick(getAdapterPosition());
            return false;
        }
    }
}
