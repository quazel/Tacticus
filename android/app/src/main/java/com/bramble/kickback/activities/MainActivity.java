package com.bramble.kickback.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.bramble.kickback.R;
import com.bramble.kickback.adapter.MainActivityPageAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    MainActivityPageAdapter pageAdapter;
    FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);
        List<Fragment> fragments = getFragments();
        fm = getFragmentManager();
        pageAdapter = new MainActivityPageAdapter(fm, fragments);
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);
    }

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();

        // add fragments to list in correct order

        return fList;
    }
}
