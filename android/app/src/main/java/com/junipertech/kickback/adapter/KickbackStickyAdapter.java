package com.junipertech.kickback.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.junipertech.kickback.R;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import com.junipertech.kickback.models.Kickback;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

public class KickbackStickyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    DateTimeFormatter st;
    DateTimeFormatter nd;
    DateTimeFormatter rd;
    DateTimeFormatter th;

    private ArrayList<Kickback> kickbackArrayList;
    private LayoutInflater inflater;

    public KickbackStickyAdapter(Context context, ArrayList<Kickback> inputArrayList) {
        createDateFormatters();

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
        TextView start;
        TextView stop;
        TextView location;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.sticky_kickback_item, parent, false);

            holder.start = (TextView)convertView.findViewById(R.id.kickback_start_time);
            holder.stop = (TextView)convertView.findViewById(R.id.kickback_stop_time);
            holder.location = (TextView)convertView.findViewById(R.id.kickback_location);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.start.setText(kickbackArrayList.get(position).getStart().toString()); // TODO MAKE THIS DATE OBJECT INTO A STRING
        holder.stop.setText(kickbackArrayList.get(position).getStop().toString()); // TODO MAKE THIS DATE OBJECT INTO A STRING
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
        headerText = getFormattedStringFromDateTime(kickbackArrayList.get(position).getStart());
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


    public void createDateFormatters(){
        st = new DateTimeFormatterBuilder()
                .appendDayOfWeekText()
                .appendLiteral(", ")
                .appendMonthOfYearText()
                .appendLiteral(" ")
                .appendDayOfMonth(1)
                .appendLiteral("st")

                .toFormatter();


        nd = new DateTimeFormatterBuilder()
                .appendDayOfWeekText()
                .appendLiteral(", ")
                .appendMonthOfYearText()
                .appendLiteral(" ")
                .appendDayOfMonth(1)
                .appendLiteral("nd")
                .toFormatter();

        rd = new DateTimeFormatterBuilder()
                .appendDayOfWeekText()
                .appendLiteral(", ")
                .appendMonthOfYearText()
                .appendLiteral(" ")
                .appendDayOfMonth(1)
                .appendLiteral("rd")
                .toFormatter();

        th = new DateTimeFormatterBuilder()
                .appendDayOfWeekText()
                .appendLiteral(", ")
                .appendMonthOfYearText()
                .appendLiteral(" ")
                .appendDayOfMonth(1)
                .appendLiteral("th")
                .toFormatter();
    }

    public String getFormattedStringFromDateTime(DateTime aParticularDay) {
        int j = aParticularDay.getDayOfMonth() % 10;
        String formatted;
        switch (j) {
            case 1:
                formatted = aParticularDay.toString(st);
                break;
            case 2:
                formatted = aParticularDay.toString(nd);
                break;
            case 3:
                formatted = aParticularDay.toString(rd);
                break;
            default:
                formatted = aParticularDay.toString(th);
        }
        return formatted;
    }
}


