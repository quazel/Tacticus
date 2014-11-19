package com.junipertech.kickback.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.junipertech.kickback.R;
import com.junipertech.kickback.adapter.KickbackStickyAdapter;
import com.junipertech.kickback.models.Kickback;
import com.junipertech.kickback.util.Globals;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import static com.junipertech.kickback.util.Globals.initKickBacks;


public class KickbacksSchedule extends Activity {

    ArrayList<Kickback> kickbacks = Globals.kickbacks;

    StickyListHeadersListView stickyList;

    KickbackStickyAdapter stickyKickbackAdapterThing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kickback_schedule);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        if(kickbacks.size() == 0) {
            initKickBacks();
        }

        stickyList = (StickyListHeadersListView)findViewById(R.id.kickbacks_list);

        stickyKickbackAdapterThing = new KickbackStickyAdapter(this,kickbacks);

        stickyList.setAdapter(stickyKickbackAdapterThing);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.kickback_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add)
        {
            Intent addIntent = new Intent(this, AddKickback.class);
            startActivity(addIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
