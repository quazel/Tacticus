package com.bramble.kickback.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.bramble.kickback.R;
import com.bramble.kickback.adapter.KickbackStickyAdapter;
import com.bramble.kickback.models.Kickback;
import com.bramble.kickback.util.Globals;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import static com.bramble.kickback.util.Globals.initKickBacks;


public class KickbacksSchedule extends Activity {

    ArrayList<Kickback> kickbacks = Globals.kickbacks;
    StickyListHeadersListView stickyList;
    KickbackStickyAdapter stickyKickbackAdapterThing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kickback_schedule);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.schedule_color)));


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
