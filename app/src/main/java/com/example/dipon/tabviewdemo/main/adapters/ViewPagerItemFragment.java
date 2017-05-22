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
import android.support.design.widget.BottomSheetDialogFragment;
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
import android.widget.GridView;
import android.widget.Toast;

import com.example.dipon.tabviewdemo.R;
import com.example.dipon.tabviewdemo.main.data.CallInfo;
import com.example.dipon.tabviewdemo.main.data.ContactInfo;

/**
 * Created by Dipon on 4/5/2017.
 */

public class ViewPagerItemFragment extends Fragment implements ContactAdapter.ClickCallback , LoaderManager.LoaderCallbacks <Cursor>, CallLogAdapter.ClickCallBack {

    private static final String PAGE_TITLE = "PAGE_TITLE";

    private static final int CALL_LOG_LOADER_ID = 1;
    private static final int FAVOURITE_LOADER_ID = 2;
    private static final int CONTACT_LOADER_ID = 3;

    private static final int VERTICAL_ITEM_SPACE = 0;
    private String TAG = "Fragment Item" ;
    private String pageTitle;
    private ContactAdapter contactAdapter;
    private RecyclerView recyclerView;

    private LoaderManager loaderManager;
    private CursorLoader cursorLoader;
    private CallLogAdapter callLogAdapter;
    private RecyclerView recyclerViewLog;

    private GridView gridView;
    private GridAdapter gridAdapter;

    private Cursor contactCursor;
    Cursor callLogCursor;

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
        if (id == CALL_LOG_LOADER_ID) {
            cursorLoader = new CursorLoader(getContext(), CallLog.Calls.CONTENT_URI,null,null,null,CallLog.Calls.DEFAULT_SORT_ORDER);
            Log.d(TAG, "onCreateLoader: call_log_loader");
        } else if (id == CONTACT_LOADER_ID) {
            cursorLoader = new CursorLoader(getContext(),ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            Log.d(TAG, "onCreateLoader: Contact_loader_id");
        } else if (id == FAVOURITE_LOADER_ID) {
            String selection = ContactsContract.Contacts.STARRED + "='1'";
            cursorLoader = new CursorLoader(getContext(), ContactsContract.Contacts.CONTENT_URI, null, selection, null, null);
            Log.d(TAG, "onCreateLoader: contactloader");
        }

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {

        if (loader.getId() == CALL_LOG_LOADER_ID) {
            if(callLogAdapter!=null && cursor!=null) {
                Log.d(TAG, "onLoadFinished: ");
                callLogAdapter.swapCursor(cursor); //swap the new cursor in.
            } else
                Log.v(TAG,"OnLoadFinished: callLogAdapter is null");
        } else if (loader.getId() == CONTACT_LOADER_ID) {
            if(contactAdapter!=null && cursor!=null) {
                Log.d(TAG, "onLoadFinished: ");
                contactCursor = cursor;
                contactAdapter.swapCursor(cursor); //swap the new cursor in.
            } else
                Log.v(TAG,"OnLoadFinished: contactAdapter is null");
        } else if (loader.getId() == FAVOURITE_LOADER_ID) {
            if(gridAdapter!=null && cursor!=null) {
                Log.d(TAG, "onLoadFinished: ");
                gridAdapter.swapCursor(cursor); //swap the new cursor in.
            } else
                Log.v(TAG,"OnLoadFinished: gridAdapter is null");
        }
    }


    @Override
    public void onLoaderReset(Loader loader) {
        if (loader.getId() == CALL_LOG_LOADER_ID) {
            if(callLogAdapter!=null ) {
                Log.d(TAG, "onLoadFinished: ");
                callLogAdapter.swapCursor(null); //swap the null cursor in.
            } else
                Log.v(TAG,"OnLoadFinished: callLogAdapter is null");
        }  else if (loader.getId() == CONTACT_LOADER_ID) {
            if(contactAdapter != null ) {
                Log.d(TAG, "onLoadFinished: ");
                contactAdapter.swapCursor(null); //swap the null cursor in.
            } else
                Log.v(TAG,"OnLoadFinished: contactAdapter is null");
        } else if (loader.getId() == FAVOURITE_LOADER_ID) {
            if(gridAdapter!=null ) {
                Log.d(TAG, "onLoadFinished: ");
                gridAdapter.swapCursor(null); //swap the null cursor in.
            } else
                Log.v(TAG,"OnLoadFinished: gridAdapter is null");
        }
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
        View v = null;
        switch (pageTitle) {
            case "Contact" :
                v = inflater.inflate(R.layout.contact_layout, container, false);
                initializeContactViewAndLoader(v);
                break;
            case "Call Log" :
                v = inflater.inflate(R.layout.call_log_layout, container, false);
                initializeLogViewAndLoader(v);
                break;
            case "Favourite" :
                v = inflater.inflate(R.layout.fav_layout, container, false);
                initializeGridView(v);
                break;
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
//        ContactLoader contactLoader = new ContactLoader ();
//        contactLoader.execute();

        loaderManager = getLoaderManager();
        loaderManager.initLoader(CONTACT_LOADER_ID, null, this);
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
        loaderManager.initLoader(CALL_LOG_LOADER_ID,null,this);
    }

    private void initializeGridView (View v) {
        gridView = (GridView) v.findViewById(R.id.gridView);
        gridAdapter = new GridAdapter(getContext(), R.layout.grid_item_list, this);
        gridView.setAdapter(gridAdapter);

        loaderManager = getLoaderManager();
        loaderManager.initLoader(FAVOURITE_LOADER_ID, null, this);
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
        CallInfo callInfo = callLogAdapter.getCallInfoFromCursor(p);

        if (callInfo.getCallerName() != null) {
            Toast.makeText(getContext(),"Contact Info : "+callInfo.getCallDate().toString(),Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(),"Contact Name : "+callInfo.getCallDate().toString(),Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+ callInfo.getCallerNumber()));
        intent.putExtra("finishActivityOnSaveCompleted", true);
        startActivity(intent);

    }

    @Override
    public void onItemClick(int p) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo = contactInfo.getContactInfoFromCursor(p,getContext(),contactCursor);
        Bundle bundle = new Bundle();
        bundle.putSerializable("contactInfo",contactInfo);
        BottomSheetDialogFragment bottomSheetDialogFragment = new CustomBottomSheetDialogFragment();
        bottomSheetDialogFragment.setArguments(bundle);
        bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }

    @Override
    public void onItemLongClick(int p) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo = contactInfo.getContactInfoFromCursor(p,getContext(),contactCursor);

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+ contactInfo.getContactNumber()));
        intent.putExtra("finishActivityOnSaveCompleted", true);
        startActivity(intent);
    }
}
