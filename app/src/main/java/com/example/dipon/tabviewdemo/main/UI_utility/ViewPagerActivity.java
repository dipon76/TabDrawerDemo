package com.example.dipon.tabviewdemo.main.UI_utility;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.dipon.tabviewdemo.R;

import java.util.ArrayList;

import com.example.dipon.tabviewdemo.main.adapters.FragmentAdapter;
import com.example.dipon.tabviewdemo.main.adapters.NavigationListAdapter;

public class ViewPagerActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, NavigationView.OnNavigationItemSelectedListener  {

    private ArrayList<Drawable> icons;
    private ArrayList <String> labels;
    private DrawerLayout navDrawer;
    private RecyclerView navList;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private TabLayout tabLayout;
    private ViewPager pager;

    private static final String[] pageTitles = {"Call Log", "Favourite", "Contact"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        initialize();
        setUpNavDrawer(icons,labels);
        setUpPagerAndTabs();
    }

    private void initialize() {
        setUpToolbar();

        tabLayout = (TabLayout) findViewById(R.id.tbl_basic);
        pager = (ViewPager) findViewById(R.id.vpg_main);

//        icons = new ArrayList<>();
//        icons.add(ContextCompat.getDrawable(this,R.drawable.ic_dashboard_black_24dp));
//        icons.add(ContextCompat.getDrawable(this,R.drawable.ic_event_black_24dp));
//        icons.add(ContextCompat.getDrawable(this,R.drawable.ic_settings_black_24dp));
//
//        labels = new ArrayList<>();
//        labels.add("Contact Dashboard");
//        labels.add("SMS Dashboard");
//        labels.add("Preference");


    }

    private void setUpToolbar() {

        toolbar = (Toolbar) findViewById(R.id.app_toolbar1);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0f);
    }


    private void setUpPagerAndTabs(){

        tabLayout.setTabTextColors(ContextCompat.getColor(this, android.R.color.white),
                ContextCompat.getColor(this, R.color.bacground_shade));
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), pageTitles);
        pager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(pager);  // automated creating tabs

    }

    private void setUpNavDrawer(ArrayList <Drawable> icons, ArrayList <String> labels) {
        navDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

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
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.contact_dashboard) {
            Intent intent = new Intent(this, ViewPagerActivity.class);
            startActivity(intent);

        } else if (id == R.id.sms_dashboard) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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


//    @Override
//    public void onNavigationItemClick(int position) {
//        switch (position) {
//            case 0:
//                Intent intent = new Intent(this, ViewPagerActivity.class);
//                startActivity(intent);
//                break;
//            case 1:
//                //tabContent.setText("Calendar Activity");
//                break;
//            case 2:
//                //tabContent.setText("Preference Activity");
//                break;
//        }
//
//    }

}
