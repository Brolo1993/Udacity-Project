package com.bubblex.popularmovie.Adpter;

/**
 * Created by Amr ElBrolosy on 02/04/15.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.astuetz.PagerSlidingTabStrip;
import com.bubblex.popularmovie.Fragments.Favourite;
import com.bubblex.popularmovie.Fragments.HomeFragment;

/**
 * Created by Amr ElBrolosy on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    int icons[];

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, int mNumbOfTabsumb, int icons[]) {
        super(fm);
        this.icons = icons;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeFragment();

        } else return new Favourite();
    }

    // This method return the titles for the Tabs in the Tab Strip

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    @Override
    public int getPageIconResId(int position) {
        return icons[position];
    }
}