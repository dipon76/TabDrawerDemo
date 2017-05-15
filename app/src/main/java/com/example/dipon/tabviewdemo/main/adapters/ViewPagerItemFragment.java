package com.example.dipon.tabviewdemo.main.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dipon.tabviewdemo.R;

/**
 * Created by Dipon on 4/5/2017.
 */

public class ViewPagerItemFragment extends Fragment implements ContactAdapter.ClickCallback {
    private static final String PAGE_TITLE = "PAGE_TITLE";
    private static final int VERTICAL_ITEM_SPACE = 20;
    private String TAG = "Fragment Item" ;
    private String pageTitle;
    private FragmentPagerItemCallback callback;
    private ContactAdapter contactAdapter;

    public ViewPagerItemFragment(){}

    public static ViewPagerItemFragment getInstance(String pageTitle){
        ViewPagerItemFragment fragment = new ViewPagerItemFragment();
        Bundle args = new Bundle();
        args.putString(PAGE_TITLE, pageTitle);
        fragment.setArguments(args);
        return fragment;
    }

    public Cursor getAllContacts() throws RemoteException {
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        return cursor;
    }

    private class ContactLoader extends AsyncTask<Void,Void,Cursor> {
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
                RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.contact_list);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                contactAdapter = new ContactAdapter(getContext());
                contactAdapter.setClickCallback(this);
                recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
                recyclerView.setAdapter (contactAdapter);

                ContactLoader contactLoader = new ContactLoader ();
                contactLoader.execute();

                break;
            default:
                v = inflater.inflate(R.layout.fragment_view_pager_item, container, false);
                TextView content = ((TextView)v.findViewById(R.id.lbl_pager_item_content));
                content.setText(pageTitle);
                content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onPagerItemClick(
                                ((TextView)v).getText().toString()
                        );
                    }
                });


        }


        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentPagerItemCallback) {
            callback = (FragmentPagerItemCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentPagerItemCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onItemClick(int p) {

    }

    public interface  FragmentPagerItemCallback {
        void onPagerItemClick(String message);
    }
}
