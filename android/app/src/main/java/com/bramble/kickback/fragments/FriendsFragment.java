package com.bramble.kickback.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private RelativeLayout searchLayout;
    private RelativeLayout settingsLayout;
    private TextView friendsTitle;
    private EditText searchInput;
    private boolean searching;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_friends, container, false);
        friends = User.getUser().getFriends();
        stickyList = (StickyListHeadersListView) view.findViewById(R.id.all_friends_list);
        friendsStickyAdapter = new FriendsStickyAdapter(view.getContext(), friends, this);
        stickyList.setAdapter(friendsStickyAdapter);
        searchLayout = (RelativeLayout) view.findViewById(R.id.friend_fragment_search_layout);
        settingsLayout = (RelativeLayout) view.findViewById(R.id.friend_fragment_settings_layout);
        friendsTitle = (TextView) view.findViewById(R.id.friends_textview);
        searchInput = (EditText) view.findViewById(R.id.friends_search_edittext);
        searchInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0) {
                    friendsStickyAdapter.filter(s.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        searching = false;
        return view;
    }

    public void refreshFriendsList() {
        friendsStickyAdapter = new FriendsStickyAdapter(friendsStickyAdapter.getContext(), friends, this);
        stickyList.setAdapter(friendsStickyAdapter);
        stickyList.invalidateViews();
    }

    public void setUpSearch() {
        searchLayout.setVisibility(View.GONE);
        settingsLayout.setVisibility(View.GONE);
        friendsTitle.setVisibility(View.GONE);
        searchInput.setVisibility(View.VISIBLE);
        searching = true;
        searchInput.setFocusableInTouchMode(true);
        searchInput.requestFocus();
        final InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(searchInput, InputMethodManager.SHOW_IMPLICIT);
    }

    public void removeSearch() {
        searchLayout.setVisibility(View.VISIBLE);
        settingsLayout.setVisibility(View.VISIBLE);
        friendsTitle.setVisibility(View.VISIBLE);
        searchInput.setVisibility(View.GONE);
        searching = false;
    }

    public boolean isSearching() {
        if(searching) {
            return true;
        }
        return false;
    }

    public EditText getSearchInput() {
        return searchInput;
    }
}
