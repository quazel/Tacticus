package com.bramble.kickback.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.GridView;

import com.bramble.kickback.R;
import com.bramble.kickback.adapters.MainActivityPageAdapter;
import com.bramble.kickback.adapters.OnlineTileAdapter;
import com.bramble.kickback.fragments.AddFriendsFragment;
import com.bramble.kickback.fragments.CameraFragment;
import com.bramble.kickback.fragments.FriendsFragment;
import com.bramble.kickback.fragments.GalleryFragment;
import com.bramble.kickback.fragments.HomeFragment;
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
    private CameraFragment cameraFragment;
    private GridView friendGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialization
        homeFragment = new HomeFragment();
        cameraFragment = new CameraFragment();
        fragments = new ArrayList<Fragment>();
        fragments.add(new AddFriendsFragment());
        fragments.add(new FriendsFragment());
        fragments.add(homeFragment);
        fragments.add(cameraFragment);
        fragments.add(new GalleryFragment());
        // insert personal gallery here maybe something like this
        /*
        for(int i = 0; i < gallery.size(); i ++) {
            tempGalleryFragment = new GalleryFragment();
            tempGalleryFragment.setPicture(this.gallery.get(i));
            fragments.add(tempGalleryFragment);
        }
        */

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
        viewPager.setOffscreenPageLimit(5);

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
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + selectedFriends.get(0).getPhoneNumber()));
        startActivity(callIntent);
    }

    public void textSinglePressed(View view) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", selectedFriends.get(0).getPhoneNumber());
        smsIntent.putExtra("sms_body","Let's Kickback.");
        startActivity(smsIntent);
    }

    public void textSeveralPressed(View view) {
        String separator = "; ";
        if(android.os.Build.MANUFACTURER.equalsIgnoreCase("samsung")){
            separator = ", ";
        }
        String listOfPhoneNumbers ="";
        for(int i = 0; i < selectedFriends.size(); i ++) {
            if(i == 0) {
                listOfPhoneNumbers = selectedFriends.get(i).getPhoneNumber();
            }
            else {
                listOfPhoneNumbers = listOfPhoneNumbers + separator + selectedFriends.get(i).getPhoneNumber();
            }
        }
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", listOfPhoneNumbers);
        smsIntent.putExtra("sms_body","Let's Kickback.");
        startActivity(smsIntent);
    }

    public void cancelSeveralPressed(View view) {
        friendGrid = (GridView) findViewById(R.id.friendGrid);
        friendGrid.clearChoices();
        friendGrid.requestLayout();
        ((OnlineTileAdapter)friendGrid.getAdapter()).clearSelectedItems();
    }

    public void upperFeedPressed(View view) {
        // feed directory
    }

    public void lowerFeedPressed(View view) {
        // feed directory
    }

    public void takePicturePressed(View view) {
        cameraFragment.capturePicture();
    }

    public void friendsBackButtonPressed(View view) {
        viewPager.setCurrentItem(2);
    }

    public void addFriendsBackButtonPressed(View view) {
        viewPager.setCurrentItem(1);
    }

    public void galleryBackButtonPressed(View view) {
        viewPager.setCurrentItem(3);
    }

    public void cameraBackButtonPressed(View view) {
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
        homeFragment.updateMarquee(this.selectedFriends);
    }

    public void onBackPressed() {
        if(viewPager.getCurrentItem()==2) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you wish to exit Kickback?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Main.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else if(viewPager.getCurrentItem()==0) {
            viewPager.setCurrentItem(1);
        }
        else if(viewPager.getCurrentItem()==1) {
            viewPager.setCurrentItem(2);
        }
        else if(viewPager.getCurrentItem()==3) {
            viewPager.setCurrentItem(2);
        }
        else if(viewPager.getCurrentItem()==4) {
            viewPager.setCurrentItem(3);
        }
    }

}
