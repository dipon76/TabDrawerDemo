package com.example.dipon.tabviewdemo.main.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dipon.tabviewdemo.R;
import com.example.dipon.tabviewdemo.main.UI_utility.ImageUtil;
import com.example.dipon.tabviewdemo.main.data.CallInfo;
import com.example.dipon.tabviewdemo.main.data.ContactInfo;

import java.text.ParseException;

/**
 * Created by Dipon on 4/5/2017.
 */

public class ViewPagerItemFragment extends Fragment implements ContactAdapter.ClickCallback , LoaderManager.LoaderCallbacks <Cursor>, CallLogAdapter.ClickCallBack {

    private static final String PAGE_TITLE = "PAGE_TITLE";
    private static final int VERTICAL_ITEM_SPACE = 0;
    private String TAG = "Fragment Item" ;
    private String pageTitle;
    private ContactAdapter contactAdapter;
    private RecyclerView recyclerView;

    private LoaderManager loaderManager;
    private CursorLoader cursorLoader;
    private CallLogAdapter callLogAdapter;
    private RecyclerView recyclerViewLog;

    public ViewPagerItemFragment(){}

    public static ViewPagerItemFragment getInstance(String pageTitle){
        ViewPagerItemFragment fragment = new ViewPagerItemFragment();
        Bundle args = new Bundle();
        args.putString(PAGE_TITLE, pageTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        /**
         * return new CursorLoader(
         this,   // Parent activity context
         CallLog.Calls.CONTENT_URI,        // Table to query
         null,     // Projection to return
         null,            // No selection clause
         null,            // No selection arguments
         null             // Default sort order
         );
         null selects all column
         */
        cursorLoader = new CursorLoader(getContext(), CallLog.Calls.CONTENT_URI,null,null,null,CallLog.Calls.DEFAULT_SORT_ORDER);
        Log.d(TAG, "onCreateLoader: ");
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {
        if(callLogAdapter!=null && cursor!=null) {
            Log.d(TAG, "onLoadFinished: ");
            callLogAdapter.swapCursor(cursor); //swap the new cursor in.
        } else
            Log.v(TAG,"OnLoadFinished: callLogAdapter is null");
    }


    @Override
    public void onLoaderReset(Loader loader) {
        if(callLogAdapter!=null )
            callLogAdapter.swapCursor(null); //swap the null cursor in.
        else
            Log.v(TAG,"OnLoadFinished: callLogAdapter is null");
    }


    private class ContactLoader extends AsyncTask<Void,Void,Cursor> {

        private Cursor getAllContacts() throws RemoteException {
            ContentResolver contentResolver = getContext().getContentResolver();
            Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            return cursor;
        }
        @Override
        protected void onPostExecute (Cursor aCursor) {
            super.onPostExecute (aCursor);
            contactAdapter.swapCursor(aCursor);

        }

        @Override
        protected Cursor doInBackground (Void... params) {
            try {
                Cursor dataCursor = getAllContacts();
                if (dataCursor == null ) {
                    Log.e(TAG, "doInBackground: cursor null" );
                }
                return dataCursor;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.pageTitle = getArguments().getString(PAGE_TITLE);
        } else {
            Log.d("TAG", "Well...crushed");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        switch (pageTitle) {
            case "Contact" :
                v = inflater.inflate(R.layout.contact_layout, container, false);
                initializeContactViewAndLoader(v);
                break;
            case "Call Log" :
                v = inflater.inflate(R.layout.call_log_layout, container, false);
                initializeLogViewAndLoader(v);
                break;
            default:
                v = inflater.inflate(R.layout.fragment_view_pager_item, container, false);
                TextView content = ((TextView)v.findViewById(R.id.lbl_pager_item_content));
                content.setText(pageTitle);
        }
        return v;
    }

    private void initializeContactViewAndLoader(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.contact_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contactAdapter = new ContactAdapter(getContext(),this);
        contactAdapter.setClickCallback(this);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        recyclerView.setItemAnimator(new DefaultItemAnimator () );
        recyclerView.setAdapter (contactAdapter);
        ContactLoader contactLoader = new ContactLoader ();
        contactLoader.execute();
    }

    private void initializeLogViewAndLoader(View v) {
        recyclerViewLog = (RecyclerView) v.findViewById(R.id.callLog_list);
        recyclerViewLog.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLog.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        callLogAdapter = new CallLogAdapter(getContext(),this);

        callLogAdapter.setClickCallBack(this);
        recyclerViewLog.setAdapter(callLogAdapter);
        recyclerViewLog.setItemAnimator(new DefaultItemAnimator());

        loaderManager = getLoaderManager();
        loaderManager.initLoader(1,null,this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    /**
     *
     * @modified by Dipon
     * on 17/5/17
     */

    @Override
    public void onCallClick(int p) {
        CallInfo callInfo = null;
        callInfo = callLogAdapter.getCallInfoFromCursor(p);

        if (callInfo.getCallerName() != null) {
            Toast.makeText(getContext(),"Contact Info : "+callInfo.getCallDate().toString(),Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(),"Contact Name : "+callInfo.getCallDate().toString(),Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+ callInfo.getCallerNumber()));
        getContext().startActivity(intent);

    }

    @Override
    public void onItemClick(int p) {
        ContactInfo contactInfo = contactAdapter.getContactInfoFromCursor(p);
        Toast.makeText(getContext(),"Contact Name : "+contactInfo.getContactName(),Toast.LENGTH_SHORT).show();
    }
}
