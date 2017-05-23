package com.example.dipon.tabviewdemo.main.adapters;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dipon.tabviewdemo.R;
import com.example.dipon.tabviewdemo.main.UI_utility.ImageUtil;
import com.example.dipon.tabviewdemo.main.data.ContactInfo;

/**
 * @author Dipon
 *         on 5/21/2017.
 */

public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private static final String TAG = "Bottom Sheet" ;
    private ContactInfo contactInfo;

    private ImageView contactDetailImage;
    private TextView contactDetailName;
    private TextView contactDetailNumber;
    private ImageView favouriteIcon;
    private ImageView editIcon;
    private ImageView smsIcon;
    private View contactNumberRow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    private void setUpContentOfView (View v) {

        Bundle bundle = getArguments();
        contactInfo = (ContactInfo) bundle.getSerializable("contactInfo");
        contactDetailImage = (ImageView) v.findViewById(R.id.contact_details_image);
        contactDetailName = (TextView) v.findViewById(R.id.contact_detail_name);
        contactDetailNumber = (TextView) v.findViewById(R.id.contact_detail_number);
        favouriteIcon = (ImageView) v.findViewById(R.id.icon_favourite);
        favouriteIcon.setOnClickListener(this);
        editIcon = (ImageView) v.findViewById(R.id.icon_edit);
        editIcon.setOnClickListener(this);
        contactNumberRow = v.findViewById(R.id.details_container);
        contactNumberRow.setOnClickListener(this);
        smsIcon = (ImageView) v.findViewById(R.id.icon_sms);
        smsIcon.setOnClickListener(this);

        Log.e(TAG, "setUpContentOfView: "+contactInfo.getIsStarred());

        if (Integer.valueOf(contactInfo.getIsStarred()) == 0) {
            favouriteIcon.setImageResource(R.drawable.ic_star_border_white_24dp);
        }

        ImageUtil.setImageButDefaultImageOnException(this,contactInfo.getContactImage(), contactDetailImage,contactInfo.getContactName(),R.drawable.ic_person_black_24dp);
        contactDetailName.setText(contactInfo.getContactName());
        contactDetailNumber.setText(contactInfo.getContactNumber());
    }
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.contact_details, null);
        setUpContentOfView(contentView);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.icon_favourite) {
            if (Integer.valueOf(contactInfo.getIsStarred()) == 0) {
                favouriteIcon.setImageResource(R.drawable.ic_star_white_24dp);
                ContentValues contentValues = new ContentValues();
                contentValues.put(ContactsContract.Contacts.STARRED, "1");
                contactInfo.setIsStarred("1");
                getContext().getContentResolver().update(ContactsContract.Contacts.CONTENT_URI, contentValues, ContactsContract.Contacts.LOOKUP_KEY + "= ?",  new String[]{contactInfo.getContactLookUpKey()+""});
            } else {
                favouriteIcon.setImageResource(R.drawable.ic_star_border_white_24dp);
                ContentValues contentValues = new ContentValues();
                contentValues.put(ContactsContract.Contacts.STARRED, "0");
                contactInfo.setIsStarred("0");
                getContext().getContentResolver().update(ContactsContract.Contacts.CONTENT_URI, contentValues, ContactsContract.Contacts.LOOKUP_KEY + "= ?",  new String[]{contactInfo.getContactLookUpKey()+""});
            }

        } else if (v.getId() == R.id.icon_edit) {
            Uri lookUpUri = ContactsContract.Contacts.getLookupUri(Long.valueOf(contactInfo.getContactId()), contactInfo.getContactLookUpKey());
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setDataAndType(lookUpUri, ContactsContract.Contacts.CONTENT_ITEM_TYPE);
            intent.putExtra("finishActivityOnSaveCompleted", true);
            startActivity(intent);

        } else if (v.getId() == R.id.details_container) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+ contactInfo.getContactNumber()));
            intent.putExtra("finishActivityOnSaveCompleted", true);
            startActivity(intent);
        } else if ( v.getId() == R.id.icon_sms) {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", contactInfo.getContactNumber());
            startActivity(smsIntent);
        }
    }
}