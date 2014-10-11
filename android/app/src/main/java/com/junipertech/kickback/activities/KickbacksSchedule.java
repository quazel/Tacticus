package com.junipertech.kickback.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.junipertech.kickback.R;
import com.junipertech.kickback.models.Kickback;
import com.junipertech.kickback.util.Globals;
import com.junipertech.kickback.util.Util;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;


public class KickbacksSchedule extends Activity {

    ArrayList<Kickback> kickbacks = Globals.kickbacks;
    ArrayList<LinearLayout> layouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kickback_schedule);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        layouts = new ArrayList<LinearLayout>();

        layouts.add((LinearLayout) findViewById(R.id.list_1));
        layouts.add((LinearLayout) findViewById(R.id.list_2));
        layouts.add((LinearLayout) findViewById(R.id.list_3));
        layouts.add((LinearLayout) findViewById(R.id.list_4));
        layouts.add((LinearLayout) findViewById(R.id.list_5));
        layouts.add((LinearLayout) findViewById(R.id.list_6));
        layouts.add((LinearLayout) findViewById(R.id.list_7));
        layouts.add((LinearLayout) findViewById(R.id.list_8));
        layouts.add((LinearLayout) findViewById(R.id.list_9));
        layouts.add((LinearLayout) findViewById(R.id.list_10));
        layouts.add((LinearLayout) findViewById(R.id.list_11));
        layouts.add((LinearLayout) findViewById(R.id.list_12));
        layouts.add((LinearLayout) findViewById(R.id.list_13));
        layouts.add((LinearLayout) findViewById(R.id.list_14));

        if (kickbacks.size() == 0)
            Globals.initKickbacks();

        addKickbacks();
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

    public void addKickbacks() {
        //temp reference variable, needs to be replaced with current datetime
        DateTime reference = DateTime.now();

        for(int i = 0; i < kickbacks.size(); i++) {
            Button bt = new Button(this);
            bt.setText(setLabel(kickbacks.get(i).getTimeRange(),kickbacks.get(i).getLocation()));
            bt.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            bt.setBackgroundResource(R.drawable.full_width_selector);
            bt.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            bt.setLines(3);
            int padding = Util.dpToPixels(getResources(), 9);
            bt.setPadding(padding, 0, padding, 0);

            try {
                layouts.get(Days.daysBetween(reference.toLocalDate(),
                        kickbacks.get(i).getStart().toLocalDate()).getDays())
                        .addView(bt);
            }
            catch (ArrayIndexOutOfBoundsException e) {

            }
        }
    }

    public Spanned setLabel(String time, String loc) {
        String location = "<br><font color='#c9c9c9'>"+loc+" </font>";
        return Html.fromHtml(time + "\n" + "<small>" + location + "</small>");
    }
}
