package com.example.dipon.tabviewdemo.main.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.io.Serializable;

/**
 * Created by Dipon on 4/6/2017.
 */

public class ContactInfo implements Serializable {

    private String contactName;
    private String contactNumber;
    private String contactImage;
    private boolean isSelected;
    private String contactId;
    private String contactLookUpKey;
    private String isStarred ;

    public ContactInfo() {
    }

    public String getContactLookUpKey() {
        return contactLookUpKey;
    }

    public void setContactLookUpKey(String contactLookUpKey) {
        this.contactLookUpKey = contactLookUpKey;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getIsStarred() {
        return isStarred;
    }

    public void setIsStarred(String isStarred) {
        this.isStarred = isStarred;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactImage() {
        return contactImage;
    }

    public void setContactImage(String contactImage) {
        this.contactImage = contactImage;
    }

    public ContactInfo getContactInfoFromCursor(int adapterPosition , Context context, Cursor contactCursor) {
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
                    String lookUpKey = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                    contactInfo.setIsStarred(isStarred);
                    contactInfo.setContactLookUpKey(lookUpKey);
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
}
