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
import com.bramble.kickback.models.Person;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class InviteContactsAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private ArrayList<Person> persons;
    private LayoutInflater layoutInflater;
    private Context context;

    public InviteContactsAdapter(Context context, ArrayList<Person> persons) {
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

    public class ViewHolder {
        TextView contactName;
        TextView contactPhoneNumber;
        Button inviteButton;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        final Person person = persons.get(position);

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
                smsIntent.putExtra("sms_body","Come Kickback with me. Download the app at EXAMPLE URL HERE!");
                context.startActivity(smsIntent);
            }
        });

        holder.contactName.setText(person.getName());
        holder.contactPhoneNumber.setText(person.getPhoneNumber());

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
        holder.text.setText("INVITE FROM CONTACTS");
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
