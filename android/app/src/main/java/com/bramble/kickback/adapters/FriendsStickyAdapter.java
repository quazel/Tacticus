package com.bramble.kickback.adapters;

//This adapter is to be used with the friends list activity

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private Context context;

    public FriendsStickyAdapter(Context context, ArrayList<Friend> inputArrayList) {
        inflater = LayoutInflater.from(context);

        this.arrayList = inputArrayList;
        this.filteredList = new ArrayList<Friend>();
        this.filteredList.addAll(arrayList);
        this.context = context;
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
        RelativeLayout body;
        TextView name;
        TextView nickname;
        Button favoriteButton;
        //Button hideButton;
        Button removeButton;
        Button blockButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Friend friend = arrayList.get(position);
        holder = new ViewHolder();
        convertView = inflater.inflate(R.layout.friends_list_item, parent, false);

        holder.body = (RelativeLayout)convertView.findViewById(R.id.friends_item_background);

        if (filteredList.size()-1 == position) { //Is the last element of filteredList
            holder.body.setBackgroundResource(R.drawable.full_width_selector_nobottom); //NO line on bottom
        }
        else if(filteredList.get(position).isFavorite() && !filteredList.get(position+1).isFavorite()){
            holder.body.setBackgroundResource(R.drawable.full_width_selector_nobottom); //NO line on bottom
        }
        else if(!filteredList.get(position).isHidden() && filteredList.get(position+1).isHidden()) {
            holder.body.setBackgroundResource(R.drawable.full_width_selector_nobottom); //NO line on bottom
        }
        else {
            holder.body.setBackgroundResource(R.drawable.full_width_selector); //line on bottom
        }

        holder.name = (TextView)convertView.findViewById(R.id.name_thing);
        holder.nickname = (TextView)convertView.findViewById(R.id.nickname_thing);
        holder.favoriteButton = (Button)convertView.findViewById(R.id.toggle_favorite);
        //holder.hideButton = (Button)convertView.findViewById(R.id.toggle_hidden);

        if(friend.isFavorite()) {
            holder.favoriteButton.setText("UNFAVORITE");
            //holder.hideButton.setVisibility(View.GONE);
        }
        /*
        if(friend.isHidden()) {
            holder.hideButton.setText("UNHIDE");
            holder.favoriteButton.setVisibility(View.GONE);
        }*/

        if(!friend.isFavorite()&&!friend.isHidden()) {
            holder.favoriteButton.setVisibility(View.VISIBLE);
            //holder.hideButton.setVisibility(View.VISIBLE);
            holder.favoriteButton.setBackgroundColor(context.getResources().getColor(R.color.title_text_color));
            holder.favoriteButton.setText("FAVORITE");
            //holder.hideButton.setText("HIDE");
            //holder.hideButton.setVisibility(View.GONE);
        }
        holder.removeButton = (Button)convertView.findViewById(R.id.remove_friend);
        holder.blockButton = (Button)convertView.findViewById(R.id.block_user);
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
                ExpandAnimation expandAni = new ExpandAnimation(friendsOptions, 250);
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
                        ExpandAnimation expandAniOld = new ExpandAnimation(friendsOptionsOld, 250);
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

        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(context,"favorite button pressed", Toast.LENGTH_SHORT).show();
            }
        });

        /*
        holder.hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(context,"hide button pressed", Toast.LENGTH_SHORT).show();
            }
        });
        */

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(context,"remove button pressed", Toast.LENGTH_SHORT).show();
            }
        });

        holder.blockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(context,"block button pressed", Toast.LENGTH_SHORT).show();
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
        if(filteredList.get(position).isFavorite()) {
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
