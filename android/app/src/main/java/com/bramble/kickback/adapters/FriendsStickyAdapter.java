package com.bramble.kickback.adapters;

//This adapter is to be used with the friends list activity

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bramble.kickback.R;
import com.bramble.kickback.animations.ExpandAnimation;
import com.bramble.kickback.models.Friend;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class FriendsStickyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private List<Friend> filteredList = null;
    private ArrayList<Friend> arrayList;
    private LayoutInflater inflater;
    private View mSelected;
    private boolean mClickEnabled;

    public FriendsStickyAdapter(Context context, ArrayList<Friend> inputArrayList) {
        inflater = LayoutInflater.from(context);

        this.arrayList = inputArrayList;
        this.filteredList = new ArrayList<Friend>();
        this.filteredList.addAll(arrayList);

        mSelected = null;
        mClickEnabled = true;
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
        TextView nickname;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        holder = new ViewHolder();
        convertView = inflater.inflate(R.layout.friends_list_item, parent, false);

        RelativeLayout body = (RelativeLayout)convertView.findViewById(R.id.sticky_item_body);

        if (filteredList.size()-1 == position) { //Is the last element of filteredList
            body.setBackgroundResource(R.drawable.full_width_selector_nobottom); //NO line on bottom
        }
        else {
            if(filteredList.get(position).isFavorite() != filteredList.get(position+1).isFavorite()){
                body.setBackgroundResource(R.drawable.full_width_selector_nobottom); //NO line on bottom
            }
            else {
                body.setBackgroundResource(R.drawable.full_width_selector); //line on bottom
            }
        }

        holder.name = (TextView)convertView.findViewById(R.id.name_thing);
        holder.nickname = (TextView)convertView.findViewById(R.id.nickname_thing);
        convertView.setTag(holder);

        holder.name.setText(filteredList.get(position).getName());
        holder.nickname.setText(filteredList.get(position).getNickname());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!mClickEnabled) {
                    return;
                }
                View friendsOptions = arg0.findViewById(R.id.friends_options_container);
                ExpandAnimation expandAni = new ExpandAnimation(friendsOptions, 500);
                expandAni.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        mClickEnabled = false;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mClickEnabled = true;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                if (mSelected != null) {
                    if (mSelected == arg0) {
                        friendsOptions.startAnimation(expandAni);
                        mSelected = null;
                    }
                    else {
                        View friendsOptionsOld = mSelected.findViewById(R.id.friends_options_container);
                        ExpandAnimation expandAniOld = new ExpandAnimation(friendsOptionsOld, 500);
                        expandAniOld.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                mClickEnabled = false;
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mClickEnabled = true;
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        friendsOptionsOld.startAnimation(expandAniOld);
                        friendsOptions.startAnimation(expandAni);
                        mSelected = arg0;
                    }
                }
                else {
                    mSelected = arg0;
                    friendsOptions.startAnimation(expandAni);
                }
            }
        });

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
        }
        else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        String headerText;
        if(filteredList.get(position).isFavorite()) {
            headerText = "FAVORITES";
        }
        else {
            headerText = "FRIENDS";
        }
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        if(filteredList.get(position).isFavorite()) {
            return 0;
        }
        else {
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
        else {
            for (Friend fl : arrayList){
                if (fl.getName().toLowerCase(Locale.getDefault()).contains(charText)||fl.getNickname().toLowerCase(Locale.getDefault()).contains(charText)){
                    filteredList.add(fl);
                }
            }
        }
        notifyDataSetChanged();
    }
}
