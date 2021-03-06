package com.example.dipon.tabviewdemo.main.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Dipon on 5/14/2017.
 */

  /**
   * For Pagers with a smaller/static number of pages, use FragmentPagerAdapter. It keeps visited
caches fragments which have been opened in memory. If you require a large/dynamic number
of pages, use FragmentStatePagerAdapter instead.
*/

public class FragmentAdapter extends FragmentPagerAdapter {

    private String [] pageTitles;

    public FragmentAdapter (FragmentManager manager , String[] titles){
        super(manager);
        this.pageTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return ViewPagerItemFragment.getInstance(pageTitles[position]);
    }

    @Override
    public int getCount() {
        return pageTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return pageTitles[position];
    }
}
