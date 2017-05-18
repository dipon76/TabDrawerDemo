package com.example.dipon.tabviewdemo.main.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * @author Dipon
 *         on 5/18/2017.
 */

public class ContactSummary {
    private String conName;
    private String conImageUri;

    public ContactSummary() {
    }

    public ContactSummary(String conName, String conImageUri) {
        this.conName = conName;
        this.conImageUri = conImageUri;
    }

    public String getConName() {
        return conName;
    }

    public String getConImageUri() {
        return conImageUri;
    }

    public ContactSummary getContactSummary(String number, Context context) {

        String name = null;
        String photoUri = null;
        // define the columns I want the query to return
        String[] projection = new String[] {
                ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.PhoneLookup._ID,
                ContactsContract.PhoneLookup.PHOTO_URI};

        // encode the phone number and build the filter URI
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

        // query time
        Cursor cursor = context.getContentResolver().query(contactUri, projection, null, null, null);

        if(cursor != null) {
            if (cursor.moveToFirst()) {
                name =      cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                photoUri =   cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.PHOTO_URI));
                Log.v(TAG, "Started uploadcontactphoto: Contact Found @ " + number);
                Log.v(TAG, "Started uploadcontactphoto: Contact name  = " + name);
            } else {
                Log.v(TAG, "Contact Not Found @ " + number);
            }
            cursor.close();
        }
        ContactSummary contactSummary = new ContactSummary(name, photoUri);
        return contactSummary;
    }
}
