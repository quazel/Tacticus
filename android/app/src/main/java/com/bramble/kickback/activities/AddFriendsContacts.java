package com.bramble.kickback.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bramble.kickback.R;
import com.bramble.kickback.adapters.AddContactsAdapter;
import com.bramble.kickback.contacts.ContactLayer;
import com.bramble.kickback.fragments.LoadingBar;
import com.bramble.kickback.models.Friend;
import com.bramble.kickback.models.Person;
import com.bramble.kickback.models.RemoteUser;
import com.bramble.kickback.models.User;
import com.bramble.kickback.networking.ConnectionHandler;
import com.bramble.kickback.networking.ContactPayloadSerializer;
import com.bramble.kickback.networking.ResponseDeserializer;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class AddFriendsContacts extends Activity {

    private LoadingBar loadingBar;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ArrayList<Person> people;
    private AddContactsAdapter mAddContactsAdapter;
    private StickyListHeadersListView mAddList;
    private Person[] tempMergeArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends_contacts);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        people = new ArrayList<Person>();
        mAddContactsAdapter = new AddContactsAdapter(this, people);
        mAddList = (StickyListHeadersListView) findViewById(R.id.contacts_list);
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

    public void addFriend(RemoteUser remoteUser) {
        new AddFriendTask().execute(remoteUser);
    }

    public void removeFriend(RemoteUser remoteUser) {
        new RemoveFriendTask().execute(remoteUser);
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

                    people.addAll(contactPayloadSerializer.deserializeResponse(response));

                    HashMap<String, Person> contactsBuffer = new HashMap<String, Person>(contactLayer.getContacts());
                    for (Person remoteUser : people) {
                        if (contactsBuffer.containsKey(remoteUser.getPhoneNumber())) {
                            Person person = contactsBuffer.get(remoteUser.getPhoneNumber());
                            remoteUser.setName(person.getName());
                            contactsBuffer.remove(person.getPhoneNumber());
                        }
                    }
                    ArrayList<Friend> friends = User.getUser().getFriends();
                    for (Friend friend : friends) {
                        if (contactsBuffer.containsKey(friend.getPhoneNumber())) {
                            contactsBuffer.remove(friend.getPhoneNumber());
                        }
                    }
                    people.addAll(contactsBuffer.values());
                    tempMergeArr = new Person[people.size()];
                    alphabetizePartitions(people);

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

                mAddContactsAdapter.notifyDataSetChanged();
                mAddList.invalidateViews();
            }
            else {
                Toast.makeText(AddFriendsContacts.this, "Failed to retrieve contacts.", Toast.LENGTH_LONG).show();
            }
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
                mAddContactsAdapter.notifyDataSetChanged();
                mAddList.invalidateViews();
            }
            else {
                Toast.makeText(AddFriendsContacts.this, "An error occurred", Toast.LENGTH_SHORT).show();
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
                mAddContactsAdapter.notifyDataSetChanged();
                mAddList.invalidateViews();
            }
            else {
                Toast.makeText(AddFriendsContacts.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void alphabetizePartitions(ArrayList<Person> listToSort) {
        int index = 0;
        for (Person person : listToSort) {
            if (!(person instanceof RemoteUser)) {
                break;
            }
            index++;
        }
        if(index != 0) {
            mergeSort(listToSort, 0, index-1);
            mergeSort(listToSort,index,listToSort.size()-1);
        }
        else {
            mergeSort(listToSort, 0, listToSort.size() - 1);
        }
    }

    public void mergeSort(ArrayList<Person> listToSort, int start, int stop) {
        if (start < stop) {
            int middle = start + (stop - start) / 2;
            mergeSort(listToSort, start, middle);
            mergeSort(listToSort, middle + 1, stop);
            merge(listToSort, start, middle, stop);
        }
    }

    public void merge(ArrayList<Person> listToSort, int start, int middle, int stop) {
        for (int i = start; i <= stop; i++) {
            tempMergeArr[i] = listToSort.get(i);
        }
        int i = start;
        int j = middle + 1;
        int k = start;

        while (i <= middle && j <= stop) {
            if (tempMergeArr[i].getName().compareToIgnoreCase(tempMergeArr[j].getName()) <= 0) {
                listToSort.set(k, tempMergeArr[i]);
                i++;
            }
            else {
                listToSort.set(k, tempMergeArr[j]);
                j++;
            }
            k++;
        }
        while (i <= middle) {
            listToSort.set(k,tempMergeArr[i]);
            k++;
            i++;
        }

    }
}
