package com.bramble.kickback.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bramble.kickback.R;

public class HomeFragment extends Fragment {

    public OnlineFragment onlineFragment;
    public OfflineFragment offlineFragment;
    public FragmentManager homeManager;
    public FragmentTransaction homeTransaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        offlineFragment = new OfflineFragment();
        onlineFragment = new OnlineFragment();
        homeManager = getFragmentManager();
        homeTransaction = homeManager.beginTransaction();
        homeTransaction.add(R.id.home_container, offlineFragment);
        homeTransaction.commit();

        return view;
    }

    public void goOnline() {
        homeTransaction = homeManager.beginTransaction();
        homeTransaction.replace(R.id.home_container, onlineFragment);
        homeTransaction.commit();
    }

    public void goOffline() {
        homeTransaction = homeManager.beginTransaction();
        homeTransaction.replace(R.id.home_container, offlineFragment);
        homeTransaction.commit();
    }
}