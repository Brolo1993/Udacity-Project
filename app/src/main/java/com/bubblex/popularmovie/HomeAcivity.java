package com.bubblex.popularmovie;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.bubblex.popularmovie.Adpter.ViewPagerAdapter;
import com.bubblex.popularmovie.Fragments.HomeFragment;
import com.bubblex.popularmovie.Fragments.TabsFragment;

public class HomeAcivity extends AppCompatActivity {
    public static Fragment fragmenttabs, fragmentmovie;
    public static FragmentManager fragmentManager;
    Toolbar toolbar;
    int Numboftabs = 2;
    int icnons[] = {R.drawable.movie,
            R.drawable.white};
    ViewPager pager;
    PagerSlidingTabStrip tabs;
    String URLpop = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=0de86d2d856ff06e1fbf1e83861ac32a";
    String URLhigh = "https://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=0de86d2d856ff06e1fbf1e83861ac32a";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_acivity);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        if (getResources().getBoolean(R.bool.tab)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            fragmenttabs = new TabsFragment();
//            fragmentmovie = new ResultFragment();
            fragmentManager = getSupportFragmentManager();
            fragmenttabs = new TabsFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.tabsFragment, fragmenttabs).commit();

//            fragmentManager.beginTransaction().replace(R.id.resultFragment, fragmentmovie);
        } else {
            pager = (ViewPager) findViewById(R.id.pager);
            pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), Numboftabs, icnons));
            tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
            tabs.setViewPager(pager);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_pop:
                Toast.makeText(this,"Sorted By Popularity",Toast.LENGTH_SHORT).show();
                HomeFragment.IntialMovies(URLpop);

                break;
            case R.id.action_high:
                Toast.makeText(this,"Sorted By Highest Rated",Toast.LENGTH_SHORT).show();
                HomeFragment.IntialMovies(URLhigh);
                break;
        }
        return true;
    }


}
