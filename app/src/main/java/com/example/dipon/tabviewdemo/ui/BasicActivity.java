package com.example.dipon.tabviewdemo.ui;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.dipon.tabviewdemo.R;


import java.util.ArrayList;

import service.NavigationListAdapter;

public class BasicActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, NavigationListAdapter.OnItemClickListener {

    private ArrayList<Drawable> icons;
    private ArrayList <String> labels;
    private DrawerLayout navDrawer;
    private RecyclerView navList;
    private TextView title;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    private TabLayout tabLayout;
    private TextView tabContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0f);

        tabLayout = (TabLayout) findViewById(R.id.tbl_basic);
        tabContent = (TextView) findViewById(R.id.lbl_basic_content);

        //adding tab

        tabLayout.addTab(tabLayout.newTab().setText("CallLog"));
        tabLayout.addTab(tabLayout.newTab().setText("Favorite"));
        tabLayout.addTab(tabLayout.newTab().setText("contact"));

        tabLayout.setTabTextColors(ContextCompat.getColor(this, android.R.color.white),
                ContextCompat.getColor(this, R.color.colorAccent));
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));


        icons = new ArrayList<>();
        icons.add(ContextCompat.getDrawable(this,R.drawable.ic_dashboard_black_24dp));
        icons.add(ContextCompat.getDrawable(this,R.drawable.ic_event_black_24dp));
        icons.add(ContextCompat.getDrawable(this,R.drawable.ic_settings_black_24dp));

        labels = new ArrayList<>();
        labels.add("Dashboard");
        labels.add("Calendar");
        labels.add("Preference");




         setUpNavDrawer(icons,labels);
        tabLayout.addOnTabSelectedListener(this);
    }

    private void setUpNavDrawer(ArrayList <Drawable> icons, ArrayList <String> labels) {
        navDrawer = (DrawerLayout) findViewById(R.id.nvd_act_main);
        NavigationListAdapter adapter = new NavigationListAdapter(this, icons, labels);
        adapter.setOnClickListener(this);

        navList = (RecyclerView) findViewById(R.id.lst_nav_drawer);
        navList.setAdapter(adapter);

        toggle = new ActionBarDrawerToggle(this, navDrawer, toolbar, R.string.open, R.string.close) {

            public void onDrawerClosed(View drawer) {
                super.onDrawerClosed(drawer);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawer) {
                super.onDrawerOpened(drawer);
                invalidateOptionsMenu();
            }
        };
        // for animation properly
        navDrawer.addDrawerListener(toggle);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tabContent.setText(tab.getText().toString());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationItemClick(int position) {
        switch (position) {
            case 0:
                tabContent.setText("Dashboard Activity");
                break;
            case 1:
                tabContent.setText("Calendar Activity");
                break;
            case 2:
                tabContent.setText("Preference Activity");
                break;
        }

    }
}
