package com.bramble.kickback.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bramble.kickback.R;
import com.bramble.kickback.models.RemoteUser;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class AddFriendSearchResultsAdapter extends BaseAdapter implements StickyListHeadersAdapter {

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
        TextView searchNickname;
        TextView searchPhoneNumber;
        Button addButton;
        Button removeButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        RemoteUser remoteUser = remoteUsers.get(position);
        if(remoteUsers.get(position).isFriend()) {
            convertView = layoutInflater.inflate(R.layout.remove_search_result_item, parent, false);
            holder.removeButton = (Button) convertView.findViewById(R.id.search_remove_button);
            holder.searchNickname = (TextView) convertView.findViewById(R.id.search_remove_nickname_thing);
            holder.searchPhoneNumber = (TextView) convertView.findViewById(R.id.search_remove_phone_number_thing);

            holder.removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Toast.makeText(context, "remove button pressed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            convertView = layoutInflater.inflate(R.layout.add_search_result_item, parent, false);
            holder.addButton = (Button) convertView.findViewById(R.id.search_add_button);
            holder.searchNickname = (TextView) convertView.findViewById(R.id.search_add_nickname_thing);
            holder.searchPhoneNumber = (TextView) convertView.findViewById(R.id.search_add_phone_number_thing);

            holder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Toast.makeText(context,"add button pressed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        holder.searchNickname.setText(remoteUsers.get(position).getNickname());
        holder.searchPhoneNumber.setText(remoteUsers.get(position).getPhoneNumber());

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = layoutInflater.inflate(R.layout.header_sticky, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        }
        else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        String headerText;
        if(remoteUsers.get(position).isFriend()) {
            headerText = "FRIENDS";
        }
        else {
            headerText = "USERS";
        }
        /*
        else if (!filteredList.get(position).isFavorite() && !filteredList.get(position).isHidden()){
            headerText = "FRIENDS";
        }
        else {
            headerText = "HIDDEN";
        } */
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        if(remoteUsers.get(position).isFriend()) {
            return 0;
        }
        else {
            return 1;
        }
        /*
        else if (!filteredList.get(position).isFavorite() && !filteredList.get(position).isHidden()) {
            return 1;
        }
        else {
            return 2;
        }
        */
    }

    class HeaderViewHolder {
        TextView text;
    }
}
