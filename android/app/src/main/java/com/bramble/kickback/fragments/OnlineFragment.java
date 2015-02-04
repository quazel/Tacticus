package com.bramble.kickback.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.bramble.kickback.R;
import com.bramble.kickback.models.Friend;
import com.bramble.kickback.models.User;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class OnlineFragment extends Fragment {

    private ArrayList<Friend> onlineFriends;
    private GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_online, container, false);

        gridView = (GridView) view.findViewById(R.id.friendGrid);
        gridView.setAdapter(new ArrayAdapter<Friend>(view.getContext(), R.layout.online_tile_item_blue, R.id.nickname_place, User.getUser().getOnlineFriends()));

        /*
        onlineFriends = User.getUser().getOnlineFriends();

        stickyList = (StickyListHeadersListView) view.findViewById(R.id.online_list);

        onlineFriendsStickyAdapter = new OnlineFriendsStickyAdapter(view.getContext(), onlineFriends);

        stickyList.setAdapter(onlineFriendsStickyAdapter);
        */
        return view;
    }
}
