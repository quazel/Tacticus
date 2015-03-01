package com.bramble.kickback.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bramble.kickback.R;
import com.bramble.kickback.adapters.AddContactsAdapter;
import com.bramble.kickback.adapters.InviteContactsAdapter;
import com.bramble.kickback.fragments.LoadingBar;
import com.bramble.kickback.contacts.ContactLayer;
import com.bramble.kickback.models.Person;
import com.bramble.kickback.models.RemoteUser;
import com.bramble.kickback.models.User;
import com.bramble.kickback.networking.ConnectionHandler;
import com.bramble.kickback.networking.ContactPayloadSerializer;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class AddFriendsContacts extends Activity {

    private LoadingBar loadingBar;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ArrayList<Person> uninvitedPeople;
    private ArrayList<RemoteUser> invitedPeople;
    private InviteContactsAdapter mInviteContactsAdapter;
    private AddContactsAdapter mAddContactsAdapter;
    private StickyListHeadersListView mInviteList;
    private StickyListHeadersListView mAddList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends_contacts);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        uninvitedPeople = new ArrayList<Person>();
        invitedPeople = new ArrayList<RemoteUser>();
        mInviteContactsAdapter = new InviteContactsAdapter(this, uninvitedPeople);
        mAddContactsAdapter = new AddContactsAdapter(this, invitedPeople);
        mInviteList = (StickyListHeadersListView) findViewById(R.id.contacts_without_accounts_list);
        mAddList = (StickyListHeadersListView) findViewById(R.id.contacts_with_accounts_list);
        mInviteList.setAdapter(mInviteContactsAdapter);
        mAddList.setAdapter(mAddContactsAdapter);

        loadingBar = new LoadingBar();

        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.contacts_loading_bar_container, loadingBar);
        ft.commit();
        new CheckContactsTask().execute();
    }

    public void contactsBackButtonPressed(View view) {
        finish();
    }

    private class CheckContactsTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            ContactLayer.initialize(getContentResolver());
            try {
                ContactLayer contactLayer = ContactLayer.getInstance();
                ContactPayloadSerializer contactPayloadSerializer = new ContactPayloadSerializer(contactLayer.getContacts());

                String jsonString = contactPayloadSerializer.serializePayload();
                String response = new ConnectionHandler().checkContacts(User.getUser().getSessionId(), jsonString);

                if (response.startsWith("200:")) {
                    response = response.replace("200:", "");

                    invitedPeople.addAll(contactPayloadSerializer.deserializeResponse(response));

                    HashMap<String, Person> contactsBuffer = new HashMap<String, Person>(contactLayer.getContacts());
                    for (RemoteUser remoteUser : invitedPeople) {
                        if (contactsBuffer.containsKey(remoteUser.getPhoneNumber())) {
                            contactsBuffer.remove(remoteUser.getPhoneNumber());
                        }
                    }
                    uninvitedPeople.addAll(contactsBuffer.values());

                    return true;
                }
                else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                ft = fm.beginTransaction();
                ft.remove(loadingBar);
                ft.commit();

                mInviteContactsAdapter.notifyDataSetChanged();
                mAddContactsAdapter.notifyDataSetChanged();
                mInviteList.invalidateViews();
                mAddList.invalidateViews();
            }
            else {
                Toast.makeText(AddFriendsContacts.this, "Failed to retrieve contacts.", Toast.LENGTH_LONG).show();
            }
        }

    }
}
