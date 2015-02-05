package com.bramble.kickback.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.bramble.kickback.R;
import com.bramble.kickback.adapters.MainActivityPageAdapter;
import com.bramble.kickback.fragments.AddFriendsFragment;
import com.bramble.kickback.fragments.AddPlanFragment;
import com.bramble.kickback.fragments.FriendsFragment;
import com.bramble.kickback.fragments.HomeFragment;
import com.bramble.kickback.fragments.PlannerFragment;
import com.bramble.kickback.models.Friend;

import java.util.ArrayList;
import java.util.List;

public class Main extends Activity {

    private ArrayList<Fragment> fragments;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private ViewPager viewPager;
    private MainActivityPageAdapter mAdapter;
    // for home fragment
    private List<Friend> selectedFriends;
    private HomeFragment homeFragment;
    private GridView friendGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialization
        homeFragment = new HomeFragment();
        friendGrid = (GridView) findViewById(R.id.friendGrid);
        fragments = new ArrayList<Fragment>();
        fragments.add(new AddPlanFragment());
        fragments.add(new PlannerFragment());
        fragments.add(homeFragment);
        fragments.add(new FriendsFragment());
        fragments.add(new AddFriendsFragment());

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

    public void callSinglePressed(View view) {

    }

    public void textSinglePressed(View view) {

    }

    public void textSeveralPressed(View view) {

    }

    public void cancelSeveralPressed(View view) {
        //friendGrid.clearChoices();
        //friendGrid.requestLayout();
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

    public void updateSelectedFriends(List<Friend> selectedFriends) {
        this.selectedFriends = selectedFriends;
        if(selectedFriends.size() == 0){
            homeFragment.replaceWithGoOffline();
        }
        else if(selectedFriends.size() == 1) {
            homeFragment.replaceWithCallText();
        }
        else {
            homeFragment.replaceWithTextCancel();
        }
    }
}
