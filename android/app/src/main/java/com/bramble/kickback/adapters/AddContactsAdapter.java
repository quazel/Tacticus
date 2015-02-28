package com.bramble.kickback.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bramble.kickback.R;
import com.bramble.kickback.activities.AddFriendsSearch;
import com.bramble.kickback.models.RemoteUser;
import com.bramble.kickback.util.Util;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class AddContactsAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private ArrayList<RemoteUser> remoteUsers;
    private LayoutInflater layoutInflater;
    private Context context;

    public AddContactsAdapter(Context context, ArrayList<RemoteUser> remoteUsers) {
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
        TextView contactName;
        TextView contactNickname;
        Button addButton;
        Button removeButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        final RemoteUser remoteUser = remoteUsers.get(position);
        if(remoteUsers.get(position).isFriend()) {
            convertView = layoutInflater.inflate(R.layout.remove_search_result_item, parent, false);
            holder.removeButton = (Button) convertView.findViewById(R.id.search_remove_button);
            holder.contactName = (TextView) convertView.findViewById(R.id.search_remove_nickname_thing);
            holder.contactNickname = (TextView) convertView.findViewById(R.id.search_remove_phone_number_thing);

            holder.removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    ((AddFriendsSearch) context).removeFriend(remoteUser);
                }
            });
        }
        else {
            convertView = layoutInflater.inflate(R.layout.add_search_result_item, parent, false);
            holder.addButton = (Button) convertView.findViewById(R.id.search_add_button);
            holder.contactName = (TextView) convertView.findViewById(R.id.search_add_nickname_thing);
            holder.contactNickname = (TextView) convertView.findViewById(R.id.search_add_phone_number_thing);

            holder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    ((AddFriendsSearch) context).addFriend(remoteUser);
                }
            });
        }
        holder.contactName.setText(remoteUsers.get(position).getName());
        holder.contactNickname.setText(Util.formatPhoneNumber(remoteUsers.get(position).getNickname()));
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
        holder.text.setText("ADD FROM CONTACTS");
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
       return 0;
    }

    class HeaderViewHolder {
        TextView text;
    }
}
