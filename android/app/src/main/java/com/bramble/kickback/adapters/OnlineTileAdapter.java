package com.bramble.kickback.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.bramble.kickback.R;
import com.bramble.kickback.activities.Main;
import com.bramble.kickback.models.Friend;

import java.util.ArrayList;
import java.util.List;

public class OnlineTileAdapter extends ArrayAdapter<Friend> {

    private LayoutInflater mInflator;
    private List<Friend> mFriends;
    private List<Friend> mSelected;
    private Main mMainFragment;

    public OnlineTileAdapter(Context context, int resource, List<Friend> friends, Activity parentActivity) {
        super(context, resource, friends);
        mInflator = LayoutInflater.from(context);
        mFriends = friends;
        mSelected = new ArrayList<Friend>();
        mMainFragment = (Main) parentActivity;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder;
        Friend friend = mFriends.get(position);

        int swatch = position % 2;
        switch (swatch) {
            case 0:
                if (friend.isFavorite()) {
                    view = mInflator.inflate(R.layout.online_tile_item_orange, parent, false);
                }
                else
                    view = mInflator.inflate(R.layout.online_tile_item_blue, parent, false);
                break;
            case 1:
                if (friend.isFavorite()) {
                    view = mInflator.inflate(R.layout.online_tile_item_magenta, parent, false);
                }
                else
                    view = mInflator.inflate(R.layout.online_tile_item_green, parent, false);
                break;
        }

        viewHolder = new ViewHolder();
        viewHolder.nicknameView = (TextView) view.findViewById(R.id.nickname_place);
        view.setTag(viewHolder);

        viewHolder.nicknameView.setText(friend.getNickname());
        viewHolder.index = position;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                arg0.setClickable(false);
                GridView parentGridView = (GridView) parent;
                ViewHolder holder = (ViewHolder) arg0.getTag();
                parentGridView.setItemChecked(holder.index, !arg0.isActivated());
                arg0.setActivated(!arg0.isActivated());
                if (arg0.isActivated()) {
                    if (!mSelected.contains(mFriends.get(holder.index))) {
                        mSelected.add(mFriends.get(holder.index));
                    }
                }
                else {
                    if (mSelected.contains(mFriends.get(holder.index))) {
                        mSelected.remove(mFriends.get(holder.index));
                    }
                }
                mMainFragment.updateSelectedFriends(mSelected);
                arg0.setClickable(true);
            }
        });

        return view;
    }

    public List<Friend> getSelectedFriends() {
        return mSelected;
    }

    public void clearSelectedItems() {
        mSelected.clear();
        mMainFragment.updateSelectedFriends(mSelected);
    }

    private class ViewHolder {
        public TextView nicknameView;
        public int index;
    }
}
