package com.bramble.kickback.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bramble.kickback.R;
import com.bramble.kickback.models.RemoteUser;

import java.util.ArrayList;

public class AddFriendSearchResultsAdapter extends BaseAdapter {

    private ArrayList<RemoteUser> remoteUsers;
    private LayoutInflater layoutInflater;
    private Context context;

    public AddFriendSearchResultsAdapter(Context context, ArrayList<RemoteUser> remoteUsers) {
        this.context = context;
        this.remoteUsers = remoteUsers;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return remoteUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return remoteUsers.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        RemoteUser remoteUser = remoteUsers.get(position);
        convertView = layoutInflater.inflate(R.layout.friends_list_item, parent, false);

        return convertView;
    }

}
