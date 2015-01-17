package com.bramble.kickback.adapter;

//This adapter is to be used with the friends list activity

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bramble.kickback.R;
import com.bramble.kickback.models.Friend;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class StickyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private List<Friend> filteredList = null;
    private ArrayList<Friend> arrayList;
    private LayoutInflater inflater;

    public StickyAdapter(Context context, ArrayList<Friend> inputArrayList) {
        inflater = LayoutInflater.from(context);

        this.arrayList = inputArrayList;
        this.filteredList = new ArrayList<Friend>();
        this.filteredList.addAll(arrayList);

    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
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

        holder = new ViewHolder();
        convertView = inflater.inflate(R.layout.sticky_item, parent, false);

        LinearLayout body = (LinearLayout)convertView.findViewById(R.id.body);

        if(filteredList.size()-1 == position){ //Is the last element of filteredList
            body.setBackgroundResource(R.drawable.full_width_selector_nobottom); //NO line on bottom
        }else{
            if(filteredList.get(position).getIsFavorite() != filteredList.get(position+1).getIsFavorite()){
                body.setBackgroundResource(R.drawable.full_width_selector_nobottom); //NO line on bottom
            }else{
                body.setBackgroundResource(R.drawable.full_width_selector); //line on bottom
            }
        }

        holder.name = (TextView)convertView.findViewById(R.id.name_thing);
        holder.username = (TextView)convertView.findViewById(R.id.username_thing);
        convertView.setTag(holder);

        holder.name.setText(filteredList.get(position).getName());
        holder.username.setText(filteredList.get(position).getUsername());

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
        if(filteredList.get(position).getIsFavorite() == true){
            headerText = "FAVORITES";
        }else{
            headerText = "FRIENDS";
        }
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        if(filteredList.get(position).getIsFavorite()){
            return 0;
        }else{
            return 1;
        }
    }

    class HeaderViewHolder {
        TextView text;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        filteredList.clear();

        if (charText.length() == 0) {
            filteredList.addAll(arrayList);
        }
        else
        {
            for (Friend fl : arrayList)
            {
                if (fl.getName().toLowerCase(Locale.getDefault()).contains(charText)||fl.getUsername().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    filteredList.add(fl);
                }
            }
        }
        notifyDataSetChanged();
    }


}
