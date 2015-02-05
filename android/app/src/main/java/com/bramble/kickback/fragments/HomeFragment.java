package com.bramble.kickback.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.bramble.kickback.R;

public class HomeFragment extends Fragment {

    public FragmentManager homeManager;
    public FragmentTransaction homeTransaction;
    public OfflineFragment offlineFragment;
    public OnlineFragment onlineFragment;
    public GoOfflineFragment goOfflineFragment;
    public CallTextFragment callTextFragment;
    public TextCancelFragment textCancelFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        offlineFragment = new OfflineFragment();
        onlineFragment = new OnlineFragment();
        goOfflineFragment = new GoOfflineFragment();
        callTextFragment = new CallTextFragment();
        textCancelFragment = new TextCancelFragment();

        homeManager = getFragmentManager();
        homeTransaction = homeManager.beginTransaction();
        homeTransaction.add(R.id.offline_container, offlineFragment);
        homeTransaction.commit();

        return view;
    }

    public void goOnline() {
        homeTransaction = homeManager.beginTransaction();
        homeTransaction.remove(offlineFragment);
        homeTransaction.add(R.id.online_container, onlineFragment);
        homeTransaction.add(R.id.online_button_container, goOfflineFragment);
        homeTransaction.commit();
        offlineFragment = new OfflineFragment();
    }

    public void goOffline() {
        homeTransaction = homeManager.beginTransaction();
        homeTransaction.remove(onlineFragment);
        homeTransaction.remove(goOfflineFragment);
        homeTransaction.add(R.id.offline_container, offlineFragment);
        homeTransaction.commit();
        onlineFragment = new OnlineFragment();
        goOfflineFragment = new GoOfflineFragment();
    }

    public void replaceWithGoOffline() {
        homeTransaction = homeManager.beginTransaction();
        homeTransaction.replace(R.id.online_button_container, goOfflineFragment);
        homeTransaction.commit();
        callTextFragment = new CallTextFragment();
        textCancelFragment = new TextCancelFragment();
    }

    public void replaceWithCallText() {
        homeTransaction = homeManager.beginTransaction();
        homeTransaction.replace(R.id.online_button_container, callTextFragment);
        homeTransaction.commit();
        textCancelFragment = new TextCancelFragment();
        goOfflineFragment = new GoOfflineFragment();
    }

    public void replaceWithTextCancel() {
        homeTransaction = homeManager.beginTransaction();
        homeTransaction.replace(R.id.online_button_container, textCancelFragment);
        homeTransaction.commit();
        callTextFragment = new CallTextFragment();
        goOfflineFragment = new GoOfflineFragment();
    }
}
