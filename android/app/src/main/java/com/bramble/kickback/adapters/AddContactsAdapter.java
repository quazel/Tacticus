package com.bramble.kickback.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bramble.kickback.R;
import com.bramble.kickback.activities.AddFriendsContacts;
import com.bramble.kickback.activities.AddFriendsSearch;
import com.bramble.kickback.models.Person;
import com.bramble.kickback.models.RemoteUser;
import com.bramble.kickback.util.Util;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class AddContactsAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private ArrayList<Person> persons;
    private LayoutInflater layoutInflater;
    private Context context;

    public AddContactsAdapter(Context context, ArrayList<Person> persons) {
        this.context = context;
        this.persons = persons;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int position) {
        return persons.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final Person person = persons.get(position);

        if (person instanceof RemoteUser) {
            AddViewHolder holder = new AddViewHolder();
            final RemoteUser remoteUser = (RemoteUser) person;
            if(remoteUser.isFriend()) {
                convertView = layoutInflater.inflate(R.layout.remove_search_result_item, parent, false);
                holder.removeButton = (Button) convertView.findViewById(R.id.search_remove_button);
                holder.contactName = (TextView) convertView.findViewById(R.id.search_remove_nickname_thing);
                holder.contactNickname = (TextView) convertView.findViewById(R.id.search_remove_phone_number_thing);

                holder.removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        ((AddFriendsContacts) context).removeFriend(remoteUser);
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
                        ((AddFriendsContacts) context).addFriend(remoteUser);
                    }
                });
            }
            holder.contactName.setText(remoteUser.getName());
            holder.contactNickname.setText(Util.formatPhoneNumber(remoteUser.getPhoneNumber()));
            return convertView;
        }

        else {
            InviteViewHolder holder = new InviteViewHolder();
            convertView = layoutInflater.inflate(R.layout.invite_contact_item, parent, false);
            holder.contactName = (TextView) convertView.findViewById(R.id.contact_invite_name_thing);
            holder.contactPhoneNumber = (TextView) convertView.findViewById(R.id.contact_invite_number_text);
            holder.inviteButton = (Button) convertView.findViewById(R.id.contact_invite_button);

            holder.inviteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("address", person.getPhoneNumber());
                    smsIntent.putExtra("sms_body", "Come Kickback with me. Download the app at https://betas.to/xVw1btwL");
                    context.startActivity(smsIntent);
                }
            });

            holder.contactName.setText(person.getName());
            holder.contactPhoneNumber.setText(Util.formatPhoneNumber(person.getPhoneNumber()));

            return convertView;
        }
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
        if (persons.get(position) instanceof RemoteUser) {
            holder.text.setText("ADD FROM CONTACTS");
        }
        else {
            holder.text.setText("INVITE FROM CONTACTS");
        }
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        if (persons.get(position) instanceof RemoteUser) {
            return 0;
        }
        else {
            return 1;
        }
    }

    class HeaderViewHolder {
        TextView text;
    }

    public class AddViewHolder {
        TextView contactName;
        TextView contactNickname;
        Button addButton;
        Button removeButton;
    }

    public class InviteViewHolder {
        TextView contactName;
        TextView contactPhoneNumber;
        Button inviteButton;
    }

}
