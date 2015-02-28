package com.bramble.kickback.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.bramble.kickback.R;
import com.bramble.kickback.fragments.LoadingBar;
import com.bramble.kickback.contacts.ContactLayer;

public class AddFriendsContacts extends Activity {

    private LoadingBar loadingBar;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends_contacts);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            ft = fm.beginTransaction();
            ft.remove(loadingBar);
            ft.commit();
        }

    }
}
