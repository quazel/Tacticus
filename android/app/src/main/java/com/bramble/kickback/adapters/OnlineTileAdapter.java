package com.bramble.kickback.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bramble.kickback.R;
import com.bramble.kickback.models.Friend;

import java.util.List;

public class OnlineTileAdapter extends ArrayAdapter<Friend> {

    private LayoutInflater mInflator;
    private List<Friend> mFriends;

    public OnlineTileAdapter(Context context, int resource, List<Friend> friends) {
        super(context, resource, friends);
        mInflator = LayoutInflater.from(context);
        mFriends = friends;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder;
        Friend friend = mFriends.get(position);
        if (convertView == null) {
            int swatch = position % 2;
            switch (swatch) {
                case 0:
                    if (friend.isFavorite())
                        view = mInflator.inflate(R.layout.online_tile_item_orange, parent, false);

                    else
                        view = mInflator.inflate(R.layout.online_tile_item_blue, parent, false);
                    break;
                case 1:
                    if (friend.isFavorite())
                        view = mInflator.inflate(R.layout.online_tile_item_magenta, parent, false);
                    else
                        view = mInflator.inflate(R.layout.online_tile_item_green, parent, false);
                    break;
            }
            viewHolder = new ViewHolder();
            viewHolder.nicknameView = (TextView) view.findViewById(R.id.nickname_place);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.nicknameView.setText(friend.getNickname());

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (arg0.isActivated()) {
                    
                }
            }
        });

        return view;
    }

    private class ViewHolder {
        public TextView nicknameView;
    }
}
