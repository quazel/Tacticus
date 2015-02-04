package com.bramble.kickback.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bramble.kickback.R;
import com.bramble.kickback.adapters.FriendsStickyAdapter;
import com.bramble.kickback.models.Friend;
import com.bramble.kickback.models.User;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class FriendsFragment extends Fragment {

    private ArrayList<Friend> friends;
    private StickyListHeadersListView stickyList;
    private FriendsStickyAdapter friendsStickyAdapter;
    private EditText searchInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_friends, container, false);

        friends = User.getUser().getFriends();

        stickyList = (StickyListHeadersListView) view.findViewById(R.id.all_friends_list);

        friendsStickyAdapter = new FriendsStickyAdapter(view.getContext(), friends);

        stickyList.setAdapter(friendsStickyAdapter);

        return view;
    }

}
