package com.bramble.kickback.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bramble.kickback.R;
import com.bramble.kickback.activities.Main;
import com.bramble.kickback.models.Friend;
import com.bramble.kickback.models.User;
import com.bramble.kickback.networking.ConnectionHandler;
import com.bramble.kickback.networking.ResponseDeserializer;

import org.json.JSONException;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private FragmentManager homeManager;
    private FragmentTransaction homeTransaction;
    private OfflineFragment offlineFragment;
    private OnlineFragment onlineFragment;
    private GoOfflineFragment goOfflineFragment;
    private CallTextFragment callTextFragment;
    private TextCancelFragment textCancelFragment;
    private MarqueeFragment marqueeFragment;
    private Timer timer;

    // Keeps you from pressing the Kickback button twice
    private boolean lock;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        offlineFragment = new OfflineFragment();
        onlineFragment = new OnlineFragment();
        goOfflineFragment = new GoOfflineFragment();
        callTextFragment = new CallTextFragment();
        textCancelFragment = new TextCancelFragment();
        marqueeFragment = new MarqueeFragment();

        homeManager = getFragmentManager();
        homeTransaction = homeManager.beginTransaction();
        homeTransaction.add(R.id.offline_container, offlineFragment);
        homeTransaction.commit();

        timer = new Timer();

        lock = false;

        return view;
    }

    public void refreshGrid() {
        onlineFragment.refreshGrid();
    }

    public void refreshFriendsList() {
        ((Main) getActivity()).refreshFriendsList();
    }

    public void updateMarquee(List<Friend> selectedFriends) {
        /*
        if(selectedFriends.size() == 0) {
            homeTransaction = homeManager.beginTransaction();
            homeTransaction.remove(marqueeFragment);
            homeTransaction.commit();
        }
        else {
            TextView selectedList = (TextView) getActivity().findViewById(R.id.selected_marquee);
            String friendsToList = "";
            for(int i = 0; i < selectedFriends.size(); i ++) {
                if(i == 0) {
                    friendsToList = selectedFriends.get(i).getName();
                }
                else {
                    friendsToList = friendsToList + ", " + selectedFriends.get(i).getName();
                }
            }
            selectedList.setText(friendsToList);
        }*/
    }

    public void goOnline() {
        if (!lock) {
            lock = true;
            new InitialPollTask().execute();
        }
    }

    public void goOffline() {
        if (!lock) {
            lock = true;
            new GoOfflineTask().execute();
        }
    }

    public void replaceWithGoOffline() {
        homeTransaction = homeManager.beginTransaction();
        homeTransaction.replace(R.id.online_button_container, goOfflineFragment);
        homeTransaction.commit();
        callTextFragment = new CallTextFragment();
        textCancelFragment = new TextCancelFragment();
    }

    public void replaceWithCallText() {
        homeTransaction = homeManager.beginTransaction();
        homeTransaction.replace(R.id.online_button_container, callTextFragment);
        homeTransaction.commit();
        textCancelFragment = new TextCancelFragment();
        goOfflineFragment = new GoOfflineFragment();
    }

    public void replaceWithTextCancel() {
        homeTransaction = homeManager.beginTransaction();
        homeTransaction.replace(R.id.online_button_container, textCancelFragment);
        homeTransaction.commit();
        callTextFragment = new CallTextFragment();
        goOfflineFragment = new GoOfflineFragment();
    }

    private class InitialPollTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            String sessionId = User.getUser().getSessionId();
            try {
                String result = new ConnectionHandler().poll(sessionId);
                if (result.startsWith("200:")) {
                    result = result.replace("200:", "");
                    ResponseDeserializer.deserializePoll(result);
                    User.getUser().setOnline(true);
                    return true;
                }
                else {
                    return false;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        new PollTask().execute();
                    }
                };
                Date date = new Date((long) User.getUser().getCallMe() * 1000L);
                timer.schedule(timerTask, date);
                homeTransaction = homeManager.beginTransaction();
                homeTransaction.remove(offlineFragment);
                homeTransaction.add(R.id.online_container, onlineFragment);
                homeTransaction.add(R.id.online_button_container, goOfflineFragment);
                homeTransaction.commit();
                offlineFragment = new OfflineFragment();
                lock = false;
            } else {
                Toast.makeText(HomeFragment.this.getActivity(), "Could not connect to server!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class PollTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            String sessionId = User.getUser().getSessionId();
            try {
                String result = new ConnectionHandler().poll(sessionId);
                if (result.startsWith("200:")) {
                    result = result.replace("200:", "");
                    ResponseDeserializer.deserializePoll(result);
                    User.getUser().setOnline(true);
                    return true;
                }
                else {
                    return false;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                refreshGrid();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        new PollTask().execute();
                    }
                };
                Date date = new Date((long) User.getUser().getCallMe() * 1000L);
                timer.schedule(timerTask, date);
            }
            else {
                Toast.makeText(HomeFragment.this.getActivity(), "Could not connect to server!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GoOfflineTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                String result = new ConnectionHandler().goOffline(User.getUser().getSessionId());
                if (result.startsWith("200:")) {
                    result = result.replace("200:", "");
                    User.getUser().setSessionId(result);
                    return true;
                }
                else {
                    return false;
                }
            } catch (IOException e) {
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                homeTransaction = homeManager.beginTransaction();
                homeTransaction.remove(onlineFragment);
                homeTransaction.remove(goOfflineFragment);
                homeTransaction.add(R.id.offline_container, offlineFragment);
                homeTransaction.commit();
                onlineFragment = new OnlineFragment();
                goOfflineFragment = new GoOfflineFragment();
                timer.cancel();
                timer.purge();
                timer = new Timer();
                lock = false;
            }
            else {
                lock = false;
                Toast.makeText(HomeFragment.this.getActivity(), "Could not connect to server!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
