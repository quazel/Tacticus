package com.junipertech.kickback.StickyTest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.junipertech.kickback.R;
import com.junipertech.kickback.models.Friend;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class StickyAdapter extends BaseAdapter implements StickyListHeadersAdapter {


    private ArrayList<Friend> arrayList;
    private LayoutInflater inflater;

    public StickyAdapter(Context context, ArrayList<Friend> inputArrayList) {
        inflater = LayoutInflater.from(context);
        arrayList = inputArrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView name;
        TextView username;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.sticky_item, parent, false);

            holder.name = (TextView)convertView.findViewById(R.id.name_thing);
            holder.username = (TextView)convertView.findViewById(R.id.username_thing);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(arrayList.get(position).getName());
        holder.username.setText(arrayList.get(position).getUsername());

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.header_sticky, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        String headerText;
        if(arrayList.get(position).getIsFavorite() == true){
            headerText = "Favorites";
        }else{
            headerText = "Friends";
        }
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) { //TODO CHANGE FROM FIRST CHAR OF NAME TO FREINDS AND FAVS
        if(arrayList.get(position).getIsFavorite()){
            return 0;
        }else{
            return 1;
        }
    }

    class HeaderViewHolder {
        TextView text;
    }

}
