package com.example.dipon.tabviewdemo.main.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
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
        void onItemClick(int p);
    }

    public void swapCursor(Cursor cursor) {
        this.contactCursor = cursor;
        notifyDataSetChanged();
    }

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }


    public ContactInfo getContactInfoFromCursor(int adapterPosition) {
        ContactInfo contactInfo = new ContactInfo();
        ContentResolver contentResolver = context.getContentResolver();
        if (contactCursor == null) {
            return null;
        } else {
            if(contactCursor.getCount()>0) {
                contactCursor.moveToPosition(adapterPosition);
                int hasPhoneNumber = Integer.parseInt(contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String photoUri = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

                    String isStarred = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.STARRED));
                    contactInfo.setContactName(name);
                    contactInfo.setContactId(id);
                    contactInfo.setContactImage(photoUri);
                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);

                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactInfo.setContactNumber(phoneNumber);
                    }
                    phoneCursor.close();
                }
            }
        }
        return contactInfo;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item_list, null);
        ContactHolder contactViewHolder = new ContactHolder(view);
        Log.e(TAG, "onCreateViewHolder: creating view holder");
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

    public class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView contactName;
        TextView contactNumber;
        View container;
        ImageView contactImage;

        public ContactHolder(View itemView) {
            super(itemView);
            container = (View) itemView.findViewById(R.id.list_container);
            container.setOnClickListener(this);
            contactName = (TextView) itemView.findViewById(R.id.contact_name_text);
            contactNumber = (TextView) itemView.findViewById(R.id.contact_number_text);
            contactImage = (ImageView) itemView.findViewById(R.id.contact_profile_pic);
        }

        @Override
        public void onClick(View v) {
                clickCallback.onItemClick(getAdapterPosition());
        }
    }
}
