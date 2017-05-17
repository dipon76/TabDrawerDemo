package com.example.dipon.tabviewdemo.main.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
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
import com.example.dipon.tabviewdemo.main.data.CallInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dipon on 5/16/2017.
 */

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.LogHolder> {
    private static final String TAG = "Call Log Adapter" ;
    private Context context;
    private Cursor callLogCursor;
    private ClickCallBack clickCallBack;
    private Fragment fragment;

    public CallLogAdapter(Context context) {
        this.context = context;
    }

    public CallLogAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
    }

    public void swapCursor (Cursor cursor) {
        if (cursor == null) {
            Log.d(TAG, "swapCursor: null parameter");
        }
        this.callLogCursor = cursor;
        notifyDataSetChanged();
    }

    public String timeFormatter (String format_24) {
        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm:ss a");
            Date _24HourDt = _24HourSDF.parse(format_24);
            return _12HourSDF.format(_24HourDt).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public LogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.call_log_list_item, null);
        CallLogAdapter.LogHolder logHolder = new CallLogAdapter.LogHolder(view);
        Log.e(TAG, "onCreateViewHolder: creating view holder");
        return logHolder;
    }

    @Override
    public void onBindViewHolder(LogHolder holder, int position) {
        CallInfo callInfo = getCallInfoFromCursor(position) ;
        if( callInfo == null) {
            Log.d(TAG, "onBindViewHolder: null info");
        } else if (callInfo.getCallerName() != null) {
            holder.callerName.setText(callInfo.getCallerName());
            holder.callDate.setText(callInfo.getCallDate());
            holder.callDuration.setText(callInfo.getCallDuration());
            ImageUtil.setImageButTextImageOnException(fragment,callInfo.getCallerImage(),holder.callerPic, callInfo.getCallerName());
        } else {
            holder.callerName.setText(callInfo.getCallerNumber());
            holder.callDate.setText(callInfo.getCallDate());
            holder.callDuration.setText(callInfo.getCallDuration());
            ImageUtil.setImageButTextImageOnException(fragment,callInfo.getCallerImage(),holder.callerPic, callInfo.getCallerNumber());
        }

        switch (callInfo.getCallType()) {
            case "Outgoing" :
                holder.callType.setImageResource(R.drawable.ic_call_made_black_18dp);
                break;
            case "Incoming" :
                holder.callType.setImageResource(R.drawable.ic_call_received_black_18dp);
                break;
            case "Missed" :
                holder.callType.setImageResource(R.drawable.ic_call_missed_black_18dp);
                break;
        }
    }

    private ContactSummary getContactSummary(String number) {

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

    public CallInfo getCallInfoFromCursor(int position) {
        CallInfo callInfo = null;
        if (callLogCursor != null) {
            Log.d(TAG, "getCallInfoFromCursor: entered not null");
        }


        assert callLogCursor != null;
        int number = callLogCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int callId = callLogCursor.getColumnIndex(CallLog.Calls._ID);
        int type = callLogCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = callLogCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = callLogCursor.getColumnIndex(CallLog.Calls.DURATION);

        if (callLogCursor != null) {
            callLogCursor.moveToPosition(position);
            int call_id = callLogCursor.getInt(callId);
            String phNumber = callLogCursor.getString(number);
            ContactSummary contactSummary = getContactSummary(phNumber);
            String conName = contactSummary.getConName();
            String conImageUri = contactSummary.getConImageUri();
            String callType = callLogCursor.getString(type);
            String callDate = callLogCursor.getString(date);
            Date callDateString = new Date(Long.valueOf(callDate));

            String []result = callDateString.toString().split(" ");
            String finalTime = result[0]+", "+result[2]+" "+result[1]+", "+ timeFormatter(result[3]);

            String callDuration = callLogCursor.getString(duration);
            Integer callDur = Integer.valueOf(callDuration);
            int newDurMin = callDur/60;
            int newDueSec= callDur%60;
            String finalDur = Integer.toString(newDurMin) + " min "+ Integer.toString(newDueSec) + " sec";
            callInfo = new CallInfo(call_id, conName, phNumber, finalDur, finalTime,conImageUri);

            int callTypeCode = Integer.parseInt(callType);
            switch (callTypeCode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    callInfo.setCallType("Outgoing");
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    callInfo.setCallType("Incoming");
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    callInfo.setCallType("Missed");
                    break;
            }

        }
        return callInfo;
    }

    @Override
    public int getItemCount() {
        if(callLogCursor == null) {
            return 0;
        }
        return callLogCursor.getCount();
    }

    public interface ClickCallBack {
        void onCallClick (int p);
    }

    public void setClickCallBack(ClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public class LogHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView callerName;
        TextView callDuration;
        TextView callDate;
        ImageView callType;
        View container;
        ImageView callerPic;
        public LogHolder(View itemView) {
            super(itemView);

            callerName = (TextView) itemView.findViewById(R.id.con_name_text);
            callDuration = (TextView) itemView.findViewById(R.id.call_duration);
            callDate = (TextView) itemView.findViewById(R.id.call_date);
            callType = (ImageView) itemView.findViewById(R.id.icon_call_status);
            callerPic = (ImageView) itemView.findViewById(R.id.call_log_profile_pic);
            container = itemView.findViewById(R.id.callLog_container);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickCallBack.onCallClick(getAdapterPosition());
        }
    }

    private class ContactSummary {
        private String conName;
        private String conImageUri;

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
    }
}
