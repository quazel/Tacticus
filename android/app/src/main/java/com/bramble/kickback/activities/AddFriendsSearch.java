package com.bramble.kickback.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bramble.kickback.R;
import com.bramble.kickback.adapters.AddFriendSearchResultsAdapter;
import com.bramble.kickback.fragments.LoadingBar;
import com.bramble.kickback.models.Friend;
import com.bramble.kickback.models.RemoteUser;
import com.bramble.kickback.models.User;
import com.bramble.kickback.networking.ConnectionHandler;
import com.bramble.kickback.networking.ResponseDeserializer;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class AddFriendsSearch extends Activity {

    private EditText phoneNumberEditText;
    private StickyListHeadersListView resultsList;
    private AddFriendSearchResultsAdapter resultsAdapter;
    private ArrayList<RemoteUser> remoteUsers;
    private Button addSearchButton;
    private boolean lock;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private LoadingBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends_search);
        remoteUsers = new ArrayList<RemoteUser>();
        resultsList = (StickyListHeadersListView) findViewById(R.id.search_results_list);
        addSearchButton = (Button) findViewById(R.id.add_friend_search_button);
        resultsAdapter = new AddFriendSearchResultsAdapter(this, remoteUsers);
        resultsList.setAdapter(resultsAdapter);
        fm = getFragmentManager();
        loadingBar = new LoadingBar();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        phoneNumberEditText = (EditText) findViewById(R.id.search_edittext);
        phoneNumberEditText.requestFocus();
        phoneNumberEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    searchPhoneNumber();
                }
                return false;
            }
        });
    }

    public void searchBackButtonPressed(View view) {
        finish();
    }

    public void searchPhoneNumberPressed(View view) {
        searchPhoneNumber();
    }

    public void searchPhoneNumber() {
        if(phoneNumberEditText.getText().length()>=10) {
            ft = fm.beginTransaction();
            ft.add(R.id.add_friends_search_loading_space, loadingBar);
            ft.commit();
            addSearchButton.setEnabled(false);
            new SearchUserTask().execute(phoneNumberEditText.getText().toString());
        }
        else {
            Toast.makeText(AddFriendsSearch.this, "Please enter a full phone number.", Toast.LENGTH_SHORT).show();
        }
    }

    public void addFriend(RemoteUser remoteUser) {
        new AddFriendTask().execute(remoteUser);
    }

    public void removeFriend(RemoteUser remoteUser) {
        new RemoveFriendTask().execute(remoteUser);
    }

    private class SearchUserTask extends AsyncTask<String, Void, RemoteUser> {
        @Override
        protected RemoteUser doInBackground(String... params) {
            String sessionId = User.getUser().getSessionId();
            String phoneNumber = params[0];
            try {
                String result = new ConnectionHandler().searchForPerson(sessionId, phoneNumber);
                Log.v("ConnectionHandler", result);
                if (result.startsWith("200:")) {
                    return ResponseDeserializer.deserializeSearchResults(result.replace("200:", ""));
                }
                else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(RemoteUser result) {
            if (result != null) {
                remoteUsers.clear();
                remoteUsers.add(result);
                resultsAdapter.notifyDataSetChanged();
                resultsList.invalidateViews();
            }
            else {
                Toast.makeText(AddFriendsSearch.this, "The phone number entered does not match any users.", Toast.LENGTH_SHORT).show();
                remoteUsers.clear();
                resultsAdapter.notifyDataSetChanged();
                resultsList.invalidateViews();
            }
            ft = fm.beginTransaction();
            ft.remove(loadingBar);
            ft.commit();
            addSearchButton.setEnabled(true);
        }
    }

    private class AddFriendTask extends AsyncTask<RemoteUser, Void, Boolean> {
        @Override
        protected Boolean doInBackground(RemoteUser... params) {
            String sessionId = User.getUser().getSessionId();
            String phoneNumber = params[0].getPhoneNumber();
            try {
                String response = new ConnectionHandler().addFriend(sessionId, phoneNumber);
                boolean flag = response.startsWith("200:");
                Friend friend = new Friend(params[0].getNickname(), params[0].getName(), params[0].getPhoneNumber());
                User.getUser().addFriend(friend);
                params[0].setFriend(flag);
                return flag;
            } catch (IOException e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                resultsAdapter.notifyDataSetChanged();
                resultsList.invalidateViews();
            }
            else {
                Toast.makeText(AddFriendsSearch.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class RemoveFriendTask extends AsyncTask<RemoteUser, Void, Boolean> {
        @Override
        protected Boolean doInBackground(RemoteUser... params) {
            String sessionId = User.getUser().getSessionId();
            String phoneNumber = params[0].getPhoneNumber();
            try {
                String response = new ConnectionHandler().removeFriend(sessionId, phoneNumber);
                boolean flag = response.startsWith("200:");
                ArrayList<Friend> friends = User.getUser().getFriends();
                for (Friend friend : friends) {
                    if (friend.getPhoneNumber().equals(params[0].getPhoneNumber())) {
                        friends.remove(friend);
                        break;
                    }
                }
                params[0].setFriend(!flag);
                return flag;
            } catch (IOException e) {
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                resultsAdapter.notifyDataSetChanged();
                resultsList.invalidateViews();
            }
            else {
                Toast.makeText(AddFriendsSearch.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
