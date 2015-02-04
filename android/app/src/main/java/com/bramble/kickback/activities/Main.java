package com.bramble.kickback.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bramble.kickback.R;
import com.bramble.kickback.adapters.MainActivityPageAdapter;
import com.bramble.kickback.fragments.AddFriendsFragment;
import com.bramble.kickback.fragments.AddPlanFragment;
import com.bramble.kickback.fragments.FriendsFragment;
import com.bramble.kickback.fragments.HomeFragment;
import com.bramble.kickback.fragments.PlannerFragment;
import com.bramble.kickback.models.Friend;

import java.util.ArrayList;

public class Main extends Activity {

    private ArrayList<Fragment> fragments;
    private AddPlanFragment addPlanFragment;
    private PlannerFragment plannerFragment;
    private HomeFragment homeFragment;
    private FriendsFragment friendsFragment;
    private AddFriendsFragment addFriendsFragment;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private ViewPager viewPager;
    private MainActivityPageAdapter mAdapter;
    // for home fragment tab
    ArrayList<Friend> selectedFriends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialization
        fragments = new ArrayList<Fragment>();
        addPlanFragment = new AddPlanFragment();
        plannerFragment = new PlannerFragment();
        homeFragment = new HomeFragment();
        friendsFragment = new FriendsFragment();
        addFriendsFragment = new AddFriendsFragment();
        fragments.add(addPlanFragment);
        fragments.add(plannerFragment);
        fragments.add(homeFragment);
        fragments.add(friendsFragment);
        fragments.add(addFriendsFragment);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fm = getFragmentManager();
        mAdapter = new MainActivityPageAdapter(fm, fragments);

        viewPager.setAdapter(mAdapter);
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

        selectedFriends = new ArrayList<Friend>();
    }

    // home functionality
    public void goOnlinePressed(View view) {
        homeFragment.goOnline();
    }

    public void goOfflinePressed(View view) {
        homeFragment.goOffline();
    }

    public void friendsBackButtonPressed(View view) {
        viewPager.setCurrentItem(2);
    }

    public void addFriendsBackButtonPressed(View view) {
        viewPager.setCurrentItem(3);
    }

    public void addPlanBackButtonPressed(View view) {
        viewPager.setCurrentItem(1);
    }

    public void plannerBackButtonPressed(View view) {
        viewPager.setCurrentItem(2);
    }
}
