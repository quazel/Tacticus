package com.bramble.kickback.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bramble.kickback.R;

public class AddFriendsFragment extends Fragment{

    private FragmentManager homeManager;
    private FragmentTransaction homeTransaction;
    private AddFriendsPortal addFriendsPortal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_friends, container, false);

        addFriendsPortal = new AddFriendsPortal();

        homeManager = getFragmentManager();
        homeTransaction = homeManager.beginTransaction();
        homeTransaction.add(R.id.add_friends_container, addFriendsPortal);
        homeTransaction.commit();

        return view;
    }
}
