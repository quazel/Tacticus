package com.bramble.kickback.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bramble.kickback.R;
import com.bramble.kickback.adapter.MainActivityPageAdapter;
import com.bramble.kickback.fragments.AddFriendsFragment;
import com.bramble.kickback.fragments.AddPlanFragment;
import com.bramble.kickback.fragments.FriendsFragment;
import com.bramble.kickback.fragments.HomeFragment;
import com.bramble.kickback.fragments.OfflineFragment;
import com.bramble.kickback.fragments.OnlineFragment;
import com.bramble.kickback.fragments.PlannerFragment;
import com.bramble.kickback.models.Friend;

import java.util.ArrayList;

public class Main extends Activity implements ActionBar.TabListener{

    private ArrayList<Fragment> fragments;
    private AddFriendsFragment addFriendsFragment;
    private FriendsFragment friendsFragment;
    private HomeFragment homeFragment;
    private PlannerFragment plannerFragment;
    private AddPlanFragment addPlanFragment;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private ViewPager viewPager;
    private MainActivityPageAdapter mAdapter;
    private ActionBar actionBar;
    // for home fragment tab
    ArrayList<Friend> onlineFriends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialization
        fragments = new ArrayList<Fragment>();
        addFriendsFragment = new AddFriendsFragment();
        friendsFragment = new FriendsFragment();
        homeFragment = new HomeFragment();
        plannerFragment = new PlannerFragment();
        addPlanFragment = new AddPlanFragment();
        fragments.add(addFriendsFragment);
        fragments.add(friendsFragment);
        fragments.add(homeFragment);
        fragments.add(plannerFragment);
        fragments.add(addPlanFragment);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        actionBar = getActionBar();
        fm = getFragmentManager();
        mAdapter = new MainActivityPageAdapter(fm, fragments);

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        viewPager.setCurrentItem(2);
    }

    // home functionality
    public void goOnlinePressed(View view) {
        homeFragment.goOnline();
    }

    public void goOfflinePressed(View view) {
        homeFragment.goOffline();
    }


    // tab stuff
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    
}
