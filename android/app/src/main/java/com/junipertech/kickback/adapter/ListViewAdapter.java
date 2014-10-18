package com.junipertech.kickback.adapter;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.junipertech.kickback.R;
import com.junipertech.kickback.models.Friend;

/**
 * Created by Daniel on 10/13/2014.
 */
public class ListViewAdapter extends BaseAdapter{

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Friend> listFriendList = null;
    private ArrayList<Friend> arraylist;

    public ListViewAdapter(Context context, List<Friend> listFriendList) {
        mContext = context;
        this.listFriendList = listFriendList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Friend>();
        this.arraylist.addAll(listFriendList);
    }

    public class ViewHolder {
        TextView name;
        TextView username;
        //TextView phoneNumber;
    }

    @Override
    public int getCount() {
        return listFriendList.size();
    }

    @Override
    public Friend getItem(int position) {
        return listFriendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_item, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name_thing);
            holder.username = (TextView) view.findViewById(R.id.username_thing);
            //holder.phoneNumber = (TextView) view.findViewById(R.id.phonenumber_thing);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(listFriendList.get(position).getName());
        holder.username.setText(listFriendList.get(position).getUsername());
        //holder.phoneNumber.setText(listFriendList.get(position).getPhoneNumber());

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Intent intent = new Intent(mContext, INSERT CLASS NAME HERE.class);
                //intent.putExtra("name",(listFriendList.get(position).getName()));
                //intent.putExtra("username",(listFriendList.get(position).getUsername()));
                //intent.putExtra("phoneNumber",(listFriendList.get(position).getPhoneNumber()));
                //mContext.startActivity(intent);
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listFriendList.clear();
        if (charText.length() == 0) {
            listFriendList.addAll(arraylist);
        }
        else
        {
            for (Friend fl : arraylist)
            {
                if (fl.getName().toLowerCase(Locale.getDefault()).contains(charText)||fl.getUsername().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    listFriendList.add(fl);
                }
            }
        }
        notifyDataSetChanged();
    }
}
