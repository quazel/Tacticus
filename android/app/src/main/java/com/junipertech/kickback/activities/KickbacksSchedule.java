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

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;

import java.util.ArrayList;


public class KickbacksSchedule extends Activity {

    ArrayList<Kickback> kickbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kickback_schedule);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        kickbacks = new ArrayList<Kickback>();
        kickbacks.add(new Kickback(new DateTime(2014,10,9,5,30,0,0),
                                   new DateTime(2014,10,9,8,0,0,0),"Tempe, AZ"));
        kickbacks.add(new Kickback(new DateTime(2014,10,10,6,30,0,0),
                                   new DateTime(2014,10,10,9,0,0,0),"Woodstock, VT"));
        kickbacks.add(new Kickback(new DateTime(2014,10,10,12,30,0,0),
                                   new DateTime(2014,10,10,16,0,0,0),"San Francisco, CA"));
        kickbacks.add(new Kickback(new DateTime(2014,10,13,5,30,0,0),
                                   new DateTime(2014,10,13,8,0,0,0),"New York City, NY"));
        kickbacks.add(new Kickback(new DateTime(2014,10,15,15,30,0,0),
                                   new DateTime(2014,10,15,18,0,0,0),"Boston, MA"));
        kickbacks.add(new Kickback(new DateTime(2014,10,20,18,30,0,0),
                                   new DateTime(2014,10,20,22,0,0,0),"Seattle, OR"));


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
        if (id == R.id.action_settings)
        {
            Intent settingsIntent = new Intent(this, Settings.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addKickbacks()
    {
        //temp reference variable, needs to be replaced with current datetime
        DateTime reference = new DateTime(2014,10,9,2,30,0,0);

        for(int i = 0; i < kickbacks.size(); i++)
        {
            Button bt = new Button(this);
            bt.setText(setLabel(kickbacks.get(i).toString(),kickbacks.get(i).getLocation()));
            bt.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            bt.setBackgroundResource(R.drawable.full_width_selector);
            bt.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            bt.setLines(3);

            if(reference.get(DateTimeFieldType.dayOfMonth())==
                             kickbacks.get(i).getStart().get(DateTimeFieldType.dayOfMonth())) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.list_1);

                layout.addView(bt);
            }
            else if(reference.get(DateTimeFieldType.dayOfMonth())==
                    kickbacks.get(i).getStart().get(DateTimeFieldType.dayOfMonth())-1) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.list_2);

                layout.addView(bt);
            }
            else if(reference.get(DateTimeFieldType.dayOfMonth())==
                    kickbacks.get(i).getStart().get(DateTimeFieldType.dayOfMonth())-2) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.list_3);

                layout.addView(bt);
            }
            else if(reference.get(DateTimeFieldType.dayOfMonth())==
                    kickbacks.get(i).getStart().get(DateTimeFieldType.dayOfMonth())-3) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.list_4);

                layout.addView(bt);
            }
            else if(reference.get(DateTimeFieldType.dayOfMonth())==
                    kickbacks.get(i).getStart().get(DateTimeFieldType.dayOfMonth())-4) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.list_5);

                layout.addView(bt);
            }
            else if(reference.get(DateTimeFieldType.dayOfMonth())==
                    kickbacks.get(i).getStart().get(DateTimeFieldType.dayOfMonth())-5) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.list_6);

                layout.addView(bt);
            }
            else if(reference.get(DateTimeFieldType.dayOfMonth())==
                    kickbacks.get(i).getStart().get(DateTimeFieldType.dayOfMonth())-6) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.list_7);

                layout.addView(bt);
            }
            else if(reference.get(DateTimeFieldType.dayOfMonth())==
                    kickbacks.get(i).getStart().get(DateTimeFieldType.dayOfMonth())-7) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.list_8);

                layout.addView(bt);
            }
            else if(reference.get(DateTimeFieldType.dayOfMonth())==
                    kickbacks.get(i).getStart().get(DateTimeFieldType.dayOfMonth())-8) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.list_9);

                layout.addView(bt);
            }
            else if(reference.get(DateTimeFieldType.dayOfMonth())==
                    kickbacks.get(i).getStart().get(DateTimeFieldType.dayOfMonth())-9) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.list_10);

                layout.addView(bt);
            }
            else if(reference.get(DateTimeFieldType.dayOfMonth())==
                    kickbacks.get(i).getStart().get(DateTimeFieldType.dayOfMonth())-10) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.list_11);

                layout.addView(bt);
            }
            else if(reference.get(DateTimeFieldType.dayOfMonth())==
                    kickbacks.get(i).getStart().get(DateTimeFieldType.dayOfMonth())-11) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.list_12);

                layout.addView(bt);
            }
            else if(reference.get(DateTimeFieldType.dayOfMonth())==
                    kickbacks.get(i).getStart().get(DateTimeFieldType.dayOfMonth())-12) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.list_13);

                layout.addView(bt);
            }
            else if(reference.get(DateTimeFieldType.dayOfMonth())==
                    kickbacks.get(i).getStart().get(DateTimeFieldType.dayOfMonth())-13) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.list_14);

                layout.addView(bt);
            }
            //else/ case to check if its tomorrow
            // else/case to check ...

        }
    }

    public Spanned setLabel(String time, String loc)
    {
        String location = "<br><font color='#c9c9c9'>"+loc+" </font>";
        return Html.fromHtml(time + "\n" + "<small>" + location + "</small>");
    }
}
