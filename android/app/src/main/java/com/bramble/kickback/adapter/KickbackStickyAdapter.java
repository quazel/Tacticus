package com.bramble.kickback.adapter;

//This adapter is to be used with the kickbacks schedule activity

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bramble.kickback.R;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import com.bramble.kickback.models.Kickback;
import com.bramble.kickback.util.Util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

public class KickbackStickyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private ArrayList<Kickback> kickbackArrayList;
    private LayoutInflater inflater;

    public KickbackStickyAdapter(Context context, ArrayList<Kickback> inputArrayList) {
        inflater = LayoutInflater.from(context);

        this.kickbackArrayList = inputArrayList;

    }

    @Override
    public int getCount() {
        return kickbackArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return kickbackArrayList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {

        TextView timeRange;
        TextView location;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.sticky_kickback_item, parent, false);

            LinearLayout body = (LinearLayout)convertView.findViewById(R.id.body);

            int dayDiff = kickbackArrayList.get(position).getStart().getDayOfYear() - kickbackArrayList.get(position+1).getStart().getDayOfYear(); //TODO Make sure this doesn't throw an out of bounds error
            if(dayDiff != 0){
                body.setBackgroundResource(R.drawable.full_width_selector_nobottom); //NO line on bottom
            }else{
                body.setBackgroundResource(R.drawable.full_width_selector); //line on bottom
            }

            holder.timeRange = (TextView)convertView.findViewById(R.id.kickback_time_range);
            holder.location = (TextView)convertView.findViewById(R.id.kickback_location);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.timeRange.setText(kickbackArrayList.get(position).getTimeRange());

        holder.location.setText(kickbackArrayList.get(position).getLocation());

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.header_sticky, parent, false); //NOTE WE ARE CURRENTLY USING DUPLICATE HEADER STICKY LAYOUT
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        String headerText;
        headerText = Util.getFormattedStringFromDateTime(kickbackArrayList.get(position).getStart());
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return kickbackArrayList.get(position).getStart().getDayOfMonth();
    }

    class HeaderViewHolder {
        TextView text;
    }

}


