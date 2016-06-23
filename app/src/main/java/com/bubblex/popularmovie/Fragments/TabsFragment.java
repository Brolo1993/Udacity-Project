package com.bubblex.popularmovie.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.bubblex.popularmovie.Adpter.ViewPagerAdapter;
import com.bubblex.popularmovie.R;

/**
 * Created by Amr ElBrolosy on 26/12/2015.
 */
public class TabsFragment extends Fragment {

    int Numboftabs = 2;
    int icnons[] = {R.drawable.movie,
            R.drawable.white};
    ViewPager pager;
    PagerSlidingTabStrip tabs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tabs_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pager = (ViewPager) getActivity().findViewById(R.id.pager);
        pager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager(), Numboftabs, icnons));
        tabs = (PagerSlidingTabStrip) getActivity().findViewById(R.id.tabs);
        tabs.setViewPager(pager);
    }
}
